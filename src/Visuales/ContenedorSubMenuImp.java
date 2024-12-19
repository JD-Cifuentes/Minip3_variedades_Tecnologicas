package Visuales;

import Entidades.Negocio;
import Utils.AccessPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ContenedorSubMenuImp {

    private JPanel Contenedor_BG; //panel principal que contiene los submenus visibles
    //mapea las opciones del menu (OpcionesMenuImpresora) con sus respectivos paneles (AccessPanel)
    private final HashMap<OpcionesMenuImpresora, AccessPanel> mapeoSubImprMenu;
    // dclaracion de los paneles especificos
    private final SubMenuImpresora SubMenuImpresora;
    private final RegistroPlotter registroPlotter;
    private final RegistroTinta registroTinta;
    private final RegistroLaser registroLaser;

    /*Enum que define las opciones disponibles en el menu de impresoras para cambiar
        el contenido del panel principal
     */
    enum OpcionesMenuImpresora{
        SUB_MENU_IMPR,
        PLOTTER_MENU,
        TINTA_MENU,
        LASER_MENU;
    }

    // Constructor: inicializa los elementos del contenedor
    public ContenedorSubMenuImp(Negocio local) {

        this.mapeoSubImprMenu = new HashMap<>(); // Inicializa el HashMap que vincula opciones con paneles

        // Inicializa los paneles con la instancia del negocio y este contenedor
        SubMenuImpresora = new SubMenuImpresora(this, local);
        registroPlotter = new RegistroPlotter(this, local);
        registroTinta = new RegistroTinta(this, local);
        registroLaser = new RegistroLaser(this, local);

        // Mapea cada opcion del menu a su respectivo panel
        mapeoSubImprMenu.put(OpcionesMenuImpresora.SUB_MENU_IMPR, SubMenuImpresora);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.PLOTTER_MENU, registroPlotter);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.TINTA_MENU, registroTinta);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.LASER_MENU, registroLaser);

        cambiarVisibilidadContenido(OpcionesMenuImpresora.SUB_MENU_IMPR); //muestra el menu principal
    }

    public void cambiarVisibilidadContenido(OpcionesMenuImpresora enumPanel){
        /*
         * Cambia el contenido visible del panel principal según la opción seleccionada
         enumPanel La opción del menú que se quiere mostrar.
         */
        try{
            JPanel panel = mapeoSubImprMenu.get(enumPanel).getPanel(); // Obtiene el panel correspondiente al enum seleccionado

            panel.setSize(435,400);
            panel.setMaximumSize(new Dimension(435,400));
            panel.setLocation(0,0);
            panel.setVisible(true);

            //limpia el panel principal y agrega el nuevo contenido
            Contenedor_BG.removeAll();
            Contenedor_BG.add(panel,BorderLayout.CENTER);
            Contenedor_BG.revalidate(); //actualiza el contenedor
            Contenedor_BG.repaint(); //redibuja el contenedor

        }catch (NullPointerException e){
            System.out.println(e + " en ContenedorSubMenuImp - cambiarVisibilidadContenido");
        }

    }

    public JPanel getContenedorSubMenuImpre() {
        return this.Contenedor_BG;
    }
}

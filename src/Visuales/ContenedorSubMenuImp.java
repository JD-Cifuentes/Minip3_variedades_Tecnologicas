package Visuales;

import Entidades.Negocio;
import Utils.AccessPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ContenedorSubMenuImp {
    private JPanel Contenedor_BG;
    private final HashMap<OpcionesMenuImpresora, AccessPanel> mapeoSubImprMenu;
    private final SubMenuImpresora SubMenuImpresora;
    private final RegistroPlotter registroPlotter;
    private final RegistroTinta registroTinta;
    private final RegistroLaser registroLaser;

    enum OpcionesMenuImpresora{
        SUB_MENU_IMPR,
        PLOTTER_MENU,
        TINTA_MENU,
        LASER_MENU;
    }

    public ContenedorSubMenuImp(Negocio local) {
        this.mapeoSubImprMenu = new HashMap<>();
        SubMenuImpresora = new SubMenuImpresora(this, local);
        registroPlotter = new RegistroPlotter(this, local);
        registroTinta = new RegistroTinta(this, local);
        registroLaser = new RegistroLaser(this, local);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.SUB_MENU_IMPR, SubMenuImpresora);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.PLOTTER_MENU, registroPlotter);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.TINTA_MENU, registroTinta);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.LASER_MENU, registroLaser);

        cambiarVisibilidadContenido(OpcionesMenuImpresora.SUB_MENU_IMPR);

    }

    public void cambiarVisibilidadContenido(OpcionesMenuImpresora enumPanel){
        try{
            JPanel panel = mapeoSubImprMenu.get(enumPanel).getPanel();
            panel.setSize(435,400);
            panel.setMaximumSize(new Dimension(435,400));
            panel.setLocation(0,0);
            panel.setVisible(true);
            Contenedor_BG.removeAll();
            Contenedor_BG.add(panel,BorderLayout.CENTER);
            Contenedor_BG.revalidate();
            Contenedor_BG.repaint();
        }catch (NullPointerException e){
            System.out.println(e + " en ContenedorSubMenuImp - cambiarVisibilidadContenido");
        }

    }

    public JPanel getContenedorSubMenuImpre() {
        return this.Contenedor_BG;
    }
}

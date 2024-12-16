package Visuales;

import Utils.AccessPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ContenedorSubMenuImp {
    private JPanel Contenedor_BG;
    private final HashMap<OpcionesMenuImpresora, AccessPanel> mapeoSubImprMenu;
    private final SubMenuImpresora SubMenuImpresora;
    private final RegistroPlotter registroPlotter;

    enum OpcionesMenuImpresora{
        SUB_MENU_IMPR,
        PLOTTER_MENU;
    }

    public ContenedorSubMenuImp() {
        this.mapeoSubImprMenu = new HashMap<>();
        SubMenuImpresora = new SubMenuImpresora(this);
        registroPlotter = new RegistroPlotter(this);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.SUB_MENU_IMPR, SubMenuImpresora);
        mapeoSubImprMenu.put(OpcionesMenuImpresora.PLOTTER_MENU, registroPlotter);

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

package Visuales;

import Entidades.Negocio;
import Utils.AccessPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubMenuImpresora implements AccessPanel {

    //componentes
    private JPanel SubMenuImpresion_BG;
    private JLabel lbl_seleccionImpresion;
    private JButton bttn_FotoTinta;
    private JButton bttn_imprLaser;
    private JButton bttn_plotter;

    //constructor: recibe el contenedor del submenú y el objeto Negocio
    public SubMenuImpresora(ContenedorSubMenuImp contenedorSubMenuImp, Negocio local) {
        bttn_plotter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //muestra el menu plotter
                contenedorSubMenuImp.cambiarVisibilidadContenido(ContenedorSubMenuImp
                                    .OpcionesMenuImpresora.PLOTTER_MENU);
            }
        });

        bttn_FotoTinta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //muestra el menu tinta
                contenedorSubMenuImp.cambiarVisibilidadContenido(ContenedorSubMenuImp
                                    .OpcionesMenuImpresora.TINTA_MENU);
            }
        });

        bttn_imprLaser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //muestra el menu laser
                contenedorSubMenuImp.cambiarVisibilidadContenido(ContenedorSubMenuImp
                                    .OpcionesMenuImpresora.LASER_MENU);

            }
        });
    }

    //retorna el panel del submenu
    @Override
    public JPanel getPanel() {
        return SubMenuImpresion_BG;
    }
}

package Visuales;

import Utils.AccessPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubMenuImpresora implements AccessPanel {
    private JPanel SubMenuImpresion_BG;
    private JLabel lbl_seleccionImpresion;
    private JButton bttn_FotoTinta;
    private JButton bttn_imprLaser;
    private JButton bttn_plotter;


    public SubMenuImpresora(ContenedorSubMenuImp contenedorSubMenuImp) {
        //registroPlotter = panelRaiz;
        bttn_plotter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedorSubMenuImp
                        .cambiarVisibilidadContenido(ContenedorSubMenuImp
                                .OpcionesMenuImpresora
                                .PLOTTER_MENU);
            }
        });


    }


    @Override
    public JPanel getPanel() {
        return SubMenuImpresion_BG;
    }
}

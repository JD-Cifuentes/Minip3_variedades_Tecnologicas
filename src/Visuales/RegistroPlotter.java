package Visuales;

import Utils.AccessPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroPlotter implements AccessPanel {
    private JPanel Plotter_BG;
    private JLabel lbl_plotterTitulo;
    private JButton bttn_volver;


    public RegistroPlotter(ContenedorSubMenuImp contenedor) {
        bttn_volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedor
                        .cambiarVisibilidadContenido(ContenedorSubMenuImp
                                .OpcionesMenuImpresora
                                .SUB_MENU_IMPR);
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return this.Plotter_BG;
    }
}

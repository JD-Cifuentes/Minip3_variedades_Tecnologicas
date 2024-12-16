package Visuales;

import Utils.AccessPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroPlotter implements AccessPanel {
    private JPanel Plotter_BG;
    private JLabel lbl_plotterTitulo;
    private JButton bttn_volver;
    private JTextField textF_cantidad;
    private JRadioButton RadBttn_plano;
    private JButton bttn_registrar;
    private JRadioButton RadBttn_publicidad;
    private JLabel lbl_cantidad;
    private JTextField textF_ancho;
    private JTextField textF_alto;
    private JTextField txtReadOnly_valorcm2;
    private JLabel lbl_valorcm2;
    private JLabel lbl_alto;
    private JLabel lbl_cian;
    private JTextField txtReadOnly_cian;
    private JTextField txtReadOnly_amarillo;
    private JTextField txtReadOnly_magenta;
    private JTextField txtReadOnly_negro;
    private JLabel lbl_amarillo;
    private JLabel lbl_negro;
    private JLabel lbl_magenta;
    private JLabel lbl_ancho;
    private JLabel lbl_nivelTinta;
    private JPanel JPanel_plotter;


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

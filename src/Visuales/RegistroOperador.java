package Visuales;


import Utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroOperador implements AccessPanel {
    private JButton btn_volver;
    private JPanel Operadores_BG;
    private JLabel lbl_menuOperador;
    private JLabel lbl_tipoOperador;
    private JLabel lbl_cantidad;
    private JLabel lbl_tipoServicio;
    private JComboBox cBox_Operador;
    private JComboBox cBox_Servicio;
    private JFormattedTextField textFF_cantidad;
    private JTextField textF_valorVenta;
    private JButton btn_calcularVenta;
    private JLabel lbl_valorPagado;
    private JTextField textF_valorPagado;
    private JButton bttn_registrar;
    private JButton bttn_volver;

    public RegistroOperador() {
        bttn_volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    @Override
    public JPanel getPanel() {
        return this.Operadores_BG;
    }
}

package Visuales;


import Entidades.Negocio;
import Entidades.Operador;
import Utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

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
    private JTextField textF_cantidad;
    private JButton bttn_volver;

    public RegistroOperador(Negocio local) {
        textF_cantidad.addFocusListener(new InicializacionCamposTxtNumericos(textF_cantidad));
        textF_valorPagado.addFocusListener(new InicializacionCamposTxtNumericos(textF_valorPagado));
        textF_cantidad.addKeyListener(new KeyListenerParaInt(textF_cantidad));
        textF_valorPagado.addKeyListener(new KeyListenerParaInt(textF_valorPagado));

        ArrayList<Operador> listaOperadores = local.getOperadores();
        HashSet<Operador> setOperadores = new HashSet<>(listaOperadores);
        HashSet<String> tipoOperadores = new HashSet<>();

        for(Operador operador : setOperadores) {
            tipoOperadores.add(operador.getNombre().toString());
        }

        for(String nombreOperadores : tipoOperadores) {
            cBox_Operador.addItem(nombreOperadores);
        }

        for(Operador.TipoServicioOperador tipoServicio : Operador.TipoServicioOperador.values()){
            cBox_Servicio.addItem(tipoServicio.toString());
        }

        btn_calcularVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoOperador = cBox_Operador.getSelectedItem().toString();
                String tipoServicio = cBox_Servicio.getSelectedItem().toString();
                int cantidad = Integer.parseInt(textF_cantidad.getText().trim());
                if(cantidad>0){
                    double valorVenta = local.calcularValorVenta(tipoOperador, tipoServicio, cantidad);
                    textF_valorVenta.setText(String.valueOf(valorVenta));
                }else{
                    JOptionPane.showMessageDialog(null,"Ingrese una Cantidad Correcta");
                }
            }
        });
        cBox_Operador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String opcionOperador = cBox_Operador.getSelectedItem().toString();
                System.out.println("Escogiste el Operador " + opcionOperador);
            }
        });
        bttn_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidad = Integer.parseInt(textF_cantidad.getText().trim());
                double valorPagado = Double.parseDouble(textF_valorPagado.getText().trim());
                if(cantidad>0 &&
                        (valorPagado == Double.parseDouble(textF_valorVenta.getText().trim()))){
                    int num = 0;    // Supongo el "index" porque el metodo recibe un entero
                    if(local.registrarVentaOperador(num, cantidad, valorPagado)) {
                        JOptionPane.showMessageDialog(null, "Venta Operador registrado correctamente");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Registro Incorrecto");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Ingrese una Cantidad Correcta");
                }
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return this.Operadores_BG;
    }
}

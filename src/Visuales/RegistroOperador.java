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
                int idOperador;
                String tipoOperador = cBox_Operador.getSelectedItem().toString();
                String tipoServicio = cBox_Servicio.getSelectedItem().toString();
                idOperador = local.searchIndex(tipoOperador, tipoServicio);
                int cantidad = Integer.parseInt(textF_cantidad.getText().trim());
                if(cantidad>0){
                    double valorVenta = local.calcularValorVenta(idOperador, cantidad);
                    textF_valorVenta.setText(String.valueOf(valorVenta));
                }else{
                    JOptionPane.showMessageDialog(null,"Ingrese una Cantidad Correcta");
                }
            }
        });

        bttn_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idOperador, cantidad;
                double valorPagado, valorVenta;
                String tipoOperador = cBox_Operador.getSelectedItem().toString();
                String tipoServicio = cBox_Servicio.getSelectedItem().toString();
                idOperador = local.searchIndex(tipoOperador, tipoServicio);
                cantidad = Integer.parseInt(textF_cantidad.getText().trim());
                valorVenta = local.calcularValorVenta(idOperador, cantidad);
                valorPagado = Double.parseDouble(textF_valorPagado.getText().trim());
                boolean bandera = (valorPagado == valorVenta);
                if(cantidad>0 && bandera){
                    if(local.registrarVentaOperador(idOperador, cantidad, valorPagado)) {
                        JOptionPane.showMessageDialog(null, "Venta Operador registrado correctamente");
                        textF_cantidad.setText("0");
                        textF_valorPagado.setText("0");
                        textF_valorVenta.setText("");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Registro Incorrecto");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Ingrese una Cantidad Correcta de Pago o Cambie la Cantidad");
                }
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return this.Operadores_BG;
    }
}

package Visuales;

import Entidades.Negocio;
import Utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class RegistroTinta implements AccessPanel {
    private JPanel Tinta_BG;
    private JLabel lbl_cantidad;
    private JLabel lbl_TintaTitulo;
    private JLabel lbl_valorAPagar;
    private JTextField textF_cantidad;
    private JLabel lbl_cian;
    private JTextField txtReadOnly_cian;
    private JTextField txtReadOnly_amarillo;
    private JTextField txtReadOnly_magenta;
    private JTextField txtReadOnly_negro;
    private JLabel lbl_amarillo;
    private JLabel lbl_magenta;
    private JLabel lbl_negro;
    private JButton bttn_registrar;
    private JButton bttn_volver;
    private JRadioButton radBttn_color;
    private JRadioButton radBttn_BN;
    private JTextField textF_valorAPagar;
    private JLabel lbl_nivelTinta;
    private JTextField txtReadOnly_venta;
    private JButton bttn_venta;
    private String tipoImpresion = "";
    private boolean esColor = false;
    private int cantidad = 0;
    private int valorPagado = 0;
    private int costoUnidad = 0;
    private int valorVenta = 0;

    private final HashMap<JLabel, JTextField> mapeoTintas = new HashMap<>(){{
        put(lbl_cian, txtReadOnly_cian);
        put(lbl_amarillo, txtReadOnly_amarillo);
        put(lbl_magenta, txtReadOnly_magenta);
        put(lbl_negro, txtReadOnly_negro);
    }};

    public RegistroTinta(ContenedorSubMenuImp contenedor, Negocio local) {

        textF_cantidad.addFocusListener(new InicializacionCamposTxtNumericos(textF_cantidad));
        textF_cantidad.addKeyListener(new KeyListenerParaInt(textF_cantidad));
        textF_valorAPagar.addKeyListener(new KeyListenerParaDouble(textF_valorAPagar));

        radBttn_color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radBttn_color.setSelected(true);
                radBttn_BN.setSelected(false);
                mostrarNivelesTinta();
            }
        });

        radBttn_BN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radBttn_color.setSelected(false);
                radBttn_BN.setSelected(true);
                mostrarNivelesTinta();
            }
        });

        bttn_volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contenedor.cambiarVisibilidadContenido(ContenedorSubMenuImp.OpcionesMenuImpresora.SUB_MENU_IMPR);
            }
        });

        bttn_venta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((radBttn_color.isSelected() || radBttn_BN.isSelected())
                        && ManejoTintas.validarNivelTinta(ManejoTintas.tipoImpresoraEnBDTxt.TINTA)
                        && !(textF_cantidad.getText().isEmpty() || textF_cantidad.getText().equals("0")))
                {
                    esColor = radBttn_color.isSelected();
                    cantidad = Integer.parseInt(textF_cantidad.getText().trim());
                    if (esColor) {
                        tipoImpresion = "Color";
                        costoUnidad = 200;
                    } else {
                        tipoImpresion = "Blanco y Negro";
                        costoUnidad = 100;
                    }
                    valorVenta = costoUnidad * cantidad;
                    txtReadOnly_venta.setText(String.valueOf(valorVenta));
                }
            }
        });

        bttn_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((radBttn_color.isSelected() || radBttn_BN.isSelected())
                        && ManejoTintas.validarNivelTinta(ManejoTintas.tipoImpresoraEnBDTxt.TINTA)
                        && !(textF_cantidad.getText().isEmpty() || textF_cantidad.getText().equals("0"))
                        && !(textF_valorAPagar.getText().isEmpty() || textF_cantidad.getText().equals("0"))
                ){
                    //logica de registro tinta y visualizacion de total venta
                    esColor = radBttn_color.isSelected();
                    cantidad = Integer.parseInt(textF_cantidad.getText().trim());
                    valorPagado = Integer.parseInt(textF_valorAPagar.getText().trim());

                    if (valorPagado == valorVenta) {
                        JOptionPane.showMessageDialog(null,
                                "Registro exitoso:\n" +
                                        "Tipo de impresión: " + tipoImpresion + "\n" +
                                        "Cantidad: " + cantidad + "\n" +
                                        "Valor pagado: $" + valorPagado,
                                "Registro Exitoso",
                                JOptionPane.INFORMATION_MESSAGE);
                        textF_cantidad.setText("");
                        textF_valorAPagar.setText("");
                        radBttn_color.setSelected(false);
                        radBttn_BN.setSelected(false);
                        txtReadOnly_venta.setText("");
                        txtReadOnly_cian.setText("");
                        txtReadOnly_amarillo.setText("");
                        txtReadOnly_magenta.setText("");
                        txtReadOnly_negro.setText("");
                    }
                }else {
                    JOptionPane.showMessageDialog(null,
                            "Verifica que todos los campos esten completos",
                            "Error en el Registro",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void mostrarNivelesTinta() {
        String[][] nivelesTinta = ManejoTintas.verTinta(ManejoTintas.tipoImpresoraEnBDTxt.TINTA);
        for( JLabel lblTinta : mapeoTintas.keySet() ) {
            for (int i = 0; i < nivelesTinta.length; i++) {
                if(nivelesTinta[i][0].equals(lblTinta.getText().toLowerCase())) {
                    mapeoTintas.get(lblTinta).setText(nivelesTinta[i][1]);
                }
            }
        }
    }

    @Override
    public JPanel getPanel() {
        return this.Tinta_BG;
    }

}

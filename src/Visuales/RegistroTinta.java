package Visuales;

import Entidades.Negocio;
import Entidades.ServicioImpresora;
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

    private boolean esColor = false;
    private String tipoImpresion = "";
    private int cantidad = 0;
    private int valorPagado = 0;
    private int costoUnidad = 0;
    private int valorVenta = 0;
    private int indiceTintaseleccionado;

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
                reestablecerCampos();
                contenedor.cambiarVisibilidadContenido(ContenedorSubMenuImp.OpcionesMenuImpresora.SUB_MENU_IMPR);
            }
        });

        bttn_venta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //validar ingreso de datos
                if((radBttn_color.isSelected() || radBttn_BN.isSelected())
                        && !(textF_cantidad.getText().isEmpty() || textF_cantidad.getText().equals("0"))
                ){
                    //se separa condicional de tintas, ya que se necesita, primero validar la tinta y para eso
                    //es necesario que los campos de los radio button alguno este seleccionado y que la cantidad este
                    //establecida, a su vez para generar popups con mensajes diferentes
                    //se agrega lógica para validar cantidad de tinta dependiendo si es color o BN

                    //determinar tipo impresion y obtener costo
                    cantidad = Integer.parseInt(textF_cantidad.getText().trim());
                    esColor = radBttn_color.isSelected();
                    tipoImpresion = esColor ? "Fotocopia-Color" : "Fotocopia-BN";

                    //validar disponibilidad de tinta
                    if (ManejoTintas.validarNivelTinta(ManejoTintas.tipoImpresoraEnBDTxt.TINTA, cantidad, esColor)) {
                        //calcular el valor de la venta dependiendo tipo impresion y mostrar
                        for(ServicioImpresora servImp : local.getServicioImpresora()) {
                            if (servImp.getTipo().contains(tipoImpresion)) {
                                costoUnidad = (int) servImp.getValorParaVenta();
                                valorVenta = costoUnidad * cantidad;
                                txtReadOnly_venta.setText(String.valueOf(valorVenta));
                                indiceTintaseleccionado = local.getServicioImpresora().indexOf(servImp);
                                break;
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,
                                "LLama al técnico para recargar tintas o prueba con una menor cantidad de insumos",
                                "Tinta insuficiente",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Verifica que todos los campos esten completos",
                            "Faltan valores",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        bttn_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //validar ingreso de datos
                if ((radBttn_color.isSelected() || radBttn_BN.isSelected())
                        && !(textF_cantidad.getText().isEmpty() || textF_cantidad.getText().equals("0"))
                        && !(textF_valorAPagar.getText().isEmpty() || textF_valorAPagar.getText().equals("0"))
                        && !(txtReadOnly_venta.getText().isEmpty()) //validar que se haya calculado la venta
                ) {
                    //se separa condicional de tintas, ya que se necesita, primero validar la tinta y para eso
                    //es necesario que los campos de los radio button alguno este seleccionado y que la cantidad este
                    //establecida, a su vez para generar popups con mensajes diferentes

                    //validar disponibilidad de tinta dependiendo si es color o BN
                    if (ManejoTintas.validarNivelTinta(ManejoTintas.tipoImpresoraEnBDTxt.TINTA, cantidad, esColor)) {
                        valorPagado = Integer.parseInt(textF_valorAPagar.getText().trim());
                        boolean confirmacionPago = local.registrarVentaServImpresion(
                                indiceTintaseleccionado, cantidad, valorPagado);

                        //se recomienda cambiar logica para confirmar pago, revisar codigo del miniproyecto2
                        //todavia se encuentra en el main, esta comentado, ver si acaso la ventana plotter pero recordar
                        //que el registro de venta tanto para tinta como para laser es con valores enteros y no don double

                        if (confirmacionPago) {
                            JOptionPane.showMessageDialog(null,
                                    "Registro exitoso Fotocopia Tinta\n" +
                                            "Tipo de impresión: " + (esColor ? "Color" : "Blanco y Negro") + "\n" +
                                            "Cantidad: " + cantidad + "\n" +
                                            "Valor pagado: $" + valorPagado,
                                    "Venta registrada",
                                    JOptionPane.INFORMATION_MESSAGE);
                            ManejoTintas.mermarNivelTinta(ManejoTintas.tipoImpresoraEnBDTxt.TINTA, cantidad, esColor);
                            reestablecerCampos();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Verifica que el valor a pagar sea correcto",
                                    "Falla al registrar la venta",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,
                                "LLama al técnico para recargar tintas o prueba con una menor cantidad de insumos",
                                "Tinta insuficiente",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Verifica que todos los campos esten completos\n" +
                                    "Calcule el valor de la venta para asegurar el registro",
                            "Faltan valores",
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

    private void reestablecerCampos(){
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

    @Override
    public JPanel getPanel() {
        return this.Tinta_BG;
    }

}

package Visuales;

import Entidades.Negocio;
import Entidades.ServicioImpresora;
import Utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;

public class RegistroPlotter implements AccessPanel {
    private JPanel Plotter_BG;
    private JLabel lbl_plotterTitulo;
    private JButton bttn_volver;
    private JTextField textF_cantidad;
    private JRadioButton radBttn_plano;
    private JButton bttn_registrar;
    private JRadioButton radBttn_publicidad;
    private JLabel lbl_cantidad;
    private JTextField textF_ancho;
    private JTextField textF_alto;
    private JTextField txtReadOnly_valorcm2;
    private JLabel lbl_valorcm2;
    private JLabel lbl_alto;
    private JLabel lbl_ancho;
    private JLabel lbl_nivelTinta;
    private JLabel lbl_cian;
    private JLabel lbl_amarillo;
    private JTextField txtReadOnly_cian;
    private JTextField txtReadOnly_amarillo;
    private JLabel lbl_magenta;
    private JTextField txtReadOnly_magenta;
    private JLabel lbl_negro;
    private JTextField txtReadOnly_negro;
    private JLabel lbl_valorAPagar;
    private JTextField textF_valorAPagar;
    private JTextField txtReadOnly_total;
    private JLabel lbl_total;

    private final HashMap<JLabel, JTextField> mapeoTintas = new HashMap<>(){{
        put(lbl_cian, txtReadOnly_cian);
        put(lbl_amarillo, txtReadOnly_amarillo);
        put(lbl_magenta, txtReadOnly_magenta);
        put(lbl_negro, txtReadOnly_negro);
    }};

    private int indicePlotterseleccionado;
    private double cantidadInsumoAVender;


    public RegistroPlotter(ContenedorSubMenuImp contenedor, Negocio local) {

        textF_cantidad.addFocusListener(new InicializacionCamposTxtNumericos(textF_cantidad));
        textF_ancho.addFocusListener(new InicializacionCamposTxtNumericos(textF_ancho));
        textF_alto.addFocusListener(new InicializacionCamposTxtNumericos(textF_alto));
        textF_cantidad.addKeyListener(new KeyListenerParaInt(textF_cantidad));
        textF_ancho.addKeyListener(new KeyListenerParaDouble(textF_ancho));
        textF_alto.addKeyListener(new KeyListenerParaDouble(textF_alto));
        textF_valorAPagar.addKeyListener(new KeyListenerParaDouble(textF_valorAPagar));

        Plotter_BG.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(!(textF_cantidad.getText().isEmpty() || textF_cantidad.getText().equals("0"))
                        && !(textF_ancho.getText().isEmpty() || textF_ancho.getText().equals("0"))
                        && !(textF_alto.getText().isEmpty() || textF_alto.getText().equals("0"))
                        && !(txtReadOnly_valorcm2.getText().isEmpty())
                ){
                    cantidadInsumoAVender = Negocio.aDosDecimales(Integer.parseInt(textF_cantidad.getText())
                            * Double.parseDouble(textF_ancho.getText())
                            * Double.parseDouble(textF_alto.getText()));

                    System.out.println(cantidadInsumoAVender);

                    String total =String.format("%.2f", Negocio.aDosDecimales(
                                    cantidadInsumoAVender*Double.parseDouble(txtReadOnly_valorcm2.getText())));
                    System.out.println(total);
                    txtReadOnly_total.setText(total);

                }else if(cantidadInsumoAVender != 0.0){
                    cantidadInsumoAVender = 0.0;
                    txtReadOnly_total.setText("0");
                }


            }
        });

        radBttn_plano.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radBttn_publicidad.setSelected(false);
                radBttn_plano.setSelected(true);

                cambiarValorcm2(local, "Planos");
                mostrarNivelesTinta();
            }
        });

        radBttn_publicidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radBttn_publicidad.setSelected(true);
                radBttn_plano.setSelected(false);

                cambiarValorcm2(local, "Publicidad");
                mostrarNivelesTinta();
            }
        });


        bttn_volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                radBttn_publicidad.setSelected(false);
                radBttn_plano.setSelected(false);
                textF_alto.setText("0");
                textF_cantidad.setText("0");
                textF_ancho.setText("0");
                textF_valorAPagar.setText("");
                txtReadOnly_valorcm2.setText("");
                txtReadOnly_total.setText("");
                txtReadOnly_cian.setText("");
                txtReadOnly_amarillo.setText("");
                txtReadOnly_magenta.setText("");
                txtReadOnly_negro.setText("");

                contenedor
                        .cambiarVisibilidadContenido(ContenedorSubMenuImp
                                .OpcionesMenuImpresora
                                .SUB_MENU_IMPR);
            }
        });

        bttn_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((radBttn_plano.isSelected() || radBttn_publicidad.isSelected())
                        && ManejoTintas.validarNivelTinta(ManejoTintas.tipoImpresoraEnBDTxt.PLOTTER)
                        && !(textF_cantidad.getText().isEmpty() || textF_cantidad.getText().equals("0"))
                        && !(textF_ancho.getText().isEmpty() || textF_ancho.getText().equals("0"))
                        && !(textF_alto.getText().isEmpty() || textF_alto.getText().equals("0"))
                        && !(textF_valorAPagar.getText().isEmpty() || textF_valorAPagar.getText().equals("0"))
                ){
                    //////todo agregar logica de registro plotter y visualizacion de total venta
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Verifica que todos los campos esten completos",
                            "Faltan valores",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });


    }

    private void mostrarNivelesTinta() {
        String[][] nivelesTinta = ManejoTintas.verTinta(ManejoTintas.tipoImpresoraEnBDTxt.PLOTTER);
        for( JLabel lblTinta : mapeoTintas.keySet() ) {
            for (int i = 0; i < nivelesTinta.length; i++) {
                if(nivelesTinta[i][0].equals(lblTinta.getText().toLowerCase())) {
                    mapeoTintas.get(lblTinta).setText(nivelesTinta[i][1]);
                }
            }
        }


    }

    private void cambiarValorcm2(Negocio local, String tipoPlotter) {
        String valorcm2;

        for(ServicioImpresora servIpm : local.getServicioImpresora()){
            if(servIpm.getTipo().contains(tipoPlotter)){
                valorcm2 = String.format("%.2s", servIpm.getValorParaVenta());
                txtReadOnly_valorcm2.setText(valorcm2);
                indicePlotterseleccionado = local.getServicioImpresora().indexOf(servIpm);
                break;
            }
        }
    }

    @Override
    public JPanel getPanel() {
        return this.Plotter_BG;
    }
}

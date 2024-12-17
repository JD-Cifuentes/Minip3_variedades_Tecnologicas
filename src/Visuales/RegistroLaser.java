package Visuales;

import Entidades.Negocio;
import Utils.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class RegistroLaser implements AccessPanel {
    private JLabel lbl_cantidad;
    private JLabel lbl_LaserTitulo;
    private JLabel lbl_valorAPagar;
    private JRadioButton radBttn_color;
    private JRadioButton radBttn_BN;
    private JLabel lbl_nivelTinta;
    private JLabel lbl_cian;
    private JLabel lbl_amarillo;
    private JLabel lbl_magenta;
    private JLabel lbl_negro;
    private JLabel lbl_total;
    private JTextField txtReadOnly_cian;
    private JTextField txtReadOnly_amarillo;
    private JTextField txtReadOnly_magenta;
    private JTextField txtReadOnly_negro;
    private JTextField txtReadOnly_total;
    private JButton bttn_registrar;
    private JButton bttn_volver;
    private JTextField textF_cantidad;
    private JTextField textF_valorAPagar;
    private JPanel Laser_BG;
    private JTextField txtReadOnly_venta;
    private JButton bttn_venta;

    private final HashMap<JLabel, JTextField> mapeoTintas = new HashMap<>(){{
        put(lbl_cian, txtReadOnly_cian);
        put(lbl_amarillo, txtReadOnly_amarillo);
        put(lbl_magenta, txtReadOnly_magenta);
        put(lbl_negro, txtReadOnly_negro);
    }};

    public RegistroLaser(ContenedorSubMenuImp contenedor, Negocio local) {

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
                contenedor
                        .cambiarVisibilidadContenido(ContenedorSubMenuImp.OpcionesMenuImpresora.SUB_MENU_IMPR);
            }
        });

        bttn_registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

    }

    private void mostrarNivelesTinta() {
        String[][] nivelesTinta = ManejoTintas.verTinta(ManejoTintas.tipoImpresoraEnBDTxt.LASER);
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
        return this.Laser_BG;
    }


}

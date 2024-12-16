package Visuales;

import Entidades.Negocio;
import Entidades.ServicioImpresora;
import Utils.AccessPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    public RegistroPlotter(ContenedorSubMenuImp contenedor, Negocio local) {



        radBttn_plano.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radBttn_publicidad.setSelected(false);
                radBttn_plano.setSelected(true);

                cambiarValorcm2(local, "Planos");


            }
        });

        radBttn_publicidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radBttn_publicidad.setSelected(true);
                radBttn_plano.setSelected(false);

                cambiarValorcm2(local, "Publicidad");
            }
        });


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

    private void cambiarValorcm2(Negocio local, String tipoPlotter) {
        String valorcm2;

        for(ServicioImpresora servIpm : local.getServicioImpresora()){
            if(servIpm.getTipo().contains(tipoPlotter)){
                valorcm2 = String.format("%.2s", servIpm.getValorParaVenta());
                txtReadOnly_valorcm2.setText(valorcm2);
                break;
            }
        }
    }

    @Override
    public JPanel getPanel() {
        return this.Plotter_BG;
    }
}

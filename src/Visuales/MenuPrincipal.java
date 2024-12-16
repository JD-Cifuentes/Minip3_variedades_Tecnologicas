package Visuales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private JPanel mainBG;
    private JPanel Jpanel_BannerMenuPpl;
    private JLabel lbl_nombreNegocio1;
    private JLabel lbl_nombreNegocio2;
    private JButton bttn_Impresion;
    private JLabel lbl_tipoSelec;
    private JButton bttn_operador;
    private JButton bttn_cierreDia;
    private JButton bttn_salir;
    private JPanel Jpanel_contenido;


    private final ContenedorSubMenuImp subImpre = new ContenedorSubMenuImp();


    public MenuPrincipal() {

        bttn_Impresion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarContenido(subImpre.getContenedorSubMenuImpre());
            }
        });


        bttn_salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "¿Deseas cerrar la aplicación?","Cerrar aplicación",JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }

    public void cambiarContenido(JPanel panel){
        try{
            panel.setSize(435,400);
            panel.setMaximumSize(new Dimension(435,400));
            panel.setLocation(0,0);
            this.Jpanel_contenido.removeAll();
            this.Jpanel_contenido.add(panel,BorderLayout.CENTER);
            this.Jpanel_contenido.revalidate();
            this.Jpanel_contenido.repaint();
        }catch (NullPointerException e){
            System.out.println(e + " en menu principal - cambiarContenido");
        }

    }

    public JPanel getBackgoundMain() {
        return this.mainBG;
    }
}

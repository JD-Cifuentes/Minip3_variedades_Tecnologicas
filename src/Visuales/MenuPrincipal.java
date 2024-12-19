package Visuales;

import Entidades.Negocio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    //componentes del menu
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

    //componentes dependientes
    private final ContenedorSubMenuImp subImpre; //submenu impresiones
    private final CierreDia cierreDia; //submenu cierre

    //constructor que inicializa los submenus con objeto Negocio
    public MenuPrincipal(Negocio local) {
        subImpre = new ContenedorSubMenuImp(local);
        cierreDia = new CierreDia(local);

        bttn_Impresion.addActionListener(new ActionListener() {
            @Override
            //muestra el submenu de impresiones
            public void actionPerformed(ActionEvent e) {
                cambiarContenido(subImpre.getContenedorSubMenuImpre());
            }
        });

        bttn_cierreDia.addActionListener(new ActionListener() {
            @Override
            //muestra el submenu cierre
            public void actionPerformed(ActionEvent e) {
                cambiarContenido(cierreDia.getPanel());
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

    //cambia el contenido del panel principal
    public void cambiarContenido(JPanel panel){
        try{
            panel.setSize(435,400);
            panel.setMaximumSize(new Dimension(435,400));
            panel.setLocation(0,0);
            panel.setFocusable(false);
            this.Jpanel_contenido.removeAll();
            this.Jpanel_contenido.add(panel,BorderLayout.CENTER);
            this.Jpanel_contenido.revalidate();
            this.Jpanel_contenido.repaint();
            panel.setFocusable(true);
        }catch (NullPointerException e){
            System.out.println(e + " en menu principal - cambiarContenido");
        }

    }

    //retorna el fondo principal
    public JPanel getBackgoundMain() {
        return this.mainBG;
    }
}

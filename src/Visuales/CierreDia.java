package Visuales;

import Entidades.Negocio;
import Utils.AccessPanel;

import javax.swing.*;

public class CierreDia implements AccessPanel {
    private JPanel CierreDia_BG;
    private JButton bttn_generarCierreDia;
    private JLabel lbl_totalDineroRecogido;
    private JLabel lbl_impresoraConMasConsumo;
    private JLabel lbl_totalGanacia;
    private JLabel lbl_gastosDeProduccion;
    private JLabel lbl_servicioConMayorGanacia;
    private JLabel lbl_impresoraConMenorGanacia;
    private JTextField txtReadOnly_totalDineroRecogido;
    private JTextField txtReadOnly_gananciaObtenida;
    private JTextField txtReadOnly_gastosProduccion;
    private JTextField txtReadOnly_servicioConMayorGanancia;
    private JTextField txtReadOnly_impresoraConmenorGanacia;
    private JTextField txtReadOnly_impresoraMayorConsumoTinta;

    public CierreDia(Negocio local) {
    }

    @Override
    public JPanel getPanel() {
        return CierreDia_BG;
    }
}

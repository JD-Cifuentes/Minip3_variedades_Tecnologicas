package Visuales;

import Entidades.Negocio;
import Utils.AccessPanel;

import javax.swing.*;
import java.io.Serializable;

public class CierreDia implements AccessPanel {
    private JPanel CierreDia_BG;

    public CierreDia(Negocio local) {
    }

    @Override
    public JPanel getPanel() {
        return CierreDia_BG;
    }
}

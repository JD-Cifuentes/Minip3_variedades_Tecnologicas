package Visuales;

import Utils.AccessPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PanelBienvenida implements AccessPanel {
    private JLabel welcomeLabel;
    private JLabel welcomeGif;
    private JPanel PanelBienvenida_BG;

    @Override
    public JPanel getPanel() {
        return PanelBienvenida_BG;
    }
}

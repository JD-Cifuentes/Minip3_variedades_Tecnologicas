package Utils;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InicializacionCamposTxtNumericos implements FocusListener {
    private JTextField campoEntrada;

    public InicializacionCamposTxtNumericos(JTextField campoEntrada) {
        this.campoEntrada = campoEntrada;
        this.campoEntrada.setText("0");
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (campoEntrada.getText().equals("0")) {
            campoEntrada.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (campoEntrada.getText().isEmpty()) {
            campoEntrada.setText("0");
        }
    }
}

package Utils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerParaInt implements KeyListener {
    private JTextField textField;

    public KeyListenerParaInt(JTextField textField) {
        this.textField = textField;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!validarCaracter(c)) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean validarCaracter(char c) {
        if (Character.isDigit(c)) {
            return true;
        }
        return false;
    }
}

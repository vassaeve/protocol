package com.altynkez.rgis.vassaeve;

/**
 *
 * @author vassaeve
 */
public class App {
    
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });
    }

}

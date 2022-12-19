package data;

import data.libs.Map;

import javax.swing.*;

public class Default {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Map("level1").setVisible(true);
            }
        });
    }
}

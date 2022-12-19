package data;
import data.libs.Map;

import javax.swing.*;

import mobac.IMoBacPlugin;
import mobac.MoBacPlugin;

public class Default extends MoBacPlugin implements IMoBacPlugin {

    public void Run()
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Map("level1").setVisible(true);
            }
        });
    }
}

package data;
import data.libs.Map;

import javax.swing.*;

import data.libs.PlayerController;
import mobac.IMoBacPlugin;
import mobac.MoBacPlugin;

public class Default extends MoBacPlugin implements IMoBacPlugin {

    public void Run()
    {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Map map = new Map("level1");
                map.setVisible(true);
                PlayerController pc = new PlayerController(map.getPlayer(), map);
                map.addPerFrameAction(()->pc.tick());
            }
        });
    }
}

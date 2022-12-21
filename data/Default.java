package data;
import data.libs.False3D;

import javax.swing.*;

import data.libs.Map;
import data.libs.PlayerController;
import data.libs.parents.Render;
import mobac.IMoBacPlugin;
import mobac.MoBacPlugin;

public class Default extends MoBacPlugin implements IMoBacPlugin {

    public void Run()
    {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Render r = new Map("level1");
                Render r = new False3D("level1");
                r.setVisible(true);
                PlayerController pc = new PlayerController(r.getPlayer(), r);
                r.addPerFrameAction(()->pc.tick());
            }
        });
    }
}

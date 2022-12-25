package data;
import data.libs.*;

import javax.swing.*;

import data.libs.abstract_classes.Render;
import mobac.IMoBacPlugin;
import mobac.MoBacPlugin;

public class Default3D extends MoBacPlugin implements IMoBacPlugin {

    public void Run()
    {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Render r = new False3D("level1");
                r.setVisible(true);
                PlayerController pc = new PlayerController(r);
                r.addPerFrameAction(()->pc.useStandardEffect());
                r.addPerFrameAction(()-> CollisionResolution2D.PlayerLine(r));
            }
        });
    }
}

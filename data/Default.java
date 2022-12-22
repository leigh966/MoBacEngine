package data;
import data.libs.*;

import javax.swing.*;

import data.libs.parents.Render;
import mobac.IMoBacPlugin;
import mobac.MoBacPlugin;

import java.util.List;

public class Default extends MoBacPlugin implements IMoBacPlugin {

    public void Run()
    {

        SwingUtilities.invokeLater(new Runnable() {
            public void wallCollision(Render r)
            {
                Player player = r.getPlayer();
                List<Float[]> lines = r.getLines();
                final float PLAYER_RADIUS = 2f;
                float[] playerPos = player.getPosition();
                for(Float[] line : lines)
                {
                    Collision2D col = Collision2D.CircleLine(playerPos[0], playerPos[1], PLAYER_RADIUS,
                            line[0], line[1], line[0]+line[2], line[0]+line[3]);
                    if(col.getCollisionOccurred())
                    {
                        System.out.println("Collision Occurred!");
                        float[] intersection = col.getIntersection();
                        float[] toPlayer = new float[] {playerPos[0]-intersection[0], playerPos[1]-intersection[1]};
                        //player.transform(toPlayer[0], toPlayer[1]);
                        r.drawOval((int)intersection[0], (int)intersection[1], 2, 2);
                    }
                }
            }

            @Override
            public void run() {
                Render r = new Map("level1");
                //Render r = new False3D("level1");
                r.setVisible(true);
                PlayerController pc = new PlayerController(r.getPlayer(), r);
                r.addPerFrameAction(()->pc.tick());
                r.addPerFrameAction(()->wallCollision(r));
            }
        });
    }
}

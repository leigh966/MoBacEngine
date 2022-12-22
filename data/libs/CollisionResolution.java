package data.libs;

import data.libs.abstract_classes.Render;

import java.util.List;

public class CollisionResolution {
    public static void PlayerLine(Render r)
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
                float[] intersection = col.getIntersection();
                float[] toPlayer = new float[] {playerPos[0]-intersection[0], playerPos[1]-intersection[1]};
                player.transform(toPlayer[0], toPlayer[1]);
            }
        }
    }
}
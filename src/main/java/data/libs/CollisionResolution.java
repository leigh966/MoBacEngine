package data.libs;

import data.libs.abstract_classes.Render;

import java.util.List;

public class CollisionResolution {
    public static float playerRadius = 1f;
    public static void PlayerLine(Render r)
    {
        Player player = r.getPlayer();
        List<Float[]> lines = r.getLines();
        float[] playerPos = player.getPosition();
        for(Float[] line : lines)
        {
            Collision2D col = Collision2D.CircleLine(playerPos[0], playerPos[1], playerRadius,
                    line[0], line[1], line[0]+line[2], line[0]+line[3]);
            if(col.getCollisionOccurred())
            {
                float[] intersection = col.getIntersection();
                float[] toPlayer = new float[] {playerPos[0]-intersection[0], playerPos[1]-intersection[1]};
                float currentDistance = MoMath.pythagorasTheorem(toPlayer[0], toPlayer[1]);
                float[] normalised = MoMath.normalize(toPlayer[0], toPlayer[1], currentDistance);
                float deltaDistance = playerRadius -currentDistance;
                player.transform(normalised[0]*deltaDistance, normalised[1]*deltaDistance);
            }
        }
    }
}

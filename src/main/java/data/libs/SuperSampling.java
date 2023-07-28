package data.libs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.Buffer;

public class SuperSampling implements IPostProcessing{

    private int factor = 1;
    public void useEffect(BufferedImage bi)
    {
        int newWidth = bi.getWidth()/factor;
        int newHeight = bi.getHeight()/factor;
        Image scaledImage = bi.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_AREA_AVERAGING);
        System.out.println(newHeight);
        bi.getGraphics().drawImage(scaledImage, 0, 0, newWidth, newHeight, null);
    }

    public void preRender(False3D render)
    {
        factor = Integer.parseInt(render.getConfigValue("super-sampling"));
        render.setBufferResolution(render.getWidth()*factor, render.getHeight()*factor);
    }
}

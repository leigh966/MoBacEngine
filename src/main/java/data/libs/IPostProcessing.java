package data.libs;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public interface IPostProcessing {
    void useEffect(BufferedImage bi);
    void preRender(False3D render);
}

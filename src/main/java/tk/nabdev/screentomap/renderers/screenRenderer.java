package tk.nabdev.screentomap.renderers;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;

public class screenRenderer extends MapRenderer {
    private final int section;
    private final int x;
    private final int y;
    private final screenRenderer imageKey;
    public BufferedImage image;
    public screenRenderer(int _section, int _x, int _y){
        section = _section;
        imageKey = this;
        x = _x;
        y = _y;
    }
    public screenRenderer(int _section, screenRenderer _imageKey, int _x, int _y){
        section = _section;
        imageKey = _imageKey;
        x = _x;
        y = _y;
    }
    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        try {
            if(section == 0) {
                Robot r = new Robot();
                Toolkit t = Toolkit.getDefaultToolkit();
                Dimension d = t.getScreenSize();
                BufferedImage i = r.createScreenCapture(new Rectangle(0,0,d.width,d.height));
                image = resize(i, 448, 252);
            }
            canvas.drawImage(x, y, imageKey.image);
        } catch (AWTException e){
            e.printStackTrace();
        }
    }
    // https://stackoverflow.com/questions/9417356/bufferedimage-resize
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

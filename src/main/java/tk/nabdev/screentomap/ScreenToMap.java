package tk.nabdev.screentomap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.nabdev.screentomap.commands.mapCommand;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class ScreenToMap extends JavaPlugin {
    public BufferedImage image;
    @Override
    public void onEnable() {
        getCommand("map").setExecutor(new mapCommand(this));
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            Robot r = null;
            try {
                r = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            Toolkit t = Toolkit.getDefaultToolkit();
            Dimension d = t.getScreenSize();
            BufferedImage i = r.createScreenCapture(new Rectangle(0,0,d.width,d.height));
            image = resize(i, 448, 252);
        }, 1, 1);
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
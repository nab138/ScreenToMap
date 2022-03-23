package tk.nabdev.screentomap.renderers;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import tk.nabdev.screentomap.ScreenToMap;

import java.awt.image.BufferedImage;

public class screenRenderer extends MapRenderer {
    private final int x;
    private final int y;
    private final ScreenToMap plugin;
    public BufferedImage image;
    public screenRenderer(int _x, int _y, ScreenToMap _plugin){
        plugin = _plugin;
        x = _x;
        y = _y;
    }
    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        canvas.drawImage(0,0, plugin.image.getSubimage(x, y, (x == 384) ? 64 : 128, (y == 128) ? 124 : 128));
    }
}

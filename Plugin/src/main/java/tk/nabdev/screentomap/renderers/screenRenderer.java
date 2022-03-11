package tk.nabdev.screentomap.renderers;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;

public class screenRenderer extends MapRenderer {

    public BufferedImage image;

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        if(image != null){
            canvas.drawImage(0,0, image);
        } else {
            canvas.setPixel(0,0, MapPalette.TRANSPARENT);
        }
    }
}

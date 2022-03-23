package tk.nabdev.screentomap.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import tk.nabdev.screentomap.renderers.screenRenderer;

public class mapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            screenRenderer key = createMap(player, 0, null, 0, 0);
            createMap(player, 1, key, -128, 0);
            createMap(player, 2, key, -256, 0);
            createMap(player, 3, key, -384, 0);
            createMap(player, 4, key, 0, -128);
            createMap(player, 5, key, -128, -128);
            createMap(player, 6, key, -256, -128);
            createMap(player, 7, key, -384, -128);
        }
        return true;
    }
    private screenRenderer createMap(Player player, int i, screenRenderer key, int x, int y){
        if(key == null) {
            screenRenderer renderer = new screenRenderer(i, x, y);
            manageMap(player, renderer);
            return renderer;
        } else {
            screenRenderer renderer = new screenRenderer(i, key, x, y);
            manageMap(player, renderer);
            return null;
        }
    }
    private void manageMap(Player player, screenRenderer renderer){
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().clear();
        mapView.addRenderer(renderer);
        mapMeta.setMapView(mapView);
        mapItem.setItemMeta(mapMeta);
        player.getInventory().addItem(mapItem);
    }
}

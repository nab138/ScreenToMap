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
import tk.nabdev.screentomap.ScreenToMap;
import tk.nabdev.screentomap.renderers.screenRenderer;

public class mapCommand implements CommandExecutor {
    private final ScreenToMap plugin;

    public mapCommand(ScreenToMap _plugin){
        plugin = _plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            createMap(player, 0, 0);
            createMap(player, 128, 0);
            createMap(player,256, 0);
            createMap(player, 384, 0);
            createMap(player,0, 128);
            createMap(player, 128, 128);
            createMap(player, 256, 128);
            createMap(player, 384, 128);
        }
        return true;
    }
    private screenRenderer createMap(Player player,int x, int y){
        screenRenderer renderer = new screenRenderer(x, y, plugin);
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().clear();
        mapView.addRenderer(renderer);
        mapMeta.setMapView(mapView);
        mapItem.setItemMeta(mapMeta);
        player.getInventory().addItem(mapItem);
        return renderer;
    }
}

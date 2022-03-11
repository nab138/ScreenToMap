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

import java.util.Arrays;


public class mapCommand implements CommandExecutor {
    public screenRenderer[] renderers = new screenRenderer[8];
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Arrays.fill(renderers, null);
            createMap(player, 0);
            createMap(player, 1);
            createMap(player, 2);
            createMap(player, 3);
            createMap(player, 4);
            createMap(player, 5);
        }
        return true;
    }
    private void createMap(Player player, Integer i){
        screenRenderer renderer = new screenRenderer();
        renderers[i] = renderer;
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().clear();
        mapView.addRenderer(renderer);
        mapMeta.setMapView(mapView);
        mapItem.setItemMeta(mapMeta);
        player.getInventory().addItem(mapItem);
        return;
    }
}

package tk.nabdev.screentomap;

import org.bukkit.plugin.java.JavaPlugin;
import tk.nabdev.screentomap.commands.mapCommand;

public final class ScreenToMap extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("map").setExecutor(new mapCommand());
    }
}
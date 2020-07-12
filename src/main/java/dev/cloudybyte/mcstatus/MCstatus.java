package dev.cloudybyte.mcstatus;


import org.bukkit.plugin.java.JavaPlugin;

public class MCstatus extends JavaPlugin {

    public static void main(String[] args){}

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        InitAPI initAPI = new InitAPI();
        initAPI.initAPI(getServer());
    }
}

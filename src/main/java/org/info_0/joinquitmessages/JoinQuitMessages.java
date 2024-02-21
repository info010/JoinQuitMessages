package org.info_0.joinquitmessages;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.info_0.joinquitmessages.comands.MainCommand;
import org.info_0.joinquitmessages.events.JoinListener;
import org.info_0.joinquitmessages.events.QuitListener;

public final class JoinQuitMessages extends JavaPlugin {

    private static Permission perms = null;

    private static JoinQuitMessages instance;

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    @Override
    public void onEnable() {
        instance = this;
        setupPermissions();
        saveDefaultConfig();
        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new JoinListener(),this);
        getServer().getPluginManager().registerEvents(new QuitListener(),this);
    }

    private void registerCommands(){
        getCommand("jqm").setExecutor(new MainCommand());
    }

    public static JoinQuitMessages getInstance(){
        return instance;
    }

    public static Permission getPermissions() {
        return perms;
    }
}

package org.info_0.joinquitmessages.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.info_0.joinquitmessages.JoinQuitMessages;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class MessagesUtil {

    private static Map<String,String> messages = new HashMap<>();
    private final static File messageFile = new File(JoinQuitMessages.getInstance().getDataFolder(),"messages.yml");

    public static String getMessage(String messName){
        return messages.get(messName);
    }

    public static void loadMessages(){
        if(messageFile.exists()){
            try {
                InputStream in = JoinQuitMessages.getInstance().getResource("messages.yml");
                Files.copy(in, messageFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(messageFile);
        for(String key: yamlConfiguration.getKeys(false)){
            for(String messName: yamlConfiguration.getConfigurationSection(key).getKeys(false)){
                String message = ChatColor.translateAlternateColorCodes('&',yamlConfiguration.getString(key+'.'+messName));
                messages.put(messName,message);
            }
        }
        Bukkit.getLogger().severe(messageFile.getName() + " loaded!");
    }

    public static String refactorMessage(String string, Player player){
        if(string.contains("&")) string = string.replace("&","§");
        if(string.contains("%player%")) string = string.replaceAll("%player%",player.getName());
        return string;
    }

    public static void debugMessage(String s){
        if(!JoinQuitMessages.getInstance().getConfig().getBoolean("debug-logger")) return;
        Bukkit.getLogger().severe(s);
    }



}

package org.info_0.joinquitmessages.comands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.info_0.joinquitmessages.JoinQuitMessages;
import org.info_0.joinquitmessages.util.MessagesUtil;

import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final Permission permApi = JoinQuitMessages.getPermissions();

    private final String testMessage = JoinQuitMessages.getInstance().getConfig().getString("test-message");

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length < 1){
            commandSender.sendMessage("/jqm toggle");
            return true;
        }
        if (strings[0].equalsIgnoreCase("reload")) {
            if (commandSender.isOp()) reload();
            return true;
        }
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(strings[0].equalsIgnoreCase("toggle")){
            String permission = "jqm.prevent-messages";
            if(player.hasPermission(permission)) {
                player.sendMessage("§6§l[JQM]> §eGiriş-Çıkış mesajları açıldı.");
                permApi.playerRemove(player, permission);
            } else {
                player.sendMessage("§6§l[JQM]> §eGiriş-Çıkış mesajları kapatıldı.");
                permApi.playerAdd(player, permission);
            }
            return true;
        } else if (strings[0].equalsIgnoreCase("test")) {
            player.isOp();
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(MessagesUtil.refactorMessage(testMessage,player)));
            return true;
        } else {
            player.sendMessage("§6§l[JQM]> §eBu komutun kullanımı '/jqm toggle' şeklindedir.");
            return true;
        }
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 1) return null;
        if(commandSender.isOp()){
            return Arrays.asList("toggle","reload","test");
        }else{
            return Arrays.asList("toggle");
        }
    }

    private void reload(){
        Plugin plugin = JoinQuitMessages.getInstance();
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getPluginManager().enablePlugin(plugin);
    }
}

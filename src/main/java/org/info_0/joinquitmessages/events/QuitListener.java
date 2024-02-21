package org.info_0.joinquitmessages.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.info_0.joinquitmessages.JoinQuitMessages;
import org.info_0.joinquitmessages.util.MessagesUtil;

public class QuitListener implements Listener {

    private final String quitMessage = JoinQuitMessages.getInstance().getConfig().getString("quit-message");

    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.hasPermission("jqm.prevent-messages")) continue;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(MessagesUtil.refactorMessage(quitMessage,event.getPlayer())));
        }
    }
}

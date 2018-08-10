package com.jeder.onlineTime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    Main plugin ;

    public EventListener ( Main plugin ) {
        this.plugin = plugin ;
    }

    FileConfiguration fc = plugin.getConfig() ;

    @EventHandler
    public void onPlayerJoin ( PlayerJoinEvent e ) {
        //當玩家進入
    }
}

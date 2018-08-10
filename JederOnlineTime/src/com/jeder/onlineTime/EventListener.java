package com.jeder.onlineTime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EventListener implements Listener {
    Main plugin ;
    public Map< String , Long > PlayerLoginTime = new HashMap<>() ;

    public EventListener ( Main plugin ) {
        this.plugin = plugin ;
    }

    FileConfiguration fc = plugin.getConfig() ;

    @EventHandler
    public void onPlayerJoin ( PlayerJoinEvent e ) {
        //當玩家進入
        Player player = e.getPlayer() ;
        String playerName = player.getName() ;
        long rightNow = Calendar.getInstance().getTimeInMillis() ;
        PlayerLoginTime.put( playerName , rightNow ) ;
        if ( !fc.contains( "Players." + playerName ) ) {
            //沒有該玩家的紀錄，創建一個
            fc.set( "Players." + playerName , 0L );
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onPlayerLeave ( PlayerQuitEvent e ){
        Player player = e.getPlayer() ;
        String playerName = player.getName() ;
        if ( !PlayerLoginTime.containsKey( playerName ) ) {
            plugin.getLogger().info( "嚴重錯誤！找不到 " + playerName + " 的Hash！" );
        }
        else {
            long playerLeaveRightNow = Calendar.getInstance().getTimeInMillis() ;
            long duration = ( playerLeaveRightNow - PlayerLoginTime.get( playerName ) ) + fc.getLong( "Players." + playerName ) ; //計算出時間差
            fc.set( "Players." + playerName , duration);
            plugin.saveConfig(); //保存
        }
    }

    //@EventHandler
    //public void onPlayerKick ( PlayerKickEvent e ) {
    //    Player player = e.getPlayer() ;
    //    String playerName = player.getName() ;
    //    if ( !PlayerLoginTime.containsKey( playerName ) ) {
    //        plugin.getLogger().info( "嚴重錯誤！找不到 " + playerName + " 的Hash！" );
    //    }
    //    else {
    //        long playerLeaveRightNow = Calendar.getInstance().getTimeInMillis() ;
    //        long duration = ( playerLeaveRightNow - PlayerLoginTime.get( playerName ) ) + fc.getLong( "Players." + playerName ) ; //計算出時間差
    //        fc.set( "Players." + playerName , duration);
    //        plugin.saveConfig(); //保存
    //    }
    //}
}

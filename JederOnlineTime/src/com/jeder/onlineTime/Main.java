package com.jeder.onlineTime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Main extends JavaPlugin implements Listener {
    //繼承原版庫
    public Map< String , Long > PlayerLoginTime = new HashMap<>() ;
    FileConfiguration fc = getConfig() ;
    @Override
    public void onEnable() {
        getLogger().info( "本插件已經成功載入" );
        getLogger().info( "玩家的遊戲時間僅會在玩家退出遊戲時保存，請注意。" );


        //寫入默認設定檔
        fc.addDefault( "enabled" , true );

        this.getConfig().options().copyDefaults( true ) ;
        saveConfig(); //存儲

        //設定指令偵聽器
        this.getCommand( "jederOnlineTime" ).setExecutor( new CommandListener( this ) );
        getServer().getPluginManager().registerEvents( this , this );
    }

    @Override
    public void onDisable(){
        getLogger().info( "插件正在被停用，即將保存玩家遊戲時間紀錄" );
        if ( PlayerLoginTime.isEmpty() ) {
            //沒有線上玩家，直接跳出
            getLogger().info( "沒有在線上的玩家，將直接退出" );
        }
        else {
            for ( String playerName :
                 PlayerLoginTime.keySet() ) {
                //持續時間 = 玩家離開時間 - 玩家登入時間
                long duration = Calendar.getInstance().getTimeInMillis() -  PlayerLoginTime.get( playerName ) ;
                if ( !fc.contains( "Players." + playerName ) )
                {
                    fc.set( "Player." + playerName , 0L );
                    saveConfig();
                }
                long hadTime = fc.getLong( "Player." + playerName );
                long nowHadTime = hadTime + duration ;
                fc.set( "Players." + playerName , nowHadTime ) ;
                getLogger().info( "已寫入玩家" + playerName + "的資料" );
                PlayerLoginTime.remove( playerName ) ;
            }
        }
    }




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
            saveConfig();
        }
    }

    @EventHandler
    public void onPlayerLeave ( PlayerQuitEvent e ){
        Player player = e.getPlayer() ;
        String playerName = player.getName() ;
        if ( !PlayerLoginTime.containsKey( playerName ) ) {
            getLogger().info( "嚴重錯誤！找不到 " + playerName + " 的Hash！" );
        }
        else {
            long playerLeaveRightNow = Calendar.getInstance().getTimeInMillis() ;
            long duration = ( playerLeaveRightNow - PlayerLoginTime.get( playerName ) ) + fc.getLong( "Players." + playerName ) ; //計算出時間差
            fc.set( "Players." + playerName , duration);
            getLogger().info( "已儲存 " + playerName + " 的時間。" );
            PlayerLoginTime.remove( playerName ) ;
            saveConfig(); //保存
        }
    }
}

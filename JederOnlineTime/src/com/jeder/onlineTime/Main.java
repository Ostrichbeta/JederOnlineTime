package com.jeder.onlineTime;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    //繼承原版庫

    @Override
    public void onEnable() {
        getLogger().info( "本插件已經成功載入" );
        getLogger().info( "玩家的遊戲時間僅會在玩家退出遊戲時保存，請注意。" );
        FileConfiguration fc = getConfig() ;

        //寫入默認設定檔
        fc.addDefault( "enabled" , true );

        this.getConfig().options().copyDefaults( true ) ;
        saveConfig(); //存儲

        //設定指令偵聽器
        this.getCommand( "jederOnlineTime" ).setExecutor( new CommandListener( this ) );
        getServer().getPluginManager().registerEvents( new EventListener( this ) , this );
    }

    @Override
    public void onDisable(){
        getLogger().info( "插件正在被停用，即將保存玩家遊戲時間紀錄" );
    }
}

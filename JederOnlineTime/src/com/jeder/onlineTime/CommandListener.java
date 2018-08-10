package com.jeder.onlineTime;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {
    private final Main plugin ;

    public CommandListener ( Main main ) {
        this.plugin = main ;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //開始偵聽指令部分
        boolean isConsole = false ;
        Player cs = ( Player ) commandSender ;
        if ( commandSender instanceof ConsoleCommandSender)
        {
            isConsole = true ;
        }
        String playerName ;
        //開始判斷
        if ( command.getName().equalsIgnoreCase( "jederOnlineTime" ) ) {
            if ( strings.length == 0 ) {
                //沒有任何的參數
               if ( isConsole ) {
                   plugin.getLogger().info( ChatColor.GOLD + "[JederOnlineTime] " + "用法：/jot query <玩家名>" );
               }
               else {
                   cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "用法：/jot query"  ) ;
                   return true ;
               }
            }
            else if ( strings [ 0 ].equalsIgnoreCase( "query" ) ) {
                if ( !isConsole ) {
                    //不是控制台
                    playerName = cs.getName() ; //獲取玩家名字
                    switch ( strings.length ) {
                        case 1:
                            if ( plugin.getConfig().contains( "Players." + playerName ) ){
                                //如果設定檔中玩家有記錄
                                long time = plugin.getConfig().getLong( "Players." + playerName ) ;
                                long[] times = new Functions().msToTime( time ) ;
                                cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "自從有記錄起，你已經在這個伺服器玩了§l " + times [ 4 ] + " 天 " + times [ 3 ] + " 時 " + times [ 2 ] + " 分 " + times [ 1 ] + " 秒 " + times [ 0 ] + " 毫秒§l。"  ) ;
                            }
                            else {
                                cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "找不到您的訊息，可能是您是第一次遊玩本伺服器，當您退出遊戲時，遊玩時間才會被記錄。"  ) ;
                            }
                            return true ;

                        case 2:
                            if ( cs.hasPermission( "jederOnlineTime.seeOthers" ) ) {
                                playerName = strings[ 1 ] ; //獲取玩家名字
                                if ( plugin.getConfig().contains( "Players." + playerName ) ){
                                    //如果設定檔中玩家有記錄
                                    long time = plugin.getConfig().getLong( "Players." + playerName ) ;
                                    long[] times = new Functions().msToTime( time ) ;
                                    cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "自從有記錄起，" + strings [ 1 ] + "已經在這個伺服器玩了§l " + times [ 4 ] + " 天 " + times [ 3 ] + " 時 " + times [ 2 ] + " 分 " + times [ 1 ] + " 秒 " + times [ 0 ] + " 毫秒§l。"  ) ;
                                }
                                else {
                                    cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "找不到這個玩家的訊息，當玩家退出遊戲時，遊玩時間才會被記錄。"  ) ;
                                }

                            }
                            else
                            {
                                //玩家沒有許可權
                                cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "你沒有許可權查看別人線上時間，如果你認為這是一個錯誤，請聯繫管理員。"  ) ;
                            }
                            return true ;
                    }

                }
                else {
                    //指令是由控制台發出的
                    if ( strings.length < 2 ) {
                        //沒有多餘的參數
                        plugin.getLogger().info( ChatColor.GOLD + "[JederOnlineTime] " + "用法：/jot query <玩家名>" );
                        return true ;
                    }
                    else if ( strings.length > 2 ){
                        plugin.getLogger().info( ChatColor.GOLD + "[JederOnlineTime] " + "用法：/jot query <玩家名>" );
                        return true ;
                    }
                    else
                    {
                        playerName = strings[ 1 ] ; //獲取玩家名字
                        if ( plugin.getConfig().contains( "Players." + playerName ) ){
                            //如果設定檔中玩家有記錄
                            long time = plugin.getConfig().getLong( "Players." + playerName ) ;
                            long[] times = new Functions().msToTime( time ) ;
                            cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "自從有記錄起，" + strings [ 1 ] + "已經在這個伺服器玩了§l " + times [ 4 ] + " 天 " + times [ 3 ] + " 時 " + times [ 2 ] + " 分 " + times [ 1 ] + " 秒 " + times [ 0 ] + " 毫秒§l。"  ) ;
                        }
                        else {
                            cs.sendMessage(ChatColor.GOLD + "[JederOnlineTime] " + "找不到這個玩家的訊息，當玩家退出遊戲時，遊玩時間才會被記錄。"  ) ;
                        }
                        return true ;
                    }
                }
            }
            else if ( strings [ 0 ].equalsIgnoreCase( "help" ) ) {
                if ( isConsole ) {
                    plugin.getLogger().info( " ==== JederOnlineTime 使用幫助 ====" );
                    plugin.getLogger().info( " /jot query <用戶名> 查詢玩家線上時間" );
                    plugin.getLogger().info( " 再次感謝您選擇本插件！" );
                    return true ;
                }
                else
                {
                    //不是從控制台發出
                    cs.sendMessage( " ==== JederOnlineTime 使用幫助 ====" );
                    cs.sendMessage( " /jot query 查詢玩家線上時間" );
                    cs.sendMessage( " 如果query後面加上了用戶名，則可以查詢其他玩家的線上時間，需要獲得一些許可權。" );
                    return true;
                }
            }
        }

        return false;
    }
}

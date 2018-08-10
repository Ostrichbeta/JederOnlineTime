package com.jeder.onlineTime;

public class Functions {
    //函式庫
    public long[] msToTime ( long time ) {
        //輸入一個毫秒值，然後返回時間
        /*
        * 輸出格式
        * i     輸出
        * 0     毫秒
        * 1     秒
        * 2     分
        * 3     時
        * 4     天
        * */
        long miliSecond = time % 1000 ;
        long second = ( time / 1000 ) % 60 ;
        long minute = ( time / 1000 / 60 ) % 60 ;
        long hour = ( time / 1000 / 60 / 60 ) % 24 ;
        long day = ( time / 1000 / 60 / 60 / 24 ) ;
        long opt[] = { miliSecond , second , minute , hour , day } ;
        return opt ;
    }
}

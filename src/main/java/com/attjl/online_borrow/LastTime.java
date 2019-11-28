package com.attjl.online_borrow;

public class LastTime {
    public static String calculatingTime(Long m){
       Long day= m/(24*60*60*1000);
       Long hour=(m%(24*60*60*1000))/(60*60*1000);
       Long minute=(m%(60*60*1000))/(60*1000);
       Long second=(m%(60*1000))/1000;
       return day+"天"+hour+"时"+minute+"分"+second+"秒";
    }
}

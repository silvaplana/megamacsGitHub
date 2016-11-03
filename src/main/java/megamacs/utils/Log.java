package megamacs.utils;

import megamacs.Enums;
import megamacs.Injector;

import java.util.Map;
import java.util.UUID;

/**
 * Created by sebastien on 23/07/2016.
 */
public class Log {
    Injector i;

    public Log(Injector injector) {
        i = injector;
    }

    public static void error(String text){
        System.out.println("ERROR:" + text);
    }

    public static void warn(String text){
        System.out.println("WARN:" + text);
    }

    public static void info(String text){
        System.out.println("INFO:" + text);
    }

    public static void debug(String text){
        System.out.println("DEBUG:" + text);
    }

    public static void trace(String text){
        System.out.println("TRACE:" + text);
    }
}

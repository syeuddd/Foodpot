package com.example.ehteshs1.foodpot.Utils;

import java.io.IOException;

/**
 * Created by ehteshs1 on 2018/03/21.
 */

public class NetworkUtils {

    public static Boolean isConnected(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue==0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}

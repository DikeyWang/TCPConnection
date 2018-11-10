package com.xtkj.chat;

import java.io.Closeable;

public class CloseUtil {
    public static void clossAll(Closeable... io){
            for (Closeable temp : io){
                try {
                    temp.close();
                }catch (Exception e){

                }

            }
    }
}

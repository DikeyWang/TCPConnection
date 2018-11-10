package com.xtkj.chat;

import java.io.DataInputStream;
import java.net.Socket;

public class Recive implements Runnable{

    private DataInputStream dis ;
    private boolean isRunning = true;

    public Recive(Socket client) {
        try {
            dis = new DataInputStream(client.getInputStream());
        }catch (Exception e){
            CloseUtil.clossAll(dis);
            isRunning = false;
        }
    }

    public String getMsgFromServer(){
        String msg = "";
        try {
            msg = dis.readUTF();
        }catch (Exception e){
            CloseUtil.clossAll(dis);
            isRunning = false;
        }
        return msg;
    }

    public void run() {
        while (isRunning){
            System.out.println("msg from server \t"+getMsgFromServer());
        }
    }
}

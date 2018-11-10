package com.xtkj.chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send  implements  Runnable{

    private BufferedReader console;
    private DataOutputStream dos;
    private boolean isRunning = true;

    public Send(Socket client) {
        try {
            console = new BufferedReader(new InputStreamReader(System.in));
            dos = new DataOutputStream(client.getOutputStream());
        }catch (IOException e){
            CloseUtil.clossAll(dos,console);
            isRunning = false;
        }

    }

    private String getMsgFromConsole(){
        String msg = "";
        try {
            msg = console.readLine();
        }catch (Exception e){
            CloseUtil.clossAll(dos,console);
            isRunning = false;
        }
        return msg;
    }

    private void sendMsg(){
        try {
            dos.writeUTF(getMsgFromConsole());
        }catch (Exception e){

        }
    }

    public void run() {
        while (isRunning){
            sendMsg();
        }
    }
}

package com.xtkj.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    // 创建一个list保存已连接的socket
    List<MyCh> clientsList = new ArrayList<MyCh>();

    public static void main(String[] args) throws Exception{
        new Server().nowStart();
    }

    /**
     *开启一个通道
     */
    public void nowStart() throws Exception{
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true){
            Socket client = serverSocket.accept();
            MyCh myCh = new MyCh(client);
            clientsList.add(myCh);
            new Thread(myCh).start();
        }

    }


    private class MyCh implements Runnable{

        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isRunning = true;

        public MyCh(Socket client) {
           try {
               dis = new DataInputStream(client.getInputStream());
               dos = new DataOutputStream(client.getOutputStream());
           }catch (Exception e){
               CloseUtil.clossAll(dis,dos);
               isRunning = false;
               clientsList.remove(this);
           }
        }

        private String recive(){
            String msg = "";
            try {
                msg = dis.readUTF();
                System.out.println("server get massage "+msg);
            }catch (Exception e){
                CloseUtil.clossAll(dis);
                isRunning = false;
                clientsList.remove(this);
            }
            return msg;
        }

        private void send(String msg) {
            for (MyCh otherClient : clientsList){
                if (otherClient == this){
                    continue;
                }
                try {
                    otherClient.dos.writeUTF(msg);
                }catch (Exception e){
                    CloseUtil.clossAll(dis);
                    isRunning = false;
                    clientsList.remove(this);
                }
            }
        }

        public void run() {
            System.out.println("someone get connect");
            while (isRunning){
                send(recive());
            }
        }
    }

}

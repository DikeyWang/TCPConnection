package com.xtkj.chat;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{

        Socket client = new Socket("127.0.0.1",9999);

        new Thread(new Send(client)).start();
        new Thread(new Recive(client)).start();
    }
}

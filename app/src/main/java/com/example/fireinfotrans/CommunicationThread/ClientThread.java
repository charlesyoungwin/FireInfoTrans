package com.example.fireinfotrans.CommunicationThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by charlesyoung on 2016/4/26.
 */
public class ClientThread implements Runnable{

    private Socket mSocket;
    private BufferedReader mReader;
    private BufferedWriter mWriter;

    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "222.244.147.133";

    @Override
    public void run() {
        try{
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            mSocket = new Socket(serverAddr,SERVERPORT);

            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(mSocket.getOutputStream()));


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

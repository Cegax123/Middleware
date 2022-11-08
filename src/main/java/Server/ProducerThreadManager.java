package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ProducerThreadManager implements Runnable {
    private final Socket client;
    private final ServerJava server;
    private final ArrayList<BlockingQueue<String>> queues = new ArrayList<>();
    private String message;
    private final boolean running;
    BufferedReader inputSocket;
    PrintWriter writerSocket;
    Scanner scInput;

    public ProducerThreadManager(Socket client_, ServerJava server_) {
        this.client = client_;
        this.server = server_;

        try {
            writerSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            inputSocket = new BufferedReader(new InputStreamReader(client.getInputStream()));
            scInput = new Scanner(System.in);
        }catch (Exception e) {
            System.out.println("TCP"+ "S: Error"+e);
        }

        running = true;
    }

    public void subscribeQueue(BlockingQueue<String> blockingQueue) {
        queues.add(blockingQueue);
    }

    public void run() {

        try{
            while(true){
                message = inputSocket.readLine();
                if(message==null){
                    break;
                }
                for (BlockingQueue<String> queue : queues) {
                    try {
                        queue.put(message);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

            client.close();

        }catch (Exception e) {
            System.out.println("TCP"+ "S: Error"+e);
        }
    }


}

package Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConsumerThreadManager implements Runnable {
    private final BlockingQueue<String> queue;
    private final boolean running;
    Socket client;
    ServerJava server;
    BufferedReader inputSocket;
    PrintWriter writerSocket;
    Scanner scInput;
    String message;

    public ConsumerThreadManager(BlockingQueue<String> blockingQueue, Socket client_, ServerJava server_) {
        this.client = client_;
        this.server = server_;

        try {
            writerSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
            inputSocket = new BufferedReader(new InputStreamReader(client.getInputStream()));
            scInput = new Scanner(System.in);
        } catch (Exception e) {
            System.out.println("TCP"+ "S: Error"+e);
        }

        this.queue = blockingQueue;
        running = true;
    }

    public void run() {
        try {
            while(running) {
                try {
                    message = queue.take();
                } catch(InterruptedException e) {
                    break;
                }

                System.out.println("Enviando mensaje " + message);
                writerSocket.println(message);
                writerSocket.flush();
            }
        }catch (Exception e) {
            System.out.println("TCP"+ "S: Error"+e);
        }
    }
}

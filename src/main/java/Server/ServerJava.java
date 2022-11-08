package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerJava implements Runnable {
    private String hostname = "192.168.1.18";
    private int port = 4444;
    private int n_producers = 0;
    private int n_consumers = 0;

    BufferedReader inputSocket;

    private int n_queues = 5;
    ArrayList<BlockingQueue<String>> queues;
    PrintWriter writerSocket;

    ServerSocket serverSocket;
    ProducerThreadManager[] producerManagers = new ProducerThreadManager[20];
    ConsumerThreadManager[] consumeManagers = new ConsumerThreadManager[20];

    public ServerJava() {
        queues = new ArrayList<>();

        for(int i = 0; i < n_queues; i++)
            queues.add(new LinkedBlockingQueue<>(100));
    }

    public static void main(String[] args) {
        ServerJava myServer = new ServerJava();
        new Thread(myServer).start();
    }

    public void run() {
        try {
            System.out.println("Server Connected");
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket client = serverSocket.accept();
                inputSocket = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String line = inputSocket.readLine();

                String[] splited = line.split("\\s+");

                String typeActor = splited[0];

                if(typeActor.equals("consumer")) {
                    System.out.println("Connection from: " + client.getLocalAddress().toString() + " as consumer");
                    n_consumers++;
                    System.out.println("Creando consumer: " + n_consumers);

                    BlockingQueue<String> queue = queues.get(Integer.parseInt(splited[1]));

                    System.out.println("Added queue " + splited[1]);

                    consumeManagers[n_consumers] = new ConsumerThreadManager(queue, client, this);
                    new Thread(consumeManagers[n_consumers]).start();
                }
                else {
                    System.out.println("Connection from: " + client.getLocalAddress().toString() + " as producer");
                    n_producers++;
                    System.out.println("Creando producer: " + n_producers);

                    producerManagers[n_producers] = new ProducerThreadManager(client, this);

                    for(int i = 1; i < splited.length; i++) {
                        int idQueue = Integer.parseInt(splited[i]);
                        producerManagers[n_producers].subscribeQueue(queues.get(idQueue));

                        System.out.println("Added queue " + splited[i]);
                    }

                    new Thread(producerManagers[n_producers]).start();
                }
            }
        } catch (Exception e) {
            System.out.println("TCP" + "S: Error" + e);
        }
    }
}
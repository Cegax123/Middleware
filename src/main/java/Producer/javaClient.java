package Producer;

import java.io.*;
import java.net.*;
import java.util.*;

public class javaClient {
    Socket socket;
    BufferedReader inputSocket;
    PrintWriter writerSocket;
    Scanner scInput;
    public javaClient(String hostname, int port, String[] idQueues) {
        try {
            socket = new Socket(hostname, port);
            writerSocket = new PrintWriter(socket.getOutputStream(), false);

            String message = "producer";
            for (String idQueue : idQueues) message += " " + idQueue;

            writerSocket.println(message);
            writerSocket.flush();

            inputSocket =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            scInput = new Scanner(System.in);

        } catch (Exception e) {
            System.out.println("TCP"+ "S: Error"+e);
        }
    }

    public void run() {
        try {
            String text;

            while(true) {
                text = scInput.nextLine();
                if(text.equals("exit")){
                    break;
                }
                writerSocket.println(text);
                writerSocket.flush();
                // System.out.println("from server: " + inputSocket.readLine());
            }

            writerSocket.close();
            inputSocket.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("TCP"+ "S: Error"+e);
        }
    }
}

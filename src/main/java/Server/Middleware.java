package Server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Middleware {
    BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(20);


}

package Producer;

public class Producer1 {
    public static void main(String[] args) {
        String[] idQueues = new String[1];
        idQueues[0] = "1";

        JavaProducer client = new JavaProducer("localhost", 4444, idQueues);
        client.run();
    }
}

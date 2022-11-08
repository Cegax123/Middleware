package Producer;

public class Producer3 {
    public static void main(String[] args) {
        String[] idQueues = new String[1];
        idQueues[0] = "3 4";

        JavaProducer client = new JavaProducer("192.168.1.18", 4444, idQueues);
        client.run();
    }
}

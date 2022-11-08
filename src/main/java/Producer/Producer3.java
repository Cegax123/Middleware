package Producer;

public class Producer3 {
    public static void main(String[] args) {
        String[] idQueues = new String[1];
        idQueues[0] = "3 4";

        javaClient client = new javaClient("localhost", 4444, idQueues);
        client.run();
    }
}

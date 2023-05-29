import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(55)) {
            while (true) {
                new SMTPHandler(serverSocket.accept()).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

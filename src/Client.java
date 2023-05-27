import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    private BufferedReader br;
    private PrintWriter pw;

    public Client() {
        try {
            this.clientSocket = new Socket("127.0.0.1", 55);
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean sendEmail(String RCPT, String FROM, String Data) {
        pw.println("HELO smtp.freesmtpservers.com\r\n");
        pw.println("MAIL FROM: "+FROM+"\r\n");
        pw.println("RCPT TO: "+RCPT+"\r\n");

        return true;
    }

}

import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    private BufferedReader br;
    private PrintWriter pw;
    private boolean debugOutput;

    public Client() {
        try {
            this.clientSocket = new Socket("127.0.0.1", 55);
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            debugOutput = false;
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean sendEmail(String FROM, String RCPT, String SUBJECT, String MSG) {
        pw.println("HELO smtp.freesmtpservers.com\r\n");
        if (debugOutput) System.out.println("HELO COMPLETED");
        pw.println("MAIL FROM: "+FROM+"\r\n");
        if (debugOutput) System.out.println("MAIL FROM - "+FROM+", COMPLETED");
        pw.println("RCPT TO: "+RCPT+"\r\n");
        if (debugOutput) System.out.println("RCPT TO - "+RCPT+", COMPLETED");
        pw.println("DATA\r\n");
        pw.println("SUBJECT: "+SUBJECT+"\r\n");
        if (debugOutput) System.out.println("SUBJECT - "+SUBJECT+", COMPLETED");
        pw.println(MSG);
        if (debugOutput) System.out.println("MSG- { "+MSG+" }, COMPLETED");
        pw.println("\r\n.\r\n");
        pw.println("QUIT\r\n");
        return true;
    }

    public void setDebug(boolean debug) {
        debugOutput = debug;
    }

}

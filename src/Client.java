import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;
    private int delay = 1000;

    public boolean sendEmail(String rcpt, String from, String subject, String Data) {
        try {
            clientSocket = new Socket("127.0.0.1", 55);

            final BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        String line;
                        while((line = br.readLine()) != null)
                            System.out.println("SERVER: " + line);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            })).start();
            
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            dos.writeBytes("EHLO smtp.freesmtpservers.com\r\n");
            Thread.sleep(delay);
            dos.writeBytes("MAIL FROM:<" + from + ">\r\n");
            Thread.sleep(delay);
            dos.writeBytes("RCPT TO:<" + rcpt + ">\r\n");
            Thread.sleep(delay);
            dos.writeBytes("DATA\r\n");
            Thread.sleep(delay);
            dos.writeBytes("Subject: " + subject + "\r\n");
            Thread.sleep(delay);
            dos.writeBytes(Data + "\r\n");
            Thread.sleep(delay);
            dos.writeBytes(".\r\n");
            Thread.sleep(delay);
            dos.writeBytes("QUIT\r\n");
            Thread.sleep(delay);

            br.close();
            clientSocket.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}

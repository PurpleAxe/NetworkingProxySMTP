import java.net.*;
import java.io.*;

public class Client {
    private Socket clientSocket;

    public boolean sendEmail(String rcpt, String from, String subject, String Data) {
        try {
            clientSocket = new Socket("127.0.0.1", 55);
            
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            dos.writeBytes("EHLO smtp.freesmtpservers.com\r\n");
            dos.writeBytes("MAIL FROM:<" + from + ">\r\n");
            dos.writeBytes("RCPT TO:<" + rcpt + ">\r\n");
            dos.writeBytes("DATA\r\n");
            dos.writeBytes("Subject: " + subject + "\r\n");
            dos.writeBytes(Data + "\r\n");
            dos.writeBytes(".\r\n");
            dos.writeBytes("QUIT\r\n");

            clientSocket.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}

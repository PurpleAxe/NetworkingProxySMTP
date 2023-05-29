import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SMTPHandler extends Thread {
    
    String destinationHost = "smtp.freesmtpservers.com";
    int destinationPort = 25;
    Socket mySocket; 
    int delay = 1000;

    public SMTPHandler(Socket socket) {
        mySocket = socket;
        System.out.println("Client connected on port: " + socket.getLocalPort());
    }

    public void run() {
        try {
            Socket destinationSocket = new Socket("smtp.freesmtpservers.com", 25);
            
            InputStream sourceInput = mySocket.getInputStream();

            final BufferedReader br = new BufferedReader(new InputStreamReader(destinationSocket.getInputStream()));
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
    
            byte[] buffer = new byte[1024];
            int bytesRead;

            String data = "";
            while ((bytesRead = sourceInput.read(buffer)) != -1) {
                data += new String(buffer, 0, bytesRead);
            }

            DataOutputStream dos = new DataOutputStream(destinationSocket.getOutputStream());
            int index;
            while ((index = data.indexOf("\r\n")) >= 0) {
                dos.writeBytes(data.substring(0, index + 2));
                Thread.sleep(delay);
                data = data.substring(index + 2);
            }
            
            destinationSocket.close();
            System.out.println("Client disconnected from server");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
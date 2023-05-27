import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPHandler extends Thread {
    
    Socket mySocket;
    int delay = 1000; 

    public SMTPHandler(Socket socket) {
        mySocket = socket;
        System.out.println("Client connected on port: " + socket.getLocalPort());
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            Socket outSocket = new Socket("smtp.freesmtpservers.com", 25);

            final BufferedReader br = new BufferedReader(new InputStreamReader(outSocket.getInputStream()));
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        String line;
                        while((line = br.readLine()) != null)
                            System.out.println("SMTP SERVER: " + line);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            })).start();
            
            DataOutputStream dos = new DataOutputStream(outSocket.getOutputStream());
    
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                inputLine = inputLine.replace("\n", "");
                dos.writeBytes(inputLine);
                Thread.sleep(delay);
            }
            
            outSocket.close();
            in.close();
            out.close();
            mySocket.close();
            System.out.println("Client disconnected from server");
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
}
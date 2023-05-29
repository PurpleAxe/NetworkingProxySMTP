import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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

            Thread destinationToClientThread = new Thread(() -> relayData(destinationSocket, mySocket));
            destinationToClientThread.start();
            
            OutputStream destinationOutput = destinationSocket.getOutputStream();
            InputStream sourceInput = mySocket.getInputStream();
    
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = sourceInput.read(buffer)) != -1) {
                // Relay data from source to destination
                destinationOutput.write(buffer, 0, bytesRead);
                destinationOutput.flush();
            }
            
            destinationSocket.close();
            System.out.println("Client disconnected from server");
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    private void relayData(Socket sourceSocket, Socket destinationSocket) {
        try {
            InputStream sourceInput = sourceSocket.getInputStream();
            OutputStream destinationOutput = destinationSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = sourceInput.read(buffer)) != -1) {
                destinationOutput.write(buffer, 0, bytesRead);
                destinationOutput.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
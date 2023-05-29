import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

            boolean dataSection = false;
            String data = "";
            while ((bytesRead = sourceInput.read(buffer)) != -1) {
                data += new String(buffer, 0, bytesRead);

                // Check if the "DATA" keyword is found
                if (!dataSection && data.contains("DATA")) {
                    dataSection = true;
                    System.out.println("FOUND DATA");
                } else if (dataSection && data.contains("\n.\r\n")) {
                    dataSection = false;
                } else if (dataSection) {
                    
                }

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
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class SMTPHandler extends Thread {
    
    final String[] blacklistFrom = {
        "blocked@tuks.co.za",
    };

    final String[] blacklistTo = {
        "scam@tuks.co.za",
    };

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
            InputStream sourceInput = mySocket.getInputStream();
    
            byte[] buffer = new byte[1024];
            int bytesRead;

            String data = "";
            while ((bytesRead = sourceInput.read(buffer)) != -1) {
                data += new String(buffer, 0, bytesRead);
            }

            data = performDataAnalysis(data);

            if (data.equals("")) {
                System.out.println("Client disconnected from server");
                return;
            }
            
            Socket destinationSocket = new Socket("smtp.freesmtpservers.com", 25);
            BufferedReader br = new BufferedReader(new InputStreamReader(destinationSocket.getInputStream()));
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

    private String performDataAnalysis(String data) throws IOException {
        int index = data.indexOf("MAIL FROM:<");
        String from = data.substring(index + 11, data.indexOf(">", index));

        if (Arrays.asList(blacklistFrom).contains(from)) {
            System.out.println("Email From Black Listed Client");
            return "";
        }

        index = data.indexOf("RCPT TO:<");
        String rcpt = data.substring(index, data.indexOf(">", index));

        if (Arrays.asList(blacklistTo).contains(rcpt)) {
            System.out.println("Email To Black Listed Client");
            return "";
        }

        index = data.indexOf("DATA\r\n");
        String message = data.substring(index + 6, data.indexOf("\n.\r\n"));
        data = data.substring(0, index + 6);

        if (message.contains("Illuminati")) {
            message = "Subject: Hello World\r\nHello World\r\n";
            return data + message + "\r\n.\r\nQUIT\r\n";
        }

        // warm -> uncold
        message = message.replaceAll(" warm ", " uncold ");
        message = message.replaceAll("warm ", "uncold ");
        message = message.replaceAll(" warm", " uncold");

        // bad -> ungood
        message = message.replaceAll(" bad ", " ungood ");
        message = message.replaceAll("bad ", "ungood ");
        message = message.replaceAll(" bad", " ungood");

        // fast -> speedful
        message = message.replaceAll(" fast ", " speedful ");
        message = message.replaceAll("fast ", "speedful ");
        message = message.replaceAll(" fast", " speedful");

        // rapid -> speedful
        message = message.replaceAll(" rapid ", " speedful ");
        message = message.replaceAll("rapid ", "speedful ");
        message = message.replaceAll(" rapid", " speedful");

        // quick -> speedful
        message = message.replaceAll(" quick ", " speedful ");
        message = message.replaceAll("quick ", "speedful ");
        message = message.replaceAll(" quick", " speedful");

        // slow -> unspeedful
        message = message.replaceAll(" slow ", " unspeedful ");
        message = message.replaceAll("slow ", "unspeedful ");
        message = message.replaceAll(" slow", " unspeedful");

        // ran -> runned
        message = message.replaceAll(" ran ", " runned ");
        message = message.replaceAll("ran ", "runned ");
        message = message.replaceAll(" ran", " runned");

        // stole -> stealed
        message = message.replaceAll(" stole ", " stealed ");
        message = message.replaceAll("stole ", "stealed ");
        message = message.replaceAll(" stole", " stealed");

        // better -> gooder
        message = message.replaceAll(" better ", " gooder ");
        message = message.replaceAll("better ", "gooder ");
        message = message.replaceAll(" better", " gooder");

        // best -> goodest
        message = message.replaceAll(" best ", " goodest ");
        message = message.replaceAll("best ", "goodest ");
        message = message.replaceAll(" best", " goodest");

        // very good -> plusgood
        message = message.replaceAll(" very good ", " plusgood ");
        message = message.replaceAll("very good ", "plusgood ");
        message = message.replaceAll(" very good", " plusgood");

        // very fast -> goodest
        message = message.replaceAll(" very fast ", " plusfast ");
        message = message.replaceAll("very fast ", "plusfast ");
        message = message.replaceAll(" very fast", " plusfast");

        // best -> goodest
        message = message.replaceAll(" very bad ", " plusungood ");
        message = message.replaceAll("very bad ", "plusungood ");
        message = message.replaceAll(" very bad", " plusungood");

        return data + message + "\r\n.\r\nQUIT\r\n";;
    }
}
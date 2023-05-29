import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

public class ClientGUI extends JFrame{
    private JPanel panel1;
    private JTextField txtMailFrom;
    private JTextField txtMailTo;
    private JTextArea txtData;
    private JButton btnSend;
    private JTextField txtSubject;

    public ClientGUI() {
        setContentPane(panel1);
        setTitle("RyAndy Mail");
        setSize(450,450);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        txtData.setCaretColor(Color.green);

        txtMailTo.setForeground(Color.GRAY);
        txtMailTo.setForeground(Color.GRAY);
        txtMailTo.setText("Recipient");
        txtMailTo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtMailTo.getText().equals("Recipient")) {
                    txtMailTo.setText("");
                    txtMailTo.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtMailTo.getText().isEmpty()) {
                    txtMailTo.setForeground(Color.GRAY);
                    txtMailTo.setText("Recipient");
                }
            }
        });


        txtSubject.setForeground(Color.GRAY);
        txtSubject.setForeground(Color.GRAY);
        txtSubject.setText("Subject");
        txtSubject.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSubject.getText().equals("Subject")) {
                    txtSubject.setText("");
                    txtSubject.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtSubject.getText().isEmpty()) {
                    txtSubject.setForeground(Color.GRAY);
                    txtSubject.setText("Subject");
                }
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String FROM = "ryanandreas@cos332prac.co.za";
                String RCPT = txtMailTo.getText();
                String SUBJECT = txtSubject.getText();
                String DATA = "";

                for(String dataLine : txtData.getText().split("\\n")) DATA += dataLine + "\r\n";

                System.out.println("FROM: "+FROM);
                System.out.println("TO: "+RCPT);
                System.out.println("SUBJECT: "+SUBJECT);
                System.out.println("");

            }
        });

    }

    public static void main(String[] args) {
        ClientGUI c = new ClientGUI();
    }
}

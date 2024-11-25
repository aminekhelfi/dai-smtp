package org.example;

import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class SmtpClient {
    private String smtpServer;
    private int port;

    public SmtpClient(String smtpServer, int port) {
        this.smtpServer = smtpServer;
        this.port = port;
    }

    public void sendEmail(String sender, List<String> recipients, String message) {
        try (Socket socket = new Socket(smtpServer, port);
             OutputStream out = socket.getOutputStream()) {

            StringBuilder email = new StringBuilder();
            email.append("MAIL FROM:<").append(sender).append(">\r\n");
            for (String recipient : recipients) {
                email.append("RCPT TO:<").append(recipient).append(">\r\n");
            }
            email.append("DATA\r\n").append(message).append("\r\n.\r\n");

            out.write(email.toString().getBytes());
            out.flush();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi : " + e.getMessage());
        }
    }
}

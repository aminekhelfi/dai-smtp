package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SmtpClient {
    private String smtpServer;
    private int port;

    public SmtpClient(String smtpServer, int port) {
        this.smtpServer = smtpServer;
        this.port = port;
    }

    public void sendEmail(String sender, List<String> recipients, String message) throws IOException {
        try (Socket socket = new Socket(smtpServer, port);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {


            for (String email : recipients) {

                try {


                    // Read server response
                    System.out.println("Response: " + in.readLine());

                    // Send HELO command
                    out.write("HELO " + smtpServer);
                    System.out.println("Response: " + in.readLine());

                    // Send MAIL FROM command
                    out.write("MAIL FROM:<" + sender + ">");
                    System.out.println("Response: " + in.readLine());

                    // Send RCPT TO command
                    out.write("RCPT TO:<" + recipients + ">");
                    System.out.println("Response: " + in.readLine());

                    // Send DATA command
                    out.write("DATA");
                    System.out.println("Response: " + in.readLine());

                    // Send email headers and body
                    out.write("Subject: ");
                    out.write("\n");
                    out.write(message);
                    out.write(".");
                    System.out.println("Response: " + in.readLine());

                    // Send QUIT command
                    out.write("QUIT");
                    System.out.println("Response: " + in.readLine());

                } catch (Exception e) {
                    System.err.println("Erreur lors de l'envoi : " + e.getMessage());

                }
            }
        }
    }
}

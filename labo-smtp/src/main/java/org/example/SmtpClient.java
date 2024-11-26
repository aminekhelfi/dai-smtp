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

    public void sendEmail(String sender, List<String> recipients, List<String> message) throws IOException {

        try (Socket socket = new Socket(smtpServer, port);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            System.out.println("Connected to the server at " + smtpServer + ":" + port);

            for (String email : recipients) {
                try {
                    System.out.println("Response: " + in.readLine());
                    out.write("ehlo " + smtpServer + "\n");
                    out.flush();
                    System.out.println("Response: " + in.readLine());
                    System.out.println("Response: " + in.readLine());
                    System.out.println("Response: " + in.readLine());
                    System.out.println("Response: " + in.readLine());
                    out.write("mail from:" + "<" + sender + ">" + "\n");
                    out.flush();
                    System.out.println("Response: " + in.readLine());
                    out.write("rcpt to:" + "<" + email + ">" + "\n");
                    out.flush();
                    System.out.println("Response: " + in.readLine());
                    out.write("data" + "\n");
                    out.flush();
                    System.out.println("Response: " + in.readLine());
                    out.write("From:" + "<" + sender + ">" + "\n");
                    out.flush();
                    out.write("To:" + "<" + email + ">" + "\n");
                    out.flush();
                    out.write("Date:" + "01.03.2026" + "\n");
                    out.flush();
                    out.write("Subject:" + message.getFirst() + "\n");
                    out.flush();
                    out.write("\n");
                    out.flush();
                    out.write(message.get(1) + "\n");
                    out.flush();
                    out.write(" \n");
                    out.flush();
                    out.write("\r\n" + "." + "\r\n");
                    out.flush();
                    System.out.println("Response: " + in.readLine());
                    out.write("quit" + "\n");
                    out.flush();
                    System.out.println("Response: " + in.readLine());
                }
                catch (Exception e) {
                    System.err.println("Erreur lors de l'envoi : " + e.getMessage());
                }
            }
        }
    }
}


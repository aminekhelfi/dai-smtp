package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class SmtpClient {
    private String smtpServer;
    private int port;

    public SmtpClient(String smtpServer, int port) {
        this.smtpServer = smtpServer;
        this.port = port;
    }
    private String getActualDate()
    {
        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();

        // Définir le format souhaité
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Formater et afficher la date
        return currentDate.format(formatter);
    }

    private void connexionServeurSMTP(BufferedReader in,BufferedWriter out, String sender, List<String> receiver)
    {
        try
        {
            System.out.println("Response: " + in.readLine());
            out.write("ehlo " + smtpServer + "\n");
            out.flush();
            String r;
            while ((r = in.readLine()) != null && r.contains("r")) {
                System.out.println("Response: " + r);
            }
            out.write("mail from:" + "<" + sender + ">" + "\n");
            out.flush();
            System.out.println("Response: " + in.readLine());
            for(int i = 0; i <receiver.size(); i++) {
                out.write("rcpt to:" + "<" + receiver.get(i) + ">" + "\n");
                out.flush();
                System.out.println("Response: " + in.readLine());
            }
        }
        catch (Exception e) {
            System.err.println("Erreur lors de la connexion: " + e.getMessage());
        }


    }

    public void sendEmail(String sender, List<String> recipients, List<String> message) throws IOException {
        //message.getFirst -> sujet
        //message.getLast -> corp

        try (Socket socket = new Socket(smtpServer, port);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            System.out.println("Connected to the server at " + smtpServer + ":" + port);

            try {
                connexionServeurSMTP(in,out,sender,recipients);

                out.write("data" + "\n");
                out.flush();
                System.out.println("Response: " + in.readLine());
                out.write("From:" + "<" + sender + ">" + "\n");
                out.flush();
                for(int i = 0; i <recipients.size(); i++) {
                    out.write("To:" + "<" + recipients.get(i) + ">" + "\n");
                    out.flush();
                }
                out.write("Date:" + getActualDate() + "\n");
                out.flush();
                out.write("Content-Type: text/plain; charset=UTF-8\r\n");
                out.flush();
                out.write("Subject:" + message.getFirst() + "\r\n");
                out.flush();
                out.write("\n");
                out.flush();
                out.write(message.getLast() + "\n");
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


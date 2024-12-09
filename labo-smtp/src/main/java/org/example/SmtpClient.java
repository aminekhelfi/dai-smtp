package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Client SMTP pour l'envoi d'emails
 * Gère la connexion au serveur SMTP et l'envoi des messages
 */
public class SmtpClient {
    private String smtpServer;
    private int port;

    /**
     * Constructeur du client SMTP
     * @param smtpServer Adresse du serveur SMTP
     * @param port Port du serveur SMTP
     */
    public SmtpClient(String smtpServer, int port) {
        this.smtpServer = smtpServer;
        this.port = port;
    }
    /**
     * Récupère la date actuelle au format dd.MM.yyyy
     * @return La date formatée en chaîne de caractères
     */
    private String getActualDate()
    {
        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();

        // Définir le format souhaité
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Formater et afficher la date
        return currentDate.format(formatter);
    }

    /**
     * Établit la connexion avec le serveur SMTP et envoie les commandes initiales
     * @param in Buffer de lecture des réponses du serveur
     * @param out Buffer d'écriture des commandes au serveur
     * @param sender Adresse email de l'expéditeur
     * @param receiver Liste des adresses email des destinataires
     */
    private void connexionServeurSMTP(BufferedReader in,BufferedWriter out, String sender, List<String> receiver)
    {
        try
        {
            System.out.println("Serveur : " + in.readLine());
            out.write("ehlo " + smtpServer + "\n");
            out.flush();
            String r;
            while ((r = in.readLine()) != null && r.contains("r")) {
                System.out.println("Serveur : " + r);
            }
            out.write("mail from:" + "<" + sender + ">" + "\n");
            out.flush();
            System.out.println("Serveur : " + in.readLine());
            for(int i = 0; i <receiver.size(); i++) {
                out.write("rcpt to:" + "<" + receiver.get(i) + ">" + "\n");
                out.flush();
                System.out.println("Serveur : " + in.readLine());
            }
        }
        catch (Exception e) {
            System.err.println("Erreur lors de la connexion: " + e.getMessage());
        }


    }

    /**
     * Envoie un email via le protocole SMTP
     * @param sender Adresse email de l'expéditeur
     * @param recipients Liste des adresses email des destinataires
     * @param message Liste contenant le sujet (premier élément) et le corps (dernier élément) du message
     * @throws IOException En cas d'erreur lors de la communication avec le serveur
     */
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
                System.out.println("Serveur : " + in.readLine());
                out.write("From:" + "<" + sender + ">" + "\n");
                out.flush();
                out.write("To: you <>" + "\n");
                out.flush();
                out.write("Date:" + getActualDate() + "\n");
                out.flush();
                out.write("Content-Type: text/plain; charset=UTF-8\r\n");
                out.flush();
                out.write("Subject:" + message.get(0) + "\r\n");
                out.flush();
                out.write("\n");
                out.flush();
                out.write(message.get(message.size()-1) + "\n");
                out.flush();
                out.write(" \n");
                out.flush();
                out.write("\r\n" + "." + "\r\n");
                out.flush();
                System.out.println("Serveur : " + in.readLine());
                out.write("quit" + "\n");
                out.flush();
                System.out.println("Serveur : " + in.readLine());
            }
            catch (Exception e) {
                System.err.println("Erreur lors de l'envoi : " + e.getMessage());
            }
        }
    }
}

package org.example;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import org.json.*;
import static org.example.json_reader.*;

/**
 * Classe responsable de la génération et de l'envoi d'emails de farce
 * Gère les groupes de victimes, les messages d'email et le processus d'envoi
 */
public class PrankGenerator {
    private List<String> victims;
    private List<String> messages;
    private SmtpClient smtpClient;
    private int nbGroupe;
    private int nbAddrMail;


    /**
     * Constructeur de PrankGenerator
     * @param nbgroupe Nombre de groupes à créer (nombre d'emails à envoyer)
     * @param nbAddrMail Nombre d'adresses email par groupe
     * @throws IOException En cas d'erreur lors de la lecture des fichiers d'email ou de messages
     */
    public PrankGenerator(int nbgroupe, int nbAddrMail, String victims_email, String messages_email) throws IOException {
        if(checkEmailFormat(readJsonFile(victims_email,"emails")))
        {
            //liste tout les mail
            this.victims = readJsonFile(victims_email,"emails");
        }
        else {
            System.out.println("Email format is incorrect");
            return;
        }

        //liste tout les sujet (idx paire) et tout les corps (idx impaire)
        this.messages = readJsonFile(messages_email,"messages");

        this.smtpClient = new SmtpClient("localhost", 1025); // Adresse et port du serveur SMTP

        this.nbGroupe = nbgroupe;

        //si il y a 10 victimes dans la liste de mails, les groupes peuvent être composer de 2 à 5 addresse(s)
        if (nbAddrMail < 2 || nbAddrMail > victims.size() / 2) {
            throw new IllegalArgumentException("Nombre de groupes invalide !");
        }

        this.nbAddrMail = nbAddrMail;

    }


    /**
     * Valide le format des adresses email
     * @param emailAddresses Liste des adresses email à valider
     * @return vrai si toutes les adresses email sont valides, faux sinon
     */
    private boolean checkEmailFormat(List<String> emailAddresses) {
        // Expression régulière pour valider le format des adresses e-mail
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);

        for (String email : emailAddresses) {
            if (!pattern.matcher(email).matches()) {
                return false; // Retourne faux si une adresse e-mail est invalide
            }
        }
        return true; // Retourne vrai si toutes les adresses e-mail sont valides
    }

    /**
     * Génère et envoie des emails de farce à tous les groupes
     * @throws IOException En cas d'erreur lors de l'envoi des emails
     */
    public void generateAndSendPranks() throws IOException {

        for(int i = 0; i < nbGroupe; i++) {

            Group group = createGroup(nbAddrMail);
            smtpClient.sendEmail(group.getSender(), group.getRecipients(), group.getMessage());

        }

    }

    /**
     * Crée un groupe avec un nombre spécifié d'adresses email
     * @param nbAddrMail Nombre d'adresses email à inclure dans le groupe
     * @return Un nouvel objet Group contenant l'expéditeur et les destinataires
     */
    private Group createGroup(int nbAddrMail) {

        Collections.shuffle(victims); // Mélange les victimes
        List<String> receiver = new ArrayList<>();

        for(int i = 1; i < nbAddrMail; i++) {

            receiver.add(victims.get(i));

        }

        return  new Group(victims.get(0), receiver, getRandomMessage());

    }

    /**
     * Sélectionne un message aléatoire parmi les messages disponibles
     * @return Liste contenant le sujet (index 0) et le corps (index 1) du message
     */
    private List<String> getRandomMessage() {

        //la fonction retourne dans une liste de string un message avec son sujet en firt et son corp en last

        List<String> message = new ArrayList<>();
        int idx = new Random().nextInt(messages.size());
        if (idx % 2 == 0) {//si paire -> on a le sujet et le corp est à idx +1
            message.add(messages.get(idx));
            message.add(messages.get(idx + 1));
        } else//si impaire -> on a le corps et le sujet est à idx -1
        {
            message.add(messages.get(idx - 1));
            message.add(messages.get(idx));
        }

        return message;
    }
}

package org.example;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static org.example.json_reader.readJsonFile;

public class PrankGenerator {
    private List<String> victims;
    private List<String> messages;
    private SmtpClient smtpClient;
    private int nbGroupe;
    private int nbAddrMail;

    //prend en paramètre le nombre de groupe donc le nombre de mails qui seront envoyés
    public PrankGenerator(int nbgroupe, int nbAddrMail) throws IOException {
        if(checkEmailFormat(readJsonFile("files/email.json","emails")))
        {
            //liste tout les mail
            this.victims = readJsonFile("files/email.json","emails");
        }
        else {
            System.out.println("Email format is incorrect");
            return;
        }

        //liste tout les sujet (idx paire) et tout les corps (idx impaire)
        this.messages = readJsonFile("files/fishing_messages.json","messages");

        this.smtpClient = new SmtpClient("localhost", 1025); // Adresse et port du serveur SMTP

        this.nbGroupe = nbgroupe;

        //si il y a 10 victimes dans la liste de mails, les groupes peuvent être composer de 2 à 5 addresse(s)
        if (nbAddrMail < 2 || nbAddrMail > victims.size() / 2) {
            throw new IllegalArgumentException("Nombre de groupes invalide !");
        }

        this.nbAddrMail = nbAddrMail;

    }


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

    //groupCount est le nombre d'addresse mail qu'il y a dans le groupe
    public void generateAndSendPranks() throws IOException {

        for(int i = 0; i < nbGroupe; i++) {

            Group group = createGroup(nbAddrMail);
            smtpClient.sendEmail(group.getSender(), group.getRecipients(), group.getMessage());

        }

    }

    private Group createGroup(int nbAddrMail) {

        Collections.shuffle(victims); // Mélange les victimes
        List<String> receiver = new ArrayList<>();

        for(int i = 1; i < nbAddrMail; i++) {

            receiver.add(victims.get(i));

        }

        return  new Group(victims.getFirst(), receiver, getRandomMessage());

    }

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


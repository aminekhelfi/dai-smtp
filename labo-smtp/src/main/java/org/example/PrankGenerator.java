package org.example;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static org.example.json_reader.readJsonFile;

public class PrankGenerator {
    private List<List<String>> victims;
    private List<List<String>> messages;
    private SmtpClient smtpClient;

    public PrankGenerator(int nbgroupe) throws IOException {
        if(checkEmailFormat(readJsonFile("files/email.json","emails")))
        {
            this.victims = readJsonFile("files/email.json","emails");
        }
        else {
            System.out.println("Email format is incorrect");
            return;
        }

        this.messages = readJsonFile("files/fishing_messages.json","messages");
        this.smtpClient = new SmtpClient("localhost", 1025); // Adresse et port du serveur SMTP


        generateAndSendPranks(nbgroupe);
    }


    private boolean checkEmailFormat(List<List<String>> emailAddresses) {
        // Expression régulière pour valider le format des adresses e-mail
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);

        for (String email : emailAddresses.get(0)) {
            if (!pattern.matcher(email).matches()) {
                return false; // Retourne faux si une adresse e-mail est invalide
            }
        }
        return true; // Retourne vrai si toutes les adresses e-mail sont valides
    }

    public void generateAndSendPranks(int groupCount) throws IOException {
        if (groupCount < 1 || groupCount > victims.size() / 2) {
            throw new IllegalArgumentException("Nombre de groupes invalide !");
        }

        Collections.shuffle(victims); // Mélange les victimes
        List<Group> groups = createGroups(groupCount); //créer les groupe

        for (int i = 0; i< groups.size(); i++) {
            List<String> message = getRandomMessage();
            smtpClient.sendEmail(groups.get(i).getSender(), groups.get(i).getRecipients(), message);
        }
    }

    private List<Group> createGroups(int groupCount) {
        List<Group> groups = new ArrayList<>();
        int groupSize = victims.size() / groupCount;

        for (int i = 0; i < groupCount; i++) {
            int start = i * groupSize;
            int end = Math.min(start + groupSize, victims.size());
            List<List<String>> groupMembers = victims.subList(start, end);

            if (groupMembers.size() > 1) {
                String sender = groupMembers.get(0).get(0);
                List<String> recipients = groupMembers.subList(1, groupMembers.size()).get(0);
                groups.add(new Group(sender, recipients));
            }
        }

        return groups;
    }

    private List<String> getRandomMessage() {
        return messages.get(new Random().nextInt(messages.size()));
    }

    private List<String> loadFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            lines.add(line.trim());
        }
        reader.close();
        return lines;
    }
}


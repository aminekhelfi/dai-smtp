package org.example;

import java.io.*;
import java.util.*;
import static org.example.json_reader.readJsonFile;

public class PrankGenerator {
    private List<String> victims;
    private List<String> messages;
    private SmtpClient smtpClient;

    public PrankGenerator() throws IOException {
        this.victims = readJsonFile("files/email.json","emails");
        this.messages = readJsonFile("files/fishing_messages.json","messages");
        this.smtpClient = new SmtpClient("localhost", 1025); // Adresse et port du serveur SMTP
    }

    public void generateAndSendPranks(int groupCount) throws IOException {
        if (groupCount < 1 || groupCount > victims.size() / 2) {
            throw new IllegalArgumentException("Nombre de groupes invalide !");
        }

        Collections.shuffle(victims); // Mélange les victimes
        List<Group> groups = createGroups(groupCount); //créer les groupe

        for (Group group : groups) {
            String message = getRandomMessage();
            smtpClient.sendEmail(group.getSender(), group.getRecipients(), message);
        }
    }

    private List<Group> createGroups(int groupCount) {
        List<Group> groups = new ArrayList<>();
        int groupSize = victims.size() / groupCount;

        for (int i = 0; i < groupCount; i++) {
            int start = i * groupSize;
            int end = Math.min(start + groupSize, victims.size());
            List<String> groupMembers = victims.subList(start, end);

            if (groupMembers.size() > 1) {
                String sender = groupMembers.get(0);
                List<String> recipients = groupMembers.subList(1, groupMembers.size());
                groups.add(new Group(sender, recipients));
            }
        }

        return groups;
    }

    private String getRandomMessage() {
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


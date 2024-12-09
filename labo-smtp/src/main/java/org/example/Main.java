package org.example;
import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        // "../" compiler avec maven
        // pour compiler depuis l'IDE, enlenver "../"
        final String victims_email="../files/email.json";
        final String messages_email="../files/fishing_messages.json";

        if (args.length < 1) {

            System.out.println("Veuillez fournir un nombre en argument.");
            return;

        }

        int numberOfPranks;

        try {
            numberOfPranks = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("L'argument doit être un nombre entier.");
            return;
        }

        PrankGenerator prank = new PrankGenerator(numberOfPranks,3,victims_email,messages_email);
        prank.generateAndSendPranks();
    }
}
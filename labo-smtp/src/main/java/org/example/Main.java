package org.example;
import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class main {
    public static void main(String[] args) throws IOException {

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

        PrankGenerator prank = new PrankGenerator(numberOfPranks,3); //a passer en paramètre
        prank.generateAndSendPranks();
    }
}
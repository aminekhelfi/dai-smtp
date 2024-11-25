package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.io.*;




public class Main {
    public static void main(String[] args) {
        /*String ListeVictimes=args[0];
        String ListeMessages=args[1];
        int Groupe=Integer.parseInt(args[2]);*/

        String jsonFilePath = "C:\\Users\\khelf\\Documents\\HEIG\\DAI\\dai-lab-smtp\\email.json";
        for (String email : json_reader.readJsonFile(jsonFilePath,"emails")) {
            System.out.println(email);
        }


         jsonFilePath = "C:\\Users\\khelf\\Documents\\HEIG\\DAI\\dai-lab-smtp\\fishing_messages.json";
        for (String email : json_reader.readJsonFile(jsonFilePath,"messages")) {
            System.out.println(email);
        }



    }
}



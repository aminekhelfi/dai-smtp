package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.json.*;


//sers à lire les fichier json
public class json_reader {


    public static List<String> readJsonFile(String filePath, String key) {
        List<String> result = new ArrayList<>();

        try {
            // Lire le contenu du fichier JSON
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Convertir le contenu en un objet JSON
            JSONObject jsonObject = new JSONObject(content);

            // Extraire la clé spécifique du JSON
            if (key.equals("emails")) {
                // Si la clé est "emails", extraire un tableau simple
                JSONArray emailArray = jsonObject.getJSONArray(key);
                for (int i = 0; i < emailArray.length(); i++) {
                    result.add(emailArray.getString(i));
                }
            } else if (key.equals("messages")) {
                // Si la clé est "messages", extraire les sujets et corps
                JSONArray messageArray = jsonObject.getJSONArray(key);
                for (int i = 0; i < messageArray.length(); i++) {
                    JSONObject messageObject = messageArray.getJSONObject(i);
                    String subject = messageObject.getString("subject");
                    String body = messageObject.getString("body");
                    result.add("Sujet: " + subject + "\nCorps: " + body);
                }
            } else {
                System.err.println("Clé inconnue : " + key);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }

        return result;
    }


    public static void reader(String json_file, String row) {
        try {
            // Lire le contenu du fichier JSON
            String content = new String(Files.readAllBytes(Paths.get(json_file)));

            // Convertir le contenu en un objet JSON
            JSONObject jsonObject = new JSONObject(content);

            // Extraire la liste des e-mails
            JSONArray emailArray = jsonObject.getJSONArray(row);

            // Convertir le JSONArray en une List<String>
            List<String> emailList = new ArrayList<>();
            for (int i = 0; i < emailArray.length(); i++) {
                emailList.add(emailArray.getString(i));
            }

            for(String email : emailList) {
                System.out.println(email);
            }


        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Erreur lors de la lecture du JSON : " + e.getMessage());
        }
    }
}

package org.example;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

/**
 * Classe utilitaire pour la lecture de fichiers JSON
 * Permet de lire des fichiers contenant des listes d'emails ou de messages
 */
public class json_reader {

    /**
     * Lit un fichier JSON et extrait les données selon la clé spécifiée
     * @param filePath Chemin du fichier JSON à lire
     * @param key Clé à extraire ("emails" pour les adresses email ou "messages" pour les messages)
     * @return Liste de chaînes contenant soit les emails, soit les messages (sujet + corps)
     */
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
                    List<String> email = new ArrayList<>();
                    email.add(emailArray.getString(i));
                    result.addAll(email);
                }
            } else if (key.equals("messages")) {
                // Si la clé est "messages", extraire les sujets et corps
                JSONArray messageArray = jsonObject.getJSONArray(key);
                for (int i = 0; i < messageArray.length(); i++) {
                    JSONObject messageObject = messageArray.getJSONObject(i);
                    String subject = messageObject.getString("subject");
                    String body = messageObject.getString("body");
                    List<String> message = new ArrayList<>();
                    message.add(subject);
                    message.add(body);
                    result.addAll(message);//ajoute tout les éléments dans results (corp + sujet) de tout les message
                }
            }
            else {
                System.err.println("Clé inconnue : " + key);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }

        return result;
    }
}

package org.example;
import java.util.*;
import java.util.List;

public class Groupe {

    public static List<List<String>> formGroups(List<String> emailList, int numberOfGroups) {
        Collections.shuffle(emailList); //mélange les éléments pour que ça sois aléatoire.

        List<List<String>> groups = new ArrayList<>();
        int emailIndex = 0;

        for (int i = 0; i < numberOfGroups; i++) {
            // Taille aléatoire du groupe entre 2 et 5
            int groupSize = 2 + new Random().nextInt(4);

            // S'assurer qu'il reste assez d'adresses pour former le groupe
            groupSize = Math.min(groupSize, emailList.size() - emailIndex);

            // Former le groupe
            List<String> group = new ArrayList<>(emailList.subList(emailIndex, emailIndex + groupSize));
            groups.add(group);

            // Avancer l'index
            emailIndex += groupSize;
        }

        return groups;
    }
}

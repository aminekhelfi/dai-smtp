package org.example;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un groupe pour l'envoi d'emails de farce
 * Cette classe contient les informations sur l'expéditeur, les destinataires et le contenu du message
 */
public class Group {
    private String sender;
    private List<String> recipients;
    private List<String> message = new ArrayList<String>();

    /**
     * Constructeur pour créer un nouveau groupe
     * @param sender L'adresse email de l'expéditeur
     * @param recipients Liste des adresses email des destinataires
     * @param message Liste de chaînes de caractères contenant le message de l'email
     */
    public Group(String sender, List<String> recipients, List<String> message) {
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
    }

    /**
     * Récupère l'adresse email de l'expéditeur
     * @return L'adresse email de l'expéditeur sous forme de chaîne de caractères
     */
    public String getSender() {
        return sender;
    }

    /**
     * Récupère la liste des destinataires
     * @return Liste des adresses email des destinataires
     */
    public List<String> getRecipients() {
        return recipients;
    }

    /**
     * Récupère le contenu du message de l'email
     * @return Liste de chaînes de caractères contenant le message
     */
    public List<String> getMessage() {
        return message;
    }
}

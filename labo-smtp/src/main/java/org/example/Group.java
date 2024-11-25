package org.example;
import java.util.List;

public class Group {
    private String sender;
    private List<String> recipients;

    public Group(String sender, List<String> recipients) {
        this.sender = sender;
        this.recipients = recipients;
    }

    public String getSender() {
        return sender;
    }

    public List<String> getRecipients() {
        return recipients;
    }
}

package org.example;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private String sender;
    private List<String> recipients;
    private List<String> message = new ArrayList<String>();

    public Group(String sender, List<String> recipients, List<String> message) {
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
    }

    public String getSender()
    {
        return sender;
    }

    public List<String> getRecipients() {
                return recipients;
    }

    public List<String> getMessage() {
        return message;
    }
}

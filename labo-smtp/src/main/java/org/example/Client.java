import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;



public class Client {
    final String smtpServer = "localhost";
    final int port = 5000;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    // Méthode privée pour vérifier le format des adresses e-mail
    private boolean checkEmailFormat(List<String> emailAddresses) {
        // Expression régulière pour valider le format des adresses e-mail
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);

        for (String email : emailAddresses) {
            if (!pattern.matcher(email).matches()) {
                return false; // Retourne faux si une adresse e-mail est invalide
            }
        }
        return true; // Retourne vrai si toutes les adresses e-mail sont valides
    }

    private class Message {



    }

    private void run(List<String> emailAddresses) {
        try (Socket socket = new Socket(smtpServer, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            from = 'address dest';

            if (checkEmailFormat(emailAddresses)) {
                for (String email : emailAddresses) {

                    to = email;

                    // Read server response
                    System.out.println("Response: " + in.readLine());

                    // Send HELO command
                    out.println("HELO " + smtpServer);
                    System.out.println("Response: " + in.readLine());

                    // Send MAIL FROM command
                    out.println("MAIL FROM:<" + from + ">");
                    System.out.println("Response: " + in.readLine());

                    // Send RCPT TO command
                    out.println("RCPT TO:<" + to + ">");
                    System.out.println("Response: " + in.readLine());

                    // Send DATA command
                    out.println("DATA");
                    System.out.println("Response: " + in.readLine());

                    // Send email headers and body
                    out.println("Subject: " + subject);
                    out.println();
                    out.println(body);
                    out.println(".");
                    System.out.println("Response: " + in.readLine());

                    // Send QUIT command
                    out.println("QUIT");
                    System.out.println("Response: " + in.readLine());
                }

            }
        }
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
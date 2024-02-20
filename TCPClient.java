import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 8081;

        try {
            // Connexion au serveur
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connecté au serveur.");

            // Initialisation des flux de lecture et d'écriture
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Envoi d'une chaîne de caractères au serveur
            Scanner scanner = new Scanner(System.in);
           System.out.print("Enter la chaine à inverser: ");
            String message = scanner.nextLine();
            out.println(message);
            System.out.println("Message envoyé au serveur : " + message);

            // Lecture de la réponse du serveur
            String reversedMessage = in.readLine();
            System.out.println("Message inversé reçu du serveur : " + reversedMessage);

            // Fermeture des flux et de la connexion
            out.close();
            in.close();
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) {
        final int MAX_CLIENTS = 10;
        ServerSocket serverSocket = null;
        
        try {
            // Création du serveur sur le port 5
            serverSocket = new ServerSocket(8081);
            System.out.println("Serveur démarré. En attente des clients...");

            while (true) {
                // Attente d'une connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion cliente : " + clientSocket);

                // Si le nombre maximal de clients est atteint, fermer la connexion avec un message
                if (Thread.activeCount() > MAX_CLIENTS) {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("Nombre maximal de clients atteint. Réessayez plus tard.");
                    clientSocket.close();
                    continue;
                }

                // Création d'un thread pour traiter la requête du client
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Fermeture du socket du serveur
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                // Initialisation des flux de lecture et d'écriture
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Lecture de la chaîne de caractères envoyée par le client
                String inputLine = in.readLine();

                // Simuler un traitement en inversant la chaîne de caractères et en ajoutant un délai
                String reversedString = reverseString(inputLine);
                Thread.sleep(5000); // Ajout d'un délai de 5 secondes
                out.println(reversedString);

                // Fermeture des flux et du socket
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Méthode pour inverser une chaîne de caractères
        private String reverseString(String str) {
            return new StringBuilder(str).reverse().toString();
        }
    }
}

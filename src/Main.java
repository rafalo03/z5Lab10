import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Prosimy użytkownika o podanie trzech pierwszych cyfr numeru konta
        System.out.print("Podaj pierwsze trzy cyfry numeru konta: ");
        String userInput = scanner.nextLine().trim();

        if (userInput.length() != 3 || !userInput.matches("\\d{3}")) {
            System.out.println("Wprowadź dokładnie trzy cyfry.");
            return;
        }

        try {
            // 2. Pobieramy zawartość pliku z URL
            String urlString = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            boolean found = false;

            // 3. Wczytujemy plik linia po linii
            while ((line = reader.readLine()) != null) {
                // Zakładając, że dane mają format: "numer_banku nazwa_banku"
                String[] parts = line.split("\\s+", 2);  // Rozdzielamy tylko na dwie części: numer banku i nazwę
                if (parts.length >= 2) {
                    String bankCode = parts[0].trim(); // Pierwsze 3 cyfry numeru banku
                    String bankName = parts[1].trim(); // Reszta linii to pełna nazwa banku

                    // 4. Sprawdzamy, czy numer banku pasuje do wprowadzonego przez użytkownika
                    if (bankCode.startsWith(userInput)) {
                        System.out.println("Konto należy do banku: " + bankName);
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Nie znaleziono banku dla podanych cyfr.");
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas pobierania pliku: " + e.getMessage());
        }
    }
}
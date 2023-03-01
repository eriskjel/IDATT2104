import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Opprett en UDP-socket
            DatagramSocket socket = new DatagramSocket();

            // Opprett en Scanner for å lese fra brukeren
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Les inn tallene og operatoren fra brukeren
                System.out.print("Skriv inn det første tallet: ");
                int x = scanner.nextInt();
                System.out.print("Skriv inn det andre tallet: ");
                int y = scanner.nextInt();
                System.out.print("Skriv inn operatoren (+, -, *, /): ");
                String operator = scanner.next();

                // Lag en melding med tallene og operatoren
                String message = x + "," + y + "," + operator;

                // Send meldingen til tjeneren
                InetAddress address = InetAddress.getByName("localhost");
                int port = 1234;
                byte[] requestBytes = message.getBytes();
                DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length, address, port);
                socket.send(request);

                // Motta resultatet fra tjeneren
                byte[] responseBytes = new byte[1024];
                DatagramPacket response = new DatagramPacket(responseBytes, responseBytes.length);
                socket.receive(response);

                // Skriv ut resultatet på skjermen
                String result = new String(response.getData(), 0, response.getLength());
                System.out.println("Resultatet er " + result);

                // Vent i noen sekunder før neste iterasjon
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

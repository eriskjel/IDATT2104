import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            // Opprett en UDP-socket
            DatagramSocket socket = new DatagramSocket(1234);

            while (true) {
                // Motta melding fra klienten
                byte[] requestBytes = new byte[1024];
                DatagramPacket request = new DatagramPacket(requestBytes, requestBytes.length);
                socket.receive(request);

                // Les meldingen fra klienten
                String message = new String(request.getData(), 0, request.getLength());

                // Skriv ut regnestykket som klienten har sendt inn
                System.out.println("Regnestykket som ble sendt inn er: " + message);

                // Beregn resultatet av regnestykket
                String[] parts = message.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                String operator = parts[2];
                int result = switch (operator) {
                    case "+" -> x + y;
                    case "-" -> x - y;
                    case "*" -> x * y;
                    case "/" -> x / y;
                    default -> 0;
                };

                // Lag en melding med resultatet
                String responseMessage = Integer.toString(result);

                // Send resultatet tilbake til klienten
                InetAddress address = request.getAddress();
                int port = request.getPort();
                byte[] responseBytes = responseMessage.getBytes();
                DatagramPacket response = new DatagramPacket(responseBytes, responseBytes.length, address, port);
                socket.send(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

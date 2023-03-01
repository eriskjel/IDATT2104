import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadClass extends Thread {

    private InputStreamReader readConnection;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket clientSocket;

    public ThreadClass(Socket clientSocket) throws IOException {
        this.readConnection = new InputStreamReader(clientSocket.getInputStream());
        this.reader = new BufferedReader(readConnection);
        this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        writer.println("----------------------------------------");
        writer.println("Hey! Welcome to the calculator server!");
        writer.println("Write a simple calculation on the form a+b, a-b, a*b or a/b.");

        // Fetching input from client
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (line != null) {
            String[] arr = line.split("(?<=[-+*/])|(?=[-+*/])");
            if (arr[0].equals("exit")) {
                System.out.println("Client " + clientSocket.getInetAddress().getHostAddress() + " disconnected.");
                //writer.println("exit");
                break;
            }
            System.out.println("Client " + clientSocket.getInetAddress().getHostAddress() + " wrote: " + line);
            if (arr.length != 3) {
                writer.println("Invalid input. Try again.");
                try {
                    writer.println("Write a simple calculation on the form a+b, a-b, a*b or a/b.");
                    line = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            if (arr[1].equals("/") && arr[2].equals("0")) {
                writer.println("You can't divide by zero. Try again.");
                try {
                    writer.println("Write a simple calculation on the form a+b, a-b, a*b or a/b.");
                    line = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            String operation = arr[1];
            int numberOne = Integer.parseInt(arr[0]);
            int numberTwo = Integer.parseInt(arr[2]);

            int result = switch (operation) {
                case "+" -> numberOne + numberTwo;
                case "-" -> numberOne - numberTwo;
                case "*" -> numberOne * numberTwo;
                case "/" -> numberOne / numberTwo;
                default -> 0;
            };

            writer.println("Result of " + line + ", is: " + result + ".");
            writer.println("Write a simple calculation on the form a+b, a-b, a*b or a/b.");
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //closing connection
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.close();
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

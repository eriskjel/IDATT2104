import java.io.*;
import java.net.*;
import java.util.Scanner;

class SocketClient {
  public static void main(String[] args) throws IOException {
    final int PORTNR = 1250;


    Scanner scanner = new Scanner(System.in);
    System.out.print("State the server you want to connect to: ");
    String server = scanner.nextLine();


    Socket connection = new Socket(server, PORTNR);
    System.out.println("Connection established.");


    InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
    BufferedReader reader = new BufferedReader(readConnection);
    PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);


    String prompt1 = reader.readLine();
    String prompt2 = reader.readLine();
    String prompt3 = reader.readLine();
    System.out.println(prompt1 + "\n" + prompt2 + "\n" + prompt3);


    String line = scanner.nextLine();
    while (!line.equals("")) {
      if (line.equals("exit")) {
        writer.println("exit");
        break;
      }
      writer.println(line);
      String response = reader.readLine();
      System.out.println("From server: " + response);
      System.out.println(reader.readLine());
      line = scanner.nextLine();
    }

    //closing connection
    reader.close();
    writer.close();
    connection.close();
  }
}

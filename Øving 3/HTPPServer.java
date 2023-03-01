import java.io.*;
import java.net.*;

class HTTPServer {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 80;

        //ServerSocket server = null;

        try {
            ServerSocket server = new ServerSocket(PORTNR);
            System.out.println("Booting server... Waiting for connection...");
            while (true) {
                Socket socket = server.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Connection established");
                String string = """
                        HTTP/1.1 200 OK
                        Content-Type: text/html; charset=utf-8
                                        
                                            
                        <html><body>
                        <h1> Hilsen. Du har koblet deg opp til min enkle web-tjener!</h1>""";

                socket.getOutputStream().write(string.getBytes());
                StringBuilder sb = new StringBuilder();
                String header = "Header from client: \n <ul>";
                String line = bufferedReader.readLine();
                sb.append(header);
                while (line != null && !line.isEmpty()) {
                    String string2 = "<li>" + line + "</li>";
                    sb.append(string2);
                    line = bufferedReader.readLine();
                }
                sb.append("</ul></body></html>");
                socket.getOutputStream().write(sb.toString().getBytes());
                bufferedReader.close();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}

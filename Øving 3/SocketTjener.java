  import java.io.*;
  import java.net.*;

  class SocketTjener {
    public static void main(String[] args) throws IOException {
      final int PORTNR = 1250;

      ServerSocket server = null;

      try{
        server = new ServerSocket(PORTNR);
        System.out.println("Booting server... Waiting for connection...");


        while(true){
          Socket connection = server.accept();
          System.out.println("Connection established with: " + connection.getInetAddress().getHostAddress());
          new ThreadClass(connection).start();
        }
      } catch(IOException e){
        System.out.println("Could not listen on port: " + PORTNR);
        System.exit(-1);
      }
      finally {
        if (server != null) {
          try {
            server.close();
          }
          catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

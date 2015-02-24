import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
      boolean flag = true;
      while(flag) {

        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String inputLine, outputLine;
            int output;
            
            Protocol protocol = new Protocol();
            output = protocol.processInput(null);
            outputLine = protocol.startComm();
            System.out.println(outputLine);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                output = protocol.processInput(inputLine);
                if(output != -6 && output != -7) {
                  out.println("get " + inputLine + ", return: " + output);
                  System.out.println("S> get " + inputLine + ", return: " + output);
                } else if(output == -7) {
                    out.println("get: bye, return -5");
                    System.out.println("S> get: bye, return -5");
                    break;
                } else {
                    out.println("get: bye, return -5");
                    System.out.println("S> get: bye, return -5");
                    flag=false;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
      }
    }
}

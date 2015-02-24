import java.net.*;
import java.io.*;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
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
            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            output = kkp.processInput(null);
            outputLine = kkp.startComm();
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                output = kkp.processInput(inputLine);
                out.println("get " + inputLine + ", return: " + output);
               if (outputLine.equals("bye"))
                   break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

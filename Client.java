import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                "Usage: java Client <host name:sand> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;


            System.out.println("C> receive: Hello!");
            while ((fromServer = in.readLine()) != null) {
                String[] server =  fromServer.split(" ");

                int length = server.length;
                if(server[length-1].equals("-1")) {
                  System.out.println("C> receive: incorrect operation command.");
                } else if(server[length-1].equals("-2")) {
                  System.out.println("C> receive: number of inputs is less than two.");
                } else if(server[length-1].equals("-3")) {
                  System.out.println("C> receive: number of inputs is more than four");
                } else if(server[length-1].equals("-4")) {
                  System.out.println("C> receive: one or more of the inputs contain(s) non-number(s).");
                } else if(server[length-1].equals("-5")) {
                  System.out.println("C> receive: exit.");
                  break;
                } else if(server[0].equals("S>")) {
                  System.out.println();
                } else {
                  System.out.println("C> receive: " + server[length-1]);
                }


                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}

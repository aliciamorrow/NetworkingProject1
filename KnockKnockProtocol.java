import java.net.*;
import java.io.*;

public class KnockKnockProtocol {
    private static final int WAITING = 0;
    private static final int COMPUTATION = 1;
    private static final int EXIT = 2;

    private int state = WAITING;

    public String startComm() {
      //get and return IP address
      InetAddress ip;
      String theOutput = "";
  	  try {
    		ip = InetAddress.getLocalHost();
    		theOutput = "get connection from IP:" + ip.getHostAddress();
  	  } catch (UnknownHostException e) {
    		e.printStackTrace();
  	  }
      return theOutput;
    }

    public int processInput(String theInput) {
        int output = 0;

        if (state == WAITING) {
            state = COMPUTATION;
        } else if (state == COMPUTATION) {
            String[] str2 =  theInput.split(" ");
            String operand = str2[0];
            if(operand.equals("add")) {
                output = Integer.parseInt(str2[1]);
                for(int j = 2; j < str2.length; j++) {
                  output += Integer.parseInt(str2[j]);
                }
            } else if(operand.equals("subtract")) {
                output = Integer.parseInt(str2[1]);
                for(int k = 2; k < str2.length; k++) {
                  output -= Integer.parseInt(str2[k]);
                }
            } else if(operand.equals("multiply")) {
                output = Integer.parseInt(str2[1]);
                for(int m = 2; m < str2.length; m++) {
                  output *= Integer.parseInt(str2[m]);
                }
            } else if(operand.equals("bye")) {
                state = EXIT;
                return -5;
            } else {
                state = WAITING;
            }
        } else if (state == EXIT) {
          //theOutput = "EXIT.";
        }
        return output;
    }
}

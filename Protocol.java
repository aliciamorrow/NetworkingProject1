import java.net.*;
import java.io.*;

public class Protocol {
    private static final int WAITING = 0;
    private static final int COMPUTATION = 1;

    private int state = WAITING;

    public String startComm() {
      //get and return IP address
      InetAddress ip;
      String theOutput = "";
      try {
        ip = InetAddress.getLocalHost();
        theOutput = "S> get connection from IP:" + ip.getHostAddress();
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

            if(operand.equals("terminate")) {
                output = -6;
            } else if(operand.equals("bye")) {
                output = -7;
            } else if(!operand.equals("add") && !operand.equals("subtract") && !operand.equals("multiply") && !operand.equals("bye") && !operand.equals("terminate")) {
                //incorrect operation command
                return -1;
            } else if(str2.length < 3) {
                //number of inputs is less than 2
                return -2;
            } else if(str2.length > 5) {
                //number of inputs is more than 4
                return -3;
            } else {
                //one or more of the inputs contain(s) non-number(s)
                try {
                  for(int i = 1; i < str2.length; i++) {
                    int num = Integer.parseInt(str2[i]);
                  }
                } catch(NumberFormatException nfe) {
                  return -4;
                }

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
                } else {
                    state = WAITING;
                }
            }
        }
        return output;
    }
}

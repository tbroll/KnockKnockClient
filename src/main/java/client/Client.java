package client;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.Scanner;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException{
        Scanner scan;
        BufferedReader input;
        PrintWriter output;
        String response;
        boolean HearAnother = true;
        String xmlExample = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<jokes><lead>turnip</lead><punchline>turnip the heat, I'm freezing</punchline></jokes>";

        String serverAddress = "localhost";
        int port = 9999;

     /*   Socket s = new Socket(serverAddress, port);

        output = new PrintWriter(s.getOutputStream(), true);

        input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
*/
        scan = new Scanner(System.in);

        System.out.println("Do you want to hear a knock knock joke? y/n");
        response = scan.nextLine();
  //      output.println(response);
        if(response.equals("n")){
            HearAnother = false;    
        }

        while(HearAnother){
            if(response.equals("n")){
                break;
            }
            try {
              String joke = generateKnockKnockJoke(xmlExample);
              System.out.println(joke);
              System.out.println("");
                System.out.println("Do you want to hear another knock knock joke? y/n");
            }
            catch(JDOMException jde){
                System.out.println(jde.getMessage());
            }

            response = scan.nextLine();
   //         output.println(response);
        }
        System.exit(0);
    }
    private static String generateKnockKnockJoke(String input) throws JDOMException, IOException {
       String lead = GetLead(input);
       String punchline = GetPunchLine(input);
        return "Knock Knock\n" +
               "Who's there?\n" +
                lead + "\n" +
                lead + " who?\n"+
                punchline;
    }
    private static String GetLead(String xmlDoc) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new StringReader(xmlDoc));
        String lead = doc.getRootElement().getChildText("lead");
        return lead;

    }
    private static String GetPunchLine(String xmlDoc) throws JDOMException, IOException{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new StringReader(xmlDoc));
        String punchline = doc.getRootElement().getChildText("punchline");
        return punchline;
    }
}

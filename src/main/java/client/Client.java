package com.gmail.toddbroll;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.util.Scanner;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException{
        Scanner scan;
        BufferedReader input;
        PrintWriter output;
        String response;
        boolean HearAnother = true;

        String serverAddress = "localhost";
        int port = 9999;

        Socket s = new Socket(serverAddress, port);

        output = new PrintWriter(s.getOutputStream(), true);

        input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));

        scan = new Scanner(System.in);

        System.out.println("Do you want to hear a knock knock joke? y/n");
        response = scan.nextLine();
        output.println(response);
        if(response.equals("n")){
            HearAnother = false;    
        }

        while(HearAnother){
            if(response.equals("n")){
                break;
            }

            System.out.println("Knock Knock");
            System.out.println("who's there");
            try {
                String gotLead = GetLead(input);
                String gotPunchLine = GetPunchLine(input);
                System.out.println(gotLead);
                System.out.println(gotLead + " who");
                System.out.println(gotPunchLine);
                System.out.println("Do you want to hear another knock knock joke? y/n");
            }
            catch(JDOMException jde){
                System.out.println(jde.getMessage());
            }

            response = scan.nextLine();
            output.println(response);
        }
        System.exit(0);
    }
    private static String GetLead(BufferedReader br) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(br);
        String lead = doc.getRootElement().getChildText("lead");
        return lead;

    }
    private static String GetPunchLine(BufferedReader br) throws JDOMException, IOException{
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(br);
        String punchline = doc.getRootElement().getChildText("punchline");
        return punchline;

    }
}

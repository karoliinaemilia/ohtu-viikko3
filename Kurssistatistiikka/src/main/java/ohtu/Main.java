package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Scanner;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        Scanner lukija = new Scanner(System.in);
        String studentNr = lukija.nextLine();
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        System.out.println("opiskelijanumero " + studentNr);
        System.out.println("");
        
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        int teht = 0;
        int aika = 0;
        
        for (Submission submission : subs) {
            System.out.println("  " + submission.toString());
            aika += submission.getHours();
            teht += submission.getDone();
        }
        
        System.out.println("");
        System.out.println("yhteensä: " + teht + " tehtävää " + aika + " tuntia");

    }
}
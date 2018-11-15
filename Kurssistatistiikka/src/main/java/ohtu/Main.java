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
        
        url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        bodyText = Request.Get(url).execute().returnContent().asString();
        
        Course[] courses = mapper.fromJson(bodyText, Course[].class);
        
        int teht = 0;
        int aika = 0;
        
        for (Course course: courses) {
            System.out.println(course.getFullName() + " " + course.getTerm() + " " + course.getYear());
            System.out.println("");
            for (Submission sub: subs) {
                if (sub.getCourse().equals(course.getName())) {
                    System.out.println("viikko " + sub.getWeek() + ":");
                    System.out.println("  tehtyjä tehtäviä " + sub.getDone() + "/" + course.getWeekAmount(sub.getWeek()) + " aikaa kului " + sub.getHours() + " tehdyt tehtävät: " + sub.tehtavat() );
                    aika += sub.getHours();
                    teht += sub.getDone();
                }
            }
            System.out.println("");
            System.out.println("yhteensä: " + teht + "/" + course.getAmount() + " tehtävää " + aika + " tuntia");
            System.out.println("");
            teht = 0;
            aika = 0;
        }
    }
}
package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        Scanner lukija = new Scanner(System.in);
        String studentNr = lukija.nextLine();
        if (args.length > 0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        System.out.println("opiskelijanumero " + studentNr);
        System.out.println("");

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);

        url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        bodyText = Request.Get(url).execute().returnContent().asString();

        Course[] courses = mapper.fromJson(bodyText, Course[].class);

        url = "https://studies.cs.helsinki.fi/courses/ohtu2018/stats";

        String statsResponse = Request.Get(url).execute().returnContent().asString();
        JsonParser parser = new JsonParser();
        JsonObject parsittuDataOhtu = parser.parse(statsResponse).getAsJsonObject();

        url = "https://studies.cs.helsinki.fi/courses/rails2018/stats";
        statsResponse = Request.Get(url).execute().returnContent().asString();
        JsonObject parsittuDataRails = parser.parse(statsResponse).getAsJsonObject();

        int teht = 0;
        int aika = 0;
        double palautukset = 0;
        double palautetutTehtavat = 0;
        double aikaaKaytetty = 0;

        for (Course course : courses) {
            if (course.getName().equals("docker-beta")) {
                continue;
            }
            System.out.println(course.getFullName() + " " + course.getTerm() + " " + course.getYear());
            System.out.println("");
            for (Submission sub : subs) {
                if (sub.getCourse().equals(course.getName())) {
                    System.out.println("viikko " + sub.getWeek() + ":");
                    System.out.println("  tehtyjä tehtäviä " + sub.getDone() + "/" + course.getWeekAmount(sub.getWeek()) + " aikaa kului " + sub.getHours() + " tehdyt tehtävät: " + sub.tehtavat());
                    aika += sub.getHours();
                    teht += sub.getDone();

                }
            }
            Map map = null;
            if (course.getName().equals("ohtu2018")) {
                for (int i = 1; i <= course.getWeek(); i++) {
                    map = mapper.fromJson(parsittuDataOhtu.get("" + i), Map.class);
                    palautukset += (double) map.get("students");
                    palautetutTehtavat += (double) map.get("exercise_total");
                    aikaaKaytetty += (double) map.get("hour_total");
                }
            } else {
                for (int i = 1; i <= course.getWeek(); i++) {
                    map = mapper.fromJson(parsittuDataRails.get("" + i), Map.class);
                    palautukset += (double) map.get("students");
                    palautetutTehtavat += (double) map.get("exercise_total");
                    aikaaKaytetty += (double) map.get("hour_total");
                }
            }

            System.out.println("");
            System.out.println("yhteensä: " + teht + "/" + course.getAmount() + " tehtävää " + aika + " tuntia");
            System.out.println("");
            System.out.println("kurssilla yhteensä " + palautukset + " palautusta, palautettuja tehtäviä " + palautetutTehtavat + " kpl, aikaa käytetty yhteensä " + aikaaKaytetty + " tuntia");
            System.out.println("");
            palautukset = 0;
            palautetutTehtavat = 0;
            aikaaKaytetty = 0;
            teht = 0;
            aika = 0;
        }
    }
}

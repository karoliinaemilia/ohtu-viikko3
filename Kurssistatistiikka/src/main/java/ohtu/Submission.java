package ohtu;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;
    private String course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public int getHours() {
        return hours;
    }

    public int[] getExercises() {
        return exercises;
    }
    
    public String tehtavat() {
        String palautus = "";
        for (int teht:exercises) {
            palautus += teht + ", ";
        }
        palautus = palautus.substring(0, palautus.length()-2);
        return palautus;
    }

    public String getCourse() {
        return course;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
    public int getDone() {
        return exercises.length;
    }

    @Override
    public String toString() {
        return course + ", viikko " + week + " tehtyjä tehtäviä yhteensä " + getDone() + " aikaa kului " + hours + " tehdyt tehtävät: " + tehtavat();
    }
    
}
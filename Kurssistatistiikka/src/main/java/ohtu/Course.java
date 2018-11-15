package ohtu;

import java.util.stream.IntStream;

public class Course {
    
    private String name;
    private String fullName;
    private String term;
    private String year;
    private int week;
    private int[] exercises;

    public int getWeek() {
        return week;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public int[] getExercises() {
        return exercises;
    }

    public String getTerm() {
        return term;
    }

    public String getYear() {
        return year;
    }
    
    
    public int getWeekAmount(int week) {
        return exercises[week];
    }

    public int getAmount() {
        return IntStream.of(exercises).sum();
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setWeek(int week) {
        this.week = week;
    }
    
    

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    
}

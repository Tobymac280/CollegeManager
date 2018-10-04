package edu.century.pa2;

public class Course implements Cloneable {
    // instance variables
    private static int idTracker = 0;
    private int id;
    private String subject;
    private int credits;
    private int courseNumber;
    private int courseSection;
    private String status;
    private String instructor;

    // no-arg constructor
    public Course(){
        id = 0;
        subject = null;
        credits = 0;
        courseNumber = 0;
        courseSection = 0;
        status = null;
        instructor = null;
    }

    // all-arg constructor
    public Course(String subject, int credits, int courseNumber, int courseSection, String status, String instructor){
        this.id = idTracker; // set the current id
        idTracker++; // add 1 to the id tracker
        this.subject = subject;
        this.credits = credits;
        this.courseNumber = courseNumber;
        this.courseSection = courseSection;
        this.status = status;
        this.instructor = instructor;
    }

    // getters and setters
    // id: getter and setter
    public int getId() {return id;}
    // subject: getter and setter
    public String getSubject() {return subject;}
    public void setSubject(String subject) {this.subject = subject;}
    // credits: getter and setter
    public int getCredits() {return credits;}
    public void setCredits(int credits) {this.credits = credits;}
    // course number: getter and setter
    public int getCourseNumber() {return courseNumber;}
    public void setCourseNumber(int courseNumber) {this.courseNumber = courseNumber;}
    //course section: getter and setter
    public int getCourseSection() {return courseSection;}
    public void setCourseSection(int courseSection) {this.courseSection = courseSection;}
    // status: getter and setter
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    // instructor: getter and setter
    public String getInstructor() {return instructor;}
    public void setInstructor(String instructor) {this.instructor = instructor;}

    @Override
    public String toString() {
        return "Id: " + id + ", Subject: " + subject + ", Credits: " + credits + ", Course Number: "
                + courseNumber + ", Course Section: " + courseSection + ", Status: " + status + ", Instructor: " + instructor;
    }

    /** Pass in another Course object and it will return true or false based on if they are equal */
    public boolean equals(Course otherCourse) {
        // make sure each field is the same
        if(otherCourse.getId() == id && otherCourse.getInstructor().equals(instructor) && otherCourse.getCourseNumber() == courseNumber && otherCourse.getCourseSection() == courseSection
                && otherCourse.getCredits() == credits && otherCourse.getStatus() == status && otherCourse.getSubject().equals(subject)){
            return true; // they are the same
        }
        return false; // they are different
    }

    @Override
    public Course clone() {
        return new Course(subject, credits, courseNumber, courseSection, status, instructor); // return an exact copy of the class
    }
}

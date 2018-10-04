/**
 * Author: Nik Fernandez
 * Date created: 09/24/2018
 * Description: Models a student
 */

package edu.century.pa2;

import java.util.GregorianCalendar;

import edu.century.pa2.collections.CourseCollection;

public class Student implements Cloneable {
    private String studentId;
    private String firstName;
    private String lastName;
    private GregorianCalendar birthdate;
    private String email;
    private CourseCollection courses;

    /** No-arg constructor */
    public Student(){
        // set everything to null
        studentId = null;
        firstName = null;
        lastName = null;
        birthdate = null;
        email = null;
        courses = new CourseCollection();
    }

    /** full name and birthday constructor */
    public Student(String fullName, GregorianCalendar birthdate){
        String[] names = fullName.split(" "); // split the name into two pieces
        this.firstName = names[0];
        this.lastName = names[1];

        this.birthdate = birthdate;

        // get the last two characters of the last name field
        int lastNameLength = lastName.length();
        String lastTwoCharacters = lastName.substring(lastNameLength-2, lastNameLength);
        // initialize the student id
        this.studentId = (firstName.substring(0, 2)) + this.birthdate.get(GregorianCalendar.YEAR) + lastTwoCharacters;

        this.email = firstName + "." + lastName + "@pa2.com";
        this.courses = new CourseCollection();
    }

    // ------------ getters & setters
    // student id
    public String getStudentId() {return this.studentId;}
    public void setStudentId(String id) {this.studentId = id;}
    // first name
    public String getFirstName() {return this.firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    // last name
    public String getLastName() {return this.lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    // birth date
    public GregorianCalendar getBirthday() {return birthdate;}
    public void setBirthday(GregorianCalendar birthdate) {this.birthdate = birthdate;}
    // email
    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}
    // courses
    public CourseCollection getCourses() {return courses;}
    public void setCourses(CourseCollection courses) {this.courses = courses;}
    // ------------ end of getters & setters

    /** returns a respective list of all of the items in this student object */
    @Override
    public String toString() {
        return "Id: " + studentId + ", Name: " + firstName + " " + lastName + ", Birthdate: " +
                birthdate.get(GregorianCalendar.MONTH) + "/" + birthdate.get(GregorianCalendar.DAY_OF_MONTH) + "/" + birthdate.get(GregorianCalendar.YEAR) + ", Email: " + email;
    }

    /** Add a course */
    public void addCourse(Course course){
        courses.add(course);
    }

    /** Checks if two objects of Student are the same.
     * Argument must be of type 'Student' */
    public boolean equals(Student otherStudent){
        // check names
        if(firstName.equals(otherStudent.getFirstName()) && lastName.equals(otherStudent.getLastName())) {
            if(birthdate.get(GregorianCalendar.YEAR) == otherStudent.getBirthday().get(GregorianCalendar.YEAR)
                    && birthdate.get(GregorianCalendar.DAY_OF_MONTH) == otherStudent.getBirthday().get(GregorianCalendar.DAY_OF_MONTH)
                    && birthdate.get(GregorianCalendar.MONTH) == otherStudent.getBirthday().get(GregorianCalendar.MONTH)) {
                return true; // they are the same in every respect
            }
        }

        return false; // they are not the same objects
    }

    /** Use this method to clone any instance of Student you'd like to.
     * Note: This method will not return a reference to the current instance you are trying to clone. It is entirely a copy. */
    @Override
    public Student clone() {
        String name = firstName + " " + lastName;
        return new Student(name, birthdate);
    }

    public boolean removeCourse(Course course) {
        // Create a course node that will be checked against the other course nodes in the course collection
        CourseNode node = new CourseNode(course);

        if(courses == null || courses.getHead() == null){ // the collection is empty
            return false;
        }

        // attempt to remove the course
        boolean deleted = courses.remove(course);
        if(deleted) {
            return true; // it was deleted
        }

        return false;
    }
}

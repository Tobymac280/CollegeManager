/**
 * Author: Nik Fernandez
 * Description: A program that allows a manager to do various options on a collection of students and a collection of
 * courses that each student has.
 * Due date: 10/04/2018 */

package edu.century.pa2;

import edu.century.pa2.collections.*;

import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // StudentCollection to store everything inside of
    private static StudentCollection collectionOfStudents = new StudentCollection();
    // separate scanners to make input easier
    private static Scanner intInput = new Scanner(System.in);
    private static Scanner stringInput = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        // Welcome message
        System.out.println("Welcome, admin! This is your program to control students, and classes they have.\n\n\n");

        do {
            printMenu();
            System.out.print("Please make a choice (integer from 1-9): ");
            try {
                choice = intInput.nextInt(); // might throw and exception
            }catch(InputMismatchException e){ // exception thrown and caught
                choice = 0; // set it to a random integer that will cause the switch statement to print the default case
            }

            // evaluate the user's choice
            switch(choice) {
                case 1:
                    System.out.println("Would you like to add or remove a student?");
                    System.out.print("Add/remove: ");
                    String answer = stringInput.nextLine();
                    if(answer.toLowerCase().equals("add")){
                        addAStudent(); // add a student
                    }else if(answer.toLowerCase().equals("remove")) {
                        removeAStudent(); // remove the student
                    }else {
                        System.out.println("This wasn't an option.");
                        // do nothing else, causing them to return to the main menu
                    }
                    break;
                case 2:
                    System.out.println("Would you like to add or remove a course to/from a student?");
                    System.out.print("Add/remove: ");
                    String courseAnswer = stringInput.nextLine();
                    if(courseAnswer.toLowerCase().equals("add")){
                        addACourse();
                    }else if(courseAnswer.toLowerCase().equals("remove")){
                        // TODO: Implement removeACourse()
                    }else{
                        System.out.println("This wasn't an option.");
                        // do nothing else, causing them to return to the main menu
                    }
                    break;
                case 3:
                    System.out.println("Needs to be implemented.");
                    break;
                case 4:
                    System.out.println("Needs to be implemented.");
                    break;
                case 5:
                    System.out.println("Needs to be implemented.");
                    break;
                case 6:
                    System.out.println("Needs to be implemented.");
                    break;
                case 7:
                    System.out.println("Needs to be implemented.");
                    break;
                case 8:
                    viewStudentsAndCourses();
                    break;
                case 9:
                    System.out.println("Program exiting...");
                    System.exit(0); // quit the program
                    break; // break is here just in case
                default:
                    System.out.println("This was not an option. Try again.");
                    break;
            }
            System.out.println("\n\n\n\n\n\n");
        }while(choice != 9);
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add/Remove a student.");
        System.out.println("2. Add/Remove a course to/from a selected student.");
        System.out.println("3. Search for a student by last name.");
        System.out.println("4. Search for a student by id.");
        System.out.println("5. Search for a course by name.");
        System.out.println("6. Search for a course by id.");
        System.out.println("7. Display a selected student along with the course he/she is registered for.");
        System.out.println("8. List all registered students, along with each course the students are registered for.");
        System.out.println("9. Exit.");
    }

    /** View each student with their courses */
    private static void viewStudentsAndCourses(){
        if(collectionOfStudents == null) {// is the collection empty?
            System.out.println("No students.");
            return; // return to the caller
        }

        Scanner scanner = new Scanner(System.in);
        for(int i = 0, length = collectionOfStudents.getSize(); i < length; i++){ // go through each student
            // access the student
            System.out.println(collectionOfStudents.get(i).getFirstName() + " " + collectionOfStudents.get(i).getLastName() + ":");

            // store the courses of the current student
            CourseCollection courses = collectionOfStudents.get(i).getCourses();
            if(courses.getHead() == null){ // is the collection empty?
                System.out.println("\t\tNo courses to display.");
                return; // return to the caller
            }
            for(CourseNode currentCourse = courses.getHead(); currentCourse != null; currentCourse.getNextCourse()){ // go through the courses
                System.out.println("\t\t" + currentCourse.toString()); // print the toString
                // TODO: Fix bug
                scanner.nextLine();
            }
        }
    }

    /** Add a new student */
    private static void addAStudent() {
        Student newStudent = retrieveStudentInformation();
        collectionOfStudents.add(newStudent); // add a new student
        System.out.println("Account successfully created!");
        System.out.println("Their student id is: " + newStudent.getStudentId());
    }

    /** Add a course to a selected student*/
    private static void addACourse(){
        System.out.print("Please enter the id of the student you wish to add the course to: ");
        String id = stringInput.nextLine();
        Student studentFound = collectionOfStudents.findById(id);
        if(studentFound == null){ // was the student not found?
            System.out.println("Invalid id.");
            // return to the caller
            return;
        }

        // create a new course
        Course newCourse = retrieveCourseInformation();
        if(newCourse == null){
            System.out.println("Course creation failed.");
            return; // return to the caller
        }

        // add the course to the list for the specified user
        collectionOfStudents.addCourseById(id, newCourse);

        System.out.println("Course added.");
    }

    /** Remove a student */
    private static void removeAStudent() {
        Student studentToRemove = retrieveStudentInformation();
        // remove the student
        boolean removed = collectionOfStudents.remove(studentToRemove);

        if(removed)
            System.out.println("Account removed.");
        else
            System.out.println("Account not removed.");
    }

    /** Grab the information for a student object */
    private static Student retrieveStudentInformation(){
        // name
        String firstName, lastName, name;
        // birthday
        int dayOfMonth, month, year;
        GregorianCalendar birthdate = new GregorianCalendar();

        // Get their name information
        System.out.print("Please enter the student's first name: ");
        firstName = stringInput.nextLine();
        System.out.print("Please enter the student's last name: ");
        lastName = stringInput.nextLine();
        name = (firstName.toLowerCase() + " " + lastName.toLowerCase());

        // Get their birthday information
        System.out.println("Please enter their birthday in a mm/dd/yyyy format (example: 01/23/2018 with forward slashes, for January 23rd 2018): ");
        // retrieve the birthday from the user
        String birthdayString = stringInput.nextLine();
        String[] birthdayData = birthdayString.split("/");
        // parse the data to integer types
        month = Integer.parseInt(birthdayData[0]);
        dayOfMonth = Integer.parseInt(birthdayData[1]);
        year = Integer.parseInt(birthdayData[2]);
        // set the fields for the birthdate object
        birthdate.set(GregorianCalendar.YEAR, year);
        birthdate.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth);
        birthdate.set(GregorianCalendar.MONTH, month);

        return new Student(name, birthdate); // return the student that was received
    }

    /** Grab the information for a course object */
    public static Course retrieveCourseInformation(){
        String subject, status, instructor;
        int credits, courseNumber, courseSection;

        // retrieve the information
        System.out.println("Enter the following information: ");
        System.out.print("Subject of the course: ");
        subject = stringInput.nextLine();
        System.out.print("Status of the course: ");
        status = stringInput.nextLine();
        System.out.print("Instructor's name: ");
        instructor = stringInput.nextLine();
        try{
            System.out.print("Course number (integer): ");
            courseNumber = intInput.nextInt();
            System.out.print("Credits for the course (integer): ");
            credits = intInput.nextInt();
            System.out.print("Course Section (integer): ");
            courseSection = intInput.nextInt();
        }catch(IllegalArgumentException e){
            System.out.println("You entered an invalid argument.");
            return null; // return to the caller with nothing
        }

        // return the course information
        return new Course(subject, credits, courseNumber, courseSection, status, instructor);
    }
}

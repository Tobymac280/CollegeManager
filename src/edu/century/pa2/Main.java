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
    private static Student currentStudentSelected;
    // separate scanners to make input easier
    private static Scanner intInput = new Scanner(System.in);
    private static Scanner stringInput = new Scanner(System.in);

    public static void main(String[] args) {
        // add a few student's and courses by default
        Course course1 = new Course("Physics 1", 5, 1021, 11, "Started", "Mike Johnson");
        Course course2 = new Course("Chemistry 1", 5, 1021, 11, "Started", "Steve Gregory");
        // create some students and add them to the student collection
        Student student1 = new Student("Mike Johnson", new GregorianCalendar(1988, 8, 15));
        Student student2 = new Student("Ken Johnson", new GregorianCalendar(1942, 3, 12));
        // add the students
        collectionOfStudents.add(student1);
        collectionOfStudents.add(student2);
        // add the courses to each student
        collectionOfStudents.addCourseById(student1.getStudentId(), course1);
        collectionOfStudents.addCourseById(student1.getStudentId(), course2);
        collectionOfStudents.addCourseById(student2.getStudentId(), course1);
        collectionOfStudents.addCourseById(student2.getStudentId(), course2);

        int choice;

        // Welcome message
        System.out.println("Welcome, admin! This is your program to control students, and classes they have.\n\n\n");

        do {
            printMenu();
            System.out.print("Please make a choice (integer from 1-4): ");
            try {
                choice = intInput.nextInt(); // might throw and exception
            }catch(InputMismatchException e){ // exception thrown and caught
                choice = 0; // set it to a random integer that will cause the switch statement to print the default case
            }

            // evaluate the user's choice
            switch(choice) {
                case 1: // ADD/REMOVE A STUDENT
                    addOrRemoveStudent();
                    break;
                case 2: // ALLOW A USER TO SELECT A STUDENT
                    System.out.println("Please enter the following information in order to select a student: ");
                    currentStudentSelected = searchForAStudent();
                    int subChoice;
                    do{
                        // print out the different menu for doing student operations
                        printStudentOpsMenu();
                        System.out.print("Please make a choice (integer from 1-4): ");
                        subChoice = intInput.nextInt();

                        switch(subChoice){
                            case 1: // ADD/REMOVE A COURSE
                                addOrRemoveCourse();
                                break;
                            case 2: // PRINT OUT THE USER INFORMATION AND THE COURSE INFORMATION
                                printUserAndCourseInfo();
                                break;
                            case 3: // SEARCH FOR A COURSE BY ID
                                searchForACourseById();
                                break;
                            case 4: // EXIT BACK TO MAIN MENU
                                System.out.println("Exiting.");
                                break;
                            default:
                                System.out.println("Not an option.");
                                break;
                        }
                        System.out.println("\n\n\n\n\n\n");
                    }while(subChoice != 4);
                    break;
                case 3: // LIST ALL REGISTERED STUDENTS WITH THEIR COURSES
                    listAllStudentsAndCourses();
                    break;
                case 4: // SEARCH FOR A STUDENT
                    Student studentFound = searchForAStudent();
                    // analyze what was received from the analysis above
                    if(studentFound != null){ // there was something returned
                        System.out.println("Student found!");
                        System.out.println("Student information: " + studentFound.toString());
                    }else{ // no student was found
                        System.out.println("No student was found that matched these credentials.");
                    }
                    break;
                case 5: // EXIT THE PROGRAM
                    System.out.println("Program exiting...");
                    System.exit(0); // quit the program
                    break; // break is here just in case
                default:
                    System.out.println("This was not an option. Try again.");
                    break;
            }
            System.out.println("\n\n\n\n\n\n");
        }while(choice != 7);
    }

    // ------------- methods for all students -------------
    /** Prints the main menu */
    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add/Remove a student.");
        System.out.println("2. Select a student to perform operations on");
        System.out.println("3. List all registered students, along with each course the students are registered for.");
        System.out.println("4. Search for a student.");
        System.out.println("5. Exit.");
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
    /** Add or remove a student from the collection of students */
    private static void addOrRemoveStudent(){
        System.out.println("Would you like to add or remove a student?");
        System.out.print("Add/remove: ");
        String answer = stringInput.nextLine();
        if(answer.toLowerCase().equals("add")){
            // Add the student
            Student newStudent = retrieveStudentInformation();
            collectionOfStudents.add(newStudent); // add a new student
            System.out.println("Account successfully created!");
            System.out.println("Their student id is: " + newStudent.getStudentId());
        }else if(answer.toLowerCase().equals("remove")) {
            // remove the student
            Student studentToRemove = retrieveStudentInformation();
            // remove the student
            boolean removed = collectionOfStudents.remove(studentToRemove);

            if(removed)
                System.out.println("Account removed.");
            else
                System.out.println("Account not removed.");
        }else {
            System.out.println("This wasn't an option.");
            // do nothing else, causing them to return to the main menu
        }
    }
    /** Searches for a student and returns the student that is found */
    private static Student searchForAStudent() {
        System.out.println("Would you like to search by (integer): \n1. ID\n2. First Name\n3. Last Name");
        // get the user's choice
        int userChoice;
        try{
            userChoice = intInput.nextInt();
        }catch (IllegalArgumentException e){
            userChoice = 0; // invalid choice, according to the switch statement
        }

        // object that will hold the Student that was found
        Student studentFound;

        // Analyze the user's choice
        switch(userChoice){
            case 1:
                // get the user input
                System.out.print("Please enter the id: ");
                String id = stringInput.nextLine();
                studentFound = collectionOfStudents.search(1, id);
                break;
            case 2:
                // get the user input
                System.out.print("Please enter the first name: ");
                String firstName = stringInput.nextLine();
                studentFound = collectionOfStudents.search(2, firstName);
                break;
            case 3:
                // get the user input
                System.out.print("Please enter the last name: ");
                String lastName = stringInput.nextLine();
                studentFound = collectionOfStudents.search(3, lastName);
                break;
            default:
                System.out.println("Not an option.");
                return null; // return to the caller
        }

        if(studentFound == null){
            return null;
        }

        return studentFound;
    }
    /** Print out all of the students and their respective courses */
    private static void listAllStudentsAndCourses(){
        if(collectionOfStudents == null || collectionOfStudents.getSize() == 0) {// is the collection empty?
            System.out.println("No students.");
            return; // return to the caller
        }
        for(int i = 0, length = collectionOfStudents.getSize(); i < length; i++){ // go through each student
            // access the student
            System.out.println(collectionOfStudents.get(i).getFirstName() + " " + collectionOfStudents.get(i).getLastName() + ":");
            // store the courses of the current student
            CourseCollection courses = collectionOfStudents.get(i).getCourses();
            if(courses.getHead() == null){ // is the collection empty?
                System.out.println("\t\tNo courses to display.");
            }
            for(CourseNode currentCourse = courses.getHead(); currentCourse != null; currentCourse = currentCourse.getNextCourse()){ // go through the courses
                System.out.println("\t\t" + currentCourse.toString()); // print the toString
            }
        }
    }

    // ------------- methods for selected student -------------
    /** Lists options for operations you can perform on a specific student */
    private static void printStudentOpsMenu(){
        System.out.println("Menu for " + currentStudentSelected.getStudentId() + ":");
        System.out.println("1. Add/Remove a course to/from a student.");
        System.out.println("2. Display student information, and the courses they have.");
        System.out.println("3. Search for a course by id.");
        System.out.println("4. Exit.");
    }
    /** Block of code that will add or remove a course */
    private static void addOrRemoveCourse(){
        System.out.println("Would you like to add or remove a course to/from a student?");
        System.out.print("Add/remove: ");
        String courseAnswer = stringInput.nextLine();
        if(courseAnswer.toLowerCase().equals("add")){
            if(currentStudentSelected == null){
                System.out.println("No student was found!");
                return; // return to the caller -- menu
            }

            // create a new course
            Course newCourse = retrieveCourseInformation();
            if(newCourse == null){
                System.out.println("Course creation failed.");
                return; // return to the caller
            }

            // add the course to the list for the specified user
            collectionOfStudents.addCourseById(currentStudentSelected.getStudentId(), newCourse);

            System.out.println("Course added.");
            System.out.println("Course id: " + newCourse.getId());
        }else if(courseAnswer.toLowerCase().equals("remove")){ // REMOVE A COURSE
            if(currentStudentSelected == null){
                System.out.println("No student found.");
                return; // return to the caller
            }

            System.out.print("Now, please enter the id for the course that you would like to remove (integer): ");
            int courseId = intInput.nextInt();

            // remove a course by passing in the id and
            boolean courseRemoved = collectionOfStudents.removeCourse(currentStudentSelected.getStudentId(), courseId);

            if(courseRemoved){
                System.out.println("Course removed, successfully.");
            }else{
                System.out.println("Course remove failed.");
            }
        }else {
            System.out.println("This wasn't an option.");
            // do nothing else, causing them to return to the main menu
        }
    }
    /** Grab the information for a course object */
    private static Course retrieveCourseInformation(){
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
    /** Simply print out the user and courses info */
    private static void printUserAndCourseInfo(){
        System.out.println("Student information: " + currentStudentSelected.toString());
        if (currentStudentSelected.getCourses().getHead() != null)
            System.out.println("Course information: ");
        else
            System.out.println("No courses to show.");
        for(CourseNode cursor = currentStudentSelected.getCourses().getHead(); cursor != null; cursor = cursor.getNextCourse()){
            System.out.println("\t\t\t" + cursor.toString());
        }
    }
    /** Search for a course by id */
    private static void searchForACourseById(){
        System.out.print("Please enter an id to search by: ");
        int idToSearch = intInput.nextInt();
        Course course = currentStudentSelected.searchForCourseById(idToSearch);
        if(course == null){ // no course found
            System.out.println("No course was found.");
        }else{
            System.out.println("Course found!");
            // print out the course's information
            System.out.println("Information: " + course.toString());
        }
    }
}

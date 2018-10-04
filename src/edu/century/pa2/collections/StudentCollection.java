package edu.century.pa2.collections;

import edu.century.pa2.*;

public class StudentCollection implements Cloneable {
    private Student[] students;
    private int numberOfStudents;

    /** Default constructor */
    public StudentCollection() {
        final int INITIAL_SIZE = 10;
        students = new Student[INITIAL_SIZE]; // initialize the array to a default value
        this.numberOfStudents = 0;
    }

    /** Create a new StudentCollection, with an initial size. Size must be greater than or equal to zero */
    public StudentCollection(int initialSize) throws IllegalArgumentException {
        if(initialSize < 0)
            throw new IllegalArgumentException("Cannot be less than zero: " + initialSize);
        students = new Student[initialSize];
        this.numberOfStudents = 0;
    }

    /** Add a student to the list */
    public void add(Student student) {
        if(numberOfStudents == students.length){
            ensureCapacity(numberOfStudents*2 + 1); // double the size
        }
        // add the item to the last open spot in the array
        students[numberOfStudents] = student;
        // add 1 to the student count
        numberOfStudents++;
    }

    /** Add a course to the collection, using an id of a student */
    public void addCourseById(String studentId, Course newCourse){
        // go to the place in the array that this course needs to be added to, add a new course
        students[findIndexOfStudent(studentId)].addCourse(newCourse);
    }

    /** Helper method that will search for all of the students and finds the index of student, using their id*/
    private int findIndexOfStudent(String studentId){
        for(int i = 0, length = numberOfStudents; i < length; i++){
            if(students[i].getStudentId().equals(studentId)){ // look at each student's id -- is it equal to the id passed in?
                return i; // return the index
            }
        }
        return -1; // returns a negative index to stand for nothing being found
    }

    /** Pass in the index you wish to retrieve from the user.
     * Index starts at 0. */
    public Student get(int index){
        if(index >= numberOfStudents){ // value exceeds the maximum
            return null; // can't do anything
        }
        return students[index];
    }

    /** Helper method that makes the array larger, and copies over all of the old data */
    private void ensureCapacity(int sizeOfArray) {
        Student[] newStudentsList = new Student[sizeOfArray];

        if(students.length < sizeOfArray) {
            System.arraycopy(students, 0, newStudentsList, 0, numberOfStudents);
            students = newStudentsList; // overwrite the array
        }
    }

    /** Add an entire list to the end of this list */
    public void addAll(StudentCollection otherCollection) {
        ensureCapacity(numberOfStudents + otherCollection.numberOfStudents);
        System.arraycopy(otherCollection.students, 0, students, numberOfStudents, otherCollection.numberOfStudents);
        // add to the sum of the number of students
        numberOfStudents += otherCollection.numberOfStudents;
    }

    public void addMultiple(Student... studentsToAdd) {
        if(numberOfStudents + studentsToAdd.length > students.length){
            ensureCapacity((numberOfStudents + studentsToAdd.length)*2); // double the size of the sum of the two
        }

        System.arraycopy(studentsToAdd, 0, students, numberOfStudents, studentsToAdd.length);
        numberOfStudents += studentsToAdd.length;
    }

    @Override
    public StudentCollection clone() {
        StudentCollection collectionCopy;

        try {
            collectionCopy = (StudentCollection)super.clone();
        }catch(CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported.");
        }

        collectionCopy.students = students.clone();

        return collectionCopy; // return the copy to the caller
    }

    /** This method counts the occurrence of the id of a student appearing in the list */
    public int countOccurrencesOfId(Student target) {
        int count = 0;

        for(int i = 0; i < numberOfStudents; i++){
            if(target.equals(students[i]));
            count++; // add 1 to the count
        }

        return count; // return the count to the caller
    }

    /** Retrieve the size of the list */
    public int getCapacity() {return students.length;}

    /** Remove a student by their id
     * Returns the success of the operation*/
    public boolean remove(Student target) {
        for(int i = 0; i < numberOfStudents; i++){
            if(target.equals(students[i])) {
                // replace the item with the last element in the list
                students[i] = students[numberOfStudents-1];
                students[numberOfStudents-1] = null; // set the last space as empty
                numberOfStudents--; // take 1 from the count of students
                return true; // it worked -- student was removed
            }
        }
        return false; // nothing removed
    }

    /** Find and return a user by their id */
    public Student findById(String studentId){
        for(Student student : students){
            if(student.getStudentId().equals(studentId)){
                return student;
            }
        }

        return null; // nothing was found
    }

    /** Returns the number of students */
    public int getSize(){
        return numberOfStudents;
    }

    /** Returns the amount of student objects in this collection */
    public int size() {return numberOfStudents;}

    public String toString() {
        String stringToReturn = "";

        for(int i = 0; i < numberOfStudents; i++){
            stringToReturn += students[i] + "\n";
        }

        return stringToReturn;
    }

    /** Sizes the array down to the minimum size */
    public void trimToSize() {
        Student[] trimmedArray;

        if(students.length != numberOfStudents){
            trimmedArray = new Student[numberOfStudents];
            System.arraycopy(students, 0, trimmedArray, 0, numberOfStudents);
            students = trimmedArray;
        }
    }

    /** Search for a student by their 1. ID, 2. First Name, 3. Last Name.
     * Pass in the type, respectively to the order above, and the value of the item to search for */
    public Student search(int type, String value) {
        if(numberOfStudents == 0)
            return null; // return nothing, there are no students to search through

        switch(type){
            case 1: // id
                for(Student student: students){
                    if(student.getStudentId().equals(value)) { // are they equal?
                        return student;
                    }
                }
                break;
            case 2: // first name
                for(Student student: students){
                    if(student.getFirstName().equals(value)) { // are they equal?
                        return student;
                    }
                }
                break;
            case 3: // last name
                for(Student student: students){
                    if(student.getLastName().equals(value)) { // are they equal?
                        return student;
                    }
                }
                break;
            default:
                return null; // return nothing
        }

        return null;
    }

    /** Remove a course, by passing in the student and the course details */
    public boolean removeCourse(String studentId, Course course) {
        // search for the student that matches this id
        Student studentFound = search(1, studentId); // search by id
        if(studentFound == null){ // no student was found
            return false;
        }
        // find where the student is in the collection
        int indexOfStudent = findIndexOfStudent(studentFound.getStudentId());
        // remove the course
        boolean removed = students[indexOfStudent].removeCourse(course);
        if(removed) { // it was removed
            return true;
        }
        return false;
    }
} // end of StudentCollection class

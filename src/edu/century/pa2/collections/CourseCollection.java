package edu.century.pa2.collections;

import edu.century.pa2.*;

public class CourseCollection {
    private CourseNode head;
    private CourseNode tail;
    private int numberOfCourses;

    /** No-argument constructor */
    public CourseCollection() {
        this.head = null;
        this.tail = null;
        this.numberOfCourses = 0;
    }

    public CourseNode getHead(){return head;}

    public int getNumberOfCourses() {return numberOfCourses;}

    /** Add a course to the list */
    public void add(Course course) { // This method adds the new course to the head of the list
        CourseNode node = new CourseNode(course);
        // set this node's nextCourse as the first item in the list
        node.setNextCourse(head);
        // set the node as the head of the list
        head = node;

        if(numberOfCourses == 0) { // are there no items in the list?
            tail = head; // the head and tail are the same thing
        }

        numberOfCourses++; // add 1 to the number of nodes in the list
    }

    /** Remove a course from the list */
    public void remove(Course target) {
        if(numberOfCourses == 0){ // there's nothing in the entire list
            return; // go back to the caller -- can't do anything
        }

        CourseNode node = new CourseNode(target);

        for(CourseNode cursor = head, precursor = null; cursor!=null; precursor = cursor, cursor = cursor.getNextCourse()){
            // check for if the first element is the head
            if(target.equals(cursor) && cursor == head){
                if(numberOfCourses == 1){
                    head = tail = null; // empty the head and the tail
                }else{
                    head = head.getNextCourse(); // erase the first element, and move the last element to the head
                }
                numberOfCourses--; // take 1 away from the number of courses
            }else if(target.equals(cursor) && cursor == tail){ // we are trying to remove the tail of the list
                if(numberOfCourses == 1){
                    head = tail = null; // empty head and tail
                }else {
                    precursor.setNextCourse(null); // remove this course from the link of its previous node
                    tail = precursor;
                }
                numberOfCourses--;
            }else if(target.equals(target)) { // found the item, but it's not at the beginning or end of the list
                precursor.setNextCourse(cursor.getNextCourse());
                numberOfCourses--;
            }
        }
    } // end of remove()

    /** Returns a list of the items */
    @Override
    public String toString() {
        String stringToReturn = "";

        for(CourseNode node = head; node!= null; node = node.getNextCourse()){
            stringToReturn += node.toString() + "\n";
        }
        return stringToReturn;
    }
}

package edu.century.pa2;

public class CourseNode extends Course {
    private Course course;
    private CourseNode nextCourse;

    public CourseNode(Course course) {
        this.course = course;
        this.nextCourse = null;
    }

    // Getters & setters
    public Course getCourse() {return course;}
    public void setCourse(Course course) {this.course = course;}
    public CourseNode getNextCourse() {return nextCourse;}
    public void setNextCourse(CourseNode nextCourse) {this.nextCourse = nextCourse;}

    /** Call this method to erase a node that's in between this node and this node's next node next node
     * Example: We want to delete the middle node in the Linked List "Node1 Node2 Node3". This method will just leave "Node1 Node3". */
    public void removeNodeAfter() {
        nextCourse = nextCourse.nextCourse;
    }

    @Override
    public String toString() {
        return "Id: " + course.getId() + ", Subject: " + course.getSubject() + ", Credits: " + course.getCredits() + ", Course Number: "
                + course.getCourseNumber() + ", Course Section: " + course.getCourseSection() + ", Status: " + course.getStatus() + ", Instructor: " + course.getInstructor();
    }
}

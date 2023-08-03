package application.model;

import java.util.List;

public class CourseWithInstructorsDTO {
    private Course course;
    private List<Instructor> instructors;

    public CourseWithInstructorsDTO(Course course, List<Instructor> instructors) {
        this.course = course;
        this.instructors = instructors;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }
}

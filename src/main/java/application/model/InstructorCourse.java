package application.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "instructor_courses")
public class InstructorCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

    package com.mycompany.educationsys.entity;

    import com.mycompany.educationsys.entity.base.BaseEntity;

    import jakarta.persistence.Entity;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;

    import java.util.Objects;

    @Entity
    public class Enrollment extends BaseEntity {

        @ManyToOne(optional = false)
        @JoinColumn(name = "course_id")
        private Course course;

        @ManyToOne(optional = false)
        @JoinColumn(name = "student_id")
        private User student;

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public User getStudent() {
            return student;
        }

        public void setStudent(User student) {
            this.student = student;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Enrollment)) return false;

            Enrollment that = (Enrollment) o;

            return Objects.equals(course, that.course) &&
                    Objects.equals(student, that.student);
        }

        @Override
        public int hashCode() {
            return Objects.hash(course, student);
        }
    }
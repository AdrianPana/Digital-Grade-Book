import java.util.ArrayList;

public class FullCourse extends Course{

    public FullCourse(CourseBuilder builder) {
        super(builder);
    }

    static class FullCourseBuilder extends Course.CourseBuilder {
        FullCourseBuilder(String name, Teacher professor) {
            super(name, professor);
        }

        public Course build() {
            return new FullCourse(this);
        }
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> grads = new ArrayList<>();

        for (Grade grade : grades) {
            if (grade.getPartialScore() >= 3 && grade.getExamScore() >= 2)
                grads.add(grade.getStudent());
        }

        return grads;
    }
}

import java.util.ArrayList;

public class PartialCourse extends Course{

    protected PartialCourse(CourseBuilder builder) {
        super(builder);
    }

    static class PartialCourseBuilder extends Course.CourseBuilder {

        PartialCourseBuilder(String name, Teacher professor) {
            super(name, professor);
        }

        public Course build(){
            return new PartialCourse(this);
        }
    }


    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> grads = new ArrayList<>();

        for (Grade grade : grades) {
            if (grade.getTotal() >= 5)
                grads.add(grade.getStudent());
        }

        return grads;
    }
}

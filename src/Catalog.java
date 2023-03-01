import java.util.LinkedList;

public class Catalog {

    private static Catalog catalog = null;
    private LinkedList<Course> courses;

    public ScoreVisitor scoreVisitor;

    private Catalog() {
        courses = new LinkedList<Course>();
        scoreVisitor = new ScoreVisitor();
    }

    public static Catalog getInstance() {
        if (catalog == null)
            catalog = new Catalog();

        return catalog;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public Course getCourse(String name) {
        Course found = null;

        for (Course course : getCourses()) {
            if (course.getName().equals(name))
                found = course;
        }

        return found;
    }

    public Student getStudent(String firstName, String lastName) {
        Student student = null;

        for (Course course : courses) {
            student = course.getStudent(firstName, lastName);
            if (student != null)
                break;
        }

        return student;
    }

    public Assistant getAssistant(String firstName, String lastName) {
        Assistant assistant = null;

        for (Course course : courses) {
            assistant = course.getAssistant(firstName, lastName);
            if (assistant != null)
                break;
        }

        return assistant;
    }

    public Teacher getTeacher(String firstName, String lastName) {
        Teacher teacher = null;

        for (Course course : courses) {
            if (course.getProfessor().toString().equals(firstName + " " + lastName))
                teacher = course.getProfessor();
            if (teacher != null)
                break;
        }

        return teacher;
    }

    public Parent getParent(String firstName, String lastName) {
        Parent parent = null;

        for (Course course : courses) {
            for (Student student : course.getAllStudents()) {
                if (student.getMother() != null && student.getMother().toString().equals(firstName + " " + lastName))
                    parent = student.getMother();
                if (student.getFather() != null && student.getFather().toString().equals(firstName + " " + lastName))
                    parent = student.getFather();
            }
            if (parent != null)
                break;
        }

        return parent;
    }

    public LinkedList<Course> getCourses() {
        return courses;
    }
}

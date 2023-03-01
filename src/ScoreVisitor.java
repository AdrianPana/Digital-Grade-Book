import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class ScoreVisitor implements Visitor {

    static class Tuple<A, B, C> {

        public A a;
        public B b;
        public C c;

        Tuple(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public HashMap<Teacher, LinkedList<Tuple<Student, String, Double>>> examScores;
    public HashMap<Assistant, LinkedList<Tuple<Student, String, Double>>> partialScores;

    public ScoreVisitor() {
        examScores = new HashMap<Teacher, LinkedList<Tuple<Student, String, Double>>>();
        partialScores = new HashMap<Assistant, LinkedList<Tuple<Student, String, Double>>>();
    }

    void addScore(Teacher teacher, Student student, Course course, Double score) {
        Tuple<Student,String,Double> tuple = new Tuple<Student,String,Double>(student, course.getName(), score);

        addTeacherScore(teacher, tuple);
    }

    void addScore(Assistant ass, Student student, Course course, Double score) {
        Tuple<Student,String,Double> tuple = new Tuple<Student,String,Double>(student, course.getName(), score);

        addAssistantScore(ass, tuple);
    }

    void addTeacherScore(Teacher teacher, Tuple<Student,String,Double> tuple) {
        if (examScores.get(teacher) == null)
            examScores.put(teacher, new LinkedList<Tuple<Student, String, Double>>());

        examScores.get(teacher).add(tuple);
    }
    void addAssistantScore(Assistant assistant, Tuple<Student,String,Double> tuple) {
        if (partialScores.get(assistant) == null)
            partialScores.put(assistant, new LinkedList<Tuple<Student, String, Double>>());

        partialScores.get(assistant).add(tuple);
    }

    public Vector<String> getNotValidated(User user) {
        if (user instanceof Teacher)
            return getNotValidated((Teacher) user);
        return getNotValidated((Assistant) user);
    }
    public Vector<String> getNotValidated(Teacher teacher) {
        Vector<String> scores = new Vector<String>();

        for (Tuple<Student, String, Double> tuple : examScores.get(teacher)) {
            scores.add(tuple.b + " " + tuple.a + " " + tuple.c);
        }

        return scores;
    }
    public Vector<String> getNotValidated(Assistant ass) {
        Vector<String> scores = new Vector<String>();

        for (Tuple<Student, String, Double> tuple : partialScores.get(ass)) {
            scores.add(tuple.b + ": " + tuple.a + " - " + tuple.c);
        }

        return scores;
    }

    @Override
    public void visit(Assistant assistant) {
        for (Tuple<Student,String,Double> tuple : partialScores.get(assistant)) {

            // verifica daca proful a notat aceeasi chestie -> make a grade
            boolean teacherGraded = false;
            Grade newGrade = new Grade(tuple.a, tuple.b);
            newGrade.setPartialScore(tuple.c);

            // verifica daca grade ul exista in catalog
            Catalog catalog = Catalog.getInstance();
            Course gradedCourse = null;
            for (Course course : catalog.getCourses()) {
                if (course.getName().equals(tuple.b)) {
                    gradedCourse = course;
                    break;
                }

            }
            //modifica daca exista
            if (gradedCourse != null) {
                boolean foundGrade = false;
                LinkedList<Grade> grades = gradedCourse.getGrades();
                for (Grade grade : grades) {
                    if (grade.getStudent().equals(newGrade.getStudent())) {
                        foundGrade = true;
                        grades.get(grades.indexOf(grade)).setPartialScore(newGrade.getPartialScore());
                        tuple.a.notifyObservers(grade);
                    }
                }
                // adauga daca nu exista
                if (!foundGrade) {
                    gradedCourse.addGrade(newGrade);
                    tuple.a.notifyObservers(newGrade);
                }
            }
        }
        partialScores.clear();
    }

    @Override
    public void visit(Teacher teacher) {

        for (Tuple<Student,String,Double> tuple : examScores.get(teacher)) {

            // verifica daca asistentul a notat aceeasi chestie -> make a grade
            boolean assGraded = false;
            Grade newGrade = new Grade(tuple.a, tuple.b);
            newGrade.setExamScore(tuple.c);

            // verifica daca grade ul exista in catalog
            Catalog catalog = Catalog.getInstance();
            Course gradedCourse = null;
            for (Course course : catalog.getCourses()) {
                if (course.getName().equals(tuple.b)) {
                    gradedCourse = course;
                    break;
                }

            }
            //modifica daca exista
            if (gradedCourse != null) {
                boolean foundGrade = false;
                LinkedList<Grade> grades = gradedCourse.getGrades();
                for (Grade grade : grades) {
                    if (grade.getStudent().equals(newGrade.getStudent())) {
                        foundGrade = true;
                        grades.get(grades.indexOf(grade)).setExamScore(newGrade.getExamScore());
                        tuple.a.notifyObservers(grade);
                    }
                }
                // adauga daca nu exista
                if (!foundGrade) {
                    gradedCourse.addGrade(newGrade);
                    tuple.a.notifyObservers(newGrade);
                }

            }

        }
        examScores.clear();
    }
}

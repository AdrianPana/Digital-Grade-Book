import java.util.LinkedList;

public class BestExamScore implements Strategy {
    @Override
    public Student getBestStudent(LinkedList<Grade> grades) {

        Student s = grades.peek().getStudent();
        double score = 0;

        for (Grade grade : grades) {
            if (grade.getExamScore() > score)
            {
                score = grade.getExamScore();
                s = grade.getStudent();
            }
        }

        return s;
    }
}

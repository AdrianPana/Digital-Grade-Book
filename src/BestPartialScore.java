import java.util.LinkedList;

public class BestPartialScore implements Strategy{

    @Override
    public Student getBestStudent(LinkedList<Grade> grades) {

        Student s = grades.peek().getStudent();
        double score = 0;

        for (Grade grade : grades) {
            if (grade.getPartialScore() > score)
            {
                score = grade.getPartialScore();
                s = grade.getStudent();
            }
        }

        return s;
    }
}

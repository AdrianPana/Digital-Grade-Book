import java.util.LinkedList;

public class BestTotalScore implements Strategy {
    @Override
    public Student getBestStudent(LinkedList<Grade> grades) {

        Student s = grades.peek().getStudent();
        double score = 0;

        for (Grade grade : grades) {
            if (grade.getTotal() > score)
            {
                score = grade.getTotal();
                s = grade.getStudent();
            }
        }

        return s;
    }
}

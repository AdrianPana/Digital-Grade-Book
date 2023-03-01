import java.util.LinkedList;

public interface Strategy {

    public Student getBestStudent(LinkedList<Grade> grades);
}

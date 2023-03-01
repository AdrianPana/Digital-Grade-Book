import java.util.Comparator;
import java.util.TreeSet;

public class Group<Student> extends TreeSet<Student> {

    private Assistant assistant;
    private String ID;
    Comparator<Student> comp;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super(comp);
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
    }

    public Group(String ID, Assistant assistant) {
        this(ID, assistant, new Comparator<Student>() {
            @Override
            public int compare(Student student, Student t1) {
                return student.toString().compareTo(t1.toString());
            }
        });
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }


}

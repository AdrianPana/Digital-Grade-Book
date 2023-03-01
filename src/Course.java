import java.util.*;

public abstract class Course {

    public enum TeacherStrat {
        PARTIAL,
        EXAM,
        TOTAL
    }
    private String name;
    private Teacher professor;
    private HashSet<Assistant> assistants;
    protected LinkedList<Grade> grades;
    private HashMap<String, Group<Student>> groups;
    private int credits;
    private TeacherStrat strat;

    public Snapshot snapshot;

    protected Course(CourseBuilder builder) {
        this.name = builder.name;
        this.professor = builder.professor;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
        this.credits = builder.credits;
        this.strat = builder.strat;
    }

    static abstract class CourseBuilder {
        private final String name;
        private final Teacher professor;
        private HashSet<Assistant> assistants;
        private LinkedList<Grade> grades;
        private HashMap<String, Group<Student>> groups;
        private int credits;
        private TeacherStrat strat;

        CourseBuilder(String name, Teacher professor) {
            this.name = name;
            this.professor = professor;
        }

        public CourseBuilder assistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder grades(LinkedList<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder groups(HashMap<String, Group<Student>> groups) {
            this.groups = groups;
            return this;
        }

        public CourseBuilder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public CourseBuilder strat(TeacherStrat strat) {
            this.strat = strat;
            return this;
        }

        public abstract Course build();

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfessor(Teacher professor) {
        this.professor = professor;
    }

    public Teacher getProfessor() {
        return professor;
    }

    public void setAssistants(HashSet<Assistant> assistants) {
        this.assistants = assistants;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public Assistant getAssistant(String firstName, String lastName) {
        String name = firstName + " " + lastName;
        Assistant found = null;

        for (Assistant assistant : getAssistants()) {
            if (assistant.toString().equals(name))
                found = assistant;
        }

        return found;
    }

    public void setGrades(LinkedList<Grade> grades) {
        this.grades = grades;
    }

    public LinkedList<Grade> getGrades() {
        return grades;
    }

    public void setGroups(HashMap<String, Group<Student>> groups) {
        this.groups = groups;
    }

    public HashMap<String, Group<Student>> getGroups() {
        return groups;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public void addAssistant(String ID, Assistant assistant) {
        groups.get(ID).setAssistant(assistant);
        if (!assistants.contains(assistant)) {
            assistants.add(assistant);
        }
    }

    public void addStudent(String ID, Student student) {
        groups.get(ID).add(student);
    }

    public Student getStudent(String firstName, String lastName) {
        String name = firstName + " " + lastName;
        Student found = null;

        for (Group<Student> group : groups.values()) {
            for (Student student : group) {
                if (student.toString().equals(name))
                    found = student;
            }
        }

        return found;
    }

    public void addGroup(Group group) {
        groups.put(group.getID(), group);

        boolean assistantNotAdded = true;

        for (Assistant ass : assistants)
            if (ass.toString().equals(group.getAssistant().toString()))
                assistantNotAdded = false;

        if (assistantNotAdded)
        assistants.add(group.getAssistant());
    }

    public void addGroup(String ID, Assistant assistant) {
        addGroup(ID, assistant, null);
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        Group<Student> group = new Group<>(ID, assistant, comp);
        addGroup(group);
    }

    public Grade getGrade(Student student) {
        for (Grade grade: grades) {
            if (grade.getStudent().equals(student))
                return grade;
        }
        return null;
    }

    public void addGrade(Grade grade) {

        int i = 0;
        while (i < grades.size() && (grade.compareTo(grades.get(i)) < 0 ||
                (grade.compareTo(grades.get(i)) == 0 &&
                        grade.getStudent().toString().compareTo(grades.get(i).getStudent().toString()) > 0))) {
            i++;
        }

        grades.add(i,grade);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Group<Student> group : groups.values())
            students.addAll(group);
        return students;
    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> studentGrades = new HashMap<>();

        for (Grade grade: grades) {
            studentGrades.put(grade.getStudent(), grade);
        }

        return studentGrades;
    }
    public abstract ArrayList<Student> getGraduatedStudents();

    public Student getBestStudent() {

        Strategy strategy;

        switch (strat) {
            case PARTIAL:
                strategy = new BestPartialScore();
                break;

            case EXAM:
                strategy = new BestExamScore();
                break;

            default:
                strategy = new BestTotalScore();
                break;
        }

        return strategy.getBestStudent(grades);
    }

    private class Snapshot {
        private LinkedList<Grade> savedGrades;

        Snapshot(LinkedList<Grade> grades) {
            savedGrades = new LinkedList<Grade>();

            for (Grade grade : grades) {
                try {
                    savedGrades.add((Grade) grade.clone());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public LinkedList<Grade> getSavedGrades() {
            return savedGrades;
        }
    }

    public void makeBackup() {
        snapshot = new Snapshot(grades);
    }

    public void undo() {
        grades = snapshot.getSavedGrades();
    }

    @Override
    public String toString() {
        return getName();
    }
}

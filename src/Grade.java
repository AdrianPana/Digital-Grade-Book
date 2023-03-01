import java.util.LinkedList;

public class Grade implements Comparable, Cloneable {

    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Grade(Student student, String course) {
        this.student = student;
        this.course = course;
    }

    public void setPartialScore(Double score) {
        partialScore = score;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setExamScore(Double score) {
        examScore = score;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public boolean equals(Object obj) {

        if (obj == null)
            throw new NullPointerException();

        if (!(obj instanceof Grade))
            throw new ClassCastException("Not a grade");

        Grade g = (Grade) obj;
        return this.student == g.student && this.course == g.course;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            throw new NullPointerException();

        if (!(o instanceof Grade))
            throw new ClassCastException();

        Grade g = (Grade) o;

        return (int) (getTotal() - g.getTotal());
    }

    protected Object clone()
            throws CloneNotSupportedException
    {
        Grade grade = (Grade) super.clone();

        Student student = (Student) UserFactory.getUser(UserFactory.UserType.STUDENT,
                getStudent().getFirstName(), getStudent().getLastName());

        if (getStudent().getMother() != null) {
            Parent mother = (Parent) UserFactory.getUser(UserFactory.UserType.PARENT,
                    getStudent().getMother().getFirstName(), getStudent().getMother().getLastName());
            student.setMother(mother);
        }

        if (getStudent().getFather() != null) {
            Parent father = (Parent) UserFactory.getUser(UserFactory.UserType.PARENT,
                    getStudent().getFather().getFirstName(), getStudent().getFather().getLastName());
            student.setFather(father);
        }

        grade.setCourse(course);

        if (getPartialScore() != null)
            grade.setPartialScore(getPartialScore().doubleValue());

        if (getExamScore() != null)
            grade.setExamScore(getExamScore().doubleValue());

        return grade;
    }

    public Double getTotal() {
        return (partialScore != null ? partialScore : 0) + (examScore != null ? examScore : 0);
    }

    @Override
    public String toString() {
//        return student + " " + course + " : " + getTotal() + "\nPartial: " + getPartialScore() + "\nExamen: " + getExamScore();
        return + getTotal() + "\nPartial: "
                + (getPartialScore() == null ? "not graded" : getPartialScore()) + "\nExamen: "
                + (getExamScore() == null ? "not graded" : getExamScore()) + "\n";

    }
}

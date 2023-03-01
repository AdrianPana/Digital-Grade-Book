public class Notification {

    Grade grade;

    public Notification(Grade grade) {
        this.grade = grade;
    }

    public String toString() {

        String result  = grade.getStudent() + " are nota " + grade.getTotal() + " la cursul " + grade.getCourse() + "\n";

        result += "Nota partial: " + (grade.getPartialScore() == null ? "(nu a fost notat inca)" : grade.getPartialScore()) + "\n";
        result += "Nota examen: " + (grade.getExamScore() == null ? "(nu a fost notat inca)" : grade.getExamScore()) + "\n";

        return result;
    }
}

import java.util.LinkedList;

public class Student extends User implements Subject {

    private Parent mother, father;
    private LinkedList<Observer> parents;
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        parents = new LinkedList<Observer>();
    }

    public void setMother(Parent mother) {
        this.mother = mother;
        addObserver(mother);
    }

    public Parent getMother() {
        return mother;
    }

    public void setFather(Parent father) {
        this.father = father;
        addObserver(father);
    }

    public Parent getFather() {
        return father;
    }
    public void addObserver(Observer observer) {
        parents.add(observer);
    }

    public void removeObserver(Observer observer) {
        for (Observer parent : parents)
            if (parent.equals(observer))
                parents.remove(parent);
    }

    public void notifyObservers(Grade grade) {
        Notification notif = new Notification(grade);
        for (Observer parent : parents) {
            parent.update(notif);
        }
    }

}

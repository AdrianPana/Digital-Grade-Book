import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;

public class StudentPage extends JFrame implements ListSelectionListener {

    private Student student;
    private Vector<Course> studentCourses;

    private HashMap<Course, Assistant> assistants;
    JList<Course> courseList;
    JTextPane details;

    public StudentPage(Student student, LinkedList<Course> allCourses) {
        super("Student Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);

        this.student = student;
        studentCourses = courses(allCourses);

        courseList = new JList<Course>(studentCourses);
        courseList.addListSelectionListener(this);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());

        JTextPane studentName = new JTextPane();
        studentName.setText("Student " + student.toString());
        studentName.setEditable(false);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        studentName.setParagraphAttributes(attribs, true);

        pane.add(studentName, BorderLayout.PAGE_START);

        pane.add(courseList, BorderLayout.LINE_START);

        details = new JTextPane();
        details.setEditable(false);

        pane.add(details, BorderLayout.CENTER);

        add(pane,BorderLayout.CENTER);

        setVisible(true);
    }

    public Vector<Course> courses(LinkedList<Course> allCourses) {

        Vector<Course> courses = new Vector<Course>();
        assistants = new HashMap<Course, Assistant>();

        for (Course course : allCourses) {
            for (Group<Student> group : course.getGroups().values()) {
                if (group.contains(student)) {
                    assistants.put(course, group.getAssistant());
                    courses.add(course);
                    break;
                }
            }
        }

        return courses;
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {

        if (courseList.isSelectionEmpty())
            return;

        Course selectedCourse = courseList.getSelectedValue();
        String text = "Course: " + selectedCourse.getName() + "\n";
        text += "Course teacher: " +  selectedCourse.getProfessor() + "\n";
        text += "Course assistants: " + selectedCourse.getAssistants() + "\n";
        text += "Group assistant: " + assistants.get(selectedCourse) + "\n";
        text += "Grade: "
                + (selectedCourse.getGrade(student) == null ? "not graded" : selectedCourse.getGrade(student))
                + "\n";
        details.setText(text);
    }
}

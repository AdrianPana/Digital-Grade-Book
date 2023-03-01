import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class TeacherPage extends JFrame implements ActionListener {

    private User teacher;
    private Vector<Course> courses;

    private ScoreVisitor sv;
    JList<Course> courseList;
    JList<String> scoreList;

    JButton validateButton;

    public TeacherPage(User teacher, LinkedList<Course> allCourses, ScoreVisitor sv) {
        super("Teacher/Assistant Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.teacher = teacher;
        this.sv = sv;

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());

        JTextPane name = new JTextPane();
        if (teacher instanceof Teacher)
            name.setText("Teacher " + teacher.toString());
        else
            name.setText("Assistant " + teacher.toString());
        name.setEditable(false);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        name.setParagraphAttributes(attribs, true);
        pane.add(name, BorderLayout.PAGE_START);

        JPanel content = new JPanel(new FlowLayout());
        courses = courses(allCourses);
        courseList = new JList<Course>(courses);
        courseList.setEnabled(false);
        content.add(courseList, BorderLayout.LINE_START);

        DefaultListModel listModel = new DefaultListModel();
        scoreList = new JList<String>(listModel);
        for (String score : sv.getNotValidated(teacher))
        {
            listModel.addElement(score);
        }
        content.add(scoreList, BorderLayout.CENTER);

        validateButton = new JButton("Validate");
        validateButton.addActionListener(this);
        content.add(validateButton, BorderLayout.LINE_END);

        pane.add(content, BorderLayout.CENTER);
        add(pane, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public Vector<Course> courses(LinkedList<Course> allCourses) {

        Vector<Course> courses = new Vector<Course>();

        if (teacher instanceof Teacher) {
            for (Course course : allCourses) {
                if (course.getProfessor().equals(teacher))
                    courses.add(course);
            }
        }

        else {
            for (Course course : allCourses) {
                for (Assistant ass: course.getAssistants()) {
                    if (ass.equals(teacher))
                    {
                        courses.add(course);
                        break;
                    }
                }
            }
        }

        return courses;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DefaultListModel listModel = (DefaultListModel) scoreList.getModel();

        if (listModel.isEmpty())
            return;

        if (teacher instanceof Teacher) {
            ((Teacher) teacher).accept(sv);
        }
        else {
            ((Assistant) teacher).accept(sv);
        }

        listModel.removeAllElements();
    }

}

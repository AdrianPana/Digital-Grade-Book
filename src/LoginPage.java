import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {

    String[] users = {"Student", "Teacher", "Assistant", "Parent"};
    JTextField firstName, lastName;
    JComboBox userList;
    JButton accessButton;
    public LoginPage() {
        super("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,150);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        userList = new JComboBox(users);
        panel.add(userList);

        firstName = new JTextField();
        lastName = new JTextField();
        firstName.setToolTipText("Introdu prenume");
        lastName.setToolTipText("Introdu nume");
        panel.add(firstName);
        panel.add(lastName);

        accessButton = new JButton("Acceseaza pagina");
        accessButton.addActionListener(this);
        panel.add(accessButton);

        add(panel, BorderLayout.CENTER);
//        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent actionEvent) {

        String selection = userList.getSelectedItem().toString();
        Catalog catalog = Catalog.getInstance();
        String first = firstName.getText();
        String last = lastName.getText();

        if (selection.equals("Student")) {
            Student student = catalog.getStudent(first, last);
            if (student == null)
                return;
            StudentPage accessedPage= new StudentPage(student, catalog.getCourses());
        }
        else if (selection.equals("Teacher")) {
            Teacher teacher = catalog.getTeacher(first, last);
            if (teacher == null)
                return;
            TeacherPage accessedPage= new TeacherPage(teacher, catalog.getCourses(), catalog.scoreVisitor);
        }
        else if (selection.equals("Assistant")) {
            Assistant assistant = catalog.getAssistant(first, last);
            if (assistant == null)
                return;
            TeacherPage accessedPage= new TeacherPage(assistant, catalog.getCourses(), catalog.scoreVisitor);
        }
        if (selection.equals("Parent")) {
            Parent parent = catalog.getParent(first, last);
            if (parent == null)
                return;
            ParentPage accessedPage= new ParentPage(parent);
        }

    }
}

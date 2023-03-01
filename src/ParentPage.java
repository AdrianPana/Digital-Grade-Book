import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.Vector;

public class ParentPage extends JFrame{

    private Parent parent;

    JList<String> notifList;

    class CustomListRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            JTextArea renderer = new JTextArea(3,40);
            renderer.setText(value.toString());
            renderer.setLineWrap(true);
            return renderer;
        }
    }
    public ParentPage(Parent parent) {
        super("Parent Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);


        this.parent = parent;
        this.parent.setPage(this);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());

        JTextPane parentName = new JTextPane();
        parentName.setText("Parent " + parent.toString());
        parentName.setEditable(false);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        parentName.setParagraphAttributes(attribs, true);
        pane.add(parentName, BorderLayout.PAGE_START);

        DefaultListModel listModel = new DefaultListModel();
        notifList = new JList<String>(listModel);
        notifList.setCellRenderer(new CustomListRenderer());
        for (String notification : parent.getNotifications()) {
            listModel.addElement(notification);
        }

        JScrollPane scrollPane = new JScrollPane(notifList);

        pane.add(scrollPane, BorderLayout.CENTER);

        add(pane,BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateNotificationList(Vector<String> notifications) {
        DefaultListModel listModel = (DefaultListModel) notifList.getModel();
        listModel.clear();
        listModel.addAll(notifications);
    }

    public String toString() {
        return "Pagina";
    }

}

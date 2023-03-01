import java.util.Vector;

public class Parent extends User implements Observer{

    private Vector<String> notifications;
    private ParentPage page;
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
        notifications = new Vector<String>();
    }

    @Override
    public void update(Notification notification) {
        notifications.add(0,notification.toString());
        if (page != null) {
            page.updateNotificationList(notifications);
        }
    }

    public Vector<String> getNotifications() {
        return notifications;
    }

    public void setPage(ParentPage page) {
        this.page = page;
    }

    public ParentPage getPage() {
        return page;
    }
}

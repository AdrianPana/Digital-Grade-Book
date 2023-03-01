public class UserFactory {

    public enum UserType {
        PARENT,
        STUDENT,
        ASSISTANT,
        TEACHER
    }

    public static User getUser(UserType type, String firstName, String lastName) {
        switch (type) {
            case PARENT:
                return new Parent(firstName, lastName);

            case STUDENT:
                return new Student(firstName, lastName);

            case ASSISTANT:
                return new Assistant(firstName, lastName);

            case TEACHER:
                return new Teacher(firstName, lastName);

            default:
                throw new IllegalArgumentException("User type not recognized");
        }
    }

}

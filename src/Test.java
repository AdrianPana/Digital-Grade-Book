import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Test {

    public static Student baiatBine;
    public static Course poo;
    public static void main(String[] args) throws CloneNotSupportedException {

        Catalog catalog = Catalog.getInstance();
        Student s = null;
        Assistant a = null;
        Teacher t = null;
        Parent p = null;

        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(new File("Test.json")));
            JsonObject fileObject = fileElement.getAsJsonObject();

            JsonArray courses = fileObject.get("courses").getAsJsonArray();
            for (JsonElement courseElement : courses) {
                JsonObject courseJsonObject = courseElement.getAsJsonObject();

                String courseName = courseJsonObject.get("name").getAsString();
                JsonObject teacherJsonObject = courseJsonObject.get("teacher").getAsJsonObject();
                String teacherFirstName = teacherJsonObject.get("firstName").getAsString();
                String teacherLastName = teacherJsonObject.get("lastName").getAsString();

                Teacher teacher = (Teacher) UserFactory.getUser(UserFactory.UserType.TEACHER, teacherFirstName, teacherLastName);
                if (teacher.toString().equals("Ion Mihalache"))
                    t = teacher;

                String strategy = courseJsonObject.get("strategy").getAsString();

                Course.TeacherStrat strat;
                if (strategy.equals("BestPartialScore")) {
                    strat = Course.TeacherStrat.PARTIAL;
                } else if (strategy.equals("BestExamScore")) {
                    strat = Course.TeacherStrat.EXAM;
                } else {
                    strat = Course.TeacherStrat.TOTAL;
                }

                String type = courseJsonObject.get("type").getAsString();
                Course.CourseBuilder courseBuilder;

                if (type.equals("FullCourse")) {
                    courseBuilder = new FullCourse.FullCourseBuilder(courseName, teacher);
                }
                else {
                    courseBuilder = new PartialCourse.PartialCourseBuilder(courseName, teacher);
                }
                Course course = courseBuilder
                        .assistants(new HashSet<Assistant>())
                        .grades(new LinkedList<Grade>())
                        .groups(new HashMap<String, Group<Student>>())
                        .credits(5)
                        .strat(strat)
                        .build();

                JsonArray groups = courseJsonObject.get("groups").getAsJsonArray();
                for (JsonElement groupElement : groups) {
                    JsonObject groupObject =  groupElement.getAsJsonObject();

                    String id = groupObject.get("ID").getAsString();

                    JsonObject assJsonObject = groupObject.get("assistant").getAsJsonObject();
                    String assFirstName = assJsonObject.get("firstName").getAsString();
                    String assLastName = assJsonObject.get("lastName").getAsString();

                    Assistant ass = (Assistant) UserFactory.getUser(UserFactory.UserType.ASSISTANT, assFirstName, assLastName);

                    Group<Student> group = new Group<Student>(id, ass);

                    JsonArray students = groupObject.get("students").getAsJsonArray();
                    for (JsonElement studentElement : students) {
                        JsonObject studentObject = studentElement.getAsJsonObject();

                        String studentFirstName = studentObject.get("firstName").getAsString();
                        String studentLastName = studentObject.get("lastName").getAsString();

                        Student student = catalog.getStudent(studentFirstName, studentLastName);
                        if (student == null)
                            student = (Student) UserFactory.getUser(UserFactory.UserType.STUDENT, studentFirstName, studentLastName);

                        if (studentObject.get("mother") != null) {
                            JsonObject motherObject = studentObject.get("mother").getAsJsonObject();

                            String motherFirstName = motherObject.get("firstName").getAsString();
                            String motherLastName = motherObject.get("lastName").getAsString();

                            Parent mother = (Parent) UserFactory.getUser(UserFactory.UserType.PARENT, motherFirstName, motherLastName);
                            student.setMother(mother);
                        }

                        if (studentObject.get("father") != null) {
                            JsonObject fatherObject = studentObject.get("father").getAsJsonObject();

                            String fatherFirstName = fatherObject.get("firstName").getAsString();
                            String fatherLastName = fatherObject.get("lastName").getAsString();

                            Parent father = (Parent) UserFactory.getUser(UserFactory.UserType.PARENT, fatherFirstName, fatherLastName);
                            student.setFather(father);
                        }
                        group.add(student);
                    }
                    course.addGroup(group);
                }
                catalog.addCourse(course);
            }

            JsonArray examScores = fileObject.get("examScores").getAsJsonArray();
            for (JsonElement scoreElement : examScores) {
                JsonObject scoreObject = scoreElement.getAsJsonObject();

                String courseName = scoreObject.get("course").getAsString();
                Double score = scoreObject.get("grade").getAsDouble();

                JsonObject studentObject = scoreObject.get("student").getAsJsonObject();
                String studentFirstName = studentObject.get("firstName").getAsString();
                String studentLastName = studentObject.get("lastName").getAsString();

                Course course = catalog.getCourse(courseName);
                Student student = course.getStudent(studentFirstName, studentLastName);

                // Fac un backup dupa primele 2 note, inainte sa fie notat Costel Busuoic in json
                if (courseName.equals("Programare Orientata pe Obiecte") && student.toString().equals("Costel Busuioc"))
                    catalog.getCourse("Programare Orientata pe Obiecte").makeBackup();

                catalog.scoreVisitor.addScore(course.getProfessor(), student, course, score);

//                Se valideaza toate notele de cum sunt adaugate, decomentati pentru a vedea un catalog completat
//                course.getProfessor().accept(catalog.scoreVisitor);
            }

            JsonArray partialScores = fileObject.get("partialScores").getAsJsonArray();
            for (JsonElement scoreElement : partialScores) {
                JsonObject scoreObject = scoreElement.getAsJsonObject();

                String courseName = scoreObject.get("course").getAsString();
                Double score = scoreObject.get("grade").getAsDouble();

                JsonObject assJsonObject = scoreObject.get("assistant").getAsJsonObject();
                String assFirstName = assJsonObject.get("firstName").getAsString();
                String assLastName = assJsonObject.get("lastName").getAsString();

                JsonObject studentObject = scoreObject.get("student").getAsJsonObject();
                String studentFirstName = studentObject.get("firstName").getAsString();
                String studentLastName = studentObject.get("lastName").getAsString();

                Course course = catalog.getCourse(courseName);
                Student student = course.getStudent(studentFirstName, studentLastName);

                Assistant assistant = course.getAssistant(assFirstName, assLastName);
                catalog.scoreVisitor.addScore(assistant, student, course, score);

//                Se valideaza toate notele de cum sunt adaugate, decomentati pentru a vedea un catalog completat
//                assistant.accept(catalog.scoreVisitor);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        Setup complet cu pagini pentru student, parinte, profesor si asistent
//        Cand i se valideaza nota, studentul poate vedea actualizarea prin reselectarea cursului in lista
//        Parintelui i se actualizeaza automat pagina cu notificari

        s = catalog.getStudent("Gigel","Frone");
        StudentPage page1 = new StudentPage(s,catalog.getCourses());
        t = catalog.getTeacher("Ion", "Mihalache");
        TeacherPage page2 = new TeacherPage(t, catalog.getCourses(), catalog.scoreVisitor);
        a = catalog.getAssistant("Andrei", "Georgescu");
        TeacherPage page3 = new TeacherPage(a, catalog.getCourses(), catalog.scoreVisitor);
        p = catalog.getParent("Maria", "Frone");
        ParentPage page4 = new ParentPage(p);

//        Setup pentru afisarea tuturor functionalitatilor unui catalog completat
//        Pentru asta este nevoie sa validam(vizitam) toate notele puse
//        -> Sunt validate la liniile 142, 167, care trebuie decomentate

//        for (Course course : catalog.getCourses()) {
//            System.out.println("Nume curs: " + course.getName());
//            System.out.println("Profesor titular: " + course.getProfessor());
//            System.out.println("Asistenti: " + course.getAssistants());
//            System.out.println("Grupe: " + course.getGroups());
//            System.out.println("Note studenti: " + course.getAllStudentGrades());
//            System.out.println("Studenti promovati: " + course.getGraduatedStudents());
//            System.out.println("Cel mai bun student: " + course.getBestStudent());
//        }

//        Setup pentru Memento, trebuie folosit cu Setup-ul pentru completarea catalogului
//        S-a facut un backup la POO dupa ce s-au pus primele 2 note, acum se va da undo

//        System.out.println("------------------------------------------------------");
//        System.out.println("Note POO inainte de undo: ");
//        System.out.println(poo.getAllStudentGrades());
//        poo = catalog.getCourse("Programare Orientata pe Obiecte");
//        poo.undo();
//        System.out.println("------------------------------------------------------");
//        System.out.println("Note POO dupa undo: ");
//        System.out.println(poo.getAllStudentGrades());

//        Setup pentru pagina de logare, care poate accesa orice user
//        LoginPage loginPage = new LoginPage();
    }
}

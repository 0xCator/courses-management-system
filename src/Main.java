import java.io.*;
import java.util.*;

public class Main {
    public static Admin admin;
    public static Student student;
    public static Instructor instructor;
    public static Scanner input;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        while (true) {
            int selection = loginSelector();
            int panel = login(selection);
            switch (panel) {
                case 1:
                    //Admin panel
                    adminPanel();
                    break;
                case 2:
                    //Instructor panel
                    instructorPanel();
                    break;
                case 3:
                    //Student panel
                    studentPanel();
                    break;
            }
        }
    }

    //This method allows the user to select the type of user he/she wants to login as
    public static int loginSelector() {
        clearConsole();
        System.out.println("Select a login type:");
        printList("Login as an admin", "Login as an instructor", "Login as a student");
        while (true) {
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                if (selection >= 1 && selection <= 3) {
                    return selection;
                } else {
                    System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next(); //Disregarding the entered letter
            }
        }
    }

    //Login function, takes a username and password
    public static int login(int selection) {
        clearConsole();
        while (true) {
            System.out.print("Username: ");
            String username = input.next();
            System.out.print("Password: ");
            String password = input.next();
            try {
                switch (selection) {
                    case 1:
                        admin = new Admin();
                        admin.login(username, password);
                        return selection;
                    case 2:
                        instructor = new Instructor();
                        instructor.login(username, password);
                        return selection;
                    case 3:
                        student = new Student();
                        student.login(username, password);
                        return selection;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    //Start of admin panel methods
    public static void adminPanel() {
        while (true) {
            clearConsole();
            System.out.println("Welcome, " + admin.getUsername());
            printList("Manage parent courses", "Manage instructors", "Manage students",
                    "Create a new course", "Create a report", "Log out");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        parentCourseMenu();
                        break;
                    case 2:
                        instructorMenu();
                        break;
                    case 3:
                        studentMenu();
                        break;
                    case 4:
                        createCourse();
                        break;
                    case 5:
                        createReport();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next(); //Disregarding the entered letter
            }
        }
    }

    public static void parentCourseMenu() {
        while (true) {
            clearConsole();
            System.out.println("Manage parent courses");
            printList("Add a parent course", "Update a parent course", "Delete a parent course",
                    "View parent courses", "Main panel");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        createParentCourse();
                        break;
                    case 2:
                        updateParentCourse();
                        break;
                    case 3:
                        deleteParentCourse();
                        break;
                    case 4:
                        listParentCourses();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next(); //Disregarding the entered letter
            }
        }
    }

    public static void createParentCourse() {
        boolean error = false;
        String code, name;
        ArrayList<String> existingParentCourses = Global.getDirectoryList(Global.ParentCourseFolder);
        do {
            System.out.print("Enter parent course's code: ");
            code = input.next();
            if (existingParentCourses.contains(code.toUpperCase())) {
                System.out.println("Error: This parent course already exists, please try again.");
                error = true;
            } else {
                error = false;
            }
        } while (error);
        input.nextLine();
        do {
            System.out.print("Enter parent course's name: ");
            name = input.nextLine();
            if (name.isEmpty()) {
                System.out.println("Error: No name was given for this parent course, please try again");
                error = true;
            } else {
                error = false;
            }
        } while (error);
        admin.createParentCourse(name, code.toUpperCase());
        System.out.println("Parent course created successfully!");
        Pause();
    }

    public static void updateParentCourse() {
        ArrayList<String> existingParentCourses = Global.getDirectoryList(Global.ParentCourseFolder);
        if (existingParentCourses.size() > 0) {
            while (true) {
                clearConsole();
                System.out.println("Available parent courses: ");
                printList(existingParentCourses);
                System.out.println(existingParentCourses.size() + 1 + " - Main panel");
                System.out.print("Enter your selection: ");
                try
                {
                    int selection = input.nextInt();
                    if(selection >=1 && selection <= existingParentCourses.size())
                    {
                        updateParentCourseDetails(existingParentCourses.get(selection-1));
                    }
                    else if(selection == existingParentCourses.size()+1) break;
                    else  System.out.println("Incorrect choice, please try again");
                }
                catch (Exception e)
                {
                    System.out.println("Error: enter an actual number");
                    input.next();
                }
            }
        } else {
            System.out.println("There are 0 parent courses");
            Pause();
        }
    }

    public static void updateParentCourseDetails(String parentCourseCode) {
        ParentCourse parentCourse = new ParentCourse(parentCourseCode);
        while (true) {
            System.out.println("Available details: ");
            printList("Name: " + parentCourse.getName(), "Main panel");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        System.out.print("Enter a new name: ");
                        input.nextLine();
                        String newName = input.nextLine();
                        parentCourse.setName(newName);
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Incorrect choice, please try again");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next();
            }
        }
    }

    public static void deleteParentCourse() {
        ArrayList<String> parentCourses = Global.getDirectoryList(Global.ParentCourseFolder);
        clearConsole();
        if (parentCourses.size() > 0) {
            System.out.println("Select parent course to delete: ");
            printList(parentCourses);
            System.out.println(parentCourses.size() + 1 + " - Main panel");
            while (true) {
                System.out.print("Enter your selection: ");
                try {
                    int selection = input.nextInt();
                    if (selection >= 1 && selection <= parentCourses.size()) {
                        admin.deleteParentCourse(parentCourses.get(selection - 1));
                        System.out.println("Parent course deleted successfully!");
                        Pause();
                        return;
                    } else if (selection == parentCourses.size() + 1) {
                        return;
                    } else {
                        System.out.println("Error: Incorrect choice, please try again");
                    }
                } catch (Exception e) {
                    System.out.println("Error: enter an actual number");
                    input.next(); //Disregarding the entered letter
                }
            }
        } else {
            System.out.println("No parent courses to delete");
            Pause();
        }
    }

    public static void listParentCourses() {
        ArrayList<String> parentCourses = Global.getDirectoryList(Global.ParentCourseFolder);
        System.out.println("Parent courses: " + parentCourses.size());
        for (String parentCourseCode : parentCourses) {
            ParentCourse parentCourse = new ParentCourse(parentCourseCode);
            System.out.println(parentCourse.toString());
        }
        Pause();
    }

    public static void instructorMenu() {
        while (true) {
            clearConsole();
            System.out.println("Manage instructors");
            printList("Add an instructor", "Update an instructor", "Delete an instructor",
                    "View instructors", "Main panel");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        createInstructor();
                        break;
                    case 2:
                        modifyInstructor();
                        break;
                    case 3:
                        deleteInstructor();
                        Pause();
                        break;
                    case 4:
                        listInstructors();
                        Pause();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next(); //Disregarding the entered letter
            }
        }
    }

    public static void createInstructor() {
        boolean error = false;
        String username, password, name, phoneNumber;
        int salary = 0;
        ArrayList<String> existingInstructors = Global.getUsernameList(Global.InstructorLogin);
        //Get username
        do {
            System.out.print("Enter the instructor's username: ");
            username = input.next();
            if (existingInstructors.contains(username.toLowerCase())) {
                System.out.println("Error: This instructor already exists, please try again.");
                error = true;
            } else {
                error = false;
            }
        } while (error);
        //Get password
        System.out.print("Enter the instructor's password: ");
        password = input.next();
        input.nextLine();
        //Get name
        do {
            System.out.print("Enter the instructor's name: ");
            name = input.nextLine();
            if (name.isEmpty()) {
                System.out.println("Error: No name was given for this instructor, please try again");
                error = true;
            } else {
                error = false;
            }
        } while (error);
        //Get phonenumber
        System.out.print("Enter the instructor's phone number: ");
        phoneNumber = input.next();
        //Get salary
        do {
            try {
                System.out.print("Enter the instructor's salary: ");
                salary = input.nextInt();
                if (salary < 0) {
                    System.out.println("Error: the salary couldn't be a negative number");
                    error = true;
                } else {
                    error = false;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual salary number");
                input.next(); //Disregarding the entered letter
            }
        } while (error);
        admin.addInstructor(username.toLowerCase(), password, name, phoneNumber, salary + "");
        System.out.println("Instructor successfully added!");
        Pause();
    }

    public static void modifyInstructor() {
        ArrayList<String> existingInstructors = Global.getUsernameList(Global.InstructorLogin);
        if (existingInstructors.size() > 0) {
            while (true) {
                clearConsole();
                System.out.println("Available instructors: ");
                printList(existingInstructors);
                System.out.println(existingInstructors.size() + 1 + " - Main panel");
                System.out.print("Enter your selection: ");
                try
                {
                    int selection = input.nextInt();
                    if(selection >=1 && selection <= existingInstructors.size())
                    {
                        modifyInstructorDetails(existingInstructors.get(selection-1));
                    }
                    else if(selection == existingInstructors.size()+1) break;
                    else  System.out.println("Incorrect choice, please try again");
                }
                catch (Exception e)
                {
                    System.out.println("Error: enter an actual number");
                    input.next();
                }
            }
        } else {
            System.out.println("There are 0 instructors");
            Pause();
        }
    }

    public static void modifyInstructorDetails(String instructorUsername) {
        Instructor currentInstructor = new Instructor(instructorUsername);
        while (true) {
            System.out.println("Available details: ");
            printList("Name: " + currentInstructor.getName(), "Phone number: " + currentInstructor.getPhoneNumber(),
                    "Salary: " + currentInstructor.getSalary(), "Main panel");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        System.out.print("Enter a new name: ");
                        input.nextLine();
                        String newName = input.nextLine();
                        currentInstructor.setName(newName);
                        break;
                    case 2:
                        System.out.print("Enter a new phone number: ");
                        input.nextLine();
                        String newPhone = input.nextLine();
                        currentInstructor.setPhoneNumber(newPhone);
                        break;
                    case 3:
                        System.out.print("Enter a new salary: ");
                        input.nextLine();
                        String newSalary = input.nextLine();
                        currentInstructor.setSalary(newSalary);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Incorrect choice, please try again");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next();
            }
        }
    }

    public static void deleteInstructor() {
        ArrayList<String> instructorUsernames = Global.getUsernameList(Global.InstructorLogin);
        clearConsole();
        if (instructorUsernames.size() > 0) {
            System.out.println("Select instructor to delete: ");
            printList(instructorUsernames);
            System.out.println(instructorUsernames.size() + 1 + " - Main panel");
            while (true) {
                System.out.print("Enter your selection: ");
                try {
                    int selection = input.nextInt();
                    if (selection >= 1 && selection <= instructorUsernames.size()) {
                        admin.deleteInstructor(instructorUsernames.get(selection - 1));
                        System.out.println("Instructor deleted successfully!");
                        return;
                    } else if (selection == instructorUsernames.size() + 1) {
                        return;
                    } else {
                        System.out.println("Error: Incorrect choice, please try again");
                    }
                } catch (Exception e) {
                    System.out.println("Error: enter an actual number");
                    input.next(); //Disregarding the entered letter
                }
            }
        } else {
            System.out.println("No instructors to delete");
        }
    }

    public static void listInstructors() {
        ArrayList<String> instructorList = Global.getUsernameList(Global.InstructorLogin);
        System.out.println("Instructors: " + instructorList.size());
        for (String instructorUsername : instructorList) {
            Instructor listableInstructor = new Instructor(instructorUsername);
            System.out.println(listableInstructor.toString());
        }
    }

    public static void studentMenu() {
        while (true) {
            clearConsole();
            System.out.println("Manage students");
            printList("Add a student", "Update a student", "Delete a student",
                    "View students", "Main panel");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        createStudent();
                        break;
                    case 2:
                        modifyStudent();
                        break;
                    case 3:
                        deleteStudent();
                        Pause();
                        break;
                    case 4:
                        listStudents();
                        Pause();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next(); //Disregarding the entered letter
            }
        }
    }

    public static void createStudent() {
        boolean error = false;
        String username, password, name, phoneNumber;
        int age = 0;
        ArrayList<String> existingStudents = Global.getUsernameList(Global.StudentLogin);
        //Get username
        do {
            System.out.print("Enter the student's username: ");
            username = input.next();
            if (existingStudents.contains(username.toLowerCase())) {
                System.out.println("Error: This student already exists, please try again.");
                error = true;
            } else {
                error = false;
            }
        } while (error);
        //Get password
        System.out.print("Enter the student's password: ");
        password = input.next();
        input.nextLine();
        //Get name
        do {
            System.out.print("Enter the student's name: ");
            name = input.nextLine();
            if (name.isEmpty()) {
                System.out.println("Error: No name was given for this student, please try again");
                error = true;
            } else {
                error = false;
            }
        } while (error);
        //Get phonenumber
        System.out.print("Enter the student's phone number: ");
        phoneNumber = input.next();
        //Get age
        do {
            try {
                System.out.print("Enter the student's age: ");
                age = input.nextInt();
                if (age < 0) {
                    System.out.println("Error: the age couldn't be a negative number");
                    error = true;
                } else {
                    error = false;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual age");
                input.next(); //Disregarding the entered letter
            }
        } while (error);
        admin.addStudent(username.toLowerCase(), password, name, phoneNumber, age + "");
        System.out.println("Student successfully added!");
        Pause();
    }

    public static void modifyStudent() {
        ArrayList<String> existingStudents = Global.getUsernameList(Global.StudentLogin);
        if (existingStudents.size() > 0) {
            while (true) {
                clearConsole();
                System.out.println("Available students: ");
                printList(existingStudents);
                System.out.println(existingStudents.size() + 1 + " - Main panel");
                System.out.print("Enter your selection: ");
                try
                {
                    int selection = input.nextInt();
                    if(selection >=1 && selection <= existingStudents.size())
                    {
                        modifyStudentDetails(existingStudents.get(selection-1));
                    }
                    else if(selection == existingStudents.size()+1) break;
                    else  System.out.println("Incorrect choice, please try again");
                }
                catch (Exception e)
                {
                    System.out.println("Error: enter an actual number");
                    input.next();
                }
            }
        } else {
            System.out.println("There are 0 students");
            Pause();
        }
    }

    public static void modifyStudentDetails(String studentUsername) {
        Student currentStudent = new Student(studentUsername);
        while (true) {
            System.out.println("Available details: ");
            printList("Name: " + currentStudent.getName(), "Age: " + currentStudent.getAge(),
                    "Phone number: " + currentStudent.getPhone(), "Main panel");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        System.out.print("Enter a new name: ");
                        input.nextLine();
                        String newName = input.nextLine();
                        currentStudent.setName(newName);
                        break;
                    case 2:
                        System.out.print("Enter a new age: ");
                        input.nextLine();
                        String newAge = input.nextLine();
                        currentStudent.setAge(newAge);
                        break;
                    case 3:
                        System.out.print("Enter a new phone number: ");
                        input.nextLine();
                        String newPhone = input.nextLine();
                        currentStudent.setPhone(newPhone);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Incorrect choice, please try again");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next();
            }
        }
    }

    public static void deleteStudent() {
        ArrayList<String> studentList = Global.getUsernameList(Global.StudentLogin);
        clearConsole();
        if (studentList.size() > 0) {
            System.out.println("Select student to delete: ");
            printList(studentList);
            System.out.println(studentList.size() + 1 + " - Main panel");
            while (true) {
                System.out.print("Enter your selection: ");
                try {
                    int selection = input.nextInt();
                    if (selection >= 1 && selection <= studentList.size()) {
                        admin.deleteStudent(studentList.get(selection - 1));
                        System.out.println("Student deleted successfully!");
                        return;
                    } else if (selection == studentList.size() + 1) {
                        return;
                    } else {
                        System.out.println("Error: Incorrect choice, please try again");
                    }
                } catch (Exception e) {
                    System.out.println("Error: enter an actual number");
                    input.next(); //Disregarding the entered letter
                }
            }
        } else {
            System.out.println("No students to delete");
            Pause();
        }
    }

    public static void listStudents() {
        ArrayList<String> studentList = Global.getUsernameList(Global.StudentLogin);
        System.out.println("Students: " + studentList.size());
        for (String studentUsername : studentList) {
            Student listableStudent = new Student(studentUsername);
            System.out.println(listableStudent.toString());
        }
    }

    public static void createCourse() {

    }

    public static void createReport() {
        clearConsole();
        System.out.println("Select report type: ");
        printList("Courses near to start", "Courses near to end");
        while (true) {
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        admin.createReport(true);
                        System.out.println("The report is saved in Reports/starting.txt");
                        break;
                    case 2:
                        admin.createReport(false);
                        System.out.println("The report is saved in Reports/ending.txt");
                        break;
                    default:
                        System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next(); //Disregarding the entered letter
            }
            Pause();
        }
    }

    //Instructor methods
    public static void instructorPanel()
    {
        while (true) {
            clearConsole();
            System.out.println("Welcome, " + instructor.getName());
            printList("Publish grades", "View courses", "View survey", "Log out");
            System.out.print("Enter your selection: ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        publishGrades();
                        break;
                    case 2:
                        viewCourses();
                        Pause();
                        break;
                    case 3:
                        viewSurvey();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Error: Incorrect choice, please try again");
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next(); //Disregarding the entered letter
            }
        }
    }

    public static void publishGrades()
    {
        ArrayList<String> courses = instructor.getCourseID();
        //list courses
        if(instructor.getCourseID().isEmpty())
        {
            System.out.println("You have zero courses");
            Pause();
        }
        else
        {
            while (true)
            {
                clearConsole();
                System.out.println("Available courses: ");
                printList(courses);
                System.out.println(courses.size() + 1 + " - Main panel");
                System.out.print("Enter your selection: ");

                try
                {
                    int selection = input.nextInt();
                    if(selection >=1 && selection <=instructor.getCourseID().size())
                    {
                        setGrades(instructor.getCourseID().get(selection-1));
                    }
                    else if(selection ==instructor.getCourseID().size()+1) break;
                    else  System.out.println("Incorrect choice, please try again");
                }
                catch (Exception e)
                {
                    System.out.println("Error: enter an actual number");
                    input.next();
                }

            }
        }
    }

    public static void setGrades(String courseID)
    {
        Course course = new Course(courseID);
        for(String student : course.getStudentUsernames())
        {
            boolean errComp = false;
            while (!errComp)
            {
                try {
                    clearConsole();
                    System.out.print("Set grade for " + student + ": ");
                    int grade = input.nextInt();
                    if(grade >=0 && grade<= course.getGrade()) {
                        instructor.setGrade(courseID,student,grade+"");
                        errComp = true;
                    }
                    else
                    {
                        System.out.println("Grade is not in specified range ");
                    }

                }
                catch (Exception e)
                {
                    System.out.println("Error: enter an actual number");
                    input.next();
                }
            }
        }
        System.out.println("All grades are set!");
        Pause();

    }

    public static void viewCourses()
    {
        ArrayList<String> courseIDs = instructor.getCourseID();
        if(courseIDs.isEmpty())
        {
            System.out.println("You have zero courses.");
        }
        else
        {
            System.out.println("Courses number : "+courseIDs.size());
            for(String id : courseIDs)
            {
                Course course = new Course(id);
                System.out.println(course.toString());
            }
        }

    }

    public static void viewSurvey()
    {
        ArrayList<String> courses = instructor.getCourseID();
        if(instructor.getCourseID().isEmpty())
        {
            System.out.println("You have zero courses");
        }
        else
        {
            while (true)
            {
                clearConsole();
                System.out.println("Available courses: ");
                printList(courses);
                System.out.println(courses.size() + 1 + " - Main panel");
                System.out.print("Enter your selection: ");

                try
                {
                    int selection = input.nextInt();
                    if(selection >=1 && selection <=instructor.getCourseID().size())
                    {
                        instructor.readSurvey(instructor.getCourseID().get(selection-1));
                        Pause();
                    }
                    else if(selection ==instructor.getCourseID().size()+1) break;
                    else  System.out.println("Incorrect choice, please try again");
                }
                catch (Exception e)
                {
                    System.out.println("Error: enter an actual number");
                    input.next();
                }

            }
        }

    }

    //Start of student panel methods
    public static void studentPanel() {
        while (true) {
            clearConsole();
            System.out.println("Welcome, " + student.getName());
            printList("View grades", "View courses", "View all courses", "Create survey", "Update information", "Log out");
            System.out.println("Enter your selection: ");
            try {
                int selection = input.nextInt();
                clearConsole();
                switch (selection) {
                    case 1:
                        showGrades();
                        Pause();
                        break;
                    case 2:
                        listCourses();
                        Pause();
                        break;
                    case 3:
                        listAllCourses();
                        Pause();
                        break;
                    case 4:
                        createSurvey();
                        break;
                    case 5:
                        updateStudent();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Incorrect choice, please try again");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next();
            }
        }
    }

    public static void showGrades() {
        if (!student.getCourses().isEmpty()) {
            System.out.println("Your grades ");
            System.out.println("_____________________");

            for (int i = 0; i < student.getGrades().size(); i++) {
                if (!student.getGrades().get(i).equals(""))
                    System.out.println(student.getCourses().get(i) + " : " + student.getGrades().get(i));
                else System.out.println(student.getCourses().get(i) + " : " + "unset grade");
            }
        } else System.err.println(student.getName() + " do not register any course.");
    }

    public static void listCourses() {
        System.out.println("Your courses ");
        System.out.println("_____________________");
        if (!student.getCourses().isEmpty()) {
            int index = 1;
            for (String course : student.getCourses()) {
                System.out.println(index + "- " + course);
                index++;
            }

        }
    }

    public static void listAllCourses() {
        ArrayList<String> courses = Global.getDirectoryList(Global.CourseFolder);
        System.out.println("All courses: " + courses.size());
        for (String courseID: courses) {
            Course course = new Course(courseID);
            System.out.println(course.toString());
        }
    }

    public static void createSurvey() {
        boolean err = false;
        do {
            clearConsole();
            listCourses();
            System.out.println(student.getCourses().size() + 1 + "- Main panel");
            System.out.println("Select course number : ");
            try {
                int selection = input.nextInt();
                if (selection >= 1 && selection <= student.getCourses().size()) {
                    System.out.println("Enter your comment : ");
                    String comment = input.next();
                    student.createSurvey(student.getCourses().get(selection - 1), comment);
                    err = true;

                } else if (selection == student.getCourses().size() + 1) {
                    err = false;
                } else {
                    System.out.println("Incorrect choice, please try again");
                    err = true;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next();
                err = true;
            }
        } while (err);

    }

    public static void updateStudent() {
        boolean err = true;
        String newInfo;

        do {
            clearConsole();
            System.out.println("Update panel");
            System.out.println("_____________________");
            printList("Name : " + student.getName(), "Age : " + student.getAge(), "Phone number : " + student.getPhone(),
                    "Main panel");
            System.out.println("Enter your selection : ");
            try {
                int selection = input.nextInt();
                switch (selection) {
                    case 1:
                        System.out.println("Enter a new name : ");
                        input.nextLine();
                        newInfo = input.nextLine();
                        student.setName(newInfo);
                        break;
                    case 2:
                        System.out.println("Enter a new age : ");
                        input.nextLine();
                        newInfo = input.nextLine();
                        student.setAge(newInfo);
                        break;
                    case 3:
                        System.out.println("Enter an new phone number : ");
                        input.nextLine();
                        newInfo = input.nextLine();
                        student.setPhone(newInfo);
                        break;
                    case 4:
                        err = false;
                        break;
                    default:
                        System.out.println("Incorrect choice, please try again");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: enter an actual number");
                input.next();
            }
        } while (err);
    }

    //Helper functions
    public static void printList(String... list) {
        for (int i = 0; i < list.length; i++) {
            System.out.println(i+1 + " - " + list[i]);
        }
    }

    public static void printList(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + " - " + list.get(i));
        }
    }

    public static void Pause() {
        Scanner input = new Scanner(System.in);
        System.out.print("Press Enter to return ");
        try {
            input.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearConsole()  {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                new ProcessBuilder("cmd", "/c", "clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ignored) {}
    }

}

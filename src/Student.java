public class Student {
    int id;
    String name;
    float gpa;
    char grade;
    boolean enrolled;

    public Student(int id, String name, float gpa, char grade, boolean enrolled) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.grade = grade;
        this.enrolled = enrolled;
    }

    public String toString() {
        return id + " - " + name + " - " + gpa + " - " + grade + " - " + enrolled;
    }
}

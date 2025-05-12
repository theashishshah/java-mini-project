public class Course {
    int courseId;
    String courseName;
    float rating;
    char level;
    boolean active;

    public Course(int courseId, String courseName, float rating, char level, boolean active) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.rating = rating;
        this.level = level;
        this.active = active;
    }

    public String toString() {
        return courseId + " - " + courseName + " - " + rating + " - " + level + " - " + active;
    }
}

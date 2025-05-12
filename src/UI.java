import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class UI {
    JFrame frame;
    JComboBox<String> tableSelect, collectionSelect;
    JTextArea resultArea;
    JTextField idField, nameField, gpaField, gradeField, enrolledField;
    JTextField courseIdField, titleField, ratingField, categoryField, activeField;

    public UI() {
        frame = new JFrame("DB Swing Interface");
        frame.setLayout(new FlowLayout());

        String[] tables = {"students", "courses"};
        String[] collections = {"ArrayList", "LinkedList"};

        tableSelect = new JComboBox<>(tables);
        collectionSelect = new JComboBox<>(collections);
        JButton fetchButton = new JButton("Fetch Data");
        JButton addButton = new JButton("Add Data");
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);

        frame.add(new JLabel("Select Table:"));
        frame.add(tableSelect);
        frame.add(new JLabel("Select Collection:"));
        frame.add(collectionSelect);
        frame.add(fetchButton);
        frame.add(addButton);
        frame.add(new JScrollPane(resultArea));

        // Fields for adding data
        idField = new JTextField(10);
        nameField = new JTextField(10);
        gpaField = new JTextField(10);
        gradeField = new JTextField(10);
        enrolledField = new JTextField(10);

        courseIdField = new JTextField(10);
        titleField = new JTextField(10);
        ratingField = new JTextField(10);
        categoryField = new JTextField(10);
        activeField = new JTextField(10);

        // Adding fields and labels to the frame
        frame.add(new JLabel("ID:"));
        frame.add(idField);
        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("GPA:"));
        frame.add(gpaField);
        frame.add(new JLabel("Grade:"));
        frame.add(gradeField);
        frame.add(new JLabel("Enrolled (true/false):"));
        frame.add(enrolledField);

        frame.add(new JLabel("Course ID:"));
        frame.add(courseIdField);
        frame.add(new JLabel("Title:"));
        frame.add(titleField);
        frame.add(new JLabel("Rating:"));
        frame.add(ratingField);
        frame.add(new JLabel("Category:"));
        frame.add(categoryField);
        frame.add(new JLabel("Active (true/false):"));
        frame.add(activeField);

        fetchButton.addActionListener(e -> fetchData());
        addButton.addActionListener(e -> addData());

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void fetchData() {
        String table = (String) tableSelect.getSelectedItem();
        String collection = (String) collectionSelect.getSelectedItem();

        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);

            resultArea.setText("");

            if (table.equals("students")) {
                Collection<Student> data = collection.equals("ArrayList") ? new ArrayList<>() : new LinkedList<>();
                while (rs.next()) {
                    data.add(new Student(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getFloat(3),
                            rs.getString(4).charAt(0),
                            rs.getBoolean(5)
                    ));
                }

                // Using for-each and Comparator
                List<Student> sorted = new ArrayList<>(data);
                sorted.sort(Comparator.comparingDouble(s -> s.gpa));
                for (Student s : sorted) {
                    resultArea.append(s.toString() + "\n");
                }

            } else {
                Collection<Course> data = collection.equals("ArrayList") ? new ArrayList<>() : new LinkedList<>();
                while (rs.next()) {
                    data.add(new Course(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getFloat(3),
                            rs.getString(4).charAt(0),
                            rs.getBoolean(5)
                    ));
                }

                List<Course> sorted = new ArrayList<>(data);
                sorted.sort(Comparator.comparingDouble(c -> c.rating));
                for (Course c : sorted) {
                    resultArea.append(c.toString() + "\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resultArea.setText("Error fetching data.");
        }
    }

    private void addData() {
        String table = (String) tableSelect.getSelectedItem();
        try (Connection conn = DBConnection.getConnection()) {
            if (table.equals("students")) {
                // Validate and insert student data
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                float gpa = Float.parseFloat(gpaField.getText());
                char grade = gradeField.getText().charAt(0);
                boolean isEnrolled = Boolean.parseBoolean(enrolledField.getText());

                String query = "INSERT INTO students (id, name, gpa, grade, is_enrolled) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, id);
                    ps.setString(2, name);
                    ps.setFloat(3, gpa);
                    ps.setString(4, String.valueOf(grade));
                    ps.setBoolean(5, isEnrolled);

                    ps.executeUpdate();
                    resultArea.setText("Student data added successfully.");
                } catch (SQLException e) {
                    resultArea.setText("Error adding student data: " + e.getMessage());
                }
            } else if (table.equals("courses")) {
                // Validate and insert course data
                int courseId = Integer.parseInt(courseIdField.getText());
                String title = titleField.getText();
                float rating = Float.parseFloat(ratingField.getText());
                char category = categoryField.getText().charAt(0);
                boolean isActive = Boolean.parseBoolean(activeField.getText());

                String query = "INSERT INTO courses (course_id, title, rating, category, is_active) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, courseId);
                    ps.setString(2, title);
                    ps.setFloat(3, rating);
                    ps.setString(4, String.valueOf(category));
                    ps.setBoolean(5, isActive);

                    ps.executeUpdate();
                    resultArea.setText("Course data added successfully.");
                } catch (SQLException e) {
                    resultArea.setText("Error adding course data: " + e.getMessage());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultArea.setText("Database error: " + ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

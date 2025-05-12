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

        fetchButton.setBackground(Color.BLUE);           // Background color
        fetchButton.setForeground(Color.WHITE);          // Text color
        fetchButton.setFont(new Font("Arial", Font.BOLD, 14)); // Font style

        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        fetchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        addButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        fetchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);

        frame.add(new JLabel("Select Table:"));
        frame.add(tableSelect);
        frame.add(new JLabel("Select Collection:"));
        frame.add(collectionSelect);
        frame.add(fetchButton);
        frame.add(addButton);
        frame.add(new JScrollPane(resultArea));
// --- Student Info Panel ---
        JPanel studentPanel = new JPanel(new GridLayout(3, 4, 10, 10)); // 3 rows, 4 columns
        studentPanel.setBorder(BorderFactory.createTitledBorder("Student Info"));

        idField = new JTextField(10);
        nameField = new JTextField(10);
        gpaField = new JTextField(10);
        gradeField = new JTextField(10);
        enrolledField = new JTextField(10);

        studentPanel.add(new JLabel("ID:"));
        studentPanel.add(idField);
        studentPanel.add(new JLabel("Name:"));
        studentPanel.add(nameField);

        studentPanel.add(new JLabel("GPA:"));
        studentPanel.add(gpaField);
        studentPanel.add(new JLabel("Grade:"));
        studentPanel.add(gradeField);

        studentPanel.add(new JLabel("Enrolled (true/false):"));
        studentPanel.add(enrolledField);
        studentPanel.add(new JLabel()); // filler for alignment
        studentPanel.add(new JLabel()); // filler for alignment

// --- Course Info Panel ---
        JPanel coursePanel = new JPanel(new GridLayout(3, 4, 10, 10)); // 3 rows, 4 columns
        coursePanel.setBorder(BorderFactory.createTitledBorder("Course Info"));

        courseIdField = new JTextField(10);
        titleField = new JTextField(10);
        ratingField = new JTextField(10);
        categoryField = new JTextField(10);
        activeField = new JTextField(10);

        coursePanel.add(new JLabel("Course ID:"));
        coursePanel.add(courseIdField);
        coursePanel.add(new JLabel("Title:"));
        coursePanel.add(titleField);

        coursePanel.add(new JLabel("Rating:"));
        coursePanel.add(ratingField);
        coursePanel.add(new JLabel("Category:"));
        coursePanel.add(categoryField);

        coursePanel.add(new JLabel("Active (true/false):"));
        coursePanel.add(activeField);
        coursePanel.add(new JLabel()); // filler
        coursePanel.add(new JLabel()); // filler

// --- Add to Frame ---
        frame.add(studentPanel);
        frame.add(coursePanel);


        fetchButton.addActionListener(e -> fetchData());
        addButton.addActionListener(e -> addData());

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Create a watermark label
        JLabel watermark = new JLabel("Team: CoreCode (cc) | Ashish Shah (1BI22CS025) & Gaurav Singh (1BI22CS056)");
        watermark.setFont(new Font("SansSerif", Font.ITALIC, 12));
        watermark.setForeground(new Color(150, 150, 150)); // Light gray like a watermark
        watermark.setHorizontalAlignment(SwingConstants.CENTER);

// Use a JPanel with transparent background to position it
        JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(frame.getWidth(), 30));
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        footerPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        footerPanel.add(watermark);

        frame.add(footerPanel);
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

                    // Clear student fields
                    idField.setText("");
                    nameField.setText("");
                    gpaField.setText("");
                    gradeField.setText("");
                    enrolledField.setText("");
                }

            } else if (table.equals("courses")) {
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

                    // Clear course fields
                    courseIdField.setText("");
                    titleField.setText("");
                    ratingField.setText("");
                    categoryField.setText("");
                    activeField.setText("");
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
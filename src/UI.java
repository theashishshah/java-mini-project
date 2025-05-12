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

    public UI() {
        frame = new JFrame("DB Swing Interface");
        frame.setLayout(new FlowLayout());

        String[] tables = {"students", "courses"};
        String[] collections = {"ArrayList", "LinkedList"};

        tableSelect = new JComboBox<>(tables);
        collectionSelect = new JComboBox<>(collections);
        JButton fetchButton = new JButton("Fetch Data");
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);

        frame.add(new JLabel("Select Table:"));
        frame.add(tableSelect);
        frame.add(new JLabel("Select Collection:"));
        frame.add(collectionSelect);
        frame.add(fetchButton);
        frame.add(new JScrollPane(resultArea));

        fetchButton.addActionListener(e -> fetchData());

        frame.setSize(500, 400);
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
}

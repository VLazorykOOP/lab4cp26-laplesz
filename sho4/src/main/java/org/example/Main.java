package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/computer_shop";
    private static final String USER = "root";
    private static final String PASS = "minecraftik020";

    private JTextField txtName = new JTextField();
    private JComboBox<String> cmbCpuMaker = new JComboBox<>(new String[]{"Intel", "AMD", "Apple Silicon"});
    private JSpinner spinCpuFreq = new JSpinner(new SpinnerNumberModel(2.5, 1.0, 6.0, 0.1));
    private JTextField txtVideo = new JTextField();
    private JSpinner spinRam = new JSpinner(new SpinnerNumberModel(8, 2, 128, 2));
    private JTextField txtSound = new JTextField();
    private JSpinner spinHdd = new JSpinner(new SpinnerNumberModel(512, 128, 4000, 128));

    private JButton btnAdd = new JButton("Додати в базу");

    public Main() {
        setTitle("Лабораторна: Облік комп'ютерів");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2, 10, 10));

        add(new JLabel("  1. Назва комп'ютера:"));
        add(txtName);
        add(new JLabel("  2. Виробник CPU:"));
        add(cmbCpuMaker);
        add(new JLabel("  3. Частота (ГГц):"));
        add(spinCpuFreq);
        add(new JLabel("  4. Відеокарта:"));
        add(txtVideo);
        add(new JLabel("  5. ОЗУ (ГБ):"));
        add(spinRam);
        add(new JLabel("  6. Звукова карта:"));
        add(txtSound);
        add(new JLabel("  7. Вінчестер (ГБ):"));
        add(spinHdd);
        add(new JLabel(""));
        add(btnAdd);

        btnAdd.addActionListener(e -> saveToDatabase());
    }

    private void saveToDatabase() {
        String name = txtName.getText();
        String cpuMaker = (String) cmbCpuMaker.getSelectedItem();
        double freq = (double) spinCpuFreq.getValue();
        String video = txtVideo.getText();
        int ram = (int) spinRam.getValue();
        String sound = txtSound.getText();
        int hdd = (int) spinHdd.getValue();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введіть назву!");
            return;
        }

        String sql = "INSERT INTO computers (name, cpu_maker, cpu_freq, video_card, ram, sound_card, hdd) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, cpuMaker);
            pstmt.setDouble(3, freq);
            pstmt.setString(4, video);
            pstmt.setInt(5, ram);
            pstmt.setString(6, sound);
            pstmt.setInt(7, hdd);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Успішно додано!");

            txtName.setText("");
            txtVideo.setText("");
            txtSound.setText("");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Помилка: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
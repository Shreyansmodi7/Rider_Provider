import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

import javax.swing.border.TitledBorder;

public class LoginPage extends JFrame {
    // Color palette
    private static final Color PRIMARY_COLOR = new Color(54, 116, 181);
    private static final Color SECONDARY_COLOR = new Color(87, 143, 202);
    private static final Color ACCENT_COLOR = new Color(161, 227, 249);
    private static final Color BACKGROUND_COLOR = new Color(209, 248, 239);
    private static final int PADDING = 15;

    private JTextField nameField, ageField, addressField, aadharField;
    private JComboBox<String> roleComboBox;
    private File selectedImageFile;

    public LoginPage() {
        setTitle("RiderProvider - Login");
        setSize(500, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        contentPanel.setBackground(BACKGROUND_COLOR);

        JPanel rolePanel = createRoleSelectionPanel();
        contentPanel.add(rolePanel);
        contentPanel.add(Box.createVerticalStrut(20));

        JPanel formPanel = createFormPanel();
        contentPanel.add(formPanel);

        add(contentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(getWidth(), 80));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoPanel.setOpaque(false);

        ImageIcon originalIcon = new ImageIcon("logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(scaledIcon);
        JLabel companyName = new JLabel("RiderProvider");
        companyName.setFont(new Font("Arial", Font.BOLD, 24));
        companyName.setForeground(Color.WHITE);

        logoPanel.add(logo);
        logoPanel.add(companyName);

        header.add(logoPanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createRoleSelectionPanel() {
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.Y_AXIS));
        rolePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 2, true), "User Role"));
        rolePanel.setBackground(BACKGROUND_COLOR);
        rolePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel selectRole = new JLabel("Select Role:");
        selectRole.setFont(new Font("Arial", Font.BOLD, 16));
        selectRole.setForeground(PRIMARY_COLOR);
        selectRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectRole.setBorder(BorderFactory.createEmptyBorder(5, 5, 8, 0));

        String[] roles = {"Select", "Tenant", "Owner"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setForeground(PRIMARY_COLOR);
        roleComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, roleComboBox.getPreferredSize().height + 5));
        roleComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        rolePanel.add(selectRole);
        rolePanel.add(roleComboBox);

        return rolePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR, 2, true), "User Information"));
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        nameField = createFormField("Full Name");
        ageField = createFormField("Age");
        addressField = createFormField("Address");
        aadharField = createFormField("Aadhar Number");

        JButton uploadPhotoButton = new JButton("Upload Profile Picture");
        uploadPhotoButton.setFont(new Font("Arial", Font.BOLD, 16));
        uploadPhotoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        uploadPhotoButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, uploadPhotoButton.getPreferredSize().height + 5));
        uploadPhotoButton.setBackground(PRIMARY_COLOR);
        uploadPhotoButton.setForeground(Color.BLUE);
        uploadPhotoButton.setFocusPainted(false);
        uploadPhotoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        uploadPhotoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Profile Picture");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "bmp", "gif"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(this, "Selected File: " + selectedImageFile.getName(), "Photo Selected", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(PADDING));
        formPanel.add(ageField);
        formPanel.add(Box.createVerticalStrut(PADDING));
        formPanel.add(addressField);
        formPanel.add(Box.createVerticalStrut(PADDING));
        formPanel.add(aadharField);
        formPanel.add(Box.createVerticalStrut(PADDING));
        formPanel.add(uploadPhotoButton);

        return formPanel;
    }

    private JTextField createFormField(String title) {
        JTextField field = new JTextField();
        Font titleFont = new Font("Arial", Font.BOLD, 16);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(50, 50, 50));

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SECONDARY_COLOR), title);
        titledBorder.setTitleFont(titleFont);
        titledBorder.setTitleColor(PRIMARY_COLOR);

        field.setBorder(BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height + 20));
        return field;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, PADDING, PADDING, PADDING));
        bottomPanel.setBackground(BACKGROUND_COLOR);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(200, 50));
        submitButton.setBackground(PRIMARY_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));

        submitButton.addActionListener(e -> {
            try {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                if (selectedRole.equals("Select")) {
                    JOptionPane.showMessageDialog(this, "Please select a valid role.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String address = addressField.getText();
                String aadhar = aadharField.getText();

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RiderProvider", "root", "Jatin#Naik@31");

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users (full_name, age, address, aadhar_number, role, profile_picture) VALUES (?, ?, ?, ?, ?, ?)");

                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, address);
                stmt.setString(4, aadhar);
                stmt.setString(5, selectedRole);

                if (selectedImageFile != null) {
                    FileInputStream fis = new FileInputStream(selectedImageFile);
                    stmt.setBinaryStream(6, fis, (int) selectedImageFile.length());
                } else {
                    stmt.setNull(6, Types.BLOB);
                }

                int result = stmt.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "User registered successfully!");
                    if (selectedRole.equals("Tenant")) {
                        new UserHomePage();
                    } else {
                        new OwnerHomePage();
                    }
                    dispose();
                }

                stmt.close();
                conn.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(submitButton);
        return bottomPanel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}

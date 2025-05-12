import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OwnerHomePage extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(54, 116, 181);    // #3674B5
    private static final Color SECONDARY_COLOR = new Color(87, 143, 202);  // #578FCA
    private static final Color ACCENT_COLOR = new Color(161, 227, 249);    // #A1E3F9
    private static final Color BACKGROUND_COLOR = new Color(209, 248, 239); // #D1F8EF
    private static final int PADDING = 15;
    
    private JTextField locationField, modelYearField, seatsField, registrationNumberField, pricePerDayField;
    private JComboBox<String> conditionCombo, typeCombo;
    private JCheckBox insuranceCheck;
    
    public OwnerHomePage() {
        setTitle("RiderProvider - Vehicle Upload");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Create and add header panel
        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);
        
        // Main content area with form and preview
        JPanel mainContent = new JPanel(new BorderLayout(20, 0));
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        // Form panel on the left
        JPanel formPanel = createFormPanel();
        
        // Preview panel on the right
        JPanel previewPanel = createPreviewPanel();
        
        // Add panels to main content
        mainContent.add(formPanel, BorderLayout.CENTER);
        mainContent.add(previewPanel, BorderLayout.EAST);
        add(mainContent, BorderLayout.CENTER);
        
        // Bottom panel with submit button
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(getWidth(), 80));
        
        // Panel for logo and company name
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoPanel.setOpaque(false);
        
        // Scale the logo
        ImageIcon originalIcon = new ImageIcon("logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logo = new JLabel(scaledIcon);
        
        // Company name label
        JLabel companyName = new JLabel("RiderProvider");
        companyName.setFont(new Font("Arial", Font.BOLD, 24));
        companyName.setForeground(Color.WHITE);
        
        logoPanel.add(logo);
        logoPanel.add(companyName);
        header.add(logoPanel, BorderLayout.WEST);
        
        // Page title
        JLabel pageTitle = new JLabel("Upload Your Vehicle", SwingConstants.CENTER);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 24));
        pageTitle.setForeground(Color.WHITE);
        header.add(pageTitle, BorderLayout.CENTER);
        
        // Profile button
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setOpaque(false);
        JButton profileButton = new JButton("My Profile");
        profileButton.setFont(new Font("Arial", Font.BOLD, 16));
        profileButton.setBackground(SECONDARY_COLOR);
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profilePanel.add(profileButton);
        header.add(profilePanel, BorderLayout.EAST);
        
        return header;
    }

    
    private JPanel createFormPanel() {
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(BACKGROUND_COLOR);
        
        JLabel formTitle = new JLabel("Vehicle Details");
        formTitle.setFont(new Font("Arial", Font.BOLD, 20));
        formTitle.setForeground(PRIMARY_COLOR);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        formWrapper.add(formTitle, BorderLayout.NORTH);
        
        // Create the form with GridBagLayout for better control
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 0.3;
        
        // Row 1: Location
        locationField = createStyledTextField();
        addFormRow(form, gbc, "Location:", locationField);
        
        // Row 2: Car Condition
        String[] conditions = {"Excellent", "Good", "Fair", "Poor"};
        conditionCombo = createStyledComboBox(conditions);
        addFormRow(form, gbc, "Car Condition:", conditionCombo);
        
        // Row 3: Upload Images
        JButton chooseFilesBtn = createStyledButton("Choose Files");
        addFormRow(form, gbc, "Upload Images:", chooseFilesBtn);
        
        // Row 4: Insurance
        JPanel insurancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        insurancePanel.setBackground(Color.WHITE);
        insuranceCheck = new JCheckBox("Yes");
        insuranceCheck.setFont(new Font("Arial", Font.PLAIN, 16));
        insuranceCheck.setBackground(Color.WHITE);
        insurancePanel.add(insuranceCheck);
        addFormRow(form, gbc, "Insurance Available:", insurancePanel);
        
        // Row 5: Model Year
        modelYearField = createStyledTextField();
        addFormRow(form, gbc, "Model Year:", modelYearField);
        
        // Row 6: Seats
        String[] seats = {"2", "4", "5", "7", "8+"};
        seatsField = createStyledTextField();
        addFormRow(form, gbc, "Seats:", seatsField);
        
        // Row 7: Vehicle Type
        String[] vehicleTypes = {"Car", "Bike", "SUV", "Van"};
        typeCombo = createStyledComboBox(vehicleTypes);
        addFormRow(form, gbc, "Vehicle Type:", typeCombo);
        
        // Row 8: Registration Number
        registrationNumberField = createStyledTextField();
        addFormRow(form, gbc, "Registration Number:", registrationNumberField);
        
        // Row 9: Price/Day
        pricePerDayField = createStyledTextField();
        addFormRow(form, gbc, "Price/Day:", pricePerDayField);
        
        formWrapper.add(new JScrollPane(form), BorderLayout.CENTER);
        return formWrapper;
    }
    
    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, Component component) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(PRIMARY_COLOR);
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
        gbc.weightx = 0.3;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(200, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }
    
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setPreferredSize(new Dimension(200, 35));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(50, 50, 50));
        return comboBox;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 35));
        button.setBackground(ACCENT_COLOR);
        button.setForeground(new Color(50, 50, 50));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private JPanel createPreviewPanel() {
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setPreferredSize(new Dimension(300, 500));
        previewPanel.setBackground(Color.WHITE);
        previewPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel previewTitle = new JLabel("Vehicle Preview", SwingConstants.CENTER);
        previewTitle.setFont(new Font("Arial", Font.BOLD, 20));
        previewTitle.setForeground(PRIMARY_COLOR);
        previewTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        previewPanel.add(previewTitle, BorderLayout.NORTH);
        
        // Preview image placeholder
        JPanel imagePlaceholder = new JPanel();
        imagePlaceholder.setBackground(new Color(240, 240, 240));
        imagePlaceholder.setPreferredSize(new Dimension(270, 200));
        imagePlaceholder.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR));
        
        JLabel placeholderText = new JLabel("Vehicle Image Preview", SwingConstants.CENTER);
        placeholderText.setFont(new Font("Arial", Font.ITALIC, 16));
        placeholderText.setForeground(SECONDARY_COLOR);
        imagePlaceholder.add(placeholderText);
        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(imagePlaceholder);
        previewPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Preview info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        
        addPreviewInfoRow(infoPanel, "Vehicle Type:", "Not selected");
        addPreviewInfoRow(infoPanel, "Condition:", "Not specified");
        addPreviewInfoRow(infoPanel, "Location:", "Not specified");
        addPreviewInfoRow(infoPanel, "Price/Day:", "₹0");
        
        previewPanel.add(infoPanel, BorderLayout.SOUTH);
        
        return previewPanel;
    }

    private void addPreviewInfoRow(JPanel panel, String label, String value) {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBackground(Color.WHITE);
        rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 16));
        labelComponent.setForeground(PRIMARY_COLOR);
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 16));
        
        rowPanel.add(labelComponent, BorderLayout.WEST);
        rowPanel.add(valueComponent, BorderLayout.CENTER);
        
        panel.add(rowPanel);
    }
    
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, PADDING, PADDING, PADDING));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        
        JButton submitButton = new JButton("Upload Vehicle");
        submitButton.setPreferredSize(new Dimension(250, 50));
        submitButton.setBackground(PRIMARY_COLOR);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadVehicle();
            }
        });
        
        bottomPanel.add(submitButton);
        
        return bottomPanel;
    }

    private void uploadVehicle() {
        // Get form values
        String location = locationField.getText();
        String condition = (String) conditionCombo.getSelectedItem();
        boolean insuranceAvailable = insuranceCheck.isSelected();
        int modelYear = Integer.parseInt(modelYearField.getText());
        int seats = Integer.parseInt(seatsField.getText());
        String vehicleType = (String) typeCombo.getSelectedItem();
        String registrationNumber = registrationNumberField.getText();
        double pricePerDay = Double.parseDouble(pricePerDayField.getText());
        
        // Call a method to save the data to the database
        saveVehicleToDatabase(location, condition, insuranceAvailable, modelYear, seats, vehicleType, registrationNumber, pricePerDay);
    }

    private void saveVehicleToDatabase(String location, String condition, boolean insuranceAvailable, int modelYear, int seats, String vehicleType, String registrationNumber, double pricePerDay) {
        // Example connection setup, assuming you have a JDBC setup
        String url = "jdbc:mysql://localhost:3306/car_rental_db";
        String user = "root";
        String password = "your_password";
        
        String sql = "INSERT INTO vehicles (location, condition, insurance_available, model_year, seats, vehicle_type, registration_number, price_per_day) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, location);
            stmt.setString(2, condition);
            stmt.setBoolean(3, insuranceAvailable);
            stmt.setInt(4, modelYear);
            stmt.setInt(5, seats);
            stmt.setString(6, vehicleType);
            stmt.setString(7, registrationNumber);
            stmt.setDouble(8, pricePerDay);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Vehicle uploaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error uploading vehicle.");
        }
    }
    
    public static void main(String[] args) {
        new OwnerHomePage();
    }
}

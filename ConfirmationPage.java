import javax.swing.*;
import java.awt.*;

public class ConfirmationPage extends JFrame {

    // Color constants
    private static final Color PRIMARY_COLOR = new Color(54, 116, 181);     // #3674B5
    private static final Color SECONDARY_COLOR = new Color(87, 143, 202);   // #578FCA
    private static final Color ACCENT_COLOR = new Color(161, 227, 249);     // #A1E3F9
    private static final Color BACKGROUND_COLOR = new Color(209, 248, 239); // #D1F8EF
    private static final int PADDING = 15;

    public ConfirmationPage(String name, String phone, String pickupDate, String returnDate, String license, String card, String expiry) {
        setTitle("Booking Confirmation");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with padding and background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(PADDING, PADDING));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));

        // Add custom styled header
        mainPanel.add(createHeader(), BorderLayout.NORTH);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        infoPanel.setOpaque(false); // so background color shows through

        infoPanel.add(new JLabel("Name: " + name));
        infoPanel.add(new JLabel("Phone: " + phone));
        infoPanel.add(new JLabel("Pickup Date: " + pickupDate));
        infoPanel.add(new JLabel("Return Date: " + returnDate));
        infoPanel.add(new JLabel("License Number: " + license));
        infoPanel.add(new JLabel("Card Number: " + card));
        infoPanel.add(new JLabel("Card Expiry Date: " + expiry));

        // Style the labels
        for (Component c : infoPanel.getComponents()) {
            c.setFont(new Font("SansSerif", Font.PLAIN, 16));
        }

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        header.setPreferredSize(new Dimension(getWidth(), 80));

        // Left: Logo and Company Name
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoPanel.setOpaque(false);

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logo = new JLabel(scaledIcon);

        JLabel companyName = new JLabel("RiderProvider");
        companyName.setFont(new Font("Arial", Font.BOLD, 24));
        companyName.setForeground(Color.WHITE);

        logoPanel.add(logo);
        logoPanel.add(companyName);
        header.add(logoPanel, BorderLayout.WEST);

        // Center: Page Title
        JLabel pageTitle = new JLabel("✅ Booking Confirmed!", SwingConstants.CENTER);
        pageTitle.setFont(new Font("Arial", Font.BOLD, 24));
        pageTitle.setForeground(Color.WHITE);
        header.add(pageTitle, BorderLayout.CENTER);

        // Right: Empty or optional Profile Button
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setOpaque(false);
        header.add(profilePanel, BorderLayout.EAST);

        return header;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfirmationPage(
                "Tabitha",                // name
                "1234567890",           // phone
                "2025-05-04",           // pickupDate
                "2025-05-07",           // returnDate
                "TN01-123456",          // license
                "1234-5678-9012",       // card
                "11/25"                 // expiry
        ));
    }
}

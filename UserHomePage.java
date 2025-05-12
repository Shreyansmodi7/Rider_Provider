import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.font.FontRenderContext;
import javax.swing.border.*;

public class UserHomePage {
    // Your color palette
    private static final Color PRIMARY_COLOR = new Color(54, 116, 181);     // #3674B5
    private static final Color SECONDARY_COLOR = new Color(87, 143, 202);   // #578FCA
    private static final Color ACCENT_COLOR = new Color(161, 227, 249);     // #A1E3F9
    private static final Color BACKGROUND_COLOR = new Color(209, 248, 239); // #D1F8EF
    
    // Additional colors for contrast and highlights
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color BUTTON_HOVER_COLOR = new Color(45, 95, 148);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    private JFrame frame;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserHomePage::new);
    }

    public UserHomePage() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        frame = new JFrame("RiderProvider - Home (Tenant)");
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // Main panel with background color
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Enhanced header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Improved filter panel
        JPanel filterPanel = createFilterPanel();
        
        // Container panel for content with padding
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        
        // Scrollable car list with improved styling
        JPanel carListPanel = createCarListPanel();
        JScrollPane scrollPane = new JScrollPane(carListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(BACKGROUND_COLOR);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        header.setPreferredSize(new Dimension(900, 80));
        
        // Panel for logo and company name
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        logoPanel.setOpaque(false);
        
        // Create placeholder logo
        ImageIcon originalIcon = new ImageIcon("logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logo = new JLabel(scaledIcon);
        
        // Company name label
        JLabel companyName = new JLabel("RiderProvider");
        companyName.setFont(new Font("Segoe UI", Font.BOLD, 24));
        companyName.setForeground(Color.WHITE);
        
        logoPanel.add(logo);
        logoPanel.add(companyName);
        header.add(logoPanel, BorderLayout.WEST);
        
        // Page title
        JLabel pageTitle = new JLabel("Available Rides", SwingConstants.CENTER);
        pageTitle.setFont(TITLE_FONT);
        pageTitle.setForeground(Color.WHITE);
        header.add(pageTitle, BorderLayout.CENTER);
        
        // Profile button
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        profilePanel.setOpaque(false);
        JButton profileButton = new JButton("My Profile");
        profileButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        profileButton.setBackground(SECONDARY_COLOR);
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profilePanel.add(profileButton);
        header.add(profilePanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACCENT_COLOR, 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Filter title
        JLabel filterTitle = new JLabel("Search Rides");
        filterTitle.setFont(SUBTITLE_FONT);
        filterTitle.setForeground(PRIMARY_COLOR);
        filterTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Filter options panel
        JPanel optionsPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Styled combo boxes
        JComboBox<String> locationFilter = createStyledComboBox(new String[]{"All Locations", "Chennai", "Bangalore", "Hyderabad"});
        JComboBox<String> passengerFilter = createStyledComboBox(new String[]{"Any Passengers", "2 Passengers", "4 Passengers", "6+ Passengers"});
        JComboBox<String> priceFilter = createStyledComboBox(new String[]{"Any Price", "< ₹1000", "₹1000 - ₹2000", "> ₹2000"});
        JComboBox<String> yearFilter = createStyledComboBox(new String[]{"Any Year", "2020+", "2015-2020", "Before 2015"});
        
        // Labels with custom styling
        JLabel locLabel = createFilterLabel("Location");
        JLabel passLabel = createFilterLabel("Passengers");
        JLabel priceLabel = createFilterLabel("Price Range");
        JLabel yearLabel = createFilterLabel("Model Year");
        
        optionsPanel.add(locLabel); 
        optionsPanel.add(locationFilter);
        optionsPanel.add(passLabel); 
        optionsPanel.add(passengerFilter);
        optionsPanel.add(priceLabel); 
        optionsPanel.add(priceFilter);
        optionsPanel.add(yearLabel); 
        optionsPanel.add(yearFilter);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(REGULAR_FONT);
        resetButton.setForeground(TEXT_COLOR);
        resetButton.setBackground(Color.WHITE);
        resetButton.setBorder(new LineBorder(SECONDARY_COLOR, 1));
        resetButton.setFocusPainted(false);
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.setPreferredSize(new Dimension(100, 35));
        
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(PRIMARY_COLOR);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(searchButton);
        
        filterPanel.add(filterTitle);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        filterPanel.add(optionsPanel);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        filterPanel.add(buttonPanel);
        
        return filterPanel;
    }
    
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(REGULAR_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        ((JComponent) comboBox.getRenderer()).setOpaque(true);
        return comboBox;
    }
    
    private JLabel createFilterLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(80, 80, 80));
        return label;
    }
    
    private JPanel createCarListPanel() {
        JPanel carListPanel = new JPanel();
        carListPanel.setLayout(new BoxLayout(carListPanel, BoxLayout.Y_AXIS));
        carListPanel.setBackground(BACKGROUND_COLOR);
        carListPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // Car card models
        String[][] carModels = {
            {"Honda City", "₹1500/day", "2019", "4", "Chennai", "Petrol", "Automatic"},
            {"Toyota Innova", "₹2200/day", "2021", "7", "Bangalore", "Diesel", "Manual"},
            {"Hyundai Verna", "₹1800/day", "2020", "5", "Hyderabad", "Petrol", "Automatic"}
        };
        
        for (String[] car : carModels) {
            JPanel carCard = createCarCard(car);
            carListPanel.add(carCard);
            carListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        return carListPanel;
    }
    
    private JPanel createCarCard(String[] carDetails) {
        JPanel carCard = new JPanel(new BorderLayout(15, 0));
        carCard.setBackground(Color.WHITE);
        carCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Car image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(150, 120));
        
        // Create a placeholder for the car image
        JLabel carImage = new JLabel();
        carImage.setIcon(createCarPlaceholderIcon(150, 120));
        carImage.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(carImage, BorderLayout.CENTER);
        
        // Car info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        // Car title with model
        JLabel carTitle = new JLabel(carDetails[0]);
        carTitle.setFont(SUBTITLE_FONT);
        carTitle.setForeground(PRIMARY_COLOR);
        carTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Car details panel
        JPanel detailsGrid = new JPanel(new GridLayout(2, 2, 30, 5));
        detailsGrid.setBackground(Color.WHITE);
        detailsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Car specs with icons
        JLabel priceLabel = createDetailLabel("Price: " + carDetails[1]);
        JLabel yearLabel = createDetailLabel("Year: " + carDetails[2]);
        JLabel passengersLabel = createDetailLabel("Passengers: " + carDetails[3]);
        JLabel locationLabel = createDetailLabel("Location: " + carDetails[4]);
        
        detailsGrid.add(priceLabel);
        detailsGrid.add(yearLabel);
        detailsGrid.add(passengersLabel);
        detailsGrid.add(locationLabel);
        
        // Features panel
        JPanel featuresPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        featuresPanel.setBackground(Color.WHITE);
        featuresPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel fuelType = createFeatureLabel(carDetails[5]);
        JLabel transmission = createFeatureLabel(carDetails[6]);
        JLabel insurance = createFeatureLabel("Insured");
        
        featuresPanel.add(fuelType);
        featuresPanel.add(transmission);
        featuresPanel.add(insurance);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton specButton = createStyledButton("View Details", SECONDARY_COLOR, Color.WHITE);
        specButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                null, 
                "Car Details:\nModel: " + carDetails[0] + 
                "\nFuel: " + carDetails[5] + 
                "\nTransmission: " + carDetails[6] + 
                "\nInsurance: Yes\nCondition: Good\nLocation: " + carDetails[4] + 
                "\nModel Year: " + carDetails[2], 
                "Specifications", 
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        JButton bookButton = createStyledButton("Book Now", PRIMARY_COLOR, Color.WHITE);
        // Modified action listener to open BookingPage
        bookButton.addActionListener(e -> {
            try {
                // Hide current frame
                frame.setVisible(false);
                
                // Open the BookingPage with car details
                new BookingPage(carDetails[0], carDetails[1], carDetails[2], carDetails[4], this);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Error opening booking page: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        buttonPanel.add(specButton);
        buttonPanel.add(bookButton);
        
        // Add all components to info panel
        infoPanel.add(carTitle);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(detailsGrid);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(featuresPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(buttonPanel);
        
        // Add panels to card
        carCard.add(imagePanel, BorderLayout.WEST);
        carCard.add(infoPanel, BorderLayout.CENTER);
        
        return carCard;
    }
    
    // Method to make the frame visible again
    public void showFrame() {
        frame.setVisible(true);
    }
    
    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(REGULAR_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    private JLabel createFeatureLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(TEXT_COLOR);
        label.setBackground(ACCENT_COLOR.brighter());
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        return label;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(fgColor);
        button.setBackground(bgColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    // Utility methods for placeholder images
    private ImageIcon createPlaceholderIcon(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        g2d.setColor(SECONDARY_COLOR);
        g2d.fillRoundRect(0, 0, width, height, 10, 10);
        
        g2d.setColor(PRIMARY_COLOR);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "RP";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text, (width - textWidth) / 2, height / 2 + textHeight / 4);
        
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    private ImageIcon createCarPlaceholderIcon(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRect(0, 0, width, height);
        
        g2d.setColor(SECONDARY_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(10, 20, width - 20, height - 40, 20, 20);
        
        // Draw simple car shape
        int carWidth = width - 40;
        int carHeight = height - 60;
        int startX = 20;
        int startY = 30;
        
        g2d.setColor(PRIMARY_COLOR);
        // Car body
        g2d.fillRoundRect(startX, startY + carHeight/3, carWidth, carHeight/2, 20, 20);
        // Car top
        g2d.fillRoundRect(startX + carWidth/4, startY, carWidth/2, carHeight/3, 10, 10);
        // Wheels
        g2d.setColor(new Color(60, 60, 60));
        g2d.fillOval(startX + carWidth/5, startY + carHeight*2/3, carWidth/5, carHeight/5);
        g2d.fillOval(startX + carWidth*3/5, startY + carHeight*2/3, carWidth/5, carHeight/5);
        
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    private Image createCircularAvatar(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw circle background
        g2d.setColor(ACCENT_COLOR);
        g2d.fillOval(0, 0, width, height);
        
        // Draw text
        g2d.setColor(PRIMARY_COLOR);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "JD";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text, (width - textWidth) / 2, height / 2 + textHeight / 4);
        
        g2d.dispose();
        return img;
    }
}
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class HotelManagementGUI extends JFrame {
    private Hotel hotel;
    private JTable availableTable, occupiedTable;
    private DefaultTableModel availableModel, occupiedModel;

    public HotelManagementGUI() {
        hotel = new Hotel("Grand Hotel");
        setTitle("Hotel Management System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load and resize the image (make sure hotel_logo.png is in your project directory)
        ImageIcon logoIcon = new ImageIcon("hotel_logo.png");
        Image img = logoIcon.getImage();
        Image resizedImg = img.getScaledInstance(1600, 280, Image.SCALE_SMOOTH); // width=200, height=80 (adjust as needed)
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER); 

        // Create a panel for the logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.add(logoLabel, BorderLayout.CENTER);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Available Rooms Tab
        availableModel = new DefaultTableModel(new Object[]{"Room No", "Type", "Price (Rs.)"}, 0);
        availableTable = new JTable(availableModel);
        JScrollPane availableScroll = new JScrollPane(availableTable);
        JButton refreshAvailable = new JButton("Refresh");
        refreshAvailable.addActionListener(e -> loadAvailableRooms());
        JPanel availablePanel = new JPanel(new BorderLayout());
        availablePanel.add(availableScroll, BorderLayout.CENTER);
        availablePanel.add(refreshAvailable, BorderLayout.SOUTH);

        // Occupied Rooms Tab
        occupiedModel = new DefaultTableModel(new Object[]{"Room No", "Type", "Guest"}, 0);
        occupiedTable = new JTable(occupiedModel);
        JScrollPane occupiedScroll = new JScrollPane(occupiedTable);
        JButton refreshOccupied = new JButton("Refresh");
        refreshOccupied.addActionListener(e -> loadOccupiedRooms());
        JPanel occupiedPanel = new JPanel(new BorderLayout());
        occupiedPanel.add(occupiedScroll, BorderLayout.CENTER);
        occupiedPanel.add(refreshOccupied, BorderLayout.SOUTH);

        // Book Room Tab
        JPanel bookPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField bookRoomField = new JTextField();
        JTextField bookGuestField = new JTextField();
        JTextField bookCardField = new JTextField();
        JTextField bookHolderField = new JTextField();
        JButton bookButton = new JButton("Book Room");
        JLabel bookStatus = new JLabel(" ");
        bookPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        bookPanel.add(new JLabel("Room Number:"));
        bookPanel.add(bookRoomField);
        bookPanel.add(new JLabel("Guest Name:"));
        bookPanel.add(bookGuestField);
        bookPanel.add(new JLabel("Credit Card Number:"));
        bookPanel.add(bookCardField);
        bookPanel.add(new JLabel("Card Holder Name:"));
        bookPanel.add(bookHolderField);
        bookPanel.add(bookButton);
        bookPanel.add(bookStatus);

        bookButton.addActionListener(e -> {
            try {
                int roomNumber = Integer.parseInt(bookRoomField.getText().trim());
                String guestName = bookGuestField.getText().trim();
                String cardNumber = bookCardField.getText().trim();
                String cardHolder = bookHolderField.getText().trim();
                if (guestName.isEmpty() || cardNumber.isEmpty() || cardHolder.isEmpty()) {
                    bookStatus.setText("All fields are required.");
                    return;
                }
                PaymentProcessor payment = new CreditCardPayment(cardNumber, cardHolder);
                boolean success = hotel.bookRoom(roomNumber, guestName, payment);
                if (success) {
                    bookStatus.setText("Room booked successfully!");
                    loadAvailableRooms();
                    loadOccupiedRooms();
                } else {
                    bookStatus.setText("Failed to book room. Please check details.");
                }
            } catch (Exception ex) {
                bookStatus.setText("Invalid input. Please try again.");
            }
        });

        // Check Out Tab
        JPanel checkoutPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField checkoutRoomField = new JTextField();
        JButton checkoutButton = new JButton("Check Out");
        JLabel checkoutStatus = new JLabel(" ");
        checkoutPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        checkoutPanel.add(new JLabel("Room Number:"));
        checkoutPanel.add(checkoutRoomField);
        checkoutPanel.add(checkoutButton);
        checkoutPanel.add(checkoutStatus);

        checkoutButton.addActionListener(e -> {
            try {
                int roomNumber = Integer.parseInt(checkoutRoomField.getText().trim());
                boolean success = hotel.checkOut(roomNumber);
                if (success) {
                    checkoutStatus.setText("Check-out successful!");
                    loadAvailableRooms();
                    loadOccupiedRooms();
                } else {
                    checkoutStatus.setText("Failed to check out. Please check details.");
                }
            } catch (Exception ex) {
                checkoutStatus.setText("Invalid input. Please try again.");
            }
        });

        // Add tabs
        tabbedPane.addTab("Available Rooms", availablePanel);
        tabbedPane.addTab("Occupied Rooms", occupiedPanel);
        tabbedPane.addTab("Book Room", bookPanel);
        tabbedPane.addTab("Check Out", checkoutPanel);

        // Set up the background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel("background.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(logoPanel, BorderLayout.NORTH);
        backgroundPanel.add(tabbedPane, BorderLayout.CENTER);
        setContentPane(backgroundPanel);

        // Initial load
        loadAvailableRooms();
        loadOccupiedRooms();
    }

    private void loadAvailableRooms() {
        availableModel.setRowCount(0);
        for (Room room : hotel.getAvailableRooms()) {
            availableModel.addRow(new Object[]{
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPrice()
            });
        }
    }

    private void loadOccupiedRooms() {
        occupiedModel.setRowCount(0);
        for (Room room : getOccupiedRooms()) {
            occupiedModel.addRow(new Object[]{
                room.getRoomNumber(),
                room.getRoomType(),
                room.getGuestName()
            });
        }
    }

    private java.util.List<Room> getOccupiedRooms() {
        java.util.List<Room> occupied = new java.util.ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (Room room : hotel.getAvailableRooms()) {
                // skip available rooms
                if (room.getRoomNumber() == i) {
                    break;
                }
            }
        }
        for (Room room : getAllRooms()) {
            if (room.isOccupied()) {
                occupied.add(room);
            }
        }
        return occupied;
    }

    private java.util.List<Room> getAllRooms() {
        // Reflection hack: get all rooms (since Hotel.rooms is private)
        try {
            java.lang.reflect.Field f = Hotel.class.getDeclaredField("rooms");
            f.setAccessible(true);
            return (java.util.List<Room>) f.get(hotel);
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HotelManagementGUI().setVisible(true);
        });
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        backgroundImage = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image, scaled to fill the panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
} 
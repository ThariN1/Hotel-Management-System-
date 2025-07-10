import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        // Add rooms with specific prices
        rooms.add(new StandardRoom(1, 5000.0));
        rooms.add(new StandardRoom(2, 5000.0));
        rooms.add(new StandardRoom(3, 6000.0));
        rooms.add(new StandardRoom(4, 6000.0));
        rooms.add(new StandardRoom(5, 7000.0));
        rooms.add(new DeluxeRoom(6, 15000.0));
        rooms.add(new DeluxeRoom(7, 15000.0));
        rooms.add(new DeluxeRoom(8, 20000.0));
        rooms.add(new DeluxeRoom(9, 22000.0));
        rooms.add(new DeluxeRoom(10, 30000.0));
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isOccupied()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public boolean bookRoom(int roomNumber, String guestName, PaymentProcessor paymentProcessor) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && !room.isOccupied()) {
                if (paymentProcessor.processPayment(room.getPrice())) {
                    room.setOccupied(true);
                    room.setGuestName(guestName);
                    bookings.add(new Booking(room, guestName, paymentProcessor));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkOut(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isOccupied()) {
                room.setOccupied(false);
                room.setGuestName("");
                return true;
            }
        }
        return false;
    }

    public void displayAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : getAvailableRooms()) {
            System.out.println("Room " + room.getRoomNumber() + 
                             " - Type: " + room.getRoomType() + 
                             " - Price: Rs." + room.getPrice());
        }
    }

    public void displayOccupiedRooms() {
        System.out.println("\nOccupied Rooms:");
        for (Room room : rooms) {
            if (room.isOccupied()) {
                System.out.println("Room " + room.getRoomNumber() + 
                                 " - Guest: " + room.getGuestName() + 
                                 " - Type: " + room.getRoomType());
            }
        }
    }
} 
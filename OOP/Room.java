public abstract class Room {
    protected int roomNumber;
    protected double price;
    protected boolean isOccupied;
    protected String guestName;

    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isOccupied = false;
        this.guestName = "";
    }

    // Abstract method that must be implemented by subclasses
    public abstract String getRoomType();

    // Getters and setters
    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
} 
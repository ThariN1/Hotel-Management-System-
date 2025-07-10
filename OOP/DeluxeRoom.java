public class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNumber, double price) {
        super(roomNumber, price);
    }
 
    @Override
    public String getRoomType() {
        return "Deluxe";
    }
} 
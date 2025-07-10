public class StandardRoom extends Room {
    public StandardRoom(int roomNumber, double price) {
        super(roomNumber, price);
    }
 
    @Override
    public String getRoomType() {
        return "Standard";
    }
} 
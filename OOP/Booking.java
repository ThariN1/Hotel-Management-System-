public class Booking {
    private Room room;
    private String guestName;
    private PaymentProcessor paymentProcessor;

    public Booking(Room room, String guestName, PaymentProcessor paymentProcessor) {
        this.room = room;
        this.guestName = guestName;
        this.paymentProcessor = paymentProcessor;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public PaymentProcessor getPaymentProcessor() {
        return paymentProcessor;
    }
} 
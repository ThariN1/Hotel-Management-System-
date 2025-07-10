public class CreditCardPayment implements PaymentProcessor {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate payment processing
        System.out.println("Processing credit card payment of Rs." + amount);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
} 
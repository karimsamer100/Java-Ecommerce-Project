package javafxapplication3;

public class Order {
    private String orderDetails;
    private String paymentMethod;

    public Order(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Order(String orderDetails, String paymentMethod) {
        this.orderDetails = orderDetails;
        this.paymentMethod = paymentMethod;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

package javafxapplication3;

import java.util.List;

public class Customer extends User {
    private double storeCredit;
    private String address;
    private Gender gender;
    private List<String> interests;

    public Customer(String username, String password, String dateOfBirth, double storeCredit, String address, Gender gender, List<String> interests) {
        super(username, password, dateOfBirth);
        this.storeCredit = storeCredit;
        this.address = address;
        this.gender = gender;
        this.interests = interests;
    }
@Override
    public void displayInfo() {
        System.out.println("Customer Info: " + getUsername() + " - Store Credit: $" + storeCredit);
    }
    // Getters and Setters
    public double getStoreCredit() { return storeCredit; }
    public void setStoreCredit(double storeCredit) { this.storeCredit = storeCredit; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }
}

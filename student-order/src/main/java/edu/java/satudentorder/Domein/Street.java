package edu.java.satudentorder.Domein;

public class Street {
    private long street_code;
    private String streetName;

    public Street() {
    }

    public Street(long street_code, String streetName) {
        this.street_code = street_code;
        this.streetName = streetName;
    }

    public long getStreet_code() {
        return street_code;
    }

    public void setStreet_code(long street_code) {
        this.street_code = street_code;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}

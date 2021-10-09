package model;

public class Location {
    private String address;
    private String area;

    // EFFECTS: creates a location with a given address and area
    public Location(String address, String area) {
        this.address = address;
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

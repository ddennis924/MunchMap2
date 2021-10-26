package model;

import org.json.JSONObject;
import persistence.Writable;

import java.nio.file.Watchable;

// Represents a Location with an address and area (city or region)
public class Location implements Writable {
    private String address;
    private String area;

    // REQUIRES: Address and area must be non-zero length strings
    // MODIFIES: this
    // EFFECTS: creates a location with a given address and area
    public Location(String address, String area) {
        this.address = address;
        this.area = area;
    }

    // setters and getters
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("address", address);
        json.put("area", area);
        return json;
    }
}

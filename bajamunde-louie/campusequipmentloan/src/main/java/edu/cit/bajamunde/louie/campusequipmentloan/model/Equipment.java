package edu.cit.bajamunde.louie.campusequipmentloan.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "equipment")
public class Equipment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Column(name = "serial_number")
    private String serialNumber;

    // true = available
    private boolean availability = true;

    public Equipment() {}
    public Equipment(String name, String type, String serialNumber, boolean availability) {
        this.name = name; this.type = type; this.serialNumber = serialNumber; this.availability = availability;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public boolean isAvailability() { return availability; }
    public void setAvailability(boolean availability) { this.availability = availability; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment)) return false;
        Equipment that = (Equipment) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}

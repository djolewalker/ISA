package com.ftnisa.isa.model.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "isa_vehicle_type")
public class VehicleType {
    @Id
    @SequenceGenerator(name = "vehicleTypeSeqGen", sequenceName = "vehicleTypeSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicleTypeSeqGen")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type_name", nullable = false)
    private VehicleTypeName vehicleTypeName;

    @Column(name = "price_per_km")
    private Float pricePerKm;

    public VehicleType() {
    }

    public VehicleType(VehicleTypeName vehicleTypeName, Float pricePerKm) {
        this.vehicleTypeName = vehicleTypeName;
        this.pricePerKm = pricePerKm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VehicleTypeName getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(VehicleTypeName vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public Float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(Float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }


    @Override
    public String toString() {
        return "VehicleType{" +
                "vehicleTypeName=" + vehicleTypeName +
                ", pricePerKm=" + pricePerKm +
                '}';
    }
}

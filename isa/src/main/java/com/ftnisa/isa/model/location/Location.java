package com.ftnisa.isa.model.location;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "isa_location")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @SequenceGenerator(name = "locationSeqGen", sequenceName = "locationSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationGen")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "longitude", nullable = false)
    private float longitude;

    @Column(name = "latitude", nullable = false)
    private float latitude;

    public Location(float longitude, float latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }

    public Location(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.name = location.getName();
    }
}

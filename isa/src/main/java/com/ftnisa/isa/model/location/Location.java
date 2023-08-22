package com.ftnisa.isa.model.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "isa_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @SequenceGenerator(name = "locationSeqGen", sequenceName = "locationSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationGen")
    private Integer id;

    @Column(name = "longitude", nullable = false)
    private float longitude;

    @Column(name = "latitude", nullable = false)
    private float latitude;

    public Location(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

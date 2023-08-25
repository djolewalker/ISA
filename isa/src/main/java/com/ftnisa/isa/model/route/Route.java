package com.ftnisa.isa.model.route;

import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONIndividualRouteResponse;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;

@Entity
@Table(name = "isa_route")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @SequenceGenerator(name = "routeSeqGen", sequenceName = "routeSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routeSeqGen")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_location")
    @NotNull
    private Location startLocation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "finish_location")
    @NotNull
    private Location finishLocation;

    @Column(name = "length")
    private float length;

    @Column(name = "estimated_duration")
    private Duration estimatedDuration;

    @Column(name = "route_price")
    private float routePrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Getter
    private Instant createdAt;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "geo")
    private GeoJSONIndividualRouteResponse geo;

    @PrePersist
    public void onPrePersist() {
        createdAt = Instant.now();
    }
}
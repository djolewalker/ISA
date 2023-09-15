package com.ftnisa.isa.model.route;

import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONIndividualRouteResponse;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "isa_route")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Getter
@Setter
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

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<IntermediateStop> stops;

    @Column(name = "length")
    private float length;

    @Column(name = "estimated_duration")
    private Duration estimatedDuration;

    @Column(name = "route_price")
    private float routePrice;

    @ManyToOne
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

    public Route(Location startLocation, Location finishLocation, List<IntermediateStop> stops, float length, Duration estimatedDuration, float routePrice, Ride ride, Instant createdAt, GeoJSONIndividualRouteResponse geo) {
        this.startLocation = startLocation;
        this.finishLocation = finishLocation;
        this.stops = stops;
        this.length = length;
        this.estimatedDuration = estimatedDuration;
        this.routePrice = routePrice;
        this.ride = ride;
        this.createdAt = createdAt;
        this.geo = geo;
    }
}

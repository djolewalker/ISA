/*
 * This file is part of Openrouteservice.
 *
 * Openrouteservice is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, see <https://www.gnu.org/licenses/>.
 */

package com.ftnisa.isa.integrations.ors.responses.routing.json;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Schema(name = "JSONLeg", description = "Leg of a route")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JSONLeg implements Serializable {
    @Schema(description = "The type of the leg, possible values are currently 'walk' and 'pt'.", example = "pt")
    @JsonProperty("type")
    private String type;
    @Schema(description = "The departure location of the leg.", example = "Dossenheim, Süd Bstg G1")
    @JsonProperty("departure_location")
    private String departureLocation;
    @Schema(description = "The headsign of the public transport vehicle of the leg.", example = "Bismarckplatz - Speyererhof - EMBL - Boxberg - Mombertplatz")
    @JsonProperty("trip_headsign")
    private String tripHeadsign;
    @Schema(description = "The public transport route name of the leg.", example = "RNV Bus 39A")
    @JsonProperty("route_long_name")
    private String routeLongName;
    @Schema(description = "The public transport route name (short version) of the leg.", example = "39A")
    @JsonProperty("route_short_name")
    private String routeShortName;
    @Schema(description = "The route description of the leg (if provided in the GTFS data set).", example = "Bus")
    @JsonProperty("route_desc")
    private String routeDesc;
    @Schema(description = "The route type of the leg (if provided in the GTFS data set).", example = "1")
    @JsonProperty("route_type")
    private int routeType;
    @Schema(description = "The distance for the leg in metres.", example = "245")
    @JsonProperty("distance")
    private Double distance;
    @Schema(description = "The duration for the leg in seconds.", example = "96.2")
    @JsonProperty("duration")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "%.1d")
    private Double duration;
    @Schema(description = "Departure date and time",
            extensions = {@Extension(name = "validWhen", properties = {
                    @ExtensionProperty(name = "ref", value = "departure"),
                    @ExtensionProperty(name = "value", value = "true", parseValue = true)}
            )}, example = "2020-01-31T12:45:00+01:00")
    @JsonProperty(value = "departure")
    protected ZonedDateTime departure;
    @Schema(description = "Arrival date and time",
            extensions = {@Extension(name = "validWhen", properties = {
                    @ExtensionProperty(name = "ref", value = "arrival"),
                    @ExtensionProperty(name = "value", value = "true", parseValue = true)}
            )}, example = "2020-01-31T13:15:00+01:00")
    @JsonProperty(value = "arrival")
    protected ZonedDateTime arrival;
    @Schema(description = "The feed ID this public transport leg based its information from.", example = "gtfs_0")
    @JsonProperty("feed_id")
    private String feedId;
    @Schema(description = "The trip ID of this public transport leg.", example = "trip_id: vrn-19-39A-1-2-21-H-8-Special-50-42")
    @JsonProperty("trip_id")
    private String tripId;
    @Schema(description = "The route ID of this public transport leg.", example = "vrn-19-39A-1")
    @JsonProperty("route_id")
    private String routeId;
    @Schema(description = "Whether the legs continues in the same vehicle as the previous one.", example = "false")
    @JsonProperty("is_in_same_vehicle_as_previous")
    private Boolean isInSameVehicleAsPrevious;
    @Schema(description = "The geometry of the leg. This is an encoded polyline.", example = "yuqlH{i~s@gaUe@VgEQFcBRbB_C")
    @JsonProperty("geometry")
    @JsonUnwrapped
    private String geomResponse;
    @Schema(description = "List containing the specific steps the segment consists of.")
    @JsonProperty("instructions")
    private List<JSONStep> instructions;
    @Schema(description = "List containing the stops the along the leg.")
    @JsonProperty("stops")
    private List<JSONPtStop> stops;

    public JSONLeg() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public ZonedDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(ZonedDateTime departure) {
        this.departure = departure;
    }

    public ZonedDateTime getArrival() {
        return arrival;
    }

    public void setArrival(ZonedDateTime arrival) {
        this.arrival = arrival;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Boolean getInSameVehicleAsPrevious() {
        return isInSameVehicleAsPrevious;
    }

    public void setInSameVehicleAsPrevious(Boolean inSameVehicleAsPrevious) {
        isInSameVehicleAsPrevious = inSameVehicleAsPrevious;
    }

    public String getGeomResponse() {
        return geomResponse;
    }

    public void setGeomResponse(String geomResponse) {
        this.geomResponse = geomResponse;
    }

    public List<JSONStep> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<JSONStep> instructions) {
        this.instructions = instructions;
    }

    public List<JSONPtStop> getStops() {
        return stops;
    }

    public void setStops(List<JSONPtStop> stops) {
        this.stops = stops;
    }
}

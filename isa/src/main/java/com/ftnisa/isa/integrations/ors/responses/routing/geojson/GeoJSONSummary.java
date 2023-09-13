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

package com.ftnisa.isa.integrations.ors.responses.routing.geojson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ftnisa.isa.integrations.ors.responses.routing.RouteWarning;
import com.ftnisa.isa.integrations.ors.responses.routing.json.JSONExtra;
import com.ftnisa.isa.integrations.ors.responses.routing.json.JSONLeg;
import com.ftnisa.isa.integrations.ors.responses.routing.json.JSONSegment;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ftnisa.isa.integrations.ors.responses.routing.json.JSONSummary;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({ "distance", "duration" })
public class GeoJSONSummary extends JSONSummary {
    private List<JSONSegment> segments;

    // @JsonProperty("way_points")
    // private List<Integer> wayPoints;

    @JsonProperty("extras")
    private Map<String, JSONExtra> extras;

    @JsonProperty("legs")
    private List<JSONLeg> legs;

    @JsonProperty("warnings")
    private List<RouteWarning> warnings;

    @JsonProperty("summary")
    public JSONSummary summary;

    @Schema(description = "Departure date and time", extensions = { @Extension(name = "validWhen", properties = {
            @ExtensionProperty(name = "ref", value = "departure"),
            @ExtensionProperty(name = "value", value = "true", parseValue = true) }) }, example = "2020-01-31T12:45:00+01:00")
    @JsonProperty(value = "departure")
    protected ZonedDateTime departure;

    @Schema(description = "Arrival date and time", extensions = { @Extension(name = "validWhen", properties = {
            @ExtensionProperty(name = "ref", value = "arrival"),
            @ExtensionProperty(name = "value", value = "true", parseValue = true) }) }, example = "2020-01-31T13:15:00+01:00")
    @JsonProperty(value = "arrival")
    protected ZonedDateTime arrival;

    public GeoJSONSummary() {
    }

    public List<JSONSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<JSONSegment> segments) {
        this.segments = segments;
    }

    // public List<Integer> getWayPoints() {
    // return wayPoints;
    // }
    //
    // public void setWayPoints(List<Integer> wayPoints) {
    // this.wayPoints = wayPoints;
    // }

    public Map<String, JSONExtra> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, JSONExtra> extras) {
        this.extras = extras;
    }

    public List<JSONLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<JSONLeg> legs) {
        this.legs = legs;
    }

    public List<RouteWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<RouteWarning> warnings) {
        this.warnings = warnings;
    }

    public JSONSummary getSummary() {
        return summary;
    }

    public void setSummary(JSONSummary summary) {
        this.summary = summary;
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
}

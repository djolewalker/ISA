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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Schema(name = "JSONIndividualRouteResponse", description = "An individual JSON based route created by the service")
public class JSONIndividualRouteResponse extends JSONBasedIndividualRouteResponse {
    @Schema(description = "The geometry of the route. For JSON route responses this is an encoded polyline.", example = "yuqlH{i~s@gaUe@VgEQFcBRbB_C")
    @JsonProperty("geometry")
    @JsonUnwrapped
    private String geomResponse;

    @Schema(description = "Summary information about the route")
    private JSONSummary summary;

//    @Schema(description = "List containing the segments and its corresponding steps which make up the route.")
//    private List<JSONSegment> segments;

    @JsonProperty("way_points")
    @Schema(description = "List containing the indices of way points corresponding to the *geometry*.", example = "[0,23]")
    private List<Integer> wayPoints;

    @JsonProperty("warnings")
    @Schema(description = "List of warnings that have been generated for the route")
    private List<JSONWarning> warnings;

    @Schema(description = "List containing the legs the route consists of.")
    @JsonProperty("legs")
    @JsonInclude()
    private List<JSONLeg> legs;

    @Schema(description = "List of extra info objects representing the extra info items that were requested for the route.")
    @JsonProperty("extras")
    private Map<String, JSONExtra> extras;

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

    public JSONIndividualRouteResponse() {
    }

    public String getGeomResponse() {
        return geomResponse;
    }

    public void setGeomResponse(String geomResponse) {
        this.geomResponse = geomResponse;
    }

    public JSONSummary getSummary() {
        return summary;
    }

    public void setSummary(JSONSummary summary) {
        this.summary = summary;
    }

//    public List<JSONSegment> getSegments() {
//        return segments;
//    }
//
//    public void setSegments(List<JSONSegment> segments) {
//        this.segments = segments;
//    }

    public List<Integer> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<Integer> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public List<JSONWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<JSONWarning> warnings) {
        this.warnings = warnings;
    }

    public List<JSONLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<JSONLeg> legs) {
        this.legs = legs;
    }

    public Map<String, JSONExtra> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, JSONExtra> extras) {
        this.extras = extras;
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

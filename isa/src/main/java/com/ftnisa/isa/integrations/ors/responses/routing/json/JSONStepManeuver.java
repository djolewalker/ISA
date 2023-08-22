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
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Maneuver object of the step")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JSONStepManeuver {
    private static final int COORDINATE_PRECISION = 6;
    private static final int ELEVATION_DECIMAL_PLACES = 2;

    @Schema(description = "The coordinate of the point where a maneuver takes place.", example = "[8.678962,49.407819]")
    @JsonProperty("location")
    private Double[] location;
    @Schema(description = "The azimuth angle (in degrees) of the direction right before the maneuver.", example = "24")
    @JsonProperty("bearing_before")
    private Integer bearingBefore;
    @Schema(description = "The azimuth angle (in degrees) of the direction right after the maneuver.", example = "96")
    @JsonProperty("bearing_after")
    private Integer bearingAfter;

    public JSONStepManeuver() {
    }

    public Double[] getLocation() {
        return location;
    }

    public void setLocation(Double[] location) {
        this.location = location;
    }

    public Integer getBearingBefore() {
        return bearingBefore;
    }

    public void setBearingBefore(Integer bearingBefore) {
        this.bearingBefore = bearingBefore;
    }

    public Integer getBearingAfter() {
        return bearingAfter;
    }

    public void setBearingAfter(Integer bearingAfter) {
        this.bearingAfter = bearingAfter;
    }
}

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
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(name = "JSONStep", description = "Step of a route segment")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JSONStep {
    @Schema(description = "The distance for the step in metres.", example = "245")
    @JsonProperty("distance")
    private Double distance;
    @Schema(description = "The duration for the step in seconds.", example = "96.2")
    @JsonProperty("duration")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "%.1d")
    private Double duration;
    @Schema(description = "The [instruction](https://GIScience.github.io/openrouteservice/documentation/Instruction-Types.html) action for symbolisation purposes.", example = "1")
    @JsonProperty("type")
    private Integer type;
    @Schema(description = "The routing instruction text for the step.", example = "Turn right onto Berliner Straße")
    @JsonProperty("instruction")
    private String instruction;
    @Schema(description = "The name of the next street.", example = "Berliner Straße")
    @JsonProperty("name")
    private String name;
    @Schema(description = "Only for roundabouts. Contains the number of the exit to take.", example = "2")
    @JsonProperty("exit_number")
    private Integer exitNumber;
    @Schema(description = "Contains the bearing of the entrance and all passed exits in a roundabout.",
            extensions = {@Extension(name = "validWhen", properties = {
                    @ExtensionProperty(name = "ref", value = "roundabout_exits"),
                    @ExtensionProperty(name = "value", value = "true", parseValue = true)}
            )}, example = "[10,45,60]")
    @JsonProperty("exit_bearings")
    private int[] exitBearings;
    @Schema(description = "List containing the indices of the steps start- and endpoint corresponding to the *geometry*.", example = "[45,48]")
    @JsonProperty("way_points")
    private int[] waypoints;
    @Schema(description = "The maneuver to be performed.",
            extensions = {@Extension(name = "validWhen", properties = {
                    @ExtensionProperty(name = "ref", value = "maneuvers"),
                    @ExtensionProperty(name = "value", value = "true", parseValue = true)}
            )})

    @JsonProperty("maneuver")
    private JSONStepManeuver maneuver;

    public JSONStep() {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExitNumber() {
        return exitNumber;
    }

    public void setExitNumber(Integer exitNumber) {
        this.exitNumber = exitNumber;
    }

    public int[] getExitBearings() {
        return exitBearings;
    }

    public void setExitBearings(int[] exitBearings) {
        this.exitBearings = exitBearings;
    }

    public int[] getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(int[] waypoints) {
        this.waypoints = waypoints;
    }

    public JSONStepManeuver getManeuver() {
        return maneuver;
    }

    public void setManeuver(JSONStepManeuver maneuver) {
        this.maneuver = maneuver;
    }
}

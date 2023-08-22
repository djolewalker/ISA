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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ftnisa.isa.integrations.ors.responses.routing.json.JSONBasedIndividualRouteResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class GeoJSONIndividualRouteResponse extends JSONBasedIndividualRouteResponse implements Serializable {
    @JsonProperty("type")
    public final String type = "Feature";

    @JsonProperty("properties")
    private GeoJSONSummary properties;

    @Schema(implementation = JSONObject.class, description = "The geometry of the route. For GeoJSON route responses this is a JSON LineString.")
    @JsonProperty("geometry")
    private JSONObject getGeometry;

    @JsonProperty("bbox")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private double[] getBBox;

    public String getType() {
        return type;
    }

    public GeoJSONSummary getProperties() {
        return properties;
    }

    public void setProperties(GeoJSONSummary properties) {
        this.properties = properties;
    }

    public JSONObject getGetGeometry() {
        return getGeometry;
    }

    public void setGetGeometry(JSONObject getGeometry) {
        this.getGeometry = getGeometry;
    }

    public double[] getGetBBox() {
        return getBBox;
    }

    public void setGetBBox(double[] getBBox) {
        this.getBBox = getBBox;
    }
}

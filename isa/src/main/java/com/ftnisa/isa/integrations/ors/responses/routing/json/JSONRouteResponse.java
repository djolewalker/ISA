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
import com.ftnisa.isa.integrations.ors.responses.routing.RouteResponse;
import com.ftnisa.isa.integrations.ors.responses.routing.RouteResponseInfo;


@Schema(name = "JSONRouteResponse")
public class JSONRouteResponse extends RouteResponse {
    public JSONRouteResponse() {}

    @JsonProperty("routes")
    @Schema(description = "A list of routes returned from the request")
    public JSONIndividualRouteResponse[] getRoutes() {
        return routeResults.toArray(new JSONIndividualRouteResponse[0]);
    }

    @JsonProperty("bbox")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Bounding box that covers all returned routes", example = "[49.414057, 8.680894, 49.420514, 8.690123]")
    public long[] getBBoxsArray() {
        return bbox;
    }

    @JsonProperty("metadata")
    @Schema(description = "Information about the service and request")
    public RouteResponseInfo getInfo() {
        return responseInformation;
    }
}

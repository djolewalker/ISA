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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ftnisa.isa.integrations.ors.responses.routing.IndividualRouteResponse;
import io.swagger.v3.oas.annotations.media.Schema;


public class JSONBasedIndividualRouteResponse extends IndividualRouteResponse {

    @Schema(description = "A bounding box which contains the entire route", example = "[49.414057, 8.680894, 49.420514, 8.690123]")
    @JsonProperty("bbox")
    protected long[] bbox;

    public JSONBasedIndividualRouteResponse()  {
        super();
    }

    public long[] getBbox() {
        return bbox;
    }

    public void setBbox(long[] bbox) {
        this.bbox = bbox;
    }
}

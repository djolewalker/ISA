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
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "JSONExtra", description = "An object representing one of the extra info items requested")
public class JSONExtra {
    private List<List<Long>> values;
    private List<JSONExtraSummary> summary;

    public JSONExtra() {
    }

    @Schema(description = """
            A list of values representing a section of the route. The individual values are:\s
            Value 1: Indice of the staring point of the geometry for this section,
            Value 2: Indice of the end point of the geoemetry for this sections,
            Value 3: [Value](https://GIScience.github.io/openrouteservice/documentation/extra-info/Extra-Info.html) assigned to this section.""",
            example = "[[0,3,26],[3,10,12]]")
    @JsonProperty("values")
    private List<List<Long>> getValues() {
        return values;
    }

    @Schema(description = "List representing the summary of the extra info items.")
    @JsonProperty("summary")
    private List<JSONExtraSummary> getSummary() {
        return summary;
    }

    public void setValues(List<List<Long>> values) {
        this.values = values;
    }

    public void setSummary(List<JSONExtraSummary> summary) {
        this.summary = summary;
    }
}

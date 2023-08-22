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

package com.ftnisa.isa.integrations.ors.responses.routing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ftnisa.isa.integrations.ors.requests.routing.RouteRequest;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Information about the request")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RouteResponseInfo {
    @Schema(description = "ID of the request (as passed in by the query)", example = "request123")
    @JsonProperty("id")
    private String id;

    @Schema(description = "Copyright and attribution information", example = "openrouteservice.org | OpenStreetMap contributors")
    @JsonProperty("attribution")
    private String attribution;

    @Schema(description = "The MD5 hash of the OSM planet file that was used for generating graphs", example = "c0327ba6")
    @JsonProperty("osm_file_md5_hash")
    private String osmFileMD5Hash;

    @Schema(description = "The service that was requested", example = "routing")
    @JsonProperty("service")
    private  String service;

    @Schema(description = "Time that the request was made (UNIX Epoch time)", example = "1549549847974")
    @JsonProperty("timestamp")
    private long timeStamp;

    @Schema(description = "The information that was used for generating the route")
    @JsonProperty("query")
    private RouteRequest request;

    @Schema(description = "Information about the routing service")
    @JsonProperty("engine")
    private EngineInfo engineInfo;

    @Schema(description = "System message", example = "A message string configured in the service")
    @JsonProperty("system_message")
    private String systemMessage;

    public RouteResponseInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getOsmFileMD5Hash() {
        return osmFileMD5Hash;
    }

    public void setOsmFileMD5Hash(String osmFileMD5Hash) {
        this.osmFileMD5Hash = osmFileMD5Hash;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public RouteRequest getRequest() {
        return request;
    }

    public void setRequest(RouteRequest request) {
        this.request = request;
    }

    public EngineInfo getEngineInfo() {
        return engineInfo;
    }

    public void setEngineInfo(EngineInfo engineInfo) {
        this.engineInfo = engineInfo;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    @Schema(description = "Information about the version of the openrouteservice that was used to generate the route")
    private static class EngineInfo {
        @Schema(description = "The backend version of the openrouteservice that was queried", example = "5.0")
        @JsonProperty("version")
        private String version;
        @Schema(description = "The date that the service was last updated", example = "2019-02-07T14:28:11Z")
        @JsonProperty("build_date")
        private String buildDate;
        @Schema(description = "The date that the graph data was last updated", example = "2019-02-07T14:28:11Z")
        @JsonProperty("graph_date")
        private String graphDate;

        public EngineInfo() {
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getBuildDate() {
            return buildDate;
        }

        public void setBuildDate(String buildDate) {
            this.buildDate = buildDate;
        }

        public String getGraphDate() {
            return graphDate;
        }

        public void setGraphDate(String graphDate) {
            this.graphDate = graphDate;
        }
    }
}

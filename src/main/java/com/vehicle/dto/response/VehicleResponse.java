package com.vehicle.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleResponse extends MaintenanceResponse{
	
	@Schema(description ="vehicleId, the Vehicle Id")
	@JsonProperty("vehicleId")
	private String vehicleId;
	
	@Schema(description="model, the model")
	@JsonProperty("model")
	private String model;
	
	@Schema(description="type")
	@JsonProperty("type")
	private String type;

	@Schema(description="licensePlate")
	@JsonProperty("licensePlate")
	private String licensePlate;

	@Schema(description="availability")
	@JsonProperty("availability")
	private String availability;
}

package com.vehicle.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleRequest {
		
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
	
	private String action;
	
}

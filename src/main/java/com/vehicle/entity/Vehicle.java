package com.vehicle.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="vehicle")
public class Vehicle extends Maintenance {

	@Id
	@Column(name="VEHICLE_ID")
	private String vehicleId;

	@Column(name="MODEL")
	private String model;
	
	@Column(name="TYPE")
	private String type;

	@Column(name="LICENSE_PLATE")
	private String licensePlate;

	@Column(name="AVAILABILITY")
	private String availability;

}

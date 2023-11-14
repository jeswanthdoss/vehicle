package com.vehicle.repository.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vehicle.entity.Vehicle;
import com.vehicle.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VehicleRepositoryHelper {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	public List<Vehicle> findAllVehicles(){
		return vehicleRepository.findAll();
	}
	
	public List<Vehicle> findAvailableVehicles(String availability){
		return vehicleRepository.findAvailableVehicles(availability);
	}
	
	public Optional<Vehicle> findByVehicleId(String vehicleId){
		return vehicleRepository.findByVehicleId(vehicleId);
	}
	
	public Optional<Vehicle> findByLicensePlate(String licensePlate){
		return vehicleRepository.findByLicensePlate(licensePlate);
	}

	public Vehicle saveVehicle(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}
	
	public void deleteVehicle(Vehicle vehicle) {
		 vehicleRepository.delete(vehicle);
	}
}
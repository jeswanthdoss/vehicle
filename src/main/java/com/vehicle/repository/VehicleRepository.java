package com.vehicle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vehicle.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
		
	List<Vehicle> findAll();
	
	Optional<Vehicle> findByVehicleId(String vehicleId);
	
	Optional<Vehicle> findByLicensePlate(String licensePlate);

	@Query("SELECT v FROM Vehicle v WHERE v.availability = :availability")
	List<Vehicle> findAvailableVehicles(@Param("availability") String availability);
	
}

package com.vehicle.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.vehicle.dto.request.VehicleRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface VehicleService {

	Map<String, Object> getAllVehicles(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap);
	
	Map<String, Object> getAvailableVehicles(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap);
	
	Map<String, Object> getVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String vehicleId,
			String licensePlate, Map<String, String> logMap, Map<String, String> requestHeaderMap);

	Map<String, Object> createOrUpdateVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody VehicleRequest vehicleRequest, Map<String, String> logMap,
			Map<String, String> requestHeaderMap);

	Map<String, Object> deleteVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String vehicleId, String licensePlate, Map<String, String> logMap, Map<String, String> requestHeaderMap);

}
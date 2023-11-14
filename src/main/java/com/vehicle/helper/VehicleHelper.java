package com.vehicle.helper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.vehicle.constants.VehicleConstants;
import com.vehicle.dto.request.VehicleRequest;
import com.vehicle.entity.Vehicle;
import com.vehicle.repository.helper.VehicleRepositoryHelper;
import com.vehicle.utils.VehicleUtils;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VehicleHelper implements VehicleConstants {

	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	//private VehicleConfiguration vehicleConfiguration;

	@Autowired
	private VehicleRepositoryHelper vehicleRepositoryHelper;

	@Autowired
	private VehicleUtils vehicleUtils;
	
	public void validateGetOrDeleteVehicle(String vehicleId, String licensePlate,
			Map<String, Object> response, Map<String, String> logMap) {
		String methodName = "validateGetOrDeleteVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		if (StringUtils.isBlank(vehicleId) && StringUtils.isBlank(licensePlate)) {
			vehicleUtils.prepareResponse(WARNING_CODE, VEHICLE_ID_LICENSE_PLATE_MISSING_MSG,
					WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public void validateCreateOrUpdateVehicle(VehicleRequest vehicleRequest,
			Map<String, Object> response, Map<String, String> logMap) {
		String methodName = "validateCreateOrUpdateVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		
		if(null != vehicleRequest) {
			if (StringUtils.isBlank(vehicleRequest.getVehicleId())){
				vehicleRequest.setAction(CREATE);
				logMap.put(ACTION, CREATE);
			}else {
				logMap.put("vehicleId", vehicleRequest.getVehicleId());
				logMap.put(ACTION, UPDATE);
				vehicleRequest.setAction(UPDATE);
			}
			
			if(vehicleRequest.getAction().equalsIgnoreCase(CREATE)) {
				if(StringUtils.isBlank(vehicleRequest.getModel())) {
					vehicleUtils.prepareResponse(WARNING_CODE, MODEL_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(vehicleRequest.getType())) {
					vehicleUtils.prepareResponse(WARNING_CODE, TYPE_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(vehicleRequest.getLicensePlate())) {
					vehicleUtils.prepareResponse(WARNING_CODE, LICENSE_PLATE_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(vehicleRequest.getAvailability())) {
					vehicleUtils.prepareResponse(WARNING_CODE, AVAILABILITY_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
			}
			
			if(vehicleRequest.getAction().equalsIgnoreCase(UPDATE)) {
				if(StringUtils.isBlank(vehicleRequest.getModel()) &&
						StringUtils.isBlank(vehicleRequest.getType()) &&
						StringUtils.isBlank(vehicleRequest.getLicensePlate()) &&
						StringUtils.isBlank(vehicleRequest.getAvailability())) {
					vehicleUtils.prepareResponse(WARNING_CODE, MODEL_TYPE_LP_AVAIL_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}				
			}
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public Map<String, Object> getAllVehicles(Map<String, String> logMap) {
		String methodName = "getAllVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		List<Vehicle> vehiclesList = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = "No Vehicles found";
		
		vehiclesList = vehicleRepositoryHelper.findAllVehicles();
		
		logger.info("vehiclesList={}", vehiclesList);
		
		if (null != vehiclesList && vehiclesList.size() > 0) {
			populateGetVehiclesResponse(vehiclesList, response, logMap);
		} else {
			vehicleUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}
	
	
	
	public Map<String, Object> getAvailableVehicles(Map<String, String> logMap) {
		String methodName = "getAvailableVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		List<Vehicle> vehiclesList = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = "No Vehicles found";
		
		vehiclesList = vehicleRepositoryHelper.findAvailableVehicles("Y");
		
		logger.info("vehiclesList={}", vehiclesList);
		
		if (null != vehiclesList && vehiclesList.size() > 0) {
			populateGetVehiclesResponse(vehiclesList, response, logMap);
		} else {
			vehicleUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}
	
	
	public Map<String, Object> getVehicle(String vehicleId, String licensePlate, Map<String, String> logMap) {
		String methodName = "getVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<Vehicle> optVehicle;
		Vehicle vehicle = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = null;
		
		if (StringUtils.isNotBlank(vehicleId)) {
			logMap.put("vehicleId", vehicleId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for vehicleId: "+vehicleId;
			optVehicle = vehicleRepositoryHelper.findByVehicleId(vehicleId);

		} else if (StringUtils.isNotBlank(licensePlate)) {
			logMap.put("licensePlate", licensePlate);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for licensePlate: "+licensePlate;
			optVehicle = vehicleRepositoryHelper.findByLicensePlate(licensePlate);
		}else {
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG;
			optVehicle = null;
		}

		if (null!= optVehicle && optVehicle.isPresent()) {
			vehicle = optVehicle.get();
		}
		logger.debug("vehicle={}", vehicle);

		if (null != vehicle) {
			populateGetVehicleResponse(vehicle, response);
		} else {
			vehicleUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;

	}
	
	private void populateGetVehiclesResponse(List<Vehicle> vehiclesList, Map<String, Object> response,
			Map<String, String> logMap) {
		String methodName = "populateGetVehiclesResponse";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		

		response.put("vehicles", vehiclesList);
		/*
		for(Vehicle vehicle: vehiclesList) {
			Map<String, Object> vehicleMap = new HashMap<String, Object>();

			vehicleMap.put("vehicleId", vehicle.getVehicleId());
			vehicleMap.put("model", vehicle.getModel());
			vehicleMap.put("type", vehicle.getType());
			vehicleMap.put("licensePlate", vehicle.getLicensePlate());
			vehicleMap.put("availability", vehicle.getAvailability());
			vehicleMap.put("createdTimestamp", vehicle.getCreatedTimestamp());
			vehicleMap.put("createdBy", vehicle.getCreatedBy());
			vehicleMap.put("modifiedTimestamp", vehicle.getModifiedTimestamp());
			vehicleMap.put("modifiedBy", vehicle.getModifiedBy());	
		}
		*/

		response.put(HTTP_STATUS, HttpStatus.OK);
		logger.info("populateGetVehiclesResponse response={}", response);

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}

	private void populateGetVehicleResponse(Vehicle vehicle, Map<String, Object> response) {
		String methodName = "populateGetVehicleResponse";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		response.put("vehicleId", vehicle.getVehicleId());
		response.put("model", vehicle.getModel());
		response.put("type", vehicle.getType());
		response.put("licensePlate", vehicle.getLicensePlate());
		response.put("availability", vehicle.getAvailability());
		response.put("createdTimestamp", vehicle.getCreatedTimestamp());
		response.put("createdBy", vehicle.getCreatedBy());
		response.put("modifiedTimestamp", vehicle.getModifiedTimestamp());
		response.put("modifiedBy", vehicle.getModifiedBy());
		
		response.put(HTTP_STATUS, HttpStatus.OK);
		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public Map<String, Object> createOrUpdateVehicle(VehicleRequest vehicleRequest, Map<String, String> logMap) {
		String methodName = "createOrUpdateVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<Vehicle> optVehicle;
		Vehicle vehicle = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = null;
		String successMsg = null;
		
		if(vehicleRequest.getAction().equalsIgnoreCase(UPDATE)) {
			optVehicle = vehicleRepositoryHelper.findByVehicleId(vehicleRequest.getVehicleId());
			
			if (null!= optVehicle && optVehicle.isPresent()) {
				vehicle = optVehicle.get();
				
				if (null != vehicle) {
					if(StringUtils.isNotBlank(vehicleRequest.getModel())) {
						vehicle.setModel(vehicleRequest.getModel());
					}
					
					if(StringUtils.isNotBlank(vehicleRequest.getType())) {
						vehicle.setType(vehicleRequest.getType());
					}
					
					if(StringUtils.isNotBlank(vehicleRequest.getLicensePlate())) {
						vehicle.setLicensePlate(vehicleRequest.getLicensePlate());
					}
					
					if(StringUtils.isNotBlank(vehicleRequest.getAvailability())) {
						vehicle.setAvailability(vehicleRequest.getAvailability());
					}
					
					vehicle.setModifiedBy(VEHICLE_APP);
					vehicle.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
					
					vehicleRepositoryHelper.saveVehicle(vehicle);
					successMsg = UPDATE + " completed for vehicleId: "+vehicleRequest.getVehicleId();
					vehicleUtils.prepareResponse(SUCCESS_CODE, successMsg,
							SUCCESS_MSG, HttpStatus.OK, response, logMap);
				}	
			}else {
				recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for vehicleId: "+vehicleRequest.getVehicleId();
				vehicleUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
						WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
			}
		}
		
		if(vehicleRequest.getAction().equalsIgnoreCase(CREATE)) {
			vehicle = new Vehicle();
			String vehicleId = "vehicle-"+UUID.randomUUID().toString();
			vehicle.setVehicleId(vehicleId);
			vehicle.setModel(vehicleRequest.getModel());
			vehicle.setType(vehicleRequest.getType());
			vehicle.setLicensePlate(vehicleRequest.getLicensePlate());
			vehicle.setAvailability(vehicleRequest.getAvailability());
			
			vehicle.setCreatedBy(VEHICLE_APP);
			vehicle.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));			
			vehicle.setModifiedBy(VEHICLE_APP);
			vehicle.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
			
			vehicleRepositoryHelper.saveVehicle(vehicle);
			successMsg = CREATE + " completed. vehicleId: "+vehicleId;
			logMap.put("vehicleId", vehicleId);
			response.put("vehicleId", vehicleId);
			vehicleUtils.prepareResponse(SUCCESS_CODE, successMsg,
					SUCCESS_MSG, HttpStatus.OK, response, logMap);
		}
	
		logger.debug(METHOD_CLASS_EXIT, methodName, className);
		
		return response;
	}
	
	public Map<String, Object> deleteVehicle(String vehicleId, String licensePlate, Map<String, String> logMap) {
		String methodName = "deleteVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<Vehicle> optVehicle;
		Vehicle vehicle = null;
		Map<String, Object> response = new HashMap<>();
		String successMsg = null;
		String recordNotFoundMsg = null;
		
		if (StringUtils.isNotBlank(vehicleId)) {
			logMap.put("vehicleId", vehicleId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for vehicleId: "+vehicleId;
			successMsg = "Vehicle with vehicleId: "+vehicleId+" deleted successfully.";
			
			optVehicle = vehicleRepositoryHelper.findByVehicleId(vehicleId);

		} else if (StringUtils.isNotBlank(licensePlate)) {
			logMap.put("licensePlate", licensePlate);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for licensePlate: "+licensePlate;
			successMsg = "Vehicle with licensePlate: "+licensePlate+" deleted successfully.";

			optVehicle = vehicleRepositoryHelper.findByLicensePlate(licensePlate);
		}else {
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG;
			optVehicle = null;
		}

		if (null!= optVehicle && optVehicle.isPresent()) {
			vehicle = optVehicle.get();
		}
		logger.debug("vehicle={}", vehicle);

		if (null != vehicle) {
			vehicleRepositoryHelper.deleteVehicle(vehicle);
			vehicleUtils.prepareResponse(SUCCESS_CODE, successMsg,
					SUCCESS_MSG, HttpStatus.OK, response, logMap);
			
		} else {
			vehicleUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}

}

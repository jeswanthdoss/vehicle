package com.vehicle.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.constants.VehicleConstants;
import com.vehicle.dto.request.VehicleRequest;
import com.vehicle.dto.response.VehicleResponse;
import com.vehicle.dto.response.VehiclesResponse;
import com.vehicle.service.VehicleService;
import com.vehicle.utils.VehicleUtils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import javax.validation.Valid;


@RestController
@Validated
@RequestMapping(path="/")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = VehicleConstants.APP_NAME, description = VehicleConstants.APP_DESC, version = VehicleConstants.APP_VERSION))
public class VehicleController implements VehicleConstants{
	
	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VehicleUtils vehicleUtils;
	
	@Autowired
	private VehicleService vehicleService;
	
	private ObjectMapper objectMapper;
	
	@GetMapping("/")
	@Operation(description = "startUp API", summary = "startUp", tags = "startUp")
	public ResponseEntity<String> startUp(){
		String methodName = "startUp";
		logger.info(METHOD_CLASS_ENTRY, methodName,className);

		return ResponseEntity.ok().body(START_UP_SUCCESS);
	}
	
	@GetMapping("/vehicles")
	public ResponseEntity<VehiclesResponse> getAllVehicles(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getAllVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = vehicleService.getAllVehicles(httpRequest, httpResponse,
							logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		VehiclesResponse vehiclesResponse = objectMapper.readValue(jsonResponse, VehiclesResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		vehicleUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(vehiclesResponse, httpStatus);
	}
	
	@GetMapping("/availableVehicles")
	public ResponseEntity<VehiclesResponse> getAvailableVehicles(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getAvailableVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = vehicleService.getAvailableVehicles(httpRequest, httpResponse,
							logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		VehiclesResponse vehiclesResponse = objectMapper.readValue(jsonResponse, VehiclesResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		vehicleUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(vehiclesResponse, httpStatus);
	}
	
	@GetMapping("/vehicle")
	public ResponseEntity<VehicleResponse> getVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestParam(value="vehicleId", defaultValue="") final String vehicleId, 
			@RequestParam(value="licensePlate", defaultValue="") final String licensePlate,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = vehicleService.getVehicle(httpRequest, httpResponse, vehicleId, licensePlate,
							logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		VehicleResponse vehicleResponse = objectMapper.readValue(jsonResponse, VehicleResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		vehicleUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(vehicleResponse, httpStatus);
	}
	
	@PostMapping("/vehicle")
	public ResponseEntity<VehicleResponse> createOrUpdateVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody VehicleRequest vehicleRequest,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "createOrUpdateVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);
				
		Map<String, Object> response =  vehicleService.createOrUpdateVehicle(httpRequest, httpResponse,
												vehicleRequest, logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		VehicleResponse vehicleResponse = objectMapper.readValue(jsonResponse, VehicleResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		vehicleUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(vehicleResponse, httpStatus);
	}
	
	@DeleteMapping("/vehicle")
	public ResponseEntity<VehicleResponse> deleteVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestParam(value="vehicleId", defaultValue="") final String vehicleId, 
			@RequestParam(value="licensePlate", defaultValue="") final String licensePlate,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "deleteVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, DELETE);

		Map<String, Object> response = vehicleService.deleteVehicle(httpRequest, httpResponse, vehicleId, licensePlate,
					logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		VehicleResponse vehicleResponse = objectMapper.readValue(jsonResponse, VehicleResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		vehicleUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(vehicleResponse, httpStatus);
	}
}

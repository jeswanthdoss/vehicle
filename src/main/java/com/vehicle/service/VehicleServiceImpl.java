package com.vehicle.service;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vehicle.config.VehicleConfiguration;
import com.vehicle.constants.VehicleConstants;
import com.vehicle.dto.request.VehicleRequest;
import com.vehicle.helper.VehicleHelper;
import com.vehicle.utils.VehicleUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService, VehicleConstants
 {
	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VehicleHelper vehicleHelper;
	
	@Autowired
	private VehicleUtils vehicleUtils;
	
	@Autowired
	private VehicleConfiguration vehicleConfiguration;

	@Override
	public Map<String, Object>  getVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String vehicleId, String licensePlate, Map<String, String> logMap, Map<String, String> requestHeaderMap)
	{
		// TODO Auto-generated method stub
		String methodName = "getVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {
			logger.info("vehicleConfiguration customValue={}",vehicleConfiguration.getCustomValue());
			vehicleHelper.validateGetOrDeleteVehicle(vehicleId, licensePlate, response, logMap);

			if(!response.isEmpty()) {
				return response;
			}
			
			response=vehicleHelper.getVehicle(vehicleId, licensePlate, logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in VehicleServiceImpl getVehicle method {}",e.getMessage());
			vehicleUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			vehicleUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}


	@Override
	public Map<String, Object> createOrUpdateVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody VehicleRequest vehicleRequest, Map<String, String> logMap,  Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "createOrUpdateVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {			
			vehicleHelper.validateCreateOrUpdateVehicle(vehicleRequest, response, logMap);

			if(!response.isEmpty()) {
				return response;
			}
			
			response=vehicleHelper.createOrUpdateVehicle(vehicleRequest, logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in VehicleServiceImpl getVehicle method {}",e.getMessage());
			vehicleUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			vehicleUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}

	@Override
	public Map<String, Object> deleteVehicle(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String vehicleId, String licensePlate, Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "deleteVehicle";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {			
			vehicleHelper.validateGetOrDeleteVehicle(vehicleId, licensePlate, response, logMap);

			if(!response.isEmpty()) {
				return response;
			}
			
			response=vehicleHelper.deleteVehicle(vehicleId, licensePlate, logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in VehicleServiceImpl getVehicle method {}",e.getMessage());
			vehicleUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			vehicleUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}


	@Override
	public Map<String, Object> getAllVehicles(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "getAllVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {			
			
			response=vehicleHelper.getAllVehicles(logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in VehicleServiceImpl getAllVehicles method {}", e.getMessage());
			vehicleUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			vehicleUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}


	@Override
	public Map<String, Object> getAvailableVehicles(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "getAvailableVehicles";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {			
			
			response=vehicleHelper.getAvailableVehicles(logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in VehicleServiceImpl getAvailableVehicles method {}", e.getMessage());
			vehicleUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			vehicleUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;	}
	

}

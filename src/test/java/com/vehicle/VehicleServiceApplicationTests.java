package com.vehicle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.vehicle.config.VehicleConfiguration;
import com.vehicle.constants.VehicleConstants;
import com.vehicle.dto.request.VehicleRequest;
import com.vehicle.entity.Vehicle;
import com.vehicle.repository.VehicleRepository;
import com.vehicle.service.VehicleService;
import com.vehicle.utils.VehicleUtils;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
//@SpringBootTest(properties = { "spring.config.location=classpath:application-test.properties" })
//@TestPropertySource("classpath:application-test.properties")
class VehicleServiceApplicationTests implements VehicleConstants {

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private VehicleUtils vehicleUtils;

	//@Autowired
	//private VehicleConfiguration vehicleConfiguration;

	@MockBean
	private VehicleRepository vehicleRepository;

	private MockHttpServletRequest mockHttpRequest;
	private MockHttpServletResponse mockHttpResponse;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateVehicle() {
		String methodName = "testCreateVehicle";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		boolean assertFlag = false;

		Map<String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);
		VehicleRequest vr = new VehicleRequest();

		vr.setModel("BMW X7");
		vr.setType("SUV");
		vr.setLicensePlate("TN01BV7284");
		vr.setAvailability("Y");

		// System.out.println("testCreateVehicle
		// customValue="+testConfiguration.getCustomValue());

		response = vehicleService.createOrUpdateVehicle(mockHttpRequest, mockHttpResponse, vr, logMap,
				requestHeaderMap);

		System.out.println("testCreateVehicle response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testCreateVehicle assertFlag=" + assertFlag);

		assertTrue(assertFlag);
	}

	@Test
	public void testGetVehicle() {
		String methodName = "testGetVehicle";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		boolean findByVehicleId = false;
		boolean findByLicensePlate = false;

		boolean assertFlag = false;

		Map<String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);

		when(vehicleRepository.findByVehicleId("bmw-1"))
				.thenReturn(Optional.of(new Vehicle("bmw-1", "BMW X7", "SUV", "TNO1AB1234", "Y")));

		// findByVehicleId
		response = vehicleService.getVehicle(mockHttpRequest, mockHttpResponse, "bmw-1", null, logMap,
				requestHeaderMap);

		System.out.println("testGetVehicle findByVehicleId response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				findByVehicleId = true;
			}
		}

		when(vehicleRepository.findByLicensePlate("TNO1AB1234"))
				.thenReturn(Optional.of(new Vehicle("bmw-1", "BMW X7", "SUV", "TNO1AB1234", "Y")));

		// findByLicensePlate
		response = vehicleService.getVehicle(mockHttpRequest, mockHttpResponse, null, "TNO1AB1234", logMap,
				requestHeaderMap);

		System.out.println("testGetVehicle findByLicensePlate response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				findByLicensePlate = true;
			}
		}

		if (findByVehicleId && findByLicensePlate) {
			assertFlag = true;
		}

		System.out.println("testGetVehicle assertFlag=" + assertFlag);

		assertTrue(assertFlag);

	}

	@Test
	public void testGetAllVehicles() {
		String methodName = "testGetAllVehicles";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		boolean assertFlag = false;

		Map<String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);

		when(vehicleRepository.findAll())
				.thenReturn(Stream
						.of(new Vehicle("bmw-1", "BMW X7", "SUV", "TNO1AB1234", "Y"),
								new Vehicle("audi-1", "Audi Q5", "SUV", "APO1AB1234", "Y"))
						.collect(Collectors.toList()));

		response = vehicleService.getAllVehicles(mockHttpRequest, mockHttpResponse, logMap, requestHeaderMap);

		System.out.println("testGetAllVehicles response=" + response);

		// assertEquals(2, response.size());

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testGetAllVehicles assertFlag=" + assertFlag);

		assertTrue(assertFlag);
	}
	
	@Test
	public void testDeleteVehicle() {
		String methodName = "testDeleteVehicle";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		boolean assertFlag = false;

		Map<String, String> logMap = vehicleUtils.initiateLogMap(methodName, VEHICLE_APP, startTime, requestHeaderMap);

		Vehicle vehicle= new Vehicle("bmw-2", "BMW X5", "SUV", "MHO1AB1234", "Y");
				
		when(vehicleRepository.findByVehicleId("bmw-2"))
		.thenReturn(Optional.of(vehicle));

		doNothing().when(vehicleRepository).delete(vehicle);

		// deleteVehicle
		response = vehicleService.deleteVehicle(mockHttpRequest, mockHttpResponse, vehicle.getVehicleId(), null, logMap,
				requestHeaderMap);

		System.out.println("testDeleteVehicle deleteVehicle response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testDeleteVehicle assertFlag=" + assertFlag);

		assertTrue(assertFlag);

	}

}

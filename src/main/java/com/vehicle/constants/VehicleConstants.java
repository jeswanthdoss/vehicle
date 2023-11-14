package com.vehicle.constants;

public interface VehicleConstants {

	String VEHICLE_APP = "Vehicle_App";
	String START_UP_SUCCESS ="startUp Success.";

	String APP_NAME = "appName";
	String APP_DESC = "appDesc";
	String APP_VERSION = "1.0.0";
	
	String API_NAME = "apiName";
	String START_TIME = "startTime";
	String END_TIME = "endTime";
	String RESPONSE_TIME = "responseTime";
		
	String HTTP_STATUS = "httpStatus";

	String ACTION = "action";
	String LOG_TYPE = "logType";
	String EXIT = "EXIT";
	String METHOD_CLASS_ENTRY = "Entering method={} class={}";
	String METHOD_CLASS_EXIT = "Exiting method={} class={}";
	
	String CODE="code";
	String MSG="msg";
	String DESC="desc";
	
	String GET = "get";
	String CREATE = "create";
	String UPDATE = "update";
	String DELETE = "delete";
	
	String SUCCESS_CODE = "200";
	String SUCCESS_MSG = "SUCCESS";
	
	String FAILURE_CODE = "500";
	String FAILURE_MSG = "FAILURE";
	
	String WARNING_CODE = "400";
	String WARNING_MSG = "WARNING";
	
	String NOT_FOUND_CODE = "404";
	String RECORD_NOT_FOUND_MSG = "Record Not Found in the Database";
	
	String VEHICLE_ID_LICENSE_PLATE_MISSING_MSG = "Either vehicleId or licensePlate should be present";	
	String MODEL_MISSING_MSG = "model should be present for action=create";
	String TYPE_MISSING_MSG = "type should be present for action=create";
	String LICENSE_PLATE_MISSING_MSG = "licensePlate should be present for action=create";
	String AVAILABILITY_MISSING_MSG = "availability should be present for action=create";
	String MODEL_TYPE_LP_AVAIL_MISSING_MSG = "Atleast one out of model, type, licensePlate or availability should be present for action=update";

}

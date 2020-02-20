package com.systemk.spyder.Config.Handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemk.spyder.Dto.ApiResultConstans;
import com.systemk.spyder.Service.ErrorLogService;

@ControllerAdvice(basePackages = {"com.systemk.spyder.Controller.Api.Common",
								  "com.systemk.spyder.Controller.Api.Distribution",
								  "com.systemk.spyder.Controller.Api.Production",
								  "com.systemk.spyder.Controller.Api.Web"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonExceptionHandler {

	@Autowired
	private ErrorLogService errorLogService;

	@ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleException(HttpServletRequest request, Exception ex, Device device) throws Exception{

		Map<String, Object> obj = new HashMap<String, Object>();

		obj.put("resultCode", ApiResultConstans.ERROR);
	    obj.put("resultMessage", ApiResultConstans.ERROR_MESSAGE);
	    obj.put("errorMessage", ex.getMessage());

	    ex.printStackTrace();

	    errorLogService.save(request, ex, Integer.toString(ApiResultConstans.ERROR), device);

        return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
    }
}

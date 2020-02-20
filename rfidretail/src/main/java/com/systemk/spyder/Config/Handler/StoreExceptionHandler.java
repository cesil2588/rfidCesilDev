package com.systemk.spyder.Config.Handler;

import com.systemk.spyder.Service.ErrorLogService;
import com.systemk.spyder.Util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 아래 패키지는 CB to RFID M/W시 ResultUtil.setCBResult 형식으로 리턴해준다.
 * @author youdozi
 *
 */
@ControllerAdvice(basePackages = "com.systemk.spyder.Controller.Api.Store")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StoreExceptionHandler {

	@Autowired
	private ErrorLogService errorLogService;

	@ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleException(HttpServletRequest request, Exception ex, Device device) throws Exception{

	    ex.printStackTrace();

	    errorLogService.save(request, ex, "E", device);

        return new ResponseEntity<>(ResultUtil.setCommonResult("E", "에러가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

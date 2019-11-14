package com.zj.caoshangfei.common;

import com.zj.caoshangfei.common.exceptions.SystemException;
import com.zj.caoshangfei.common.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jin.zhang@fuwo.com on 2017/12/21.
 */
@ControllerAdvice
@RestController
public class CustomExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ModelAndView notFoundHtml() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/4xx.html");
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		return modelAndView;
	}

	@ExceptionHandler(value = SystemException.class)
	@ResponseBody
	public RestResult sysErrorHandler(HttpServletRequest req, SystemException e) {
		RestResult restResult = new RestResult();
		restResult.setMsg(e.getMessage());
		restResult.setCode(e.getCode());
		return restResult;
	}


}

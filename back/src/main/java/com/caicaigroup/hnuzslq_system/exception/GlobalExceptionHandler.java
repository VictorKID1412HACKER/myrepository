package com.caicaigroup.hnuzslq_system.exception;

import com.caicaigroup.hnuzslq_system.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理全局异常
     * @param se 异常
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result handle2(Exception e){
//        e.printStackTrace();
//        return Result.error(Constants.CODE_600, "未知错误，请联系管理员");
//    }

}

package com.kq.project.exception;

import com.kq.project.common.BaseResponse;
import com.kq.project.common.ErrorCode;
import com.kq.project.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
@SuppressWarnings("all")
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }

    /**
     * 捕获参数异常
     *
     * @param exception MethodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponse argumentException(MethodArgumentNotValidException exception) {
        String result = "";
        //查看MethodArgumentNotValidException类可以发现,异常的信息在BindingResult下List<ObjectError>中
        //我这里取第一条的信息进行展示,可根据实际项目情况自行修改
        //getDefaultMessage()获取的信息就是我们RequestVerificationCode中的message部分
        if (exception.getBindingResult() != null
                && exception.getBindingResult().getAllErrors() != null
                && exception.getBindingResult().getAllErrors().size() > 0) {
            result = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        //自定义的返回类,可根据实际项目情况自行修改
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, result);
    }

}

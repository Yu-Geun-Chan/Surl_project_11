package com.koreait.surl_project_11.global.exceptionHandlers;

import com.koreait.surl_project_11.global.exceptions.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandlers {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        log.debug("handleException 1");
        return e.getMessage();
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public String handleException(GlobalException ex) {
        log.debug("handleException 2");
        return ex.getMessage();
    }
}

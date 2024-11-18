package com.koreait.surl_project_11.global.exceptionHandlers;

import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.dto.Empty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandlers {
    @ExceptionHandler(GlobalException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST) -> ResponseEntity를 쓰면 안붙여도 된다.
    @ResponseBody
    public ResponseEntity<RsData<Empty>> handleException(GlobalException ex) {
        RsData<Empty> rsData = ex.getRsData();

        rsData.getStatusCode();

        return ResponseEntity
                .status(rsData.getStatusCode())
                .body(rsData);
    }
}

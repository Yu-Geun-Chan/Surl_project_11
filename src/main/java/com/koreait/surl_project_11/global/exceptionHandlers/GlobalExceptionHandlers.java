package com.koreait.surl_project_11.global.exceptionHandlers;

import com.koreait.surl_project_11.global.exceptions.GlobalException;
import com.koreait.surl_project_11.global.rq.Rq;
import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.dto.Empty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandlers {
    private Rq rq;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("resultCode", "500-1");
        body.put("statusCode", 500);
        body.put("msg", ex.getLocalizedMessage());
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        body.put("data", data);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        data.put("trace", sw.toString().replace("\t", "    ").split("\\r\\n"));
        String path = rq.getCurrentUrlPath();
        data.put("path", path);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GlobalException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST) -> ResponseEntity를 쓰면 안붙여도 된다.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<RsData<Empty>> handleException(GlobalException ex) {

        RsData<Empty> rsData = ex.getRsData();

        return ResponseEntity
                .status(rsData.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(rsData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST) -> ResponseEntity를 쓰면 안붙여도 된다.
    @ResponseBody
    public ResponseEntity<RsData<Empty>> handleException(MethodArgumentNotValidException ex) {

        String resultCode = "400-" + ex.getBindingResult().getFieldError().getCode();
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();

        return handleException(
                new GlobalException(
                        resultCode,
                        msg
                )
        );
    }
}

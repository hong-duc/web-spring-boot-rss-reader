/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dman.hongduc.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author duc
 */
public class ResponseError{
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user khong ton tai")
    public static class UserNotExists extends RuntimeException {
    }
    
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "không thể lấy rss từ url này hoặc không hỗ trợ")
    public static class InvalidUrl extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "có thể không phải là url")
    public static class NotGoodUrl extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "có lỗi ở bên server")
    public static class ServerError extends RuntimeException {
    }
}

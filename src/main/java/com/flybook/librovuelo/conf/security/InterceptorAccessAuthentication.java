package com.flybook.librovuelo.conf.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class InterceptorAccessAuthentication implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(InterceptorAccessAuthentication.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().contains("/desactivar-notificacion")) {
            Boolean accessVerified = (Boolean) request.getSession().getAttribute("accessVerified");
            if(accessVerified == null || !accessVerified){
                request.getSession().setAttribute("accessVerified", false);
                request.getSession().setAttribute("pendingRequest", request.getRequestURI().substring(request.getContextPath().length()));
                response.sendRedirect(request.getContextPath()+"/login-access-request");
                return false;
            }else{
                request.getSession().setAttribute("accessVerified", false);
                request.getSession().setAttribute("pendingRequest", null);
            }
        }

        if(request.getRequestURI().contains("login-access-request") && request.getSession().getAttribute("pendingRequest") == null) {
            response.sendRedirect(request.getContextPath()+"/");
            return false;
        }

        return true;
    }

}

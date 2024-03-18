package com.example.demo.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ResultEntity;
import com.example.demo.utils.JwtUtils;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ResultEntity resultEntity = new ResultEntity<>();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getRequestURI().toString();

        if (!url.contains("email") || !url.contains("list")||!url.contains("group")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = req.getHeader("token");

        if (!StringUtils.hasLength(jwt)) {
            ResultEntity error = resultEntity.construct("NOT_LOGIN", false);
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }

        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.error("Token verification failed: {}", e.getMessage());
            ResultEntity error = resultEntity.construct("NOT_LOGIN", false);
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        } finally {
            // Close any resources if needed
        }

        filterChain.doFilter(request, response);
    }
}

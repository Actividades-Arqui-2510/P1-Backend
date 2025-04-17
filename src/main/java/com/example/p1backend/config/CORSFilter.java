package com.example.p1backend.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, SOAPAction");
        httpResponse.setHeader("Access-Control-Max-Age", "86400");

        chain.doFilter(request, response);
    }
}

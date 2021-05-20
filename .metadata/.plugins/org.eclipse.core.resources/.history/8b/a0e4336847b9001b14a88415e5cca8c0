package com.nido.infrastructure.oauth.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
// configura la url de acceso para realizar el login 
/**
 * https://blog.marcosbarbero.com/centralized-authorization-jwt-spring-boot2/
 * url : localhost:9001/auth-api/users/token
 * body: 
 * {
    "username": "caramelo",
    "password": "caramelo"
	}

Basic auth:
	username = clientId
	password = clientId
 * @author Sr.Cekas
 *
 */
@Slf4j
@Component
public class JsonToUrlEncodedAuthenticationFilter extends OncePerRequestFilter {
	

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     *
     */
    @Override
    protected void doFilterInternal(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		FilterChain filterChain) throws ServletException, IOException {

        if (Objects.equals(request.getServletPath(), "/oauth/token") && Objects.equals(request.getContentType(), "application/json")) {
        	
            byte[] json = ByteStreams.toByteArray(request.getInputStream());

            Map<String, String> jsonMap = new ObjectMapper().readValue(json, Map.class);
            // agrego grant_type para que no sea necesario mandarlo en el body
            jsonMap.put("grant_type", "password"); 
            
            Map<String, String[]> parameters
                    = jsonMap.entrySet().stream()
                    .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    e -> new String[]{e.getValue()})
                    );

            HttpServletRequest requestWrapper = new RequestWrapper(request, parameters);
            filterChain.doFilter(requestWrapper, response);
        } else {
        	log.info("\n\nrequest {} \n\n", request.getServletPath());
            filterChain.doFilter(request, response);
        }
    }

    private class RequestWrapper extends HttpServletRequestWrapper {

        private final Map<String, String[]> params;

        RequestWrapper(HttpServletRequest request, Map<String, String[]> params) {
            super(request);
            this.params = params;
        }

        @Override
        public String getParameter(String name) {
            if (this.params.containsKey(name)) {
                return this.params.get(name)[0];
            }
            return "";
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return this.params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return new Enumerator<>(params.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }
    }

}
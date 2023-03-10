package spring.custom.security.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.custom.security.ErrorResponse;
import spring.custom.security.user.UserDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class BasicAuthFilter extends OncePerRequestFilter {

    private String extractToken(String header) {
       String[] split = header.split("Basic");
       return split[1].trim();
    }

    private UserDto decodeToken(String token) {
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);
        String username = decodedString.split(":")[0];
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        return userDto;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.contains("Basic")) {
            try {
                String token = extractToken(authorization);

                UserDto userDto = decodeToken(token);
                userDto.addRole(Authority.create("ROLE_USER"));

                request.setAttribute("userDto", userDto);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                setErrorResponse(response, 401, "올바르지 않은 토큰입니다.");
            }
        } else {
            setErrorResponse(response, 401, "토큰이 존재하지 않습니다");
        }
    }

    private void setErrorResponse(HttpServletResponse response, int httpStatus, String message) {
        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            ErrorResponse errorResponse = new ErrorResponse(httpStatus, message);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

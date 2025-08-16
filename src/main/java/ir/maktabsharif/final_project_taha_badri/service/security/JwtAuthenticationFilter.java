package ir.maktabsharif.final_project_taha_badri.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import ir.maktabsharif.final_project_taha_badri.domain.dto.request.LoginRequest;
import ir.maktabsharif.final_project_taha_badri.domain.entity.base.Person;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final PathPatternRequestMatcher loginPath = PathPatternRequestMatcher
            .withDefaults()
            .matcher(HttpMethod.POST, "/api/auth/v1/login");
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtil jwtUtil;

    protected JwtAuthenticationFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        super(loginPath, authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    public Authentication authenticationMapper(HttpServletRequest httpServletRequest) throws IOException {
        LoginRequest loginDTO = objectMapper.readValue(httpServletRequest.getInputStream(), LoginRequest.class);
        return new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, ServletException, IOException {
        Authentication authentication = authenticationMapper(request);
        if (authentication == null) {
            return null;
        }
        Authentication authenticate = getAuthenticationManager().authenticate(authentication);
        if (authenticate == null) {
            throw new ServletException("Authentication Failed");
        }
        return authenticate;
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException{
        Person principal = (Person) authResult.getPrincipal();
        String token = jwtUtil.generateToken(principal);
        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Invalid username or password\"}");
    }
}

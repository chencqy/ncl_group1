package uk.ac.ncl.rbac.filter;

import uk.ac.ncl.rbac.config.TokenAuthenticationHelper;
import uk.ac.ncl.rbac.domain.ErrorDetails;
import uk.ac.ncl.rbac.model.User;
import uk.ac.ncl.rbac.service.LoginCountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.HtmlUtils;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final LoginCountService loginCountService;

    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, LoginCountService loginCountService) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        this.loginCountService = loginCountService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        loginCountService.judgeLoginCount(httpServletRequest);
        User user = new ObjectMapper().readValue(httpServletRequest.getInputStream(), User.class);
        String username = user.getUsername();
        username = HtmlUtils.htmlEscape(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username,
                user.getPassword(),
                user.getAuthorities()
        );
        return getAuthenticationManager().authenticate(token);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        loginCountService.cleanLoginCount(request);
        TokenAuthenticationHelper.addAuthentication(request, response ,authResult);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        loginCountService.addLoginCount(request, 1);
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDetails.setMessage("Login failed！");
        errorDetails.setError(failed.getLocalizedMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setPath(request.getServletPath());
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(errorDetails));
        out.flush();
        out.close();
    }
}

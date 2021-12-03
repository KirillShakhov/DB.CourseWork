package ru.itmo.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import ru.itmo.services.UserDataService;

import javax.servlet.*;
import java.io.IOException;

@Order(1)
public class TokenFilter implements Filter {

    private final UserDataService userDataService;
    @Autowired
    public TokenFilter(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        servletResponse.getOutputStream().flush();
        try {
            if (servletRequest.getParameterMap().containsKey("login")) {
                String token = servletRequest.getParameterMap().get("login")[0];
                if (userDataService.getByLogin(token).isPresent()) {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
                else{
                    servletResponse.getOutputStream().println("{\"status\":\"error\",\"message\":\"your token is not valid\"}");
                }
            } else {
                servletResponse.getOutputStream().println("{\"status\":\"error\",\"message\":\"where is token?\"}");
            }
        }catch (Exception e){
            servletResponse.getOutputStream().println("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}

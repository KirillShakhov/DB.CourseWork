package ru.itmo.filter;

import ru.itmo.services.TokenDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

@Order(1)
public class TokenFilter implements Filter {

    private final TokenDataService tokenDataService;
    @Autowired
    public TokenFilter(TokenDataService tokenDataService) {
        this.tokenDataService = tokenDataService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.getOutputStream().flush();
        try {
            if (servletRequest.getParameterMap().containsKey("token")) {
                String token = servletRequest.getParameterMap().get("token")[0];
                if (tokenDataService.checkById(token)){
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

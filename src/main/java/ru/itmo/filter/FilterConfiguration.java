package ru.itmo.filter;

import ru.itmo.services.TokenDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    private final TokenDataService tokenDataService;
    @Autowired
    public FilterConfiguration(TokenDataService tokenDataService) {
        this.tokenDataService = tokenDataService;
    }
    @Bean
    public FilterRegistrationBean<TokenFilter> loggingFilter(){
        FilterRegistrationBean<TokenFilter> registrationBean
                = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenFilter(tokenDataService));
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

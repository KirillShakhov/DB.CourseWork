package ru.itmo.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.itmo.services.UserDataService;

@Configuration
public class FilterConfiguration {
    private final UserDataService userDataService;
    @Autowired
    public FilterConfiguration(UserDataService userDataService) {
        this.userDataService = userDataService;
    }
//    @Bean
//    public FilterRegistrationBean<TokenFilter> loggingFilter(){
//        FilterRegistrationBean<TokenFilter> registrationBean
//                = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new TokenFilter(tokenDataService));
//        registrationBean.addUrlPatterns("/api/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
}

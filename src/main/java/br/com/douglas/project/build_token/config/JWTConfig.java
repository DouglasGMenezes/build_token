package br.com.douglas.project.build_token.config;

import br.com.douglas.project.build_token.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    @Bean
    public FilterRegistrationBean<TokenFilter> routeFilter() {
        FilterRegistrationBean<TokenFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new TokenFilter());
        filterFilterRegistrationBean.addUrlPatterns("/auth/user/*");

        return filterFilterRegistrationBean;
    }
}

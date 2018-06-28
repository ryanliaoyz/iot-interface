package org.totem.iot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.totem.common.service.CachedService;
import org.totem.common.service.impl.RedisServiceImpl;
import org.totem.common.utils.AuthInterceptor;
import org.totem.components.mail.MailService;
import org.totem.components.mail.impl.MailServiceImpl;
import org.totem.iot.utils.IotInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Bean
    public HandlerInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Bean
    public HandlerInterceptor iotInterceptor(){
        return new IotInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(iotInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");
    }

    /**
    * 重置Session时长
    * @return
    */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(7200);//单位为S
            }
        };
    }

    @Bean
    public MailService mailService(){
        MailServiceImpl mailService = new MailServiceImpl();
        mailService.setMailSender(mailSender);
        mailService.setFreeMarkerConfigurer(freeMarkerConfigurer);
        return mailService;
    }

    @Bean
    public CachedService cachedService(){
        RedisServiceImpl cachedService = new RedisServiceImpl();
        return cachedService;
    }

}
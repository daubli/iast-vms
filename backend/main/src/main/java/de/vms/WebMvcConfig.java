package de.vms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.vms.interceptor.AgentCommunicationInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private AgentCommunicationInterceptor agentCommunicationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(agentCommunicationInterceptor).addPathPatterns("/api/internal/**");
    }

    @Autowired
    public void setAgentCommunicationInterceptor(AgentCommunicationInterceptor agentCommunicationInterceptor){
        this.agentCommunicationInterceptor = agentCommunicationInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
                .addResourceLocations("classpath:/static/", "file:../../frontend/dist/")
                .resourceChain(false);
    }
}
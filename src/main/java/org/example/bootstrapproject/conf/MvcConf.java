package org.example.bootstrapproject.conf;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class MvcConf implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("/user/profile");
        registry.addViewController("/admin").setViewName("/admin/users");
        int x=0;
        System.out.println(x);
        int b[]=new int[]{1,2,3,4};
        for (int i = 0; i <b.length; i++) {
            if (b[i]!=0){
                System.out.println("Все ок");
            }
        }
    }
}
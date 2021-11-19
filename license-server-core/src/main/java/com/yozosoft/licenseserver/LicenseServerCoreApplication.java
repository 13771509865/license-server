package com.yozosoft.licenseserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.yozosoft"})
@MapperScan("com.yozosoft.licenseserver.dao")
@ServletComponentScan("com.yozosoft.licenseserver")
public class LicenseServerCoreApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LicenseServerCoreApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LicenseServerCoreApplication.class, args);
    }

}

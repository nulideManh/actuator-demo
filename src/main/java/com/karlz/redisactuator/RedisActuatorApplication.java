package com.karlz.redisactuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class RedisActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisActuatorApplication.class, args);
    }

}

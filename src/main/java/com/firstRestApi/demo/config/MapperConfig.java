package com.firstRestApi.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Contains bean definitions for the Spring container.
public class MapperConfig {

    // bean creates singletons. instead of we have different model mappers, this is created and stored in spring container so
    // if more than one classes need it , same instance is sent. scope can be changed though , lifecycle.
    @Bean // Registers a Spring-managed object.
    public ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}

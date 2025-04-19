package com.suhas.NotesApp.mapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.config.Configuration.AccessLevel;

import java.util.logging.Logger;

@Configuration
@Slf4j
public class CustomModelMapper {
    private static final Logger logger=Logger.getLogger(CustomModelMapper.class.getName());
    @Bean
    public ModelMapper modelMapper()
    {
        logger.info("Creating Model Mapper Object");
         ModelMapper mapper= new ModelMapper();
                 mapper.getConfiguration()
                 .setMatchingStrategy(MatchingStrategies.STANDARD)
                 .setFieldAccessLevel(AccessLevel.PRIVATE);
         return mapper;
    }
}

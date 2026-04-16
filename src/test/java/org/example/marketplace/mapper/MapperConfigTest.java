package org.example.marketplace.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapperConfigTest {

    private final MapperConfig mapperConfig = new MapperConfig();

    @Test
    void modelMapper() {
        ModelMapper mapper = mapperConfig.modelMapper();

        assertNotNull(mapper);

        var configuration = mapper.getConfiguration();

        assertEquals(MatchingStrategies.STRICT, configuration.getMatchingStrategy());
        assertTrue(configuration.isFieldMatchingEnabled());
        assertTrue(configuration.isSkipNullEnabled());
        assertEquals(Configuration.AccessLevel.PRIVATE, configuration.getFieldAccessLevel());
    }
}
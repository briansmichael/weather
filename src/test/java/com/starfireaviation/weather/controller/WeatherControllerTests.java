/*
 *  Copyright (C) 2022 Starfire Aviation, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.starfireaviation.weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starfireaviation.weather.config.WeatherProperties;
import com.starfireaviation.weather.exception.InvalidPayloadException;
import com.starfireaviation.weather.exception.ResourceNotFoundException;
import com.starfireaviation.weather.model.METAR;
import com.starfireaviation.weather.model.WeatherProduct;
import com.starfireaviation.weather.model.WeatherProductRepository;
import com.starfireaviation.weather.service.WeatherService;
import com.starfireaviation.weather.util.SSLUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class WeatherControllerTests {

    /**
     * WeatherService.
     */
    @Mock
    WeatherService weatherService;

    /**
     * WeatherProperties.
     */
    @Mock
    WeatherProperties weatherProperties;

    /**
     * WeatherController.
     */
    private WeatherController weatherController;

    /**
     * Test setup.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        Mockito
                .doReturn("KCNI,KGVL,KVPC,KJCA,KRYY,KLZU,KWDR,KPUJ,KMGE,KPDK,KFTY,"
                        + "KCTJ,KCVC,KATL,KCCO,KFFC,KHMP,KLGC,KOPN")
                .when(weatherProperties).getAtlantaIcaoCodes();

        weatherController = new WeatherController(weatherService, weatherProperties);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", null);

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_Atlanta() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("ATLANTA", null);

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.atLeast(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.atLeast(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_ObservedFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("observed"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_RawTextFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("raw_text"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_BarometerFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("barometer"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_CeilingFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("ceiling"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_CloudsFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("clouds"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_DewpointFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("dewpoint"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_ElevationFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("elevation"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_FlightCategoryFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("flight_category"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_HumidityPercentFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("humidity_percent"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_TemperatureFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("temperature"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_VisibilityFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("visibility"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_WindFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("wind"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     * @throws ResourceNotFoundException when things go wrong
     * @throws InvalidPayloadException when things go wrong
     */
    @Test
    public void testMetar_UnknownFilter() throws ResourceNotFoundException, InvalidPayloadException, JsonProcessingException {
        weatherService.metar("KATL", List.of("unknown"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.anyString(), Mockito.eq(METAR.class));
        Mockito.verifyNoMoreInteractions(objectMapper);

        Mockito.verify(weatherProductRepository, Mockito.times(1)).findByKey(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(weatherProductRepository);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities);
    }

    /**
     * Test metar.
     *
     */
    @Test
    public void testMetar_InvalidStation() {
        InvalidPayloadException ipe = Assertions.assertThrows(
                InvalidPayloadException.class,
                () -> weatherService.metar("KDEN", null),
                "Expected InvalidPayloadException to throw, but it did not"
        );

        Assertions.assertTrue(ipe.getMessage().contains("Please provide an accepted station identifier"));

        Mockito.verify(weatherProperties, Mockito.times(1)).getAtlantaIcaoCodes();
        Mockito.verifyNoMoreInteractions(weatherProperties);

        Mockito.verifyNoInteractions(restTemplate, sslUtilities, objectMapper, weatherProductRepository);
    }
}

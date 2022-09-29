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

import com.starfireaviation.weather.config.WeatherProperties;
import com.starfireaviation.weather.exception.InvalidPayloadException;
import com.starfireaviation.weather.exception.ResourceNotFoundException;
import com.starfireaviation.weather.model.METAR;
import com.starfireaviation.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.starfireaviation.weather.service.WeatherService.ATLANTA;
import static com.starfireaviation.weather.service.WeatherService.INVALID_STATION_MSG;

@Slf4j
@RestController
@RequestMapping("/metars")
public class WeatherController {

    /**
     * WeatherService.
     */
    private final WeatherService weatherService;

    /**
     * WeatherProperties.
     */
    private final WeatherProperties weatherProperties;

    /**
     * Constructor to initialize services.
     *
     * @param wService WeatherService
     * @param wProperties WeatherProperties
     */
    @Autowired
    public WeatherController(final WeatherService wService, final WeatherProperties wProperties) {
        weatherService = wService;
        weatherProperties = wProperties;
    }

    /**
     * Required implementation.
     *
     * @param icao station code
     * @param dataList attributes to be returned in response
     * @return METAR
     * @throws ResourceNotFoundException when METAR is not found
     * @throws InvalidPayloadException when an invalid station code is provided
     */
    public List<METAR> metar(
            @PathVariable("icao") final String icao,
            @RequestParam(required = false, value = "data") final List<String> dataList)
            throws ResourceNotFoundException, InvalidPayloadException {
        final List<METAR> metars = new ArrayList<>();
        if (ATLANTA.equalsIgnoreCase(icao)) {
            Arrays.asList(weatherProperties.getAtlantaIcaoCodes().split(",")).forEach(code -> {
                try {
                    metars.add(weatherService.getMETAR(code));
                } catch (ResourceNotFoundException e) {
                    log.warn("No METAR information found for ICAO Code {}", code);
                }
            });
        } else if (weatherService.isValidStation(icao.toUpperCase())) {
            metars.add(weatherService.getMETAR(icao.toUpperCase()));
        }
        if (CollectionUtils.isNotEmpty(metars)) {
            return weatherService.filterAttributes(metars, dataList);
        }
        throw new InvalidPayloadException(String.format(INVALID_STATION_MSG, icao));
    }


}

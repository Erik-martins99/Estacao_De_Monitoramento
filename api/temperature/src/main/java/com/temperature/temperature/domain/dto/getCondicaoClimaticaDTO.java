package com.temperature.temperature.domain.dto;

import com.temperature.temperature.domain.entityes.CondicaoClimatica;

public record getCondicaoClimaticaDTO(String data,
                                      Double temperatura,
                                      Integer luminosidade){

    public getCondicaoClimaticaDTO(CondicaoClimatica data) {
        this(data.getData(), data.getTemperatura(), data.getLuminosidade());
    }
}

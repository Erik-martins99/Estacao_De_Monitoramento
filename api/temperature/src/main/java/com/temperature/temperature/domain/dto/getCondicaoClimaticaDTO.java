package com.temperature.temperature.domain.dto;

import com.temperature.temperature.domain.entityes.CondicaoClimatica;

public record getCondicaoClimaticaDTO(String data,
                                      Double temperatura,
                                      Integer luminosidade,
                                      Double umidade,
                                      Double indiceDeCalor,
                                      int som){

    public getCondicaoClimaticaDTO(CondicaoClimatica data) {
        this(data.getData(),
                data.getTemperatura(),
                data.getLuminosidade(),
                data.getUmidade(),
                data.getIndiceDeCalor(),
                data.getSom());
    }
}

package com.temperature.temperature.domain.service;

import com.temperature.temperature.domain.dto.RegistraCondicaoClimaticaDTO;
import com.temperature.temperature.domain.dto.getCondicaoClimaticaDTO;
import com.temperature.temperature.domain.entityes.CondicaoClimatica;
import com.temperature.temperature.repository.CondicaoClimaticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondicaoClimaticaService {

    @Autowired
    private CondicaoClimaticaRepository repository;

    public void registraCondicaoClimatica(RegistraCondicaoClimaticaDTO data) {
        repository.save(new CondicaoClimatica(data));
    }

    public List<getCondicaoClimaticaDTO> getCondicaoClimatica() {
        return repository.findTop10().stream().map(getCondicaoClimaticaDTO::new).toList();
    }

    public List<getCondicaoClimaticaDTO> getCondicaoClimaticaPorHora() {
        return repository.findTop10Forhours().stream().map(getCondicaoClimaticaDTO::new).toList();
    }

    public List<getCondicaoClimaticaDTO> getCondicaoClimaticaPorDia() {
        return repository.findTop10ForDay().stream().map(getCondicaoClimaticaDTO::new).toList();
    }
}

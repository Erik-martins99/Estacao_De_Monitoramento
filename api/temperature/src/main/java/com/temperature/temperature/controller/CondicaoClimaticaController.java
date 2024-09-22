package com.temperature.temperature.controller;

import com.temperature.temperature.domain.dto.RegistraCondicaoClimaticaDTO;
import com.temperature.temperature.domain.dto.getCondicaoClimaticaDTO;
import com.temperature.temperature.domain.service.CondicaoClimaticaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/condicaoClimatica")
@CrossOrigin("*")
public class CondicaoClimaticaController {

    @Autowired
    private CondicaoClimaticaService service;

    @PostMapping
    @Transactional
    public ResponseEntity dataRegister(@RequestBody RegistraCondicaoClimaticaDTO data) {
        service.registraCondicaoClimatica(data);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<getCondicaoClimaticaDTO>> getData() {
        var condicoes = service.getCondicaoClimatica();
        return ResponseEntity.ok(condicoes);
    }

    //Verificação por hora
    @GetMapping("/hora")
    public ResponseEntity<List<getCondicaoClimaticaDTO>> getDataForHours() {
        var condicoes = service.getCondicaoClimaticaPorHora();
        return ResponseEntity.ok(condicoes);
    }

    //Verificação por dia
    @GetMapping("/dia")
    public ResponseEntity<List<getCondicaoClimaticaDTO>> getDataForDays() {
        var condicoes = service.getCondicaoClimaticaPorDia();
        return ResponseEntity.ok(condicoes);
    }
}

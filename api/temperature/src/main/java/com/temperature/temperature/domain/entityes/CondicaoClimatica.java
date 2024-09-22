package com.temperature.temperature.domain.entityes;

import com.temperature.temperature.domain.dto.RegistraCondicaoClimaticaDTO;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "condicoes_climaticas")
public class CondicaoClimatica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    private Double temperatura;
    private Integer luminosidade;

    public CondicaoClimatica() {}

    public CondicaoClimatica(RegistraCondicaoClimaticaDTO data) {
        this.data = String.valueOf(LocalDateTime.now()).substring(0,19).replace("T", " ");
        this.temperatura = data.temperatura();
        this.luminosidade = data.luminosidade();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(Integer luminosidade) {
        this.luminosidade = luminosidade;
    }
}

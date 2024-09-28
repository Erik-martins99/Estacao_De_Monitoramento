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
    private Double umidade;
    private Double indiceDeCalor;

    public CondicaoClimatica() {}

    public CondicaoClimatica(RegistraCondicaoClimaticaDTO data) {
        this.data = String.valueOf(LocalDateTime.now()).substring(0,19).replace("T", " ");
        this.temperatura = data.temperatura();
        this.luminosidade = data.luminosidade();
        this.umidade = data.umidade();
        this.indiceDeCalor = calculaIndiceDeCalor(this.temperatura, this.umidade);
    }

    public double calculaIndiceDeCalor(double t, double u) {
        double tempF = (t * 9/5) + 32;
        double hi = 1.1 * tempF - 10.3 + 0.047 * u;
        return Math.round((hi - 32) * 5/9);
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

    public Double getUmidade() {
        return umidade;
    }

    public void setUmidade(Double umidade) {
        this.umidade = umidade;
    }

    public Double getIndiceDeCalor() {
        return indiceDeCalor;
    }

    public void setIndiceDeCalor(Double indiceDeCalor) {
        this.indiceDeCalor = indiceDeCalor;
    }
}



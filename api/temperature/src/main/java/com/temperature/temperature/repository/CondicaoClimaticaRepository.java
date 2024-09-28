package com.temperature.temperature.repository;

import com.temperature.temperature.domain.entityes.CondicaoClimatica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CondicaoClimaticaRepository extends JpaRepository<CondicaoClimatica, Long> {
    @Query(value = "SELECT * FROM (SELECT * FROM condicoes_climaticas ORDER BY id DESC LIMIT 10) AS recent_records ORDER BY id ASC;", nativeQuery = true)
    List<CondicaoClimatica> findTop10();

    @Query(value = "SELECT SUBSTRING(data, 1, 13) AS data, ROUND(AVG(temperatura), 2) AS temperatura, ROUND(AVG(luminosidade), 2) AS luminosidade, ROUND(AVG(umidade), 2) AS umidade, ROUND(AVG(indice_de_calor), 2) AS indice_de_calor, MIN(id) AS id FROM estacao_meteorV1.condicoes_climaticas GROUP BY SUBSTRING(data, 1, 13);", nativeQuery = true)
    List<CondicaoClimatica> findTop10Forhours();

    @Query(value = "SELECT SUBSTRING(data, 1, 10) AS data, ROUND(AVG(temperatura), 2) AS temperatura, ROUND(AVG(luminosidade), 2) AS luminosidade, ROUND(AVG(umidade), 2) AS umidade, ROUND(AVG(indice_de_calor), 2) AS indice_de_calor, MIN(id) AS id FROM estacao_meteorV1.condicoes_climaticas GROUP BY SUBSTRING(data, 1, 10);", nativeQuery = true)
    List<CondicaoClimatica> findTop10ForDay();
}

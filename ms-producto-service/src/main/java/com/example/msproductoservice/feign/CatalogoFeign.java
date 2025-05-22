package com.example.msproductoservice.feign;

import com.example.msproductoservice.dto.CategoriaDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo-service", path = "/categoria")
public interface CatalogoFeign {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "categoriaListarPorIdCB", fallbackMethod = "fallbackCategoriaById")
    ResponseEntity<CategoriaDto> buscarCategoria(@PathVariable Integer id);

    default ResponseEntity<CategoriaDto> fallbackCategoriaById(Integer id, Exception e) {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setNombre("Servicio no disponible de catálogo");
        return ResponseEntity.ok(categoriaDto);
    }
}
package com.example.mscatalogoservice.repository;

import com.example.mscatalogoservice.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}

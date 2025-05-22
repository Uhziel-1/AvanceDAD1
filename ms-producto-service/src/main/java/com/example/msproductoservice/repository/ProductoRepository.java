package com.example.msproductoservice.repository;

import com.example.msproductoservice.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}

package com.example.msproductoservice.service.impl;

import com.example.msproductoservice.entity.Producto;
import com.example.msproductoservice.feign.CatalogoFeign;
import com.example.msproductoservice.repository.ProductoRepository;
import com.example.msproductoservice.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private CatalogoFeign catalogoFeign;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        System.out.println("Service Impl" + producto);
        System.out.println("Repository" + productoRepository.save(producto));
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> listarPorId(Integer id) {
        Producto producto = productoRepository.findById(id).get();
        producto.setCategoriaDto(catalogoFeign.buscarCategoria(producto.getCategoriaId()).getBody());
        return productoRepository.findById(id);
    }

    @Override
    public void eliminarPorId(Integer id) {
        productoRepository.deleteById(id);
    }
}

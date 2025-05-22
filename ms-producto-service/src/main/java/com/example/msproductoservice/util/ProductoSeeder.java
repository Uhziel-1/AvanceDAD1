package com.example.msproductoservice.util;

import com.example.msproductoservice.entity.Producto;
import com.example.msproductoservice.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductoSeeder implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public ProductoSeeder(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) {
        // Verificamos si ya existen datos para no duplicar
        if (productoRepository.count() == 0) {
            Producto prod1 = new Producto(null, "Smartphone Samsung Galaxy S23", "Smartphone de alta gama con pantalla AMOLED y cámara de 108 MP", 3500.00, 13, null);
            Producto prod2 = new Producto(null, "Laptop Dell XPS 15", "Laptop potente con Intel Core i7, 16GB RAM y SSD de 512GB", 8000.00, 12, null);
            Producto prod3 = new Producto(null, "Smart TV LG OLED 55\"", "Televisor 4K Ultra HD con inteligencia artificial y control por voz", 4500.00, 1, null);
            Producto prod4 = new Producto(null, "Camiseta Deportiva Nike", "Camiseta de poliéster transpirable para entrenamientos intensos", 100.00, 2, null);
            Producto prod5 = new Producto(null, "Juego de Ollas de Acero Inoxidable", "Set de 5 ollas de acero inoxidable con tapa de vidrio templado", 300.00, 3, null);
            Producto prod6 = new Producto(null, "Muñeca Barbie Dreamhouse", "Muñeca de colección con accesorios y casa de ensueño", 250.00, 4, null);
            Producto prod7 = new Producto(null, "Libro: El Principito", "Novela clásica ilustrada por Antoine de Saint-Exupéry", 30.00, 5, null);
            Producto prod8 = new Producto(null, "Bicicleta de Montaña", "Bicicleta todo terreno con suspensión delantera y frenos de disco", 1500.00, 6, null);
            Producto prod9 = new Producto(null, "Suplemento Vitaminas Complejo B", "Suplemento multivitamínico para energía y metabolismo", 60.00, 7, null);
            Producto prod10 = new Producto(null, "Perfume Calvin Klein", "Fragancia masculina amaderada y fresca", 200.00, 8, null);
            Producto prod11 = new Producto(null, "Llantas Michelin R16", "Llantas de alto rendimiento para todo tipo de terrenos", 1000.00, 9, null);
            Producto prod12 = new Producto(null, "Comida para Perros 20Kg", "Alimento completo y balanceado para perros adultos", 180.00, 10, null);
            Producto prod13 = new Producto(null, "Taladro Inalámbrico Bosch", "Taladro con batería de 20V, ideal para trabajos de bricolaje", 400.00, 11, null);
            Producto prod14 = new Producto(null, "Auriculares Sony WH-1000XM4", "Auriculares con cancelación de ruido y sonido Hi-Res", 800.00, 14, null);
            Producto prod15 = new Producto(null, "Manguera Extensible para Jardín", "Manguera retráctil con boquilla de 7 funciones", 120.00, 15, null);
            Producto prod16 = new Producto(null, "Paquete de Cereales Kellogg's", "Cereales enriquecidos con vitaminas y minerales", 30.00, 16, null);
            Producto prod17 = new Producto(null, "Cerveza Artesanal 750ml", "Cerveza elaborada con ingredientes naturales", 20.00, 17, null);
            Producto prod18 = new Producto(null, "Refrigerador Samsung No Frost", "Refrigerador de alta capacidad con tecnología inverter", 5000.00, 18, null);
            Producto prod19 = new Producto(null, "Anillo de Oro 18K", "Anillo elegante con incrustaciones de diamantes", 2500.00, 19, null);
            Producto prod20 = new Producto(null, "PlayStation 5", "Consola de videojuegos de nueva generación con gráficos 4K", 5500.00, 20, null);

            productoRepository.save(prod1);
            productoRepository.save(prod2);
            productoRepository.save(prod3);
            productoRepository.save(prod4);
            productoRepository.save(prod5);
            productoRepository.save(prod6);
            productoRepository.save(prod7);
            productoRepository.save(prod8);
            productoRepository.save(prod9);
            productoRepository.save(prod10);
            productoRepository.save(prod11);
            productoRepository.save(prod12);
            productoRepository.save(prod13);
            productoRepository.save(prod14);
            productoRepository.save(prod15);
            productoRepository.save(prod16);
            productoRepository.save(prod17);
            productoRepository.save(prod18);
            productoRepository.save(prod19);
            productoRepository.save(prod20);

            System.out.println("Datos de productos insertados correctamente.");
        } else {
            System.out.println("Los productos ya existen, no se insertaron datos.");
        }
    }
}
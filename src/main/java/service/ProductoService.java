package service;

import dto.PageResponseDTO;
import dto.ProductRequestDTO;
import dto.ProductResponseDTO;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import modelo.ProductoModel;
import repository.ProductoRepository;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProductoService {

    @Inject
    ProductoRepository productoRepository;

    // ===== Crear =====
    @Transactional
    public ProductResponseDTO crear(ProductRequestDTO dto){
        ProductoModel entity = new ProductoModel(
                dto.nombre(),
                dto.descripcion(),
                dto.categoria(),
                dto.precio(),
                dto.stock()
        );
        productoRepository.persist(entity);
        return ProductResponseDTO.fromEntity(entity);
    }

    // ===== Obtener por ID =====
    public ProductResponseDTO obtenerId(Long id){
        ProductoModel entity = productoRepository.findById(id);
        if(entity == null){
            throw new NotFoundException("Producto no encontrado con id " + id );
        }
        return ProductResponseDTO.fromEntity(entity);
    }

    // ===== Actualizar =====
    public ProductResponseDTO actualizar(Long id,ProductRequestDTO dto){
        ProductoModel entity =  productoRepository.findById(id);
        if(entity == null){
            throw new NotFoundException("Producto no encontrado con id " + id );
        }

        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setCategoria(dto.categoria());
        entity.setPrecio(dto.precio());
        entity.setStock(dto.stock());

        // como es entidad manejada, se actualiza sola al commit
        return ProductResponseDTO.fromEntity(entity);
    }

    // ===== Eliminar =====
    public void eliminar(Long id){
        boolean deleted = productoRepository.deleteById(id);
        if (!deleted){
            throw new NotFoundException("Prodcuto no encontrado con id "+ id);
        }
    }

    // ===== Búsqueda con filtros + paginación =====
    public PageResponseDTO<ProductResponseDTO> buscar(
            String categoria,
            BigDecimal precioMin,
            BigDecimal precioMax,
            String texto,
            int page,
            int size,
            String sortField,
            String sortDir
    ) {
        PanacheQuery<ProductoModel> query = productoRepository.search(
                categoria,
                precioMin,
                precioMax,
                texto,
                page,
                size,
                sortField,
                sortDir
        );

        List<ProductResponseDTO> items = query.list().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();

        long total = query.count();

        return new PageResponseDTO<>(
                items,
                total,
                page,
                size
        );
    }

}

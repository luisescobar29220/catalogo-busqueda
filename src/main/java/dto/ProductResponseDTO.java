package dto;

import modelo.ProductoModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//DTO para devolver al cliente (response)
public record ProductResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        String categoria,
        BigDecimal precio,
        Integer stock,
        LocalDateTime createdAt
) {
    public static ProductResponseDTO fromEntity(ProductoModel p) {
        return new ProductResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getCategoria(),
                p.getPrecio(),
                p.getStock(),
                p.getCreatedAt()
        );
    }
}

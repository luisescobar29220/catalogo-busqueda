package dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
//DTO para crear / actualizar
public record ProductRequestDTO(
        @NotBlank
        String nombre,

        @Size(max = 100)
        String descripcion,

        @NotBlank
        String categoria,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal precio,

        @Min(0)
        Integer stock
) {
}

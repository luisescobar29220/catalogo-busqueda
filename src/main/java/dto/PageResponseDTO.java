package dto;

import java.util.List;

//DTO genérico para respuestas paginadas (para la búsqueda)
public record PageResponseDTO<T>(
        List<T> items,
        long total,
        int page,
        int size
) {
}

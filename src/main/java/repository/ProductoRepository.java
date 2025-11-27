package repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import modelo.ProductoModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ProductoRepository implements PanacheRepository<ProductoModel> {

    public PanacheQuery<ProductoModel> search(
            String categoria,
            BigDecimal precioMin,
            BigDecimal precioMax,
            String texto,
            int page,
            int size,
            String sortField,
            String sortDir
    ) {
        StringBuilder where = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (categoria != null && !categoria.isBlank()) {
            where.append(" and categoria = :categoria");
            params.put("categoria", categoria);
        }

        if (precioMin != null) {
            where.append(" and precio >= :precioMin");
            params.put("precioMin", precioMin);
        }

        if (precioMax != null) {
            where.append(" and precio <= :precioMax");
            params.put("precioMax", precioMax);
        }

        if (texto != null && !texto.isBlank()) {
            where.append(" and (lower(nombre) like :texto or lower(descripcion) like :texto)");
            params.put("texto", "%" + texto.toLowerCase() + "%");
        }
        // Campo de ordenamiento seguro (usa nombres de atributos de ProductoModel)
        String safeSortField = (sortField == null || sortField.isBlank())
                ? "id"
                : sortField;

        Sort sort = Sort.by(safeSortField);
        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = sort.descending();
        }

        PanacheQuery<ProductoModel> query = find(where.toString(), sort, params);
        return query.page(Page.of(page, size));
    }


}


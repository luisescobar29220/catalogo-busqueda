package resource;

import dto.PageResponseDTO;
import dto.ProductRequestDTO;
import dto.ProductResponseDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import service.ProductoService;

import java.math.BigDecimal;

@Path("/productos")
public class ProductoResource {

    @Inject
    ProductoService productoService;

    // ===== Crear producto =====
    @POST
    public Response crear(@Valid ProductRequestDTO dto){
        ProductResponseDTO creado = productoService.crear(dto);
        return Response.status(Response.Status.CREATED).entity(creado).build();
    }

    // ===== Obtener producto por ID =====
    @GET
    @Path("/{id}")
    public ProductResponseDTO objtenerPorId(@PathParam("id") Long id){
        return productoService.obtenerId(id);
    }

    // ===== Actualizar producto =====
    @PUT
    @Path("/{id}")
    public ProductResponseDTO actualizar(@PathParam("id") Long id, @Valid ProductRequestDTO dto){
        return productoService.actualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        productoService.eliminar(id);
        return Response.ok("Eliminado correctamente").build();
    }

    // ===== Búsqueda con filtros + paginación =====
    @GET
    public PageResponseDTO<ProductResponseDTO> buscar(
            @QueryParam("categoria") String categoria,
            @QueryParam("precioMin") BigDecimal precioMin,
            @QueryParam("precioMax") BigDecimal precioMax,
            @QueryParam("texto") String texto,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sort") @DefaultValue("id") String sortField,
            @QueryParam("dir") @DefaultValue("asc") String sortDir
    ) {
        return productoService.buscar(
                categoria,
                precioMin,
                precioMax,
                texto,
                page,
                size,
                sortField,
                sortDir
        );
    }
}

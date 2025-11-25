package modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto",
indexes = {
        @Index(name = "idx_prod_categoria", columnList = "categoria"),
        @Index(name = "idx_prod_precio", columnList = "precio"),
        @Index(name = "idx_prod_nombre", columnList = "nombre")
})
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 100)
    private String descripcion;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal precio;

    private Integer Stock;

    private LocalDateTime createdAt;

    //“Si el producto aún no tiene fecha de creación, ponle la fecha de creación ahora mismo antes de guardarlo en la BD.”
    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // ===== Constructores =====


    public ProductoModel() {
    }

    public ProductoModel(String nombre, String descripcion, String categoria, BigDecimal precio, Integer stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        Stock = stock;
    }

    // ===== Getters y Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer stock) {
        Stock = stock;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

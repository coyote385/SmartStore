package com.smartstore.model;

import java.util.Objects;

/**
 * Representa una categoría de productos dentro del sistema SmartStore.
 *
 * Cada producto pertenece a una única categoría, lo que facilita
 * su organización, búsqueda y clasificación.
 *
 * Ejemplos:
 * - Tecnología
 * - Hogar
 * - Papelería
 * - Deportes
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Categoria {

    /**
     * Identificador único de la categoría.
     */
    private int id;

    /**
     * Nombre de la categoría.
     */
    private String nombre;

    /**
     * Descripción de la categoría.
     */
    private String descripcion;

    /**
     * Indica si la categoría está activa.
     */
    private boolean activa;

    /**
     * Constructor completo.
     *
     * @param id identificador
     * @param nombre nombre de la categoría
     * @param descripcion descripción
     * @param activa estado
     */
    public Categoria(int id, String nombre, String descripcion, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activa = activa;
    }

    // ==========================
    // Getters y Setters
    // ==========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor que cero.");
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

        this.descripcion = descripcion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * Dos categorías son iguales si tienen el mismo ID.
     */
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Categoria))
            return false;

        Categoria that = (Categoria) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representación en texto.
     */
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", activa=" + activa +
                '}';
    }

}
package com.smartstore.model;

import java.util.Objects;

/**
 * Representa un proveedor de productos dentro del sistema SmartStore.
 *
 * Un proveedor puede suministrar uno o varios productos al inventario.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Proveedor {

    private int id;
    private String nombre;
    private String nit;
    private String telefono;
    private String correo;
    private String direccion;
    private boolean activo;

    /**
     * Constructor completo.
     */
    public Proveedor(int id, String nombre, String nit,
                     String telefono, String correo,
                     String direccion, boolean activo) {

        setId(id);
        setNombre(nombre);
        setNit(nit);
        setTelefono(telefono);
        setCorreo(correo);
        setDireccion(direccion);

        this.activo = activo;
    }

    //=========================
    // Getters y Setters
    //=========================

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
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {

        if (nit == null || nit.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIT es obligatorio.");
        }

        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {

        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }

        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {

        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Correo electrónico inválido.");
        }

        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {

        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }

        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Proveedor))
            return false;

        Proveedor other = (Proveedor) obj;

        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {

        return "Proveedor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nit='" + nit + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", activo=" + activo +
                '}';
    }

}
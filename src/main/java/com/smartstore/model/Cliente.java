package com.smartstore.model;

import java.io.Serializable;

/**
 * Representa un cliente registrado en el sistema.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class Cliente implements Serializable {

    private int id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private String correo;
    private String direccion;
    private boolean activo;

    /**
     * Constructor de Cliente.
     *
     * @param id Identificador único.
     * @param nombre Nombre del cliente.
     * @param apellido Apellido del cliente.
     * @param cedula Documento de identidad.
     * @param telefono Número telefónico.
     * @param correo Correo electrónico.
     * @param direccion Dirección de residencia.
     * @param activo Estado del cliente.
     */
    public Cliente(
            int id,
            String nombre,
            String apellido,
            String cedula,
            String telefono,
            String correo,
            String direccion,
            boolean activo) {

        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setCedula(cedula);
        setTelefono(telefono);
        setCorreo(correo);
        setDireccion(direccion);

        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }

        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio."
            );
        }

        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {

        if (apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException(
                    "El apellido es obligatorio."
            );
        }

        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {

        if (cedula == null || cedula.isBlank()) {
            throw new IllegalArgumentException(
                    "La cédula es obligatoria."
            );
        }

        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {

        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException(
                    "El teléfono es obligatorio."
            );
        }

        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {

        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException(
                    "El correo es obligatorio."
            );
        }

        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {

        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException(
                    "La dirección es obligatoria."
            );
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
    public String toString() {

        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", activo=" + activo +
                '}';
    }
}
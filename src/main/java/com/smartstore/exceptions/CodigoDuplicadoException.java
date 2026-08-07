package com.smartstore.exceptions;

/**
 * Excepción lanzada cuando se intenta registrar
 * un código que ya existe en el sistema.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class CodigoDuplicadoException extends Exception {

    public CodigoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
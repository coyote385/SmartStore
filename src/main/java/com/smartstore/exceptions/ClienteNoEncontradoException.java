package com.smartstore.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un cliente.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class ClienteNoEncontradoException extends Exception {

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
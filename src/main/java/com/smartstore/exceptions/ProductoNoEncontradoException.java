package com.smartstore.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un producto.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class ProductoNoEncontradoException extends Exception {

    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
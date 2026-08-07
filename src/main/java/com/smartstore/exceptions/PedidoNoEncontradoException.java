package com.smartstore.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un pedido.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class PedidoNoEncontradoException extends Exception {

    public PedidoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
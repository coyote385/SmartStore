package com.smartstore.exceptions;

/**
 * Excepción lanzada cuando no existe suficiente stock
 * para realizar una operación.
 *
 * @author Jonathan Mendez
 * @version 1.0
 */
public class StockInsuficienteException extends Exception {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
package com.yme.movementsservice.constant;

/**
 * App error messages.
 */
public class ErrorMessages {
    //NOT FOUND
    public static final String ERROR_ACCOUNT_NOT_FOUND = "La cuenta no existe";
    public static final String ERROR_CLIENT_NOT_FOUND = "El cliente no existe";

    //ALREADY EXISTS
    public static final String ERROR_ACCOUNT_ALREADY_EXISTS = "La cuenta ya existe";

    //BUSINESS
    public static final String ERROR_MOVEMENT_NOT_ALLOWED = "No se puede realizar el movimiento. Valide la data de entrada.";
    public static final String ERROR_INSUFFICIENT_FUNDS = "Fondos insuficientes.";
    public static final String ERROR_DATE_FORMAT = "Error en el formato de la fecha. Debe ser: ";
    public static final String ERROR_DATE_RANGE_FORMAT = "Error en el formato del rango de fechas. Debe ser: ";

    //DATA INTEGRITY
    public static final String ERROR_DATA_INTEGRITY_VIOLATION = "No se pudo realizar la operación. Existen violaciones a la integridad de la data.";
}

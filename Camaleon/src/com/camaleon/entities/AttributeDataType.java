/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

/**
 * {@link Enum} que representa el tipo de dato del atributo de la relación
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public enum AttributeDataType {
    VARCHAR(true), NUMBER(false);

    private final boolean requiresLength;

    AttributeDataType(boolean requiresLength) {
        this.requiresLength = requiresLength;
    }

    /**
     * Obtiene un valor booleano que indica si requiere especificarse
     * longitud para este tipo de dato
     * @return boolean valor que indica si se debe especificar longitud para este tipo de dato
     */
    public boolean getValue() {
        return requiresLength;
    }

    @Override
    public String toString() {
        return "AttributeDataType{" + this.name() + '}';
    }
    
    
}

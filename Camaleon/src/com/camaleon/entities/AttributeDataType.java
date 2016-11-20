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
    SMALLINT("SMALLINT", false, false), 
    INTEGER("INTEGER", false, false),
    DECIMAL("DECIMAL", true, true),
    NUMERIC("NUMERIC", true, true),
    FLOAT("FLOAT", true, false),
    REAL("REAL", false, false),
    DOUBLE_PRECISION("DOUBLE PRECISION", false, false),
    CHARACTER("CHARACTER", true, false),
    NATIONAL_CHARACTER("NATIONAL CHARACTER", true, false),
    CHARACTER_VARYING("CHARACTER VARYING", true, false),
    NATIONAL_CHARACTER_VARYING("NATIONAL CHARACTER VARYING", true, false),
    BIT("BIT", true, false),
    BIT_VARYING("BIT VARYING", true, false),
    DATE("DATE", false, false),
    TIME("TIME", false, false),
    TIMESTAMP("TIMESTAMP", false, false);

    private final boolean requiresLength;
    private final boolean requiresScale;
    private final String name;

    AttributeDataType(String name, boolean requiresLength, boolean requiresScale) {
        this.name = name;
        this.requiresLength = requiresLength;
        this.requiresScale = requiresScale;
    }

    /**
     * Obtiene un valor booleano que indica si requiere especificarse
     * longitud para este tipo de dato
     * @return boolean valor que indica si se debe especificar longitud para este tipo de dato
     */
    public boolean requiresLength() {
        return requiresLength;
    }

    /**
     * Obtiene un valor booleano que indica si requiere especificarse
     * scala para este tipo de dato
     * @return boolean valor que indica si se debe especificar escala para este tipo de dato
     */
    public boolean requiresScale() {
        return requiresScale;
    }

    /**
     * Obtiene un String con el nombre del tipo de dato
     * @return String el nombre del tipo de dato
     */
    public String getName() {
        return name;
    }
    
    
    
    @Override
    public String toString() {
        return this.name();
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

public enum AttributeDataType {
    VARCHAR(true), NUMBER(false);

    private final boolean requiresLength;

    AttributeDataType(boolean requiresLength) {
        this.requiresLength = requiresLength;
    }

    public boolean getValue() {
        return requiresLength;
    }

    @Override
    public String toString() {
        return "AttributeDataType{" + this.name() + '}';
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Representa una tabla para la generación del script de base de datos
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Table {
    
    private String tableName;
    private Map<String, Attribute> attributes;

    public Table(String tableName) {
        this.tableName = tableName;
        this.attributes = new HashMap<>();
    }

    public Table(String tableName, Map<String, Attribute> attributes) {
        this.tableName = tableName;
        this.attributes = attributes;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Attribute> attributes) {
        this.attributes = attributes;
    }    
    
    /**
     * Obtiene las llaves de los atributos de la tabla
     *
     * @return {@link Set} con las llaves de los atributos de la tabla
     */
    public Set<String> getAttributeKeys(){
        return attributes.keySet();
    }

    @Override
    public String toString() {
        return "Table{" + "tableName=" + tableName + ", attributes=" + attributes + '}';
    }    
    
}

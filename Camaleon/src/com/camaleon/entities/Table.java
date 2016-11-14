/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa una tabla para la generación del script de base de datos
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Table {
    
    private String tableName;
    private List<Attribute> attributes;

    public Table(String tableName) {
        this.tableName = tableName;
        this.attributes = new LinkedList<>();
    }

    public Table(String tableName, List<Attribute> attributes) {
        this.tableName = tableName;
        this.attributes = attributes;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
    
    public void addAttribute(Attribute attribute){
        this.attributes.add(attribute);
    }

    @Override
    public String toString() {
        return "Table{" + "tableName=" + tableName + ", attributes=" + attributes + '}';
    }    
    
}

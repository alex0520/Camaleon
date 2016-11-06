/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

import java.sql.JDBCType;

/**
 * 
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Attribute {
    
    private String name;
    private JDBCType type;
    
    public Attribute() {
    }
  
    public Attribute(String name) {
        this.name = name;
    }
    /**
     * 
     * @param name Attribute
     * @param type 
     */
    public Attribute(String name, JDBCType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JDBCType getType() {
        return type;
    }

    public void setType(JDBCType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Attribute{");
        sb.append(name);
        if(type!=null){
            sb.append(" ");
            sb.append("[");
            sb.append(type.toString());
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

import java.util.Objects;

/**
 * Clase que representa un atributo de una relación
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Attribute implements Comparable<Attribute>  {
    
    private String key;
    private String name;
    private AttributeDataType type;
    private Long length;
    private Long scale;
    
    public Attribute() {
    }
  
    public Attribute(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Attribute(String key, String name, AttributeDataType type) {
        this.key = key;
        this.name = name;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeDataType getType() {
        return type;
    }

    public void setType(AttributeDataType type) {
        this.type = type;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getScale() {
        return scale;
    }

    public void setScale(Long scale) {
        this.scale = scale;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attribute other = (Attribute) obj;
        return Objects.equals(this.key, other.key);
    }    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append(" : ");
        sb.append(name);
        return sb.toString();
    }

    @Override
    public int compareTo(Attribute o) {
        return this.getKey().compareTo(o.getKey());
    }
}

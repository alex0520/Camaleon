/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

import com.google.common.base.Converter;

/**
 * 
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class RelationTableConverter extends Converter<Relation, Table>{

    @Override
    protected Table doForward(Relation relation) {
        Table table = new Table(relation.getAttributes().toString());
        for(String attribute : relation.getAttributes()){
            Attribute attr = new Attribute(attribute);
            table.addAttribute(attr);
        }
        return table;
    }

    @Override
    protected Relation doBackward(Table b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

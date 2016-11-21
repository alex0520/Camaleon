/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.entities;

import com.google.common.base.Converter;
import java.util.Map;

/**
 * Convierte una {@link Relation} en una {@link Table}
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class RelationTableConverter extends Converter<Relation, Table>{

    /*
	 * (non-Javadoc)
	 * @see com.google.common.base.Converter#doForward
	 */
    @Override
    protected Table doForward(Relation relation) {
        Table table = new Table(relation.getAttributeKeys().toString());
        for(Map.Entry<String,Attribute> entry : relation.getAttributes().entrySet()){
            table.getAttributes().put(entry.getKey(),entry.getValue());
        }
        return table;
    }

    /*
	 * (non-Javadoc)
	 * @see com.google.common.base.Converter#doBackward
	 */
    @Override
    protected Relation doBackward(Table table) {
        Relation relation = new Relation();
        relation.getAttributes().putAll(table.getAttributes());
        return relation;
    }
    
}

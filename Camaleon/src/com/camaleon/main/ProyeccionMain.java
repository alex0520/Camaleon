/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.main;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.LoadFileResult;
import com.camaleon.entities.Relation;
import com.camaleon.logic.LoadFile;
import com.camaleon.logic.MinimalCover;
import com.camaleon.logic.Projection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ASUS
 */
public class ProyeccionMain {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        LoadFileResult loadFileResult = LoadFile.loadFile("C:/Users/ASUS/Downloads/proyeccion_attr.json");
        
        if (loadFileResult.getStatus().equals(LoadFileResult.Status.SUCCESS)) {
            Relation relacion = loadFileResult.getRelation();

            HashMap<Set<String>, Set<String>> closures = new HashMap<Set<String>, Set<String>>();
            
            relacion.setDependencies(MinimalCover.rightDecomposition(relacion
                    .getDependencies()));

            relacion.setDependencies(MinimalCover.removeStrangeElemLeft(
                    relacion, closures));

            relacion.setDependencies(MinimalCover
                    .removeRedundantDependencies(relacion.getDependencies()));


        Projection projection = new Projection(relacion.getDependencies(),relacion.getAttributeKeys());
        
        Set<String> test = new HashSet<>();
        test.add("C");
        test.add("E");
        test.add("I");
        test.add("L");
        
            Set<FuncDependency> proyeccionResult = projection.getProjection(test);
            
            System.out.println(proyeccionResult);
        
        }
        
    }
    
}

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
import com.camaleon.logic.proyeccion.Dependencia;
import com.camaleon.logic.proyeccion.Proyeccion;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author ASUS
 */
public class ProyeccionMain {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        LoadFileResult loadFileResult = LoadFile.loadFile("C:/Users/ASUS/Downloads/proyeccion.json");
        
        if (loadFileResult.getStatus().equals(LoadFileResult.Status.SUCCESS)) {
            Relation relacion = loadFileResult.getRelation();

            HashMap<Set<String>, Set<String>> closures = new HashMap<Set<String>, Set<String>>();
            
            relacion.setDependencies(MinimalCover.rightDecomposition(relacion
                    .getDependencies()));

            relacion.setDependencies(MinimalCover.removeStrangeElemLeft(
                    relacion, closures));

            relacion.setDependencies(MinimalCover
                    .removeRedundantDependencies(relacion.getDependencies()));
            
            Function<FuncDependency, Dependencia> convertFromFuncDepToDep = new Function<FuncDependency, Dependencia>() {
            public Dependencia apply(FuncDependency t) {
                Dependencia d = new Dependencia(t.getImplicantKeys(), t.getImpliedKeys());
                return d;
            }
        };

        Set<Dependencia> dependencias = relacion.getDependencies().stream().map(convertFromFuncDepToDep).collect(Collectors.<Dependencia>toSet());

        /*Function<Dependencia, FuncDependency> convertFromDepToFuncDep = new Function<Dependencia, FuncDependency>() {
            public FuncDependency apply(Dependencia t) {
                FuncDependency f = new FuncDependency(new HashSet<String>(t.getImplicantes()), new HashSet<String>(t.getImplicados()));
                return f;
            }
        };

        Proyeccion proyeccion = new Proyeccion(dependencias, relacion.getAttributes());
        
        Set<String> test = new HashSet<>();
        test.add("C");
        test.add("E");
        test.add("I");
        test.add("L");
        
            Set<Dependencia> proyeccionResult = proyeccion.obtenerProyeccion(test);
            
            System.out.println(proyeccionResult);*/
        
        }
        
    }
    
}

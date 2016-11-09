package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.camaleon.logic.proyeccion.Dependencia;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * Algoritmo de Síntesis de Bernstein
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Bernstein {
/**
 * Obtiene las particiones con base a un universo discurso compuesto de dependencias funcionales y atributos
 * @param attributes Lista de atributos
 * @param dependencies Lista de dependencias funcionales
 * @return 
 */
    public static TreeMap<String, HashSet<String>> getPartitions(Set<String> attributes, List<FuncDependency> dependencies) {
        TreeMap<String, HashSet<String>> partitions = new TreeMap<String, HashSet<String>>();
        for (int i = 0; i < dependencies.size(); i++) {
            String key = Joiner.on("").join(dependencies.get(i).getImplicantKeys());
            if (partitions.containsKey(key)) {
                partitions.get(key).addAll(dependencies.get(i).getImpliedKeys());
            } else {
                HashSet<String> values = new HashSet<>();
                values.addAll(dependencies.get(i).getImplicantKeys());
                values.addAll(dependencies.get(i).getImpliedKeys());
                partitions.put(key, values);
            }
        }

        com.google.common.base.Function<FuncDependency, Set<String>> impliedFunction = new com.google.common.base.Function<FuncDependency, Set<String>>() {
            @Override
            public Set<String> apply(FuncDependency input) {
                return input.getImpliedKeys();
            }
        };

        com.google.common.base.Function<FuncDependency, Set<String>> implicantFunction = new com.google.common.base.Function<FuncDependency, Set<String>>() {
            @Override
            public Set<String> apply(FuncDependency input) {
                return input.getImplicantKeys();
            }
        };

        Set<Set<String>> implicant = new HashSet<Set<String>>(
                Collections2.transform(dependencies,
                        implicantFunction));

        Set<Set<String>> implied = new HashSet<Set<String>>(
                Collections2.transform(dependencies,
                        impliedFunction));

        HashSet<String> implicantNew = new HashSet<String>();
        HashSet<String> impliedNew = new HashSet<String>();

        for (Set<String> hashSet : implied) {
            implicantNew.addAll(hashSet);
        }

        for (Set<String> hashSet : implicant) {
            impliedNew.addAll(hashSet);
        }

        Sets.SetView<String> keys = Sets.difference(Sets.union(implicantNew, impliedNew), attributes);
        if (keys.size() > 0) {

            String key = Joiner.on("").join(keys);
            HashSet<String> values = new HashSet<String>(keys);
            partitions.put(key, values);
        }

        return partitions;
    }

    public static List<HashSet<String>> remDupPartitions(TreeMap<String, HashSet<String>> partitions) {
        List<HashSet<String>> cleanPartitions = new ArrayList<HashSet<String>>();
        int i = 0;
        LinkedList<HashSet<String>> list = new LinkedList(partitions.values());
        
        Collections.sort(list, (HashSet<String> o1, HashSet<String> o2) -> o2.size() - o1.size());
        
        for(HashSet<String> partition : list ){
            if (i == 0) {
                cleanPartitions.add(partition);
            }
            boolean duplicate = false;
            for (int j = 0; j < cleanPartitions.size(); j++) {
                HashSet<String> hashSet = cleanPartitions.get(j);
                if (hashSet.containsAll(partition)) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                cleanPartitions.add(partition);
            }
            i++;
        }
        Collections.sort(cleanPartitions, Util.hashSetComparator);
        return cleanPartitions;
    }
/**
 * Automatización del procedimiento establecido del algoritmo de Síntesis de Bernstein
 * @param attributes Lista de atributos
 * @param dependencies Lista de dependencias funcionales
 * @return 
 */
    public static List<Relation> getBernstein(Set<String> attributes, List<FuncDependency> dependencies) {
        List<Relation> proyecciones = new ArrayList<Relation>();
        TreeMap<String, HashSet<String>> partitions = Bernstein.getPartitions(attributes, dependencies);
        List<HashSet<String>> cleanPartitions = Bernstein.remDupPartitions(partitions);
        Function<FuncDependency, Dependencia> convertFromFuncDepToDep = new Function<FuncDependency, Dependencia>() {
            public Dependencia apply(FuncDependency t) {
                Dependencia d = new Dependencia(t.getImplicantKeys(), t.getImpliedKeys());
                return d;
            }
        };

        Set<Dependencia> dependencias = dependencies.stream().map(convertFromFuncDepToDep).collect(Collectors.<Dependencia>toSet());

        /*
        Function<Dependencia, FuncDependency> convertFromDepToFuncDep = new Function<Dependencia, FuncDependency>() {
            public FuncDependency apply(Dependencia t) {
                FuncDependency f = new FuncDependency(new HashSet<String>(t.getImplicantes()), new HashSet<String>(t.getImplicados()));
                return f;
            }
        };

        Proyeccion proyeccion = new Proyeccion(dependencias, attributes);
        for (Iterator<HashSet<String>> iterator = cleanPartitions.iterator(); iterator.hasNext();) {
            HashSet<String> partition = iterator.next();
            Relation relation = new Relation();
            relation.setAttributes(partition);
            Set<Dependencia> proyDependencies = proyeccion.obtenerProyeccion(partition);
            List<FuncDependency> proyDepList = proyDependencies.stream().map(convertFromDepToFuncDep).collect(Collectors.<FuncDependency>toList());
            relation.setDependencies(proyDepList);
            proyecciones.add(relation);
        }*/
        return proyecciones;
    }
}

package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.camaleon.logic.proyeccion.Dependencia;
import com.camaleon.logic.proyeccion.Proyeccion;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Bernstein {

    public static TreeMap<String, HashSet<String>> getPartitions(HashSet<String> attributes, List<FuncDependency> dependencies) {
        TreeMap<String, HashSet<String>> partitions = new TreeMap<String, HashSet<String>>();
        for (int i = 0; i < dependencies.size(); i++) {
            String key = Joiner.on("").join(dependencies.get(i).getImplicant());
            if (partitions.containsKey(key)) {
                partitions.get(key).addAll(dependencies.get(i).getImplied());
            } else {
                HashSet<String> values = new HashSet<>();
                values.addAll(dependencies.get(i).getImplicant());
                values.addAll(dependencies.get(i).getImplied());
                partitions.put(key, values);
            }
        }

        com.google.common.base.Function<FuncDependency, HashSet<String>> impliedFunction = new com.google.common.base.Function<FuncDependency, HashSet<String>>() {
            @Override
            public HashSet<String> apply(FuncDependency input) {
                return input.getImplied();
            }
        };

        com.google.common.base.Function<FuncDependency, HashSet<String>> implicantFunction = new com.google.common.base.Function<FuncDependency, HashSet<String>>() {
            @Override
            public HashSet<String> apply(FuncDependency input) {
                return input.getImplicant();
            }
        };

        Set<HashSet<String>> implicant = new HashSet<HashSet<String>>(
                Collections2.transform(dependencies,
                        implicantFunction));

        Set<HashSet<String>> implied = new HashSet<HashSet<String>>(
                Collections2.transform(dependencies,
                        impliedFunction));

        HashSet<String> implicantNew = new HashSet<String>();
        HashSet<String> impliedNew = new HashSet<String>();

        for (HashSet<String> hashSet : implied) {
            implicantNew.addAll(hashSet);
        }

        for (HashSet<String> hashSet : implicant) {
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
        for (Map.Entry<String, HashSet<String>> partition : partitions.entrySet()) {
            if (i == 0) {
                cleanPartitions.add(partition.getValue());
            }
            boolean duplicate = false;
            for (int j = 0; j < cleanPartitions.size(); j++) {
                HashSet<String> hashSet = cleanPartitions.get(j);
                if (hashSet.containsAll(partition.getValue())) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                cleanPartitions.add(partition.getValue());
            }
            i++;
        }
        Collections.sort(cleanPartitions, Util.hashSetComparator);
        return cleanPartitions;
    }

    public static List<Relation> getBernstein(HashSet<String> attributes, List<FuncDependency> dependencies) {
        List<Relation> proyecciones = new ArrayList<Relation>();
        TreeMap<String, HashSet<String>> partitions = Bernstein.getPartitions(attributes, dependencies);
        List<HashSet<String>> cleanPartitions = Bernstein.remDupPartitions(partitions);
        Function<FuncDependency, Dependencia> convertFromFuncDepToDep = new Function<FuncDependency, Dependencia>() {
            public Dependencia apply(FuncDependency t) {
                Dependencia d = new Dependencia(t.getImplicant(), t.getImplied());
                return d;
            }
        };

        Set<Dependencia> dependencias = dependencies.stream().map(convertFromFuncDepToDep).collect(Collectors.<Dependencia>toSet());

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
        }
        return proyecciones;
    }
}

package com.camaleon.logic;

import com.camaleon.entities.Attribute;
import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import java.util.*;
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
        TreeMap<String, HashSet<String>> partitions = new TreeMap<>();
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
        List<HashSet<String>> cleanPartitions = new ArrayList<>();
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
 * @param relation la relación de la que desea calcular las particiones
 * @return 
 */
    public static List<Relation> getBernstein(Relation relation) {
        List<Relation> proyecciones = new ArrayList<>();
        TreeMap<String, HashSet<String>> partitions = Bernstein.getPartitions(relation.getAttributeKeys(), relation.getDependencies());
        List<HashSet<String>> cleanPartitions = Bernstein.remDupPartitions(partitions);

        Proyeccion proyeccion = new Proyeccion(relation.getDependencies(), relation.getAttributeKeys());
        for (Iterator<HashSet<String>> iterator = cleanPartitions.iterator(); iterator.hasNext();) {
            HashSet<String> partition = iterator.next();
            Relation relationIn = new Relation();
            Map<String, Attribute> attributeMap = new HashMap<>();
            partition.forEach(attribute -> attributeMap.put(attribute,relation.getAttributes().get(attribute)));
            relation.setAttributes(attributeMap);
            List<FuncDependency> proyDependencies = new LinkedList<>(proyeccion.obtenerProyeccion(partition));
            relation.setDependencies(proyDependencies);
            proyecciones.add(relation);
        }
        return proyecciones;
    }
}

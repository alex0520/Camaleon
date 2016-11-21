package com.camaleon.logic;

import com.camaleon.entities.Attribute;
import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.Map.Entry;

import java.util.stream.Collectors;

/**
 * Clase encargada de calcular las particiones de una relación
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Bernstein {
    /**
     * Obtiene las particiones con base a un universo discurso compuesto de dependencias funcionales y atributos
     *
     * @param attributes Lista de atributos
     * @param dependencies Lista de dependencias funcionales
     * @return {@link TreeMap} Con las particiones de la relación
     */
    public static TreeMap<String, Set<String>> getPartitions(Set<String> attributes, List<FuncDependency> dependencies) {
        TreeMap<String, Set<String>> partitions = new TreeMap<>();
        for (int i = 0; i < dependencies.size(); i++) {
            String key = Joiner.on(",").join(dependencies.get(i).getImplicantKeys());
            if (partitions.containsKey(key)) {
                partitions.get(key).addAll(dependencies.get(i).getImpliedKeys());
            } else {
                HashSet<String> values = new HashSet<>();
                values.addAll(dependencies.get(i).getImplicantKeys());
                values.addAll(dependencies.get(i).getImpliedKeys());
                partitions.put(key, values);
            }
        }

        HashSet<String> implicantNew = new HashSet<>();
        HashSet<String> impliedNew = new HashSet<>();

        dependencies.stream().map(dependency -> dependency.getImpliedKeys())
                .collect(Collectors.toSet())
                .forEach(hashSet -> impliedNew.addAll(hashSet));
        dependencies.stream().map(dependency -> dependency.getImplicantKeys())
                .collect(Collectors.toSet())
                .forEach(hashSet -> implicantNew.addAll(hashSet));

        Sets.SetView<String> keys = Sets.difference(Sets.union(implicantNew, impliedNew), attributes);
        if (keys.size() > 0) {

            String key = Joiner.on(",").join(keys);
            HashSet<String> values = new HashSet<>(keys);
            partitions.put(key, values);
        }

        return partitions;
    }

    /**
     * Elimina las particiones duplicadas, de un conjunto de particiones
     *
     * @param partitions Conjunto de particiones
     * @return {@link List} Lista de particiones sin dependencias duplicadas
     */
    public static TreeMap<String, Set<String>> remDupPartitions(TreeMap<String, Set<String>> partitions) {
        TreeMap<String, Set<String>> cleanPartitions = new TreeMap<>();
        
        LinkedHashMap<String, Set<String>> localPartitions = partitions.entrySet().stream().sorted(Entry.comparingByValue((Set o1, Set o2) -> o2.size()-o1.size())).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                
        int i = 0;
        for (Map.Entry<String,Set<String>> partitionEntry : localPartitions.entrySet()) {
            Set<String> partition = partitionEntry.getValue();
            String key = Joiner.on(",").join(partition);
            if (i == 0) {
                cleanPartitions.put(key, partition);
            }
            boolean duplicate = false;
            for(Map.Entry<String,Set<String>> entry : cleanPartitions.entrySet()) {
                Set<String> hashSet = entry.getValue();
                if (hashSet.containsAll(partition)) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                cleanPartitions.put(partitionEntry.getKey(), partition);
            }
            i++;
        }
        return cleanPartitions;
    }

    /**
     * Automatización del procedimiento establecido del algoritmo de Síntesis de Bernstein
     *
     * @param relation la relación de la que desea calcular las particiones
     * @return {@link List} Listado de particiones
     */
    public static Map<String,Relation> getBernstein(Relation relation) {
        Map<String, Relation> proyecciones = new HashMap<>();
        TreeMap<String, Set<String>> partitions = Bernstein.getPartitions(relation.getAttributeKeys(), relation.getDependencies());
        TreeMap<String, Set<String>> cleanPartitions = Bernstein.remDupPartitions(partitions);

        Projection projection = new Projection(relation.getDependencies(), relation.getAttributeKeys());
        for(Map.Entry<String,Set<String>> entry : cleanPartitions.entrySet()) {
            Set<String> partition = entry.getValue();
            Relation relationIn = new Relation();
            Map<String, Attribute> attributeMap = new HashMap<>();
            partition.forEach(attribute -> attributeMap.put(attribute, relation.getAttributes().get(attribute)));
            relationIn.setAttributes(attributeMap);
            List<FuncDependency> proyDependencies = new LinkedList<>(projection.getProjection(partition));
            relationIn.setDependencies(proyDependencies);
            proyecciones.put(entry.getKey(),relationIn);
        }
        Map<String, Relation> newProyecciones = new HashMap<>();
        for(Map.Entry<String,Relation> entryRelation: proyecciones.entrySet()){
            String actualKey = entryRelation.getKey();
            Relation localRelation = entryRelation.getValue();
            List<HashSet<String>> candidateKeys = CandidateKeys.candidateKeys(localRelation, null);
            String newKey = actualKey;
            for(HashSet<String> keySet : candidateKeys){
                Set<String> primarykeyItems = new HashSet<>(Arrays.asList(newKey.split(",")));
                if(keySet.size()<primarykeyItems.size()){
                    newKey = Joiner.on(",").join(keySet);
                }
            }
            newProyecciones.put(newKey, localRelation);
            
        }
        
        return newProyecciones;
    }
}

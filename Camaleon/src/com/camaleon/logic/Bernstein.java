package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.camaleon.entities.FuncDependency;
import com.google.common.base.Joiner;
import java.util.Collections;

public class Bernstein {

    public static TreeMap<String, HashSet<String>> getPartitions(List<FuncDependency> dependencies) {
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

    public static List<HashSet<String>> checkKeys(List<HashSet<String>> partitions, List<HashSet<String>> candidateKeys) {
        boolean containKey = false;
        for (int i = 0; i < partitions.size(); i++) {
            for (int j = 0; j < candidateKeys.size(); j++) {
                if (partitions.get(i).containsAll(candidateKeys.get(j))) {
                    containKey = true;
                    return partitions;
                }
            }
        }
        if (!containKey) {
            partitions.addAll(candidateKeys);
        }
        return partitions;
    }

}

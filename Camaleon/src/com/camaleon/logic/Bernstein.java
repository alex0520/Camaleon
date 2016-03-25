package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.camaleon.entities.FuncDependency;

public class Bernstein {
	public static TreeMap<String, HashSet<String>> getPartitions(List<FuncDependency> dependencies) {
		TreeMap<String, HashSet<String>> partitions = new TreeMap<String, HashSet<String>>();
		for (int i = 0; i < dependencies.size(); i++) {
			if (partitions.containsKey(dependencies.get(i).getImplicant().toString())) {
				partitions.get(dependencies.get(i).getImplicant().toString()).addAll(dependencies.get(i).getImplied());
			} else {
				partitions.put(dependencies.get(i).getImplicant().toString(), dependencies.get(i).getImplicant());
				partitions.get(dependencies.get(i).getImplicant().toString()).addAll(dependencies.get(i).getImplied());
			}
		}
		return partitions;
	}

	public static List<HashSet<String>> remDupPartitions(TreeMap<String, HashSet<String>> partitions) {
		List<HashSet<String>> cleanPartitions = new ArrayList<HashSet<String>>();
		for (Map.Entry<String, HashSet<String>> partition : partitions.entrySet()) {
			for (Iterator<HashSet<String>> iterator = cleanPartitions.iterator(); iterator.hasNext();) {
				HashSet<String> hashSet = (HashSet<String>) iterator.next();
				if (!hashSet.containsAll(partition.getValue())) {
					cleanPartitions.add(partition.getValue());
				}
			}
		}
		return cleanPartitions;
	}
	
	public static List<HashSet<String>> checkKeys(List<HashSet<String>> partitions, List<HashSet<String>> candidateKeys){
		boolean containKey=false;
		for (int i = 0; i < partitions.size(); i++) {
			for (int j = 0; j < candidateKeys.size(); j++) {
				if(partitions.get(i).containsAll(candidateKeys.get(j))){
					containKey = true;
					return partitions;
				}
			}
		}
		if(!containKey){
			partitions.addAll(candidateKeys);
		}
		return partitions;
	}

}

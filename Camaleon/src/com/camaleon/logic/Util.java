package com.camaleon.logic;

import com.camaleon.entities.FuncDependency;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Util {

    public static final Comparator<HashSet> hashSetComparator = (o1, o2) -> {
        String s1 = Joiner.on("").join(o1);
        String s2 = Joiner.on("").join(o2);
        return s1.compareTo(s2);
    };

    public static Set<String> setDifference(Set<String> atributosT, Set<String> atributosY) {
        return Sets.difference(atributosT, atributosY);
    }

    public static Set unirConjuntos(Set conjuntoA, Set conjuntoB) {
        return Sets.union(conjuntoA, conjuntoB);
    }

    public static Set<FuncDependency> funcDependencySetDifference(Set<FuncDependency> A, Set<FuncDependency> B) {
        return Sets.difference(Sets.union(A, B), Sets.intersection(A, B));
    }
}

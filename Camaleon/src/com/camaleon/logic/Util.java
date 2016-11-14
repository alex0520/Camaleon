package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.camaleon.entities.FuncDependency;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {

    public static Set<String> closure(Set<String> attributes, List<FuncDependency> dependencies,
                                      Map<Set<String>, Set<String>> closures) {

        if (closures == null) {
            closures = new HashMap<Set<String>, Set<String>>();
        }

        if (!closures.containsKey(attributes)) {
            HashSet<String> closure = new HashSet<>();
            HashSet<String> closureNew = new HashSet<>();
            closureNew.addAll(attributes);

            Set<Set<String>> implicants = dependencies.stream().map(dependency -> dependency.getImplicantKeys()).collect(Collectors.toSet());

            do {
                closure.addAll(closureNew);
                if (!closures.containsKey(closure)) {
                    Set<Set<String>> powerSet = Sets.powerSet(closure);

                    final SetView<Set<String>> intersection = Sets.intersection(implicants, powerSet);

                    List<FuncDependency> crossDep = dependencies.stream().filter(dependency -> {
                        Set<String> implicant = dependency.getImplicantKeys();
                        for (Set<String> hashSet : intersection) {
                            if (hashSet.containsAll(implicant)) {
                                return true;
                            }
                        }
                        return false;
                    }).collect(Collectors.toList());

                    for (FuncDependency funcDependency : crossDep) {
                        closureNew.addAll(funcDependency.getImpliedKeys());
                    }
                } else {
                    closureNew.addAll(closures.get(closure));
                }

            } while (closure.size() != closureNew.size());
            closures.put(attributes, closureNew);
        }
        return closures.get(attributes);
    }

    public static final Comparator<HashSet> hashSetComparator = new Comparator<HashSet>() {
        @Override
        public int compare(HashSet o1, HashSet o2) {
            String s1 = Joiner.on("").join(o1);
            String s2 = Joiner.on("").join(o2);
            return s1.compareTo(s2);
        }

    };

    public static Set<String> diferenciaConjuntos(Set<String> atributosT, Set<String> atributosY) {
        return Sets.difference(atributosT, atributosY);
    }

    public static Set unirConjuntos(Set conjuntoA, Set conjuntoB) {
        return Sets.union(conjuntoA, conjuntoB);
    }

    public static Set<FuncDependency> diferenciaConjuntoDep(Set<FuncDependency> A, Set<FuncDependency> B) {
        return Sets.difference(Sets.union(A, B), Sets.intersection(A, B));
    }
}

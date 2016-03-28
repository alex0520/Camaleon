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

public class Util {

    public static HashSet<String> closure(HashSet<String> attributes, List<FuncDependency> dependencies,
            HashMap<HashSet<String>, HashSet<String>> closures) {

        if (closures == null) {
            closures = new HashMap<HashSet<String>, HashSet<String>>();
        }

        if (!closures.containsKey(attributes)) {
            HashSet<String> closure = new HashSet<String>();
            HashSet<String> closureNew = new HashSet<String>();
            closureNew.addAll(attributes);

            Function<FuncDependency, HashSet<String>> function = new Function<FuncDependency, HashSet<String>>() {
                @Override
                public HashSet<String> apply(FuncDependency input) {
                    return input.getImplicant();
                }
            };

            Set<HashSet<String>> implicants = new HashSet<HashSet<String>>(
                    Collections2.transform(dependencies, function));

            do {
                closure.addAll(closureNew);
                if (!closures.containsKey(closure)) {
                    Set<Set<String>> powerSet = Sets.powerSet(closure);

                    final SetView<HashSet<String>> intersection = Sets.intersection(implicants, powerSet);

                    Predicate<FuncDependency> crossDependencies = new Predicate<FuncDependency>() {
                        @Override
                        public boolean apply(FuncDependency dependency) {
                            HashSet<String> implicant = dependency.getImplicant();
                            for (HashSet<String> hashSet : intersection) {
                                if (hashSet.containsAll(implicant)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    };

                    List<FuncDependency> crossDep = new ArrayList<FuncDependency>(
                            Collections2.filter(dependencies, crossDependencies));

                    for (FuncDependency funcDependency : crossDep) {
                        closureNew.addAll(funcDependency.getImplied());
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

}

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

public class Util {

    public static Set<String> closure(Set<String> attributes, List<FuncDependency> dependencies,
            Map<Set<String>, Set<String>> closures) {

        if (closures == null) {
            closures = new HashMap<Set<String>, Set<String>>();
        }

        if (!closures.containsKey(attributes)) {
            HashSet<String> closure = new HashSet<String>();
            HashSet<String> closureNew = new HashSet<String>();
            closureNew.addAll(attributes);

            Function<FuncDependency, Set<String>> function = new Function<FuncDependency, Set<String>>() {
                @Override
                public Set<String> apply(FuncDependency input) {
                    return input.getImplicantKeys();
                }
            };

            Set<Set<String>> implicants = new HashSet<Set<String>>(
                    Collections2.transform(dependencies, function));

            do {
                closure.addAll(closureNew);
                if (!closures.containsKey(closure)) {
                    Set<Set<String>> powerSet = Sets.powerSet(closure);

                    final SetView<Set<String>> intersection = Sets.intersection(implicants, powerSet);

                    Predicate<FuncDependency> crossDependencies = new Predicate<FuncDependency>() {
                        @Override
                        public boolean apply(FuncDependency dependency) {
                            Set<String> implicant = dependency.getImplicantKeys();
                            for (Set<String> hashSet : intersection) {
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

}

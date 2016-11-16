package com.camaleon.logic;

import com.camaleon.entities.FuncDependency;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Algoritmo de Clausura
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Closure {

    /**
     * Calcula el cierre de un conjunto de atributos, de acuerdo a un conjunto de dependencias funcionales
     *
     * @param attributes Atributos a los cuales se les quiere calcular el cierre
     * @param dependencies Cunjunto de dependencias funcionales
     * @param closures Cierres calculados previamente
     * @return {@link Set} Conjunto de atributos (cierre)
     */
    public static Set<String> closure(Set<String> attributes, List<FuncDependency> dependencies,
                                      Map<Set<String>, Set<String>> closures) {

        if (closures == null) {
            closures = new HashMap<>();
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

                    final Sets.SetView<Set<String>> intersection = Sets.intersection(implicants, powerSet);

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
}

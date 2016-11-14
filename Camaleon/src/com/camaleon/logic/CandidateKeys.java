package com.camaleon.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
/**
 * LLaves Candidatas
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class CandidateKeys {
    /**
     * 
     * @param relation Tipo de dato relación compuesto por una lista de atributos y dependencias funcionales correspondientes a un universo discurso
     * @param closures Listado de clausuras previamente calculadas para dicho universo discurso.
     * @return 
     */
    public static List<HashSet<String>> candidateKeys(Relation relation,
            HashMap<Set<String>, Set<String>> closures) {
        List<HashSet<String>> candidateKeys = new ArrayList<>();

        Set<Set<String>> implicant = relation.getDependencies().stream().
                map(funcDependency -> funcDependency.getImplicantKeys())
                .collect(Collectors.toSet());

        Set<Set<String>> implied = relation.getDependencies().stream().
                map(funcDependency -> funcDependency.getImpliedKeys())
                .collect(Collectors.toSet());

        int attrSize = relation.getAttributes().size();
        HashSet<String> yesHashSet = new HashSet<>();
        HashSet<String> noHashSet;
        HashSet<String> maybeHashSet;
        HashSet<String> impliedHashSet = new HashSet<>();
        HashSet<String> implicantHashSet = new HashSet<>();

        for (Set<String> hashSet : implied) {
            impliedHashSet.addAll(hashSet);
        }

        for (Set<String> hashSet : implicant) {
            implicantHashSet.addAll(hashSet);
        }

        SetView<String> yesSet = Sets.difference(relation.getAttributeKeys(),
                impliedHashSet);
        SetView<String> noSet = Sets.difference(relation.getAttributeKeys(),
                implicantHashSet);
        noHashSet = new HashSet<>(noSet);

        if (yesSet.size() > 0) {
            yesHashSet = new HashSet<>(yesSet);
            Set<String> closureYesSet = Util.closure(yesHashSet,
                    relation.getDependencies(), closures);
            if (closureYesSet.size() == relation.getAttributes().size()) {
                candidateKeys.add(yesHashSet);
                return candidateKeys;
            }
            SetView<String> maybeSet = Sets.difference(
                    relation.getAttributeKeys(), yesHashSet);
            maybeHashSet = new HashSet<>(maybeSet);
        } else {
            maybeHashSet = new HashSet<>(relation.getAttributeKeys());
        }

        if (noHashSet.size() > 0 && noHashSet.size() < attrSize) {
            SetView<String> maybeSet = Sets.difference(maybeHashSet, noHashSet);
            maybeHashSet = new HashSet<>(maybeSet);
        }

        Set<Set<String>> powerSet = Sets.powerSet(maybeHashSet);
        List<Set<String>> powerList = new ArrayList<>(powerSet);

        Collections.sort(powerList, (o1, o2) -> o1.hashCode() - o2.hashCode());

        for (int i = 1; i < powerList.size(); i++) {
            final HashSet<String> trySet = new HashSet<>(powerList.get(i));
            HashSet<String> tempTrySet = new HashSet<>(trySet);
            if (yesHashSet.size() > 0) {
                tempTrySet.addAll(yesHashSet);
            }
            Set<String> closure = Util.closure(tempTrySet,
                    relation.getDependencies(), closures);
            if (closure.size() == attrSize) {
                candidateKeys.add(tempTrySet);

                List<Set<String>> subCombinations = powerList.stream()
                        .filter(element -> element.containsAll(trySet))
                        .collect(Collectors.toList());
                powerList.removeAll(subCombinations);
                i--;
            }

        }

        Collections.sort(candidateKeys, Util.hashSetComparator);
        return candidateKeys;
    }
}

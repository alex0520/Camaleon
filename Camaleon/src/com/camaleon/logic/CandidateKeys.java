package com.camaleon.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
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
            HashMap<HashSet<String>, HashSet<String>> closures) {
        List<HashSet<String>> candidateKeys = new ArrayList<HashSet<String>>();
        Function<FuncDependency, HashSet<String>> impliedFunction = new Function<FuncDependency, HashSet<String>>() {
            @Override
            public HashSet<String> apply(FuncDependency input) {
                return input.getImplied();
            }
        };

        Function<FuncDependency, HashSet<String>> implicantFunction = new Function<FuncDependency, HashSet<String>>() {
            @Override
            public HashSet<String> apply(FuncDependency input) {
                return input.getImplicant();
            }
        };

        Set<HashSet<String>> implicant = new HashSet<HashSet<String>>(
                Collections2.transform(relation.getDependencies(),
                        implicantFunction));

        Set<HashSet<String>> implied = new HashSet<HashSet<String>>(
                Collections2.transform(relation.getDependencies(),
                        impliedFunction));

        int attrSize = relation.getAttributes().size();
        HashSet<String> yesHashSet = new HashSet<String>();
        HashSet<String> noHashSet = new HashSet<String>();
        HashSet<String> maybeHashSet = new HashSet<String>();
        HashSet<String> impliedHashSet = new HashSet<String>();
        HashSet<String> implicantHashSet = new HashSet<String>();

        for (HashSet<String> hashSet : implied) {
            impliedHashSet.addAll(hashSet);
        }

        for (HashSet<String> hashSet : implicant) {
            implicantHashSet.addAll(hashSet);
        }

        SetView<String> yesSet = Sets.difference(relation.getAttributes(),
                impliedHashSet);
        SetView<String> noSet = Sets.difference(relation.getAttributes(),
                implicantHashSet);
        noHashSet = new HashSet<String>(noSet);

        if (yesSet.size() > 0) {
            yesHashSet = new HashSet<String>(yesSet);
            HashSet<String> closureYesSet = Util.closure(yesHashSet,
                    relation.getDependencies(), closures);
            if (closureYesSet.size() == relation.getAttributes().size()) {
                candidateKeys.add(yesHashSet);
                return candidateKeys;
            }
            SetView<String> maybeSet = Sets.difference(
                    relation.getAttributes(), yesHashSet);
            maybeHashSet = new HashSet<String>(maybeSet);
        } else {
            maybeHashSet = new HashSet<String>(relation.getAttributes());
        }

        if (noHashSet.size() > 0 && noHashSet.size() < attrSize) {
            SetView<String> maybeSet = Sets.difference(maybeHashSet, noHashSet);
            maybeHashSet = new HashSet<String>(maybeSet);
        }

        Set<Set<String>> powerSet = Sets.powerSet(maybeHashSet);
        List<Set<String>> powerList = new ArrayList<Set<String>>(powerSet);

        Collections.sort(powerList, new Comparator<Set<String>>() {

            @Override
            public int compare(Set<String> o1, Set<String> o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });

        for (int i = 1; i < powerList.size(); i++) {
            final HashSet<String> trySet = new HashSet<String>(powerList.get(i));
            HashSet<String> tempTrySet = new HashSet<String>(trySet);
            if (yesHashSet.size() > 0) {
                tempTrySet.addAll(yesHashSet);
            }
            HashSet<String> closure = Util.closure(tempTrySet,
                    relation.getDependencies(), closures);
            if (closure.size() == attrSize) {
                candidateKeys.add(tempTrySet);
                Predicate<Set<String>> subCombinationsPredicate = new Predicate<Set<String>>() {
                    @Override
                    public boolean apply(Set<String> input) {
                        return (input.containsAll(trySet));
                    }
                };

                ArrayList<Set<String>> subCombinations = new ArrayList<Set<String>>(
                        Collections2
                        .filter(powerList, subCombinationsPredicate));
                powerList.removeAll(subCombinations);
                i--;
            }

        }

        Collections.sort(candidateKeys, Util.hashSetComparator);
        return candidateKeys;
    }
}

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
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class CandidateKeys {
	public static List<HashSet<String>> candidateKeys(Relation relation,
			HashMap<HashSet<String>, HashSet<String>> closures) {
		List<HashSet<String>> candidateKeys = new ArrayList<HashSet<String>>();
		Function<FuncDependency, HashSet<String>> function = new Function<FuncDependency, HashSet<String>>() {
			@Override
			public HashSet<String> apply(FuncDependency input) {
				return input.getImplied();
			}
		};

		Set<HashSet<String>> implied = new HashSet<HashSet<String>>(
				Collections2.transform(relation.getDependencies(), function));

		int attrSize = relation.getAttributes().size();
		HashSet<String> yesHashSet = new HashSet<String>();
		HashSet<String> noHashSet = new HashSet<String>();
		HashSet<String> maybeHashSet = new HashSet<String>();

		for (HashSet<String> hashSet : implied) {
			noHashSet.addAll(hashSet);
		}

		SetView<String> yesSet = Sets.difference(relation.getAttributes(), noHashSet);

		if (yesSet.size() > 0) {
			yesHashSet = new HashSet<String>(yesSet);
			HashSet<String> closureYesSet = Util.closure(yesHashSet, relation.getDependencies(), closures);
			if (closureYesSet.size() == relation.getAttributes().size()) {
				candidateKeys.add(yesHashSet);
				return candidateKeys;
			}
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
			HashSet<String> closure = Util.closure(trySet, relation.getDependencies(), closures);
			if (closure.size() == attrSize) {
				candidateKeys.add(trySet);
				Predicate<Set<String>> subCombinationsPredicate = new Predicate<Set<String>>() {
					@Override
					public boolean apply(Set<String> input) {
						return (input.containsAll(trySet));
					}
				};

				ArrayList<Set<String>> subCombinations = new ArrayList<Set<String>>(
						Collections2.filter(powerList, subCombinationsPredicate));
				powerList.removeAll(subCombinations);
				i--;
			}

		}
		return candidateKeys;
	}
}

package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.camaleon.entities.FuncDependency;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class Util {

	public static HashSet<String> closure(HashSet<String> attributes, List<FuncDependency> dependencies,
			HashMap<HashSet<String>, HashSet<String>> closures) {

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
							boolean contains = false;
							for (HashSet<String> hashSet : intersection) {
								contains = (contains || hashSet.containsAll(implicant));
								if (contains) {
									break;
								}
							}
							return contains;
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

}
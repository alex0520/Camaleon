package com.camaleon.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.camaleon.entities.FuncDependency;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class Util {

	public static HashSet<String> closure(HashSet<String> attributes,
			List<FuncDependency> dependencies) {
		Set<Set<String>> powerSet = Sets.powerSet(attributes);
		Function<FuncDependency, HashSet<String>> function = new Function<FuncDependency, HashSet<String>>() {
			@Override
			public HashSet<String> apply(FuncDependency input) {
				return input.getImplicant();
			}
		};

		Set<HashSet<String>> implicants = new HashSet<HashSet<String>>(
				Collections2.transform(dependencies, function));

		SetView<Set<String>> intersection = Sets.intersection(powerSet,
				implicants);

		for (Set<String> set : intersection) {
			System.out.println(set);
		}

		return attributes;
	}

}

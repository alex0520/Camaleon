package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.camaleon.entities.FuncDependency;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class MinimalCover {

	public static List<FuncDependency> rightDecomposition(
			List<FuncDependency> dependencies) {

		Predicate<FuncDependency> impliedComp = new Predicate<FuncDependency>() {
			@Override
			public boolean apply(FuncDependency dependency) {
				return dependency.getImplied().size() > 1;
			}
		};

		List<FuncDependency> compDependencies = new ArrayList<FuncDependency>(
				Collections2.filter(dependencies, impliedComp));

		for (FuncDependency dependency : compDependencies) {
			FuncDependency tempDependency;
			HashSet<String> tempHash;
			for (String impliedAttr : dependency.getImplied()) {
				tempDependency = new FuncDependency();
				tempHash = new HashSet<String>();
				tempDependency.setImplicant(dependency.getImplicant());
				tempHash.add(impliedAttr);
				tempDependency.setImplied(tempHash);
				dependencies.add(tempDependency);
			}
			dependencies.remove(dependency);
		}

		return dependencies;
	}
}

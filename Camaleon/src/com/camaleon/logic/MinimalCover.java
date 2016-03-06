package com.camaleon.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.camaleon.entities.FuncDependency;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class MinimalCover {

	public static List<FuncDependency> rightDecomposition(List<FuncDependency> dependencies) {

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

	public static List<FuncDependency> removeStrangeElemLeft(List<FuncDependency> dependencies,
			HashMap<HashSet<String>, HashSet<String>> closures) {
		int i = 0;
		int j = 0;
		do {
			FuncDependency funcDependency = dependencies.get(i);
			boolean strange = false;
			if (funcDependency.getImplicant().size() > 1) {
				HashSet<String> implicant = funcDependency.getImplicant();
				HashSet<String> implied = funcDependency.getImplied();
				List<String> tempImplicant = new ArrayList<String>(implicant);

				do {
					tempImplicant = new ArrayList<String>(implicant);
					if (j < tempImplicant.size()) {
						tempImplicant.remove(j);
						HashSet<String> closure = Util.closure(new HashSet<String>(tempImplicant), dependencies,
								closures);
						if (closure.containsAll(implied)) {
							strange = true;
							FuncDependency tempFuncDep = new FuncDependency();
							tempFuncDep.setImplicant(new HashSet<String>(tempImplicant));
							tempFuncDep.setImplied(implied);
							dependencies.remove(i);
							dependencies.add(i, tempFuncDep);
							break;
						}
						j++;
					}
				} while (j < implicant.size());

			}
			if (!strange) {
				i++;
				j = 0;
			}
		} while (i < dependencies.size());

		return dependencies;
	}

	public static List<FuncDependency> removeRedundantDependencies(List<FuncDependency> dependencies) {
		List<FuncDependency> tempDependencies;
		int i = 0;
		do {
			tempDependencies = new ArrayList<FuncDependency>(dependencies);
			FuncDependency funcDependency = tempDependencies.get(i);
			HashSet<String> implicant = funcDependency.getImplicant();
			HashSet<String> implied = funcDependency.getImplied();
			tempDependencies.remove(i);
			HashSet<String> closure = Util.closure(implicant, tempDependencies, null);
			if (closure.containsAll(implied)) {
				dependencies.remove(i);
			} else {
				i++;
			}
		} while (i < dependencies.size());
		return dependencies;
	}
}

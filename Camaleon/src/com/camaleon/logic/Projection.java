/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.logic;

import com.camaleon.entities.Attribute;
import com.camaleon.entities.FuncDependency;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author senneko
 */
public class Projection {

    private final Set<FuncDependency> dependencies;
    private final Set<String> attributes;

    public Projection(List<FuncDependency> dependencies, Set<String> attributes) {
        this.dependencies = new HashSet<>(dependencies);
        this.attributes = attributes;
    }

    /**
     * retira las dependencies triviales del conjunto de dependencies dado.
     *
     * @param dependencySet
     * @return
     */
    public Set<FuncDependency> removeTrivialDependencies(Set<FuncDependency> dependencySet) {
        return dependencySet.stream()
                .filter(dep -> !dep.isTrivialDependency())
                .collect(Collectors.toSet());
    }

    public Set<FuncDependency> transitivityAxiom(Set<FuncDependency> dependencySet, String attribute) {
        Set<FuncDependency> result;
        if (dependencySet.size() > 1) {
            result = new HashSet<>();
            Set<FuncDependency> newDependency = new HashSet<>();
            // proyecta dependencies
            for (FuncDependency dependency : dependencySet) {
                newDependency.addAll(dependency.project());
            }
            // quitar triviales
            newDependency = removeTrivialDependencies(newDependency);
            Set<FuncDependency> impliedFuncDependencies;
            Set<FuncDependency> implicantFuncDependencies;

            implicantFuncDependencies = newDependency.stream()
                    .filter(dep -> dep.getImplicantKeys().contains(attribute))
                    .collect(Collectors.toSet());
            impliedFuncDependencies = newDependency.stream()
                    .filter(dep -> dep.getImpliedKeys().contains(attribute))
                    .collect(Collectors.toSet());

            if (!impliedFuncDependencies.isEmpty() && !implicantFuncDependencies.isEmpty()) {
                Map<String, Attribute> nImplied;
                for (FuncDependency impliedFuncDependency : impliedFuncDependencies) {
                    for (FuncDependency implicantFuncDependency : implicantFuncDependencies) {

                        nImplied = new HashMap<>();
                        nImplied.putAll(implicantFuncDependency.getImplicant());
                        nImplied.remove(attribute);
                        nImplied.putAll(impliedFuncDependency.getImplicant());
                        result.add(new FuncDependency(nImplied,
                                implicantFuncDependency.getImplied()));
                    }
                }
            }
        } else {
            result = new HashSet<>(dependencySet);
        }
        return result;
    }


    public Set<FuncDependency> getProjection(Set<String> attributeSet) {
        Set<FuncDependency> dependencySetG = new HashSet<>(dependencies);
        Set<String> attributeSetW = Util.setDifference(attributes, attributeSet);
        Set<String> attributeA = new HashSet<>();
        Set<FuncDependency> dependencySetH;
        Set<FuncDependency> union;

        for (String attribute : attributeSetW) {
                attributeA.clear();
                attributeA.add(attribute);
                dependencySetH = getFuncDependencyContainsAttribute(attributeA, dependencySetG);
                union = new HashSet<>(dependencySetH);
                dependencySetH = transitivityAxiom(dependencySetH,
                        attributeA.toArray(new String[attributeA.size()])[0]);
                dependencySetH = removeTrivialDependencies(dependencySetH);
                union.addAll(dependencySetH);
                dependencySetG = Util.funcDependencySetDifference(dependencySetG, union);
        }
        return dependencySetG;
    }

    private Set<FuncDependency> getFuncDependencyContainsAttribute(Set<String> A, Set<FuncDependency> G) {
        return G.stream().filter(dep -> dep.getImpliedKeys().containsAll(A)|| dep.getImplicantKeys().containsAll(A)).collect(Collectors.toSet());
    }

}

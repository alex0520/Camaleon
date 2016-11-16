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
 * Clase encargada de calcular las proyecciones de una {@link FuncDependency}
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Projection {

    private final Set<FuncDependency> dependencies;
    private final Set<String> attributes;

    public Projection(List<FuncDependency> dependencies, Set<String> attributes) {
        this.dependencies = new HashSet<>(dependencies);
        this.attributes = attributes;
    }

    /**
     * Retira las dependencies triviales del conjunto de dependencies dado.
     *
     * @param dependencySet
     * @return {@link Set} Conjunto de dependencias funcionales sin dependencias triviales
     */
    public Set<FuncDependency> removeTrivialDependencies(Set<FuncDependency> dependencySet) {
        return dependencySet.stream()
                .filter(dep -> !dep.isTrivialDependency())
                .collect(Collectors.toSet());
    }

    /**
     * Ejecuta el axioma de Armstron de transitividad, sobre un conjunto de {@link FuncDependency}
     *
     * @param dependencySet Conjunto de dependencias funcionales
     * @param attribute Atributo del que se quiere quitar de la proyección
     * @return {@link Set} Conjunto de dependencias funcionales sin el atributo enviado
     */
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

    /**
     * Obtiene la proyección de una {@link FuncDependency}
     *
     * @param attributeSet
     * @return {@link Set} Conjunto de dependencias funcionales proyectadas
     */
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

    /**
     * Obtiene las {@link FuncDependency} que contienen un atributo
     *
     * @param A El atributo a buscar
     * @param G El conjunto de dependencias funcionales
     * @return {@link Set} Conjunto de dependencias funcionales que contienen el atributo enviado
     */
    private Set<FuncDependency> getFuncDependencyContainsAttribute(Set<String> A, Set<FuncDependency> G) {
        return G.stream().filter(dep -> dep.getImpliedKeys().containsAll(A)|| dep.getImplicantKeys().containsAll(A)).collect(Collectors.toSet());
    }

}

package com.camaleon.logic;

import com.camaleon.entities.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clase encargada de calcular el recubrimiento minimo de una relación
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class MinimalCover {

    /**
     * Divide las dependencias funcionales con implicante compuesto, para obtener dependencias funcionales con implicado simple
     *
     * @param dependencies Conjunto de dependencias funcionales
     * @return {@link List} Lista de dependencias funcionales con implicantes individuales
     */
    public static List<FuncDependency> rightDecomposition(List<FuncDependency> dependencies) {

        List<FuncDependency> compDependencies = dependencies.stream().filter(dependency -> dependency.getImpliedKeys().size()>1).collect(Collectors.toList());

        for (FuncDependency dependency : compDependencies) {
            FuncDependency tempDependency;
            Map<String, Attribute> tempHash;
            for (String impliedAttr : dependency.getImpliedKeys()) {
                tempDependency = new FuncDependency();
                tempHash = new HashMap<>();
                tempDependency.setImplicant(dependency.getImplicant());
                tempHash.put(impliedAttr, dependency.getImplied().get(impliedAttr));
                tempDependency.setImplied(tempHash);
                dependencies.add(tempDependency);
            }
            dependencies.remove(dependency);
        }

        return dependencies;
    }

    /**
     * Elimina elementos extraños en el implicante de una {@link FuncDependency}
     *
     * @param relation La relación que contiene las dependencias funcionales
     * @param closures Clausuras previamente calculadas
     * @return {@link List} Lista de dependencias funcionales, sin elementos extraños en el implicante
     */
    public static List<FuncDependency> removeStrangeElemLeft(Relation relation,
                                                             Map<Set<String>, Set<String>> closures) {
        int i = 0;
        int j = 0;
        List<FuncDependency> dependencies = relation.getDependencies();
        do {
            FuncDependency funcDependency = dependencies.get(i);
            boolean strange = false;
            if (funcDependency.getImplicant().size() > 1) {
                Set<String> implicant = funcDependency.getImplicantKeys();
                Set<String> implied = funcDependency.getImpliedKeys();
                List<String> tempImplicant;
                tempImplicant = new ArrayList<>(implicant);

                do {
                    tempImplicant = new ArrayList<>(implicant);
                    if (j < tempImplicant.size()) {
                        tempImplicant.remove(j);
                        Set<String> closure = Closure.closure(new HashSet<String>(tempImplicant), dependencies, closures);
                        if (closure.containsAll(implied)) {
                            strange = true;
                            FuncDependency tempFuncDep = new FuncDependency();
                            Map<String, Attribute> tempImplicantMap = new HashMap<>();
                            tempImplicant.stream().forEach(implicantKey-> tempImplicantMap.put(implicantKey, relation.getAttributes().get(implicantKey)));
                            tempFuncDep.setImplicant(tempImplicantMap);
                            Map<String, Attribute> tempImpliedMap = new HashMap<>();
                            implied.stream().forEach(impliedKey-> tempImpliedMap.put(impliedKey, relation.getAttributes().get(impliedKey)));
                            tempFuncDep.setImplied(tempImpliedMap);
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

    /**
     * Elimina {@link FuncDependency} redundantes
     *
     * @param dependencies Lista de dependencias funcionales
     * @return {@link List} Lista de dependencias funcionales, sin dependencias funcionales redundantes
     */
    public static List<FuncDependency> removeRedundantDependencies(List<FuncDependency> dependencies) {
        dependencies.sort((f1,f2) -> f2.getImplicantKeys().size()-f1.getImplicantKeys().size());
        List<FuncDependency> localDependencies = new ArrayList<>(dependencies);
        List<FuncDependency> tempDependencies;        
        for(FuncDependency funcDependency : dependencies){
            Set<String> implicant = funcDependency.getImplicantKeys();
            Set<String> implied = funcDependency.getImpliedKeys();
            tempDependencies = new ArrayList<>(localDependencies);
            tempDependencies.remove(funcDependency);
            Set<String> closure = Closure.closure(implicant, tempDependencies, null);
            if (closure.containsAll(implied)) {
                localDependencies.remove(funcDependency);
            }
        }
        return localDependencies;
    }
}

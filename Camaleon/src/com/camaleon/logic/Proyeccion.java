/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.logic;

import com.camaleon.entities.Attribute;
import com.camaleon.entities.FuncDependency;
import com.camaleon.logic.Util;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author senneko
 */
public class Proyeccion {

    private final Set<FuncDependency> dependencias;
    private final Set<String> atributos;

    public Proyeccion(List<FuncDependency> dependencias, Set<String> atributos) {
        this.dependencias = new HashSet<>(dependencias);
        this.atributos = atributos;
    }

    /**
     * retira las dependencias triviales del conjunto de dependencias dado.
     *
     * @param conjuntoDependencias
     * @return
     */
    public Set<FuncDependency> retirarDependeciasTriviales(Set<FuncDependency> conjuntoDependencias) {
        return conjuntoDependencias.stream()
                .filter(dep -> !dep.isTrivialDependency())
                .collect(Collectors.toSet());
    }

    public Set<FuncDependency> quitarAttrDependencias(Set<FuncDependency> dependencias, String atributo) {
        Set<FuncDependency> resultado;
        if (dependencias.size() > 1) {
            resultado = new HashSet<>();
            Set<FuncDependency> nDependencia = new HashSet<>();
            // proyecta dependencias
            for (FuncDependency dependencia : dependencias) {
                nDependencia.addAll(dependencia.project());
            }
            // quitar triviales
            nDependencia = retirarDependeciasTriviales(nDependencia);
            Set<FuncDependency> impliedFuncDependencies;
            Set<FuncDependency> implicantFuncDependencies;

            implicantFuncDependencies = nDependencia.stream().filter(dep -> dep.getImplicantKeys().contains(atributo)).collect(Collectors.toSet());
            impliedFuncDependencies = nDependencia.stream().filter(dep -> dep.getImpliedKeys().contains(atributo)).collect(Collectors.toSet());

            if (!impliedFuncDependencies.isEmpty() && !implicantFuncDependencies.isEmpty()) {
                Map<String, Attribute> nImplied;
                for (FuncDependency impliedFuncDependency : impliedFuncDependencies) {
                    for (FuncDependency implicantFuncDependency : implicantFuncDependencies) {

                        nImplied = new HashMap<>();
                        nImplied.putAll(implicantFuncDependency.getImplicant());
                        nImplied.remove(atributo);
                        nImplied.putAll(impliedFuncDependency.getImplicant());
                        resultado.add(new FuncDependency(nImplied,
                                implicantFuncDependency.getImplied()));
                    }
                }
            }
        } else {
            resultado = new HashSet<>(dependencias);
        }
        return resultado;
    }


    public Set<FuncDependency> obtenerProyeccion(Set<String> subconjunto) {
        Set<FuncDependency> conjuntoDependenciasG = new HashSet<>(dependencias);
        Set<String> conjuntoAttrW = Util.diferenciaConjuntos(atributos, subconjunto);
        Set<String> atributoA = new HashSet<>();
        Set<FuncDependency> conjuntoDepH;
        Set<FuncDependency> union;

        Iterator<String> iteradorW = conjuntoAttrW.iterator();

        while (iteradorW.hasNext()) {
            atributoA.clear();
            atributoA.add(iteradorW.next());
            conjuntoDepH = obtenerDepSegunAttr(atributoA, conjuntoDependenciasG);
            union = new HashSet<>(conjuntoDepH);
            conjuntoDepH = quitarAttrDependencias(conjuntoDepH,
                    atributoA.toArray(new String[atributoA.size()])[0]);
            conjuntoDepH = retirarDependeciasTriviales(conjuntoDepH);
            union.addAll(conjuntoDepH);
            conjuntoDependenciasG = Util.diferenciaConjuntoDep(conjuntoDependenciasG, union);
        }
        return conjuntoDependenciasG;
    }

    private Set<FuncDependency> obtenerDepSegunAttr(Set<String> A, Set<FuncDependency> G) {
        return G.stream().filter(dep -> dep.getImpliedKeys().containsAll(A)|| dep.getImplicantKeys().containsAll(A)).collect(Collectors.toSet());
    }

}

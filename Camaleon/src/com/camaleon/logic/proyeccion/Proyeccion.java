/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camaleon.logic.proyeccion;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author senneko
 */
public class Proyeccion {

    private final Set<Dependencia> dependencias;
    private final Set<String> atributos;

    public Proyeccion(Set<Dependencia> dependencias, Set<String> atributos) {
        this.dependencias = dependencias;
        this.atributos = atributos;
    }


    public Set<Dependencia> obtenerProyeccion(Set<String> subconjunto) {
        Set<Dependencia> conjuntoDependenciasG = new HashSet<>(dependencias);
        Set<String> conjuntoAttrW = Conjunto.diferenciaConjuntos(atributos, subconjunto);
        Set<String> atributoA = new HashSet<>();
        Set<Dependencia> conjuntoDepH;
        Set<Dependencia> union;

        Iterator<String> iteradorW = conjuntoAttrW.iterator();

        while (iteradorW.hasNext()) {
            atributoA.clear();
            atributoA.add(iteradorW.next());
            conjuntoDepH = obtenerDepSegunAttr(atributoA, conjuntoDependenciasG);
            union = new HashSet<>(conjuntoDepH);
            conjuntoDepH = Conjunto.quitarAttrDependencias(conjuntoDepH,
                    atributoA.toArray(new String[atributoA.size()])[0]);
            conjuntoDepH = Conjunto.retirarDependeciasTriviales(conjuntoDepH);
            union.addAll(conjuntoDepH);
            conjuntoDependenciasG = Conjunto.diferenciaConjuntoDep(conjuntoDependenciasG, union);
        }
        return conjuntoDependenciasG;
    }

    private Set<Dependencia> obtenerDepSegunAttr(Set<String> A, Set<Dependencia> G) {
        return G.stream().filter(dep -> dep.getImplicados().containsAll(A)|| dep.getImplicantes().containsAll(A)).collect(Collectors.toSet());
    }

}

package com.camaleon.logic.proyeccion;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Conjunto {

    public static Set<String> diferenciaConjuntos(Set<String> atributosT, Set<String> atributosY) {
        return Sets.difference(atributosT, atributosY);
    }

    public static Set<String> unirConjuntos(Set<String> conjuntoA, Set<String> conjuntoB) {
        return Sets.union(conjuntoA, conjuntoB);
    }

    public static Set<Dependencia> diferenciaConjuntoDep(Set<Dependencia> A, Set<Dependencia> B) {
        return Sets.difference(Sets.union(A, B), Sets.intersection(A, B));
    }

    /**
     * retira las dependencias triviales del conjunto de dependencias dado.
     *
     * @param conjuntoDependencias
     * @return
     */
    public static Set<Dependencia> retirarDependeciasTriviales(Set<Dependencia> conjuntoDependencias) {
        return conjuntoDependencias.stream().filter(dep -> !dep.esDependenciaTrivial()).collect(Collectors.toSet());        
    }

    public static Set<Dependencia> quitarAttrDependencias(Set<Dependencia> dependencias, String atributo) {
        Set<Dependencia> resultado;
        if (dependencias.size() > 1) {
            resultado = new HashSet<>();
            Set<Dependencia> nDependencia = new HashSet<>();
            // proyecta dependencias
            for (Dependencia dependencia : dependencias) {
                nDependencia.addAll(Dependencia.proyectarDependencia(dependencia));
            }
            // quitar triviales
            nDependencia = Conjunto.retirarDependeciasTriviales(nDependencia);
            Set<Dependencia> implicados = new HashSet<>();
            Set<Dependencia> implicantes = new HashSet<>();
            
            implicantes = nDependencia.stream().filter(dep -> dep.getImplicantes().contains(atributo)).collect(Collectors.toSet());
            implicados = nDependencia.stream().filter(dep -> dep.getImplicados().contains(atributo)).collect(Collectors.toSet());
            
            if (!implicados.isEmpty() && !implicantes.isEmpty()) {
                Set<String> nImplicado;
                for (Dependencia implicado : implicados) {
                    for (Dependencia implicante : implicantes) {
                        nImplicado = new HashSet<>(implicante.getImplicantes());
                        nImplicado.remove(atributo);
                        resultado.add(new Dependencia(Conjunto.unirConjuntos(nImplicado, implicado.getImplicantes()),
                                implicante.getImplicados()));
                    }
                }
            }
        } else {
            resultado = new HashSet<>(dependencias);
        }
        return resultado;
    }

}

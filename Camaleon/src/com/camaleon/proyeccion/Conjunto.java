package com.camaleon.proyeccion;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Conjunto {

    public static Set<String> diferenciaConjuntos(Set<String> atributosT, Set<String> atributosY) {
        Set<String> atributosZ = new HashSet<>(atributosT);
        for (String atributo : atributosY) {
            atributosZ.remove(atributo);
        }
        return atributosZ;
    }

    public static Set<String> unirConjuntos(Set<String> conjuntoA, Set<String> conjuntoB) {
        if (conjuntoA.isEmpty()) {
            return new HashSet<>(conjuntoB);
        }
        if (conjuntoB.isEmpty()) {
            return new HashSet<>(conjuntoA);
        }
        Set<String> union = new HashSet<>(conjuntoA);
        union.addAll(conjuntoB);

        return union;

    }

    public static Set<Dependencia> diferenciaConjuntoDep(Set<Dependencia> A, Set<Dependencia> B) {
        Set<Dependencia> subA = new HashSet<>(A);
        Set<Dependencia> subB = new HashSet<>(B);
        for (Dependencia dependenciaA : A) {
            for (Dependencia dependenciaB : B) {
                if (dependenciaA.equals(dependenciaB)) {
                    subA.remove(dependenciaA);
                    subB.remove(dependenciaA);
                }
            }
        }
        subA.addAll(subB);
        return subA;
    }

    /**
     * retira las dependencias triviales del conjunto de dependencias dado.
     *
     * @param conjuntoDependencias
     * @return
     */
    public static Set<Dependencia> retirarDependeciasTriviales(Set<Dependencia> conjuntoDependencias) {
        Set<Dependencia> dependenciaSinTriviales = new LinkedHashSet<>();
        for (Dependencia dependencia : conjuntoDependencias) {
            if (!dependencia.esDependenciaTrivial()) {
                dependenciaSinTriviales.add(dependencia);
            }
        }
        return dependenciaSinTriviales;
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
            for (Dependencia dependencia : nDependencia) {
                if (dependencia.getImplicados().contains(atributo)) {
                    implicados.add(dependencia);
                }
                if (dependencia.getImplicantes().contains(atributo)) {
                    implicantes.add(dependencia);
                }
            }
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

package com.camaleon.logic.proyeccion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Dependencia {
    /**
     * correponde a X
     */
    private Set<String> implicantes;
    /*
     * corresponde a Y
     */
    private Set<String> implicados;

    public Set<String> getImplicantes() {
        return implicantes;
    }

    public Set<String> getImplicados() {
        return implicados;
    }

    public Dependencia() {
        this(new LinkedHashSet<String>(), new LinkedHashSet<String>());
    }

    public Dependencia(Set<String> implicantes, Set<String> implicados) {
        this.implicantes = implicantes;
        this.implicados = implicados;
    }

    /**
     * una dependencia es elemental cuando su conjunto de implicados es igual a
     * 1
     *
     * @return
     */
    public boolean esDependenciaElemental() {
        return implicados.size() == 1;
    }

    /**
     * * una dependencia es trivial cuando: - el implicante y el implicado son
     * el mismo atributo - al retirar atributos iguales en implicante o
     * implicados, alguno de los dos conjuntos de atributos queda vacio.
     *
     * @return
     */
    public boolean esDependenciaTrivial() {
        return implicantes.containsAll(implicados);
    }

    public static Set<Dependencia> proyectarDependencia(Dependencia dependencia) {
        Set<Dependencia> dependenciasProyectadas = new LinkedHashSet<>();
        for (String attrImplicado : dependencia.implicados) {
            dependenciasProyectadas
                    .add(new Dependencia(dependencia.implicantes, new HashSet<>(Arrays.asList(attrImplicado))));
        }
        return dependenciasProyectadas;
    }

    @Override
    public String toString() {
        return "[" + getImplicantes().toString() + "->" + getImplicados().toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        Dependencia dep = (Dependencia) obj;
        if (dep == null)
            return false;
        return this.implicantes.equals(dep.implicantes) && this.implicados.equals(dep.implicados);
    }

}

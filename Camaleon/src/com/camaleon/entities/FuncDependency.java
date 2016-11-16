package com.camaleon.entities;

import java.util.*;

/**
 * Clase que representa una dependencia funcional de una relación
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class FuncDependency implements Comparable<FuncDependency> {

    private Map<String, Attribute> implicant;
    private Map<String, Attribute> implied;

    
    public FuncDependency() {
        super();
        this.implicant = new HashMap<>();
        this.implied = new HashMap<>();
    }

    public FuncDependency(Map<String, Attribute> implicant, Map<String, Attribute> implied) {
        super();
        this.implicant = implicant;
        this.implied = implied;
    }

    public Map<String, Attribute> getImplicant() {
        return implicant;
    }

    public void setImplicant(Map<String, Attribute> implicant) {
        this.implicant = implicant;
    }

    public Map<String, Attribute> getImplied() {
        return implied;
    }

    public void setImplied(Map<String, Attribute> implied) {
        this.implied = implied;
    }
    
    public Set<String> getImplicantKeys(){
        return implicant.keySet();
    }
    
    public Set<String> getImpliedKeys(){
        return implied.keySet();
    }

    @Override
    public String toString() {
        return "[" + implicant + " -> "
                + implied + "]";
    }

    /**
     * Indica si una dependencia funcional es trivial,
     * es trivial cuando los atributos del implicante, contiene los del implicado
     * @return boolean que indica si la dependencia es trivial
     */
    public boolean isTrivialDependency() {
        return getImplicantKeys().containsAll(getImpliedKeys());
    }

    /**
     * Proyecta una dependencia
     *
     * @return {@link Set} con las dependencias proyectadas, a partir de esta dependencia funcional
     */
    public Set<FuncDependency> project() {
        Set<FuncDependency> projectDependencies = new LinkedHashSet<>();
        for (Map.Entry<String, Attribute> entry : implied.entrySet()) {
            Map<String, Attribute> map = new HashMap<>();
            map.put(entry.getKey(), entry.getValue());
            projectDependencies
                    .add(new FuncDependency(implicant, map));
        }
        return projectDependencies;
    }

    @Override
    public int compareTo(FuncDependency o) {
        if (this.implicant.size() < o.implicant.size()) {
            return -1;
        } else if (this.implicant.size() > o.implicant.size()) {
            return 1;
        } else if (this.implicant.hashCode() < o.implicant.hashCode()) {
            return -1;
        } else if (this.implicant.hashCode() > o.implicant.hashCode()) {
            return 1;
        } else if (this.implied.size() < o.implied.size()) {
            return -1;
        } else if (this.implied.size() > o.implied.size()) {
            return -1;
        } else {
            return (this.implicant.hashCode() - o.implicant.hashCode())==0?this.implied.hashCode() - o.implied.hashCode():this.implicant.hashCode() - o.implicant.hashCode();
        }

    }

}

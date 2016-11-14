package com.camaleon.entities;

import java.util.*;

/**
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
    /**
     * 
     * @param implicant Dato de tipo HashSet para el implicante
     * @param implied Dato de tipo HashSet para el implicado
     */
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
     * * una dependencia es trivial cuando: - el implicante y el implicado son
     * el mismo atributo - al retirar atributos iguales en implicante o
     * implicados, alguno de los dos conjuntos de atributos queda vacio.
     *
     * @return
     */
    public boolean isTrivialDependency() {
        return getImplicantKeys().containsAll(getImpliedKeys());
    }

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

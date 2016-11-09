package com.camaleon.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

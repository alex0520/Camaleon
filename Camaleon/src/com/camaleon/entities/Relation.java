package com.camaleon.entities;

import java.util.*;

/**
 *
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class Relation {

    private Map<String, Attribute> attributes;
    private List<FuncDependency> dependencies;

    public Relation() {
        super();
        this.attributes = new HashMap<>();
        this.dependencies = new LinkedList<>();
    }

    public Relation(Map<String, Attribute> attributes,
                    List<FuncDependency> dependencies) {
        super();
        this.attributes = attributes;
        this.dependencies = dependencies;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<FuncDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<FuncDependency> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return "Relation [attributes=" + attributes + ", dependencies="
                + dependencies + "]";
    }
    
    public Set<String> getAttributeKeys(){
        return attributes.keySet();
    }

    public Set<String> getAttribute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param oldValue Valor Actual de un atributo
     * @param newValue Nuevo Valor del atributo
     */
    /*
    public void editAttr(String oldValue, String newValue) {
        this.attributes.remove(oldValue);
        this.attributes.add(newValue);
        for (int i = 0; i < this.dependencies.size(); i++) {
            FuncDependency funcDep = this.dependencies.get(i);
            boolean change = false;
            if (funcDep.getImplicant().contains(oldValue)) {
                funcDep.getImplicant().remove(oldValue);
                funcDep.getImplicant().add(newValue);
                change = true;
            }
            if (funcDep.getImplied().contains(oldValue)) {
                funcDep.getImplied().remove(oldValue);
                funcDep.getImplied().add(newValue);
                change = true;
            }
            if (change) {
                this.dependencies.remove(i);
                this.dependencies.add(i, funcDep);
            }
        }
    }*/

    /**
     *
     * @param oldValue Valor del atributo anterior
     */
    
    /*
    public void delAttr(String oldValue) {
        this.attributes.remove(oldValue);
        for (int i = 0; i < this.dependencies.size(); i++) {
            FuncDependency funcDep = this.dependencies.get(i);
            boolean change = false;
            boolean remove = false;
            if (funcDep.getImplicant().contains(oldValue)) {
                if (funcDep.getImplicant().size() == 1) {
                    remove = true;
                } else {
                    funcDep.getImplicant().remove(oldValue);
                    change = true;
                }
            }
            if (!remove && funcDep.getImplied().contains(oldValue)) {
                if (funcDep.getImplied().size() == 1) {
                    remove = true;
                } else {
                    funcDep.getImplied().remove(oldValue);
                    change = true;
                }
            }

            if (remove) {
                this.dependencies.remove(i);
                i--;
            }

            if (change) {
                this.dependencies.remove(i);
                this.dependencies.add(i, funcDep);
            }
        }
    }*/

}

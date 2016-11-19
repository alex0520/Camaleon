package com.camaleon.entities;

import java.util.*;

/**
 * Clase que representa una relación
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

    /**
     * Obtiene las llaves de los atributos de la relación
     *
     * @return {@link Set} con las llaves de los atributos de la relación
     */
    public Set<String> getAttributeKeys(){
        return attributes.keySet();
    }

    /**
     *Modifica el valor de un atributo
     *
     * @param oldValue Valor Actual de un atributo
     * @param newValue Nuevo Valor del atributo
     */
    public void editAttr(String oldValue, Attribute newValue) {
        this.attributes.remove(oldValue);
        this.attributes.put(newValue.getKey(),newValue);
        for (int i = 0; i < this.dependencies.size(); i++) {
            FuncDependency funcDep = this.dependencies.get(i);
            boolean change = false;
            if (funcDep.getImplicantKeys().contains(oldValue)) {
                funcDep.getImplicant().remove(oldValue);
                funcDep.getImplicant().put(newValue.getKey(), newValue);
                change = true;
            }
            if (funcDep.getImpliedKeys().contains(oldValue)) {
                funcDep.getImplied().remove(oldValue);
                funcDep.getImplied().put(newValue.getKey(), newValue);
                change = true;
            }
            if (change) {
                this.dependencies.remove(i);
                this.dependencies.add(i, funcDep);
            }
        }
    }

    /**
     *  Elimina un atributo de la relación
     *
     * @param oldValue Valor del atributo anterior
     */
    public void delAttr(String oldValue) {
        this.attributes.remove(oldValue);
        for (int i = 0; i < this.dependencies.size(); i++) {
            FuncDependency funcDep = this.dependencies.get(i);
            boolean change = false;
            boolean remove = false;
            if (funcDep.getImplicantKeys().contains(oldValue)) {
                if (funcDep.getImplicant().size() == 1) {
                    remove = true;
                } else {
                    funcDep.getImplicant().remove(oldValue);
                    change = true;
                }
            }
            if (!remove && funcDep.getImplied().containsKey(oldValue)) {
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
    }

}

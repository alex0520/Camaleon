package com.camaleon.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Relation {

    private HashSet<String> attributes;
    private List<FuncDependency> dependencies;

    public Relation() {
        super();
        this.attributes = new HashSet<String>();
        this.dependencies = new ArrayList<FuncDependency>();
    }

    public Relation(HashSet<String> attributes,
            ArrayList<FuncDependency> dependencies) {
        super();
        this.attributes = attributes;
        this.dependencies = dependencies;
    }

    public HashSet<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashSet<String> attributes) {
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
    }

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
    }

}

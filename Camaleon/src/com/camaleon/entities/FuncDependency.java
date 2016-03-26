package com.camaleon.entities;

import java.util.HashSet;

public class FuncDependency implements Comparable<FuncDependency> {

    private HashSet<String> implicant;
    private HashSet<String> implied;

    public FuncDependency() {
        super();
        this.implicant = new HashSet<String>();
        this.implied = new HashSet<String>();
    }

    public FuncDependency(HashSet<String> implicant, HashSet<String> implied) {
        super();
        this.implicant = implicant;
        this.implied = implied;
    }

    public HashSet<String> getImplicant() {
        return implicant;
    }

    public void setImplicant(HashSet<String> implicant) {
        this.implicant = implicant;
    }

    public HashSet<String> getImplied() {
        return implied;
    }

    public void setImplied(HashSet<String> implied) {
        this.implied = implied;
    }

//	@Override
//	public String toString() {
//		return "FuncDependency [implicant=" + implicant + ", implied="
//				+ implied + "]";
//	}
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

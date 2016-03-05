package com.camaleon.entities;

import java.util.HashSet;

public class FuncDependency {

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

	@Override
	public String toString() {
		return "FuncDependency [implicant=" + implicant + ", implied="
				+ implied + "]";
	}

}

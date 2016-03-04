package com.camaleon.entities;

public class FuncDependency {

	private AttributeGrp implicant;
	private AttributeGrp implied;

	public FuncDependency() {
		super();
		this.implicant = new AttributeGrp();
		this.implied = new AttributeGrp();
	}

	public FuncDependency(AttributeGrp implicant, AttributeGrp implied) {
		super();
		this.implicant = implicant;
		this.implied = implied;
	}

	public AttributeGrp getImplicant() {
		return implicant;
	}

	public void setImplicant(AttributeGrp implicant) {
		this.implicant = implicant;
	}

	public AttributeGrp getImplied() {
		return implied;
	}

	public void setImplied(AttributeGrp implied) {
		this.implied = implied;
	}

	@Override
	public String toString() {
		return "FuncDependency [implicant=" + implicant + ", implied="
				+ implied + "]";
	}

}

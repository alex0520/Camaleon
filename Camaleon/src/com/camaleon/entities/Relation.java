package com.camaleon.entities;

import java.util.ArrayList;
import java.util.List;

public class Relation {

	private AttributeGrp attributes;
	private List<FuncDependency> dependencies;

	public Relation() {
		super();
		this.attributes = new AttributeGrp();
		this.dependencies = new ArrayList<FuncDependency>();
	}

	public Relation(AttributeGrp attributes, List<FuncDependency> dependencies) {
		super();
		this.attributes = attributes;
		this.dependencies = dependencies;
	}

	@Override
	public String toString() {
		return "Relation [attributes=" + attributes + ", dependencies="
				+ dependencies + "]";
	}
	
}

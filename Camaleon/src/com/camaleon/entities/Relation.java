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

}

/**
 * 
 */
package com.camaleon.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author LizethValbuena Clase que define un grupo de atributos
 * 
 */
public class AttributeGrp {

	private HashSet<String> attributes;

	public AttributeGrp() {
		super();
		this.attributes = new HashSet<String>();
	}

	public AttributeGrp(HashSet<String> attributes) {
		super();
		this.setAttributes(attributes);
	}

	public HashSet<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashSet<String> attributes) {
		List<String> sortedList = new ArrayList<String>(attributes);
		Collections.sort(sortedList);
		this.attributes = new HashSet<String>(sortedList);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AttributeGrp)) {
			return false;
		}
		AttributeGrp other = (AttributeGrp) o;
		return ((this.attributes.size() == other.getAttributes().size()) && (this.attributes
				.containsAll(other.getAttributes())));
	}

	@Override
	public int hashCode() {
		int hashCode = 1;
		for (Iterator<String> iterator = this.attributes.iterator(); iterator
				.hasNext();) {
			String attribute = (String) iterator.next();
			hashCode *= attribute.hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		String separator = "";
		StringBuilder sb = new StringBuilder();
		for (String s : this.attributes) {
			sb.append(separator).append(s);
			separator = ",";
		}
		return "AttributeGrp [attributes=" + sb.toString() + "]";
	}

}

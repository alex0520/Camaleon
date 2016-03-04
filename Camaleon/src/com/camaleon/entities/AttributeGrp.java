/**
 * 
 */
package com.camaleon.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author LizethValbuena Clase que define un grupo de atributos
 * 
 */
public class AttributeGrp {

	private List<String> attributes;

	public AttributeGrp() {
		super();
		this.attributes = new ArrayList<String>();
	}

	public AttributeGrp(List<String> attributes) {
		super();
		Collections.sort(attributes);
		this.attributes = attributes;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
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
		StringBuilder sb = new StringBuilder();
		for (String s : this.attributes) {
			sb.append(s);
			sb.append(",");
		}
		return "AttributeGrp [attributes=" + sb.toString() + "]";
	}

}

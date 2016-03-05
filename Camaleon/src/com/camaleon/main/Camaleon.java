package com.camaleon.main;

import java.util.HashSet;
import java.util.Set;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.camaleon.logic.MinimalCover;
import com.camaleon.logic.Util;
import com.google.common.collect.Sets;

public class Camaleon {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Relation relacion = new Relation();
		FuncDependency dependency = new FuncDependency();
		HashSet<String> atributos = new HashSet<String>();
		HashSet<String> implicante = new HashSet<String>();
		HashSet<String> implicado = new HashSet<String>();
		atributos.add("B");
		atributos.add("C");
		atributos.add("D");
		atributos.add("E");
		atributos.add("A");
		atributos.add("F");
		relacion.setAttributes(atributos);

		implicante.add("A");
		implicante.add("B");
		implicado.add("C");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("B");
		implicado.add("E");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("C");
		implicante.add("D");
		implicado.add("E");
		implicado.add("F");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		Set<Set<String>> powerSet = Sets.powerSet(relacion.getAttributes());

		System.out.println(relacion);

		relacion.setDependencies(MinimalCover.rightDecomposition(relacion
				.getDependencies()));

		System.out.println(relacion);

		Util.closure(relacion.getAttributes(), relacion.getDependencies());

	}
}

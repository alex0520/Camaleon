package com.camaleon.main;

import java.util.HashMap;
import java.util.HashSet;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.camaleon.logic.MinimalCover;
import com.camaleon.logic.Util;

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
		HashMap<HashSet<String>, HashSet<String>> closures = new HashMap<HashSet<String>, HashSet<String>>();
		
		atributos.add("A");
		atributos.add("B");
		atributos.add("C");
		atributos.add("D");
		atributos.add("E");
		atributos.add("F");
		atributos.add("G");
		relacion.setAttributes(atributos);

		//A -> B
		implicante.add("A");
		implicado.add("B");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		//ABCD -> E
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("A");
		implicante.add("B");
		implicante.add("C");
		implicante.add("D");
		implicado.add("E");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		//EF -> GH
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("E");
		implicante.add("F");
		implicado.add("G");
		implicado.add("H");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);		
		relacion.getDependencies().add(dependency);
		
		//ACDF -> EG
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("A");
		implicante.add("C");
		implicante.add("D");
		implicante.add("F");
		implicado.add("E");
		implicado.add("G");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);		
		relacion.getDependencies().add(dependency);

		System.out.println("L: "+relacion);

		relacion.setDependencies(MinimalCover.rightDecomposition(relacion.getDependencies()));

		System.out.println("L0: "+relacion);
		
		relacion.setDependencies(MinimalCover.removeStrangeElemLeft(relacion.getDependencies(),closures));
		
		System.out.println("L1: "+relacion);

	}
}

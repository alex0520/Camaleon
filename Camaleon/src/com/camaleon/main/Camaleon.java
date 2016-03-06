package com.camaleon.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.camaleon.logic.CandidateKeys;
import com.camaleon.logic.MinimalCover;

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
		relacion.setAttributes(atributos);

		// AB -> C
		implicante.add("A");
		implicante.add("B");
		implicado.add("C");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		// D -> EF
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("F");
		implicado.add("E");
		implicado.add("F");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		// C -> A
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("C");
		implicado.add("A");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		// BE -> C
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("B");
		implicante.add("E");
		implicado.add("C");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		// BC -> D
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("B");
		implicante.add("C");
		implicado.add("E");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		// CF -> BD
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("C");
		implicante.add("F");
		implicado.add("B");
		implicado.add("D");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		// ACD -> B
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("A");
		implicante.add("C");
		implicante.add("D");
		implicado.add("B");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);
		
		// CE -> AF
		dependency = new FuncDependency();
		implicante = new HashSet<String>();
		implicado = new HashSet<String>();
		implicante.add("C");
		implicante.add("E");
		implicado.add("A");
		implicado.add("F");
		dependency.setImplicant(implicante);
		dependency.setImplied(implicado);
		relacion.getDependencies().add(dependency);

		System.out.println("L: " + relacion);

		relacion.setDependencies(MinimalCover.rightDecomposition(relacion.getDependencies()));

		System.out.println("L0: " + relacion);

		relacion.setDependencies(MinimalCover.removeStrangeElemLeft(relacion.getDependencies(), closures));

		System.out.println("L1: " + relacion);

		relacion.setDependencies(MinimalCover.removeRedundantDependencies(relacion.getDependencies()));

		System.out.println("L2: " + relacion);

		List<HashSet<String>> keys = CandidateKeys.candidateKeys(relacion, closures);

		System.out.println("LLaves Candidatas: " + keys);

	}
}

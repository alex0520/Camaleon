package com.camaleon.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.camaleon.entities.Relation;
import com.camaleon.logic.CandidateKeys;
import com.camaleon.logic.LoadFile;
import com.camaleon.logic.MinimalCover;

public class Camaleon {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Relation relacion = LoadFile
				.loadFile("C:/Users/USUARIO/Desktop/JSON3.JSON");

		HashMap<HashSet<String>, HashSet<String>> closures = new HashMap<HashSet<String>, HashSet<String>>();

		System.out.println("L: " + relacion);

		relacion.setDependencies(MinimalCover.rightDecomposition(relacion
				.getDependencies()));

		System.out.println("L0: " + relacion);

		relacion.setDependencies(MinimalCover.removeStrangeElemLeft(
				relacion.getDependencies(), closures));

		System.out.println("L1: " + relacion);

		relacion.setDependencies(MinimalCover
				.removeRedundantDependencies(relacion.getDependencies()));

		System.out.println("L2: " + relacion);

		List<HashSet<String>> keys = CandidateKeys.candidateKeys(relacion,
				closures);

		System.out.println("LLaves Candidatas: " + keys);

	}
}

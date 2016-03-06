package com.camaleon.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;

public class ReadCVS {

	public static Relation loadFile(String filePath, String separator,
			String attrSeparator) {

		Relation relation = new Relation();

		String csvFile = filePath;
		BufferedReader br = null;
		String line = "";
		Integer lineNumber = 0;

		try {
			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				if (lineNumber == 0) {
					String[] attrs = line.split(separator);
					HashSet<String> attrSet = new HashSet<String>(
							Arrays.asList(attrs));
					relation.setAttributes(attrSet);
				} else {
					String[] funDefParts = line.split(separator);
					FuncDependency funcDependency = new FuncDependency();
					String[] implicant = funDefParts[0].split(attrSeparator);
					HashSet<String> implicantSet = new HashSet<String>(
							Arrays.asList(implicant));
					String[] implied = funDefParts[1].split(attrSeparator);
					HashSet<String> impliedSet = new HashSet<String>(
							Arrays.asList(implied));
					funcDependency.setImplicant(implicantSet);
					funcDependency.setImplied(impliedSet);
					relation.getDependencies().add(funcDependency);
				}
				lineNumber++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return relation;
	}

}
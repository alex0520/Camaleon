package com.camaleon.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;

public class LoadFile {
	@SuppressWarnings("unchecked")
	public static Relation loadFile(String filePath) {

		Relation relation = new Relation();
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jsonAttributes = (JSONArray) jsonObject.get("attributes");
			HashSet<String> attributes = new HashSet<String>();
			for (Iterator<String> iterator = jsonAttributes.iterator(); iterator
					.hasNext();) {
				String attribute = (String) iterator.next();
				attributes.add(attribute);
			}

			relation.setAttributes(attributes);

			JSONArray jsonFuncDeps = (JSONArray) jsonObject
					.get("functionalDependencies");

			for (int i = 0; i < jsonFuncDeps.size(); i++) {
				FuncDependency funcDependency = new FuncDependency();

				JSONObject jsonFuncDep = (JSONObject) jsonFuncDeps.get(i);
				JSONArray jsonImplicant = (JSONArray) jsonFuncDep
						.get("implicant");

				HashSet<String> implicant = new HashSet<String>();
				for (Iterator<String> iterator = jsonImplicant.iterator(); iterator
						.hasNext();) {
					String attribute = (String) iterator.next();
					implicant.add(attribute);
				}

				JSONArray jsonImplied = (JSONArray) jsonFuncDep.get("implied");

				HashSet<String> implied = new HashSet<String>();
				for (Iterator<String> iterator = jsonImplied.iterator(); iterator
						.hasNext();) {
					String attribute = (String) iterator.next();
					implied.add(attribute);
				}

				funcDependency.setImplicant(implicant);
				funcDependency.setImplied(implied);
				relation.getDependencies().add(funcDependency);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return relation;
	}

}
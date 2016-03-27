package com.camaleon.logic;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.LoadFileResult;
import com.camaleon.entities.Relation;
import java.util.ArrayList;
import java.util.List;

public class LoadFile {

    @SuppressWarnings("unchecked")
    public static LoadFileResult loadFile(String filePath) {

        LoadFileResult loadFileResult = new LoadFileResult();
        Relation relation = new Relation();
        JSONParser parser = new JSONParser();
        List<FuncDependency> trivialDependencies = new ArrayList<FuncDependency>();
        if (filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length()).equalsIgnoreCase("json")) {
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
                    
                    if(!relation.getAttributes().containsAll(implicant) || !relation.getAttributes().containsAll(implied)){
                        StringBuilder sb = new StringBuilder();
                        sb.append("Alguno de los atributos de la siguiente dependencia funcional no ha sido definido en el conjunto de atributos: ");
                        sb.append(funcDependency);
                        sb.append(".");
                        
                        throw new Exception(sb.toString());
                    }
                    
                    if (implicant.containsAll(implied)) {
                       trivialDependencies.add(funcDependency);
                    } else {
                        relation.getDependencies().add(funcDependency);
                    }
                }
                loadFileResult.setStatus(LoadFileResult.Status.SUCCESS);
                loadFileResult.setRelation(relation);
                if(trivialDependencies.size()>0){
                    StringBuilder sb = new StringBuilder();
                    sb.append("- Las siguientes dependencias son triviales y no fueron cargadas: ");
                    sb.append(trivialDependencies);
                    sb.append(".");
                    loadFileResult.getMessages().add(sb.toString());
                }

            } catch (Exception e) {
                loadFileResult.setStatus(LoadFileResult.Status.ERROR);
                loadFileResult.getMessages().add(new StringBuilder("- ").append(e.getMessage()).toString());
            }
        } else {
            loadFileResult.setStatus(LoadFileResult.Status.ERROR);
            loadFileResult.getMessages().add(new StringBuilder("- ").append("Ud no selecciono un archivo válido").toString());
        }

        return loadFileResult;
    }

}

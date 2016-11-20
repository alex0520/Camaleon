package com.camaleon.logic;

import com.camaleon.entities.Attribute;
import com.camaleon.entities.AttributeDataType;
import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.LoadFileResult;
import com.camaleon.entities.Relation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Clase encargada de realizar la carga de una {@link Relation} a partir
 * de un archivo JSON
 * @author Lizeth Valbuena, Alexander Lozano
 */
public class LoadFile {

    /**
     * Metodo que se encarga de la carga de una {@link Relation} a partir
     * de un archivo JSON
     *
     * @param filePath Ruta del archivo
     * @return Objeto {@link LoadFileResult} con el resultado de la carga
     */
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
                Map<String, Attribute> attributes = new HashMap<>();
                for (int i = 0; i < jsonAttributes.size(); i++) {
                    Attribute attribute = new Attribute();
                    JSONObject jsonAttribute = (JSONObject) jsonAttributes.get(i);
                    attribute.setKey(jsonAttribute.get("key").toString());
                    attribute.setName(jsonAttribute.get("name").toString());
                    attribute.setType(AttributeDataType.valueOf(jsonAttribute.get("type").toString()));
                    if (attribute.getType().requiresLength()) {
                        Long length = (Long) jsonAttribute.get("length");
                        if(length==null){
                            throw new Exception("El tipo de dato "+attribute.getType()+" del atributo "+attribute.getName()+" requiere que se especifique longitud");
                        }
                        attribute.setLength(length);
                    }
                    if (attribute.getType().requiresScale()) {
                        Long scale = (Long) jsonAttribute.get("scale");
                        if(scale==null){
                            throw new Exception("El tipo de dato "+attribute.getType()+" del atributo "+attribute.getName()+" requiere que se especifique escala");
                        }
                        attribute.setScale(scale);
                    }
                    attributes.put(attribute.getKey(), attribute);
                }

                relation.setAttributes(attributes);

                JSONArray jsonFuncDeps = (JSONArray) jsonObject
                        .get("functionalDependencies");

                for (int i = 0; i < jsonFuncDeps.size(); i++) {
                    FuncDependency funcDependency = new FuncDependency();

                    JSONObject jsonFuncDep = (JSONObject) jsonFuncDeps.get(i);
                    JSONArray jsonImplicant = (JSONArray) jsonFuncDep
                            .get("implicant");

                    Map<String, Attribute> implicant = new HashMap<>();
                    for (Iterator<String> iterator = jsonImplicant.iterator(); iterator
                            .hasNext();) {
                        String attributekey = (String) iterator.next();
                        if (relation.getAttributes().containsKey(attributekey)) {
                            implicant.put(attributekey, relation.getAttributes().get(attributekey));
                        }
                    }

                    JSONArray jsonImplied = (JSONArray) jsonFuncDep.get("implied");

                    Map<String, Attribute> implied = new HashMap<>();
                    for (Iterator<String> iterator = jsonImplied.iterator(); iterator
                            .hasNext();) {
                        String attributekey = (String) iterator.next();
                        if (relation.getAttributes().containsKey(attributekey)) {
                            implied.put(attributekey, relation.getAttributes().get(attributekey));
                        }
                    }

                    funcDependency.setImplicant(implicant);
                    funcDependency.setImplied(implied);

                    if (!relation.getAttributeKeys().containsAll(funcDependency.getImplicantKeys()) || !relation.getAttributeKeys().containsAll(funcDependency.getImpliedKeys())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Alguno de los atributos de la siguiente dependencia funcional no ha sido definido en el conjunto de atributos: ");
                        sb.append(funcDependency);
                        sb.append(".");

                        throw new Exception(sb.toString());
                    }
                    
                    if (funcDependency.getImplicantKeys().containsAll(funcDependency.getImpliedKeys())) {
                        trivialDependencies.add(funcDependency);
                    } else {
                        relation.getDependencies().add(funcDependency);
                    }
                }
                loadFileResult.setStatus(LoadFileResult.Status.SUCCESS);
                loadFileResult.setRelation(relation);
                if (trivialDependencies.size() > 0) {
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

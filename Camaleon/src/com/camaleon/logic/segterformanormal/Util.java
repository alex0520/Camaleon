package com.camaleon.logic.segterformanormal;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class Util {

    public static String getStringFromFuntionalDependencies(List<DependenciaFuncional> dependencias) {

        String res = "{";
        for (DependenciaFuncional dependenciaFuncional : dependencias) {
            res += dependenciaFuncional.getImplicantesAsString("") + " -> "
                    + dependenciaFuncional.getImplicadosAsString("") + " , ";
        }
        res = removeCharactersFromTail(res, 2);
        res += "}";
        return res;
    }

    public static String getStringFormArrayBiDimentional(List<List<String>> dependenciasAgrupadas) {
        String res = "";

        for (List<String> arrayList : dependenciasAgrupadas) {
            res += getStringFormArray(arrayList) + ",";
        }
        res = removeCharactersFromTail(res, 1);
        return res;
    }

    public static String getStringFormArray(List<String> arreglo) {
        String res = "(";

        for (String string : arreglo) {
            res += string + ",";
        }
        res = removeCharactersFromTail(res, 1);

        res += ")";
        return res;
    }

    public static String removeCharactersFromTail(String res, int i) {

        if (res.length() > i) {
            res = res.substring(0, res.length() - (i));
        }
        return res;
    }

    public static List<Atribute> convertirArrayToList(List<String> atributos) {
        List<Atribute> nuevosAtributo = new ArrayList<Atribute>();
        for (int i = 0; i < atributos.size(); i++) {
            nuevosAtributo.add(new Atribute(atributos.get(i)));
        }
        return nuevosAtributo;
    }

    public static Generator<String> obtenerTodasLasCombinatorias(List<String> attributeStringList) {

        String[] aux = new String[attributeStringList.size()];
        attributeStringList.toArray(aux);
        ICombinatoricsVector<String> initialSet = Factory.createVector(aux);
        // Create an instance of the subset generator
        Generator<String> gen = Factory.createSubSetGenerator(initialSet);
        return gen;
    }

    public static void imprimirListaAtt(List<Atribute> combinacionAtributos) {
        System.out.print("Atributo: ");
        for (Atribute atribute : combinacionAtributos) {
            System.out.print("-" + atribute.getNombre());
        }
        System.out.println();
    }

    public static List<String> obtenerArrayStringListDeListaDeAtributos(List<Atribute> attributeList) {
        List<String> attributeStringList = new ArrayList<>();
        for (Atribute atribute : attributeList) {
            attributeStringList.add(atribute.getNombre());
        }
        return attributeStringList;
    }
}

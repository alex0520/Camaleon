package com.camaleon.main;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.LoadFileResult;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.camaleon.entities.Relation;
import com.camaleon.logic.Bernstein;
import com.camaleon.logic.CandidateKeys;
import com.camaleon.logic.LoadFile;
import com.camaleon.logic.MinimalCover;
import com.camaleon.logic.proyeccion.Dependencia;
import com.camaleon.logic.segterformanormal.Atribute;
import com.camaleon.logic.segterformanormal.DependenciaFuncional;
import com.camaleon.logic.segterformanormal.NormalForm;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Camaleon {

    /**
     * @param args
     */
    public static void main(String[] args) {

        LoadFileResult loadFileResult = LoadFile.loadFile("C:/Users/ASUS/Downloads/test_attr.json");

        if (loadFileResult.getStatus().equals(LoadFileResult.Status.SUCCESS)) {
            Relation relacion = loadFileResult.getRelation();

            HashMap<Set<String>, Set<String>> closures = new HashMap<Set<String>, Set<String>>();

            System.out.println("L: " + relacion);

            relacion.setDependencies(MinimalCover.rightDecomposition(relacion
                    .getDependencies()));

            System.out.println("L0: " + relacion);

            relacion.setDependencies(MinimalCover.removeStrangeElemLeft(
                    relacion, closures));

            System.out.println("L1: " + relacion);

            relacion.setDependencies(MinimalCover
                    .removeRedundantDependencies(relacion.getDependencies()));

            System.out.println("L2: " + relacion);

            List<HashSet<String>> keys = CandidateKeys.candidateKeys(relacion,
                    closures);

            System.out.println("LLaves Candidatas: " + keys);

            List<Relation> bernstein = Bernstein.getBernstein(relacion.getAttributeKeys(), relacion.getDependencies());
            System.out.println("Bernstein:" + bernstein);

            Function<String, Atribute> convertFromStringToAtribute = new Function<String, Atribute>() {
                @Override
                public Atribute apply(String t) {
                    return new Atribute(t);
                }
            };

            Function<FuncDependency, DependenciaFuncional> convertFromFuncDepToDepFunc = new Function<FuncDependency, DependenciaFuncional>() {
                public DependenciaFuncional apply(FuncDependency t) {
                    List<Atribute> implicante = t.getImplicantKeys().stream().map(convertFromStringToAtribute).collect(Collectors.<Atribute>toList());
                    List<Atribute> implicado = t.getImpliedKeys().stream().map(convertFromStringToAtribute).collect(Collectors.<Atribute>toList());
                    DependenciaFuncional f = new DependenciaFuncional(implicante, implicado);
                    return f;
                }
            };

            List<DependenciaFuncional> dependencias = relacion.getDependencies().stream().map(convertFromFuncDepToDepFunc).collect(Collectors.<DependenciaFuncional>toList());
            List<Atribute> atributos = relacion.getAttributeKeys().stream().map(convertFromStringToAtribute).collect(Collectors.<Atribute>toList());
            List<List<Atribute>> keysList = new ArrayList<>();
            for (Iterator<HashSet<String>> iterator = keys.iterator(); iterator.hasNext();) {
                HashSet<String> key = iterator.next();
                List<Atribute> collect = key.stream().map(convertFromStringToAtribute).collect(Collectors.<Atribute>toList());
                keysList.add(collect);
            }
            NormalForm normalForm = new NormalForm();
            boolean segundaFormaNormal = normalForm.validarSiEstaEnSegundaFormaNormal(dependencias, atributos, keysList);
            boolean terceraFormaNormal = normalForm.validarSiEstaEnTerceraFormaNormal(dependencias, keysList);

        }

    }
}

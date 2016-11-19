package com.camaleon.main;

import com.camaleon.entities.LoadFileResult;
import com.camaleon.entities.Relation;
import com.camaleon.logic.Bernstein;
import com.camaleon.logic.CandidateKeys;
import com.camaleon.logic.LoadFile;
import com.camaleon.logic.MinimalCover;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

            Map<String, Relation> bernstein = Bernstein.getBernstein(relacion);
            System.out.println("Bernstein:" + bernstein);
/*
            Function<FuncDependency, DependenciaFuncional> convertFromFuncDepToDepFunc = new Function<FuncDependency, DependenciaFuncional>() {
                public DependenciaFuncional apply(FuncDependency t) {
                    List<Atribute> implicante = t.getImplicantKeys().stream().map(key -> new Atribute(key)).collect(Collectors.toList());
                    List<Atribute> implicado = t.getImpliedKeys().stream().map(key -> new Atribute(key)).collect(Collectors.toList());
                    DependenciaFuncional f = new DependenciaFuncional(implicante, implicado);
                    return f;
                }
            };

            List<DependenciaFuncional> dependencias = relacion.getDependencies().stream().map(convertFromFuncDepToDepFunc).collect(Collectors.<DependenciaFuncional>toList());
            List<Atribute> atributos = relacion.getAttributeKeys().stream().map(key -> new Atribute(key)).collect(Collectors.<Atribute>toList());
            List<List<Atribute>> keysList = new ArrayList<>();
            for (Iterator<HashSet<String>> iterator = keys.iterator(); iterator.hasNext();) {
                HashSet<String> key = iterator.next();
                List<Atribute> collect = key.stream().map(str -> new Atribute(str)).collect(Collectors.<Atribute>toList());
                keysList.add(collect);
            }
            NormalForm normalForm = new NormalForm();
            boolean segundaFormaNormal = normalForm.validarSiEstaEnSegundaFormaNormal(dependencias, atributos, keysList);
            boolean terceraFormaNormal = normalForm.validarSiEstaEnTerceraFormaNormal(dependencias, keysList);
*/
        }

    }
}

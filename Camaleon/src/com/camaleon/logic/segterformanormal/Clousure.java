package com.camaleon.logic.segterformanormal;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Clousure {
    
    public List<Atribute> calcularCierre(List<Atribute> alAtributosCierre, List<Atribute> alAtributosCierreRecorridos, List<DependenciaFuncional> dependencia, List<Atribute> alAtributosCierreCalculado, List<Atribute> alOpcionalimplicados, boolean bolOmitirDependencia) {
        /*si el subconjunto a evaluar de la combinatoria es el primero lo agrega al arrayList de
        atributos recorridos algo asi como : A+ = A
        */
        if (alAtributosCierreRecorridos.isEmpty()) {
            alAtributosCierreRecorridos = new ArrayList<>(alAtributosCierre);
        }

        List<Atribute> alControl = new ArrayList<>(alAtributosCierreRecorridos);
        /* Por cada dependencia funcional hace un recorrido */
        for (DependenciaFuncional objDependencia : dependencia) {
            
           
            int cont = objDependencia.implicantes.size();
             /* Por cada atributoCierreRecorrido en el AL*/
            for (Atribute objAtributoCierre : alControl) {
                
                /*  Si los implicantes de la dependencia contienen el AtributoCierre actual
                    disminuye el contador en 1 A contiene ABC,  B contiene ABC, C contiene ABC daria cero
                */
                if (objDependencia.implicantes.contains(objAtributoCierre)) {
                    cont--;
                }
            }

            if (cont == 0) {
                for (Atribute objAtributo : objDependencia.implicados) {
                    
                    /* Si el conjunto de atributosCierreRecorridos no contiene
                    al atributo implicado actual se agrega a la lista de atributos
                    recorridos
                    */
                    if (!alAtributosCierreRecorridos.contains(objAtributo)) {
                        alAtributosCierreRecorridos.add(objAtributo);
                    }
                }
            }

        }

        ///Si los atributos alAtributosCierre es igual alAtributosCierreRecorridos quiere decir que no tenia 
        ///implicantes que adicionar y se finaliza la recursividad y se retorna
        if (getAtributosOrdenados(alControl).equals(getAtributosOrdenados(alAtributosCierreRecorridos))) {
            return alAtributosCierreRecorridos;
        } else {
            calcularCierre(alAtributosCierre, alAtributosCierreRecorridos, dependencia, alAtributosCierreCalculado, alOpcionalimplicados, bolOmitirDependencia);
        }

        return alAtributosCierreRecorridos;
    }

    private String getAtributosOrdenados(List<Atribute> alListaAtributos) {
        String strResultado[] = new String[alListaAtributos.size()];

        if (alListaAtributos.size() > 1) {
            int Cont = 0;
            for (Atribute atributo : alListaAtributos) {
                strResultado[Cont] = atributo.getNombre();
                Cont++;
            }
            Arrays.sort(strResultado);
            return Arrays.toString(strResultado);
        } else {
            strResultado[0] = alListaAtributos.get(0).getNombre();
        }

        return strResultado[0];
    }
}

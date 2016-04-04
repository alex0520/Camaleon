package com.camaleon.logic.segterformanormal;

import java.util.List;

public class DependenciaFuncional {

    protected List<Atribute> implicantes;
    protected List<Atribute> implicados;

    public DependenciaFuncional(List<Atribute> implicantes, List<Atribute> implicados) {
        this.implicantes = implicantes;
        this.implicados = implicados;

    }

    public String getImplicantesAsString(String separator) {
        String elements = "";
        for (Atribute atribute : implicantes) {
            elements += atribute.getNombre() + separator;
        }
        return elements;
    }

    public String getImplicadosAsString(String separator) {
        String elements = "";
        for (Atribute atribute : implicados) {
            elements += atribute.getNombre() + separator;
        }
        return elements;
    }

    public List<Atribute> getImplicantes() {
        return implicantes;
    }

    public void setImplicantes(List<Atribute> implicantes) {
        this.implicantes = implicantes;
    }

    public List<Atribute> getImplicados() {
        return implicados;
    }

    public void setImplicados(List<Atribute> implicados) {
        this.implicados = implicados;
    }

    @Override
    public String toString() {
        String s = "";
        for (Atribute implicante : implicantes) {
            s += implicante.getNombre();
        }
        s += "->";
        for (Atribute implicado : implicados) {
            s += implicado.getNombre();
        }
        return s;
    }

}

package com.camaleon.logic.segterformanormal;

public class Atribute {

    private String nombre;

    public Atribute(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        Atribute objAtributo = (Atribute) o;
        if (objAtributo.nombre.equals(nombre)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getNombre();
    }

}

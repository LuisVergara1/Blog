package com.Blog.entity;

public enum Rol {
    
    ADMINISTRADOR("A"),
    USUARIO("B"),
    INVITADO("C");

    private final String codigo;

    Rol(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static Rol fromCodigo(String codigo) {
        for (Rol rol : values()) {
            if (rol.getCodigo().equals(codigo)) {
                return rol;
            }
        }
        return null;
    }
}


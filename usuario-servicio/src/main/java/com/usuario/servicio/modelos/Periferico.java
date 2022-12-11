package com.usuario.servicio.modelos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Periferico {

    private @Getter @Setter String nombre;
    private @Getter @Setter String marca;
    private @Getter @Setter int usuarioId;
}

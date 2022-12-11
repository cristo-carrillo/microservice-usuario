package com.usuario.servicio.modelos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
public class Rol {

    private @Getter @Setter String nombre;
    private @Getter @Setter Date fechaIngreso;
    private @Getter @Setter int usuarioId;

}

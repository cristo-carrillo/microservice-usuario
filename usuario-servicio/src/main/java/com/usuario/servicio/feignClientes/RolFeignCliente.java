package com.usuario.servicio.feignClientes;

import com.usuario.servicio.modelos.Rol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "rol-servicio",url = "http://localhost:8002")
@RequestMapping("/rol")
public interface RolFeignCliente {

    @PostMapping()
    public Rol save(@RequestBody Rol rol);

    @GetMapping("/usuario/{usuarioId}")
    public List<Rol> getRoles(@PathVariable("usuarioId") int usuarioId);

}

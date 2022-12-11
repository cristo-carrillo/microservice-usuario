package com.usuario.servicio.feignClientes;

import com.usuario.servicio.modelos.Periferico;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "periferico-servicio",url = "http://localhost:8003")
@RequestMapping("/periferico")
public interface PerifericoFeignCliente {

    @PostMapping()
    public Periferico save(@RequestBody Periferico periferico);

    @GetMapping("/usuario/{usuarioId}")
    public List<Periferico> getPerifericos(@PathVariable("usuarioId") int usuarioId);
}

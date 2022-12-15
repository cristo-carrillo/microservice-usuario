package com.usuario.servicio.controlador;

import com.usuario.servicio.entidades.Usuario;
import com.usuario.servicio.modelos.Rol;
import com.usuario.servicio.modelos.Periferico;
import com.usuario.servicio.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioServicio.getAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id")int id){
        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioServicio.save(usuario);
        return nuevoUsuario == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/roles/{usuarioId}")
    public ResponseEntity<List<Rol>> getRoles(@PathVariable("usuarioId")int id){

        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<Rol> roles = usuarioServicio.getRoles(id);
        return roles.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(roles);
    }

    @GetMapping("/perifericos/{usuarioId}")
    public ResponseEntity<List<Periferico>> getPerifericos(@PathVariable("usuarioId")int id){

        Usuario usuario = usuarioServicio.getUsuarioById(id);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        List<Periferico> perifericos = usuarioServicio.getPerifericos(id);
        return perifericos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(perifericos);
    }

    @PostMapping("/rol/{usuarioId}")
    public ResponseEntity<Rol> guardarRol(@PathVariable("usuarioId") int usuarioId, @RequestBody Rol rol){

        Rol nuevoRol = usuarioServicio.saveRol(usuarioId, rol);
        return nuevoRol == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(nuevoRol);

    }

    @PostMapping("/periferico/{usuarioId}")
    public ResponseEntity<Periferico> guardarPeriferico(@PathVariable("usuarioId") int usuarioId, @RequestBody Periferico periferico){

        Periferico nuevoPeriferico = usuarioServicio.savePeriferico(usuarioId, periferico);
        return  nuevoPeriferico == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(nuevoPeriferico);

    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String,Object>> listarTodasAreas(@PathVariable("usuarioId") int usuarioId){
        Map<String,Object> resultado = usuarioServicio.getUsuarioAndArea(usuarioId);
        return ResponseEntity.ok(resultado);
    }

}



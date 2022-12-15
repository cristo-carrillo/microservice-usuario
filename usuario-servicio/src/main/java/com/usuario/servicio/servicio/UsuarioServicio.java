package com.usuario.servicio.servicio;

import com.usuario.servicio.entidades.Usuario;
import com.usuario.servicio.feignClientes.RolFeignCliente;
import com.usuario.servicio.feignClientes.PerifericoFeignCliente;
import com.usuario.servicio.modelos.Rol;
import com.usuario.servicio.modelos.Periferico;
import com.usuario.servicio.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UsuarioServicio {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolFeignCliente rolFeignCliente;

    @Autowired
    private PerifericoFeignCliente perifericoFeignCliente;

    public List<Rol> getRoles(int usuarioId) {
        try {
            return restTemplate.getForObject("http://localhost:8002/rol/usuario/" + usuarioId, List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Periferico> getPerifericos(int usuarioId) {
        try {
            return restTemplate.getForObject("http://localhost:8003/periferico/usuario/" + usuarioId, List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Rol saveRol(int usuarioId, Rol rol) {
        try {
            rol.setUsuarioId(usuarioId);
            return rolFeignCliente.save(rol);
        } catch (Exception e) {
            return null;
        }
    }

    public Periferico savePeriferico(int usuarioId, Periferico periferico) {
        try {
            periferico.setUsuarioId(usuarioId);
            return perifericoFeignCliente.save(periferico);
        }catch (Exception e){
            return  null;
        }
    }

    public Map<String, Object> getUsuarioAndArea(int usuarioId) {
        Map<String, Object> response = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null) {
            response.put("msj", "El usuario no se encuentra registrado");
            return response;
        }
        response.put("Usuario", usuario);
        try {
            List<Rol> roles = rolFeignCliente.getRoles(usuarioId);
            response.put("Roles", roles == null ? "El usuario no tiene rol" : roles);
        } catch (Exception e) {
            response.put("Roles", "El microservicio Roles no esta disponible");
        }

        try {
            List<Periferico> perifericos = perifericoFeignCliente.getPerifericos(usuarioId);
            response.put("Perifericos", perifericos == null ? "El usuario no tiene perifericos" : perifericos);
        } catch (Exception e) {
            response.put("Perifericos", "El microservicio Perifericos no se encuentra disponible");
        }


        return response;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}

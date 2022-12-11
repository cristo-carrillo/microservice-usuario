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

    public List<Rol> getRoles(int usuarioId){
        List<Rol> roles = restTemplate.getForObject("http://localhost:8002/rol/usuario/" + usuarioId, List.class);
        return roles;
    }

    public List<Periferico> getPerifericos(int usuarioId){
        List<Periferico> perifericos = restTemplate.getForObject("http://localhost:8003/periferico/usuario/" + usuarioId, List.class);
        return perifericos;
    }

    public Rol saveRol(int usuarioId, Rol rol){
        rol.setUsuarioId(usuarioId);
        Rol nuevoRol = rolFeignCliente.save(rol);
        return nuevoRol;
    }

    public Periferico savePeriferico(int usuarioId, Periferico periferico){
        periferico.setUsuarioId(usuarioId);
        Periferico nuevoPeriferico = perifericoFeignCliente.save(periferico);
        return nuevoPeriferico;
    }

    public Map<String, Object> getUsuarioAndArea(int usuarioId){
        Map<String,Object> response = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null){
            response.put("msj","El usuario no se encuentra registrado");
            return response;
        }
        response.put("Usuario",usuario);
        List<Rol> roles = rolFeignCliente.getRoles(usuarioId);

        response.put("Roles",roles==null ? "El usuario no tiene rol" : roles);

        List<Periferico> perifericos = perifericoFeignCliente.getPerifericos(usuarioId);

        response.put("Perifericos",perifericos==null ? "El usuario no tiene perifericos" : perifericos);

        return response;
    }

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public   Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario save(Usuario usuario){
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return nuevoUsuario;
    }
}

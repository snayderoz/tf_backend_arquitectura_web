package pe.edu.upc.tf_arquitectura_web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.tf_arquitectura_web.dtos.UsuariosDTO;
import pe.edu.upc.tf_arquitectura_web.entities.Usuarios;
import pe.edu.upc.tf_arquitectura_web.serviceinterfaces.IUsuariosService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    private IUsuariosService pS;

    @PostMapping
    public void registrar(@RequestBody UsuariosDTO dto) {
        ModelMapper m = new ModelMapper();
        Usuarios p = m.map(dto, Usuarios.class);
        pS.insert(p);

    }
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<UsuariosDTO>listar(){
        return pS.list().stream().map(x->{
            ModelMapper m = new ModelMapper();
            return m.map(x,UsuariosDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id")Integer id){pS.delete(id);}

    @GetMapping("/{id}")
    public UsuariosDTO listId(@PathVariable("id") Integer id){
        ModelMapper m = new ModelMapper();
        UsuariosDTO dto = m.map(pS.listId(id), UsuariosDTO.class);
        return dto;
    }

}

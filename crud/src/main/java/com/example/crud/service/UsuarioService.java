package com.example.crud.service;

import com.example.crud.Model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //fazer metodo da service, para esse metodo 'listar' que esta na classe UsuarioController
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
}

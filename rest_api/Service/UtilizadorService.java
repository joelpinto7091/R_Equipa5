package com.upt.lp.rest_api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upt.lp.rest_api.Model.Utilizador;
import com.upt.lp.rest_api.Repository.UtilizadorRepository;

import java.util.*;

@Service
public class UtilizadorService {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    public Optional<Utilizador> procurarPorEmail(String email) {
        return utilizadorRepository.findByEmail(email);
    }

    public Optional<Utilizador> validarCredenciais(String email, String password) {
        Optional<Utilizador> utilizador = utilizadorRepository.findByEmail(email);
        if (utilizador.isPresent() && utilizador.get().getPassword().equals(password)) {
            return utilizador;
        } else {
            return Optional.empty();
        }
    }

    public List<Utilizador> selecionarTodos() {
        return utilizadorRepository.findAll();
    }

    public Optional<Utilizador> procurarPorId(Long id) {
        return utilizadorRepository.findById(id);
    }


    public Utilizador guardar(Utilizador utilizador) {
        return utilizadorRepository.save(utilizador);
    }

    public void eliminar(Long id) {
        utilizadorRepository.deleteById(id);
    }
}

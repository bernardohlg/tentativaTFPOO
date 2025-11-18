package com.bgarcia;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Cliente {
    protected final String cpf;
    protected final String nome;
    protected final String celular;
    protected final Set<String> placas = new LinkedHashSet<>();

    public Cliente(String cpf, String nome, String celular) {
        this.cpf = cpf;
        this.nome = nome;
        this.celular = celular;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getCelular() { return celular; }

    public Set<String> getPlacas() { return Collections.unmodifiableSet(placas); }

    public boolean cadastraVeiculo(String placa) {
        return placas.add(placa);
    }

    public boolean removeVeiculo(String placa) { return placas.remove(placa); }

    public boolean possuiPlaca(String placa) { return placas.contains(placa); }

    public boolean temVeiculoNoEstacionamento(java.util.Map<String, java.time.LocalDateTime> entradas) {
        for (String p : placas) {
            if (entradas.containsKey(p)) return true;
        }
        return false;
    }

    public abstract double calculaCusto(LocalDateTime entrada, LocalDateTime saida);

    public abstract String getTipo();
}

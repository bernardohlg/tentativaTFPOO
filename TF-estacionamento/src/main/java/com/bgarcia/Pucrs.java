package com.bgarcia;

import java.time.LocalDateTime;

public class Pucrs extends Cliente {
    private static final int MAX_PLACAS = 2;

    public Pucrs(String cpf, String nome, String celular) {
        super(cpf, nome, celular);
    }

    @Override
    public boolean cadastraVeiculo(String placa) {
        if (placas.size() >= MAX_PLACAS) return false;
        return super.cadastraVeiculo(placa);
    }

    @Override
    public double calculaCusto(LocalDateTime entrada, LocalDateTime saida) {
        return 0.0; // funcionários não pagam
    }

    @Override
    public String getTipo() { return "Pucrs"; }
}

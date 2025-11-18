package com.bgarcia;

import java.time.Duration;
import java.time.LocalDateTime;

public class Tecnopuc extends Cliente {
    private double taxaPorHora = 1.25; // atualizado conforme solicitação

    public Tecnopuc(String cpf, String nome, String celular, double taxaInicial) {
        super(cpf, nome, celular);
        this.taxaPorHora = taxaInicial;
    }

    public Tecnopuc(String cpf, String nome, String celular) {
        super(cpf, nome, celular);
    }

    @Override
    public double calculaCusto(LocalDateTime entrada, LocalDateTime saida) {
        long minutos = Duration.between(entrada, saida).toMinutes();
        if (minutos < 15) return 0.0;
        double horas = Math.ceil(minutos / 60.0);
        return horas * taxaPorHora;
    }

    @Override
    public String getTipo() { return "Tecnopuc"; }
}

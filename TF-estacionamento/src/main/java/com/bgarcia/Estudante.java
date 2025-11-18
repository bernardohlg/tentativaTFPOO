package com.bgarcia;

import java.time.Duration;
import java.time.LocalDateTime;

public class Estudante extends Cliente {
    private int saldo; 
    private static final int MAX_PLACAS = 2;
    public static final double TARIFA_POR_ENTRADA = 10.0;

    public Estudante(String cpf, String nome, String celular, int saldo) {
        super(cpf, nome, celular);
        this.saldo = saldo;
    }

    @Override
    public boolean cadastraVeiculo(String placa) {
        if (placas.size() >= MAX_PLACAS) return false;
        return super.cadastraVeiculo(placa);
    }

    @Override
    public double calculaCusto(LocalDateTime entrada, LocalDateTime saida) {
        long minutos = Duration.between(entrada, saida).toMinutes();
        if (minutos < 15) return 0.0;
        return TARIFA_POR_ENTRADA;
    }

    public int getSaldo() { return saldo; }

    public void creditar(int valor) { this.saldo += valor; }

    public void debitar(int valor) { this.saldo -= valor; }

    @Override
    public String getTipo() { return "Estudante"; }
}

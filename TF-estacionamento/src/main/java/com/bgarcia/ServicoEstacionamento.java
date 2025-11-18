package com.bgarcia;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ServicoEstacionamento {
    private final int capacidade;
    private final Map<String, LocalDateTime> entradas = new HashMap<>(); // placa -> hora entrada
    private final CadastroClientes cadastro;

    public ServicoEstacionamento(int capacidade, CadastroClientes cadastro) {
        this.capacidade = capacidade;
        this.cadastro = cadastro;
    }

    public boolean estaLotado() { return entradas.size() >= capacidade; }

    public boolean entradaPlaca(String placa, LocalDateTime horario) {
        if (estaLotado()) {
            System.out.println("Estacionamento lotado. Entrada negada para " + placa);
            return false;
        }
        Cliente cliente = cadastro.buscaPorPlaca(placa);
        if (cliente == null) {
            System.out.println("Placa não cadastrada: " + placa + " - entrada não permitida.");
            return false;
        }
        // regra: cada cliente pode estacionar apenas um carro ao mesmo tempo quando aplicável
        if (cliente.temVeiculoNoEstacionamento(entradas)) {
            if (cliente instanceof Pucrs || cliente instanceof Estudante) {
                System.out.println("Cliente já possui um veículo no estacionamento: " + cliente.getNome());
                return false;
            }
        }

        if (cliente instanceof Estudante) {
            Estudante e = (Estudante) cliente;
            if (e.getSaldo() < Estudante.TARIFA_POR_ENTRADA) {
                System.out.println("Estudante com saldo insuficiente. Entrada negada: " + e.getNome());
                return false;
            }
        }

        entradas.put(placa, horario);
        System.out.println("Entrada registrada: " + placa + " em " + horario.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return true;
    }

    public double saidaPlaca(String placa, LocalDateTime horarioSaida) {
        LocalDateTime horarioEntrada = entradas.remove(placa);
        if (horarioEntrada == null) {
            System.out.println("Saída solicitada para placa não presente: " + placa);
            return 0.0;
        }
        Cliente cliente = cadastro.buscaPorPlaca(placa);
        double custo = 0.0;
        if (cliente != null) {
            long minutos = Duration.between(horarioEntrada, horarioSaida).toMinutes();
            if (minutos < 15) {
                custo = 0.0;
            } else {
                custo = cliente.calculaCusto(horarioEntrada, horarioSaida);
                if (cliente instanceof Estudante) {
                    Estudante est = (Estudante) cliente;
                    est.debitar((int) Math.round(custo));
                }
            }
        }
        System.out.println("Saída registrada: " + placa + " Entrada: " + horarioEntrada + " Saída: " + horarioSaida + " Custo: R$ " + String.format("%.2f", custo));
        return custo;
    }

    public Map<String, LocalDateTime> getEntradasAtuais() { return new HashMap<>(entradas); }
}

package com.bgarcia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private static final DateTimeFormatter ENTRADA_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void carregaClientesCsv(String nomeArquivo, CadastroClientes cadastro) {
        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            for (String linha : linhas) {
                if (linha == null || linha.trim().isEmpty()) continue;
                String[] partes = linha.split(",");
                int tipoIndex = -1;
                for (int i = 0; i < partes.length; i++) {
                    String t = partes[i].trim();
                    if (t.equalsIgnoreCase("Estudante") || t.equalsIgnoreCase("Tecnopuc") || t.equalsIgnoreCase("Pucrs")) { tipoIndex = i; break; }
                }
                if (tipoIndex == -1) throw new IllegalArgumentException("Tipo de cliente não encontrado na linha: " + linha);
                String cpf = partes[0].trim();
                String nome = partes[1].trim();
                String celular = partes[2].trim();
                String tipo = partes[tipoIndex].trim();
                int credito = 0;
                // se houver um campo numérico imediatamente antes do tipo, interpretamos como saldo/débito
                if (tipoIndex - 1 >= 3) {
                    String possivelNumero = partes[tipoIndex - 1].trim();
                    try { credito = Integer.parseInt(possivelNumero); } catch (NumberFormatException ignored) { }
                }

                Cliente c;
                if (tipo.equalsIgnoreCase("Estudante")) {
                    c = new Estudante(cpf, nome, celular, credito);
                } else if (tipo.equalsIgnoreCase("Tecnopuc")) {
                    c = new Tecnopuc(cpf, nome, celular, 1.25);
                } else { // Pucrs
                    c = new Pucrs(cpf, nome, celular);
                }

                for (int i = tipoIndex + 1; i < partes.length; i++) {
                    String placa = partes[i].trim();
                    if (!placa.isEmpty()) c.cadastraVeiculo(placa);
                }

                cadastro.adicionaCliente(c);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException("Erro ao ler arquivo de clientes: " + e.getMessage(), e);
        }
    }

    public static Map<String, LocalDateTime> carregaEntradasCsv(String nomeArquivo) {
        Map<String, LocalDateTime> entradas = new HashMap<>();
        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivo));
            for (String linha : linhas) {
                if (linha == null || linha.trim().isEmpty()) continue;
                String[] partes = linha.split(",");
                String placa = partes[0].trim();
                String data = partes[1].trim();
                String hora = partes[2].trim();
                String dataHora = data + " " + hora;
                LocalDateTime horarioEntrada = LocalDateTime.parse(dataHora, ENTRADA_FORMAT);
                entradas.put(placa, horarioEntrada);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo de entradas: " + e.getMessage(), e);
        }
        return entradas;
    }
}

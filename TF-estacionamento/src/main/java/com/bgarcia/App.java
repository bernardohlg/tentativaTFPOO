package com.bgarcia;

public class App {
    public static void main(String[] args) {
        System.out.println("Carregando dados de exemplo.");
        CadastroClientes cadastro = new CadastroClientes();
        String base = "data";
        try {
            DataLoader.carregaClientesCsv(base + "/clientes.csv", cadastro);
            System.out.println("Clientes carregados: " + cadastro.todos().size());

            java.util.Map<String, java.time.LocalDateTime> entradas = DataLoader.carregaEntradasCsv(base + "/entradas.csv");
            ServicoEstacionamento serv = new ServicoEstacionamento(500, cadastro);

            // processa entradas carregadas
            entradas.forEach((placa, horario) -> serv.entradaPlaca(placa, horario));

            System.out.println("Entradas processadas. Entradas atuais no sistema: " + serv.getEntradasAtuais().size());
        } catch (RuntimeException ex) {
            System.err.println("Erro ao carregar dados: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

package com.bgarcia;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CadastroClientes {
    private final Map<String, Cliente> porCpf = new HashMap<>();
    private final Map<String, Cliente> porPlaca = new HashMap<>();

    public void adicionaCliente(Cliente c) {
        porCpf.put(c.getCpf(), c);
        for (String p : c.getPlacas()) {
            porPlaca.put(p, c);
        }
    }

    public Cliente buscaPorCpf(String cpf) { return porCpf.get(cpf); }

    public Cliente buscaPorPlaca(String placa) { return porPlaca.get(placa); }

    public Collection<Cliente> todos() { return porCpf.values(); }

    public void registraPlaca(Cliente c, String placa) {
        if (c.cadastraVeiculo(placa)) {
            porPlaca.put(placa, c);
        }
    }
}

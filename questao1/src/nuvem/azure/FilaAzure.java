package nuvem.azure;

import nuvem.FilaMensagens;

// Abstract Factory - PRODUTO CONCRETO (fila de mensagens Azure)
class FilaAzure implements FilaMensagens {

    static final double CUSTO_MENSAL = 0.50;

    private static int contador = 0;

    private String id;

    FilaAzure() {
        id = "sb-" + proximoNumero();
    }

    private static synchronized int proximoNumero() {
        contador++;
        return contador;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getCustoMensal() {
        return CUSTO_MENSAL;
    }
}

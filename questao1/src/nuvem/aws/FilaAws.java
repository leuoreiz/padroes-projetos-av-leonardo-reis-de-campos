package nuvem.aws;

import nuvem.FilaMensagens;

// Abstract Factory - PRODUTO CONCRETO (fila de mensagens Aws)
class FilaAws implements FilaMensagens {

    static final double CUSTO_MENSAL = 0.40;

    private static int contador = 0;

    private String id;

    FilaAws() {
        id = "sqs-" + proximoNumero();
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

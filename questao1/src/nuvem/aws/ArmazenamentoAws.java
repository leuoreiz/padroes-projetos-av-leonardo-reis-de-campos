package nuvem.aws;

import nuvem.Armazenamento;

// Abstract Factory - PRODUTO CONCRETO (armazenamento Aws)
class ArmazenamentoAws implements Armazenamento {

    static final double CUSTO_POR_GB = 0.023;

    private static int contador = 0;

    private String id;
    private int gb;

    ArmazenamentoAws(int gb) {
        this.id = "s3://" + proximoNumero();
        this.gb = gb;
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
    public int getGb() {
        return gb;
    }

    @Override
    public double getCustoPorGb() {
        return CUSTO_POR_GB;
    }
}

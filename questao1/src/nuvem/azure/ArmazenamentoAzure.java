package nuvem.azure;

import nuvem.Armazenamento;

// Abstract Factory - PRODUTO CONCRETO (armazenamento Azure)
class ArmazenamentoAzure implements Armazenamento {

    static final double CUSTO_POR_GB = 0.02;

    private static int contador = 0;

    private String id;
    private int gb;

    ArmazenamentoAzure(int gb) {
        this.id = "blob://" + proximoNumero();
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

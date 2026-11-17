package nuvem.azure;

import nuvem.MaquinaVirtual;

// Abstract Factory - PRODUTO CONCRETO (máquina virtual Azure)
class MaquinaVirtualAzure implements MaquinaVirtual {

    static final double CUSTO_POR_HORA = 0.12;

    private static int contador = 0;

    private String id;

    MaquinaVirtualAzure() {
        id = "vm-" + proximoNumero();
    }

    // synchronized: duas threads ao mesmo tempo não recebem o mesmo número (RF03)
    private static synchronized int proximoNumero() {
        contador++;
        return contador;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getCustoPorHora() {
        return CUSTO_POR_HORA;
    }
}

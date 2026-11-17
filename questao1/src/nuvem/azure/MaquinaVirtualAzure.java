package nuvem.azure;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import nuvem.MaquinaVirtual;

/**
 * Padrão Abstract Factory - papel: PRODUTO CONCRETO (máquina virtual Azure).
 * Classe e construtor package-private: só a FabricaAzure instancia (RNF06).
 */
final class MaquinaVirtualAzure implements MaquinaVirtual {

    static final BigDecimal CUSTO_POR_HORA = new BigDecimal("0.12");

    /** Sequência por tipo de recurso (RF03); atômica porque há provisionamentos simultâneos. */
    private static final AtomicInteger SEQUENCIA = new AtomicInteger();

    private final String id;

    MaquinaVirtualAzure() {
        this.id = "vm-" + SEQUENCIA.incrementAndGet();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public BigDecimal getCustoPorHora() {
        return CUSTO_POR_HORA;
    }
}

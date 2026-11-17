package nuvem.aws;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import nuvem.MaquinaVirtual;

/**
 * Padrão Abstract Factory - papel: PRODUTO CONCRETO (máquina virtual Aws).
 * Classe e construtor package-private: só a FabricaAws instancia (RNF06).
 */
final class MaquinaVirtualAws implements MaquinaVirtual {

    static final BigDecimal CUSTO_POR_HORA = new BigDecimal("0.10");

    /** Sequência por tipo de recurso (RF03); atômica porque há provisionamentos simultâneos. */
    private static final AtomicInteger SEQUENCIA = new AtomicInteger();

    private final String id;

    MaquinaVirtualAws() {
        this.id = "i-" + SEQUENCIA.incrementAndGet();
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

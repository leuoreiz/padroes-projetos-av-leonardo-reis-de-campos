package nuvem.azure;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import nuvem.Armazenamento;

/**
 * Padrão Abstract Factory - papel: PRODUTO CONCRETO (armazenamento Azure).
 * Classe e construtor package-private: só a FabricaAzure instancia (RNF06).
 */
final class ArmazenamentoAzure implements Armazenamento {

    static final BigDecimal CUSTO_POR_GB_MES = new BigDecimal("0.02");

    private static final AtomicInteger SEQUENCIA = new AtomicInteger();

    private final String id;
    private final int gb;

    ArmazenamentoAzure(int gb) {
        this.id = "blob://" + SEQUENCIA.incrementAndGet();
        this.gb = gb;
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
    public BigDecimal getCustoPorGbMes() {
        return CUSTO_POR_GB_MES;
    }
}

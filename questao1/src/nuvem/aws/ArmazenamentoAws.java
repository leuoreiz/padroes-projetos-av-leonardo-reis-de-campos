package nuvem.aws;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import nuvem.Armazenamento;

/**
 * Padrão Abstract Factory - papel: PRODUTO CONCRETO (armazenamento Aws).
 * Classe e construtor package-private: só a FabricaAws instancia (RNF06).
 */
final class ArmazenamentoAws implements Armazenamento {

    static final BigDecimal CUSTO_POR_GB_MES = new BigDecimal("0.023");

    private static final AtomicInteger SEQUENCIA = new AtomicInteger();

    private final String id;
    private final int gb;

    ArmazenamentoAws(int gb) {
        this.id = "s3://" + SEQUENCIA.incrementAndGet();
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

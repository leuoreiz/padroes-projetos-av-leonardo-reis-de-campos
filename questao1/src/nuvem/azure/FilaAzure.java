package nuvem.azure;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import nuvem.FilaMensagens;

/**
 * Padrão Abstract Factory - papel: PRODUTO CONCRETO (fila de mensagens Azure).
 * Classe e construtor package-private: só a FabricaAzure instancia (RNF06).
 */
final class FilaAzure implements FilaMensagens {

    static final BigDecimal CUSTO_FIXO_MENSAL = new BigDecimal("0.50");

    private static final AtomicInteger SEQUENCIA = new AtomicInteger();

    private final String id;

    FilaAzure() {
        this.id = "sb-" + SEQUENCIA.incrementAndGet();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public BigDecimal getCustoFixoMensal() {
        return CUSTO_FIXO_MENSAL;
    }
}

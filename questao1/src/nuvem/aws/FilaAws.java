package nuvem.aws;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import nuvem.FilaMensagens;

/**
 * Padrão Abstract Factory - papel: PRODUTO CONCRETO (fila de mensagens Aws).
 * Classe e construtor package-private: só a FabricaAws instancia (RNF06).
 */
final class FilaAws implements FilaMensagens {

    static final BigDecimal CUSTO_FIXO_MENSAL = new BigDecimal("0.40");

    private static final AtomicInteger SEQUENCIA = new AtomicInteger();

    private final String id;

    FilaAws() {
        this.id = "sqs-" + SEQUENCIA.incrementAndGet();
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

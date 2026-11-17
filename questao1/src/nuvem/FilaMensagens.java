package nuvem;

import java.math.BigDecimal;

/**
 * Padrão Abstract Factory - papel: PRODUTO ABSTRATO (fila de mensagens).
 */
public interface FilaMensagens {

    String getId();

    BigDecimal getCustoFixoMensal();
}

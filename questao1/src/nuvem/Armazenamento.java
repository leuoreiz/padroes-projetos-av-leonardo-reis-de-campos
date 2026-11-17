package nuvem;

import java.math.BigDecimal;

/**
 * Padrão Abstract Factory - papel: PRODUTO ABSTRATO (armazenamento de objetos).
 */
public interface Armazenamento {

    String getId();

    int getGb();

    BigDecimal getCustoPorGbMes();
}

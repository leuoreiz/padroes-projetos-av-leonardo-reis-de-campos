package nuvem;

import java.math.BigDecimal;

/**
 * Padrão Abstract Factory - papel: PRODUTO ABSTRATO (máquina virtual).
 */
public interface MaquinaVirtual {

    String getId();

    BigDecimal getCustoPorHora();
}

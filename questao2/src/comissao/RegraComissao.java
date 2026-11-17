package comissao;

import java.math.BigDecimal;

/**
 * Padrão Strategy - papel: ESTRATÉGIA (Strategy).
 *
 * Regra de comissão cobrada do vendedor sobre o valor de venda (RF06).
 * Cada categoria tem a sua implementação; nenhuma classe usa if/switch sobre a
 * categoria (RNF02).
 */
public interface RegraComissao {

    BigDecimal calcular(BigDecimal valorVenda);

    String getDescricao();
}

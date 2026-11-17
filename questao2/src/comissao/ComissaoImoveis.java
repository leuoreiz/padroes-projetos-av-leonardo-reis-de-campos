package comissao;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Padrão Strategy - papel: ESTRATÉGIA CONCRETA. Imóveis: 3% do valor mais R$ 1.500,00 fixos. */
public class ComissaoImoveis implements RegraComissao {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.03");
    private static final BigDecimal VALOR_FIXO = new BigDecimal("1500.00");

    @Override
    public BigDecimal calcular(BigDecimal valorVenda) {
        return valorVenda.multiply(PERCENTUAL).add(VALOR_FIXO).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Imoveis: 3% do valor + R$ 1.500,00 fixos";
    }
}

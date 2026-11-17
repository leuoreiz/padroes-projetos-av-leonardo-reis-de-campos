package servicos;

import java.math.BigDecimal;
import java.util.List;

/**
 * Padrão Decorator - papel: COMPONENTE (Component).
 *
 * Compra de um lote vendido, com ou sem serviços adicionais. A impressão da
 * fatura usa só esta interface, então trata igualmente uma compra sem serviços
 * e uma com qualquer quantidade deles (RNF04).
 */
public interface Compra {

    String getNomeLote();

    String getComprador();

    BigDecimal getValorVenda();

    /** Serviços contratados, na ordem em que foram adicionados. Cada chamada devolve uma lista nova. */
    List<ItemFatura> getServicos();

    BigDecimal getTotal();
}

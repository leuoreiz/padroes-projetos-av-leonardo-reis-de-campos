package servicos;

import java.math.BigDecimal;
import java.util.List;

/** Padrão Decorator - papel: DECORADOR CONCRETO. Entrega expressa: R$ 450,00 fixos. */
public class EntregaExpressa extends ServicoAdicional {

    private static final BigDecimal CUSTO = new BigDecimal("450.00");

    public EntregaExpressa(Compra compra) {
        super(compra);
    }

    @Override
    public List<ItemFatura> getServicos() {
        List<ItemFatura> servicos = super.getServicos();
        servicos.add(new ItemFatura("Entrega expressa", CUSTO));
        return servicos;
    }

    @Override
    public BigDecimal getTotal() {
        return super.getTotal().add(CUSTO);
    }
}

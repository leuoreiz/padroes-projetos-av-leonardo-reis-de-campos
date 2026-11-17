package servicos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Padrão Decorator - papel: DECORADOR CONCRETO. Seguro de transporte: 1,5% do valor de venda. */
public class SeguroTransporte extends ServicoAdicional {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.015");

    public SeguroTransporte(Compra compra) {
        super(compra);
    }

    @Override
    public List<ItemFatura> getServicos() {
        List<ItemFatura> servicos = super.getServicos();
        servicos.add(new ItemFatura("Seguro de transporte (1,5% do valor de venda)", custo()));
        return servicos;
    }

    @Override
    public BigDecimal getTotal() {
        return super.getTotal().add(custo());
    }

    private BigDecimal custo() {
        return getValorVenda().multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
    }
}

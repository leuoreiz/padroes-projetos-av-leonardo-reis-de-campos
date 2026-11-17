package servicos;

import java.math.BigDecimal;
import java.util.List;

/** Padrão Decorator - papel: DECORADOR CONCRETO. Embalagem especial: R$ 180,00 fixos. */
public class EmbalagemEspecial extends ServicoAdicional {

    private static final BigDecimal CUSTO = new BigDecimal("180.00");

    public EmbalagemEspecial(Compra compra) {
        super(compra);
    }

    @Override
    public List<ItemFatura> getServicos() {
        List<ItemFatura> servicos = super.getServicos();
        servicos.add(new ItemFatura("Embalagem especial", CUSTO));
        return servicos;
    }

    @Override
    public BigDecimal getTotal() {
        return super.getTotal().add(CUSTO);
    }
}

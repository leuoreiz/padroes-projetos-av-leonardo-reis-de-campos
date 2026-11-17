package servicos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Padrão Decorator - papel: DECORADOR ABSTRATO (Decorator).
 *
 * É uma Compra e envolve outra Compra, repassando as chamadas para ela.
 * Os decoradores concretos acrescentam a sua linha e o seu custo. Assim os
 * serviços se combinam em qualquer ordem e quantidade, sem classes de
 * combinações prontas (RNF03).
 */
public abstract class ServicoAdicional implements Compra {

    protected final Compra compra;

    protected ServicoAdicional(Compra compra) {
        this.compra = Objects.requireNonNull(compra, "compra");
    }

    @Override
    public String getNomeLote() {
        return compra.getNomeLote();
    }

    @Override
    public String getComprador() {
        return compra.getComprador();
    }

    @Override
    public BigDecimal getValorVenda() {
        return compra.getValorVenda();
    }

    @Override
    public List<ItemFatura> getServicos() {
        return compra.getServicos();
    }

    @Override
    public BigDecimal getTotal() {
        return compra.getTotal();
    }
}

package servicos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import leilao.Lote;

/**
 * Padrão Decorator - papel: COMPONENTE CONCRETO.
 *
 * A compra "pura" de um lote vendido, sem nenhum serviço adicional.
 * Só pode ser criada para um lote vendido (Lote.getValorVenda() falha caso contrário).
 */
public class CompraLote implements Compra {

    private final String nomeLote;
    private final String comprador;
    private final BigDecimal valorVenda;

    public CompraLote(Lote lote) {
        this.valorVenda = lote.getValorVenda();
        this.comprador = lote.getComprador().getNome();
        this.nomeLote = lote.getNome();
    }

    @Override
    public String getNomeLote() {
        return nomeLote;
    }

    @Override
    public String getComprador() {
        return comprador;
    }

    @Override
    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    @Override
    public List<ItemFatura> getServicos() {
        return new ArrayList<>();
    }

    @Override
    public BigDecimal getTotal() {
        return valorVenda;
    }
}

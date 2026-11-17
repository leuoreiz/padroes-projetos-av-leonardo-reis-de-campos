package servicos;

import leilao.Lote;

// Decorator - COMPONENTE CONCRETO: a compra do lote, sem nenhum serviço
public class CompraLote implements Compra {

    private Lote lote;

    public CompraLote(Lote lote) {
        this.lote = lote;
    }

    @Override
    public String getDescricao() {
        return "Lote: " + lote.getNome() + "\n"
                + "Comprador: " + lote.getComprador().getNome() + "\n"
                + String.format("Valor de venda: R$ %.2f", lote.getValorVenda());
    }

    @Override
    public double getValorVenda() {
        return lote.getValorVenda();
    }

    @Override
    public double getTotal() {
        return lote.getValorVenda();
    }
}

package servicos;

// Decorator - DECORADOR CONCRETO: entrega expressa, R$ 450,00
public class EntregaExpressa extends ServicoAdicional {

    public EntregaExpressa(Compra compra) {
        super(compra);
    }

    @Override
    public String getDescricao() {
        return compra.getDescricao() + "\n  + Entrega expressa: R$ 450,00";
    }

    @Override
    public double getTotal() {
        return compra.getTotal() + 450.00;
    }
}

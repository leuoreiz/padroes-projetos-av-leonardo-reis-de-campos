package servicos;

// Decorator - DECORADOR CONCRETO: seguro de transporte, 1,5% do valor de venda
public class SeguroTransporte extends ServicoAdicional {

    public SeguroTransporte(Compra compra) {
        super(compra);
    }

    private double custo() {
        return getValorVenda() * 0.015;
    }

    @Override
    public String getDescricao() {
        return compra.getDescricao() + String.format("\n  + Seguro de transporte (1,5%%): R$ %.2f", custo());
    }

    @Override
    public double getTotal() {
        return compra.getTotal() + custo();
    }
}

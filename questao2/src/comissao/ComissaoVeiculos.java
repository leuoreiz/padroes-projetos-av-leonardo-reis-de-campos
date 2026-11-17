package comissao;

// Strategy - ESTRATÉGIA CONCRETA: Veículos, 8% do valor, limitada a R$ 5.000,00
public class ComissaoVeiculos implements RegraComissao {

    @Override
    public double calcular(double valorVenda) {
        return Math.min(valorVenda * 0.08, 5000.00);
    }

    @Override
    public String getDescricao() {
        return "Veiculos (8%, limitada a R$ 5000,00)";
    }
}

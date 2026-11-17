package comissao;

// Strategy - ESTRATÉGIA CONCRETA: Eletrônicos, 12% do valor, com mínimo de R$ 50,00
public class ComissaoEletronicos implements RegraComissao {

    @Override
    public double calcular(double valorVenda) {
        return Math.max(valorVenda * 0.12, 50.00);
    }

    @Override
    public String getDescricao() {
        return "Eletronicos (12%, minimo R$ 50,00)";
    }
}

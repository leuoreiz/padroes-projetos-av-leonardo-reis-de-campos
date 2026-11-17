package comissao;

// Strategy - ESTRATÉGIA: regra de comissão cobrada do vendedor (RF06).
// Cada categoria tem a sua classe, sem if/switch sobre a categoria (RNF02).
public interface RegraComissao {

    double calcular(double valorVenda);

    String getDescricao();
}

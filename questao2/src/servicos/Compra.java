package servicos;

// Decorator - COMPONENTE: a compra de um lote, com ou sem serviços adicionais.
// A fatura só usa esta interface, então trata igual uma compra sem serviços
// e uma com qualquer quantidade deles (RNF04).
public interface Compra {

    String getDescricao();

    double getValorVenda();

    double getTotal();
}

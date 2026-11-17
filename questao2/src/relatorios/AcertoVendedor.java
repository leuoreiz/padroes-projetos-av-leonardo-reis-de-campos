package relatorios;

import leilao.Lote;

// RF10: imprime o acerto do vendedor (valor de venda, comissão e valor líquido)
public class AcertoVendedor {

    public void imprimir(Lote lote) {
        double valorVenda = lote.getValorVenda();
        double comissao = lote.getComissao();

        System.out.println("========== ACERTO DO VENDEDOR ==========");
        System.out.println("Lote: " + lote.getNome() + " (" + lote.getCategoria().getNome() + ")");
        System.out.println("Regra de comissao: " + lote.getRegraComissao().getDescricao());
        System.out.println(String.format("Valor de venda: R$ %.2f", valorVenda));
        System.out.println(String.format("Comissao cobrada: R$ %.2f", comissao));
        System.out.println(String.format("Valor liquido a repassar: R$ %.2f", valorVenda - comissao));
        System.out.println("========================================");
    }
}

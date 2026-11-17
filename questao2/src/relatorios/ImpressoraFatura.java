package relatorios;

import servicos.Compra;
import servicos.ItemFatura;
import util.Moeda;

/**
 * RF09: imprime a fatura do comprador.
 *
 * Depende apenas do Componente (Compra) do Decorator: não sabe nem verifica quais
 * serviços foram contratados, e trata igualmente uma compra sem serviços e uma
 * com vários (RNF04).
 */
public class ImpressoraFatura {

    public void imprimir(Compra compra) {
        String quebra = System.lineSeparator();
        StringBuilder fatura = new StringBuilder();
        fatura.append("================ FATURA DO COMPRADOR ================").append(quebra)
                .append(linha("Lote", compra.getNomeLote())).append(quebra)
                .append(linha("Comprador", compra.getComprador())).append(quebra)
                .append(linha("Valor de venda", Moeda.formatar(compra.getValorVenda()))).append(quebra)
                .append("Servicos adicionais:").append(quebra);
        for (ItemFatura item : compra.getServicos()) {
            fatura.append(String.format("  + %-46s %15s", item.getDescricao(), Moeda.formatar(item.getCusto())))
                    .append(quebra);
        }
        fatura.append("-----------------------------------------------------").append(quebra)
                .append(linha("TOTAL A PAGAR", Moeda.formatar(compra.getTotal()))).append(quebra)
                .append("=====================================================");
        System.out.println(fatura);
    }

    private static String linha(String rotulo, String valor) {
        return String.format("%-16s: %s", rotulo, valor);
    }
}

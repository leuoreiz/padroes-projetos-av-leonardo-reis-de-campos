package relatorios;

import java.math.BigDecimal;

import leilao.Lote;
import util.Moeda;

/**
 * RF10: imprime o acerto do vendedor (valor de venda, comissão cobrada e valor
 * líquido a repassar). A comissão vem do Lote, calculada no encerramento pela
 * estratégia vigente; este relatório não conhece nenhuma regra concreta.
 */
public class AcertoVendedor {

    public void imprimir(Lote lote) {
        BigDecimal valorVenda = lote.getValorVenda();
        BigDecimal comissao = lote.getComissaoCobrada();
        BigDecimal liquido = valorVenda.subtract(comissao);

        String quebra = System.lineSeparator();
        String acerto = "================ ACERTO DO VENDEDOR ================" + quebra
                + linha("Lote", lote.getNome() + " (" + lote.getCategoria().getNome() + ")") + quebra
                + linha("Regra aplicada", lote.getRegraComissao().getDescricao()) + quebra
                + linha("Valor de venda", Moeda.formatar(valorVenda)) + quebra
                + linha("Comissao cobrada", "- " + Moeda.formatar(comissao)) + quebra
                + "----------------------------------------------------" + quebra
                + linha("Liquido a repassar", Moeda.formatar(liquido)) + quebra
                + "====================================================";
        System.out.println(acerto);
    }

    private static String linha(String rotulo, String valor) {
        return String.format("%-18s: %s", rotulo, valor);
    }
}

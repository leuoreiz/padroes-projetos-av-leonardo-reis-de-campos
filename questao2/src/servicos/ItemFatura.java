package servicos;

import java.math.BigDecimal;

/** Linha da fatura: descrição do serviço e o seu custo (RF08). */
public final class ItemFatura {

    private final String descricao;
    private final BigDecimal custo;

    public ItemFatura(String descricao, BigDecimal custo) {
        this.descricao = descricao;
        this.custo = custo;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getCusto() {
        return custo;
    }
}

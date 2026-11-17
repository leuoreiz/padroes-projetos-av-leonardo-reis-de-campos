package leilao;

import comissao.RegraComissao;

/**
 * Categoria de um lote e a regra de comissão que ela usa por padrão.
 *
 * A categoria não decide nada com if/switch: ela apenas carrega a sua estratégia.
 * Uma categoria nova é só uma nova RegraComissao + new Categoria(...) (RNF02).
 */
public final class Categoria {

    private final String nome;
    private final RegraComissao regraPadrao;

    public Categoria(String nome, RegraComissao regraPadrao) {
        this.nome = nome;
        this.regraPadrao = regraPadrao;
    }

    public String getNome() {
        return nome;
    }

    public RegraComissao getRegraPadrao() {
        return regraPadrao;
    }
}

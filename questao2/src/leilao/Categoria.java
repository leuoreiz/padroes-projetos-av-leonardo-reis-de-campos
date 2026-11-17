package leilao;

import comissao.RegraComissao;

// Categoria do lote e a regra de comissão que ela usa por padrão.
// Não tem if/switch: cada categoria já vem com a sua estratégia (RNF02).
public class Categoria {

    private String nome;
    private RegraComissao regraPadrao;

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

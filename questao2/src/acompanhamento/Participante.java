package acompanhamento;

import java.math.BigDecimal;

import leilao.Lance;
import leilao.Licitante;
import leilao.Lote;
import util.Moeda;

/**
 * Padrão Observer - papel: OBSERVADOR CONCRETO (participante do leilão).
 *
 * Também é Licitante: pode dar lances. Ao ser notificado, exibe o nome do lote
 * e o novo valor (RF04).
 */
public class Participante implements ObservadorLance, Licitante {

    private final String nome;

    public Participante(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void acompanhar(Lote lote) {
        lote.adicionarObservador(this);
        System.out.println("[ACOMPANHAMENTO] " + nome + " passou a acompanhar o lote '" + lote.getNome() + "'.");
    }

    public void deixarDeAcompanhar(Lote lote) {
        lote.removerObservador(this);
        System.out.println("[ACOMPANHAMENTO] " + nome + " deixou de acompanhar o lote '" + lote.getNome() + "'.");
    }

    public boolean darLance(Lote lote, BigDecimal valor) {
        return lote.receberLance(this, valor);
    }

    @Override
    public void atualizar(Lote lote, Lance lance) {
        System.out.println("    [" + nome + "] Novo lance no lote '" + lote.getNome() + "': "
                + Moeda.formatar(lance.getValor()) + " (de " + lance.getLicitante().getNome() + ")");
    }
}

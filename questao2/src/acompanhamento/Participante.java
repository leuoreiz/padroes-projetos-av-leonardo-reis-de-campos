package acompanhamento;

import leilao.Lance;
import leilao.Licitante;
import leilao.Lote;

// Observer - OBSERVADOR CONCRETO: participante do leilão (também dá lances)
public class Participante implements ObservadorLance, Licitante {

    private String nome;

    public Participante(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void acompanhar(Lote lote) {
        lote.adicionarObservador(this);
        System.out.println(nome + " passou a acompanhar o lote " + lote.getNome());
    }

    public void deixarDeAcompanhar(Lote lote) {
        lote.removerObservador(this);
        System.out.println(nome + " deixou de acompanhar o lote " + lote.getNome());
    }

    public void darLance(Lote lote, double valor) {
        lote.receberLance(this, valor);
    }

    // RF04: mostra o nome do lote e o novo valor
    @Override
    public void atualizar(Lote lote, Lance lance) {
        System.out.println(String.format("   [%s] Novo lance no lote %s: R$ %.2f",
                nome, lote.getNome(), lance.getValor()));
    }
}

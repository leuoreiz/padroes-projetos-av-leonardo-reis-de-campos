package acompanhamento;

import leilao.Lance;
import leilao.Lote;

// Observer - OBSERVADOR CONCRETO: painel do leiloeiro.
// RF04: mostra sempre o maior lance e o total de lances recebidos.
public class PainelLeiloeiro implements ObservadorLance {

    @Override
    public void atualizar(Lote lote, Lance lance) {
        System.out.println(String.format("   [Painel] Lote %s | maior lance: R$ %.2f | total de lances: %d",
                lote.getNome(), lote.getLanceAtual(), lote.getTotalLances()));
    }
}

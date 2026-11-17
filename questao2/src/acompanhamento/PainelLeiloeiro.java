package acompanhamento;

import leilao.Lance;
import leilao.Lote;
import util.Moeda;

/**
 * Padrão Observer - papel: OBSERVADOR CONCRETO (painel do leiloeiro).
 *
 * Exibe sempre o maior lance e o total de lances recebidos pelo lote (RF04).
 */
public class PainelLeiloeiro implements ObservadorLance {

    @Override
    public void atualizar(Lote lote, Lance lance) {
        Lance maior = lote.getMaiorLance();
        System.out.println("    [Painel do leiloeiro] '" + lote.getNome() + "' | maior lance: "
                + Moeda.formatar(maior.getValor()) + " (" + maior.getLicitante().getNome() + ")"
                + " | total de lances: " + lote.getTotalLances());
    }
}

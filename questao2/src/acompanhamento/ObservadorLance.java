package acompanhamento;

import leilao.Lance;
import leilao.Lote;

/**
 * Padrão Observer - papel: OBSERVADOR (Observer).
 *
 * Todo interessado nos lances de um lote implementa esta interface. Um novo tipo
 * de interessado é apenas uma nova classe que a implementa, sem mexer no Lote (RNF01).
 */
public interface ObservadorLance {

    void atualizar(Lote lote, Lance lance);
}

package acompanhamento;

import leilao.Lance;
import leilao.Lote;

// Observer - OBSERVADOR: todo interessado nos lances implementa esta interface.
// Um novo tipo de interessado é só uma nova classe, sem mexer no Lote (RNF01).
public interface ObservadorLance {

    void atualizar(Lote lote, Lance lance);
}

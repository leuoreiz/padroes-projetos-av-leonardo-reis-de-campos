package acompanhamento;

import leilao.Lance;

// Observer - SUJEITO: o que pode ser acompanhado
public interface Sujeito {

    void adicionarObservador(ObservadorLance observador);

    void removerObservador(ObservadorLance observador);

    void notificarObservadores(Lance lance);
}

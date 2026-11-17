package acompanhamento;

import leilao.Lance;

/**
 * Padrão Observer - papel: SUJEITO (Subject).
 *
 * Contrato de quem pode ser acompanhado: registrar, remover e notificar observadores.
 */
public interface Sujeito {

    void adicionarObservador(ObservadorLance observador);

    void removerObservador(ObservadorLance observador);

    void notificarObservadores(Lance lance);
}

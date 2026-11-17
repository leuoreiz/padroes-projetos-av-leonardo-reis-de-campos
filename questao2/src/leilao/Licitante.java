package leilao;

/**
 * Quem pode dar lances em um lote.
 *
 * O Lote conhece apenas esta abstração, nunca a classe concreta Participante (RNF01).
 */
public interface Licitante {

    String getNome();
}

package leilao;

// Quem dá lances. O Lote só conhece esta interface, nunca a classe Participante (RNF01).
public interface Licitante {

    String getNome();
}

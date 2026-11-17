package leilao;

// Lance aceito: quem deu e o valor (RF02)
public class Lance {

    private Licitante licitante;
    private double valor;

    public Lance(Licitante licitante, double valor) {
        this.licitante = licitante;
        this.valor = valor;
    }

    public Licitante getLicitante() {
        return licitante;
    }

    public double getValor() {
        return valor;
    }
}

package leilao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Lance aceito em um lote: quem deu, o valor e quando (RF02). */
public final class Lance {

    private final Licitante licitante;
    private final BigDecimal valor;
    private final LocalDateTime dataHora;

    Lance(Licitante licitante, BigDecimal valor, LocalDateTime dataHora) {
        this.licitante = licitante;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Licitante getLicitante() {
        return licitante;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}

package infra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Registro imutável de um provisionamento, aceito ou recusado (RF07). */
public final class RegistroAuditoria {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    private final int sequencia;
    private final LocalDateTime dataHora;
    private final String nomeAmbiente;
    private final String provedor;
    private final ResultadoProvisionamento resultado;
    private final String motivo;

    RegistroAuditoria(int sequencia, LocalDateTime dataHora, String nomeAmbiente, String provedor,
                      ResultadoProvisionamento resultado, String motivo) {
        this.sequencia = sequencia;
        this.dataHora = dataHora;
        this.nomeAmbiente = nomeAmbiente;
        this.provedor = provedor;
        this.resultado = resultado;
        this.motivo = motivo;
    }

    public int getSequencia() {
        return sequencia;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getNomeAmbiente() {
        return nomeAmbiente;
    }

    public String getProvedor() {
        return provedor;
    }

    public ResultadoProvisionamento getResultado() {
        return resultado;
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public String toString() {
        String texto = "#" + sequencia + " | " + FORMATO.format(dataHora) + " | " + nomeAmbiente
                + " | " + provedor + " | " + resultado;
        return motivo == null ? texto : texto + " (motivo: " + motivo + ")";
    }
}

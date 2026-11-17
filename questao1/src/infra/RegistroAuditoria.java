package infra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Um registro de auditoria: data e hora, ambiente, provedor e resultado (RF07)
public class RegistroAuditoria {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private LocalDateTime dataHora;
    private String ambiente;
    private String provedor;
    private String resultado;

    public RegistroAuditoria(LocalDateTime dataHora, String ambiente, String provedor, String resultado) {
        this.dataHora = dataHora;
        this.ambiente = ambiente;
        this.provedor = provedor;
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return dataHora.format(FORMATO) + " | " + ambiente + " | " + provedor + " | " + resultado;
    }
}

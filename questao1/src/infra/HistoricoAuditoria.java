package infra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Padrão Singleton - papel: SINGLETON (histórico de auditoria único).
 *
 * RNF05: existe uma única instância no programa, acessível por qualquer classe
 * via {@link #getInstancia()}, sem ser passada por parâmetro.
 *
 * Thread-safety: a criação usa double-checked locking com volatile e a lista de
 * registros só é acessada dentro de métodos synchronized. Data/hora e número
 * sequencial são gerados dentro do mesmo bloqueio da inclusão, então registros
 * simultâneos nunca se sobrescrevem e o histórico sai na ordem em que foi feito.
 */
public final class HistoricoAuditoria {

    private static volatile HistoricoAuditoria instancia;

    private final List<RegistroAuditoria> registros = new ArrayList<>();

    private HistoricoAuditoria() {
    }

    public static HistoricoAuditoria getInstancia() {
        HistoricoAuditoria local = instancia;
        if (local == null) {
            synchronized (HistoricoAuditoria.class) {
                local = instancia;
                if (local == null) {
                    local = new HistoricoAuditoria();
                    instancia = local;
                }
            }
        }
        return local;
    }

    public synchronized RegistroAuditoria registrar(String nomeAmbiente, String provedor,
                                                    ResultadoProvisionamento resultado, String motivo) {
        RegistroAuditoria registro = new RegistroAuditoria(registros.size() + 1, LocalDateTime.now(),
                nomeAmbiente, provedor, resultado, motivo);
        registros.add(registro);
        return registro;
    }

    /** Cópia imutável, para que ninguém altere o histórico por fora. */
    public synchronized List<RegistroAuditoria> getRegistros() {
        return Collections.unmodifiableList(new ArrayList<>(registros));
    }

    public void imprimir() {
        List<RegistroAuditoria> copia = getRegistros();
        StringBuilder saida = new StringBuilder();
        saida.append(System.lineSeparator())
                .append("========== HISTORICO DE AUDITORIA (").append(copia.size()).append(" registros) ==========")
                .append(System.lineSeparator());
        for (RegistroAuditoria registro : copia) {
            saida.append(registro).append(System.lineSeparator());
        }
        System.out.print(saida);
    }
}

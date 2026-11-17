package infra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Singleton - histórico de auditoria único (RNF05)
// Qualquer classe acessa com HistoricoAuditoria.getInstancia(), sem parâmetro.
public class HistoricoAuditoria {

    private static HistoricoAuditoria instancia;

    private List<RegistroAuditoria> registros = new ArrayList<>();

    private HistoricoAuditoria() {
    }

    public static synchronized HistoricoAuditoria getInstancia() {
        if (instancia == null) {
            instancia = new HistoricoAuditoria();
        }
        return instancia;
    }

    // synchronized: só uma thread por vez mexe na lista, então nenhum
    // registro se perde nem sobrescreve outro em provisionamentos simultâneos
    public synchronized void registrar(String ambiente, String provedor, String resultado) {
        registros.add(new RegistroAuditoria(LocalDateTime.now(), ambiente, provedor, resultado));
    }

    public synchronized void imprimir() {
        System.out.println();
        System.out.println("===== HISTORICO DE AUDITORIA (" + registros.size() + " registros) =====");
        for (RegistroAuditoria registro : registros) {
            System.out.println(registro);
        }
    }
}

package acompanhamento;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import leilao.Lance;
import leilao.Lote;
import util.Moeda;

/**
 * Padrão Observer - papel: OBSERVADOR CONCRETO (auditor).
 *
 * Registra todos os lances aceitos com data e hora (RF04).
 */
public class AuditorLances implements ObservadorLance {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    private final List<String> registros = new ArrayList<>();

    @Override
    public void atualizar(Lote lote, Lance lance) {
        String registro = FORMATO.format(lance.getDataHora()) + " | " + lote.getNome() + " | "
                + lance.getLicitante().getNome() + " | " + Moeda.formatar(lance.getValor());
        registros.add(registro);
        System.out.println("    [Auditor] Lance registrado: " + registro);
    }

    public List<String> getRegistros() {
        return Collections.unmodifiableList(registros);
    }

    public void imprimirRegistros() {
        System.out.println("========== REGISTRO DO AUDITOR (" + registros.size() + " lances aceitos) ==========");
        for (String registro : registros) {
            System.out.println(registro);
        }
    }
}

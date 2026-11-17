package acompanhamento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import leilao.Lance;
import leilao.Lote;

// Observer - OBSERVADOR CONCRETO: auditor.
// RF04: registra todos os lances aceitos com data e hora.
public class AuditorLances implements ObservadorLance {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private List<String> registros = new ArrayList<>();

    @Override
    public void atualizar(Lote lote, Lance lance) {
        String registro = String.format("%s | %s | %s | R$ %.2f", LocalDateTime.now().format(FORMATO),
                lote.getNome(), lance.getLicitante().getNome(), lance.getValor());
        registros.add(registro);
        System.out.println("   [Auditor] registrou: " + registro);
    }

    public void imprimirRegistros() {
        System.out.println("===== REGISTROS DO AUDITOR (" + registros.size() + " lances) =====");
        for (String registro : registros) {
            System.out.println(registro);
        }
    }
}

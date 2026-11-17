package provisionamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nuvem.Armazenamento;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import util.Moeda;

/**
 * Ambiente provisionado: máquinas virtuais, armazenamento e fila.
 *
 * O construtor é package-private: só o ProvisionadorAmbiente monta um ambiente,
 * e ele sempre usa uma única fábrica, o que garante recursos do mesmo provedor (RNF01).
 */
public final class Ambiente {

    private final String nome;
    private final String provedor;
    private final String regiao;
    private final List<MaquinaVirtual> maquinas;
    private final Armazenamento armazenamento;
    private final FilaMensagens fila;
    private final BigDecimal custoMensalEstimado;

    Ambiente(String nome, String provedor, String regiao, List<MaquinaVirtual> maquinas,
             Armazenamento armazenamento, FilaMensagens fila, BigDecimal custoMensalEstimado) {
        this.nome = nome;
        this.provedor = provedor;
        this.regiao = regiao;
        this.maquinas = Collections.unmodifiableList(new ArrayList<>(maquinas));
        this.armazenamento = armazenamento;
        this.fila = fila;
        this.custoMensalEstimado = custoMensalEstimado;
    }

    public String getNome() {
        return nome;
    }

    public String getProvedor() {
        return provedor;
    }

    public String getRegiao() {
        return regiao;
    }

    public List<MaquinaVirtual> getMaquinas() {
        return maquinas;
    }

    public Armazenamento getArmazenamento() {
        return armazenamento;
    }

    public FilaMensagens getFila() {
        return fila;
    }

    public BigDecimal getCustoMensalEstimado() {
        return custoMensalEstimado;
    }

    /** RF06: nome, provedor, região, identificador de cada recurso e custo mensal estimado. */
    public String resumo() {
        String quebra = System.lineSeparator();
        StringBuilder idsMaquinas = new StringBuilder();
        for (MaquinaVirtual maquina : maquinas) {
            if (idsMaquinas.length() > 0) {
                idsMaquinas.append(", ");
            }
            idsMaquinas.append(maquina.getId());
        }
        return "---------- AMBIENTE PROVISIONADO ----------" + quebra
                + "Ambiente             : " + nome + quebra
                + "Provedor             : " + provedor + quebra
                + "Regiao               : " + regiao + quebra
                + "Maquinas virtuais    : " + idsMaquinas + quebra
                + "Armazenamento        : " + armazenamento.getId() + " (" + armazenamento.getGb() + " GB)" + quebra
                + "Fila de mensagens    : " + fila.getId() + quebra
                + "Custo mensal estimado: " + Moeda.formatar(custoMensalEstimado) + quebra
                + "-------------------------------------------";
    }
}

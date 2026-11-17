package leilao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import acompanhamento.ObservadorLance;
import acompanhamento.Sujeito;
import comissao.RegraComissao;
import util.Moeda;

/**
 * Lote do leilão. Acumula dois papéis, sem que um dependa do outro (RNF05):
 *
 * - Observer: SUJEITO CONCRETO. Guarda a lista de observadores pela interface
 *   ObservadorLance e avisa todos a cada lance aceito, exceto quem deu o lance.
 *   Não conhece Participante, PainelLeiloeiro nem AuditorLances (RNF01).
 *
 * - Strategy: CONTEXTO. Guarda uma RegraComissao, trocável em tempo de execução
 *   antes do encerramento, sem recriar o lote (RNF02 / RF07).
 */
public class Lote implements Sujeito {

    /** RF02: o novo lance precisa ser pelo menos 5% maior que o lance atual. */
    private static final BigDecimal FATOR_LANCE_MINIMO = new BigDecimal("1.05");

    private final String nome;
    private final Categoria categoria;
    private final BigDecimal precoInicial;
    private final BigDecimal precoReserva;

    private final List<ObservadorLance> observadores = new ArrayList<>();
    private final List<Lance> lances = new ArrayList<>();

    private RegraComissao regraComissao;
    private SituacaoLote situacao = SituacaoLote.ABERTO;
    private BigDecimal comissaoCobrada;

    public Lote(String nome, Categoria categoria, BigDecimal precoInicial, BigDecimal precoReserva) {
        if (precoInicial.signum() <= 0 || precoReserva.signum() < 0) {
            throw new IllegalArgumentException("Precos do lote invalidos");
        }
        this.nome = nome;
        this.categoria = categoria;
        this.precoInicial = precoInicial;
        this.precoReserva = precoReserva;
        this.regraComissao = categoria.getRegraPadrao();
    }

    // ------------------------------------------------------------------
    // Observer - operações do Sujeito (RF03)
    // ------------------------------------------------------------------

    @Override
    public void adicionarObservador(ObservadorLance observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    @Override
    public void removerObservador(ObservadorLance observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(Lance lance) {
        // Cópia da lista: um observador pode deixar de acompanhar durante a notificação.
        for (ObservadorLance observador : new ArrayList<>(observadores)) {
            if (observador != lance.getLicitante()) {
                observador.atualizar(this, lance);
            }
        }
    }

    // ------------------------------------------------------------------
    // Lances (RF01 / RF02)
    // ------------------------------------------------------------------

    public boolean receberLance(Licitante licitante, BigDecimal valor) {
        String oferta = licitante.getNome() + " ofereceu " + Moeda.formatar(valor) + " no lote '" + nome + "'";
        if (situacao != SituacaoLote.ABERTO) {
            System.out.println("[LANCE RECUSADO] " + oferta + ": o lote ja foi encerrado e nao aceita novos lances.");
            return false;
        }
        BigDecimal lanceAtual = getLanceAtual();
        BigDecimal minimo = lanceAtual.multiply(FATOR_LANCE_MINIMO);
        if (valor.compareTo(minimo) < 0) {
            System.out.println("[LANCE RECUSADO] " + oferta + ": o lance deve ser pelo menos 5% maior que o lance atual de "
                    + Moeda.formatar(lanceAtual) + " (minimo de " + Moeda.formatar(minimo.setScale(2, RoundingMode.CEILING)) + ").");
            return false;
        }
        Lance lance = new Lance(licitante, valor, LocalDateTime.now());
        lances.add(lance);
        System.out.println("[LANCE ACEITO] " + oferta + ".");
        notificarObservadores(lance);
        return true;
    }

    /** RF01: até o primeiro lance, o lance atual é o preço inicial. */
    public BigDecimal getLanceAtual() {
        return lances.isEmpty() ? precoInicial : getMaiorLance().getValor();
    }

    /** Como todo lance aceito supera o anterior, o maior lance é o último. Retorna null se não houver lances. */
    public Lance getMaiorLance() {
        return lances.isEmpty() ? null : lances.get(lances.size() - 1);
    }

    public int getTotalLances() {
        return lances.size();
    }

    public List<Lance> getLances() {
        return Collections.unmodifiableList(lances);
    }

    // ------------------------------------------------------------------
    // Strategy - troca da regra de comissão (RF07)
    // ------------------------------------------------------------------

    public void trocarRegraComissao(RegraComissao novaRegra) {
        exigirAberto("trocar a regra de comissao");
        System.out.println("[CAMPANHA] Lote '" + nome + "' (" + categoria.getNome() + "): regra de comissao trocada de '"
                + regraComissao.getDescricao() + "' para '" + novaRegra.getDescricao() + "'.");
        this.regraComissao = novaRegra;
    }

    // ------------------------------------------------------------------
    // Encerramento (RF05)
    // ------------------------------------------------------------------

    public void encerrar() {
        exigirAberto("encerrar");
        Lance maior = getMaiorLance();
        if (maior != null && maior.getValor().compareTo(precoReserva) >= 0) {
            situacao = SituacaoLote.VENDIDO;
            // RF07: a comissão é calculada pela regra vigente no momento do encerramento.
            comissaoCobrada = regraComissao.calcular(maior.getValor());
            System.out.println("[ENCERRADO] Lote '" + nome + "' VENDIDO para " + maior.getLicitante().getNome()
                    + " por " + Moeda.formatar(maior.getValor()) + " (reserva de " + Moeda.formatar(precoReserva) + ").");
        } else {
            situacao = SituacaoLote.NAO_VENDIDO;
            String motivo = maior == null
                    ? "nenhum lance recebido"
                    : "maior lance de " + Moeda.formatar(maior.getValor())
                            + ", abaixo da reserva de " + Moeda.formatar(precoReserva);
            System.out.println("[ENCERRADO] Lote '" + nome + "' NAO VENDIDO: " + motivo + ".");
        }
    }

    public Licitante getComprador() {
        exigirVendido();
        return getMaiorLance().getLicitante();
    }

    public BigDecimal getValorVenda() {
        exigirVendido();
        return getMaiorLance().getValor();
    }

    public BigDecimal getComissaoCobrada() {
        exigirVendido();
        return comissaoCobrada;
    }

    private void exigirAberto(String operacao) {
        if (situacao != SituacaoLote.ABERTO) {
            throw new IllegalStateException("Nao e possivel " + operacao + ": o lote '" + nome + "' ja foi encerrado");
        }
    }

    private void exigirVendido() {
        if (situacao != SituacaoLote.VENDIDO) {
            throw new IllegalStateException("O lote '" + nome + "' nao foi vendido");
        }
    }

    // ------------------------------------------------------------------
    // Consultas
    // ------------------------------------------------------------------

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public BigDecimal getPrecoInicial() {
        return precoInicial;
    }

    public BigDecimal getPrecoReserva() {
        return precoReserva;
    }

    public RegraComissao getRegraComissao() {
        return regraComissao;
    }

    public SituacaoLote getSituacao() {
        return situacao;
    }
}

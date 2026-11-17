package leilao;

import java.util.ArrayList;
import java.util.List;

import acompanhamento.ObservadorLance;
import acompanhamento.Sujeito;
import comissao.RegraComissao;

// Observer - SUJEITO CONCRETO: avisa os observadores a cada lance aceito.
//   Só conhece a interface ObservadorLance, nunca Participante, Painel ou Auditor (RNF01).
// Strategy - CONTEXTO: guarda a regra de comissão, que pode ser trocada
//   antes do encerramento sem recriar o lote (RNF02 / RF07).
public class Lote implements Sujeito {

    private String nome;
    private Categoria categoria;
    private double precoInicial;
    private double precoReserva;
    private double lanceAtual;
    private boolean encerrado = false;
    private boolean vendido = false;
    private double comissao;

    private RegraComissao regraComissao;
    private List<Lance> lances = new ArrayList<>();
    private List<ObservadorLance> observadores = new ArrayList<>();

    public Lote(String nome, Categoria categoria, double precoInicial, double precoReserva) {
        this.nome = nome;
        this.categoria = categoria;
        this.precoInicial = precoInicial;
        this.precoReserva = precoReserva;
        this.lanceAtual = precoInicial;                   // RF01
        this.regraComissao = categoria.getRegraPadrao();  // regra da categoria
    }

    // ---------- Observer ----------

    @Override
    public void adicionarObservador(ObservadorLance observador) {
        observadores.add(observador);
    }

    @Override
    public void removerObservador(ObservadorLance observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(Lance lance) {
        for (ObservadorLance observador : observadores) {
            // RF03: avisa todos, menos quem deu o lance
            if (observador != lance.getLicitante()) {
                observador.atualizar(this, lance);
            }
        }
    }

    // ---------- Lances (RF02) ----------

    public void receberLance(Licitante licitante, double valor) {
        if (encerrado) {
            System.out.println("[RECUSADO] " + licitante.getNome() + " tentou dar lance no lote " + nome
                    + ", mas ele ja foi encerrado.");
            return;
        }
        double minimo = lanceAtual * 1.05;
        if (valor < minimo) {
            System.out.println(String.format("[RECUSADO] %s ofereceu R$ %.2f no lote %s. O lance precisa ser"
                    + " pelo menos 5%% maior que R$ %.2f (minimo R$ %.2f).",
                    licitante.getNome(), valor, nome, lanceAtual, minimo));
            return;
        }
        lanceAtual = valor;
        Lance lance = new Lance(licitante, valor);
        lances.add(lance);
        System.out.println(String.format("[ACEITO] %s deu R$ %.2f no lote %s.", licitante.getNome(), valor, nome));
        notificarObservadores(lance);
    }

    // ---------- Strategy (RF07) ----------

    public void trocarRegraComissao(RegraComissao novaRegra) {
        if (encerrado) {
            System.out.println("Nao e possivel trocar a regra: o lote " + nome + " ja foi encerrado.");
            return;
        }
        System.out.println("[CAMPANHA] Lote " + nome + ": regra de comissao trocada de '"
                + regraComissao.getDescricao() + "' para '" + novaRegra.getDescricao() + "'.");
        regraComissao = novaRegra;
    }

    // ---------- Encerramento (RF05) ----------

    public void encerrar() {
        encerrado = true;
        if (!lances.isEmpty() && lanceAtual >= precoReserva) {
            vendido = true;
            comissao = regraComissao.calcular(lanceAtual); // regra vigente no encerramento
            System.out.println(String.format("[ENCERRADO] Lote %s VENDIDO para %s por R$ %.2f.",
                    nome, getComprador().getNome(), lanceAtual));
        } else {
            System.out.println(String.format("[ENCERRADO] Lote %s NAO VENDIDO (maior lance R$ %.2f, reserva R$ %.2f).",
                    nome, lanceAtual, precoReserva));
        }
    }

    // ---------- Getters ----------

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getPrecoInicial() {
        return precoInicial;
    }

    public double getLanceAtual() {
        return lanceAtual;
    }

    public int getTotalLances() {
        return lances.size();
    }

    // O último lance aceito é sempre o maior
    public Lance getMaiorLance() {
        return lances.get(lances.size() - 1);
    }

    public Licitante getComprador() {
        return getMaiorLance().getLicitante();
    }

    public boolean isVendido() {
        return vendido;
    }

    public double getValorVenda() {
        return lanceAtual;
    }

    public double getComissao() {
        return comissao;
    }

    public RegraComissao getRegraComissao() {
        return regraComissao;
    }
}

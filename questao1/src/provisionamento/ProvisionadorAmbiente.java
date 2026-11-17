package provisionamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import infra.Configuracao;
import infra.HistoricoAuditoria;
import infra.ResultadoProvisionamento;
import nuvem.Armazenamento;
import nuvem.FabricaNuvem;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import util.Moeda;

/**
 * Padrão Abstract Factory - papel: CLIENTE.
 *
 * Monta o ambiente usando apenas a fábrica abstrata e os produtos abstratos:
 * não conhece nenhuma classe concreta de provedor e não tem if/switch para
 * decidir o provedor (RNF02). Os três recursos vêm sempre da mesma fábrica (RNF01).
 *
 * Configuração e auditoria são obtidas pelos Singletons, sem parâmetros (RNF04/RNF05).
 * A classe não tem estado, então pode ser usada por várias threads ao mesmo tempo.
 */
public class ProvisionadorAmbiente {

    public Optional<Ambiente> provisionar(SolicitacaoAmbiente solicitacao) {
        FabricaNuvem fabrica = solicitacao.getProvedor();
        Configuracao configuracao = Configuracao.getInstancia();
        HistoricoAuditoria historico = HistoricoAuditoria.getInstancia();

        String nome = solicitacao.getNomeAmbiente();
        String provedor = fabrica.getNomeProvedor();

        // RF04/RF05: o custo é estimado antes de criar qualquer recurso.
        BigDecimal custoEstimado = fabrica.getTabelaPrecos()
                .estimarCustoMensal(solicitacao.getQuantidadeMaquinas(), solicitacao.getGbArmazenamento());
        BigDecimal orcamento = configuracao.getOrcamentoMensalMaximo();

        if (custoEstimado.compareTo(orcamento) > 0) {
            String motivo = "custo estimado de " + Moeda.formatar(custoEstimado)
                    + " ultrapassa o orcamento mensal de " + Moeda.formatar(orcamento);
            historico.registrar(nome, provedor, ResultadoProvisionamento.RECUSADO, motivo);
            System.out.println("[RECUSADO] Ambiente " + nome + " (" + provedor + "): " + motivo
                    + ". Nenhum recurso foi criado.");
            return Optional.empty();
        }

        List<MaquinaVirtual> maquinas = new ArrayList<>();
        for (int i = 0; i < solicitacao.getQuantidadeMaquinas(); i++) {
            maquinas.add(fabrica.criarMaquinaVirtual());
        }
        Armazenamento armazenamento = fabrica.criarArmazenamento(solicitacao.getGbArmazenamento());
        FilaMensagens fila = fabrica.criarFila();

        Ambiente ambiente = new Ambiente(nome, provedor, configuracao.getRegiaoPadrao(),
                maquinas, armazenamento, fila, custoEstimado);

        historico.registrar(nome, provedor, ResultadoProvisionamento.ACEITO, null);
        // Um único println por resumo, para que as linhas de threads diferentes não se misturem.
        System.out.println(ambiente.resumo());
        return Optional.of(ambiente);
    }
}

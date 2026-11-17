package provisionamento;

import java.util.ArrayList;
import java.util.List;

import infra.Configuracao;
import infra.HistoricoAuditoria;
import nuvem.Armazenamento;
import nuvem.FabricaNuvem;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;

// Abstract Factory - CLIENTE
// Monta o ambiente usando só a fábrica abstrata (FabricaNuvem). Não conhece
// FabricaAws nem FabricaAzure e não tem if/switch de provedor (RNF02).
public class ProvisionadorAmbiente {

    public void provisionar(SolicitacaoAmbiente solicitacao) {
        FabricaNuvem fabrica = solicitacao.getProvedor();
        String nome = solicitacao.getNomeAmbiente();
        String provedor = fabrica.getNomeProvedor();

        // Singletons: ninguém passou a configuração nem o histórico por parâmetro
        Configuracao configuracao = Configuracao.getInstancia();
        HistoricoAuditoria historico = HistoricoAuditoria.getInstancia();

        // RF05: estima o custo antes de criar qualquer recurso
        double custo = fabrica.getTabelaPrecos()
                .estimarCustoMensal(solicitacao.getQuantidadeMaquinas(), solicitacao.getGbArmazenamento());

        if (custo > configuracao.getOrcamentoMaximo()) {
            String motivo = String.format("custo estimado de R$ %.2f passa do orcamento de R$ %.2f",
                    custo, configuracao.getOrcamentoMaximo());
            historico.registrar(nome, provedor, "RECUSADO (" + motivo + ")");
            System.out.println("[RECUSADO] " + nome + ": " + motivo + ". Nenhum recurso foi criado.");
            return;
        }

        // Os três recursos vêm da MESMA fábrica, então são do mesmo provedor (RNF01)
        List<MaquinaVirtual> maquinas = new ArrayList<>();
        for (int i = 0; i < solicitacao.getQuantidadeMaquinas(); i++) {
            maquinas.add(fabrica.criarMaquinaVirtual());
        }
        Armazenamento armazenamento = fabrica.criarArmazenamento(solicitacao.getGbArmazenamento());
        FilaMensagens fila = fabrica.criarFila();

        Ambiente ambiente = new Ambiente(nome, provedor, configuracao.getRegiaoPadrao(),
                maquinas, armazenamento, fila, custo);

        historico.registrar(nome, provedor, "ACEITO");
        ambiente.imprimirResumo();
    }
}

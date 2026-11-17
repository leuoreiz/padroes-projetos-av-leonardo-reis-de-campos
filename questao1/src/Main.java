import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import infra.Configuracao;
import infra.HistoricoAuditoria;
import nuvem.FabricaNuvem;
import nuvem.aws.FabricaAws;
import nuvem.azure.FabricaAzure;
import provisionamento.ProvisionadorAmbiente;
import provisionamento.SolicitacaoAmbiente;

/**
 * RF08: realiza quatro provisionamentos (dois na Aws, um no Azure e um recusado
 * por orçamento), todos disparados ao mesmo tempo em threads distintas.
 *
 * A Main é o único ponto que escolhe o provedor, instanciando a fábrica concreta.
 * Para incluir um provedor novo (ex.: Gcp), basta criar FabricaGcp e seus recursos
 * e usar new FabricaGcp() aqui (RNF03).
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        FabricaNuvem aws = new FabricaAws();
        FabricaNuvem azure = new FabricaAzure();

        List<SolicitacaoAmbiente> solicitacoes = Arrays.asList(
                new SolicitacaoAmbiente("loja-virtual", aws, 3, 100),
                new SolicitacaoAmbiente("api-pagamentos", aws, 2, 500),
                new SolicitacaoAmbiente("portal-rh", azure, 4, 250),
                new SolicitacaoAmbiente("data-lake-analytics", azure, 10, 1000)); // acima do orçamento

        ProvisionadorAmbiente provisionador = new ProvisionadorAmbiente();

        // Todas as threads esperam a mesma "largada" para disparar ao mesmo tempo.
        CountDownLatch largada = new CountDownLatch(1);
        List<Thread> threads = new ArrayList<>();
        for (SolicitacaoAmbiente solicitacao : solicitacoes) {
            Thread thread = new Thread(() -> {
                aguardarLargada(largada);
                provisionador.provisionar(solicitacao);
            }, "prov-" + solicitacao.getNomeAmbiente());
            threads.add(thread);
            thread.start();
        }

        System.out.println("Disparando " + threads.size() + " provisionamentos simultaneos...");
        largada.countDown();
        for (Thread thread : threads) {
            thread.join();
        }

        HistoricoAuditoria.getInstancia().imprimir();
        System.out.println("config.properties lido " + Configuracao.getQuantidadeLeiturasDoArquivo()
                + " vez(es) durante a execucao.");
    }

    private static void aguardarLargada(CountDownLatch largada) {
        try {
            largada.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

import infra.HistoricoAuditoria;
import nuvem.FabricaNuvem;
import nuvem.aws.FabricaAws;
import nuvem.azure.FabricaAzure;
import provisionamento.ProvisionadorAmbiente;
import provisionamento.SolicitacaoAmbiente;

// RF08: quatro provisionamentos (dois na Aws, um no Azure e um recusado por
// orçamento), disparados ao mesmo tempo em threads diferentes.
public class Main {

    public static void main(String[] args) throws InterruptedException {
        // A Main é quem escolhe o provedor. Para incluir o Gcp, basta criar
        // FabricaGcp e os recursos dela e usar new FabricaGcp() aqui (RNF03).
        FabricaNuvem aws = new FabricaAws();
        FabricaNuvem azure = new FabricaAzure();

        SolicitacaoAmbiente loja = new SolicitacaoAmbiente("loja-virtual", aws, 3, 100);
        SolicitacaoAmbiente pagamentos = new SolicitacaoAmbiente("api-pagamentos", aws, 2, 500);
        SolicitacaoAmbiente rh = new SolicitacaoAmbiente("portal-rh", azure, 4, 250);
        SolicitacaoAmbiente dataLake = new SolicitacaoAmbiente("data-lake", azure, 10, 1000); // passa do orçamento

        ProvisionadorAmbiente provisionador = new ProvisionadorAmbiente();

        Thread t1 = new Thread(() -> provisionador.provisionar(loja));
        Thread t2 = new Thread(() -> provisionador.provisionar(pagamentos));
        Thread t3 = new Thread(() -> provisionador.provisionar(rh));
        Thread t4 = new Thread(() -> provisionador.provisionar(dataLake));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Espera as quatro threads terminarem antes de imprimir o histórico
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        HistoricoAuditoria.getInstancia().imprimir();
    }
}

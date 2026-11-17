import acompanhamento.AuditorLances;
import acompanhamento.PainelLeiloeiro;
import acompanhamento.Participante;
import comissao.ComissaoArte;
import comissao.ComissaoEletronicos;
import comissao.ComissaoImoveis;
import comissao.ComissaoVeiculos;
import leilao.Categoria;
import leilao.Lote;
import relatorios.AcertoVendedor;
import relatorios.ImpressoraFatura;
import servicos.CertificadoAutenticidade;
import servicos.Compra;
import servicos.CompraLote;
import servicos.EmbalagemEspecial;
import servicos.EntregaExpressa;
import servicos.SeguroTransporte;

// RF11: cenário completo da casa de leilões
public class Main {

    public static void main(String[] args) {
        // Cada categoria já vem com a sua regra de comissão (Strategy)
        Categoria arte = new Categoria("Arte", new ComissaoArte());
        Categoria veiculos = new Categoria("Veiculos", new ComissaoVeiculos());
        Categoria imoveis = new Categoria("Imoveis", new ComissaoImoveis());
        Categoria eletronicos = new Categoria("Eletronicos", new ComissaoEletronicos());
        System.out.println("Categorias: " + arte.getNome() + ", " + veiculos.getNome() + ", "
                + imoveis.getNome() + ", " + eletronicos.getNome() + "\n");

        Lote quadro = new Lote("Quadro Paisagem", arte, 10000.00, 15000.00);
        Lote caminhonete = new Lote("Caminhonete 4x4", veiculos, 150000.00, 200000.00);

        Participante ana = new Participante("Ana");
        Participante bruno = new Participante("Bruno");
        Participante carla = new Participante("Carla");
        PainelLeiloeiro painel = new PainelLeiloeiro();
        AuditorLances auditor = new AuditorLances();

        System.out.println(">>> Quem acompanha cada lote (Observer)");
        quadro.adicionarObservador(painel);
        quadro.adicionarObservador(auditor);
        caminhonete.adicionarObservador(painel);
        caminhonete.adicionarObservador(auditor);
        ana.acompanhar(quadro);
        bruno.acompanhar(quadro);
        carla.acompanhar(quadro);
        ana.acompanhar(caminhonete);
        carla.acompanhar(caminhonete);

        System.out.println("\n>>> Lances no quadro");
        ana.darLance(quadro, 10500.00);   // exatamente 5% acima: aceito
        bruno.darLance(quadro, 10800.00); // menos de 5%: recusado
        bruno.darLance(quadro, 11500.00); // aceito
        carla.deixarDeAcompanhar(quadro); // Carla não recebe mais avisos do quadro
        ana.darLance(quadro, 13000.00);   // aceito
        bruno.darLance(quadro, 16000.00); // aceito

        System.out.println("\n>>> Lances na caminhonete");
        carla.darLance(caminhonete, 160000.00); // aceito
        ana.darLance(caminhonete, 165000.00);   // menos de 5%: recusado
        ana.darLance(caminhonete, 170000.00);   // aceito

        System.out.println("\n>>> Campanha: o quadro passa a usar a regra de Eletronicos (Strategy)");
        quadro.trocarRegraComissao(eletronicos.getRegraPadrao());

        System.out.println("\n>>> Encerramento");
        quadro.encerrar();      // 16.000 >= reserva de 15.000: vendido
        caminhonete.encerrar(); // 170.000 < reserva de 200.000: não vendido
        carla.darLance(caminhonete, 250000.00); // lote encerrado: recusado

        System.out.println("\n>>> Fatura do comprador (Decorator)");
        ImpressoraFatura impressora = new ImpressoraFatura();
        Compra compra = new CompraLote(quadro);
        System.out.println("Antes dos servicos:");
        impressora.imprimir(compra);

        compra = new SeguroTransporte(compra);
        compra = new EmbalagemEspecial(compra);
        compra = new CertificadoAutenticidade(compra);
        compra = new EmbalagemEspecial(compra); // mesmo serviço contratado duas vezes
        compra = new EntregaExpressa(compra);
        System.out.println("\nCom os servicos contratados:");
        impressora.imprimir(compra);

        System.out.println("\n>>> Acerto do vendedor");
        new AcertoVendedor().imprimir(quadro);

        System.out.println();
        auditor.imprimirRegistros();
    }
}

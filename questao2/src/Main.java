import java.math.BigDecimal;

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
import util.Moeda;

/**
 * RF11: cenário completo da casa de leilões.
 *
 * - dois lotes de categorias distintas (Arte e Veículos);
 * - três participantes, um painel do leiloeiro e um auditor acompanhando;
 * - lances aceitos e recusados, saída de um participante do acompanhamento;
 * - troca da regra de comissão (campanha);
 * - encerramento de um lote vendido e de um não vendido;
 * - fatura com quatro serviços, a embalagem especial contratada duas vezes.
 */
public class Main {

    public static void main(String[] args) {
        // Categorias: cada uma carrega a sua estratégia de comissão (Strategy).
        Categoria arte = new Categoria("Arte", new ComissaoArte());
        Categoria veiculos = new Categoria("Veiculos", new ComissaoVeiculos());
        Categoria imoveis = new Categoria("Imoveis", new ComissaoImoveis());
        Categoria eletronicos = new Categoria("Eletronicos", new ComissaoEletronicos());
        System.out.println("Categorias cadastradas: " + arte.getNome() + ", " + veiculos.getNome() + ", "
                + imoveis.getNome() + ", " + eletronicos.getNome());

        Lote quadro = new Lote("Quadro Paisagem Paranaense", arte, reais(10000), reais(15000));
        Lote caminhonete = new Lote("Caminhonete 4x4 2022", veiculos, reais(150000), reais(200000));

        Participante ana = new Participante("Ana");
        Participante bruno = new Participante("Bruno");
        Participante carla = new Participante("Carla");
        PainelLeiloeiro painel = new PainelLeiloeiro();
        AuditorLances auditor = new AuditorLances();

        titulo("1. Interessados passam a acompanhar os lotes (Observer)");
        quadro.adicionarObservador(painel);
        quadro.adicionarObservador(auditor);
        caminhonete.adicionarObservador(painel);
        caminhonete.adicionarObservador(auditor);
        ana.acompanhar(quadro);
        bruno.acompanhar(quadro);
        carla.acompanhar(quadro);
        ana.acompanhar(caminhonete);
        carla.acompanhar(caminhonete);

        titulo("2. Lances no lote de Arte");
        ana.darLance(quadro, reais(10500));    // exatamente 5% acima do preço inicial: aceito
        bruno.darLance(quadro, reais(10800));  // abaixo de 5%: recusado
        bruno.darLance(quadro, reais(11500));  // aceito
        carla.deixarDeAcompanhar(quadro);      // Carla não recebe mais avisos deste lote
        ana.darLance(quadro, reais(13000));    // aceito (Carla não é avisada)
        bruno.darLance(quadro, reais(16000));  // aceito

        titulo("3. Lances no lote de Veiculos");
        carla.darLance(caminhonete, reais(160000)); // aceito
        ana.darLance(caminhonete, reais(165000));   // abaixo de 5%: recusado
        ana.darLance(caminhonete, reais(170000));   // aceito

        titulo("4. Campanha: lote de Arte passa a usar a regra de Eletronicos (Strategy)");
        quadro.trocarRegraComissao(eletronicos.getRegraPadrao());

        titulo("5. Encerramento dos lotes");
        quadro.encerrar();       // 16.000 >= reserva de 15.000: vendido
        caminhonete.encerrar();  // 170.000 < reserva de 200.000: não vendido
        carla.darLance(caminhonete, reais(250000)); // lote encerrado: recusado

        titulo("6. Fatura do comprador (Decorator)");
        Compra compra = new CompraLote(quadro);
        ImpressoraFatura impressora = new ImpressoraFatura();
        System.out.println("Fatura antes dos servicos adicionais:");
        impressora.imprimir(compra);

        compra = new SeguroTransporte(compra);
        compra = new EmbalagemEspecial(compra);
        compra = new CertificadoAutenticidade(compra);
        compra = new EmbalagemEspecial(compra); // mesmo serviço contratado duas vezes
        compra = new EntregaExpressa(compra);
        System.out.println();
        System.out.println("Fatura final, com os servicos contratados:");
        impressora.imprimir(compra);

        titulo("7. Acerto do vendedor");
        new AcertoVendedor().imprimir(quadro);
        System.out.println("(Sem a campanha, a regra de Arte cobraria "
                + Moeda.formatar(arte.getRegraPadrao().calcular(quadro.getValorVenda())) + ".)");

        titulo("8. Registro do auditor");
        auditor.imprimirRegistros();
    }

    private static BigDecimal reais(long valor) {
        return BigDecimal.valueOf(valor);
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println(">>> " + texto);
    }
}

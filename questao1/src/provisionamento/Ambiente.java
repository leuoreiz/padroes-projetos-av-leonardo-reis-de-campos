package provisionamento;

import java.util.List;

import nuvem.Armazenamento;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;

// Ambiente provisionado: máquinas virtuais, armazenamento e fila, todos do mesmo provedor
public class Ambiente {

    private String nome;
    private String provedor;
    private String regiao;
    private List<MaquinaVirtual> maquinas;
    private Armazenamento armazenamento;
    private FilaMensagens fila;
    private double custoMensal;

    public Ambiente(String nome, String provedor, String regiao, List<MaquinaVirtual> maquinas,
                    Armazenamento armazenamento, FilaMensagens fila, double custoMensal) {
        this.nome = nome;
        this.provedor = provedor;
        this.regiao = regiao;
        this.maquinas = maquinas;
        this.armazenamento = armazenamento;
        this.fila = fila;
        this.custoMensal = custoMensal;
    }

    // RF06: nome, provedor, região, id de cada recurso e custo mensal estimado
    public void imprimirResumo() {
        String ids = "";
        for (MaquinaVirtual maquina : maquinas) {
            ids += maquina.getId() + " ";
        }
        // Monta tudo numa String e imprime de uma vez, para as linhas de
        // threads diferentes não se misturarem no console
        String resumo = "----- AMBIENTE PROVISIONADO -----\n"
                + "Ambiente: " + nome + "\n"
                + "Provedor: " + provedor + "\n"
                + "Regiao: " + regiao + "\n"
                + "Maquinas virtuais: " + ids + "\n"
                + "Armazenamento: " + armazenamento.getId() + " (" + armazenamento.getGb() + " GB)\n"
                + "Fila: " + fila.getId() + "\n"
                + String.format("Custo mensal estimado: R$ %.2f", custoMensal);
        System.out.println(resumo);
    }
}

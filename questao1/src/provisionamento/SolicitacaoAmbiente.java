package provisionamento;

import nuvem.FabricaNuvem;

// Pedido de ambiente (RF03): nome, provedor, quantidade de máquinas e GB.
// O provedor é a própria fábrica: o único objeto que cria os três recursos (RNF02).
public class SolicitacaoAmbiente {

    private String nomeAmbiente;
    private FabricaNuvem provedor;
    private int quantidadeMaquinas;
    private int gbArmazenamento;

    public SolicitacaoAmbiente(String nomeAmbiente, FabricaNuvem provedor, int quantidadeMaquinas, int gbArmazenamento) {
        this.nomeAmbiente = nomeAmbiente;
        this.provedor = provedor;
        this.quantidadeMaquinas = quantidadeMaquinas;
        this.gbArmazenamento = gbArmazenamento;
    }

    public String getNomeAmbiente() {
        return nomeAmbiente;
    }

    public FabricaNuvem getProvedor() {
        return provedor;
    }

    public int getQuantidadeMaquinas() {
        return quantidadeMaquinas;
    }

    public int getGbArmazenamento() {
        return gbArmazenamento;
    }
}

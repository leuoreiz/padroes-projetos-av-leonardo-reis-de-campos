package provisionamento;

import nuvem.FabricaNuvem;

/**
 * RF03: pedido de ambiente com nome, provedor, quantidade de máquinas virtuais
 * e GB de armazenamento.
 *
 * O provedor é informado pela própria fábrica abstrata: é o "único objeto
 * responsável por criar os três recursos do provedor escolhido" (RNF02).
 */
public final class SolicitacaoAmbiente {

    private final String nomeAmbiente;
    private final FabricaNuvem provedor;
    private final int quantidadeMaquinas;
    private final int gbArmazenamento;

    public SolicitacaoAmbiente(String nomeAmbiente, FabricaNuvem provedor,
                               int quantidadeMaquinas, int gbArmazenamento) {
        if (nomeAmbiente == null || nomeAmbiente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do ambiente e obrigatorio");
        }
        if (provedor == null) {
            throw new IllegalArgumentException("O provedor e obrigatorio");
        }
        if (quantidadeMaquinas < 1 || gbArmazenamento < 1) {
            throw new IllegalArgumentException("Informe ao menos 1 maquina virtual e 1 GB de armazenamento");
        }
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

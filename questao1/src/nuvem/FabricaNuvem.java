package nuvem;

// Abstract Factory - FÁBRICA ABSTRATA
// Cria os três recursos de um mesmo provedor. Quem recebe uma fábrica só
// consegue montar um ambiente com recursos daquele provedor (RNF01).
public interface FabricaNuvem {

    String getNomeProvedor();

    // Preços do provedor, para estimar o custo ANTES de criar os recursos (RF05)
    TabelaPrecos getTabelaPrecos();

    MaquinaVirtual criarMaquinaVirtual();

    Armazenamento criarArmazenamento(int gb);

    FilaMensagens criarFila();
}

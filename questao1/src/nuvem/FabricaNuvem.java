package nuvem;

/**
 * Padrão Abstract Factory - papel: FÁBRICA ABSTRATA (AbstractFactory).
 *
 * Declara a criação da família completa de recursos de um provedor:
 * máquina virtual, armazenamento de objetos e fila de mensagens.
 * Quem monta o ambiente recebe UMA única fábrica e, portanto, só consegue
 * obter recursos do mesmo provedor (RNF01).
 */
public interface FabricaNuvem {

    String getNomeProvedor();

    /** Preços unitários do provedor, usados para estimar o custo antes de criar recursos (RF05). */
    TabelaPrecos getTabelaPrecos();

    MaquinaVirtual criarMaquinaVirtual();

    Armazenamento criarArmazenamento(int gb);

    FilaMensagens criarFila();
}

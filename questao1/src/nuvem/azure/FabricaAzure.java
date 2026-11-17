package nuvem.azure;

import nuvem.Armazenamento;
import nuvem.FabricaNuvem;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import nuvem.TabelaPrecos;

/**
 * Padrão Abstract Factory - papel: FÁBRICA CONCRETA (Azure).
 *
 * Cria a família de recursos do provedor Azure. As classes dos recursos Azure
 * são package-private: fora deste pacote ninguém consegue instanciá-las (RNF06),
 * apenas esta fábrica.
 */
public class FabricaAzure implements FabricaNuvem {

    private static final TabelaPrecos PRECOS = new TabelaPrecos(
            MaquinaVirtualAzure.CUSTO_POR_HORA,
            ArmazenamentoAzure.CUSTO_POR_GB_MES,
            FilaAzure.CUSTO_FIXO_MENSAL);

    @Override
    public String getNomeProvedor() {
        return "Azure";
    }

    @Override
    public TabelaPrecos getTabelaPrecos() {
        return PRECOS;
    }

    @Override
    public MaquinaVirtual criarMaquinaVirtual() {
        return new MaquinaVirtualAzure();
    }

    @Override
    public Armazenamento criarArmazenamento(int gb) {
        return new ArmazenamentoAzure(gb);
    }

    @Override
    public FilaMensagens criarFila() {
        return new FilaAzure();
    }
}

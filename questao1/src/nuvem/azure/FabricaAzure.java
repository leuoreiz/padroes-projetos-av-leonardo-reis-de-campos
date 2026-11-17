package nuvem.azure;

import nuvem.Armazenamento;
import nuvem.FabricaNuvem;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import nuvem.TabelaPrecos;

// Abstract Factory - FÁBRICA CONCRETA (Azure)
// As classes dos recursos Azure não são public: fora deste pacote ninguém consegue
// dar new nelas, só esta fábrica (RNF06).
public class FabricaAzure implements FabricaNuvem {

    @Override
    public String getNomeProvedor() {
        return "Azure";
    }

    @Override
    public TabelaPrecos getTabelaPrecos() {
        return new TabelaPrecos(MaquinaVirtualAzure.CUSTO_POR_HORA, ArmazenamentoAzure.CUSTO_POR_GB, FilaAzure.CUSTO_MENSAL);
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

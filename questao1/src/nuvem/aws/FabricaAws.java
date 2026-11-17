package nuvem.aws;

import nuvem.Armazenamento;
import nuvem.FabricaNuvem;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import nuvem.TabelaPrecos;

// Abstract Factory - FÁBRICA CONCRETA (Aws)
// As classes dos recursos Aws não são public: fora deste pacote ninguém consegue
// dar new nelas, só esta fábrica (RNF06).
public class FabricaAws implements FabricaNuvem {

    @Override
    public String getNomeProvedor() {
        return "Aws";
    }

    @Override
    public TabelaPrecos getTabelaPrecos() {
        return new TabelaPrecos(MaquinaVirtualAws.CUSTO_POR_HORA, ArmazenamentoAws.CUSTO_POR_GB, FilaAws.CUSTO_MENSAL);
    }

    @Override
    public MaquinaVirtual criarMaquinaVirtual() {
        return new MaquinaVirtualAws();
    }

    @Override
    public Armazenamento criarArmazenamento(int gb) {
        return new ArmazenamentoAws(gb);
    }

    @Override
    public FilaMensagens criarFila() {
        return new FilaAws();
    }
}

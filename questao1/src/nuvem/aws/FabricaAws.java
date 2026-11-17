package nuvem.aws;

import nuvem.Armazenamento;
import nuvem.FabricaNuvem;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import nuvem.TabelaPrecos;

/**
 * Padrão Abstract Factory - papel: FÁBRICA CONCRETA (Aws).
 *
 * Cria a família de recursos do provedor Aws. As classes dos recursos Aws
 * são package-private: fora deste pacote ninguém consegue instanciá-las (RNF06),
 * apenas esta fábrica.
 */
public class FabricaAws implements FabricaNuvem {

    private static final TabelaPrecos PRECOS = new TabelaPrecos(
            MaquinaVirtualAws.CUSTO_POR_HORA,
            ArmazenamentoAws.CUSTO_POR_GB_MES,
            FilaAws.CUSTO_FIXO_MENSAL);

    @Override
    public String getNomeProvedor() {
        return "Aws";
    }

    @Override
    public TabelaPrecos getTabelaPrecos() {
        return PRECOS;
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

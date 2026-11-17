package servicos;

import java.math.BigDecimal;
import java.util.List;

/** Padrão Decorator - papel: DECORADOR CONCRETO. Certificado de autenticidade: R$ 300,00 fixos. */
public class CertificadoAutenticidade extends ServicoAdicional {

    private static final BigDecimal CUSTO = new BigDecimal("300.00");

    public CertificadoAutenticidade(Compra compra) {
        super(compra);
    }

    @Override
    public List<ItemFatura> getServicos() {
        List<ItemFatura> servicos = super.getServicos();
        servicos.add(new ItemFatura("Certificado de autenticidade", CUSTO));
        return servicos;
    }

    @Override
    public BigDecimal getTotal() {
        return super.getTotal().add(CUSTO);
    }
}

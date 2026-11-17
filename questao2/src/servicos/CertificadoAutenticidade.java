package servicos;

// Decorator - DECORADOR CONCRETO: certificado de autenticidade, R$ 300,00
public class CertificadoAutenticidade extends ServicoAdicional {

    public CertificadoAutenticidade(Compra compra) {
        super(compra);
    }

    @Override
    public String getDescricao() {
        return compra.getDescricao() + "\n  + Certificado de autenticidade: R$ 300,00";
    }

    @Override
    public double getTotal() {
        return compra.getTotal() + 300.00;
    }
}

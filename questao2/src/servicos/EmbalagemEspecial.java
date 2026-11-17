package servicos;

// Decorator - DECORADOR CONCRETO: embalagem especial, R$ 180,00
public class EmbalagemEspecial extends ServicoAdicional {

    public EmbalagemEspecial(Compra compra) {
        super(compra);
    }

    @Override
    public String getDescricao() {
        return compra.getDescricao() + "\n  + Embalagem especial: R$ 180,00";
    }

    @Override
    public double getTotal() {
        return compra.getTotal() + 180.00;
    }
}

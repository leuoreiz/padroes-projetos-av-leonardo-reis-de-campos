package servicos;

// Decorator - DECORADOR ABSTRATO: é uma Compra e guarda a Compra que ele envolve.
// Os serviços se combinam em qualquer ordem e quantidade, sem precisar de
// classes prontas como SeguroComEmbalagem (RNF03).
public abstract class ServicoAdicional implements Compra {

    protected Compra compra;

    public ServicoAdicional(Compra compra) {
        this.compra = compra;
    }

    @Override
    public double getValorVenda() {
        return compra.getValorVenda();
    }
}

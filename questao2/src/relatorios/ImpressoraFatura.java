package relatorios;

import servicos.Compra;

// RF09: imprime a fatura do comprador.
// Recebe qualquer Compra e não verifica quais serviços foram contratados (RNF04).
public class ImpressoraFatura {

    public void imprimir(Compra compra) {
        System.out.println("========== FATURA DO COMPRADOR ==========");
        System.out.println(compra.getDescricao());
        System.out.println(String.format("TOTAL A PAGAR: R$ %.2f", compra.getTotal()));
        System.out.println("=========================================");
    }
}

package nuvem;

// Preços de um provedor e a fórmula do custo mensal estimado (RF04).
// Permite recusar uma solicitação acima do orçamento sem criar nenhum recurso (RF05).
public class TabelaPrecos {

    private double custoHoraMaquina;
    private double custoGbMes;
    private double custoFila;

    public TabelaPrecos(double custoHoraMaquina, double custoGbMes, double custoFila) {
        this.custoHoraMaquina = custoHoraMaquina;
        this.custoGbMes = custoGbMes;
        this.custoFila = custoFila;
    }

    // máquinas x custo por hora x 730 horas + GB x custo por GB + custo fixo da fila
    public double estimarCustoMensal(int quantidadeMaquinas, int gb) {
        return quantidadeMaquinas * custoHoraMaquina * 730
                + gb * custoGbMes
                + custoFila;
    }
}

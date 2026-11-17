package nuvem;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Tabela de preços unitários de um provedor e regra do custo mensal estimado (RF04).
 *
 * Permite calcular o custo ANTES de criar qualquer recurso, para que uma
 * solicitação acima do orçamento seja recusada sem provisionar nada (RF05).
 * É a mesma para todos os provedores: incluir um novo provedor não a altera (RNF03).
 */
public final class TabelaPrecos {

    public static final BigDecimal HORAS_POR_MES = new BigDecimal("730");

    private final BigDecimal custoHoraMaquina;
    private final BigDecimal custoGbMes;
    private final BigDecimal custoFixoFila;

    public TabelaPrecos(BigDecimal custoHoraMaquina, BigDecimal custoGbMes, BigDecimal custoFixoFila) {
        this.custoHoraMaquina = custoHoraMaquina;
        this.custoGbMes = custoGbMes;
        this.custoFixoFila = custoFixoFila;
    }

    /** RF04: maquinas x custo/hora x 730 + GB x custo/GB + custo fixo da fila. */
    public BigDecimal estimarCustoMensal(int quantidadeMaquinas, int gbArmazenamento) {
        BigDecimal maquinas = custoHoraMaquina
                .multiply(HORAS_POR_MES)
                .multiply(BigDecimal.valueOf(quantidadeMaquinas));
        BigDecimal armazenamento = custoGbMes.multiply(BigDecimal.valueOf(gbArmazenamento));
        return maquinas.add(armazenamento).add(custoFixoFila).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCustoHoraMaquina() {
        return custoHoraMaquina;
    }

    public BigDecimal getCustoGbMes() {
        return custoGbMes;
    }

    public BigDecimal getCustoFixoFila() {
        return custoFixoFila;
    }
}

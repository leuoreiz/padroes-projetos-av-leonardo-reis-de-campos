package infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Singleton - configuração global (RNF04)
// O arquivo é lido uma única vez, no construtor privado. Qualquer classe pega
// os mesmos valores com Configuracao.getInstancia(), sem receber por parâmetro.
public class Configuracao {

    private static Configuracao instancia;

    private String regiaoPadrao;
    private double orcamentoMaximo;

    private Configuracao() {
        Properties propriedades = new Properties();
        try (FileInputStream arquivo = new FileInputStream(localizarArquivo())) {
            propriedades.load(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Nao foi possivel ler o config.properties", e);
        }
        regiaoPadrao = propriedades.getProperty("regiao.padrao");
        orcamentoMaximo = Double.parseDouble(propriedades.getProperty("orcamento.mensal.maximo"));
        System.out.println("[Configuracao] config.properties lido (acontece uma unica vez)");
    }

    // synchronized: se duas threads chamarem ao mesmo tempo, só uma cria a instância
    public static synchronized Configuracao getInstancia() {
        if (instancia == null) {
            instancia = new Configuracao();
        }
        return instancia;
    }

    // Funciona rodando de dentro de src/ ou da pasta questao1/
    private static File localizarArquivo() {
        File arquivo = new File("config.properties");
        if (!arquivo.exists()) {
            arquivo = new File("src/config.properties");
        }
        return arquivo;
    }

    public String getRegiaoPadrao() {
        return regiaoPadrao;
    }

    public double getOrcamentoMaximo() {
        return orcamentoMaximo;
    }
}

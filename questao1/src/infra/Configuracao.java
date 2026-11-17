package infra;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Padrão Singleton - papel: SINGLETON (configuração global).
 *
 * RNF04: o arquivo config.properties é lido uma única vez, no construtor privado.
 * Todas as classes obtêm os mesmos valores por {@link #getInstancia()}, sem
 * receber a configuração por parâmetro.
 *
 * A criação é preguiçosa e thread-safe (double-checked locking com volatile):
 * mesmo que várias threads de provisionamento peçam a instância ao mesmo tempo,
 * apenas uma executa a leitura do arquivo.
 */
public final class Configuracao {

    private static final String ARQUIVO = "config.properties";

    private static volatile Configuracao instancia;

    /** Apenas para evidenciar, ao final da execução, que a leitura ocorreu uma única vez. */
    private static final AtomicInteger LEITURAS_DO_ARQUIVO = new AtomicInteger();

    private final String regiaoPadrao;
    private final BigDecimal orcamentoMensalMaximo;

    private Configuracao() {
        Properties propriedades = lerArquivo();
        this.regiaoPadrao = obrigatoria(propriedades, "regiao.padrao");
        this.orcamentoMensalMaximo = new BigDecimal(obrigatoria(propriedades, "orcamento.mensal.maximo"));
        LEITURAS_DO_ARQUIVO.incrementAndGet();
        System.out.println("[Configuracao] " + ARQUIVO + " lido pela thread "
                + Thread.currentThread().getName()
                + " (regiao=" + regiaoPadrao + ", orcamento=" + orcamentoMensalMaximo + ")");
    }

    public static Configuracao getInstancia() {
        Configuracao local = instancia;
        if (local == null) {
            synchronized (Configuracao.class) {
                local = instancia;
                if (local == null) {
                    local = new Configuracao();
                    instancia = local;
                }
            }
        }
        return local;
    }

    public String getRegiaoPadrao() {
        return regiaoPadrao;
    }

    public BigDecimal getOrcamentoMensalMaximo() {
        return orcamentoMensalMaximo;
    }

    public static int getQuantidadeLeiturasDoArquivo() {
        return LEITURAS_DO_ARQUIVO.get();
    }

    private static Properties lerArquivo() {
        Properties propriedades = new Properties();
        try (InputStream entrada = abrirArquivo()) {
            propriedades.load(entrada);
        } catch (IOException e) {
            throw new UncheckedIOException("Nao foi possivel ler " + ARQUIVO, e);
        }
        return propriedades;
    }

    /** Procura o arquivo no classpath, depois no diretório atual e, por fim, em src/. */
    private static InputStream abrirArquivo() throws IOException {
        InputStream doClasspath = Configuracao.class.getClassLoader().getResourceAsStream(ARQUIVO);
        if (doClasspath != null) {
            return doClasspath;
        }
        Path noDiretorioAtual = Paths.get(ARQUIVO);
        if (Files.exists(noDiretorioAtual)) {
            return Files.newInputStream(noDiretorioAtual);
        }
        return Files.newInputStream(Paths.get("src", ARQUIVO));
    }

    private static String obrigatoria(Properties propriedades, String chave) {
        String valor = propriedades.getProperty(chave);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalStateException("Chave obrigatoria ausente em " + ARQUIVO + ": " + chave);
        }
        return valor.trim();
    }
}

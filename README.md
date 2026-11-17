# Padrões de Projetos: Avaliação Semestral

Universidade Positivo, disciplina de Padrões de Projetos.
Linguagem: Java, apenas a biblioteca padrão. O código compila a partir do Java 8.

## Como executar

Em cada questão, a partir da pasta da questão (`questao1/` ou `questao2/`):

```bash
javac -encoding UTF-8 -d out -sourcepath src src/Main.java
java -cp out Main
```

Na questão 1, o `config.properties` fica em `questao1/src/`. Ele é procurado no classpath, no diretório atual e em `src/`, então também funciona ao rodar pela IDE.

---

## Questão 1: Provisionamento Multinuvem

Diagrama: [`questao1/diagrama/questao1.puml`](questao1/diagrama/questao1.puml)

| Requisito | Padrão | Como foi atendido |
|---|---|---|
| RNF01, RNF02, RNF03, RNF06 | **Abstract Factory** | `FabricaNuvem` (fábrica abstrata) cria a família `MaquinaVirtual` / `Armazenamento` / `FilaMensagens`. `FabricaAws` e `FabricaAzure` são as fábricas concretas. O `ProvisionadorAmbiente` (cliente) recebe uma única fábrica, dentro da `SolicitacaoAmbiente`, e só usa as abstrações, sem `if`/`switch` de provedor. |
| RNF04 | **Singleton** | `Configuracao` lê o `config.properties` uma única vez, no construtor privado. O `getInstancia()` é thread-safe (double-checked locking + `volatile`). |
| RNF05 | **Singleton** | `HistoricoAuditoria` tem instância única, e `registrar()`/`getRegistros()` são `synchronized`. A data/hora e a sequência são geradas dentro do mesmo bloqueio, então nenhum registro se perde e o histórico sai na ordem em que foi feito. |

Decisões de projeto:
- **RNF01 e RNF06 garantidos pelo compilador:** os produtos concretos (`MaquinaVirtualAws` etc.) são classes package-private. Fora do pacote do provedor, só a fábrica dele consegue instanciá-los, e um ambiente só é montado com recursos de uma mesma fábrica.
- **RF05 (recusa sem criar recursos):** cada fábrica expõe uma `TabelaPrecos` com os preços do provedor. O custo é estimado antes de criar qualquer recurso, então uma solicitação recusada não consome identificadores.
- **RF03 com concorrência:** a numeração sequencial de cada tipo de recurso usa `AtomicInteger`.
- **RF08:** a `Main` dispara os 4 provisionamentos em 4 threads, liberadas juntas por um `CountDownLatch`.
- **RNF03:** para incluir o Gcp, basta criar `FabricaGcp` e os seus 3 recursos e usar `new FabricaGcp()` na `Main`.

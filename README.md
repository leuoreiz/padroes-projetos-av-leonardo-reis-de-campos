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

---

## Questão 2: Casa de Leilões Online

Diagrama: [`questao2/diagrama/questao2.puml`](questao2/diagrama/questao2.puml)

| Requisito | Padrão | Como foi atendido |
|---|---|---|
| RNF01 | **Observer** | `Sujeito` (subject) e `ObservadorLance` (observer). O `Lote` é o sujeito concreto e conhece apenas as interfaces `ObservadorLance` e `Licitante`. `Participante`, `PainelLeiloeiro` e `AuditorLances` são os observadores concretos. Cada lance aceito é enviado a todos, menos a quem deu o lance. |
| RNF02 | **Strategy** | `RegraComissao` (estratégia) com `ComissaoArte`, `ComissaoVeiculos`, `ComissaoImoveis` e `ComissaoEletronicos`. O `Lote` é o contexto: `trocarRegraComissao()` troca a regra em tempo de execução, antes do encerramento. A `Categoria` só carrega a sua regra padrão, sem `if`/`switch`. |
| RNF03, RNF04 | **Decorator** | `Compra` (componente), `CompraLote` (componente concreto), `ServicoAdicional` (decorador abstrato) e os 4 serviços (decoradores concretos). A `ImpressoraFatura` recebe qualquer `Compra` e trata da mesma forma uma compra com ou sem serviços. |
| RNF05 | Pacotes independentes | Os pacotes `acompanhamento` (Observer), `comissao` (Strategy) e `servicos` (Decorator) não dependem uns dos outros. |

Decisões de projeto:
- **RF07:** a comissão é calculada e guardada no momento do `encerrar()`, com a regra vigente naquele instante. Depois do encerramento, a regra não pode mais ser trocada.
- **RF02:** valores em `BigDecimal`, para que o limite de 5% seja exato (ex.: 1.102,50 sobre 1.050,00 é aceito).
- **RF11:** a `Main` cobre os dois lotes (Arte e Veículos), três participantes, painel e auditor, lances aceitos e recusados, a saída da Carla, a campanha (regra de Eletrônicos no lote de Arte), o lote vendido e o não vendido, e a fatura com 4 serviços, com a embalagem especial contratada duas vezes.

# Padrões de Projetos: Avaliação Semestral

Universidade Positivo, disciplina de Padrões de Projetos. Java, só com a biblioteca padrão.

## Como executar

Em cada questão, a partir da pasta da questão (`questao1/` ou `questao2/`):

```bash
javac -d out -sourcepath src src/Main.java
java -cp out Main
```

Na questão 1, o `config.properties` fica em `questao1/src/`.

## Questão 1: Provisionamento Multinuvem

Diagrama: [`questao1/diagrama/questao1.puml`](questao1/diagrama/questao1.puml)

| Requisito | Padrão | Onde |
|---|---|---|
| RNF01, RNF02, RNF03, RNF06 | **Abstract Factory** | `FabricaNuvem` (fábrica abstrata), `FabricaAws` e `FabricaAzure` (fábricas concretas), `MaquinaVirtual` / `Armazenamento` / `FilaMensagens` (produtos). O `ProvisionadorAmbiente` (cliente) recebe uma única fábrica e não tem `if`/`switch` de provedor. As classes concretas dos recursos não são `public`, então só a fábrica do provedor consegue criá-las. |
| RNF04 | **Singleton** | `Configuracao`: construtor privado que lê o `config.properties` uma única vez, com `getInstancia()` `synchronized`. |
| RNF05 | **Singleton** | `HistoricoAuditoria`: instância única, com `registrar()` `synchronized` para que os registros de threads simultâneas não se percam. |

O custo é estimado pela `TabelaPrecos` da fábrica antes de criar os recursos, então a solicitação recusada por orçamento não cria nada (RF05). A `Main` dispara os 4 provisionamentos em 4 threads (RF08).

## Questão 2: Casa de Leilões Online

Diagrama: [`questao2/diagrama/questao2.puml`](questao2/diagrama/questao2.puml)

| Requisito | Padrão | Onde |
|---|---|---|
| RNF01 | **Observer** | `Sujeito` e `ObservadorLance`. O `Lote` é o sujeito concreto, e `Participante`, `PainelLeiloeiro` e `AuditorLances` são os observadores. O lote só conhece as interfaces e avisa todos, menos quem deu o lance. |
| RNF02 | **Strategy** | `RegraComissao`, com uma classe por categoria. O `Lote` é o contexto e troca a regra com `trocarRegraComissao()` (RF07). |
| RNF03, RNF04 | **Decorator** | `Compra` (componente), `CompraLote` (componente concreto), `ServicoAdicional` (decorador) e os 4 serviços. A `ImpressoraFatura` imprime qualquer `Compra` do mesmo jeito. |
| RNF05 | Pacotes separados | `acompanhamento` (Observer), `comissao` (Strategy) e `servicos` (Decorator) não dependem uns dos outros. |

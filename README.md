# Intelipost: Teste prático para analista de qualidade e testes

Para a crição da automação dos testes no formato BDD, escolhemos o Cucumber e a sintaxe Gherkin por ser muito utilizado na área de testes.
Foi utilizado o maven para facilitar a compilação e gestão das bibliotecas externas e dependências do projeto.

Os steps foram criados de forma genérica visando sua reutilização, pois pode ser reutilizado no testes de outras APIs JSON sem grandes alterações em seu código.

Os cenários de testes do arquivo `features/intelipost.feature` foram agrupados da seguinte forma, onde possuem a mesma validação dentro dos "Scenarios Outlines" do Cucumber.
  - testes positivos gerais
  - testes positivos - Retornos sem "Correios PAC"
  - testes positivos - Destino Pará - 20 dias na estimativa
  - testes negativos - Canais CN1 e CN2 e origem no TO e destino na região sudeste

A maior dificuldade da automação foi em como organizar os cenários e definir a quantidade de testes realizados.

### Execução

- Antes da execução é preciso configurar o arquivo .properties:
 ```sh
 src\main\resources\cucumber.properties
 ```
 Nele temos a configuração do endereço base da api testada, o caminho de onde se encontram os arquivos .feature, quais tags do cucumber queremos utilizar e onde o relatório HTML será gerado.


- É possível executar o projeto em uma IDE importanto o projeto e executando o método main da classe RESTRunner.java no caminho abaixo:
```sh
src\main\java\intelipost\cucumber\common\utils\RunnerClass\RESTRunner.java
```

- Também é possível executar em um terminal (linux ou windows) com os seguintes comandos:
```sh
$ mvn clean install
$ cd target
$ java -jar target/cucumber-runner-1.0.0.0.jar
```
Após a execução um relatório em .json e outro em HTML serão gerados na pasta `executionReports`:
```sh
executionReports/execution.json
executionReports/cucumber-html-reports/overview-features.html
```
O caminho completo do relatório HTML aparecerá no final do log da execução.
Um relatorio de exemplo da execução está no projeto na pasta indicada acima.


A orgranização do projeto
A pasta `jmeter` contém o script `quote_by_product.csv.jmx` e a massa de dados `quote_by_product.csv` relativas ao cenário `testes negativos - Canais CN1 e CN2 e origem no TO e destino na região sudeste`.

O projeto java possui o pacote commom.utils, que contem a classe principal para a execução do projeto (`RestRunner.java`) e também alguns utilitários para realziação dos requests HTTP (`HttpClient.java` e `RestApi.java`), classe para ler o arquivo .properties (`PropertiesUtil.java`) e a classe para gerar o relatório HTML (`ReposrtJson.java`).

Dentre as bibliotecas utilizadas, podemos destacar:
- org.json para validação da resposta json;
- org.apache.httpcomponents httpclient e commons-httpclient para a criação das classes para requisições HTTP;
- bibliotecas do Cucumber para criar os testes na sintaxe Gherkin;
- e a cucumber-reporting, plugin do jenkins utilizado para a criação do relatório de execução.


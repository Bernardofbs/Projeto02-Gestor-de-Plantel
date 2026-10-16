# Relatório Técnico: Sistema para Gestão de Fazendas de Gado Leiteiro (Gestão do Plantel)

**Nome do Aluno:** Bernardo Ferreira Barbosa Silva  
**Curso:** Sistemas para Internet  

## Descrição do trabalho desenvolvido

O projeto consiste no desenvolvimento de um sistema de Gestão de Fazendas de Gado Leiteiro, construído inteiramente visando aplicar a Programação Orientada a Objetos. O objetivo principal do software é automatizar o controle do plantel, permitindo o registro, a listagem e a organização das informações referentes às vacas matrizes (vacas que produzem leite) e aos seus bezerros. 

Para garantir que as informações do rebanho não sejam perdidas entre as execuções, o sistema implementa a persistência de dados utilizando a leitura e escrita em um arquivo de texto. Através de um menu interativo executado no console, o usuário atua como o administrador da fazenda, podendo consultar relatórios de produção, avaliar o ciclo de lactação das matrizes e visualizar listas ordenadas por diferentes critérios estratégicos, como identificação numérica ou idade. 

Além disso, o sistema agora permite a gestão individual de cada animal, possibilitando a atualização de estados de saúde, o registo do histórico de vacinas dos bezerros, a remoção lógica de animais (registo de óbito) e a emissão de relatórios específicos de negócio, como a contagem de bezerros machos prontos para venda ou em espera.

## Descrição das classes e métodos implementados

A arquitetura do software foi dividida em quatro classes principais, separando as entidades de modelo das classes de controle e interface, sem a utilização de banco de dados externo. A lógica central funciona carregando os dados do arquivo físico para a memória RAM, através de vetores, no momento da inicialização, manipulando esses vetores durante a execução e reescrevendo o arquivo físico no encerramento.

1. **Classe Vaca e Classe Bezerro:** Entidades de modelo. Aplicam o conceito de encapsulamento, possuindo todos os seus atributos privados. A interação com esses dados ocorre unicamente por meio de métodos de acesso `get`. Elas também abrigam os métodos das regras de negócio exigidas, como a verificação do ciclo de lactação da vaca, a verificação do período de desmame (60 dias), a liberação de venda de bezerros machos a partir do 6º mês e o controlo do histórico de vacinas de cada bezerro.

2. **Classe Gerenciador:** Funciona como o motor do sistema, possuindo as estruturas de armazenamento. Ela contém os métodos de ler o arquivo linha por linha e outro para varrer os vetores e regravar o arquivo. Além disso, centraliza os métodos de ordenação (`TimSort`) e atua como um mecanismo de busca: implementa métodos para encontrar animais específicos pelo seu identificador (brinco ou número provisório), permitindo que o Menu atualize a saúde, registe vacinas e óbitos sem manipular os vetores diretamente. Também abriga os algoritmos que geram os relatórios consolidados do rebanho.

3. **Classe Menu:** É a interface de interação com o usuário final e a classe principal do programa. Ela instancia o gerenciador, começa a leitura inicial do arquivo e mantém o sistema em execução através de um laço de repetição. Dentro desse laço, uma estrutura de seleção interpreta os comandos numéricos digitados pelo usuário, direcionando para a funcionalidade correspondente no gerenciador e garantindo o salvamento automático ao selecionar a opção de saída.

## Descrição da ordem dos dados armazenados nos arquivos

O armazenamento persistente foi estruturado em um arquivo de texto, utilizando o `;` (ponto e vírgula) como delimitador entre os campos.

O cabeçalho do arquivo é composto por duas linhas numéricas: a primeira indica a capacidade total do vetor de vacas e a segunda a capacidade do vetor de bezerros. Essa estrutura informa ao sistema o tamanho exato da memória que deve ser alocada (tamanho do vetor) antes da leitura dos dados individuais.

A partir da terceira linha, os registros dos animais são listados sequencialmente. Para diferenciar a leitura, cada linha é iniciada por uma letra de identificação ("V" para Vacas e "B" para Bezerros):

*   **Linha de Vaca:** Inicia com "V", seguida pelo número do brinco, raça, data de nascimento, quantidade de gestações bem-sucedidas, data do último parto, volume de produção média diária de leite e estado de saúde atual.
*   **Linha de Bezerro:** Inicia com "B", seguida pelo número de identificação do pai, número de identificação da mãe, número provisório do bezerro, data de nascimento, sexo e estado de saúde atual.

## Erros e acertos durante o desenvolvimento do trabalho

Durante o processo de desenvolvimento, a manipulação de vetores estáticos e a conversão de tipos de dados apresentaram desafios. Um erro recorrente no início foi a ocorrência de ponteiros nulos ao tentar armazenar objetos no vetor de bezerros, causado pela falta da instanciação do vetor na memória.

Outro obstáculo superado ocorreu na fase de salvamento, onde dados como raça, produção de leite e datas resultavam em valores `null` ou zerados no arquivo final. Esse erro lógico foi diagnosticado como uma falha na atribuição de variáveis dentro dos construtores das classes Vaca e Bezerro, sendo resolvido com o uso do referenciador interno da classe (`this`).

Entre os acertos do projeto, destaca-se a conversão das strings de data diretamente para objetos de tempo `LocalDate`, o que evitou cálculos manuais complexos de calendário e facilitou a avaliação do rebanho.

Relatório Técnico: Sistema para Gestão de Fazendas de Gado Leiteiro (Projeto Final - Fase Provisória)

Nome do Aluno: Bernardo Ferreira Barbosa Silva

Curso: Sistemas para Internet

1. Descrição do Trabalho Desenvolvido

O projeto consiste no desenvolvimento de um Sistema de Gestão de Fazendas de Gado Leiteiro, construído com base nos princípios da Programação Orientada a Objetos (POO). O objetivo principal do software é automatizar o controlo e acompanhamento do plantel, permitindo a gestão de vacas matrizes (produção de leite, histórico reprodutivo e lactação) e dos seus bezerros (histórico de vacinação, desmame e viabilidade de venda).

Na presente fase provisória do Projeto Final, o sistema evoluiu de uma interface de linha de comandos (CLI) para uma Interface Gráfica de Utilizador (GUI) desenvolvida em Java Swing com o IntelliJ UI Designer. Esta transição trouxe melhorias significativas na usabilidade, permitindo:

Gestão em Tempo Real via Tabelas (JTable): Visualização segregada por abas (JTabbedPane) para Vacas e Bezerros com suporte a estilização visual dinâmica e alternância de Modo Claro / Modo Escuro.

Edição Granular por Célula: Permite selecionar qualquer célula da tabela para modificar atributos específicos (raça, produção, datas, estados de saúde e vacinas) sem a necessidade de reescrever todo o registo.

Ordenação Otimizada ($O(n \log n)$): Ordenação dupla (Estado de Saúde + Produção de Leite para vacas) e por Data de Nascimento (para bezerros) utilizando o algoritmo TimSort (Arrays.sort com Comparator.nullsLast).

Remoção Lógica e Persistência Expandida: Inativação de animais (registos de óbito) mantendo a integridade dos vetores e sincronização automática com o ficheiro de texto arquivo_plantel.txt.

2. Descrição das Classes e Arquitetura Implementada

A arquitetura do software adota a separação entre Entidades de Modelo, Camada de Controlo/Gerenciamento e Camada de Apresentação (GUI):

Classe Vaca e Classe Bezerro (Modelo):

Encapsulam todos os atributos privados com métodos de acesso get e set.

Vaca: Controla dados de lactação, partos sucedidos, volume diário de leite e verificação dinâmica do ciclo biológico.

Bezerro: Gerencia dados de filiação, sexo, regras de desmame (60 dias), liberação para venda (machos $\ge$ 6 meses) e um vetor dinâmico para registo do histórico de vacinas.

Classe Gerenciador (Controlo e Persistência):

Atua como o motor lógico do sistema. Armazena os animais em vetores (Vaca[] e Bezerro[]) inicializados de forma segura (tamanho zero em vez de null) para prevenção de NullPointerException.

Implementa o cadastro dinâmico expandindo arrays via Arrays.copyOf.

Realiza a leitura e escrita serializada no ficheiro arquivo_plantel.txt, garantindo o suporte à 8ª coluna (lista de vacinas formatadas por vírgula).

Contém os métodos de ordenação via Comparator, busca por identificador único (brinco ou número provisório) e relatórios de trecho/intervalo.

Classe TelaPrincipal (Interface Gráfica Swing):

Substituiu o antigo Menu.java de consola. Constrói a janela principal (JFrame) utilizando JTabbedPane, JScrollPane e JTable.

Gerencia os ouvintes de eventos (Listeners) para a ordenação do plantel, remoção lógica, alternância de temas em tempo real (aplicarTema) e encerramento com salvamento automático.

3. Descrição do Formato de Armazenamento (arquivo_plantel.txt)

A persistência de dados ocorre em ficheiro de texto plano utilizando o caractere ; (ponto e vírgula) como delimitador de campos.

Cabeçalho

Linha 1: Quantidade total de vacas ativas no plantel.

Linha 2: Quantidade total de bezerros ativos no plantel.

Registos dos Animais

Linha de Vaca (V):

V;numBrinco;raca;dataNascimento;gestacoesSucedidas;dataUltimoParto;producaoMediaDia;estadoDeSaude

Linha de Bezerro (B):

B;numPai;numMae;numProv;dataNascimentoB;sexo;estadoDeSaude;vacinasFormatadas

(O campo vacinasFormatadas armazena as vacinas separadas por vírgula ou o termo "Nenhuma" caso não existam registos).

4. Evolução, Ajustes e Correções Aplicadas

Durante a transição da segunda etapa para a fase provisória do Projeto Final, foram incorporadas melhorias essenciais decorrentes do feedback recebido:

Tratamento de Arquivos Vazios / Inicialização Segura:

Os vetores vetorVacas e vetorBezerro passam a ser instanciados no construtor com tamanho 0, eliminando exceções caso a leitura do ficheiro seja iniciada sem dados prévios.

Listagem por Trecho (Subconjuntos):

Adicionados os métodos listarTrechoVacas(int inicio, int fim) e listarTrechoBezerros(int inicio, int fim) para exibição parcial e parametrizada dos vetores.

Persistência Completa de Vacinas:

A coluna de vacinas foi totalmente integrada na serialização do ficheiro .txt e apresentada na 7ª coluna da JTable de Bezerros na interface gráfica.

Preservação de Dados durante a Ordenação:

A substituição de algoritmos manuais pelo TimSort (Arrays.sort) garantiu a manutenção perfeita das referências em memória, resolvendo o problema de perda de dados alterados antes da ordenação.

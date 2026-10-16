//Para Ler o arquivo
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

//Para salvar o arquivo
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

//Para ordenar os vetores usando TimSort (MergeSort otimizado)
import java.util.Arrays;
import java.util.Comparator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Gerenciador {

    private Vaca[] vetorVacas;
    private Bezerro[] vetorBezerro;
    //Declarei os vetores 'null' para serem instanciados e preenchidos com os dados do arquivo


    // ************************** MÉTODOS GERAIS DO SISTEMA **************************


    public void lerArquivo(String arquivoPlantel) {

        try {

            File arquivo = new File(arquivoPlantel);
            //Aponta o caminho do arquivo e aponta para a variável "arquivoPlantel"

            // Abre e começa a ler o arquivo
            Scanner ler = new Scanner(arquivo);

            // Passou até aqui, ent o arquivo existe

            //Ler a primeira linha e descobrir o tamanho dos vetores
            if (ler.hasNextInt()) {
                // Lê a primeira linha: quantidade de vacas
                int numVacas = ler.nextInt();
                ler.nextLine(); // Consome a quebra de linha
                this.vetorVacas = new Vaca[numVacas];

                // Lê a segunda linha: quantidade de bezerros
                int numBezerros = ler.nextInt();
                ler.nextLine(); // Consome a quebra de linha
                this.vetorBezerro = new Bezerro[numBezerros];
            }

            //while para ler o resto do arquivo linha por linha, usando split para separar

            DateTimeFormatter formatadorBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            //Formatar as datas para o formato brasileiro

            // Variável para controlar em qual indice do vetor vai ser guardada a vaca atual
            int indiceVaca = 0;
            int indiceBezerro = 0;

            // Enquanto existir uma próxima linha no arquivo, o laço continua
            while (ler.hasNextLine()) {

                //Puxa a linha inteira do txt
                String linha = ler.nextLine();

                // Corta o texto onde tem o ";" e cria um vetor com as partes do txt separadas
                String[] partes = linha.split(";");
                //Separar as linhas de Vacas e de Bezerros:
                String tipoAnimal = partes[0];
                // Pega o "V" ou o "B" da primeira posição

                if (tipoAnimal.equals("V")) {

                    //numBrinco para int
                    int numBrinco = Integer.parseInt(partes[1]);
                    String raca = partes[2];

                    //Transformar essas partes nos seus respectivos tipos:
                    //Transformar a String da data em uma data real tipo LocalDate:
                    LocalDate nascimento = LocalDate.parse(partes[3], formatadorBR);
                    LocalDate dataNascimento = nascimento;

                    //Ja vem inteiro
                    int gestacoes = Integer.parseInt(partes[4]);

                    // Converte a Data do Último Parto
                    LocalDate parto = LocalDate.parse(partes[5], formatadorBR);
                    LocalDate dataUltimoParto = parto;

                    // Converte o double
                    double producaoMedia = Double.parseDouble(partes[6]);

                    //Ja vem String
                    String estadoDeSaude = partes[7];

                    Vaca novaVaca = new Vaca(numBrinco, raca, dataNascimento, gestacoes, dataUltimoParto, producaoMedia, estadoDeSaude);

                    //Colocar o objeto tipo Vaca com os atributos preenchidos em um indice do Vetor e depois avançar para o proximo
                    this.vetorVacas[indiceVaca] = novaVaca;
                    indiceVaca++;

                } else if (tipoAnimal.equals("B")) {

                    // numPai para int
                    int numPai = Integer.parseInt(partes[1]);

                    // numMae para int
                    int numMae = Integer.parseInt(partes[2]);

                    // numProv para int
                    int numProv = Integer.parseInt(partes[3]);

                    // Transformar a String da data em uma data real:
                    LocalDate nascBezerro = LocalDate.parse(partes[4], formatadorBR);
                    LocalDate dataNascimentoB = nascBezerro;

                    // Transformar a String do sexo em char
                    char sexo = partes[5].charAt(0);

                    // Ja vem String
                    String estadoDeSaude = partes[6];

                    // Criação do objeto
                    Bezerro novoBezerro = new Bezerro(numPai, numMae, numProv, dataNascimentoB, sexo, estadoDeSaude);

                    // Colocar o objeto tipo Bezerro com os atributos preenchidos em um indice do Vetor e depois avançar para o proximo
                    this.vetorBezerro[indiceBezerro] = novoBezerro;
                    indiceBezerro++;
                }

            }

            ler.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.Vetores serão iniciados vazios.");
        }
    }

    public void salvarArquivo(String arquivoPlantel) {
        try {
            //FileWriter abre o arquivo e escreve nele substituindo o antigo
            FileWriter arquivo = new FileWriter(arquivoPlantel);
            PrintWriter gravador = new PrintWriter(arquivo);

            //Grava as quantidades nas duas primeiras linhas
            gravador.println(this.vetorVacas.length);
            gravador.println(this.vetorBezerro.length);

            //formatador agora faz o caminho inverso LocalDate para String
            DateTimeFormatter formatadorBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            //Varre o vetor de Vacas e escreve
            for (int i = 0; i < this.vetorVacas.length; i++) {
                Vaca v = this.vetorVacas[i];

                //Salva se o indice não estiver vazio
                if (v != null) {
                    //Junta os dados colocando ; entre eles.
                    String linhaVaca = "V;" +
                            v.getNumBrinco() + ";" +
                            v.getRaca() + ";" +
                            v.getDataNascimentoV().format(formatadorBR) + ";" +
                            v.getGestacaoSucedida() + ";" +
                            v.getDataUltimoParto().format(formatadorBR) + ";" +
                            v.getProducaoMediaDia() + ";" +
                            v.getEstadoDeSaude();

                    gravador.println(linhaVaca);
                }
            }

            //Varre o vetor de Bezerros e escreve
            for (int i = 0; i < this.vetorBezerro.length; i++) {
                Bezerro b = this.vetorBezerro[i];

                if (b != null) {
                    String linhaBezerro = "B;" +
                            b.getNumPai() + ";" +
                            b.getNumMae() + ";" +
                            b.getNumProv() + ";" +
                            b.getDataNascimentoB().format(formatadorBR) + ";" +
                            b.getSexo() + ";" +
                            b.getEstadoDeSaude();

                    gravador.println(linhaBezerro);
                }
            }

            gravador.close(); //Salvar o arquivo no disco
            System.out.println("Arquivo salvo com sucesso");

        } catch (IOException e) {
            System.out.println("Erro ao tentar salvar o arquivo de texto.");
        }
    }


    // ************************** MÉTODOS DAS VACAS **************************


    public void listarVacas() {

        //Imprimir os vetores ordenados
        System.out.println("\n--- LISTA DE VACAS ---");
        for (int i = 0; i < this.vetorVacas.length; i++) {
            Vaca v = this.vetorVacas[i];

            //Só imprime se tiver um objeto
            if (v != null) {
                System.out.println("Brinco: " + v.getNumBrinco() +
                        " | Produção: " + v.getProducaoMediaDia() + " L/dia" +
                        " | Raça: " + v.getRaca() +
                        " | Saúde: " + v.getEstadoDeSaude());
            }
        }
        System.out.println("----------------------\n");
    }

    public void ordenarVacasPorBrinco() {
        //Ordena pelo número do brinco e leva indices vazios para o final
        Arrays.sort(this.vetorVacas, Comparator.nullsLast(Comparator.comparingInt(Vaca::getNumBrinco)));
        System.out.println("Vacas ordenadas por Número do Brinco com sucesso");
    }

    public void ordenarVacasPorProducao() {
        //Ordena pela produção de leite
        Arrays.sort(this.vetorVacas, Comparator.nullsLast(Comparator.comparingDouble(Vaca::getProducaoMediaDia)));
        System.out.println("Vacas ordenadas por Produção de Leite com sucesso");
    }

    public void QuantidadesVacasNoCiclo(LocalDate dataAtual) {

        int quantProduzindo = 0;
        int quantDescanso = 0;
        int quantAmamentando = 0;
        int quantPronta = 0;

        for (int i = 0; i < this.vetorVacas.length; i++) {
            Vaca vacaAtual = this.vetorVacas[i];

            if (vacaAtual != null && vacaAtual.isregistroAtivoVaca()) {
                String estado = vacaAtual.verificarCicloAtual(dataAtual);

                if (estado.equals("Em descanso")) {
                    quantDescanso++;
                } else if (estado.equals("Produzindo leite")) {
                    quantProduzindo++;
                } else if (estado.equals("Amamentando")) {
                    quantAmamentando++;
                } else if (estado.equals("Pronta para a inseminação")) {
                    quantPronta++;
                }
            }
        }

        System.out.println("Vacas Amamentando: " + quantAmamentando);
        System.out.println("Vacas Produzindo Leite: " + quantProduzindo);
        System.out.println("Vacas Em Descanso: " + quantDescanso);
        System.out.println("Vacas Prontas para Inseminação: " + quantPronta);
        System.out.println("Total Ativas: " + (quantAmamentando + quantProduzindo + quantDescanso + quantPronta));
    }
    // Método auxiliar para encontrar a vaca pelo brinco
    public Vaca buscarVacaPorBrinco(int brinco) {
        for (int i = 0; i < this.vetorVacas.length; i++) {
            if (this.vetorVacas[i] != null && this.vetorVacas[i].getNumBrinco() == brinco) {
                return this.vetorVacas[i];
            }
        }
        return null; // Não achou
    }

    //Atualizar a saúde
    public void alterarSaudeVaca(int brinco, String novaSaude) {
        Vaca v = buscarVacaPorBrinco(brinco);
        if (v != null) {
            v.atualizarSaude(novaSaude);
            System.out.println("Saúde da vaca " + brinco + " atualizada com sucesso");
        } else {
            System.out.println("Vaca com o brinco " + brinco + " não encontrada no plantel.");
        }
    }

    // Método que o Menu vai chamar para ver o ciclo
    public void consultarCicloVaca(int brinco) {
        Vaca v = buscarVacaPorBrinco(brinco);
        if (v != null) {
            LocalDate hoje = LocalDate.now();
            System.out.println("--- DADOS DA VACA " + brinco + " ---");
            System.out.println("Ciclo Atual: " + v.verificarCicloAtual(hoje));
            System.out.println("Inseminação: " + v.DataNovaInseminacao(hoje));
            System.out.println("---------------------------");
        } else {
            System.out.println("Vaca com o brinco " + brinco + " não encontrada no plantel.");
        }
    }

    public void registrarFalecimentoVaca(int brinco) {
        Vaca v = buscarVacaPorBrinco(brinco);
        if (v != null) {
            v.marcarComoFalecida();
        } else {
            System.out.println("Vaca com o brinco " + brinco + " não encontrada no plantel.");
        }
    }


    // ************************** MÉTODOS DOS BEZERROS **************************


    public void listarBezerros() {

        //Imprimir os vetores ordenados
        System.out.println("\n--- LISTA DE BEZERROS ---");
        DateTimeFormatter formatadorBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < this.vetorBezerro.length; i++) {
            Bezerro b = this.vetorBezerro[i];

            if (b != null) {
                System.out.println("Num Prov: " + b.getNumProv() +
                        " | Nascimento: " + b.getDataNascimentoB().format(formatadorBR) +
                        " | Sexo: " + b.getSexo() +
                        " | Saúde: " + b.getEstadoDeSaude());
            }
        }
        System.out.println("-------------------------\n");
    }

    public void ordenarBezerrosPorNumeroProvisorio() {
        //Compara usando números int
        Arrays.sort(this.vetorBezerro, Comparator.nullsLast(Comparator.comparingInt(Bezerro::getNumProv)));
        System.out.println("Bezerros ordenados por Número Provisório com sucesso");
    }

    public void ordenarBezerrosPorNascimento() {
        //LocalDate é um objeto, uso apenas.comparing() sem o Int no final
        Arrays.sort(this.vetorBezerro, Comparator.nullsLast(Comparator.comparing(Bezerro::getDataNascimentoB)));
        System.out.println("Bezerros ordenados por Data de Nascimento com sucesso");
    }

    // Encontrar o bezerro pelo número provisório
    public Bezerro buscarBezerroPorNumProv(int numProv) {
        for (int i = 0; i < this.vetorBezerro.length; i++) {
            if (this.vetorBezerro[i] != null && this.vetorBezerro[i].getNumProv() == numProv) {
                return this.vetorBezerro[i];
            }
        }
        return null; // Não achou
    }

    // Consultar desmame e venda juntos
    public void consultarStatusBezerro(int numProv) {
        Bezerro b = buscarBezerroPorNumProv(numProv);
        if (b != null) {
            LocalDate hoje = LocalDate.now();
            System.out.println("--- STATUS DO BEZERRO " + numProv + " ---");
            System.out.println("Desmame: " + b.verificarDesmame(hoje));
            System.out.println("Venda: " + b.liberarVenda(hoje));
            System.out.println("-----------------------------");
        } else {
            System.out.println("Bezerro com o número " + numProv + " não encontrado.");
        }
    }

    public void relatorioVendaBezerros(LocalDate dataAtual) {
        int prontos = 0;
        int espera = 0;

        System.out.println("\n--- RELATÓRIO DE VENDA DE BEZERROS ---");
        for (int i = 0; i < this.vetorBezerro.length; i++) {
            Bezerro b = this.vetorBezerro[i];

            //Avalia apenas se o bezerro existir no indice
            if (b != null) {
                //fêmeas são mantidas na fazenda
                if (b.getSexo() == 'M' || b.getSexo() == 'm') {

                    LocalDate liberadoParaVenda = b.getDataNascimentoB().plusMonths(6);

                    if (dataAtual.isBefore(liberadoParaVenda)) {
                        espera++; //Ainda não tem 6 meses
                    } else {
                        prontos++; //Já tem 6 meses ou mais
                    }
                }
            }
        }

        System.out.println("Bezerros machos PRONTOS para venda: " + prontos);
        System.out.println("Bezerros machos EM ESPERA: " + espera);
        System.out.println("--------------------------------------\n");
    }

    //Atualizar a saúde
    public void alterarSaudeBezerro(int numProv, String novaSaude) {
        Bezerro b = buscarBezerroPorNumProv(numProv);
        if (b != null) {
            b.atualizarSaude(novaSaude);
            System.out.println("Saúde do bezerro " + numProv + " atualizada com sucesso");
        } else {
            System.out.println("Bezerro com o número " + numProv + " não encontrado.");
        }
    }

    //Registrar óbito
    public void registrarFalecimentoBezerro(int numProv) {
        Bezerro b = buscarBezerroPorNumProv(numProv);
        if (b != null) {
            b.marcarComoFalecido();
        } else {
            System.out.println("Bezerro com o número " + numProv + " não encontrado.");
        }
    }

    //Aplicar vacina
    public void vacinarBezerro(int numProv, String nomeVacina) {
        Bezerro b = buscarBezerroPorNumProv(numProv);
        if (b != null) {
            b.adicionarVacina(nomeVacina);
            System.out.println("Vacina '" + nomeVacina + "' registrada para o bezerro " + numProv + "");
        } else {
            System.out.println("Bezerro com o número " + numProv + " não encontrado.");
        }
    }

}
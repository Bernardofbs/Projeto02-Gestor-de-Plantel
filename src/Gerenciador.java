import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Gerenciador {

    private Vaca[] vetorVacas;
    private Bezerro[] vetorBezerro;
    //Declarei os vetores 'null' para serem instanciados e preenchidos com os dados do arquivo

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

    public void QuantidadesVacasNoCiclo(LocalDate dataAtual) {

        int quantProduzindo = 0;
        int quantDescanso = 0;

        //this.vetorVacas.length para saber o limite do laço
        for (int i = 0; i < this.vetorVacas.length; i++) {

            Vaca vacaAtual = this.vetorVacas[i];

            //Proteção contra indices vazias
            if (vacaAtual != null && vacaAtual.isregistroAtivoVaca()) {

                String estado = vacaAtual.verificarCicloAtual(dataAtual);

                if (estado.equals("Em descanso")) {
                    quantDescanso++;
                } else if (estado.equals("Produzindo leite")) {
                    quantProduzindo++;
                }
            }
        }

        // Imprimir o resultado
        System.out.println("Vacas em descanso: " + quantDescanso);
        System.out.println("Vacas produzindo: " + quantProduzindo);

    }
}




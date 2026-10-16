import java.util.Scanner;
import java.time.LocalDate;

public class Menu {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        // Cria a instância do Gerenciador
        Gerenciador fazenda = new Gerenciador();

        System.out.println("Iniciando a leitura do arquivo...");
        fazenda.lerArquivo("arquivo_plantel.txt");
        System.out.println("Leitura do arquivo finalizada com sucesso");

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== SISTEMA DE GESTÃO PLANTEL ===");
            System.out.println("1  - Listar Vacas Cadastradas");
            System.out.println("2  - Listar Bezerros Cadastrados");
            System.out.println("3  - Ordenar Vacas por Número do Brinco");
            System.out.println("4  - Ordenar Vacas por Produção Média");
            System.out.println("5  - Ordenar Bezerros por Número Provisório");
            System.out.println("6  - Ordenar Bezerros por Data de Nascimento");
            System.out.println("7  - Relatório: Vacas em Produção / Descanso / Amamentando / Pronta para a inseminação");
            System.out.println("8  - Consultar Ciclo e Inseminação de uma Vaca");
            System.out.println("9  - Atualizar Estado de Saúde de uma Vaca");
            System.out.println("10 - Consultar Status de um Bezerro (Desmame e Venda)");
            System.out.println("11 - Relatório: Bezerros Prontos para Venda / Em Espera");
            System.out.println("12 - Atualizar Estado de Saúde de um Bezerro");
            System.out.println("13 - Registrar Vacina para um Bezerro");
            System.out.println("14 - Registrar Falecimento de um Bezerro");
            System.out.println("15 - Registrar Falecimento de uma Vaca");
            System.out.println("0  - Salvar Dados e Sair");
            System.out.print("Escolha uma opção: ");

            opcao = teclado.nextInt();

            switch (opcao) {
                case 1:
                    fazenda.listarVacas();
                    break;
                case 2:
                    fazenda.listarBezerros();
                    break;
                case 3:
                    fazenda.ordenarVacasPorBrinco();
                    fazenda.listarVacas();
                    break;
                case 4:
                    fazenda.ordenarVacasPorProducao();
                    fazenda.listarVacas();
                    break;
                case 5:
                    fazenda.ordenarBezerrosPorNumeroProvisorio();
                    fazenda.listarBezerros();
                    break;
                case 6:
                    fazenda.ordenarBezerrosPorNascimento();
                    fazenda.listarBezerros();
                    break;
                case 7:
                    fazenda.QuantidadesVacasNoCiclo(LocalDate.now());
                    break;
                case 8:
                    System.out.print("Digite o número do brinco da vaca: ");
                    int brincoConsulta = teclado.nextInt();
                    fazenda.consultarCicloVaca(brincoConsulta);
                    break;
                case 9:
                    System.out.print("Digite o número do brinco da vaca: ");
                    int brincoSaude = teclado.nextInt();
                    teclado.nextLine(); // Consome a quebra de linha
                    System.out.print("Digite o novo estado (Saudável, Em Descanso ou Doente): ");
                    String novaSaudeVaca = teclado.nextLine();
                    fazenda.alterarSaudeVaca(brincoSaude, novaSaudeVaca);
                    break;
                case 10:
                    System.out.print("Digite o número provisório do bezerro: ");
                    int provStatus = teclado.nextInt();
                    fazenda.consultarStatusBezerro(provStatus);
                    break;
                case 11:
                    fazenda.relatorioVendaBezerros(LocalDate.now());
                    break;
                case 12:
                    System.out.print("Digite o número provisório do bezerro: ");
                    int provSaude = teclado.nextInt();
                    teclado.nextLine(); // Consome a quebra de linha
                    System.out.print("Digite o novo estado (Saudável ou Doente): ");
                    String novaSaudeBezerro = teclado.nextLine();
                    fazenda.alterarSaudeBezerro(provSaude, novaSaudeBezerro);
                    break;
                case 13:
                    System.out.print("Digite o número provisório do bezerro: ");
                    int provVacina = teclado.nextInt();
                    teclado.nextLine(); // Consome a quebra de linha
                    System.out.print("Digite o nome da vacina (ex: Febre Aftosa): ");
                    String nomeVacina = teclado.nextLine();
                    fazenda.vacinarBezerro(provVacina, nomeVacina);
                    break;
                case 14:
                    System.out.print("Digite o número provisório do bezerro que faleceu: ");
                    int provObito = teclado.nextInt();
                    fazenda.registrarFalecimentoBezerro(provObito);
                    break;
                case 15:
                    System.out.print("Digite o número do brinco da vaca que faleceu: ");
                    int brincoObito = teclado.nextInt();
                    fazenda.registrarFalecimentoVaca(brincoObito);
                    break;
                case 0:
                    System.out.println("Salvando informações...");
                    fazenda.salvarArquivo("arquivo_plantel.txt");
                    System.out.println("Sistema encerrado com sucesso.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
        teclado.close();
    }
}
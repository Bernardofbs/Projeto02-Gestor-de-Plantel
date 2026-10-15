public class Menu {
    public static void main(String[] args) {

        //Cria a instância do Gerenciador
        Gerenciador fazenda = new Gerenciador();

        System.out.println("Iniciando o sistema...");

        fazenda.lerArquivo("arquivo_plantel.txt");

        //Se o código chegar até aqui sem erros, a leitura funcionou
        System.out.println("Leitura do arquivo finalizada com sucesso");
    }
}
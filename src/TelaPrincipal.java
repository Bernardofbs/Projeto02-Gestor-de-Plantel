import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

public class TelaPrincipal extends JFrame {

    // O IntelliJ criou estas variáveis automaticamente
    private JPanel painelPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel abaVacas;
    private JPanel abaBezerros;
    private JTable tabelaV;
    private JTable tabelaB;
    private JScrollPane tabelaVacas;
    private JScrollPane tabelaBezerros;

    // Adicionamos o Gerenciador
    private Gerenciador fazenda;

    public TelaPrincipal() {
        // 1. Configurações Básicas da Janela
        setContentPane(painelPrincipal);
        setTitle("Sistema de Gestão de Plantel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600); // Tamanho da janela
        setLocationRelativeTo(null); // Centra a janela no ecrã

        // 2. Inicializar o sistema lógico e ler ficheiro
        fazenda = new Gerenciador();
        fazenda.lerArquivo("arquivo_plantel.txt"); // Garante que o .txt está na pasta raiz do projeto

        // 3. Preencher as tabelas
        carregarTabelaVacas();
        carregarTabelaBezerros();
    }

    private void carregarTabelaVacas() {
        // Define o nome das colunas
        String[] colunas = {"Brinco", "Raça", "Nasc.", "Gestações", "Últ. Parto", "Produção", "Saúde"};

        // O DefaultTableModel é o tradutor de dados para a JTable visual
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Percorre o vetor de vacas e preenche as linhas da tabela
        for (Vaca v : fazenda.getVetorVacas()) {
            if (v != null && v.isRegistroAtivoVaca()) {
                Object[] linha = {
                        v.getNumBrinco(),
                        v.getRaca(),
                        v.getDataNascimentoV().format(formatoBR),
                        v.getGestacaoSucedida(),
                        v.getDataUltimoParto().format(formatoBR),
                        v.getProducaoMediaDia(),
                        v.getEstadoDeSaude()
                };
                modelo.addRow(linha); // Adiciona a linha ao modelo
            }
        }

        // Aplica os dados à tabela visual
        tabelaV.setModel(modelo);
    }

    private void carregarTabelaBezerros() {
        // Define o nome das colunas para Bezerros
        String[] colunas = {"Nº Prov", "Mãe", "Pai", "Nascimento", "Sexo", "Saúde"};

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Percorre o vetor de bezerros e preenche as linhas
        for (Bezerro b : fazenda.getVetorBezerros()) {
            if (b != null && b.isRegistroAtivoBezerro()) {
                Object[] linha = {
                        b.getNumProv(),
                        b.getNumMae(),
                        b.getNumPai(),
                        b.getDataNascimentoB().format(formatoBR),
                        b.getSexo(),
                        b.getEstadoDeSaude()
                };
                modelo.addRow(linha);
            }
        }

        // Aplica os dados na tabela visual de bezerros
        tabelaB.setModel(modelo);
    }

    public static void main(String[] args) {
        // Inicia a Interface Gráfica
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true); // Torna a janela visível
        });
    }
}
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class TelaPrincipal extends JFrame {

    // O IntelliJ criou estas variáveis automaticamente
    private JPanel painelPrincipal;
    private JTabbedPane painel1;
    private JPanel abaVacas;
    private JPanel abaBezerros;
    private JTable tabelaV;
    private JTable tabelaB;
    private JScrollPane tabelaVacas;
    private JScrollPane tabelaBezerros;
    private JPanel painelBotoes;
    private JButton ordenarPlantelButton;
    private JButton removerAnimalButton;
    private JButton salvarESairButton;

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
        ordenarPlantelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama a lógica de dupla ordenação Gerenciador
                fazenda.ordenarVacasPorSaudeEProducao();
                fazenda.ordenarBezerrosPorNascimento();

                // Atualiza a interface gráfica para mostrar os dados já ordenados
                carregarTabelaVacas();
                carregarTabelaBezerros();

                // Mostra um aviso ao usuario a confirmar a ação
                JOptionPane.showMessageDialog(null, "Plantel ordenado com sucesso");
            }
        });
        removerAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int abaSelecionada =  painel1.getSelectedIndex(); // 0 = Vacas, 1 = Bezerros

                if (abaSelecionada == 0) { // Aba das Vacas
                    int linhaSelecionada = tabelaV.getSelectedRow();
                    if (linhaSelecionada == -1) {
                        JOptionPane.showMessageDialog(null, "Por favor, selecione uma vaca na tabela para remover.");
                        return;
                    }

                    int numBrinco = Integer.parseInt(tabelaV.getValueAt(linhaSelecionada, 0).toString());
                    int confirmacao = JOptionPane.showConfirmDialog(null,
                            "Tem certeza que deseja registrar o óbito/remoção da vaca nº " + numBrinco + "?",
                            "Confirmar Remoção Lógica", JOptionPane.YES_NO_OPTION);

                    if (confirmacao == JOptionPane.YES_OPTION) {
                        fazenda.registrarFalecimentoVaca(numBrinco);
                        carregarTabelaVacas(); // Recarrega a tabela (a vaca inativa desaparecerá)
                        JOptionPane.showMessageDialog(null, "Registro inativado com sucesso!");
                    }

                } else if (abaSelecionada == 1) { // Aba dos Bezerros
                    int linhaSelecionada = tabelaB.getSelectedRow();
                    if (linhaSelecionada == -1) {
                        JOptionPane.showMessageDialog(null, "Por favor, selecione um bezerro na tabela para remover.");
                        return;
                    }

                    int numProv = Integer.parseInt(tabelaB.getValueAt(linhaSelecionada, 0).toString());
                    int confirmacao = JOptionPane.showConfirmDialog(null,
                            "Tem certeza que deseja registrar o óbito/remoção do bezerro prov. " + numProv + "?",
                            "Confirmar Remoção Lógica", JOptionPane.YES_NO_OPTION);

                    if (confirmacao == JOptionPane.YES_OPTION) {
                        fazenda.registrarFalecimentoBezerro(numProv);
                        carregarTabelaBezerros(); // Recarrega a tabela
                        JOptionPane.showMessageDialog(null, "Registro inativado com sucesso!");
                    }
                }
            }
        });
        salvarESairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarESairButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fazenda.salvarArquivo("arquivo_plantel.txt");
                        JOptionPane.showMessageDialog(null, "Alterações salvas com sucesso em arquivo_plantel.txt!");
                        System.exit(0); // Encerra o programa
                    }
                });
            }
        });
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
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaPrincipal extends JFrame {

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
    private JButton editarCampoButton;

    private Gerenciador fazenda;

    public TelaPrincipal() {
        // 1. Configurações Básicas da Janela
        setContentPane(painelPrincipal);
        setTitle("Sistema de Gestão de Plantel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // 2. Inicializar o sistema lógico e ler ficheiro
        fazenda = new Gerenciador();
        fazenda.lerArquivo("arquivo_plantel.txt");

        // 3. Preencher as tabelas na inicialização
        carregarTabelaVacas();
        carregarTabelaBezerros();

        // Listener Ordenar Plantel
        ordenarPlantelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazenda.ordenarVacasPorSaudeEProducao();
                fazenda.ordenarBezerrosPorNascimento();

                carregarTabelaVacas();
                carregarTabelaBezerros();

                JOptionPane.showMessageDialog(null, "Plantel ordenado com sucesso!");
            }
        });

        // Listener Remover Animal
        removerAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int abaSelecionada = painel1.getSelectedIndex(); // 0 = Vacas, 1 = Bezerros

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
                        carregarTabelaVacas();
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
                        carregarTabelaBezerros();
                        JOptionPane.showMessageDialog(null, "Registro inativado com sucesso!");
                    }
                }
            }
        });

        // Listener Salvar e Sair
        salvarESairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazenda.salvarArquivo("arquivo_plantel.txt");
                JOptionPane.showMessageDialog(null, "Alterações salvas com sucesso em arquivo_plantel.txt!");
                System.exit(0);
            }
        });

        // Edita exatamente a coluna que o utilizador selecionou na tabela
        editarCampoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int abaSelecionada = painel1.getSelectedIndex(); // 0 = Vacas, 1 = Bezerros
                DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                if (abaSelecionada == 0) { // Aba das Vacas
                    int linha = tabelaV.getSelectedRow();
                    int coluna = tabelaV.getSelectedColumn();

                    if (linha == -1 || coluna == -1) {
                        JOptionPane.showMessageDialog(null, "Por favor, clique numa célula da tabela de Vacas para escolher o campo que deseja editar.");
                        return;
                    }

                    int numBrinco = Integer.parseInt(tabelaV.getValueAt(linha, 0).toString());
                    Vaca v = fazenda.buscarVacaPorBrinco(numBrinco);

                    if (v != null) {
                        try {
                            switch (coluna) {
                                case 0: // Brinco
                                    String novoBrincoStr = JOptionPane.showInputDialog(null, "Novo Número de Brinco:", v.getNumBrinco());
                                    if (novoBrincoStr != null && !novoBrincoStr.trim().isEmpty()) {
                                        v.setNumBrinco(Integer.parseInt(novoBrincoStr.trim()));
                                    }
                                    break;
                                case 1: // Raça
                                    String novaRaca = JOptionPane.showInputDialog(null, "Nova Raça:", v.getRaca());
                                    if (novaRaca != null && !novaRaca.trim().isEmpty()) {
                                        v.setRaca(novaRaca.trim());
                                    }
                                    break;
                                case 2: // Nascimento
                                    String dataNascStr = JOptionPane.showInputDialog(null, "Nova Data de Nascimento (dd/MM/yyyy):", v.getDataNascimentoV().format(formatoBR));
                                    if (dataNascStr != null && !dataNascStr.trim().isEmpty()) {
                                        v.setDataNascimentoV(LocalDate.parse(dataNascStr.trim(), formatoBR));
                                    }
                                    break;
                                case 3: // Gestações
                                    String gestStr = JOptionPane.showInputDialog(null, "Número de Gestações:", v.getGestacaoSucedida());
                                    if (gestStr != null && !gestStr.trim().isEmpty()) {
                                        v.setGestacaoSucedida(Integer.parseInt(gestStr.trim()));
                                    }
                                    break;
                                case 4: // Último Parto
                                    String dataPartoStr = JOptionPane.showInputDialog(null, "Nova Data do Último Parto (dd/MM/yyyy):", v.getDataUltimoParto().format(formatoBR));
                                    if (dataPartoStr != null && !dataPartoStr.trim().isEmpty()) {
                                        v.setDataUltimoParto(LocalDate.parse(dataPartoStr.trim(), formatoBR));
                                    }
                                    break;
                                case 5: // Produção
                                    String prodStr = JOptionPane.showInputDialog(null, "Nova Produção Média (L/dia):", v.getProducaoMediaDia());
                                    if (prodStr != null && !prodStr.trim().isEmpty()) {
                                        v.setProducaoMediaDia(Double.parseDouble(prodStr.trim()));
                                    }
                                    break;
                                case 6: // Saúde
                                    String novaSaude = JOptionPane.showInputDialog(null, "Novo Estado de Saúde:", v.getEstadoDeSaude());
                                    if (novaSaude != null && !novaSaude.trim().isEmpty()) {
                                        v.setEstadoDeSaude(novaSaude.trim());
                                    }
                                    break;
                            }
                            carregarTabelaVacas();
                            JOptionPane.showMessageDialog(null, "Campo da vaca atualizado com sucesso!");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao atualizar campo: Formato do valor digitado é inválido.");
                        }
                    }

                } else if (abaSelecionada == 1) { // Aba dos Bezerros
                    int linha = tabelaB.getSelectedRow();
                    int coluna = tabelaB.getSelectedColumn();

                    if (linha == -1 || coluna == -1) {
                        JOptionPane.showMessageDialog(null, "Por favor, clique numa célula da tabela de Bezerros para escolher o campo que deseja editar.");
                        return;
                    }

                    int numProv = Integer.parseInt(tabelaB.getValueAt(linha, 0).toString());
                    Bezerro b = fazenda.buscarBezerroPorNumProv(numProv);

                    if (b != null) {
                        try {
                            switch (coluna) {
                                case 0: // Nº Prov
                                    String novoProvStr = JOptionPane.showInputDialog(null, "Novo Número Provisório:", b.getNumProv());
                                    if (novoProvStr != null && !novoProvStr.trim().isEmpty()) {
                                        b.setNumProv(Integer.parseInt(novoProvStr.trim()));
                                    }
                                    break;
                                case 1: // Mãe
                                    String novaMaeStr = JOptionPane.showInputDialog(null, "Novo Brinco da Mãe:", b.getNumMae());
                                    if (novaMaeStr != null && !novaMaeStr.trim().isEmpty()) {
                                        b.setNumMae(Integer.parseInt(novaMaeStr.trim()));
                                    }
                                    break;
                                case 2: // Pai
                                    String novoPaiStr = JOptionPane.showInputDialog(null, "Novo Brinco do Pai:", b.getNumPai());
                                    if (novoPaiStr != null && !novoPaiStr.trim().isEmpty()) {
                                        b.setNumPai(Integer.parseInt(novoPaiStr.trim()));
                                    }
                                    break;
                                case 3: // Nascimento
                                    String dataNascStr = JOptionPane.showInputDialog(null, "Nova Data de Nascimento (dd/MM/yyyy):", b.getDataNascimentoB().format(formatoBR));
                                    if (dataNascStr != null && !dataNascStr.trim().isEmpty()) {
                                        b.setDataNascimentoB(LocalDate.parse(dataNascStr.trim(), formatoBR));
                                    }
                                    break;
                                case 4: // Sexo
                                    String sexoStr = JOptionPane.showInputDialog(null, "Novo Sexo (M/F):", b.getSexo());
                                    if (sexoStr != null && !sexoStr.trim().isEmpty()) {
                                        b.setSexo(sexoStr.trim().toUpperCase().charAt(0));
                                    }
                                    break;
                                case 5: // Saúde
                                    String novaSaude = JOptionPane.showInputDialog(null, "Novo Estado de Saúde:", b.getEstadoDeSaude());
                                    if (novaSaude != null && !novaSaude.trim().isEmpty()) {
                                        b.setEstadoDeSaude(novaSaude.trim());
                                    }
                                    break;
                                case 6: // Vacinas
                                    String novaVacina = JOptionPane.showInputDialog(null, "Adicionar Nova Vacina ao Bezerro:");
                                    if (novaVacina != null && !novaVacina.trim().isEmpty()) {
                                        b.adicionarVacina(novaVacina.trim());
                                    }
                                    break;
                            }
                            carregarTabelaBezerros();
                            JOptionPane.showMessageDialog(null, "Campo do bezerro atualizado");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao atualizar campo: Formato do valor digitado é inválido.");
                        }
                    }
                }
            }
        });
    }

    private void carregarTabelaVacas() {
        String[] colunas = {"Brinco", "Raça", "Nasc.", "Gestações", "Últ. Parto", "Produção", "Saúde"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
                modelo.addRow(linha);
            }
        }
        tabelaV.setModel(modelo);
    }

    private void carregarTabelaBezerros() {
        String[] colunas = {"Nº Prov", "Mãe", "Pai", "Nascimento", "Sexo", "Saúde", "Vacinas"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (!fazenda.temBezerrosCadastrados()) {
            tabelaB.setModel(modelo);
            return;
        }

        for (Bezerro b : fazenda.getVetorBezerros()) {
            if (b != null && b.isRegistroAtivoBezerro()) {
                Object[] linha = {
                        b.getNumProv(),
                        b.getNumMae(),
                        b.getNumPai(),
                        b.getDataNascimentoB().format(formatoBR),
                        b.getSexo(),
                        b.getEstadoDeSaude(),
                        b.getVacinasFormatadas()
                };
                modelo.addRow(linha);
            }
        }
        tabelaB.setModel(modelo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
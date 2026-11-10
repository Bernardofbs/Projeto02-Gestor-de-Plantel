import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bezerro {

    private int numPai;
    private int numMae;
    private int numProv;
    private LocalDate dataNascimentoB;
    private char sexo;
    private String estadoDeSaude;
    private String[] listaVacinas = new String[20];
    private int contadorVacinas = 0;
    private boolean registroAtivoBezerro = true;

    public Bezerro(int numPai,
                   int numMae,
                   int numProv,
                   LocalDate dataNascimentoB,
                   char sexo,
                   String estadoDeSaude
    ) {

        this.numPai = numPai;
        this.numMae = numMae;
        this.numProv = numProv;
        this.dataNascimentoB = dataNascimentoB;
        this.sexo = sexo;
        this.estadoDeSaude = estadoDeSaude;
        // Garante que os construtores inicializem a lista de vacinas caso venha nula:
        if (this.listaVacinas == null) {
            this.listaVacinas = new String[10];
            this.contadorVacinas = 0;
        }
    }

    public String verificarDesmame(LocalDate dataAtual) {

        LocalDate desmameVerificado = dataNascimentoB.plusDays(60);

        if (dataAtual.isBefore(desmameVerificado)) {
            return "Bezerro ainda está mamando";
        } else {
            return "Bezerro está pronto para desmamar";
        }

    }

    public String liberarVenda(LocalDate dataAtual) {

        if (sexo == 'F' || sexo == 'f') {
            return "É uma fêmea. Não pode ser vendida.";
        }

        LocalDate liberadoParaVenda = dataNascimentoB.plusMonths(6);

        if (dataAtual.isBefore(liberadoParaVenda)) {
            return "Bezerro é macho, mas não está disponível para venda ainda.";
        } else {
            return "Bezerro é macho e pode ser vendido.";
        }
    }

    public void atualizarSaude(String novoEstadoDeSaude) {

        if (novoEstadoDeSaude.equalsIgnoreCase("Saudável") ||
                novoEstadoDeSaude.equalsIgnoreCase("Doente")) {

            this.estadoDeSaude = novoEstadoDeSaude;
        } else {
            System.out.print("Estado de saúde inválido. Tente 'Saudável', ou 'Doente'.");
        }
    }

    public void marcarComoFalecido() {
        this.registroAtivoBezerro = false;
        System.out.println("Registro alterado para Inativo (Falecido).");
    }

    public void adicionarVacina(String nomeVacina) {

        if (contadorVacinas < listaVacinas.length) {

            listaVacinas[contadorVacinas] = nomeVacina;

            contadorVacinas++;

        } else {
            System.out.println("Erro: O cartão de vacinas deste bezerro está cheio!");
        }
    }

    public int getNumPai() {
        return numPai;
    }

    public int getNumMae() {
        return numMae;
    }

    public int getNumProv() {
        return numProv;
    }

    public LocalDate getDataNascimentoB() {
        return dataNascimentoB;
    }

    public char getSexo() {
        return sexo;
    }

    public String getEstadoDeSaude() {
        return estadoDeSaude;
    }

    public String[] getListaVacinas() {
        return listaVacinas;
    }

    //adiciona getter isRegistroAtivoBezerro para controle de remocao logica
    public boolean isRegistroAtivoBezerro() {
        return registroAtivoBezerro;
    }

    //adiciona setters para permitir edicao de dados

    public void setNumProv(int numProv) {
        this.numProv = numProv;
    }

    public void setNumPai(int numPai) {
        this.numPai = numPai;
    }

    public void setNumMae(int numMae) {
        this.numMae = numMae;
    }

    public void setDataNascimentoB(LocalDate dataNascimentoB) {
        this.dataNascimentoB = dataNascimentoB;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setEstadoDeSaude(String estadoDeSaude) { this.estadoDeSaude = estadoDeSaude; }


    //Correção 1: Adicionar método para editar a lista de vacinas
    public String getVacinasFormatadas() {
        if (contadorVacinas == 0 || listaVacinas == null) {
            return "Nenhuma";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contadorVacinas; i++) {
            if (listaVacinas[i] != null && !listaVacinas[i].trim().isEmpty()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(listaVacinas[i]);
            }
        }
        return sb.length() > 0 ? sb.toString() : "Nenhuma";
    }
}
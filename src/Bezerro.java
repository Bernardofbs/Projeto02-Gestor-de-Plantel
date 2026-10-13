import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bezerro {

    private int numPai;
    private int numMae;
    private int numProv;
    private LocalDateTime dataNascimentoB;
    private char sexo;
    private String estadoDeSaude;
    private String[] listaVacinas = new String[20];
    private int contadorVacinas = 0;
    private boolean registroAtivoBezerro = true;

    public Bezerro (int numPai,
                    int numMae,
                    int numProv,
                    LocalDateTime dataNascimentoB,
                    char sexo,
                    String estadoDeSaude
    ){

        this.numPai = numPai;
        this.numMae = numMae;
        this.numProv = numProv;
        this.dataNascimentoB = dataNascimentoB;
        this.sexo = sexo;
        this.estadoDeSaude = estadoDeSaude;
    }

    public String verificarDesmame(LocalDateTime dataAtual){

    LocalDateTime desmameVerificado = dataNascimentoB.plusDays(60);

    if (dataAtual.isBefore(desmameVerificado)){
        return "Bezerro ainda está mamando";
    } else {
        return "Bezerro está pronto para desmamar";
    }

    }

    public String liberarVenda(LocalDateTime dataAtual) {

        if (sexo == 'F' || sexo == 'f') {
            return "É uma fêmea. Não pode ser vendida.";
        }

        LocalDateTime liberadoParaVenda = dataNascimentoB.plusMonths(6);

        if (dataAtual.isBefore(liberadoParaVenda)) {
            return "Bezerro é macho, mas não está disponível para venda ainda.";
        } else {
            return "Bezerro é macho e pode ser vendido.";
        }
    }
    public void atualizarSaude (String novoEstadoDeSaude) {

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

}
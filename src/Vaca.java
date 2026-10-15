import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vaca {

    private int numBrinco;
    private String raca;
    private LocalDate dataNascimentoV;
    private int gestacaoSucedida;
    private LocalDate dataUltimoParto;
    private double producaoMediaDia;
    private String estadoDeSaude;
    private boolean registroAtivoVaca = true;
    private Object is;

    public Vaca(int numBrinco,
                String raca, LocalDate dataNascimento,
                int gestacoes, LocalDate dataUltimoParto, double producaoMedia, String estadoDeSaude
    ) {

        this.numBrinco = numBrinco;
        this.raca = this.raca;
        this.dataNascimentoV = dataNascimentoV;
        this.gestacaoSucedida = gestacaoSucedida;
        this.dataUltimoParto = this.dataUltimoParto;
        this.producaoMediaDia = producaoMediaDia;
        this.estadoDeSaude = estadoDeSaude;
    }

    public String verificarCicloAtual(LocalDate dataAtual) {

        LocalDate inicioProducaoLeite = dataUltimoParto.plusDays(60);
        LocalDate inicioDescanso = inicioProducaoLeite.plusMonths(10);
        LocalDate inseminacaoLiberada = inicioDescanso.plusMonths(2);

        if (dataAtual.isBefore(inicioProducaoLeite)) {
            return "Amamentando";

        } else if (dataAtual.isBefore(inicioDescanso)) {
            return "Produzindo leite";

        } else if (dataAtual.isBefore(inseminacaoLiberada)) {
            return "Em descanso";

        } else {
            return "Pronta para a inseminação";
        }
    }

    public LocalDate DataNovaInseminacao(LocalDate dataAtual) {

        LocalDate inicioProducaoLeite = dataUltimoParto.plusDays(60);
        LocalDate inicioDescanso = inicioProducaoLeite.plusMonths(10);
        LocalDate inseminacaoLiberada = inicioDescanso.plusMonths(2);

        if (dataAtual.isBefore(inseminacaoLiberada)) {

            DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = inseminacaoLiberada.format(formatoBR);

            return LocalDate.parse("Não está pronta. Pronta para a inseminação em: " + dataFormatada);

        } else {
            return dataUltimoParto.plusDays(60).plusMonths(12);
        }
    }

    public void atualizarSaude (String novoEstadoDeSaude) {

        if (novoEstadoDeSaude.equalsIgnoreCase("Saudável") ||
                novoEstadoDeSaude.equalsIgnoreCase("Em Descanso") ||
                novoEstadoDeSaude.equalsIgnoreCase("Doente")) {

            this.estadoDeSaude = novoEstadoDeSaude;
        } else {
            System.out.print("Estado de saúde inválido. Tente 'Saudável', 'Em Descanso' ou 'Doente'.");
        }
    }
    public void marcarComoFalecido() {
        this.registroAtivoVaca = false;
        System.out.println("Registro alterado para Inativo (Falecido).");
    }

    public boolean isregistroAtivoVaca() {
        return this.registroAtivoVaca;
    }
}
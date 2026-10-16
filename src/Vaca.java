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
                String raca,
                LocalDate dataNascimentoV,
                int gestacaoSucedida,
                LocalDate dataUltimoParto,
                double producaoMediaDia,
                String estadoDeSaude)
    {

        this.numBrinco = numBrinco;
        this.raca = raca;
        this.dataNascimentoV = dataNascimentoV;
        this.gestacaoSucedida = gestacaoSucedida;
        this.dataUltimoParto = dataUltimoParto;
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

    public String DataNovaInseminacao(LocalDate dataAtual) {

        LocalDate inicioProducaoLeite = dataUltimoParto.plusDays(60);
        LocalDate inicioDescanso = inicioProducaoLeite.plusMonths(10);
        LocalDate inseminacaoLiberada = inicioDescanso.plusMonths(2);

        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (dataAtual.isBefore(inseminacaoLiberada)) {
            String dataFormatada = inseminacaoLiberada.format(formatoBR);
            return "Não está pronta. Pronta para a inseminação em: " + dataFormatada;
        } else {
            return "Já está pronta para a inseminação desde: " + inseminacaoLiberada.format(formatoBR);
        }
    }

    public void atualizarSaude(String novoEstadoDeSaude) {

        if (novoEstadoDeSaude.equalsIgnoreCase("Saudável") ||
                novoEstadoDeSaude.equalsIgnoreCase("Em Descanso") ||
                novoEstadoDeSaude.equalsIgnoreCase("Doente")) {

            this.estadoDeSaude = novoEstadoDeSaude;
        } else {
            System.out.print("Estado de saúde inválido. Tente 'Saudável', 'Em Descanso' ou 'Doente'.");
        }
    }

    public void marcarComoFalecida() {
        this.registroAtivoVaca = false;
        System.out.println("Registro alterado para Inativo (Falecido).");
    }

    public boolean isregistroAtivoVaca() {
        return this.registroAtivoVaca;
    }

    public int getNumBrinco() {
        return numBrinco;
    }

    public String getRaca() {
        return raca;
    }

    public LocalDate getDataNascimentoV() {
        return dataNascimentoV;
    }

    public int getGestacaoSucedida() {
        return gestacaoSucedida;
    }

    public LocalDate getDataUltimoParto() {
        return dataUltimoParto;
    }

    public double getProducaoMediaDia() {
        return producaoMediaDia;
    }

    public String getEstadoDeSaude() {
        return estadoDeSaude;
    }

    public boolean isRegistroAtivoVaca() {
        return registroAtivoVaca;
    }
}

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vaca {

    private int numBrinco;
    private String raca;
    private LocalDateTime dataNascimentoV;
    private int gestacaoSucedida;
    private LocalDateTime dataUltimoParto;
    private double producaoMediaDia;
    private String estadoDeSaude;
    private boolean registroAtivoVaca = true;
    private Object is;

    public Vaca(int numBrinco,
                String raca,
                LocalDateTime dataNascimento,
                int gestacaoSucedida,
                LocalDateTime dataUltimoParto,
                double producaoMediaDia, String estadoDeSaude,
                boolean registroAtivoVaca
    ) {

        this.numBrinco = numBrinco;
        this.raca = raca;
        this.dataNascimentoV = dataNascimentoV;
        this.gestacaoSucedida = gestacaoSucedida;
        this.dataUltimoParto = dataUltimoParto;
        this.producaoMediaDia = producaoMediaDia;
        this.estadoDeSaude = estadoDeSaude;
    }

    public String verificarCicloAtual(LocalDateTime dataAtual) {

        LocalDateTime inicioProducaoLeite = dataUltimoParto.plusDays(60);
        LocalDateTime inicioDescanso = inicioProducaoLeite.plusMonths(10);
        LocalDateTime inseminacaoLiberada = inicioDescanso.plusMonths(2);

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

    public LocalDateTime DataNovaInseminacao(LocalDateTime dataAtual) {

        LocalDateTime inicioProducaoLeite = dataUltimoParto.plusDays(60);
        LocalDateTime inicioDescanso = inicioProducaoLeite.plusMonths(10);
        LocalDateTime inseminacaoLiberada = inicioDescanso.plusMonths(2);

        if (dataAtual.isBefore(inseminacaoLiberada)) {

            DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = inseminacaoLiberada.format(formatoBR);

            return LocalDateTime.parse("Não está pronta. Pronta para a inseminação em: " + dataFormatada);

        } else {
            return dataUltimoParto.plusDays(60).plusMonths(12);
        }
    }

    public void QuantidadesVacasNoCiclo(LocalDateTime dataAtual) {

        int quantProduzindo = 0;
        int quantDescanso = 0;

        for (int i = 0; i < quantidadeMatrizesCadastradas; i++) {

            Vaca vacaAtual = vetorMatrizes[i];

            if (vacaAtual.is.registroAtivoVaca()) {
                String estado = vacaAtual.verificarCicloAtual(dataAtual);

                if (estado.equals("Em descanso")) {
                    quantDescanso++;
                } else if (estado.equals("Produzindo leite")) {
                    quantProduzindo++;
                }
            }
        }
    }
}

    public String AtualizarSaude (String estadoDeSaude){


}


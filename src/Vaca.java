import java.time.LocalDateTime;

public class Vaca {

    private int numBrinco;
    private String raca;
    private LocalDateTime dataNascimentoV;
    private int gestacaoSucedida;
    private LocalDateTime dataUltimoParto;
    private double producaoMediaDia;
    private String estadoDeSaude;
    private boolean registroAtivoVaca = true;

    public Vaca(int numBrinco,
                String raca,
                LocalDateTime dataNascimento,
                int gestacaoSucedida,
                LocalDateTime dataUltimoParto,
                double producaoMediaDia, String estadoDeSaude,
                boolean registroAtivoVaca
                ){

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

    public void DataNovaInseminacao (){

    }









}

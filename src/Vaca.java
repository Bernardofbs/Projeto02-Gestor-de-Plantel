import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vaca {

    private int numBrinco;
    private String raca;
    private LocalDateTime dataNascimento;
    private int gestacaoSucedida;
    private LocalDateTime dataUltimoParto;
    private double producaoMediaDia;
    private String estadoDeSaude;
    private boolean registroAtivoVaca = true;

    public Vaca(int numBrinco, String raca, LocalDateTime dataNascimento, int gestacaoSucedida,
                LocalDateTime dataUltimoParto, double producaoMediaDia, String estadoDeSaude,
                boolean registroAtivoVaca) {

        this.numBrinco = numBrinco;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.gestacaoSucedida = gestacaoSucedida;
        this.dataUltimoParto = dataUltimoParto;
        this.producaoMediaDia = producaoMediaDia;
        this.estadoDeSaude = estadoDeSaude;
    }

    public void calcularPartoDescanso (){
   }

    public void VerificarDozeMeses (){

    }









}

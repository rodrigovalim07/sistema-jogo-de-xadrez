package jogoTabuleiro;

public class ExceçaoTabuleiro extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExceçaoTabuleiro(String mensagem) {
		super(mensagem);
	}
}

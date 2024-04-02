package jogoTabuleiro;

public class Peça {

	protected Posiçao posicao;
	private Tabuleiro tabuleiro;
	
	public Peça(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
}

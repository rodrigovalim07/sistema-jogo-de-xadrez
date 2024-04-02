package xadrez;

import jogoTabuleiro.Peça;
import jogoTabuleiro.Tabuleiro;

public class PeçaXadrez extends Peça {

	private Cor cor;

	public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
}

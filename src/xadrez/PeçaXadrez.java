package xadrez;

import jogoTabuleiro.Peça;
import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;

public abstract class PeçaXadrez extends Peça {

	private Cor cor;

	public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	protected boolean existePeçaAdversaria(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posiçao);
		return p != null && p.getCor() != cor;
	}
}

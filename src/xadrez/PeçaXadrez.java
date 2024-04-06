package xadrez;

import jogoTabuleiro.Peça;
import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;

public abstract class PeçaXadrez extends Peça {

	private Cor cor;
	private int contagemMovimentos;

	public PeçaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContagemMovimentos() {
		return contagemMovimentos;
	}

	public void incrementarMovimento() {
		contagemMovimentos++;
	}
	
	public void decrementarMovimento() {
		contagemMovimentos--;
	}
	
	public PosiçaoXadrez getPosiçaoXadrez() {
		return PosiçaoXadrez.dePosiçao(posiçao);
	}
	
	protected boolean existePeçaAdversaria(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posiçao);
		return p != null && p.getCor() != cor;
	}
}

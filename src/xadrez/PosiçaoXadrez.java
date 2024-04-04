package xadrez;

import jogoTabuleiro.Posiçao;

public class PosiçaoXadrez {

	private char coluna;
	private int linha;
	
	public PosiçaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExceçaoXadrez("Erro ao instanciar a posição: valores válidos são de a1 até h8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Posiçao paraPosiçao() {
		return new Posiçao(8 - linha, coluna - 'a');
	}
	
	protected static PosiçaoXadrez dePosiçao(Posiçao posiçao) {
		return new PosiçaoXadrez((char) ('a' - posiçao.getColuna()), 8 - posiçao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}

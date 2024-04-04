package xadrez;

import jogoTabuleiro.ExceçaoTabuleiro;

public class ExceçaoXadrez extends ExceçaoTabuleiro {

	private static final long serialVersionUID = 1L;

	public ExceçaoXadrez(String mensagem) {
		super(mensagem);
	}
}

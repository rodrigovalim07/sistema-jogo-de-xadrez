package xadrez.peças;

import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posiçao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posiçao p = new Posiçao(0, 0);
		
		// acima
		p.setValores(posiçao.getLinha() - 1, posiçao.getColuna());
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// abaixo
		p.setValores(posiçao.getLinha() + 1, posiçao.getColuna());
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// esquerda
		p.setValores(posiçao.getLinha(), posiçao.getColuna() - 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// direita
		p.setValores(posiçao.getLinha(), posiçao.getColuna() + 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// noroeste
		p.setValores(posiçao.getLinha() - 1, posiçao.getColuna() - 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// nordeste
		p.setValores(posiçao.getLinha() - 1, posiçao.getColuna() + 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// sudoeste
		p.setValores(posiçao.getLinha() + 1, posiçao.getColuna() - 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// sudeste
		p.setValores(posiçao.getLinha() + 1, posiçao.getColuna() + 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}

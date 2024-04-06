package xadrez.peças;

import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Cavalo extends PeçaXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posiçao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posiçao p = new Posiçao(0, 0);
		
		p.setValores(posiçao.getLinha() - 1, posiçao.getColuna() - 2);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posiçao.getLinha() - 2, posiçao.getColuna() - 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posiçao.getLinha() - 2, posiçao.getColuna() + 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posiçao.getLinha() - 1, posiçao.getColuna() + 2);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posiçao.getLinha() + 1, posiçao.getColuna() + 2);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posiçao.getLinha() + 2, posiçao.getColuna() + 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posiçao.getLinha() + 2, posiçao.getColuna() - 1);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setValores(posiçao.getLinha() + 1, posiçao.getColuna() - 2);
		if (getTabuleiro().existePosiçao(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}

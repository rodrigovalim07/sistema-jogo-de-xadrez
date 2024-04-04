package xadrez.peças;

import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PeçaXadrez;

public class Torre extends PeçaXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		 
		Posiçao p = new Posiçao(0, 0);
		
		// acima
		p.setValores(posiçao.getLinha() - 1, posiçao.getColuna());
		while (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// esquerda
		p.setValores(posiçao.getLinha(), posiçao.getColuna() - 1);
		while (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// direita
		p.setValores(posiçao.getLinha(), posiçao.getColuna() + 1);
		while (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// abaixo
		p.setValores(posiçao.getLinha() + 1, posiçao.getColuna());
		while (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}

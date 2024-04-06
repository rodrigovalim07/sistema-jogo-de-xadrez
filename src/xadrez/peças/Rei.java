package xadrez.peças;

import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Rei extends PeçaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posiçao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeRoqueTorre(Posiçao posiçao) {
		PeçaXadrez p = (PeçaXadrez)getTabuleiro().peça(posiçao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContagemMovimentos() == 0;
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
		
		// movimento especial roque
		if (getContagemMovimentos() == 0 && !partidaXadrez.getCheck()) {
			// roque pequeno, lado do rei
			Posiçao posiçaoT1 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 3);
			if (testeRoqueTorre(posiçaoT1)) {
				Posiçao p1 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 1);
				Posiçao p2 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 2);
				if (getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null) {
					mat[posiçao.getLinha()][posiçao.getColuna() + 2] = true;
				}
			}
			// roque grande, lado da rainha
			Posiçao posiçaoT2 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 4);
			if (testeRoqueTorre(posiçaoT2)) {
				Posiçao p1 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 1);
				Posiçao p2 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 2);
				Posiçao p3 = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 3);
				if (getTabuleiro().peça(p1) == null && getTabuleiro().peça(p2) == null && getTabuleiro().peça(p3) == null) {
					mat[posiçao.getLinha()][posiçao.getColuna() - 2] = true;
				}
			}
			
		}
		
		return mat;
	}
}

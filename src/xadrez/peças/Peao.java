package xadrez.peças;

import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PeçaXadrez;

public class Peao extends PeçaXadrez {

	private PartidaXadrez partidaXadrez;
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		 
		Posiçao p = new Posiçao(0, 0);
		
		if (getCor() == Cor.BRANCA) {
			p.setValores(posiçao.getLinha() - 1, posiçao.getColuna());
			if (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posiçao.getLinha() - 2, posiçao.getColuna());
			Posiçao p2 = new Posiçao(posiçao.getLinha() - 1, posiçao.getColuna());
			if (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p) && getTabuleiro().existePosiçao(p2) && !getTabuleiro().temUmaPeça(p2) && getContagemMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posiçao.getLinha() - 1, posiçao.getColuna() - 1);
			if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posiçao.getLinha() - 1, posiçao.getColuna() + 1);
			if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// movimento especial en passant brancas
			if (posiçao.getLinha() == 3) {
				Posiçao esquerda = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 1);
				if (getTabuleiro().existePosiçao(esquerda) && existePeçaAdversaria(esquerda) && getTabuleiro().peça(esquerda) == partidaXadrez.getVulneravelAenPassant()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posiçao direita = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 1);
				if (getTabuleiro().existePosiçao(direita) && existePeçaAdversaria(direita) && getTabuleiro().peça(direita) == partidaXadrez.getVulneravelAenPassant()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}	
			}
		}
		else {
			p.setValores(posiçao.getLinha() + 1, posiçao.getColuna());
			if (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posiçao.getLinha() + 2, posiçao.getColuna());
			Posiçao p2 = new Posiçao(posiçao.getLinha() + 1, posiçao.getColuna());
			if (getTabuleiro().existePosiçao(p) && !getTabuleiro().temUmaPeça(p) && getTabuleiro().existePosiçao(p2) && !getTabuleiro().temUmaPeça(p2) && getContagemMovimentos() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posiçao.getLinha() + 1, posiçao.getColuna() - 1);
			if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posiçao.getLinha() + 1, posiçao.getColuna() + 1);
			if (getTabuleiro().existePosiçao(p) && existePeçaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// movimento especial en passant pretas
			if (posiçao.getLinha() == 4) {
				Posiçao esquerda = new Posiçao(posiçao.getLinha(), posiçao.getColuna() - 1);
				if (getTabuleiro().existePosiçao(esquerda) && existePeçaAdversaria(esquerda) && getTabuleiro().peça(esquerda) == partidaXadrez.getVulneravelAenPassant()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posiçao direita = new Posiçao(posiçao.getLinha(), posiçao.getColuna() + 1);
				if (getTabuleiro().existePosiçao(direita) && existePeçaAdversaria(direita) && getTabuleiro().peça(direita) == partidaXadrez.getVulneravelAenPassant()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
}

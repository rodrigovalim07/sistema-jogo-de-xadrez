package jogoTabuleiro;

public abstract class Peça {

	protected Posiçao posiçao;
	private Tabuleiro tabuleiro;
	
	public Peça(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentoPossivel(Posiçao posiçao) {
		return movimentosPossiveis()[posiçao.getLinha()][posiçao.getColuna()];
	}
	
	public boolean existeMovimentoPossivel() {
		boolean[][] mat = movimentosPossiveis();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}

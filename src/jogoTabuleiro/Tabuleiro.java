package jogoTabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peça[][] peças;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExceçaoTabuleiro("Erro ao criar o tabuleiro: é necessário haver ao menos 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		peças = new Peça[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peça peça(int linha, int coluna) {
		if (!existePosiçao(linha, coluna)) {
			throw new ExceçaoTabuleiro("Posição não se encontra no tabuleiro");
		}
		return peças[linha][coluna];
	}
	
	public Peça peça(Posiçao posiçao) {
		if (!existePosiçao(posiçao)) {
			throw new ExceçaoTabuleiro("Posição não se encontra no tabuleiro.");
		}
		return peças[posiçao.getLinha()][posiçao.getColuna()];
	}
	
	public void colocarPeça(Peça peça, Posiçao posiçao) {
		if (temUmaPeça(posiçao)) {
			throw new ExceçaoTabuleiro("Já existe uma peça na posição " + posiçao);
		}
		peças[posiçao.getLinha()][posiçao.getColuna()] = peça;
		peça.posiçao = posiçao;		
	}
	
	public Peça removerPeça(Posiçao posiçao) {
		if (!existePosiçao(posiçao)) {
			throw new ExceçaoTabuleiro("Posição não se encontra no tabuleiro.");
		}
		if (peça(posiçao) == null) {
			return null;
		}
		Peça auxiliar = peça(posiçao);
		auxiliar.posiçao = null;
		peças[posiçao.getLinha()][posiçao.getColuna()] = null;
		return auxiliar;
	}
	
	private boolean existePosiçao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean existePosiçao(Posiçao posiçao) {
		return existePosiçao(posiçao.getLinha(), posiçao.getColuna());
	}
	
	public boolean temUmaPeça(Posiçao posiçao) {
		if (!existePosiçao(posiçao)) {
			throw new ExceçaoTabuleiro("Posição não se encontra no tabuleiro.");
		}
		return peça(posiçao) != null;
	}
}

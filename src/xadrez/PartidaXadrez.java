package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogoTabuleiro.Peça;
import jogoTabuleiro.Posiçao;
import jogoTabuleiro.Tabuleiro;
import xadrez.peças.Bispo;
import xadrez.peças.Cavalo;
import xadrez.peças.Peao;
import xadrez.peças.Rainha;
import xadrez.peças.Rei;
import xadrez.peças.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PeçaXadrez vulneravelAenPassant;
	private PeçaXadrez promoçao;
	
	private List<Peça> peçasNoTabuleiro = new ArrayList<>();
	private List<Peça> peçasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCA;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate ;
	}
	
	public PeçaXadrez getVulneravelAenPassant() {
		return vulneravelAenPassant;
	}
	
	public PeçaXadrez getPromoçao() {
		return promoçao;
	}
	
	public PeçaXadrez[][] getPeças() {
		PeçaXadrez[][] mat = new PeçaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getLinhas(); j++) {
				mat[i][j] = (PeçaXadrez) tabuleiro.peça(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] movimentosPossiveis(PosiçaoXadrez posiçaoOrigem) {
		 Posiçao posiçao = posiçaoOrigem.paraPosiçao();
		 validarPosiçaoOrigem(posiçao);
		 return tabuleiro.peça(posiçao).movimentosPossiveis();
	}
	
	public PeçaXadrez executarMovimento(PosiçaoXadrez posiçaoOrigem, PosiçaoXadrez posiçaoDestino) {
		Posiçao origem = posiçaoOrigem.paraPosiçao();
		Posiçao destino = posiçaoDestino.paraPosiçao();
		validarPosiçaoOrigem(origem);
		validarPosiçaoDestino(origem, destino);
		Peça peçaCapturada = façaMover(origem, destino);
		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, peçaCapturada);
			throw new ExceçaoXadrez("Você não pode se colocar em check.");
		}
		
		PeçaXadrez peçaMovida = (PeçaXadrez)tabuleiro.peça(destino);
		
		// movimento especial promoção
		promoçao = null;
		if (peçaMovida instanceof Peao) {
			if (peçaMovida.getCor() == Cor.BRANCA && destino.getLinha() == 0 || peçaMovida.getCor() == Cor.PRETA && destino.getLinha() == 7) {
				promoçao = (PeçaXadrez)tabuleiro.peça(destino);
				promoçao = substituirPeçaPromovida("Q");
			}
		}
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
		
		// movimento especial en passant
		if (peçaMovida instanceof Peao && destino.getLinha() == origem.getLinha() -2 || destino.getLinha() == origem.getLinha() + 2) {
			vulneravelAenPassant = peçaMovida;
		}
		else {
			vulneravelAenPassant = null;
		}
		
		return (PeçaXadrez)peçaCapturada;
	}
	
	public PeçaXadrez substituirPeçaPromovida(String tipo) {
		if (promoçao == null) {
			throw new IllegalStateException("Não há peça para ser promovida.");
		}
		if (!tipo.equals("B") && !tipo.equals("T") && !tipo.equals("C") && !tipo.equals("Q")) {
			return promoçao;
		}
		
		Posiçao posiçao = promoçao.getPosiçaoXadrez().paraPosiçao();
		Peça p = tabuleiro.removerPeça(posiçao);
		peçasNoTabuleiro.remove(p);
		
		PeçaXadrez novaPeça = novaPeça(tipo, promoçao.getCor());
		tabuleiro.colocarPeça(novaPeça, posiçao);
		peçasNoTabuleiro.add(novaPeça);
		
		return novaPeça;
	}
	
	private PeçaXadrez novaPeça(String tipo, Cor cor) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if (tipo.equals("T")) return new Torre(tabuleiro, cor);
		return new Rainha(tabuleiro, cor);
	}
	
	private Peça façaMover(Posiçao origem, Posiçao destino) {
		PeçaXadrez p = (PeçaXadrez)tabuleiro.removerPeça(origem);
		p.incrementarMovimento();
		Peça peçaCapturada = tabuleiro.removerPeça(destino);
		tabuleiro.colocarPeça(p, destino);
		
		if (peçaCapturada != null) {
			peçasNoTabuleiro.remove(peçaCapturada);
			peçasCapturadas.add(peçaCapturada);
		}
		
		// movimento especial roque lado rei
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posiçao origemTorre = new Posiçao(origem.getLinha(), origem.getColuna() + 3);
			Posiçao destinoTorre = new Posiçao(origem.getLinha(), origem.getColuna() + 1);
			PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(origemTorre);
			tabuleiro.colocarPeça(torre, destinoTorre);
			torre.incrementarMovimento();
		}
		
		// movimento especial roque lado rainha
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posiçao origemTorre = new Posiçao(origem.getLinha(), origem.getColuna() - 4);
			Posiçao destinoTorre = new Posiçao(origem.getLinha(), origem.getColuna() - 1);
			PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(origemTorre);
			tabuleiro.colocarPeça(torre, destinoTorre);
			torre.incrementarMovimento();
		}
		
		// movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && peçaCapturada == null) {
				Posiçao peaoPosiçao;
				if (p.getCor() == Cor.BRANCA) {
					peaoPosiçao = new Posiçao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					peaoPosiçao = new Posiçao(destino.getLinha() - 1, destino.getColuna());
				}
				peçaCapturada = tabuleiro.removerPeça(peaoPosiçao);
				peçasCapturadas.add(peçaCapturada);
				peçasNoTabuleiro.remove(peçaCapturada);
			}
		}
		
		return peçaCapturada;
	}
	
	private void desfazerMovimento(Posiçao origem, Posiçao destino, Peça peçaCapturada) {
		PeçaXadrez p = (PeçaXadrez)tabuleiro.removerPeça(destino);
		p.decrementarMovimento();
		tabuleiro.colocarPeça(p, origem);
		
		if (peçaCapturada != null) {
			tabuleiro.colocarPeça(peçaCapturada, destino);
			peçasCapturadas.remove(peçaCapturada);
			peçasNoTabuleiro.add(peçaCapturada);
		}
		
		// movimento especial roque lado rei
		if (p instanceof Rei && destino.getColuna() == destino.getColuna() + 2) {
			Posiçao origemTorre = new Posiçao(origem.getLinha(), origem.getColuna() + 3);
			Posiçao destinoTorre = new Posiçao(origem.getLinha(), origem.getColuna() + 1);
			PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(destinoTorre);
			tabuleiro.colocarPeça(torre, origemTorre);
			torre.decrementarMovimento();
		}
		
		// movimento especial roque lado rainha
		if (p instanceof Rei && destino.getColuna() == destino.getColuna() - 2) {
			Posiçao origemTorre = new Posiçao(origem.getLinha(), origem.getColuna() - 4);
			Posiçao destinoTorre = new Posiçao(origem.getLinha(), origem.getColuna() - 1);
			PeçaXadrez torre = (PeçaXadrez)tabuleiro.removerPeça(destinoTorre);
			tabuleiro.colocarPeça(torre, origemTorre);
			torre.decrementarMovimento();
		}
		
		// movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && peçaCapturada == vulneravelAenPassant) {
				PeçaXadrez peao = (PeçaXadrez)tabuleiro.removerPeça(destino);
				Posiçao peaoPosiçao;
				if (p.getCor() == Cor.BRANCA) {
					peaoPosiçao = new Posiçao(3, destino.getColuna());
				}
				else {
					peaoPosiçao = new Posiçao(4, destino.getColuna());
				}
				tabuleiro.colocarPeça(peao, peaoPosiçao);
			}
		}
	}
	
	private void validarPosiçaoOrigem(Posiçao posiçao) {
		if (!tabuleiro.temUmaPeça(posiçao)) {
			throw new ExceçaoXadrez("Não há peça na posição.");
		}
		if (jogadorAtual != ((PeçaXadrez)tabuleiro.peça(posiçao)).getCor()) {
			throw new ExceçaoXadrez("A peça escolhida não é sua.");
		}
		if (!tabuleiro.peça(posiçao).existeMovimentoPossivel()) {
			throw new ExceçaoXadrez("Não existem movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validarPosiçaoDestino(Posiçao origem, Posiçao destino) {
		if (!tabuleiro.peça(origem).movimentoPossivel(destino)) {
			throw new ExceçaoXadrez("A peça escolhida não pode se mover para a posição de destino.");
		}
	}
	
	private void proximoTurno() {
		turno ++;
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private PeçaXadrez rei(Cor cor) {
		List<Peça> lista = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peça p : lista) {
			if (p instanceof Rei) {
				return (PeçaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe um rei da cor" + cor + "no tabuleiro");
	}
	
	private boolean testarCheck(Cor cor) {
		Posiçao posiçaoRei = rei(cor).getPosiçaoXadrez().paraPosiçao();
		List<Peça> peçasOponente = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peça p : peçasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posiçaoRei.getLinha()][posiçaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peça> lista = peçasNoTabuleiro.stream().filter(x -> ((PeçaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peça p : lista) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posiçao origem = ((PeçaXadrez)p).getPosiçaoXadrez().paraPosiçao();
						Posiçao destino = new Posiçao(i, j);
						Peça peçaCapturada = façaMover(origem, destino);
						boolean testarCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, peçaCapturada);
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocarNovaPeça(char coluna, int linha, PeçaXadrez peça) {
		tabuleiro.colocarPeça(peça, new PosiçaoXadrez(coluna, linha).paraPosiçao());
		peçasNoTabuleiro.add(peça);
	}
	
	private void setupInicial() {

		colocarNovaPeça('e', 1, new Rei(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('a', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		colocarNovaPeça('a', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('b', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('c', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('d', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('e', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('f', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('g', 2, new Peao(tabuleiro, Cor.BRANCA, this));
		colocarNovaPeça('h', 2, new Peao(tabuleiro, Cor.BRANCA, this));


		colocarNovaPeça('e', 8, new Rei(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('d', 8, new Rainha(tabuleiro, Cor.PRETA));
		colocarNovaPeça('a', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeça('h', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeça('c', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocarNovaPeça('f', 8, new Bispo(tabuleiro, Cor.PRETA));
		colocarNovaPeça('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocarNovaPeça('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
		colocarNovaPeça('a', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('b', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('c', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('d', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('e', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('f', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('g', 7, new Peao(tabuleiro, Cor.PRETA, this));
		colocarNovaPeça('h', 7, new Peao(tabuleiro, Cor.PRETA, this));
	}
}

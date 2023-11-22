package bowling;


public class Tour {

	protected int numero;
	protected int quillesAbattuesLancer1 = 0;
	protected int quillesAbattuesLancer2 = 0;
	protected int boulesLancees = 0;

	protected Tour suivant;
	
	public Tour() {
		// Vide pour l'héritage
	}

	public Tour(int number, Tour next) {
		if (number < 1 || number > 9) {
			throw new IllegalArgumentException("Les tours normaux sont numérotés de 1 to 9");
		}
		if (next == null) {
			throw new IllegalArgumentException("Les tours normaux doivent avoir un suivant");
		}

		numero = number;
		suivant = next;
	}

	
	public int getBoulesLancees() {
		return boulesLancees;
	}

	
	public int getNumero() {
		return numero;
	}

	
	public boolean estTermine() {
		return estUnStrike() || (boulesLancees == 2);
	}

	
	void enregistreLancer(int combien) {
		if (combien < 0) {
			throw new IllegalArgumentException("Ne peut pas être négatif");
		}
		if ((combien + quillesAbattuesLancer1) > 10) {
			throw new IllegalArgumentException("Max 10 points dans un tour");
		}
		if (boulesLancees > 1) {
			throw new IllegalStateException("Max 2 boules dans un tour normal");
		}

		boulesLancees++;
		if (boulesLancees == 1) {
			quillesAbattuesLancer1 = combien;
		} else if (boulesLancees == 2) {
			quillesAbattuesLancer2 = combien;
		}
	}

	
	boolean estUnStrike() {
		return quillesAbattuesLancer1 == 10;
	}

	
	boolean estUnSpare() {
		return (quillesAbattuesLancer1 + quillesAbattuesLancer2) == 10;
	}

	int bonusPourSpare() {
		return quillesAbattuesLancer1;
	}

	
	int bonusPourStrike() {
		if (estUnStrike()) {
			return quillesAbattuesLancer1 + suivant.bonusPourSpare();
		} else {
			return quillesAbattuesLancer1 + quillesAbattuesLancer2;
		}
	}

	
	int score() {
		int scoreDeCeTour = 0;
		if (estUnStrike()) {
			scoreDeCeTour = 10 + suivant.bonusPourStrike();
		} else if (estUnSpare()) {
			scoreDeCeTour = 10 + suivant.bonusPourSpare();
		} else {
			scoreDeCeTour = quillesAbattuesLancer1 + quillesAbattuesLancer2;
		}
		return scoreDeCeTour + suivant.score();
	}

	
	Tour next() {
		return suivant;
	}

	@Override
	public String toString() {
		return "Tour# " + numero + ", premier Lancer: " + quillesAbattuesLancer1 + ", secondLancer: " + quillesAbattuesLancer2;
	}
}

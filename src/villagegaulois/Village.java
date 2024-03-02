package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	private static class Marche {
		// Classe qui conserve ses valeurs d'une instance à une autre
		private Etal [] etals ;
		private int nbEtalsMax ;
		private Marche(int nbEtals) {
			super();
			etals = new Etal[nbEtals];
			this.nbEtalsMax = nbEtals;
		}
	
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,
				String produit, int nbProduit) {
			etals[indiceEtal] = new Etal();
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length ; i++) {
				if (etals[i] == null) {
					return i;
				}
			}
			return -1;
		}
		private Etal[] trouverEtals(String produit) {
			int nbEtalsProduit = 0; 
			for (int i = 0; i < nbEtalsMax && etals[i] != null; i++ ) {
				if (etals[i].contientProduit(produit)) {
					nbEtalsProduit ++;
				}
			} 
			Etal[] etalsProduit = new Etal[nbEtalsProduit];
			for (int i = 0 , j = 0; i < nbEtalsMax && etals[i] != null; i++) {
				if (j < nbEtalsProduit && etals[i].contientProduit(produit) ) {
					etalsProduit[j] = etals[i];
					j ++;
				}
			}
			return etalsProduit;
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			for (Etal etal : etals) {
				if (etal.getVendeur() == gaulois) {
					return etal;
				}
			}
			return null;
		}
		private String afficherEtal() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalsVides = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) {
					chaine.append(etal.afficherEtal()) ;
				} else {
					nbEtalsVides ++ ;
				}
			}
			chaine.append ("Il reste " + nbEtalsVides + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
		
		public int getNbEtalsMax() {
			return nbEtalsMax;
		}
		public Etal[] getEtals() {
			return etals;
		}
		public void setEtals(Etal[] etals) {
			this.etals = etals;
		}
		
		
	}
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	 public String installerVendeur(Gaulois vendeur, String produit,int
			 nbProduit) {
		 int i, j;
		 StringBuilder chaine = new StringBuilder();
		 chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + "\n");
		 Etal[] etals = marche.getEtals();
		 for (i = 0; i < marche.getNbEtalsMax() && etals[i] != null ; i ++);
		 j = i + 1;
		 marche.utiliserEtal(i, vendeur, produit, nbProduit);
		 chaine.append("Le vendeur " + vendeur.getNom() + " vend des fleurs à l'étal numéro " + j + "\n");
		 return chaine.toString();
	 }
	 
	 public String rechercherVendeursProduit(String produit) {
		 Etal[] etalsProduit = marche.trouverEtals(produit);
		 StringBuilder chaine = new StringBuilder();
		 int nbEtalsProduit = etalsProduit.length;
		switch (nbEtalsProduit) {
		case 0: 
			chaine.append("Il n'y a pas de vendeur qui propose " + produit +" au marché. \n");
			break;
		case 1 : 
			chaine.append("Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché \n");
			break;
		default : 
			chaine.append("Les vendeurs qui proposent des " + produit + " sont : \n" );
			for (int i = 0; i < nbEtalsProduit ; i++) {
				chaine.append("- " + etalsProduit[i].getVendeur().getNom() + "\n");
			}
			break;
		}
		return chaine.toString();
	 }

	}

package packnp.tests.tools;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
//import org.objenesis.tck.search.SystemOutListener;

import packnp.pharmacie.IPharmacie;
import packnp.pharmacie.IProduit;
import packnp.pharmacie.Medicament;


class Tool {
	public static boolean egal(LinkedList<Medicament> meds, LinkedList<String> noms) {
		boolean res=meds.size()==noms.size();
		if (res) {
		for (String nom : noms) {
			if (quantite(meds,nom)<1) {
				return false;
			}
		}
		}
		return res;
	}
	public static String toString(LinkedList<Medicament> meds) {
		String res = "{\n";
		for (Medicament m : meds) {
			IProduit p = (IProduit)m;
			res = res+"("+p.getQuantite()+" de \""+p.getNom()+"\")\n";
		}
		return res+"}\n";
	}
	public static String toString2(LinkedList<Medicament> meds) {
		String res = "[";
		for (Medicament m : meds) {
			IProduit p = (IProduit)m;
			res = res+p.getNom()+", ";
		}
		return res+"]";
	}
	public static String toString(int[]q) {
		String res = "{\n";
		String[] noms =
		{"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps",
		"Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps", 
		"Arrow ibuprofene 200mg 30cps","Doliprane 1000mg 8cps", "Doliprane 500mg 16cps", 
		"Paracetanol biogaran 1000mg 8cps", "Dafalgan 500mg 16cps", "Topalgic 100mg 30cps",
		"Contramal 100mg 30cps","Tramadol arrow 100mg 30cps", "Tramadol biogaran 100mg 30cps"};

		for (int j=0; j<q.length; j++) {
			if (q[j]>0) {
			res = res+"("+q[j]+" de \""+noms[j]+"\")\n";
			}
		}
		return res+"}\n";
	}
	public static int quantite(LinkedList<Medicament>meds, String nom) {
		for (Medicament m : meds) {
			IProduit p = (IProduit)m;
			if (p.getNom().equals(nom)) {
				return p.getQuantite();
			}
		}
		return 0;
	}
	public static boolean isOk(LinkedList<Medicament> meds, int[] q) {
		String[] noms =
		{"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps",
		"Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps", 
		"Arrow ibuprofene 200mg 30cps","Doliprane 1000mg 8cps", "Doliprane 500mg 16cps", 
		"Paracetanol biogaran 1000mg 8cps", "Dafalgan 500mg 16cps", "Topalgic 100mg 30cps",
		"Contramal 100mg 30cps","Tramadol arrow 100mg 30cps", "Tramadol biogaran 100mg 30cps"};
		
		for (int i=0;i<noms.length; i++) {
			if (quantite(meds,noms[i])!=q[i]) {
				return false;
			}
		}
		return true;
	}
}

//
//class tool {
//	public static boolean egaux(List<packnp.solution.Garniture> l1, List<packnp.solution.Garniture> l2) {
//		boolean eq=l1.size()==l2.size();
//		for (packnp.solution.Garniture gg : l1) {
//			if (!l2.contains(gg)) {
//				eq=false;
//			}
//		}
//		for (packnp.solution.Garniture gg : l2) {
//			if (!l1.contains(gg)) {
//				eq=false;
//			}
//		}
//		return eq;
//	}
//}
//


public class TestsMPO2023EvalF {

	private static final Object[][] PARAM_PRODUITS = {
			{"Advil 200mg 20cps", 3.13, 84},
			{"Advil 200mg 30cps", 4.40, 32},
			{"Advil 400mg 14cps", 3.32, 60},
			{"Spifen 200mg 30cps", 2.50, 35},
			{"Ibuprofen biogaran 200mg 30cps", 2.31, 95},
			{"Doliprane 1000mg 8cps", 2.47, 47},
			{"Doliprane 500mg 16cps", 2.11, 38},
			{"Paracetanol biogaran 1000mg 8cps", 2.15, 34},
			{"Dafalgan 500mg 16cps", 2.12, 26},

	};
	private static final Object[][] BAD_PARAMS = {
			{null, 3.13, 84},
			{"Advil 200mg 30cps", -0.1, 32},
			{"Advil 400mg 14cps", 3.32, -1},
	};
	public static int test01ProduitDeclarationVariables() {
		int notePrix=15;
		int noteNom=15;
		int noteQuantite=15;
		int noteImplements=40;
		int noteRienDAutre=15;
		System.out.println("   Test verifiant la declaration de la classe Produit : ");
		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
			noteRienDAutre=0;
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
			notePrix=0;
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				notePrix=notePrix/2;
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
				notePrix=notePrix/2;
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
			noteNom=0;
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				noteNom=noteNom/2;
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
				noteNom=noteNom/2;
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
			noteQuantite=0;
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				noteQuantite=noteQuantite/2;
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
				noteQuantite=noteQuantite/2;
			}
		}
		if (noteNom==0 || notePrix==0 || noteQuantite==0) {
			noteRienDAutre=0;
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
			noteImplements=0;
		}
		if (noteNom+notePrix+noteQuantite+noteRienDAutre+noteImplements==100) {
			System.out.println("   Ok. Votre code passe ce test avec succes.");
		}
		return noteNom+notePrix+noteQuantite+noteImplements+noteRienDAutre;
	}


	public static int test02ProduitConstructeur() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement du constructeur de Produit");
		int noteDeclaration=25;
		int noteInitNom=25;
		int noteInitPrix=25;
		int noteInitQuantite=25;
		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);
		Class<?>[] arStringDouble = {String.class, double.class, int.class};
		Constructor<?> constC1=null;
		try {
			constC1 = cProduit.getDeclaredConstructor(arStringDouble);
		} catch (NoSuchMethodException e) {
			constC1=null;
		} catch (SecurityException e) {
			constC1=null;
		}
		if (constC1==null) {
			System.out.println("   Aie... je ne trouve pas le constructeur de Produit prenant en parametre une String, un double et un int ");
			return 0;
		}
		if (!Modifier.isPublic(constC1.getModifiers())) {
			System.out.println("   Aie... Le constructeur de Produit devrait etre public");
			noteDeclaration=noteDeclaration/2;
		}
		constC1.setAccessible(true);
		Object ptc1=null;
		try {
			for (int i=0; i<PARAM_PRODUITS.length; i++) {
				ptc1 = (constC1.newInstance(PARAM_PRODUITS[i]));
				if (fieldNom==null) {
					noteInitNom=0;
				}else if (fieldNom.get(ptc1)==null) {
					System.out.println("  Aie... votre constructeur initialise la variable "+fieldNom.getName()+" a null \n");
					noteInitNom=0;
				} else {
					String nom = (String)(fieldNom.get(ptc1));
					if (noteInitNom>0 && !nom.equals(PARAM_PRODUITS[i][0])) {
						System.out.println("  Aie... apres new Produit(\""+PARAM_PRODUITS[i][0]+"\", \""+PARAM_PRODUITS[i][1]+"\", \""+PARAM_PRODUITS[i][2]+"\") la variable "+fieldNom.getName()+" du Produit vaut \""+nom+"\" au lieu de \""+PARAM_PRODUITS[i][0]+"\")");
						noteInitNom=0;
					} 
				}
				if (fieldPrix==null) {
					noteInitPrix=0;
				}else  {
					double pr = (double)(fieldPrix.get(ptc1));
					if (noteInitPrix>0 && Math.abs(pr-(double)PARAM_PRODUITS[i][1])>0.01) {
						System.out.println("  Aie... apres new Produit(\""+PARAM_PRODUITS[i][0]+"\", \""+PARAM_PRODUITS[i][1]+"\", \""+PARAM_PRODUITS[i][2]+"\") la variable "+fieldPrix.getName()+" du Produit vaut \""+pr+"\" au lieu de \""+PARAM_PRODUITS[i][1]+"\")");
						noteInitPrix=0;
					} 
				}
				if (fieldQuantite==null) {
					noteInitQuantite=0;
				}else  {
					int q = (int)(fieldQuantite.get(ptc1));
					if (noteInitQuantite>0 && q!=(int)PARAM_PRODUITS[i][2]) {
						System.out.println("  Aie... apres new Produit(\""+PARAM_PRODUITS[i][0]+"\", \""+PARAM_PRODUITS[i][1]+"\", \""+PARAM_PRODUITS[i][2]+"\") la variable "+fieldQuantite.getName()+" du Produit vaut \""+q+"\" au lieu de \""+PARAM_PRODUITS[i][2]+"\")");
						noteInitQuantite=0;
					} 
				}
			}
		} catch (Exception e) {
			System.out.println(" Votre code leve une exception dans un cas ou il ne devrait pas le faire ");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}
		boolean badParamNomOk=false;
		try {
			ptc1 = (constC1.newInstance(BAD_PARAMS[0]));
		} catch (Exception e) {
			badParamNomOk=true;
		}
		boolean badParamPrixOk=false;
		try {
			ptc1 = (constC1.newInstance(BAD_PARAMS[1]));
		} catch (Exception e) {
			badParamPrixOk=true;
		}
		boolean badParamQuantiteOk=false;
		try {
			ptc1 = (constC1.newInstance(BAD_PARAMS[2]));
		} catch (Exception e) {
			badParamQuantiteOk=true;
		}
		if (!badParamNomOk) {
			System.out.println("   Aie. Votre constructeur ne leve pas une Error lorsque le premier parametre est null");
			System.out.println("        Indiquez  throw new Error();   pour lever une erreur si le premier parametre est null");
			noteInitNom=noteInitNom/2;
		}
		if (!badParamPrixOk) {
			System.out.println("   Aie. Votre constructeur ne leve pas une Error lorsque le second parametre est negatif");
			System.out.println("        Indiquez  throw new Error();   pour lever une erreur si le second parametre est negatif");
			noteInitPrix=noteInitPrix/2;
		}
		if (!badParamQuantiteOk) {
			System.out.println("   Aie. Votre constructeur ne leve pas une Error lorsque le troisieme parametre est negatif");
			System.out.println("        Indiquez  throw new Error();   pour lever une erreur si le troisieme parametre est negatif");
			noteInitQuantite=noteInitQuantite/2;
		}


		if (noteDeclaration+noteInitPrix+noteInitQuantite+noteInitNom==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteInitPrix+noteInitQuantite+noteInitNom);
	}

	public static int test03ProduitGetters() {
		System.out.println("   Test verifiant le bon fonctionnement des getters (accesseurs en lecture) de Produit");
		int noteDeclarationGetNom=9;
		int noteComportementGetNom=25;
		int noteDeclarationGetPrix=8;
		int noteComportementGetPrix=25;
		int noteDeclarationGetQuantite=8;
		int noteComportementGetQuantite=25;

		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);








		Method mGetNom=null, mGetPrix=null, mGetQuantite=null;
		try {
			Method[] methods = cProduit.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getNom")) {
					mGetNom=m;
				} else if (m.getName().equals("getPrix")) {
					mGetPrix=m;
				} else if (m.getName().equals("getQuantite")) {
					mGetQuantite=m;
				} 				}
		} catch (Exception e1) {
		} 
		if (mGetNom==null) {
			noteDeclarationGetNom=0;
			noteComportementGetNom=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur getNom() de Produit");
		}
		if (mGetPrix==null) {
			noteDeclarationGetPrix=0;
			noteComportementGetPrix=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur getPrix() de Produit");
		}
		if (mGetQuantite==null) {
			noteDeclarationGetQuantite=0;
			noteComportementGetQuantite=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur getQuantite() de Produit");
		}
		if (noteDeclarationGetNom>0) {
			if (mGetNom.getParameterCount()!=0) {
				System.out.println("   Aie... Votre methode getNom ne devrait pas prendre de parametres");
				noteDeclarationGetNom=0;
				noteComportementGetNom=0;
			}
		}
		if (noteDeclarationGetNom>0) {
			if (!mGetNom.getReturnType().equals(String.class)) {
				System.out.println("   Aie... Votre methode getNom() n'a pas le type retour attendu");
				noteDeclarationGetNom=noteDeclarationGetNom/2;
				noteComportementGetNom=0;			}
		}
		if (noteDeclarationGetPrix>0) {
			if (mGetPrix.getParameterCount()!=0) {
				System.out.println("   Aie... Votre methode getPrix ne devrait pas prendre de parametres");
				noteDeclarationGetPrix=0;
				noteComportementGetPrix=0;
			}
		}
		if (noteDeclarationGetPrix>0) {
			if (!mGetPrix.getReturnType().equals(double.class)) {
				System.out.println("   Aie... Votre methode getPrix() n'a pas le type retour attendu");
				noteDeclarationGetPrix=noteDeclarationGetPrix/2;
				noteComportementGetPrix=0;
			}
		}
		if (noteDeclarationGetQuantite>0) {
			if (mGetQuantite.getParameterCount()!=0) {
				System.out.println("   Aie... Votre methode getQuantite ne devrait pas prendre de parametres");
				noteDeclarationGetQuantite=0;
				noteComportementGetQuantite=0;
			}
		}
		if (noteDeclarationGetQuantite>0) {
			if (!mGetQuantite.getReturnType().equals(int.class)) {
				System.out.println("   Aie... Votre methode getQuantite() n'a pas le type retour attendu");
				noteDeclarationGetQuantite=noteDeclarationGetQuantite/2;
				noteComportementGetQuantite=0;
			}
		}
		if (mGetNom!=null && !Modifier.isPublic(mGetNom.getModifiers())) {
			System.out.println("   Aie... Votre methode getNom() n'a pas la visibilite attendue");
			noteDeclarationGetNom = noteDeclarationGetNom/2;
		}
		if (mGetPrix!=null && !Modifier.isPublic(mGetPrix.getModifiers())) {
			System.out.println("   Aie... Votre methode getPrix() n'a pas la visibilite attendue");
			noteDeclarationGetPrix = noteDeclarationGetPrix/2;
		}
		if (mGetQuantite!=null && !Modifier.isPublic(mGetQuantite.getModifiers())) {
			System.out.println("   Aie... Votre methode getQuantite() n'a pas la visibilite attendue");
			noteDeclarationGetQuantite = noteDeclarationGetQuantite/2;
		}


		if (mGetNom!=null) mGetNom.setAccessible(true);
		if (mGetPrix!=null) mGetPrix.setAccessible(true);
		if (mGetQuantite!=null) mGetQuantite.setAccessible(true);


		Object[] argssn = {};
		for (int i=0; i<PARAM_PRODUITS.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cProduit);
			pt1 = instantiator.newInstance();
			String resNom=null;
			double resPrix=-123.4;
			int resQuantite=-123;
			try {
				if (fieldNom!=null) fieldNom.set(pt1,PARAM_PRODUITS[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pt1,PARAM_PRODUITS[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pt1,PARAM_PRODUITS[i][2]);
				if (noteComportementGetNom>0) {
					if ( mGetNom!=null) {
						resNom=(String)(mGetNom.invoke(pt1,  argssn));
					}
					if ( resNom==null ) {
						System.out.println("   Aie... votre accesseur getNom n'est pas correct. Il retourne null sur une instance ayant un nom initialise a \""+PARAM_PRODUITS[i][0]+"\"");
						noteComportementGetNom=0;
					} else if (!resNom.equals(PARAM_PRODUITS[i][0])) {
						System.out.println("   Aie... votre accesseur getNom n'est pas correct. Il retourne \""+resNom+"\" sur une instance ayant un nom initialise a \""+PARAM_PRODUITS[i][0]+"\"");
						noteComportementGetNom=0;
					}
				}
				if (noteComportementGetPrix>0) {
					if ( mGetPrix!=null) {
						resPrix=(Double)(mGetPrix.invoke(pt1,  argssn));
					}
					if (Math.abs(resPrix-(double)(PARAM_PRODUITS[i][1]))>0.01) {
						System.out.println("   Aie... votre accesseur getPrix n'est pas correct. Il retourne \""+resPrix+"\" sur une instance ayant un prix initialisee a \""+PARAM_PRODUITS[i][1]+"\"");
						noteComportementGetPrix=0;
					}
				}
				if (noteComportementGetQuantite>0) {
					if ( mGetQuantite!=null) {
						resQuantite=(int)(mGetQuantite.invoke(pt1,  argssn));
					}
					if (resQuantite!=(int)(PARAM_PRODUITS[i][2])) {
						System.out.println("   Aie... votre accesseur getQuantite n'est pas correct. Il retourne \""+resQuantite+"\" sur une instance ayant une quantite initialisee a \""+PARAM_PRODUITS[i][2]+"\"");
						noteComportementGetQuantite=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportementGetNom=0;
				noteComportementGetPrix=0;
				noteComportementGetQuantite=0;
			}
		}
		if (noteDeclarationGetNom+noteDeclarationGetPrix+noteComportementGetPrix+noteComportementGetNom+noteDeclarationGetQuantite+noteComportementGetQuantite==100	) {
			System.out.println("   Ok. Votre code passe le test avec succes.");
		}
		return noteDeclarationGetNom+noteDeclarationGetPrix+noteComportementGetPrix+noteComportementGetNom+noteDeclarationGetQuantite+noteComportementGetQuantite;
	}



	public static int test04ProduitSetters() {
		System.out.println("   Test verifiant le bon fonctionnement des setters (accesseurs en ecriture) de Produit");
		int noteDeclarationSetPrix=10;
		int noteComportementSetPrix=40;
		int noteDeclarationSetQuantite=10;
		int noteComportementSetQuantite=40;

		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);


		Method 		mSetPrix=null, mSetQuantite=null;
		try {
			Method[] methods = cProduit.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("setPrix")) {
					mSetPrix=m;
				} else if (m.getName().equals("setQuantite")) {
					mSetQuantite=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mSetPrix==null) {
			noteDeclarationSetPrix=0;
			noteComportementSetPrix=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur setPrix() de Produit");
		}
		if (mSetQuantite==null) {
			noteDeclarationSetQuantite=0;
			noteComportementSetQuantite=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur setQuantite() de Produit");
		}
		if (noteDeclarationSetPrix>0) {
			if (mSetPrix.getParameterCount()!=1 || !mSetPrix.getParameters()[0].getType().equals(double.class)) {
				System.out.println("   Aie... Votre methode setPrix devrait prendre un unique parametre de type double");
				noteDeclarationSetPrix=0;
				noteComportementSetPrix=0;
			}
		}
		if (noteDeclarationSetPrix>0) {
			if (!mSetPrix.getReturnType().equals(void.class)) {
				System.out.println("   Aie... Votre methode setPrix() devrait avoir void pour type retour");
				noteDeclarationSetPrix=noteDeclarationSetPrix/2;
				noteComportementSetPrix=0;
			}
		}
		if (noteDeclarationSetQuantite>0) {
			if (mSetQuantite.getParameterCount()!=1 || !mSetQuantite.getParameters()[0].getType().equals(int.class)) {
				System.out.println("   Aie... Votre methode setQuantite devrait avoir un unique parametre de type int");
				noteDeclarationSetQuantite=0;
				noteComportementSetQuantite=0;
			}
		}
		if (noteDeclarationSetQuantite>0) {
			if (!mSetQuantite.getReturnType().equals(void.class)) {
				System.out.println("   Aie... Votre methode setQuantite() devrait avoir void pour type retour");
				noteDeclarationSetQuantite=noteDeclarationSetQuantite/2;
				noteComportementSetQuantite=0;
			}
		}
		if (mSetPrix!=null && !Modifier.isPublic(mSetPrix.getModifiers())) {
			System.out.println("   Aie... Votre methode setPrix() n'a pas la visibilite attendue");
			noteDeclarationSetPrix = noteDeclarationSetPrix/2;
		}
		if (mSetQuantite!=null && !Modifier.isPublic(mSetQuantite.getModifiers())) {
			System.out.println("   Aie... Votre methode setQuantite() n'a pas la visibilite attendue");
			noteDeclarationSetQuantite = noteDeclarationSetQuantite/2;
		}


		if (mSetPrix!=null) mSetPrix.setAccessible(true);
		if (mSetQuantite!=null) mSetQuantite.setAccessible(true);


		Object[] PARAM = {"bidon", 12.3, 24};
		double[] prix= {0.5, 1.0, 2.2, 4.8, 12.4};
		int[] quantites= {1, 12, 20, 8,12};
		Object[] tabParam = {" "};
		for (int i=0; i<prix.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cProduit);
			pt1 = instantiator.newInstance();
			double resPrix=-123.4;
			int resQuantite=-123;
			try {
				if (fieldNom!=null) fieldNom.set(pt1,PARAM[0]);
				if (fieldPrix!=null) fieldPrix.set(pt1,PARAM[1]);
				if (fieldQuantite!=null) fieldQuantite.set(pt1,PARAM[2]);
				if (noteComportementSetPrix>0) {
					if ( mSetPrix!=null) {
						tabParam[0]= Double.valueOf(prix[i]);
						mSetPrix.invoke(pt1, tabParam);
						if (fieldPrix!=null) resPrix=fieldPrix.getDouble(pt1);
					}
					if (Math.abs(resPrix-prix[i])>0.01) {
						if (fieldPrix!=null) System.out.println("   Aie... Appelee sur un produit ayant un prix de 12.3, la methode setPrix("+prix[i]+") affecte la variable prix a "+resPrix+" au lieu de "+prix[i]+"\"");
						else System.out.println("  la variable d'instance prix n'est pas definie --> aucun point accorde au comportement de setPrix");
						noteComportementSetPrix=0;
					}
				}
				if (noteComportementSetQuantite>0) {
					if ( mSetQuantite!=null) {
						tabParam[0]= Integer.valueOf(quantites[i]);
						mSetQuantite.invoke(pt1, tabParam);
						if (fieldQuantite!=null) resQuantite=fieldQuantite.getInt(pt1);
					}
					if (resQuantite!=quantites[i]) {
						if (fieldQuantite!=null) System.out.println("   Aie... Appelee sur un produit ayant une quantite de 24, la methode setQuantite("+quantites[i]+") affecte la variable quantite a "+resQuantite+" au lieu de "+quantites[i]+"\"");
						else System.out.println("  la variable d'instance quantite n'est pas definie --> aucun point accorde au comportement de setQuantite");
						noteComportementSetQuantite=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportementSetPrix=0;
				noteComportementSetQuantite=0;
			}
		}
		// test levees d'exceptions
		Object pt1=null;
		Objenesis objenesis = new ObjenesisStd(); 
		ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cProduit);
		pt1 = instantiator.newInstance();
		boolean setPrixLeve=false;
		boolean setQuantiteLeve=false;
		try {
			if (fieldNom!=null) fieldNom.set(pt1,PARAM[0]);
			if (fieldPrix!=null) fieldPrix.set(pt1,PARAM[1]);
			if (fieldQuantite!=null) fieldQuantite.set(pt1,PARAM[2]);
			if (noteComportementSetPrix>0) {
				if ( mSetPrix!=null) {
					tabParam[0]= Double.valueOf(-0.5);
					mSetPrix.invoke(pt1, tabParam);
					setPrixLeve=(double)(fieldPrix.get(pt1))==(double)PARAM[1];
						if (!setPrixLeve) {
							System.out.println("  Aie... setPrix(-0.5) ne devrait pas modifier le prix du produit.");
							System.out.println("  setPrix(x) ne doit modifier le prix du produit que si x>=0");
							noteComportementSetPrix=noteComportementSetPrix/2;
						}						
					
				}
			}
		} catch (Exception e) {
			//setPrixLeve=true;
		}
		try {
			if (fieldNom!=null) fieldNom.set(pt1,PARAM[0]);
			if (fieldPrix!=null) fieldPrix.set(pt1,PARAM[1]);
			if (fieldQuantite!=null) fieldQuantite.set(pt1,PARAM[2]);
			if (noteComportementSetQuantite>0) {
				if ( mSetQuantite!=null) {
					tabParam[0]= Integer.valueOf(-2);
					mSetQuantite.invoke(pt1, tabParam);
					setQuantiteLeve=(int)(fieldQuantite.get(pt1))==(int)PARAM[2];
					if (!setQuantiteLeve) {
						System.out.println("  Aie... setQuantite(-2) ne devrait pas modifier la quantite du produit.");
						System.out.println("  setQuantite(x) ne doit modifier la quantite du produit que si x>=0");
						noteComportementSetQuantite=noteComportementSetQuantite/2;
					}
				}
			}
		} catch (Exception e) {
			setQuantiteLeve=true;
		}
//		if (!setPrixLeve) {
//			System.out.println("  Aie... setPrix(-0.5) devrait lever une IllegalArgumentException (et ce n'est pas le cas)");
//			noteComportementSetPrix=noteComportementSetPrix/2;
//		}
//		if (!setQuantiteLeve) {
//			System.out.println("  Aie... setQuantite(-2) devrait lever une IllegalArgumentException (et ce n'est pas le cas)");
//			noteComportementSetQuantite=noteComportementSetQuantite/2;
//		}
		if (noteDeclarationSetPrix+noteDeclarationSetQuantite+noteComportementSetPrix+noteComportementSetQuantite==100	) {
			System.out.println("   Ok. Votre code passe le test avec succes.");
		}
		return noteDeclarationSetPrix+noteDeclarationSetQuantite+noteComportementSetPrix+noteComportementSetQuantite;
	}

	public static int test05ProduitEquals() {
		System.out.println("   Test verifiant le bon fonctionnement de la methode equals de Produit");
		int noteDeclaration=30;
		int noteComportement=70;

		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);


		Method mEquals=null;
		try {
			Method[] methods = cProduit.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("equals")) {//.getReturnType().equals(cPoint)) {
					mEquals=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mEquals==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode equals dans Produit");
		}
		if (noteDeclaration>0) {
			if (mEquals.getParameterCount()!=1) {
				System.out.println("   Aie... Votre methode equals devrait comporter un unique parametre");
				noteDeclaration=noteDeclaration/2;
				noteComportement=0;
			} else {
				if (!mEquals.getParameters()[0].getType().equals(Object.class)) {
					System.out.println("   Aie... Le type du parametre de equals n'est pas celui attendu");
					noteDeclaration=noteDeclaration/2;
					noteComportement=0;
				}
			}
		}
		if (noteDeclaration>0) {
			if (!mEquals.getReturnType().equals(boolean.class)) {
				System.out.println("   Aie... Votre methode equals n'a pas le type retour attendu");
				noteDeclaration=noteDeclaration/2;
				noteComportement=0;			}
		}
		if (noteDeclaration>0) {
			if (!Modifier.isPublic(mEquals.getModifiers())) {
				System.out.println("   Aie... Votre methode equals n'est pas declaree public");
				noteDeclaration=noteDeclaration/2;
				noteComportement=0;			}
		}

		if (mEquals!=null) mEquals.setAccessible(true);

		Object[][] produit1 = {
				{"Advil 200mg 20cps", 3.13, 84},
				{"Advil 200mg 30cps", 4.40, 32},
				{"Advil 400mg 14cps", 3.32, 60},
				{"Spifen 200mg 30cps", 2.50, 35},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95},
				{"Doliprane 1000mg 8cps", 2.47, 47},
				{"Doliprane 500mg 16cps", 2.11, 38},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34},
				{"Dafalgan 500mg 16cps", 2.12, 26},
		};
		Object[][] produit2 = {
				{"Advil 200mg 20cps", 4.13, 94},
				{"Advil 200mg 30cps", 5.40, 32},
				{"Advil 400mg 14cps", 3.32, 80},
				{"Spifen 200mg 30cps", 1.50, 35},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 75},
				{"Doliprane 1000mg 8cps", 1.47, 17},
				{"Doliprane 500mg 16cps", 6.11, 68},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 64},
				{"Dafalgan 500mg 16cps", 5.12, 26},
		};
		Object[][] produit3 = {
				{"Advil 100mg 20cps", 4.13, 94},
				{"Advil 200mg 20cps", 5.40, 32},
				{"Advil 400mg 7cps", 3.32, 80},
				{"Advil 200mg 30cps", 1.50, 35},
				{"Biogaran 200mg 30cps", 2.31, 75},
				{"Doliprane 500mg 8cps", 1.47, 17},
				{"Doliprane 1000mg 16cps", 6.11, 68},
				{"Biogaran 1000mg 8cps", 2.15, 64},
				{"Dafalgan 1000mg 16cps", 5.12, 26},
		};

		if (fieldNom==null) {
			noteComportement=0;
		}
		Object[] argssn = new Object[1];
		for (int i=0; noteComportement>0 && i<produit1.length; i++) {
			Object pt1=null;
			Object pt2=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cProduit);
			pt1 = instantiator.newInstance();
			pt2 = instantiator.newInstance();
			argssn[0]=pt2;
			boolean res=false;
			try {
				if (fieldNom!=null) fieldNom.set(pt1,produit1[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pt1,produit1[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pt1,produit1[i][2]);
				if (fieldNom!=null) fieldNom.set(pt2,produit2[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pt2,produit2[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pt2,produit2[i][2]);
				if (noteComportement>0) {
					if ( mEquals!=null) {
						res=(boolean)(mEquals.invoke(pt1,  argssn));
					}
					//System.out.print(res+",");
					if ( !res ) {
						System.out.println("   Aie... p1.equals(p2) ou p1 et p2 sont deux instances de produit ayant le meme nom (\""+produit1[i][0]+"\" retourne false au lieu de true");
						noteComportement=0;
					} 
				}
				if (fieldNom!=null) fieldNom.set(pt2,produit3[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pt2,produit3[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pt2,produit3[i][2]);
				if (noteComportement>0) {
					if ( mEquals!=null) {
						res=(boolean)(mEquals.invoke(pt1,  argssn));
					}
					//System.out.print(res+",");
					if ( res ) {
						System.out.println("   Aie... p1.equals(p2) ou p1 et p2 sont deux instances de produit de nom respectif \""+produit1[i][0]+ "\" et \""+produit3[i][0]+"\" retourne true au lieu de false");
						noteComportement=0;
					} 
				}
				if (noteComportement>0) {
					if ( mEquals!=null) {
						argssn[0]="toto";
						res=(boolean)(mEquals.invoke(pt1,  argssn));
					}
					if ( res ) {
						System.out.println("   Aie... p1.equals(\"toto\") ou p1 es une instance de produit de nom \""+produit1[i][0]+ "\" retourne true au lieu de false (equals doit retourner false si le parametre n'est pas une instance de IProduit)");
						noteComportement=0;
					} 
				}
				if (noteComportement>0) {
					if ( mEquals!=null) {
						argssn[0]=Double.valueOf(2.14);
						res=(boolean)(mEquals.invoke(pt1,  argssn));
					}
					if ( res ) {
						System.out.println("   Aie... p1.equals(2.14) ou p1 es une instance de produit de nom \""+produit1[i][0]+ "\" retourne true au lieu de false (equals doit retourner false si le parametre n'est pas une instance de IProduit)");
						noteComportement=0;
					} 
				}

			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportement=0;
			}
		}
		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test avec succes.");
		}
		return noteDeclaration+noteComportement;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test06Parapharmacie() {
		int noteCategorie=15;
		int noteExtends=20;
		int noteRienDAutre=5;
		int noteConstructeur=40;
		int noteDeclarationGetCategorie=5;
		int noteComportementGetCategorie=15;

		Object[][] paramsConst = {
				{"Rayban", 102.00, 2, "Solaire"},
				{"Myocalm contractions musculaires 30cps", 6.49, 22, "Sport"},
				{"Beaume du tigre rouge 19g", 7.89, 3, "Sport"},
				{"Arkopharma Azinc forme et vitalite 60cps", 6.68, 15, "Sport"},
				{"Oenobiol minceur 50cps", 8.29, 12, "Minceur"},
				{"Super diet complex guarana bio 60cps", 10.90, 20, "Minceur"},
				{"Gestarelle G3 grossesse 90cps", 15.98, 34, "Maternite"},
				{"Lansinoh creme apaisante Lanoline 40ml", 15.21, 14, "Maternite"},
				{"Seraquin Omega 60cps", 19.41, 16, "Veterinaire"},
				{"Biocanina Milbetel 2cps", 9.94, 10, "Veterinaire"},
		};

		System.out.println("   Test verifiant la declaration de la classe Parapharmacie et le comportement de son constructeur et du getter getCategorie");
		Class cParapharmacie = Reflexion.getClass("packnp.pharmacie.Parapharmacie");
		if (cParapharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Parapharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldCategorie=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cParapharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().equals("categorie")) {
				if (fieldCategorie==null) {
					fieldCategorie=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Parapharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Parapharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Parapharmacie ne doit comporter qu'une variable d'instance memorisant la categorie. Respectez les noms precises sur le diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
			noteRienDAutre=0;
			return 0;
		}
		if (fieldCategorie==null) {
			System.out.println("   Aie... La classe Parapharmacie doit comporter une variable d'instance nommee categorie.");
			noteCategorie=0;
		} else {
			if (!Modifier.isPrivate(fieldCategorie.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldCategorie.getName());
				noteCategorie=noteCategorie/2;
			}
			if (!fieldCategorie.getType().equals(String.class)) {
				System.out.println("   Aie... La variable categorie que vous avez declaree n'est pas du type attendu");
				noteCategorie=0;
			}
		}
		if (fieldCategorie!=null) fieldCategorie.setAccessible(true);

		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
				noteConstructeur=0;
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
				noteConstructeur=0;
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
				noteConstructeur=0;
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cParapharmacie, cProduit)) {
			System.out.println("   Aie... Votre classe Parapharmacie n'herite pas de la classe Produit");
			noteExtends=0;
		}

		Class[] ar = {};
		Class[] arPara = {String.class, double.class, int.class, String.class};
		Constructor constC1=null;
		try {
			constC1 = cParapharmacie.getDeclaredConstructor(arPara);
		} catch (NoSuchMethodException e) {
			constC1=null;
		} catch (SecurityException e) {
			constC1=null;
		}
		if (constC1==null) {
			System.out.println("   Aie... je ne trouve pas le constructeur de Parapharmacie prenant les parametres specifies sur le diagramme de classes");
			noteConstructeur=0;
		}
		if (noteConstructeur>0 && !Modifier.isPublic(constC1.getModifiers())) {
			System.out.println("   Aie... Le constructeur de Parapharmacie devrait etre public");
			noteConstructeur=noteConstructeur-5;
		}
		if (constC1!=null) constC1.setAccessible(true);
		if (noteExtends==0) noteConstructeur=0;

		Object ptc1=null;

		try {
			for (int i=0; constC1!=null && noteConstructeur>0 && i<paramsConst.length ; i++) {
				ptc1 = (constC1.newInstance(paramsConst[i]));
				if (noteConstructeur>0 && fieldNom==null) {
					noteConstructeur=0;
				}else if (noteConstructeur>0 &&fieldNom.get(ptc1)==null) {
					System.out.println("  Aie... apres prod=new Parapharmacie(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", \""+paramsConst[i][3]+"\") le nom de prod vaut null au lieu de "+paramsConst[i][0]);
					noteConstructeur=0;
				} else {
					String nom = (String)(fieldNom.get(ptc1));
					if (noteConstructeur>0 && !nom.equals(paramsConst[i][0])) {
						System.out.println("  Aie... apres prod=new Parapharmacie(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", \""+paramsConst[i][3]+"\") le nom de prod vaut \""+nom+"\" au lieu de "+paramsConst[i][0]);
						noteConstructeur=0;
					} 
				}
				if (noteConstructeur>0 &&fieldPrix==null) {
					noteConstructeur=0;
				} else {
					double prix = (double)(fieldPrix.get(ptc1));
					if (noteConstructeur>0 && Math.abs(prix-(double)paramsConst[i][1])>0.01) {
						System.out.println("  Aie... apres prod=new Parapharmacie(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", \""+paramsConst[i][3]+"\") le prix de prod vaut "+prix+" au lieu de "+paramsConst[i][1]);
						noteConstructeur=0;
					} 
				}
				if (noteConstructeur>0 &&fieldQuantite==null) {
					noteConstructeur=0;
				} else {
					int quantite = (int)(fieldQuantite.get(ptc1));
					if (noteConstructeur>0 && quantite!=(int)paramsConst[i][2]) {
						System.out.println("  Aie... apres prod=new Parapharmacie(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", \""+paramsConst[i][3]+"\") la quantite de prod vaut "+quantite+" au lieu de "+paramsConst[i][2]);
						noteConstructeur=0;
					} 
				}
				if (noteConstructeur>0 && fieldCategorie==null) {
					noteConstructeur=0;
				}else if (noteConstructeur>0 &&fieldCategorie.get(ptc1)==null) {
					System.out.println("  Aie... apres prod=new Parapharmacie(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", \""+paramsConst[i][3]+"\") la categorie de prod vaut null au lieu de \""+paramsConst[i][3]+"\"");
					noteConstructeur=0;
				} else {
					String categorie = (String)(fieldCategorie.get(ptc1));
					if (noteConstructeur>0 && !categorie.equals(paramsConst[i][3])) {
						System.out.println("  Aie... apres prod=new Parapharmacie(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", \""+paramsConst[i][3]+"\") la categorie de prod vaut \""+categorie+"\" au lieu de "+paramsConst[i][3]);
						noteConstructeur=0;
					} 
				}
			}
		} catch (Exception e) {
			System.out.println("   Exception levee ");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}


		//	System.out.println("noteListes="+noteListes);
		///	System.out.println("noteExtends="+noteExtends);
		//	System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
		//	System.out.println("noteConstructeurListes="+noteConstructeurListes);









		Method mGetCategorie=null;
		try {
			Method[] methods = cParapharmacie.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getCategorie")) {
					mGetCategorie=m;
				} 			
			}
		} catch (Exception e1) {
		} 
		if (mGetCategorie==null) {
			noteDeclarationGetCategorie=0;
			noteComportementGetCategorie=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur getCategorie() de Parapharmacie");
		}
		if (noteDeclarationGetCategorie>0) {
			if (mGetCategorie.getParameterCount()!=0) {
				System.out.println("   Aie... Votre methode getCategorie ne devrait pas prendre de parametres");
				noteDeclarationGetCategorie=0;
				noteComportementGetCategorie=0;
			}
		}
		if (noteDeclarationGetCategorie>0) {
			if (!mGetCategorie.getReturnType().equals(String.class)) {
				System.out.println("   Aie... Votre methode getCategorie() n'a pas le type retour attendu");
				noteDeclarationGetCategorie=noteDeclarationGetCategorie/2;
				noteComportementGetCategorie=0;
			}
		}
		if (mGetCategorie!=null && !Modifier.isPublic(mGetCategorie.getModifiers())) {
			System.out.println("   Aie... Votre methode getCategorie() n'a pas la visibilite attendue");
			noteDeclarationGetCategorie = noteDeclarationGetCategorie/2;
		}
		if (mGetCategorie!=null) mGetCategorie.setAccessible(true);

		if (fieldCategorie==null || mGetCategorie==null) {
			noteComportementGetCategorie=0;
		}
		Object[] argssn = {};
		for (int i=0; noteComportementGetCategorie>0 && i<paramsConst.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cParapharmacie);
			pt1 = instantiator.newInstance();
			String resCategorie=null;
			try {
				if (fieldNom!=null) fieldNom.set(pt1,paramsConst[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pt1,paramsConst[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pt1,paramsConst[i][2]);
				if (fieldCategorie!=null) fieldCategorie.set(pt1,paramsConst[i][3]);
				if (noteComportementGetCategorie>0) {
					if ( mGetCategorie!=null) {
						resCategorie=(String)(mGetCategorie.invoke(pt1,  argssn));
					}
					if ( resCategorie==null ) {
						System.out.println("   Aie... votre accesseur getCategorie() n'est pas correct. Il retourne null sur une instance ayant une categorie initialisee a \""+paramsConst[i][0]+"\"");
						noteComportementGetCategorie=0;
					} else if (!resCategorie.equals(paramsConst[i][3])) {
						System.out.println("   Aie... votre accesseur getCategorie() n'est pas correct. Il retourne \""+resCategorie+"\" sur une instance ayant une categorie initialisee a \""+paramsConst[i][0]+"\"");
						noteComportementGetCategorie=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportementGetCategorie=0;
			}
		}



		if (noteCategorie+noteExtends+noteConstructeur+noteRienDAutre+noteDeclarationGetCategorie+noteComportementGetCategorie==100) {
			System.out.println("   Ok. Votre code passe ce test avec succes.");
		}
		return noteCategorie+noteExtends+noteConstructeur+noteRienDAutre+noteDeclarationGetCategorie+noteComportementGetCategorie;
	}



	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test07Medicament() {
	//	int noteGenerique=5;
	//	int noteSurPrescription=5;
		int noteSubstanceActive=15;
		int noteExtends=20;
		int noteRienDAutre=5;
		int noteConstructeur=20;
	//	int noteIsGenerique=10;
	//	int noteIsSurPrescription=10;
		int noteGetSubstanceActive=20;
		int noteIsSubstituable=20;

		Object[][] paramsConst = {
				{"Advil 200mg 20cps", 3.13, 84,"ibuprofene"},
				{"Advil 200mg 30cps", 4.40, 32,"ibuprofene"},
				{"Advil 400mg 14cps", 3.32, 60,"ibuprofene"},
				{"Spifen 200mg 30cps", 2.50, 35,"ibuprofene"},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,"ibuprofene"},
				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,"ibuprofene"},
				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,"ibuprofene"},
				{"Doliprane 1000mg 8cps", 2.47, 47,"paracetamol"},
				{"Doliprane 500mg 16cps", 2.11, 38,"paracetamol"},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,"paracetamol"},
				{"Dafalgan 500mg 16cps", 2.12, 26,   "paracetamol"},
				{"Topalgic 100mg 30cps", 6.15, 16,   "tramadol chlorhydrate"},
				{"Contramal 100mg 30cps", 7.25, 8,   "tramadol chlorhydrate"},
				{"Tramadol arrow 100mg 30cps", 5.67, 18,   "tramadol chlorhydrate"},
				{"Tramadol biogaran 100mg 30cps", 5.67, 14,   "tramadol chlorhydrate"},
		};
//		Object[][] paramsConst = {
//				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
//				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
//				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
//				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
//				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
//				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
//				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
//				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
//				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
//				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
//				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
//				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
//				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
//				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
//				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
//		};
		System.out.println("   Test verifiant la declaration de la classe Medicament et le comportement de ses methodes");
		Class cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		if (cMedicament==null) {
			System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldATC=null, //fieldGenerique=null, fieldSurPrescription=null, 
				fieldSubstanceActive=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cMedicament.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
//			if ( f.getName().equals("generique")) {
//				if (fieldGenerique==null) {
//					fieldGenerique=f;
//				} else {
//					fieldOther=f;
//				}
//			} else if ( f.getName().equals("surPrescription")) {
//				if (fieldSurPrescription==null) {
//					fieldSurPrescription=f;
//				} else {
//					fieldOther=f;
//				}
//			} else 
				if ( f.getName().equals("substanceActive")) {
				if (fieldSubstanceActive==null) {
					fieldSubstanceActive=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("atc")) {
				if (fieldATC==null) {
					fieldATC=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
			noteRienDAutre=0;
			return 0;
		}
//		if (fieldGenerique==null) {
//			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
//			noteGenerique=0;
//		} else {
//			if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
//				noteGenerique=noteGenerique/2;
//			}
//			if (!fieldGenerique.getType().equals(boolean.class)) {
//				System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
//				noteGenerique=0;
//			}
//		}
//		if (fieldSurPrescription==null) {
//			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
//			noteSurPrescription=0;
//		} else {
//			if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
//				noteSurPrescription=noteSurPrescription/2;
//			}
//			if (!fieldSurPrescription.getType().equals(boolean.class)) {
//				System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
//				noteSurPrescription=0;
//			}
//		}
		if (fieldSubstanceActive==null) {
			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
			noteSubstanceActive=0;
		} else {
			if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
				noteSubstanceActive=noteSubstanceActive/2;
			}
			if (!fieldSubstanceActive.getType().equals(String.class)) {
				System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
				noteSubstanceActive=0;
			}
		}
//		if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
//		if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);

		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
				noteConstructeur=0;
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
				noteConstructeur=0;
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
				noteConstructeur=0;
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cMedicament, cProduit)) {
			System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
			noteExtends=0;
			noteConstructeur=0;
		}

		Class[] ar = {};
		Class[] arMedic = {String.class, double.class, int.class, //boolean.class, boolean.class,
				String.class};
		Constructor constC1=null;
		try {
			constC1 = cMedicament.getDeclaredConstructor(arMedic);
		} catch (NoSuchMethodException e) {
			constC1=null;
		} catch (SecurityException e) {
			constC1=null;
		}
		if (constC1==null) {
			System.out.println("   Aie... je ne trouve pas le constructeur de Medicament prenant les parametres specifies sur le diagramme de classes");
			noteConstructeur=0;
		}
		if (noteConstructeur>0 && !Modifier.isPublic(constC1.getModifiers())) {
			System.out.println("   Aie... Le constructeur de Medicament devrait etre public");
			noteConstructeur=noteConstructeur-5;
		}
		if (constC1!=null) constC1.setAccessible(true);

		Object ptc1=null;

		try {
			for (int i=0; noteConstructeur>0 && i<paramsConst.length ; i++) {
				ptc1 = (constC1.newInstance(paramsConst[i]));
				if (noteConstructeur>0 && fieldNom==null) {
					noteConstructeur=0;
				}else if (noteConstructeur>0 &&fieldNom.get(ptc1)==null) {
					System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") le nom de med vaut null au lieu de "+paramsConst[i][0]);
					noteConstructeur=0;
				} else {
					String nom = (String)(fieldNom.get(ptc1));
					if (noteConstructeur>0 && !nom.equals(paramsConst[i][0])) {
						System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") le nom de med vaut \""+nom+"\" au lieu de "+paramsConst[i][0]);
						noteConstructeur=0;
					} 
				}
				if (noteConstructeur>0 &&fieldPrix==null) {
					noteConstructeur=0;
				} else {
					double prix = (double)(fieldPrix.get(ptc1));
					if (noteConstructeur>0 && Math.abs(prix-(double)paramsConst[i][1])>0.01) {
						System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") le prix de med vaut "+prix+" au lieu de "+paramsConst[i][1]);
						noteConstructeur=0;
					} 
				}
				if (noteConstructeur>0 &&fieldQuantite==null) {
					noteConstructeur=0;
				} else {
					int quantite = (int)(fieldQuantite.get(ptc1));
					if (noteConstructeur>0 && quantite!=(int)paramsConst[i][2]) {
						System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") la quantite de med vaut "+quantite+" au lieu de "+paramsConst[i][2]);
						noteConstructeur=0;
					} 
				}
//				if (noteConstructeur>0 && fieldGenerique==null) {
//					noteConstructeur=0;
//				}else if (noteConstructeur>0 &&fieldGenerique.get(ptc1)==null) {
//					System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") la variable generique de med vaut null au lieu de \""+paramsConst[i][3]+"\"");
//					noteConstructeur=0;
//				} else {
//					boolean generique = (boolean)(fieldGenerique.get(ptc1));
//					if (noteConstructeur>0 && generique!=(boolean)paramsConst[i][3]) {
//						System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") la variable generique de med vaut "+generique+" au lieu de "+paramsConst[i][3]);
//						noteConstructeur=0;
//					} 
//				}
//				if (noteConstructeur>0 && fieldSurPrescription==null) {
//					noteConstructeur=0;
//				}else if (noteConstructeur>0 &&fieldSurPrescription.get(ptc1)==null) {
//					System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") la variable surPrescription de med vaut null au lieu de \""+paramsConst[i][4]+"\"");
//					noteConstructeur=0;
//				} else {
//					boolean surPrescription = (boolean)(fieldSurPrescription.get(ptc1));
//					if (noteConstructeur>0 && surPrescription!=(boolean)paramsConst[i][4]) {
//						System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+", "+paramsConst[i][4]+", \""+paramsConst[i][5]+"\") la variable surPrescription de med vaut "+surPrescription+" au lieu de "+paramsConst[i][4]);
//						noteConstructeur=0;
//					} 
//				}
				if (noteConstructeur>0 && fieldSubstanceActive==null) {
					noteConstructeur=0;
				}else if (noteConstructeur>0 &&fieldSubstanceActive.get(ptc1)==null) {
					System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+"\") la variable substanceActive de med vaut null au lieu de \""+paramsConst[i][3]+"\"");
					noteConstructeur=0;
				} else {
					String substanceActive = (String)(fieldSubstanceActive.get(ptc1));
					if (noteConstructeur>0 && !substanceActive.equals(paramsConst[i][3])) {
						System.out.println("  Aie... apres med=new Medicament(\""+paramsConst[i][0]+"\", "+paramsConst[i][1]+", "+paramsConst[i][2]+", "+paramsConst[i][3]+"\") la variable substanceActive de med vaut \""+substanceActive+"\" au lieu de "+paramsConst[i][3]);
						noteConstructeur=0;
					} 
				}
			}
		} catch (Exception e) {
			System.out.println("   Exception levee ");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}


		//	System.out.println("noteListes="+noteListes);
		///	System.out.println("noteExtends="+noteExtends);
		//	System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
		//	System.out.println("noteConstructeurListes="+noteConstructeurListes);









//		Method mIsGenerique=null;
//		try {
//			Method[] methods = cMedicament.getDeclaredMethods();
//			for (Method m : methods) {
//				if (m.getName().equals("isGenerique")) {
//					mIsGenerique=m;
//				} 			
//			}
//		} catch (Exception e1) {
//		} 
//		if (mIsGenerique==null) {
//			noteIsGenerique=0;
//			System.out.println("   Aie... Je ne trouve pas l'accesseur isGenerique() de Medicament");
//		}
//		if (noteIsGenerique>0) {
//			if (mIsGenerique.getParameterCount()!=0) {
//				System.out.println("   Aie... Votre methode isGenerique ne devrait pas prendre de parametres");
//				noteIsGenerique=0;
//			}
//		}
//		if (noteIsGenerique>0) {
//			if (!mIsGenerique.getReturnType().equals(boolean.class)) {
//				System.out.println("   Aie... Votre methode isGenerique() n'a pas le type retour attendu");
//				noteIsGenerique=0;	
//			}
//		}
//		if (mIsGenerique!=null && !Modifier.isPublic(mIsGenerique.getModifiers())) {
//			System.out.println("   Aie... Votre methode isGenerique() n'a pas la visibilite attendue");
//			noteIsGenerique=0;	
//		}
//		if (mIsGenerique!=null) mIsGenerique.setAccessible(true);
//
//		if (fieldGenerique==null || mIsGenerique==null) {
//			noteIsGenerique=0;
//		}
//
//
//		Method mIsSurPrescription=null;
//		try {
//			Method[] methods = cMedicament.getDeclaredMethods();
//			for (Method m : methods) {
//				if (m.getName().equals("isSurPrescription")) {
//					mIsSurPrescription=m;
//				} 			
//			}
//		} catch (Exception e1) {
//		} 
//		if (fieldSurPrescription==null || mIsSurPrescription==null) {
//			noteIsSurPrescription=0;
//		}
//
//		if (mIsSurPrescription==null) {
//			noteIsSurPrescription=0;
//			System.out.println("   Aie... Je ne trouve pas l'accesseur isSurPrescription() de Medicament");
//		}
//		if (noteIsSurPrescription>0) {
//			if (mIsSurPrescription.getParameterCount()!=0) {
//				System.out.println("   Aie... Votre methode isSurPrescription ne devrait pas prendre de parametres");
//				noteIsSurPrescription=0;
//			}
//		}
//		if (noteIsSurPrescription>0) {
//			if (!mIsSurPrescription.getReturnType().equals(boolean.class)) {
//				System.out.println("   Aie... Votre methode isSurPrescription() n'a pas le type retour attendu");
//				noteIsSurPrescription=0;
//			}
//		}
//		if (mIsSurPrescription!=null && !Modifier.isPublic(mIsSurPrescription.getModifiers())) {
//			System.out.println("   Aie... Votre methode isSurPrescription() n'a pas la visibilite attendue");
//			noteIsSurPrescription=0;	
//		}
//		if (mIsSurPrescription!=null) mIsSurPrescription.setAccessible(true);




		Method mGetSubstanceActive=null;
		try {
			Method[] methods = cMedicament.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getSubstanceActive")) {
					mGetSubstanceActive=m;
				} 			
			}
		} catch (Exception e1) {
		} 
		if (fieldSubstanceActive==null || mGetSubstanceActive==null) {
			noteGetSubstanceActive=0;
		}

		if (mGetSubstanceActive==null) {
			noteGetSubstanceActive=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur getSubstanceActive() de Medicament");
		}
		if (noteGetSubstanceActive>0) {
			if (mGetSubstanceActive.getParameterCount()!=0) {
				System.out.println("   Aie... Votre methode getSubstanceActive ne devrait pas prendre de parametres");
				noteGetSubstanceActive=0;
			}
		}
		if (noteGetSubstanceActive>0) {
			if (!mGetSubstanceActive.getReturnType().equals(String.class)) {
				System.out.println("   Aie... Votre methode getSubstanceActive() n'a pas le type retour attendu");
				noteGetSubstanceActive=0;
			}
		}
		if (mGetSubstanceActive!=null && !Modifier.isPublic(mGetSubstanceActive.getModifiers())) {
			System.out.println("   Aie... Votre methode getSubstanceActive() n'a pas la visibilite attendue");
			noteGetSubstanceActive=0;	
		}
		if (mGetSubstanceActive!=null) mGetSubstanceActive.setAccessible(true);



		Object[] argssn = {};
		for (int i=0; (//noteIsGenerique>0 || noteIsSurPrescription>0 || 
				noteGetSubstanceActive>0) && i<paramsConst.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);
			pt1 = instantiator.newInstance();
			boolean resIsGenerique=false;
			boolean resIsSurPrescription=false;
			String resGetSubstanceActive=null;
			try {
				if (fieldNom!=null && noteExtends>0) fieldNom.set(pt1,paramsConst[i][0]);
				if (fieldPrix!=null && noteExtends>0) fieldPrix.set(pt1,paramsConst[i][1]);
				if (fieldQuantite!=null && noteExtends>0) fieldQuantite.set(pt1,paramsConst[i][2]);
//				if (fieldGenerique!=null) fieldGenerique.set(pt1,paramsConst[i][3]);
//				if (fieldSurPrescription!=null) fieldSurPrescription.set(pt1,paramsConst[i][4]);
				if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pt1,paramsConst[i][3]);
//				if (noteIsGenerique>0) {
//					if ( mIsGenerique!=null) {
//						resIsGenerique=(boolean)(mIsGenerique.invoke(pt1,  argssn));
//					}
//					if (resIsGenerique!=(boolean)paramsConst[i][3]) {
//						System.out.println("   Aie... votre accesseur isGenerique() n'est pas correct. Il retourne \""+resIsGenerique+"\" sur une instance qui a "+paramsConst[i][3]+" pour valeur de sa variable generique");
//						noteIsGenerique=0;
//					}
//				}
				if (noteGetSubstanceActive>0) {
					if ( mGetSubstanceActive!=null) {
						resGetSubstanceActive=(String)(mGetSubstanceActive.invoke(pt1,  argssn));
					}
					if ( resGetSubstanceActive==null ) {
						System.out.println("   Aie... votre accesseur getSubstanceActive() n'est pas correct. Il retourne null sur une instance ayant \""+paramsConst[i][3]+"\" pour substance active");
						noteGetSubstanceActive=0;
					} else if (!resGetSubstanceActive.equals(paramsConst[i][3])) {
						System.out.println("   Aie... votre accesseur getSubstanceActive() n'est pas correct. Il retourne \""+resGetSubstanceActive+"\" sur une instance ayant \""+paramsConst[i][3]+"\" pour substance active");
						noteGetSubstanceActive=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				System.out.println("   Aie... aucune methode de Medicament ne devrait lever d'exception");
				noteGetSubstanceActive=0;
//				noteIsGenerique=0;
//				noteIsSurPrescription=0;
			}
		}




		Method mIsSubstituable=null;
		try {
			Method[] methods = cMedicament.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("isSubstituable")) {
					mIsSubstituable=m;
				} 			
			}
		} catch (Exception e1) {
		} 
		if (fieldSubstanceActive==null || mIsSubstituable==null) {
			noteIsSubstituable=0;
		}

		if (mIsSubstituable==null) {
			noteIsSubstituable=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur isSubstituable(Medicament) de Medicament");
		}
		if (noteIsSubstituable>0) {
			if (mIsSubstituable.getParameterCount()!=1) {
				System.out.println("   Aie... Votre methode isSubstituable devrait prendre un unique parametre de type Medicament");
				noteIsSubstituable=0;
			} else if (!mIsSubstituable.getParameters()[0].getType().equals(cMedicament)) {
				System.out.println("   Aie... Le parametre de votre methode isSubstituable devrait etre du type Medicament");
				noteIsSubstituable=0;
			}
		}
		if (noteIsSubstituable>0) {
			if (!mIsSubstituable.getReturnType().equals(boolean.class)) {
				System.out.println("   Aie... Votre methode isSubstituable(Medicament) n'a pas le type retour attendu");
				noteIsSubstituable=0;
			}
		}
		if (mIsSubstituable!=null && !Modifier.isPublic(mIsSubstituable.getModifiers())) {
			System.out.println("   Aie... Votre methode isSubstituable(Medicament) n'a pas la visibilite attendue");
			noteIsSubstituable=0;	
		}
		if (mIsSubstituable!=null) mIsSubstituable.setAccessible(true);


		Object[] oracle = {true,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,true,true,true,false,false,false,false,false,false,false,false,true,true,true,true,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,false,false,false,false,true,true,false,false,false,false,true,false,false,false,false,false,false,false,false,true,true,true,true,true,true};
		int ioracle=0;
		Object[] argss = new Object[1];
		for (int i=0; (noteIsSubstituable>0) && i<paramsConst.length-1; i++) {
			for (int j=i+1; (noteIsSubstituable>0) && j<paramsConst.length; j++) {
				Object pt1=null;
				Object pt2=null;
				Objenesis objenesis = new ObjenesisStd(); 
				ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);
				pt1 = instantiator.newInstance();
				pt2 = instantiator.newInstance();
				boolean resIsSubstituable=false;

				try {
					if (fieldNom!=null && noteExtends>0) fieldNom.set(pt1,paramsConst[i][0]);
					if (fieldPrix!=null && noteExtends>0) fieldPrix.set(pt1,paramsConst[i][1]);
					if (fieldQuantite!=null && noteExtends>0) fieldQuantite.set(pt1,paramsConst[i][2]);
	//				if (fieldGenerique!=null) fieldGenerique.set(pt1,paramsConst[i][3]);
	//				if (fieldSurPrescription!=null) fieldSurPrescription.set(pt1,paramsConst[i][4]);
					if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pt1,paramsConst[i][3]);
					if (fieldNom!=null && noteExtends>0) fieldNom.set(pt2,paramsConst[j][0]);
					if (fieldPrix!=null && noteExtends>0) fieldPrix.set(pt2,paramsConst[j][1]);
					if (fieldQuantite!=null && noteExtends>0) fieldQuantite.set(pt2,paramsConst[j][2]);
	//				if (fieldGenerique!=null) fieldGenerique.set(pt2,paramsConst[j][3]);
	//				if (fieldSurPrescription!=null) fieldSurPrescription.set(pt2,paramsConst[j][4]);
					if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pt2,paramsConst[j][3]);
					if (noteIsSubstituable>0) {
						if ( mIsSubstituable!=null) {
							argss[0]=pt2;
							resIsSubstituable=(boolean)(mIsSubstituable.invoke(pt1,  argss));
						}
						//System.out.print(resIsSubstituable+",");
						if (resIsSubstituable!=(boolean)oracle[ioracle]) {
							System.out.println("   Aie... m1.isSubsttituable(m2) avec m1.substanceActive==\""+paramsConst[i][3]+"\" et m2.substanceActive==\""+paramsConst[j][3]+"\" retourne "+resIsSubstituable+" au lieu de "+oracle[ioracle]);
							noteIsSubstituable=0;
						}
						ioracle++;
					}
				} catch (Exception e) {
					if (e instanceof InvocationTargetException) {
						e.getCause().printStackTrace();
					} else {
						e.printStackTrace();
					}
					System.out.println("   Aie... aucune methode de Medicament ne devrait lever d'exception");
					noteIsSubstituable=0;
				}
			}
		}			

		if (//noteIsGenerique+noteIsSurPrescription+
				noteGetSubstanceActive+noteExtends+
				noteConstructeur+noteRienDAutre
				//+noteGenerique
				+noteSubstanceActive//+noteSurPrescription
				+noteIsSubstituable==100) {
			System.out.println("   Ok. Votre code passe ce test avec succes.");
		}

		return //noteIsGenerique+noteIsSurPrescription+
				noteGetSubstanceActive+noteExtends+
				noteConstructeur+noteRienDAutre
				//+noteGenerique
				+noteSubstanceActive//+noteSurPrescription
				+noteIsSubstituable;
	}


	//			public static int test08PharmacieDeclarationVariables() {
	//				int noteMedicaments=15;
	//				int noteImplements=40;
	//				int noteRienDAutre=15;
	//				System.out.println("   Test verifiant la declaration de la classe Pharmacie : ");
	//				Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
	//				if (cPharmacie==null) {
	//					System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
	//					return 0;
	//				}
	//				Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//				Field[] tf = cPharmacie.getDeclaredFields();
	//				for (Field f : tf) {
	//					if (Modifier.isFinal(f.getModifiers())) {
	//						fieldConst=f;
	//					}
	//					if (Modifier.isStatic(f.getModifiers())) {
	//						fieldStatic=f;
	//					} 
	//					if ( f.getType().equals(LinkedList.class)) {
	//						if (fieldMedicaments==null) {
	//							fieldMedicaments=f;
	//						} else {
	//							fieldOther=f;
	//						}
	//					} else {
	//						fieldOther=f;
	//					}
	//				}
	//				if (fieldConst!=null) {
	//					System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
	//					System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//					noteRienDAutre=0;
	//				}
	//				if (fieldStatic!=null) {
	//					System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
	//					System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//					noteRienDAutre=0;
	//				}
	//				if (fieldOther!=null) {
	//					System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
	//					System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
	//					noteRienDAutre=0;
	//				}
	//				if (fieldMedicaments==null) {
	//					System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
	//					noteMedicaments=0;
	//				} else {
	//					if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
	//						System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//						System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
	//						noteMedicaments=noteMedicaments/2;
	//					}
	//					if (!fieldMedicaments.getName().equals("prix")) {
	//						System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//						System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
	//						noteMedicaments=noteMedicaments/2;
	//					}
	//				}
	//				if (noteMedicaments==0) {
	//					noteRienDAutre=0;
	//				}
	//				Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
	//				if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
	//					System.out.println("   Aie... Produit n'implemente pas l'interface IPharmacie");
	//					noteImplements=0;
	//				}
	//				if (noteMedicaments+noteRienDAutre+noteImplements==100) {
	//					System.out.println("   Ok. Votre code passe ce test avec succes.");
	//				}
	//				return noteMedicaments+noteImplements+noteRienDAutre;
	//			}
	//




	public static int test08PharmacieConstructeur() {
		System.out.println("   Test verifiant la declaration Pharmacie et le bon fonctionnement de son constructeur");
		int noteDeclarationConst=10;
		int noteInitMedicaments=30;
		int noteMedicaments=35;
		//				int noteImplements=40;
		int noteRienDAutre=25;
		//				Object[][] paramsConst = {
		//				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
		//				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
		//				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
		//				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
		//				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
		//				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
		//				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
		//				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
		//				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
		//				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
		//				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
		//				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
		//				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
		//				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
		//				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
		//			};

		//System.out.println("   Test verifiant la declaration de la classe Pharmacie : ");
		Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
		if (cPharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cPharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(LinkedList.class) || f.getType().equals(List.class) ) {
				if (fieldMedicaments==null) {
					fieldMedicaments=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
			noteRienDAutre=0;
		}
		if (fieldMedicaments==null) {
			System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
			noteMedicaments=0;
		} else {
			if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
				noteMedicaments=noteMedicaments/2;
			}
			if (!fieldMedicaments.getName().equals("medicaments")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
				noteMedicaments=noteMedicaments/2;
			}
		}
		if (noteMedicaments==0) {
			noteRienDAutre=0;
		}
		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);



		//				
		//				
		//				Class<?> cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		//				if (cMedicament==null) {
		//					System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
		//					return 0;
		//				}
		//				Field fieldATC=null, fieldGenerique=null, fieldSurPrescription=null, fieldSubstanceActive=null;
		//				fieldConst=null;
		//				fieldStatic=null;
		//				fieldOther=null;
		//		
		//				tf = cMedicament.getDeclaredFields();
		//				for (Field f : tf) {
		//					if (Modifier.isFinal(f.getModifiers())) {
		//						fieldConst=f;
		//					}
		//					if (Modifier.isStatic(f.getModifiers())) {
		//						fieldStatic=f;
		//					} 
		//					if ( f.getName().equals("generique")) {
		//						if (fieldGenerique==null) {
		//							fieldGenerique=f;
		//						} else {
		//							fieldOther=f;
		//						}
		//					} else if ( f.getName().equals("surPrescription")) {
		//						if (fieldSurPrescription==null) {
		//							fieldSurPrescription=f;
		//						} else {
		//							fieldOther=f;
		//						}
		//					} else if ( f.getName().equals("substanceActive")) {
		//						if (fieldSubstanceActive==null) {
		//							fieldSubstanceActive=f;
		//						} else {
		//							fieldOther=f;
		//						}
		//					} else if ( f.getName().equals("atc")) {
		//						if (fieldATC==null) {
		//							fieldATC=f;
		//						} else {
		//							fieldOther=f;
		//						}
		//					} else{
		//						fieldOther=f;
		//					}
		//				}
		//				if (fieldConst!=null) {
		//					System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
		//					System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		//				}
		//				if (fieldStatic!=null) {
		//					System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
		//					System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		//				}
		//				if (fieldOther!=null) {
		//					System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
		//					System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		//					noteRienDAutre=0;
		//				}
		//				if (fieldGenerique==null) {
		//					System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
		//				} else {
		//					if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
		//						System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
		//						System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
		//					}
		//					if (!fieldGenerique.getType().equals(boolean.class)) {
		//						System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
		//					}
		//				}
		//				if (fieldSurPrescription==null) {
		//					System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
		//				} else {
		//					if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
		//						System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
		//						System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
		//					}
		//					if (!fieldSurPrescription.getType().equals(boolean.class)) {
		//						System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
		//					}
		//				}
		//				if (fieldSubstanceActive==null) {
		//					System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
		//				} else {
		//					if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
		//						System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
		//						System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
		//					}
		//					if (!fieldSubstanceActive.getType().equals(String.class)) {
		//						System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
		//					}
		//				}
		//				if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
		//				if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		//				if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);
		//
		//				
		//				
		//				
		//				
		//				
		//				
		//				
		//				Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		//				if (cProduit==null) {
		//					System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
		//					return 0;
		//				}
		//				Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		//				fieldConst=null;
		//				fieldStatic=null;
		//				fieldOther=null;
		//
		//				tf = cProduit.getDeclaredFields();
		//				for (Field f : tf) {
		//					if (Modifier.isFinal(f.getModifiers())) {
		//						fieldConst=f;
		//					}
		//					if (Modifier.isStatic(f.getModifiers())) {
		//						fieldStatic=f;
		//					} 
		//					if ( f.getType().equals(String.class)) {
		//						if (fieldNom==null) {
		//							fieldNom=f;
		//						} else {
		//							fieldOther=f;
		//						}
		//					} else if ( f.getType().equals(double.class)) {
		//						if (fieldPrix==null) {
		//							fieldPrix=f;
		//						} else {
		//							fieldOther=f;
		//						}
		//					} else if ( f.getType().equals(int.class)) {
		//						if (fieldQuantite==null) {
		//							fieldQuantite=f;
		//						} else {
		//							fieldQuantite=f;
		//						}
		//					} else{
		//						fieldOther=f;
		//					}
		//				}
		//				if (fieldConst!=null) {
		//					System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
		//					System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		//				}
		//				if (fieldStatic!=null) {
		//					System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
		//					System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		//				}
		//				if (fieldOther!=null) {
		//					System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
		//					System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		//				}
		//				if (fieldPrix==null) {
		//					System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		//				} else {
		//					if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
		//						System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
		//						System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
		//						System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
		//					}
		//					if (!fieldPrix.getName().equals("prix")) {
		//						System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
		//						System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
		//					}
		//				}
		//				if (fieldNom==null) {
		//					System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		//				} else {
		//					if (!Modifier.isPrivate(fieldNom.getModifiers())) {
		//						System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
		//						System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
		//						System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
		//					}
		//					if (!fieldNom.getName().equals("nom")) {
		//						System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
		//						System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
		//					}
		//				}
		//				if (fieldQuantite==null) {
		//					System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		//				} else {
		//					if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
		//						System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
		//						System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
		//						System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
		//					}
		//					if (!fieldQuantite.getName().equals("quantite")) {
		//						System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
		//						System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
		//					}
		//				}
		//				Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		//				if (!clIProduit.isAssignableFrom(cProduit)) {
		//					System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		//				}
		//
		//				if (fieldPrix!=null) fieldPrix.setAccessible(true);
		//				if (fieldNom!=null) fieldNom.setAccessible(true);
		//				if (fieldQuantite!=null) fieldQuantite.setAccessible(true);
		//				
		//				if (!Reflexion.extendsClass(cMedicament, cProduit)) {
		//					System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
		//				}

		//				Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
		//				if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
		//					System.out.println("   Aie... Pharmacie n'implemente pas l'interface IPharmacie");
		//					noteImplements=0;
		//				}



		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);
		Class<?>[] arVide = {};
		//				Class<?>[] arStringDouble = {String.class, double.class, int.class};
		Constructor<?> constC1=null;
		try {
			constC1 = cPharmacie.getDeclaredConstructor(arVide);
		} catch (NoSuchMethodException e) {
			constC1=null;
		} catch (SecurityException e) {
			constC1=null;
		}
		if (constC1==null) {
			System.out.println("   Aie... je ne trouve pas le constructeur sans parametre de Pharmacie ");
			noteDeclarationConst=0;
			noteInitMedicaments=0;
		} 
		if (!Modifier.isPublic(constC1.getModifiers())) {
			System.out.println("   Aie... Le constructeur de Pharmacie devrait etre public");
			noteDeclarationConst=noteDeclarationConst/2;
		}
		constC1.setAccessible(true);
		Object ptc1=null;
		Object[] params  = {};
		try {
			//				for (int i=0; i<PARAM_PRODUITS.length; i++) {
			ptc1 = (constC1.newInstance(params));//PARAM_PRODUITS[i]));
			if (fieldMedicaments==null) {
				noteInitMedicaments=0;
			}else if (fieldMedicaments.get(ptc1)==null) {
				System.out.println("  Aie... votre constructeur initialise la variable "+fieldMedicaments.getName()+" a null \n");
				noteInitMedicaments=0;
			} else {
				LinkedList<?> med = (LinkedList<?>)(fieldMedicaments.get(ptc1));
				if (noteInitMedicaments>0 && med.size()!=0) {
					System.out.println("  Aie... apres new Pharmacie() la variable "+fieldMedicaments.getName()+" vaut \""+med+"\" au d'etre une liste vide");
					noteInitMedicaments=0;
				} 
			}
			//				}
		} catch (Exception e) {
			System.out.println(" Votre code leve une exception dans un cas ou il ne devrait pas le faire ");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (noteDeclarationConst+noteInitMedicaments+noteMedicaments+noteRienDAutre==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclarationConst+noteInitMedicaments+noteMedicaments+noteRienDAutre);
	}



	@SuppressWarnings("unchecked")
	public static int test09PharmacieGetMedicaments() {
		System.out.println("   Test verifiant le bon fonctionnement de l'accesseur getMedicaments de Pharmacie");
		int noteDeclaration=10;
		int noteComportement=90;
		Object[][] paramsConst = {
				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
		};

		Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
		if (cPharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cPharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(LinkedList.class) || f.getType().equals(List.class)) {
				if (fieldMedicaments==null) {
					fieldMedicaments=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
		}
		if (fieldMedicaments==null) {
			System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
		} else {
			if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
			}
			if (!fieldMedicaments.getName().equals("medicaments")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
			}
		}
		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);





		Class<?> cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		if (cMedicament==null) {
			System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldATC=null, fieldGenerique=null, fieldSurPrescription=null, fieldSubstanceActive=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cMedicament.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().equals("generique")) {
				if (fieldGenerique==null) {
					fieldGenerique=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("surPrescription")) {
				if (fieldSurPrescription==null) {
					fieldSurPrescription=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("substanceActive")) {
				if (fieldSubstanceActive==null) {
					fieldSubstanceActive=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("atc")) {
				if (fieldATC==null) {
					fieldATC=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldGenerique==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
		} else {
			if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
			}
			if (!fieldGenerique.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSurPrescription==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
		} else {
			if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
			}
			if (!fieldSurPrescription.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSubstanceActive==null) {
			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
		} else {
			if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
			}
			if (!fieldSubstanceActive.getType().equals(String.class)) {
				System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
		if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);








		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cMedicament, cProduit)) {
			System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
		}

		Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
		if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
			System.out.println("   Aie... Pharmacie n'implemente pas l'interface IPharmacie");
			return 0;
		}
		Object[] pts=new Object[paramsConst.length];
		LinkedList<Medicament> meds = new LinkedList<Medicament>();
		try {
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);

			for (int i=0; i<paramsConst.length; i++) {
				pts[i] = instantiator.newInstance();
				if (fieldNom!=null) fieldNom.set(pts[i],paramsConst[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pts[i],paramsConst[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pts[i],paramsConst[i][2]);
				if (fieldGenerique!=null) fieldGenerique.set(pts[i],paramsConst[i][3]);
				if (fieldSurPrescription!=null) fieldSurPrescription.set(pts[i],paramsConst[i][4]);
				if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pts[i],paramsConst[i][5]);
				meds.add((Medicament)pts[i]);


			}
		} catch (Exception e) {
			System.out.println("Exception levee durant la creation des medicaments. Impossible de tester");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);
		Method mGetMedicaments=null;
		try {
			Method[] methods = cPharmacie.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getMedicaments")) {
					mGetMedicaments=m;
				} 			}
		} catch (Exception e1) {
		} 
		if (mGetMedicaments==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode getMedicaments() de Pharmacie");
		}
		if (noteDeclaration>0) {
			if (mGetMedicaments.getParameterCount()!=0) {
				System.out.println("   Aie... Votre methode getMedicaments ne devrait prendre aucun parametre");
				noteDeclaration=0;
				noteComportement=0;
			}
		}
		if (mGetMedicaments!=null && !Modifier.isPublic(mGetMedicaments.getModifiers())) {
			System.out.println("   Aie... Votre methode getMedicaments() n'a pas la visibilite attendue");
			noteDeclaration = noteDeclaration/2;
		}
		if (mGetMedicaments!=null) mGetMedicaments.setAccessible(true);

//		String[] paramsGetMedicament = new String[paramsConst.length];
//		String[] paramsGetMedicamentInconnus= {"carotte", "salade", "oignon", "navet", "laitue", "poireau", "tomate", "celeri", "potiron", "patate", "choux", "poivron", "banane", "poire", "pomme", "courgette", "piment", "endive"};
//		for (int j=0;j<paramsConst.length; j++) {
//			paramsGetMedicament[j]=meds.get(j).getNom();
//		}
		Object[] arg0 = {};//new Object[1];
//		for (int i=0; i<paramsGetMedicament.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiatorP= objenesis.getInstantiatorOf(cPharmacie);
			pt1 = instantiatorP.newInstance();
			LinkedList<Medicament> resGetMedicament=null;
			try {
				if (fieldMedicaments!=null) fieldMedicaments.set(pt1,meds);
				if (noteComportement>0) {
					if ( mGetMedicaments!=null) {
						//arg1[0]=paramsGetMedicament[i];
						resGetMedicament=(LinkedList<Medicament>)(mGetMedicaments.invoke(pt1,  arg0));
					}
					if ( resGetMedicament==null ) {
						System.out.println("   Aie... votre accesseur getMedicaments n'est pas correct. Il retourne null alors que la pharmacie dispose d'une liste de medicaments");
						noteComportement=0;
					} else if (resGetMedicament!=meds) {//!resGetMedicament.equals(meds.get(i))) {
							System.out.println("   Aie... getMedicaments() retourne une liste qui n'est pas celle de la pharmacie. Veillez a retourner la liste des medicaments de la pharmacie et pas une copie de cette liste");
						noteComportement=0;
					}
				}
				LinkedList<Medicament> lv = new LinkedList<Medicament>();
				if (fieldMedicaments!=null) fieldMedicaments.set(pt1,lv);
				if (noteComportement>0) {
					if ( mGetMedicaments!=null) {
						//arg1[0]=paramsGetMedicament[i];
						resGetMedicament=(LinkedList<Medicament>)(mGetMedicaments.invoke(pt1,  arg0));
					}
					if ( resGetMedicament==null ) {
						System.out.println("   Aie... votre accesseur getMedicaments n'est pas correct. Il retourne null alors que la pharmacie dispose d'une liste de medicaments");
						noteComportement=0;
					} else if (resGetMedicament!=lv) {
							System.out.println("   Aie... getMedicaments() retourne une liste qui n'est pas celle de la pharmacie. Veillez a retourner la liste des medicaments de la pharmacie et pas une copie de cette liste");
						noteComportement=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportement=0;
			}
//		}

		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteComportement);
	}






	public static int test10PharmacieGetMedicament() {
		System.out.println("   Test verifiant le bon fonctionnement de la methode getMedicament de Pharmacie");
		int noteDeclaration=10;
		int noteComportement=90;
		Object[][] paramsConst = {
				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
		};

		Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
		if (cPharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cPharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(LinkedList.class)|| f.getType().equals(List.class)) {
				if (fieldMedicaments==null) {
					fieldMedicaments=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
		}
		if (fieldMedicaments==null) {
			System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
		} else {
			if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
			}
			if (!fieldMedicaments.getName().equals("medicaments")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
			}
		}
		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);





		Class<?> cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		if (cMedicament==null) {
			System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldATC=null, fieldGenerique=null, fieldSurPrescription=null, fieldSubstanceActive=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cMedicament.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().equals("generique")) {
				if (fieldGenerique==null) {
					fieldGenerique=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("surPrescription")) {
				if (fieldSurPrescription==null) {
					fieldSurPrescription=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("substanceActive")) {
				if (fieldSubstanceActive==null) {
					fieldSubstanceActive=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("atc")) {
				if (fieldATC==null) {
					fieldATC=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldGenerique==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
		} else {
			if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
			}
			if (!fieldGenerique.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSurPrescription==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
		} else {
			if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
			}
			if (!fieldSurPrescription.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSubstanceActive==null) {
			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
		} else {
			if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
			}
			if (!fieldSubstanceActive.getType().equals(String.class)) {
				System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
		if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);








		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cMedicament, cProduit)) {
			System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
		}

		//				Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
		//				if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
		//					System.out.println("   Aie... Pharmacie n'implemente pas l'interface IPharmacie");
		//					noteImplements=0;
		//				}
		Object[] pts=new Object[paramsConst.length];
		LinkedList<Medicament> meds = new LinkedList<Medicament>();
		try {
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);

			for (int i=0; i<paramsConst.length; i++) {
				pts[i] = instantiator.newInstance();
				if (fieldNom!=null) fieldNom.set(pts[i],paramsConst[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pts[i],paramsConst[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pts[i],paramsConst[i][2]);
				if (fieldGenerique!=null) fieldGenerique.set(pts[i],paramsConst[i][3]);
				if (fieldSurPrescription!=null) fieldSurPrescription.set(pts[i],paramsConst[i][4]);
				if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pts[i],paramsConst[i][5]);
				meds.add((Medicament)pts[i]);


			}
		} catch (Exception e) {
			System.out.println("Exception levee durant la creation des medicaments. Impossible de tester");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);
		Method mGetMedicament=null;
		try {
			Method[] methods = cPharmacie.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getMedicament")) {
					mGetMedicament=m;
				} 			}
		} catch (Exception e1) {
		} 
		if (mGetMedicament==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode getMedicament() de Pharmacie");
		}
		if (noteDeclaration>0) {
			if (mGetMedicament.getParameterCount()!=1 || !mGetMedicament.getParameters()[0].getType().equals(String.class)) {
				System.out.println("   Aie... Votre methode getMedicament devrait prendre un unique parametre de type String");
				noteDeclaration=0;
				noteComportement=0;
			}
		}
		if (mGetMedicament!=null && !Modifier.isPublic(mGetMedicament.getModifiers())) {
			System.out.println("   Aie... Votre methode getMedicament() n'a pas la visibilite attendue");
			noteDeclaration = noteDeclaration/2;
		}
		if (mGetMedicament!=null) mGetMedicament.setAccessible(true);

		String[] paramsGetMedicament = new String[paramsConst.length];
		String[] paramsGetMedicamentInconnus= {"carotte", "salade", "oignon", "navet", "laitue", "poireau", "tomate", "celeri", "potiron", "patate", "choux", "poivron", "banane", "poire", "pomme", "courgette", "piment", "endive"};
		for (int j=0;j<paramsConst.length; j++) {
			paramsGetMedicament[j]=((IProduit)(meds.get(j))).getNom();
		}
		Object[] arg1 = new Object[1];
		for (int i=0; i<paramsGetMedicament.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiatorP= objenesis.getInstantiatorOf(cPharmacie);
			pt1 = instantiatorP.newInstance();
			Medicament resGetMedicament=null;
			try {
				if (fieldMedicaments!=null) fieldMedicaments.set(pt1,meds);
				if (noteComportement>0) {
					if ( mGetMedicament!=null) {
						arg1[0]=paramsGetMedicament[i];
						resGetMedicament=(Medicament)(mGetMedicament.invoke(pt1,  arg1));
					}
					if ( resGetMedicament==null ) {
						System.out.println("   Aie... votre accesseur getMedicament n'est pas correct. Il retourne null alors qu'il existe dans la pharmacie un medicament portant le nom recherche");
						noteComportement=0;
					} else if (resGetMedicament!=meds.get(i)) {//!resGetMedicament.equals(meds.get(i))) {
						if (!resGetMedicament.equals(meds.get(i))) {
							System.out.println("   Aie... getMedicament(\""+paramsGetMedicament[i]+"\" retourne un medicament portant le nom recherche mais different de celui present dans la pharmacie.\n Attention : retournez le medicament present dans la pharmacie et pas une copie de celui ci");
						} else {
							System.out.println("   Aie... getMedicament(\""+paramsGetMedicament[i]+"\" retourne un medicament different de celui present dans la pharmacie");
						}
						noteComportement=0;
					}
				}
				if (noteComportement>0) {
					if ( mGetMedicament!=null) {
						arg1[0]=paramsGetMedicamentInconnus[i];
						resGetMedicament=(Medicament)(mGetMedicament.invoke(pt1,  arg1));
					}
					if ( resGetMedicament!=null ) {
						System.out.println("   Aie... getMedicament(\""+paramsGetMedicamentInconnus[i]+"\") ne retourne pas null alors qu'il n'existe aucun medicament portant ce nom dans la pharmacie");
						noteComportement=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportement=0;
			}
		}

		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteComportement);
	}






	public static int test11PharmacieGetQuantiteMedicament() {
		System.out.println("   Test verifiant le bon fonctionnement de la methode getQuantiteMedicament de Pharmacie");
		int noteDeclaration=10;
		int noteComportement=90;
		Object[][] paramsConst = {
				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
		};

		Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
		if (cPharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cPharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(LinkedList.class)||f.getType().equals(List.class)) {
				if (fieldMedicaments==null) {
					fieldMedicaments=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
		}
		if (fieldMedicaments==null) {
			System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
		} else {
			if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
			}
			if (!fieldMedicaments.getName().equals("medicaments")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
			}
		}
		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);





		Class<?> cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		if (cMedicament==null) {
			System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldATC=null, fieldGenerique=null, fieldSurPrescription=null, fieldSubstanceActive=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cMedicament.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().equals("generique")) {
				if (fieldGenerique==null) {
					fieldGenerique=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("surPrescription")) {
				if (fieldSurPrescription==null) {
					fieldSurPrescription=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("substanceActive")) {
				if (fieldSubstanceActive==null) {
					fieldSubstanceActive=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("atc")) {
				if (fieldATC==null) {
					fieldATC=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldGenerique==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
		} else {
			if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
			}
			if (!fieldGenerique.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSurPrescription==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
		} else {
			if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
			}
			if (!fieldSurPrescription.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSubstanceActive==null) {
			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
		} else {
			if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
			}
			if (!fieldSubstanceActive.getType().equals(String.class)) {
				System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
		if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);








		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
			System.out.println("          --> Impossible de tester");
			return 0;
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cMedicament, cProduit)) {
			System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
			System.out.println("          --> Impossible de tester");
			return 0;
		}

		//				Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
		//				if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
		//					System.out.println("   Aie... Pharmacie n'implemente pas l'interface IPharmacie");
		//					noteImplements=0;
		//				}
		Object[] pts=new Object[paramsConst.length];
		LinkedList<Medicament> meds = new LinkedList<Medicament>();
		try {
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);

			for (int i=0; i<paramsConst.length; i++) {
				pts[i] = instantiator.newInstance();
				if (fieldNom!=null) fieldNom.set(pts[i],paramsConst[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pts[i],paramsConst[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pts[i],paramsConst[i][2]);
				if (fieldGenerique!=null) fieldGenerique.set(pts[i],paramsConst[i][3]);
				if (fieldSurPrescription!=null) fieldSurPrescription.set(pts[i],paramsConst[i][4]);
				if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pts[i],paramsConst[i][5]);
				meds.add((Medicament)pts[i]);


			}
		} catch (Exception e) {
			System.out.println("Exception levee durant la creation des medicaments. Impossible de tester");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);
		Method mGetQuantiteMedicament=null;
		try {
			Method[] methods = cPharmacie.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getQuantiteMedicament")) {
					mGetQuantiteMedicament=m;
				} 			}
		} catch (Exception e1) {
		} 
		if (mGetQuantiteMedicament==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode getQuantiteMedicament() de Pharmacie");
		}
		if (noteDeclaration>0) {
			if (mGetQuantiteMedicament.getParameterCount()!=1 || !mGetQuantiteMedicament.getParameters()[0].getType().equals(String.class) || !mGetQuantiteMedicament.getReturnType().equals(int.class)) {
				System.out.println("   Aie... Votre methode getQuantiteMedicament devrait prendre un unique parametre de type String et retourne un entier");
				noteDeclaration=0;
				noteComportement=0;
			}
		}
		if (mGetQuantiteMedicament!=null && !Modifier.isPublic(mGetQuantiteMedicament.getModifiers())) {
			System.out.println("   Aie... Votre methode getQuantiteMedicament() n'a pas la visibilite attendue");
			noteDeclaration = noteDeclaration/2;
		}
		if (mGetQuantiteMedicament!=null) mGetQuantiteMedicament.setAccessible(true);

		String[] paramsGetMedicament = new String[paramsConst.length];
		String[] paramsGetMedicamentInconnus= {"carotte", "salade", "oignon", "navet", "laitue", "poireau", "tomate", "celeri", "potiron", "patate", "choux", "poivron", "banane", "poire", "pomme", "courgette", "piment", "endive"};
		int[] oracle={84, 32, 60, 35, 95, 35, 32, 47, 38, 34, 26, 16,8,18,14};

		

		for (int j=0;j<paramsConst.length; j++) {
			paramsGetMedicament[j]=((IProduit)(meds.get(j))).getNom();
		}
		Object[] arg1 = new Object[1];
		for (int i=0; i<paramsGetMedicament.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiatorP= objenesis.getInstantiatorOf(cPharmacie);
			pt1 = instantiatorP.newInstance();
			int resGetMedicament=0;
			try {
				if (fieldMedicaments!=null) fieldMedicaments.set(pt1,meds);
				if (noteComportement>0) {
					if ( mGetQuantiteMedicament!=null) {
						arg1[0]=paramsGetMedicament[i];
						resGetMedicament=(int)(mGetQuantiteMedicament.invoke(pt1,  arg1));
					}
//					if ( resGetMedicament==null ) {
//						System.out.println("   Aie... votre accesseur getQuantiteMedicament n'est pas correct. Il retourne null alors qu'il existe dans la pharmacie un medicament portant le nom recherche");
//						noteComportement=0;
//					} else
						if (resGetMedicament!=oracle[i]) {//meds.get(i)) {//!resGetMedicament.equals(meds.get(i))) {
							System.out.println("   Aie... getQuantiteMedicament(\""+paramsGetMedicament[i]+"\" retourne "+resGetMedicament+" au lieu de "+oracle[i]);
						
						noteComportement=0;
						
					}
				}
				if (noteComportement>0) {
					if ( mGetQuantiteMedicament!=null) {
						arg1[0]=paramsGetMedicamentInconnus[i];
						resGetMedicament=(int)(mGetQuantiteMedicament.invoke(pt1,  arg1));
					}
					if ( resGetMedicament!=0 ) {
						System.out.println("   Aie... getQuantiteMedicament(\""+paramsGetMedicamentInconnus[i]+"\") retourne "+resGetMedicament+" au lieu de 0.\n getQuantiteMedicament doit retourner 0 si il n'existe pas de medicament portant le nom recherche");
						noteComportement=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportement=0;
			}
		}

		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteComportement);
	}



	
	
	
	


	@SuppressWarnings("unchecked")
	public static int test12PharmacieAjouter() {
		System.out.println("   Test verifiant le bon fonctionnement de la methode ajouter(Medicament) de Pharmacie");
		int noteDeclaration=10;
		int noteComportement=90;
		Object[][] paramsConst = {
				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
		};
		int[][] oracle = {
				{84, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{84,32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{84,32,60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{84,32,60,35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{84,32,60,35,95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{84,32,60,35,95,35, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{84,32,60,35,95,35,32, 0, 0, 0, 0, 0, 0, 0, 0},
				{84,32,60,35,95,35,32,47, 0, 0, 0, 0, 0, 0, 0},
				{84,32,60,35,95,35,32,47,38, 0, 0, 0, 0, 0, 0},
				{84,32,60,35,95,35,32,47,38,34, 0, 0, 0, 0, 0},
				{84,32,60,35,95,35,32,47,38,34,26, 0, 0, 0, 0},
				{84,32,60,35,95,35,32,47,38,34,26,16, 0, 0, 0},
				{84,32,60,35,95,35,32,47,38,34,26,16, 8, 0, 0},
				{84,32,60,35,95,35,32,47,38,34,26,16, 8,18, 0},
				{84,32,60,35,95,35,32,47,38,34,26,16, 8,18,14},
				{168,32,60,35,95,35,32,47,38,34,26,16, 8,18,14},
				{168,64,60,35,95,35,32,47,38,34,26,16, 8,18,14},
				{168,64,120,35,95,35,32,47,38,34,26,16, 8,18,14},
				{168,64,120,70,95,35,32,47,38,34,26,16, 8,18,14},
				{168,64,120,70,190,35,32,47,38,34,26,16, 8,18,14},
				{168,64,120,70,190,70,32,47,38,34,26,16, 8,18,14},
				{168,64,120,70,190,70,64,47,38,34,26,16, 8,18,14},
				{168,64,120,70,190,70,64,94,38,34,26,16, 8,18,14},
				{168,64,120,70,190,70,64,94,76,34,26,16, 8,18,14},
				{168,64,120,70,190,70,64,94,76,68,26,16, 8,18,14},
				{168,64,120,70,190,70,64,94,76,68,52,16, 8,18,14},
				{168,64,120,70,190,70,64,94,76,68,52,32, 8,18,14},
				{168,64,120,70,190,70,64,94,76,68,52,32,16,18,14},
				{168,64,120,70,190,70,64,94,76,68,52,32,16,36,14},
				{168,64,120,70,190,70,64,94,76,68,52,32,16,36,28},
		};

		Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
		if (cPharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cPharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(LinkedList.class)|| f.getType().equals(List.class)) {
				if (fieldMedicaments==null) {
					fieldMedicaments=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
		}
		if (fieldMedicaments==null) {
			System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
		} else {
			if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
			}
			if (!fieldMedicaments.getName().equals("medicaments")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
			}
		}
		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);





		Class<?> cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		if (cMedicament==null) {
			System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldATC=null, fieldGenerique=null, fieldSurPrescription=null, fieldSubstanceActive=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cMedicament.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().equals("generique")) {
				if (fieldGenerique==null) {
					fieldGenerique=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("surPrescription")) {
				if (fieldSurPrescription==null) {
					fieldSurPrescription=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("substanceActive")) {
				if (fieldSubstanceActive==null) {
					fieldSubstanceActive=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("atc")) {
				if (fieldATC==null) {
					fieldATC=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldGenerique==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
		} else {
			if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
			}
			if (!fieldGenerique.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSurPrescription==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
		} else {
			if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
			}
			if (!fieldSurPrescription.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSubstanceActive==null) {
			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
		} else {
			if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
			}
			if (!fieldSubstanceActive.getType().equals(String.class)) {
				System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
		if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);








		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
			System.out.println("   Il n'est pas possible de tester cette methode si Produit n'implemente pas IProduit");
			return 0;
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cMedicament, cProduit)) {
			System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
			System.out.println("   Il n'est pas possible de tester cette methode si Medicament n'herite pas de Produit");
			return 0;
		}

		//				Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
		//				if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
		//					System.out.println("   Aie... Pharmacie n'implemente pas l'interface IPharmacie");
		//					noteImplements=0;
		//				}
		Object[] pts=new Object[paramsConst.length];
		LinkedList<Medicament> meds = new LinkedList<Medicament>();
		try {
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);
for (int j=0; j<2; j++) {
			for (int i=0; i<paramsConst.length; i++) {
				pts[i] = instantiator.newInstance();
				if (fieldNom!=null) fieldNom.set(pts[i],paramsConst[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pts[i],paramsConst[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pts[i],paramsConst[i][2]);
				if (fieldGenerique!=null) fieldGenerique.set(pts[i],paramsConst[i][3]);
				if (fieldSurPrescription!=null) fieldSurPrescription.set(pts[i],paramsConst[i][4]);
				if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pts[i],paramsConst[i][5]);
				meds.add((Medicament)pts[i]);
			}
}
		} catch (Exception e) {
			System.out.println("Exception levee durant la creation des medicaments. Impossible de tester");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);
		
		Method mAjouter=null;
		try {
			Method[] methods = cPharmacie.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("ajouter")) {
					mAjouter=m;
				} 			}
		} catch (Exception e1) {
		} 
		if (mAjouter==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode ajouter() de Pharmacie");
			return 0;
		}
		if (noteDeclaration>0) {
			if (mAjouter.getParameterCount()!=1 || !mAjouter.getParameters()[0].getType().equals(Medicament.class) ) {
				System.out.println("   Aie... Votre methode ajouter devrait prendre un unique parametre de type String");
				noteDeclaration=0;
				noteComportement=0;
			}
		}
		if (mAjouter!=null && !Modifier.isPublic(mAjouter.getModifiers())) {
			System.out.println("   Aie... Votre methode ajouter() n'a pas la visibilite attendue");
			noteDeclaration = noteDeclaration/2;
		}
		if (mAjouter!=null) mAjouter.setAccessible(true);

//		String[] paramsGetMedicament = new String[paramsConst.length];
//		String[] paramsGetMedicamentInconnus= {"carotte", "salade", "oignon", "navet", "laitue", "poireau", "tomate", "celeri", "potiron", "patate", "choux", "poivron", "banane", "poire", "pomme", "courgette", "piment", "endive"};
//		int[] oracle={84, 32, 60, 35, 95, 35, 32, 47, 38, 34, 26, 16,8,18,14};

		
		
//		for (int j=0;j<paramsConst.length; j++) {
//			paramsGetMedicament[j]=meds.get(j).getNom();
//		}
		Object[] arg1 = new Object[1];
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiatorP= objenesis.getInstantiatorOf(cPharmacie);
			pt1 = instantiatorP.newInstance();
			LinkedList<Medicament> varMeds=null;
				if (fieldMedicaments!=null)
					try {
						fieldMedicaments.set(pt1,new LinkedList<Medicament>());
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
		for (int i=0; i<meds.size(); i++) {
	//		int resGetMedicament=0;
			try {
				if (noteComportement>0) {
					if (fieldMedicaments!=null) varMeds=(LinkedList<Medicament>)(fieldMedicaments.get(pt1));
					String avant=Tool.toString(varMeds);

					if ( mAjouter!=null) {
						arg1[0]=meds.get(i);//paramsGetMedicament[i];
						//resGetMedicament=(int)(
								mAjouter.invoke(pt1,  arg1);//);
					}
					if (fieldMedicaments!=null) varMeds=(LinkedList<Medicament>)(fieldMedicaments.get(pt1));
					
//					if ( resGetMedicament==null ) {
//						System.out.println("   Aie... votre accesseur getQuantiteMedicament n'est pas correct. Il retourne null alors qu'il existe dans la pharmacie un medicament portant le nom recherche");
//						noteComportement=0;
//					} else
					if (!Tool.isOk(varMeds, oracle[i])) {
							System.out.println("i="+i+"   Aie... votre methode ajouter n'a pas le comportement attendu");
							System.out.println("avant l'appel a ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+") les quantites en sont :\n"+avant);
							System.out.println("apres l'appel a ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+") les quantites en sont :\n"+Tool.toString(varMeds));
							System.out.println("alors que les quantites en stock devraient etre :\n"+Tool.toString(oracle[i]));
	//					System.out.println("puis ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+")");
	//					System.out.println("apres="+Tool.toString(varMeds));
						noteComportement=0;
						
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportement=0;
			}
		}

		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteComportement);
	}

	
	
	
	



//	@SuppressWarnings("unchecked")
	public static int test13PharmacieRealisable() {
		System.out.println("   Test verifiant le bon fonctionnement de la methode realisable(LinkedList<String> nomsMedicaments) de Pharmacie");
		int noteDeclaration=10;
		int noteComportement=90;
		Object[][] paramsConst = {
				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
		};
//		String[] noms = {
//				"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps",
//				"Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps", "Ibuprofen sandos 200mg 30cps", 
//				"Arrow ibuprofene 200mg 30cps", "Doliprane 1000mg 8cps", "Doliprane 500mg 16cps",
//				"Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Topalgic 100mg 30cps",
//				"Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps"
//		};
		String[][] paraNoms = {
				{
					"Advil 200mg 30cps","Ibuprofen sandos 200mg 30cps", 
					
			},		
				{
				"Advil 200mg 20cps","Spifen 200mg 30cps",
		},		
				{
			"Spifen 200mg 30cps","Topalgic 100mg 30cps",
	},		
				{
		"Ibuprofen biogaran 200mg 30cps", "Doliprane 500mg 16cps",
		"Dafalgan 500mg 16cps","Tramadol arrow 100mg 30cps"
},		
		};
//		int[][] oracle = {
//				{84, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 8, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 8,18, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,32,60,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,60,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32,16,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32,16,36,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32,16,36,28},
//		};

		Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
		if (cPharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cPharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(LinkedList.class)||f.getType().equals(List.class)) {
				if (fieldMedicaments==null) {
					fieldMedicaments=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
		}
		if (fieldMedicaments==null) {
			System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
		} else {
			if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
			}
			if (!fieldMedicaments.getName().equals("medicaments")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
			}
		}
		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);





		Class<?> cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		if (cMedicament==null) {
			System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldATC=null, fieldGenerique=null, fieldSurPrescription=null, fieldSubstanceActive=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cMedicament.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().equals("generique")) {
				if (fieldGenerique==null) {
					fieldGenerique=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("surPrescription")) {
				if (fieldSurPrescription==null) {
					fieldSurPrescription=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("substanceActive")) {
				if (fieldSubstanceActive==null) {
					fieldSubstanceActive=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("atc")) {
				if (fieldATC==null) {
					fieldATC=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldGenerique==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
		} else {
			if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
			}
			if (!fieldGenerique.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSurPrescription==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
		} else {
			if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
			}
			if (!fieldSurPrescription.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSubstanceActive==null) {
			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
		} else {
			if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
			}
			if (!fieldSubstanceActive.getType().equals(String.class)) {
				System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
		if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);








		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
			System.out.println("   Il n'est pas possible de tester cette methode si Produit n'implemente pas IProduit");
			return 0;
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cMedicament, cProduit)) {
			System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
			System.out.println("   Il n'est pas possible de tester cette methode si Medicament n'herite pas de Produit");
			return 0;
		}

		//				Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
		//				if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
		//					System.out.println("   Aie... Pharmacie n'implemente pas l'interface IPharmacie");
		//					noteImplements=0;
		//				}
		Object[] pts=new Object[paramsConst.length];
		LinkedList<Medicament> meds = new LinkedList<Medicament>();
		try {
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);
for (int j=0; j<2; j++) {
			for (int i=0; i<paramsConst.length; i++) {
				pts[i] = instantiator.newInstance();
				if (fieldNom!=null) fieldNom.set(pts[i],paramsConst[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pts[i],paramsConst[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pts[i],paramsConst[i][2]);
				if (fieldGenerique!=null) fieldGenerique.set(pts[i],paramsConst[i][3]);
				if (fieldSurPrescription!=null) fieldSurPrescription.set(pts[i],paramsConst[i][4]);
				if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pts[i],paramsConst[i][5]);
				meds.add((Medicament)pts[i]);
			}
}
		} catch (Exception e) {
			System.out.println("Exception levee durant la creation des medicaments. Impossible de tester");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);
		
		Method mRealisable=null;
		try {
			Method[] methods = cPharmacie.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("realisable")) {
					mRealisable=m;
				} 			}
		} catch (Exception e1) {
		} 
		if (mRealisable==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode realisable de Pharmacie");
			return 0;
		}
		if (noteDeclaration>0) {
			if (mRealisable.getParameterCount()!=1 || !mRealisable.getParameters()[0].getType().equals(LinkedList.class) ) {
				System.out.println("   Aie... Votre methode realisable devrait prendre un unique parametre de type LinkedList<String>");
				noteDeclaration=0;
				noteComportement=0;
			}
		}
		if (mRealisable!=null && !Modifier.isPublic(mRealisable.getModifiers())) {
			System.out.println("   Aie... Votre methode realisable n'a pas la visibilite attendue");
			noteDeclaration = noteDeclaration/2;
		}
		if (mRealisable!=null) mRealisable.setAccessible(true);

//		String[] paramsGetMedicament = new String[paramsConst.length];
//		String[] paramsGetMedicamentInconnus= {"carotte", "salade", "oignon", "navet", "laitue", "poireau", "tomate", "celeri", "potiron", "patate", "choux", "poivron", "banane", "poire", "pomme", "courgette", "piment", "endive"};
//		int[] oracle={84, 32, 60, 35, 95, 35, 32, 47, 38, 34, 26, 16,8,18,14};

		
		
//		for (int j=0;j<paramsConst.length; j++) {
//			paramsGetMedicament[j]=meds.get(j).getNom();
//		}
		boolean resRealisable = false;
		boolean[] oracle = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
        int ioracle=0;
		Object[] arg1 = new Object[1];
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiatorP= objenesis.getInstantiatorOf(cPharmacie);
			pt1 = instantiatorP.newInstance();
	//		LinkedList<Medicament> varMeds=null;
		for (int i=0; i<meds.size(); i++) {
	//		int resGetMedicament=0;
			try {
				if (noteComportement>0) {
						LinkedList<Medicament> lis = new LinkedList<Medicament>();
					if (fieldMedicaments!=null)
					{
						for (int j=0; j<i; j++) {
							lis.add(meds.get(j));
						}
						fieldMedicaments.set(pt1,lis);
					}
				//if (fieldMedicaments!=null) varMeds=(LinkedList<Medicament>)(fieldMedicaments.get(pt1));
				//	String avant=Tool.toString(varMeds);
for (int kk=0; kk<paraNoms.length; kk++) {
						LinkedList<String> p = new LinkedList<String>();
					if ( mRealisable!=null) {
						p.addAll(Arrays.asList(paraNoms[kk]));
						arg1[0]=p;//meds.get(i);//paramsGetMedicament[i];
						resRealisable=(boolean)(	mRealisable.invoke(pt1,  arg1));
					}
					//System.out.print(resRealisable+",");
					if (resRealisable!=oracle[ioracle]) {
						System.out.println("   AIe... votre methode realisable n'a pas le comportement attendu");
						System.out.println("   sur une pharmacie p ayant les medicaments : \n"+Tool.toString(lis));
						System.out.println("   la methode p.realisable(meds) avec meds="+p);
						System.out.println("   retourne "+resRealisable+" au lieu de "+oracle[ioracle]);
						noteComportement=0;
					}
					ioracle++;
	//				if (fieldMedicaments!=null) varMeds=(LinkedList<Medicament>)(fieldMedicaments.get(pt1));
					
//					if ( resGetMedicament==null ) {
//						System.out.println("   Aie... votre accesseur getQuantiteMedicament n'est pas correct. Il retourne null alors qu'il existe dans la pharmacie un medicament portant le nom recherche");
//						noteComportement=0;
//					} else
//					if (!Tool.isOk(varMeds, oracle[i])) {
//							System.out.println("i="+i+"   Aie... votre methode ajouter n'a pas le comportement attendu");
//							System.out.println("avant l'appel a ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+") les quantites en sont :\n"+avant);
//							System.out.println("apres l'appel a ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+") les quantites en sont :\n"+Tool.toString(varMeds));
//							System.out.println("alors que les quantites en stock devraient etre :\n"+Tool.toString(oracle[i]));
//	//					System.out.println("puis ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+")");
//	//					System.out.println("apres="+Tool.toString(varMeds));
//						noteComportement=0;
//						
//					}
}
				}

			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportement=0;
			}
		}

		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteComportement);
	}



	
	
	
	
	
	
	

	
	



	@SuppressWarnings("unchecked")
	public static int test14PharmacieGetSubstituables() {
		System.out.println("   Test verifiant le bon fonctionnement de la methode getSubstituables(Medicament m) de Pharmacie");
		int noteDeclaration=10;
		int noteComportement=90;
		Object[][] paramsConst = {
				{"Advil 200mg 20cps", 3.13, 84,false,false,"ibuprofene"},
				{"Advil 200mg 30cps", 4.40, 32,false,false,"ibuprofene"},
				{"Advil 400mg 14cps", 3.32, 60,false,false,"ibuprofene"},
				{"Spifen 200mg 30cps", 2.50, 35,false,false,"ibuprofene"},
				{"Ibuprofen biogaran 200mg 30cps", 2.31, 95,true,false,"ibuprofene"},
				{"Ibuprofen sandos 200mg 30cps", 2.28, 35,true,false,"ibuprofene"},
				{"Arrow ibuprofene 200mg 30cps", 2.31, 32,true,false,"ibuprofene"},
				{"Doliprane 1000mg 8cps", 2.47, 47,false,false,"paracetamol"},
				{"Doliprane 500mg 16cps", 2.11, 38,false,false,"paracetamol"},
				{"Paracetanol biogaran 1000mg 8cps", 2.15, 34,true,false,"paracetamol"},
				{"Dafalgan 500mg 16cps", 2.12, 26, false, false, "paracetamol"},
				{"Topalgic 100mg 30cps", 6.15, 16, false, true, "tramadol chlorhydrate"},
				{"Contramal 100mg 30cps", 7.25, 8, false, true, "tramadol chlorhydrate"},
				{"Tramadol arrow 100mg 30cps", 5.67, 18, true, true, "tramadol chlorhydrate"},
				{"Tramadol biogaran 100mg 30cps", 5.67, 14, true, true, "tramadol chlorhydrate"},
		};
//		String[] noms = {
//				"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps",
//				"Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps", "Ibuprofen sandos 200mg 30cps", 
//				"Arrow ibuprofene 200mg 30cps", "Doliprane 1000mg 8cps", "Doliprane 500mg 16cps",
//				"Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Topalgic 100mg 30cps",
//				"Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps"
//		};
//		String[][] paraNoms = {
//				{
//					"Advil 200mg 30cps","Ibuprofen sandos 200mg 30cps", 
//					
//			},		
//				{
//				"Advil 200mg 20cps","Spifen 200mg 30cps",
//		},		
//				{
//			"Spifen 200mg 30cps","Topalgic 100mg 30cps",
//	},		
//				{
//		"Ibuprofen biogaran 200mg 30cps", "Doliprane 500mg 16cps",
//		"Dafalgan 500mg 16cps","Tramadol arrow 100mg 30cps"
//},		
//		};
//		int[][] oracle = {
//				{84, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32, 0, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47, 0, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38, 0, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34, 0, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26, 0, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 0, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 8, 0, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 8,18, 0},
//				{84,32,60,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,32,60,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,60,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,35,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,95,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,35,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,32,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,47,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,38,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,34,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,26,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,16, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32, 8,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32,16,18,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32,16,36,14},
//				{168,64,120,70,190,70,64,94,76,68,52,32,16,36,28},
//		};

		Class<?> cPharmacie = Reflexion.getClass("packnp.pharmacie.Pharmacie");
		if (cPharmacie==null) {
			System.out.println("   Aie... je ne trouve pas la classe Pharmacie dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldMedicaments=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cPharmacie.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(LinkedList.class)|| f.getType().equals(List.class)) {
				if (fieldMedicaments==null) {
					fieldMedicaments=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pharmacie");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Pharmacie ne doit comporter que la variable medicaments.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" ne devrait pas etre definie dans Pharmacie");
		}
		if (fieldMedicaments==null) {
			System.out.println("   Aie... La classe Pharmacie doit comporter une variable d'instance de type LinkedList<Medicament> nommee medicaments");
		} else {
			if (!Modifier.isPrivate(fieldMedicaments.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldMedicaments.getName());
			}
			if (!fieldMedicaments.getName().equals("medicaments")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable se nommme \""+fieldMedicaments.getName()+"\" au lieu de \"medicaments\"");
			}
		}
		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);





		Class<?> cMedicament = Reflexion.getClass("packnp.pharmacie.Medicament");
		if (cMedicament==null) {
			System.out.println("   Aie... je ne trouve pas la classe Medicament dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldATC=null, fieldGenerique=null, fieldSurPrescription=null, fieldSubstanceActive=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cMedicament.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().equals("generique")) {
				if (fieldGenerique==null) {
					fieldGenerique=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("surPrescription")) {
				if (fieldSurPrescription==null) {
					fieldSurPrescription=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("substanceActive")) {
				if (fieldSubstanceActive==null) {
					fieldSubstanceActive=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getName().equals("atc")) {
				if (fieldATC==null) {
					fieldATC=f;
				} else {
					fieldOther=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Medicament");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La variable "+fieldOther.getName()+" n'est pas mentionnee dans la classe Medicament du diagramme de classe.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldGenerique==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee generique.");
		} else {
			if (!Modifier.isPrivate(fieldGenerique.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldGenerique.getName());
			}
			if (!fieldGenerique.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable generique que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSurPrescription==null) {
		//	System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee surPrescription.");
		} else {
			if (!Modifier.isPrivate(fieldSurPrescription.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSurPrescription.getName());
			}
			if (!fieldSurPrescription.getType().equals(boolean.class)) {
				System.out.println("   Aie... La variable surPrescription que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldSubstanceActive==null) {
			System.out.println("   Aie... La classe Medicament doit comporter une variable d'instance nommee substanceActive.");
		} else {
			if (!Modifier.isPrivate(fieldSubstanceActive.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declarees private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldSubstanceActive.getName());
			}
			if (!fieldSubstanceActive.getType().equals(String.class)) {
				System.out.println("   Aie... La variable substanceActive que vous avez declaree n'est pas du type attendu");
			}
		}
		if (fieldGenerique!=null) fieldGenerique.setAccessible(true);
		if (fieldSurPrescription!=null) fieldSurPrescription.setAccessible(true);
		if (fieldSubstanceActive!=null) fieldSubstanceActive.setAccessible(true);








		Class<?> cProduit = Reflexion.getClass("packnp.pharmacie.Produit");
		if (cProduit==null) {
			System.out.println("   Aie... je ne trouve pas la classe Produit dans le package packnp.pharmacie");
			return 0;
		}
		Field fieldPrix=null, fieldNom=null, fieldQuantite=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cProduit.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(double.class)) {
				if (fieldPrix==null) {
					fieldPrix=f;
				} else {
					fieldOther=f;
				}
			} else if ( f.getType().equals(int.class)) {
				if (fieldQuantite==null) {
					fieldQuantite=f;
				} else {
					fieldQuantite=f;
				}
			} else{
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Produit");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Produit ne doit comporter que trois variables d'instance, l'une memorisant le nom, l'autre le prix et la troisieme la quantite.");
			System.out.println("          La variable \""+fieldOther.getName()+"\" semble correspondre a aucune d'elles");
		}
		if (fieldPrix==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type double memorisant le prix");
		} else {
			if (!Modifier.isPrivate(fieldPrix.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrix.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldPrix.getName().equals("prix")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le prix se nommme \""+fieldPrix.getName()+"\" au lieu de \"prix\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type chaine de caracteres memorisant le nom.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldQuantite==null) {
			System.out.println("   Aie... La classe Produit doit comporter une variable d'instance de type entier memorisant la quantite");
		} else {
			if (!Modifier.isPrivate(fieldQuantite.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldQuantite.getName());
				System.out.println("          Le comportement du constructeur ne pourra pas etre evalue tant que cette variable ne sera pas declaree private");
			}
			if (!fieldQuantite.getName().equals("quantite")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la quantite se nommme \""+fieldQuantite.getName()+"\" au lieu de \"quantite\"");
			}
		}
		Class<IProduit> clIProduit = packnp.pharmacie.IProduit.class;
		if (!clIProduit.isAssignableFrom(cProduit)) {
			System.out.println("   Aie... Produit n'implemente pas l'interface IProduit");
			System.out.println("   Il n'est pas possible de tester cette methode si Produit n'implemente pas IProduit");
			return 0;
		}

		if (fieldPrix!=null) fieldPrix.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		if (fieldQuantite!=null) fieldQuantite.setAccessible(true);

		if (!Reflexion.extendsClass(cMedicament, cProduit)) {
			System.out.println("   Aie... Votre classe Medicament n'herite pas de la classe Produit");
			System.out.println("   Il n'est pas possible de tester cette methode si Medicament n'herite pas de Produit");
			return 0;
		}

		//				Class<IPharmacie> clIPharmacie = packnp.pharmacie.IPharmacie.class;
		//				if (!clIPharmacie.isAssignableFrom(cPharmacie)) {
		//					System.out.println("   Aie... Pharmacie n'implemente pas l'interface IPharmacie");
		//					noteImplements=0;
		//				}
		Object[] pts=new Object[paramsConst.length];
		LinkedList<Medicament> meds = new LinkedList<Medicament>();
		try {
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiator= objenesis.getInstantiatorOf(cMedicament);
for (int j=0; j<2; j++) {
			for (int i=0; i<paramsConst.length; i++) {
				pts[i] = instantiator.newInstance();
				if (fieldNom!=null) fieldNom.set(pts[i],paramsConst[i][0]);
				if (fieldPrix!=null) fieldPrix.set(pts[i],paramsConst[i][1]);
				if (fieldQuantite!=null) fieldQuantite.set(pts[i],paramsConst[i][2]);
				if (fieldGenerique!=null) fieldGenerique.set(pts[i],paramsConst[i][3]);
				if (fieldSurPrescription!=null) fieldSurPrescription.set(pts[i],paramsConst[i][4]);
				if (fieldSubstanceActive!=null) fieldSubstanceActive.set(pts[i],paramsConst[i][5]);
				meds.add((Medicament)pts[i]);
			}
}
		} catch (Exception e) {
			System.out.println("Exception levee durant la creation des medicaments. Impossible de tester");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (fieldMedicaments!=null) fieldMedicaments.setAccessible(true);
		
		Method mSubstituables=null;
		try {
			Method[] methods = cPharmacie.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getSubstituables")) {
					mSubstituables=m;
				} 			}
		} catch (Exception e1) {
		} 
		if (mSubstituables==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode substituables de Pharmacie");
			return 0;
		}
		if (noteDeclaration>0) {
			if (mSubstituables.getParameterCount()!=1 || !mSubstituables.getParameters()[0].getType().equals(Medicament.class) ) {
				System.out.println("   Aie... Votre methode substituables devrait prendre un unique parametre de type LinkedList<String>");
				noteDeclaration=0;
				noteComportement=0;
			}
		}
		if (mSubstituables!=null && !Modifier.isPublic(mSubstituables.getModifiers())) {
			System.out.println("   Aie... Votre methode substituable n'a pas la visibilite attendue");
			noteDeclaration = noteDeclaration/2;
		}
		if (mSubstituables!=null) mSubstituables.setAccessible(true);

//		String[] paramsGetMedicament = new String[paramsConst.length];
//		String[] paramsGetMedicamentInconnus= {"carotte", "salade", "oignon", "navet", "laitue", "poireau", "tomate", "celeri", "potiron", "patate", "choux", "poivron", "banane", "poire", "pomme", "courgette", "piment", "endive"};
//		int[] oracle={84, 32, 60, 35, 95, 35, 32, 47, 38, 34, 26, 16,8,18,14};

		
		
//		for (int j=0;j<paramsConst.length; j++) {
//			paramsGetMedicament[j]=meds.get(j).getNom();
//		}
//		boolean resRealisable = false;
	//	boolean[] oracle = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,false,false,true,true,true,false,true,true,true,false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
 String[][] oracle = {
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps","Advil 200mg 20cps","Advil 200mg 30cps","Advil 400mg 14cps","Spifen 200mg 30cps","Ibuprofen biogaran 200mg 30cps","Ibuprofen sandos 200mg 30cps","Arrow ibuprofene 200mg 30cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps","Doliprane 1000mg 8cps","Doliprane 500mg 16cps","Paracetanol biogaran 1000mg 8cps","Dafalgan 500mg 16cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
		 {"Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps","Topalgic 100mg 30cps","Contramal 100mg 30cps","Tramadol arrow 100mg 30cps","Tramadol biogaran 100mg 30cps",},
 };
	//	int ioracle=0;
        LinkedList<Medicament> resSubstituables=null;
		Object[] arg1 = new Object[1];
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator<?> instantiatorP= objenesis.getInstantiatorOf(cPharmacie);
			pt1 = instantiatorP.newInstance();
		for (int i=0; i<meds.size(); i++) {
	//		int resGetMedicament=0;
			try {
				if (noteComportement>0) {
			//			LinkedList<Medicament> lis = new LinkedList<Medicament>();
					if (fieldMedicaments!=null)
					{
//						for (int j=0; j<i; j++) {
//							lis.add(meds.get(j));
//						}
						fieldMedicaments.set(pt1,meds);//lis);
					}
				//if (fieldMedicaments!=null) varMeds=(LinkedList<Medicament>)(fieldMedicaments.get(pt1));
				//	String avant=Tool.toString(varMeds);
//for (int kk=0; kk<paraNoms.length; kk++) {
					//	LinkedList<String> p = new LinkedList<String>();
					if ( mSubstituables!=null) {
					//	p.addAll(Arrays.asList(paraNoms[kk]));
						arg1[0]=meds.get(i);//paramsGetMedicament[i];
						resSubstituables=(LinkedList<Medicament>)(	mSubstituables.invoke(pt1,  arg1));
					}
					//System.out.print(resRealisable+",");
					
					
					
//System.out.print("{");
//for (Medicament m  : resSubstituables) {
//	System.out.print("\""+m.getNom()+"\",");
//}
//System.out.println("},");

					LinkedList<String> oracl = new LinkedList<String>();
					oracl.addAll( Arrays.asList(oracle[i]));
					if (!Tool.egal(resSubstituables,oracl)) {
						System.out.println(i+"   Aie...substituables(\""+((IProduit)(meds.get(i))).getNom()+"\") retourne :\n"+Tool.toString2(resSubstituables)+"\nau lieu de :\n"+oracl);
						noteComportement=0;
					}
//					if (resSubstituables!=oracle[ioracle]) {
//						System.out.println("   AIe... votre methode realisable n'a pas le comportement attendu");
//						System.out.println("   sur une pharmacie p ayant les medicaments : \n"+Tool.toString(lis));
//						System.out.println("   la methode p.realisable(meds) avec meds="+p);
//						System.out.println("   retourne "+resRealisable+" au lieu de "+oracle[ioracle]);
//						noteComportement=0;
//					}
//					ioracle++;
					
					
					
					
	//				if (fieldMedicaments!=null) varMeds=(LinkedList<Medicament>)(fieldMedicaments.get(pt1));
					
//					if ( resGetMedicament==null ) {
//						System.out.println("   Aie... votre accesseur getQuantiteMedicament n'est pas correct. Il retourne null alors qu'il existe dans la pharmacie un medicament portant le nom recherche");
//						noteComportement=0;
//					} else
//					if (!Tool.isOk(varMeds, oracle[i])) {
//							System.out.println("i="+i+"   Aie... votre methode ajouter n'a pas le comportement attendu");
//							System.out.println("avant l'appel a ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+") les quantites en sont :\n"+avant);
//							System.out.println("apres l'appel a ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+") les quantites en sont :\n"+Tool.toString(varMeds));
//							System.out.println("alors que les quantites en stock devraient etre :\n"+Tool.toString(oracle[i]));
//	//					System.out.println("puis ajouter((\""+((IProduit)(meds.get(i))).getNom()+"\","+((IProduit)(meds.get(i))).getQuantite()+")");
//	//					System.out.println("apres="+Tool.toString(varMeds));
//						noteComportement=0;
//						
//					}
//}
				}

			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportement=0;
			}
		}

		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteComportement);
	}


	//	public static final double[] prixGarnitures = {
	//		 	 0.55, 	 	
	//			 0.69, 	               	 	
	//		     0.77, 	               	 	
	//			  0.96, 	               	 	
	//			 0.65, 	               	 	
	//			 0.75, 	               	 	
	//			 0.42, 	               	 	
	//			 1.06, 	               	 	
	//			1.54, 	               	 	
	//			 0.78, 	               	 	
	//			 1.38, 	               	 	
	//			 0.38, 	               	 	
	//			 0.58, 	               	 	
	//			1.0, 	               	 	
	//			0.45, 	               	 	
	//			1.04, 	               	 	
	//			1.9,  	               	 	
	//			1.94, 	               	 	
	//			1.79, 	               	 	
	//			0.57, 	               	 	
	//			1.02, 	               	 	
	//			 1.35, 	               	 	
	//			 0.7, 	               	 	
	//			 0.41, 	               	 	
	//			 0.2, 	               	 	
	//			 0.12, 	               	 	
	//			 0.1, 	               	 	
	//			0.09, 	               	 	
	//			 0.68, 	               	 	
	//			 0.88, 	               	 	
	//			 0.9, 	               	 	
	//			 0.25, 	               	 	
	//			 0.2, 	               	 	
	//			0.31, 	               	 	
	//			 0.2, 	               	 	
	//			0.18, 	               	 	
	//			 0.25, 	               	 	
	//			 0.42, 	               	 	
	//			 0.19, 	               	 	
	//			0.27, 	               	 	
	//			 0.24 	               	 	
	//		
	//	};
	//	public static final String[] nomsGarnitures = {
	//		 	"Mozzarella", 	 "Emmental", 	      "Cheddar",   	   "Chevre",  	          "St Marcellin",                	 	
	//			"Roquefort", 	 "Bleu",  	          "Raclette", 	   "Reblochon", 	      "Camembert", 	               	 	
	//			"Cure nantais",  "Jambon", 	          "Lardons",  	   "Merguez", 	          "Bacon", 	               	 	
	//			"Chorizo",  	 "Andouille",   	  "Poulet",        "Kebab", 	          "Boeuf",	               	 	
	//			"Anchois", 	     "Saumon",            "Thon", 	       "Champignons", 	      "Poivrons", 	               	 	
	//			"Pommes de terre","Oignons rouges",   "Oignons",       "Aubergines",          "Bolognaise maison",  	               	 	
	//			"Tomates fraiches","Basilic",         "Origan",  	   "Ail",  	              "Curry",  	               	 	
	//			"Cumin",  	      "Aneth",  	      "Oeuf", 	       "Miel",  	          "Moutarde",  	               	 	
	//			"Olives", 
	//	};
	//	public static final int[][] garnituresPizzas = { 	               	 	
	//			{0, 11}, 	               	 	
	//			{0, 17},	 	               	 	
	//			{0, 30, 40},	 	               	 	
	//			{0, 11, 40}, 	               	 	
	//			{23, 0, 40}, 	               	 	
	//	               	 	
	//			{0, 11, 23, 40}, 	               	 	
	//			{0, 13, 27, 32}, 	               	 	
	//			{0, 27, 11, 32, 10}, 	               	 	
	//			{0, 27, 13, 24, 37, 32}, 	               	 	
	//			{0, 27, 23, 17, 8}, 	               	 	
	//	               	 	
	//			{27, 23, 30, 3, 38}, 	               	 	
	//			{0, 27, 12, 25, 8}, 	               	 	
	//			{27, 0, 13, 3, 38}, 	               	 	
	//			{0, 27, 12, 10, 25}, 	               	 	
	//			{0, 14, 6, 8, 23}, 	               	 	
	//	               	 	
	//			{0, 27, 19, 13, 24}, 	               	 	
	//			{27, 12, 0}, 	               	 	
	//			{12, 23, 2, 0, 24}, 	               	 	
	//			{0, 11, 8, 32}, 	               	 	
	//			{13, 15, 37, 0}, 	               	 	
	//	               	 	
	//			{27, 12, 0, 10}, 	               	 	
	//			{0, 27, 24, 29}, 	               	 	
	//			{0, 22, 30, 40}, 	               	 	
	//			{0, 27, 23, 24, 7, 25}, 	               	 	
	//			{0, 37, 11, 23, 40}, 	               	 	
	//	               	 	
	//			{0, 27, 12, 6}, 	               	 	
	//			{21, 25, 36}, 	               	 	
	//			{0, 20, 30, 40}, 	               	 	
	//			{15, 13, 12, 11, 37, 0}, 	               	 	
	//			{29, 27, 23, 24, 37, 0},	 	               	 	
	//	               	 	
	//			{18, 27, 0}, 	               	 	
	//			{0, 19, 3, 37, 32}, 	               	 	
	//			{0, 14, 19, 2, 27, 32}, 	               	 	
	//			{11, 12, 0, 32, 8}, 	               	 	
	//			{27, 17, 30, 7, 32, 0}, 	               	 	
	//	               	 	
	//			{27, 25, 18, 37, 0}, 	               	 	
	//			{0, 16, 23, 27}, 	               	 	
	//			{0, 3, 6, 7}, 	               	 	
	//			{6, 3, 7, 15, 0}, 	               	 	
	//			{0, 25, 27, 24, 40}, 	               	 	
	//	               	 	
	//			{19, 12, 32, 24, 35, 0}, 	               	 	
	//			{27, 11, 25, 14, 7, 0}, 	               	 	
	//			{11, 14, 0, 40, 37}, 	               	 	
	//			{11, 14, 3, 37, 40}, 	               	 	
	//			{29, 6, 24, 0}, 	               	 	
	//	               	 	
	//			{0, 27, 19, 37, 30, 2, 32}, 	               	 	
	//			{6, 7, 3, 8, 2, 10, 8, 0} 	               	 	
	//	               	 	
	//	}; 	               	 	
	//	public static final boolean[] basesPizzas = { 	               	 	
	//			true, false,  true, true, true, 	               	 	
	//			true, true, false,  true, false,  	               	 	
	//			true, false,  true, false,  true,   	               	 	
	//			true, false,  false,  false,  true,   	               	 	
	//			false,  true, true, true, true,  	               	 	
	//			true, false,  true, true, true,  	               	 	
	//			false,  true, true, false,  false,  	               	 	
	//			false,  true, false, true,  true,  	               	 	
	//			true, false,  true, true, true,  	               	 	
	//			true, false 	               	 	
	//	}; 	               	 	
	//	public static final String[] nomsPizzas= { 	               	 	
	//			"Bambino", "Bambina", "Margherita", "Reine", "Parisienne",	 	               	 	
	//			"Regina", "Salsiccia", "Posito", "Porto bello", "Demoniak", 	               	 	
	//			"Ruche", "Tartiflette", "Parola", "Gallo", "Pantonne", 	               	 	
	//			"Mexicaine", "Flammenkuche", "Hollandaise", "Diablo", "Ugo", 	               	 	
	//			"Leone", "Bolo", "Pecheur", "Vegetarienne", "Royale", 	               	 	
	//			"Auvergnate", "Salmone", "Belle mere", "Carnivore", "Italienne", 	               	 	
	//			"Kebab", "Lora", "Melano", "Miki", "Tatoo", 	               	 	
	//			"Chef", "Lyonnaise", "4 formaggi", "Antonio", "Tajine", 	               	 	
	//			"Far west", "Raclette", "Campagnarde", "Magniaco", "Fabio",  	               	 	
	//			"Paola", "8 formaggi" 	               	 	
	//	}; 	               	 	
	//	public static final double[] prixPizzas= { 	               	 	
	//			5.2, 5.2, 7.9, 7.9, 7.9, 	               	 	
	//			8.2, 8.2, 9, 9, 9.9, 	               	 	
	//			9.9, 9.9, 9.9, 9.9, 9.9, 	               	 	
	//			9.9, 9.9, 9.9, 9.2, 9.2, 	               	 	
	//			9.2, 9.2, 9.2, 9.2, 9.2, 	               	 	
	//			9.2, 9.2, 9.2, 9.9, 9.9, 	               	 	
	//			9.9, 10.0, 10.0, 10.2, 10.2, 	               	 	
	//			10.2, 10.2, 10.2, 10.4, 11.2, 	               	 	
	//			11.2, 9.9, 9.9, 9.9, 9.9, 	               	 	
	//			11.2, 12.2 	               	 	
	//	}; 	
	/*
	 	MOZZARELLA("Mozzarella",  0.55), 	               	 	
	EMMENTAL("Emmental", 0.69), 	               	 	
	CHEDDAR("Cheddar",  0.77), 	               	 	
	CHEVRE("Chevre",  0.96), 	               	 	
	ST_MARCELLIN("St Marcellin", 0.65), 	               	 	
	ROQUEFORT("Roquefort", 0.75), 	               	 	
	BLEU("Bleu", 0.42), 	               	 	
	RACLETTE("Raclette", 1.06), 	               	 	
	REBLOCHON("Reblochon", 1.54), 	               	 	
	CAMEMBERT("Camembert", 0.78), 	               	 	
	CURE("Cure nantais", 1.38), 	               	 	
	JAMBON("Jambon", 0.38), 	               	 	
	LARDONS("Lardons", 0.58), 	               	 	
	MERGUEZ("Merguez", 1.0), 	               	 	
	BACON("Bacon", 0.45), 	               	 	
	CHORIZO("Chorizo", 1.04), 	               	 	
	ANDOUILLE("Andouille", 1.9),  	               	 	
	POULET("Poulet", 1.94), 	               	 	
	KEBAB("Kebab", 1.79), 	               	 	
	BOEUF("Boeuf", 0.57), 	               	 	
	ANCHOIS("Anchois", 1.02), 	               	 	
	SAUMON("Saumon", 1.35), 	               	 	
	THON("Thon", 0.7), 	               	 	
	CHAMPIGNONS("Champignons", 0.41), 	               	 	
	POIVRONS("Poivrons", 0.2), 	               	 	
	POMME_DE_TERRES("Pommes de terre", 0.12), 	               	 	
	OIGNONS_ROUGES("Oignons rouges", 0.1), 	               	 	
	OIGNONS("Oignons", 0.09), 	               	 	
	AUBERGINES("Aubergines", 0.68), 	               	 	
	BOLOGNAISE("Bolognaise maison", 0.88), 	               	 	
	TOMATES_FRAICHES("Tomates fraiches", 0.9), 	               	 	
	BASILIC("Basilic", 0.25), 	               	 	
	ORIGAN("Origan", 0.2), 	               	 	
	AIL("Ail", 0.31), 	               	 	
	CURRY("Curry", 0.2), 	               	 	
	CUMIN("Cumin", 0.18), 	               	 	
	ANETH("Aneth", 0.25), 	               	 	
	OEUF("Oeuf", 0.42), 	               	 	
	MIEL("Miel", 0.19), 	               	 	
	MOUTARDE("Moutarde", 0.27), 	               	 	
	OLIVES("Olives", 0.24); 	               	 	

	 */

	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test01GarnitureDeclarationVariables() {
	//		int notePrixRevient=25;
	//		int noteNom=25;
	//		int noteImplements=40;
	//		int noteRienDAutre=10;
	//		System.out.println("   Test verifiant la declaration de la classe Garniture : ");
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//			noteRienDAutre=0;
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//			noteRienDAutre=0;
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			noteRienDAutre=0;
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//			notePrixRevient=0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//				notePrixRevient=notePrixRevient/2;
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//				notePrixRevient=notePrixRevient/2;
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//			noteNom=0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//				noteNom=noteNom/2;
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//				noteNom=noteNom/2;
	//			}
	//		}
	//		if (noteNom==0 || notePrixRevient==0) {
	//			noteRienDAutre=0;
	//		}
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//			noteImplements=0;
	//		}
	//		if (noteNom+notePrixRevient+noteRienDAutre+noteImplements==100) {
	//			System.out.println("   Ok. Votre code passe ce test avec succes.");
	//		}
	//		return noteNom+notePrixRevient+noteImplements+noteRienDAutre;
	//	}
	//
	//	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	//	public static int test02GarnitureConstructeur() {
	//		System.out.println("   Test verifiant la declaration et le bon fonctionnement du constructeur de Garniture");
	//		int noteDeclaration=20;
	//		int noteInitNom=40;
	//		int noteInitPrixRevient=40;
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//		}
	//		
	//		if (fieldPrixRevient!=null) fieldPrixRevient.setAccessible(true);
	//		if (fieldNom!=null) fieldNom.setAccessible(true);
	//		Class[] ar = {};
	//		Class[] arStringDouble = {String.class, double.class};
	//		Constructor constC1=null;
	//		try {
	//			constC1 = cGarniture.getDeclaredConstructor(arStringDouble);
	//		} catch (NoSuchMethodException e) {
	//			constC1=null;
	//		} catch (SecurityException e) {
	//			constC1=null;
	//		}
	//		if (constC1==null) {
	//			System.out.println("   Aie... je ne trouve pas le constructeur de Garniture prenant en parametre une String et un double ");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(constC1.getModifiers())) {
	//			System.out.println("   Aie... Le constructeur de Garniture devrait etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		constC1.setAccessible(true);
	//
	////		int indexNom=2;
	////		int indexPrenom=2;
	////		String[] argsString= {"String", "String"};
	////		List<String> params = Reflexion.constructeurNomsParametresNomFic("src"+File.separator+"packnp"+File.separator+"zoo"+File.separator+"Animal.java", "Animal", argsString);
	////		String param0 = (params!=null && params.size()>0) ? params.get(0).toLowerCase() : "";
	////		String param1 = (params!=null && params.size()>1) ? params.get(1).toLowerCase() : "";
	////		if ( (param0.contains("o") ||param0.contains("a") || param0.contains("n"))) {
	////			indexNom=0;
	////		}
	////		if ( (param1.contains("o") ||param1.contains("a") || param1.contains("n"))) {
	////			if (indexNom==2) {
	////				indexNom=1;
	////			} else {
	////				indexNom=2;
	////			}
	////		}
	////		if (indexNom==0) {
	////			indexPrenom=1;
	////		} else {
	////			indexPrenom=0;
	////		}
	//		Object[][] paramC = new Object[nomsGarnitures.length][2];
	//
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			paramC[i][0]=nomsGarnitures[i];
	//			paramC[i][1]=prixGarnitures[i];
	//		}
	////		Object[][][] paramC1 ={
	////				{
	////					{"cheeta", "singe"},
	////					{"king-kong", "gorille"},
	////					{"mickey", "souris"},
	////					{"donald", "canard"}
	////				},
	////				{
	////					{"singe", "cheeta"},
	////					{"gorille", "king-kong"},
	////					{"souris", "mickey"},
	////					{"canard", "donald"}
	////				}
	////		};
	//		Object ptc1=null;
	//
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; i<paramC.length; i++) {
	//					ptc1 = (constC1.newInstance(paramC[i]));
	//					if (fieldNom==null) {
	//						noteInitNom=0;
	//					}else if (fieldNom.get(ptc1)==null) {
	//						System.out.println("  Aie... votre constructeur initialise la variable "+fieldNom.getName()+" a null \n");
	//						noteInitNom=0;
	//					} else {
	//						String nom = (String)(fieldNom.get(ptc1));
	//						if (noteInitNom>0 && !nom.equals(paramC[i][0])) {
	//							System.out.println("  Aie... apres new Garniture(\""+paramC[i][0]+"\", \""+paramC[i][1]+"\") la variable "+fieldNom.getName()+" de la Garniture vaut \""+nom+"\" au lieu de \""+paramC[i][0]+"\")");
	//							noteInitNom=0;
	//						} 
	//					}
	//					if (fieldPrixRevient==null) {
	//						noteInitPrixRevient=0;
	//					} else if (fieldPrixRevient.get(ptc1)==null) {
	//						System.out.println("  Aie... votre constructeur initialise la variable "+fieldPrixRevient.getName()+" a null \n");
	//						noteInitPrixRevient=0;
	//					} else {
	//						double prix = (Double)(fieldPrixRevient.get(ptc1));
	//						if (noteInitPrixRevient>0 && prix!=prixGarnitures[i]) {
	//							System.out.println("  Aie... apres new Garniture(\""+paramC[i][0]+"\", \""+paramC[i][1]+"\") la variable "+fieldPrixRevient.getName()+" de la Garniture vaut \""+prix+"\" au lieu de \""+paramC[i][1]+"\")");
	//							noteInitPrixRevient=0;
	//						} 
	//					}
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	//		if (noteDeclaration+noteInitPrixRevient+noteInitNom==100) {
	//			System.out.println("   Ok. Votre code passe le test.");
	//		}
	//		return (noteDeclaration+noteInitPrixRevient+noteInitNom);
	//	}
	//
	//	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	//	public static int test03GarnitureAccesseurs() {
	//		System.out.println("   Test verifiant le bon fonctionnement des accesseurs de Garniture");
	//		int noteDeclarationGetNom=10;
	//		int noteComportementGetNom=40;
	//		int noteDeclarationGetPrixRevient=10;
	//		int noteComportementGetPrixRevient=40;
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//		}
	//		
	//		if (fieldPrixRevient!=null) fieldPrixRevient.setAccessible(true);
	//		if (fieldNom!=null) fieldNom.setAccessible(true);
	//		Method mGetNom=null, mGetPrixRevient=null, mAutre=null;
	//		try {
	//			Method[] methods = cGarniture.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("getNom")) {//.getReturnType().equals(cPoint)) {
	//					mGetNom=m;
	//				} else if (m.getName().equals("getPrixRevient")) {//'.getReturnType().equals(java.awt.Color.class)) {
	//					mGetPrixRevient=m;
	//				} else { mAutre=m;
	//
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mGetNom==null) {
	//			noteDeclarationGetNom=0;
	//			noteComportementGetNom=0;
	//			System.out.println("   Aie... Je ne trouve pas l'accesseur getNom() de Garniture");
	//		}
	//		if (mGetPrixRevient==null) {
	//			noteDeclarationGetPrixRevient=0;
	//			noteComportementGetPrixRevient=0;
	//			System.out.println("   Aie... Je ne trouve pas l'accesseur getPrixRevient() de Garniture");
	//		}
	//		if (noteDeclarationGetNom>0) {
	//			if (mGetNom.getParameterCount()!=0) {
	//				System.out.println("   Aie... Votre methode getNom ne devrait pas prendre de parametres");
	//				noteDeclarationGetNom=0;
	//				noteComportementGetNom=0;
	//			}
	//		}
	//		if (noteDeclarationGetNom>0) {
	//			if (!mGetNom.getReturnType().equals(String.class)) {
	//				System.out.println("   Aie... Votre methode getNom() n'a pas le type retour attendu");
	//				noteDeclarationGetNom=noteDeclarationGetNom/2;
	//				noteComportementGetNom=0;			}
	//		}
	//		if (noteDeclarationGetPrixRevient>0) {
	//			if (mGetPrixRevient.getParameterCount()!=0) {
	//				System.out.println("   Aie... Votre methode getPrixRevient ne devrait pas prendre de parametres");
	//				noteDeclarationGetPrixRevient=0;
	//				noteComportementGetPrixRevient=0;
	//			}
	//		}
	//		if (noteDeclarationGetPrixRevient>0) {
	//			if (!mGetPrixRevient.getReturnType().equals(double.class)) {
	//				System.out.println("   Aie... Votre methode getPrixRevient() n'a pas le type retour attendu");
	//				noteDeclarationGetPrixRevient=noteDeclarationGetPrixRevient/2;
	//				noteComportementGetPrixRevient=0;			}
	//		}
	//		if (mGetNom!=null && !Modifier.isPublic(mGetNom.getModifiers())) {
	//			System.out.println("   Aie... Votre methode getNom() n'a pas la visibilite attendue");
	//			noteDeclarationGetNom = noteDeclarationGetNom/2;
	//		}
	//		if (mGetPrixRevient!=null && !Modifier.isPublic(mGetPrixRevient.getModifiers())) {
	//			System.out.println("   Aie... Votre methode getPrixRevient() n'a pas la visibilite attendue");
	//			noteDeclarationGetPrixRevient = noteDeclarationGetPrixRevient/2;
	//		}
	//
	//
	//		if (mGetNom!=null) mGetNom.setAccessible(true);
	//		if (mGetPrixRevient!=null) mGetPrixRevient.setAccessible(true);
	//
	//
	//		Object[] argssn = {};
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			Object pt1=null;
	//			Objenesis objenesis = new ObjenesisStd(); 
	//			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	//			pt1 = instantiator.newInstance();
	//			String resNom=null;
	//			double resPrix=0.0;
	//			try {
	//				if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	//				if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	//				if (noteComportementGetNom>0) {
	//					if ( mGetNom!=null) {
	//						resNom=(String)(mGetNom.invoke(pt1,  argssn));
	//					}
	//					if ( resNom==null ) {
	//						System.out.println("   Aie... votre accesseur getNom n'est pas correct. Il retourne null sur une instance ayant un nom initialise a \""+nomsGarnitures[i]+"\"");
	//						noteComportementGetNom=0;
	//					} else if (!resNom.equals(nomsGarnitures[i])) {
	//						System.out.println("   Aie... votre accesseur getNom n'est pas correct. Il retourne \""+resNom+"\" sur une instance ayant un nom initialise a \""+nomsGarnitures[i]+"\"");
	//						noteComportementGetNom=0;
	//					}
	//				}
	//				if (noteComportementGetPrixRevient>0) {
	//					if ( mGetPrixRevient!=null) {
	//						resPrix=(Double)(mGetPrixRevient.invoke(pt1,  argssn));
	//					}
	//					if (resPrix!=prixGarnitures[i]) {
	//						System.out.println("   Aie... votre accesseur getPrixRevient n'est pas correct. Il retourne \""+resPrix+"\" sur une instance ayant un prix de revient initialisee a \""+prixGarnitures[i]+"\"");
	//						noteComportementGetPrixRevient=0;
	//					}
	//				}
	//			} catch (Exception e) {
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				noteComportementGetNom=0;
	//				noteComportementGetPrixRevient=0;
	//			}
	//		}
	//		if (noteDeclarationGetNom+noteDeclarationGetPrixRevient+noteComportementGetPrixRevient+noteComportementGetNom==100	) {
	//			System.out.println("   Ok. Votre code passe le test avec succes.");
	//		}
	//		return noteDeclarationGetNom+noteDeclarationGetPrixRevient+noteComportementGetPrixRevient+noteComportementGetNom;
	//	}
	//
	//	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	//	public static int test04GarnitureEquals() {
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode equals de Garniture");
	//		int noteDeclaration=30;
	//		int noteComportement=70;
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//		}
	//		
	//		if (fieldPrixRevient!=null) fieldPrixRevient.setAccessible(true);
	//		if (fieldNom!=null) fieldNom.setAccessible(true);
	//		Method mEquals=null, mAutre=null;
	//		try {
	//			Method[] methods = cGarniture.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("equals")) {//.getReturnType().equals(cPoint)) {
	//					mEquals=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mEquals==null) {
	//			noteDeclaration=0;
	//			noteComportement=0;
	//			System.out.println("   Aie... Je ne trouve pas la methode equals dans Garniture");
	//		}
	//		if (noteDeclaration>0) {
	//			if (mEquals.getParameterCount()!=1) {
	//				System.out.println("   Aie... Votre methode equals devrait comporter un unique parametre");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;
	//			} else {
	//				if (!mEquals.getParameters()[0].getType().equals(Object.class)) {
	//					System.out.println("   Aie... Le type du parametre de equals n'est pas celui attendu");
	//					noteDeclaration=noteDeclaration/2;
	//					noteComportement=0;
	//				}
	//			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!mEquals.getReturnType().equals(boolean.class)) {
	//				System.out.println("   Aie... Votre methode equals n'a pas le type retour attendu");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!Modifier.isPublic(mEquals.getModifiers())) {
	//				System.out.println("   Aie... Votre methode equals n'est pas declaree public");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//
	//		if (mEquals!=null) mEquals.setAccessible(true);
	////		String[][] argsOk ={
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"mickey", "souris"},
	////				{"donald", "canard"},
	////				{"daffy", "canard"},
	////				{"zira", "singe"},
	////				{"zaius", "singe"}
	////		};
	////		boolean[] rep = {false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,true};
	//
	//
	//		if (fieldNom==null) {
	//			noteComportement=0;
	//		}
	//		int r=0;
	//		Object[] argssn = new Object[1];
	//		for (int i=0; noteComportement>0 && i<nomsGarnitures.length-1; i++) {
	//			for (int j=i+1; noteComportement>0 && j<nomsGarnitures.length; j++) {
	//				Object pt1=null;
	//				Object pt2=null;
	//				Objenesis objenesis = new ObjenesisStd(); 
	//				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	//				pt1 = instantiator.newInstance();
	//				pt2 = instantiator.newInstance();
	//				argssn[0]=pt2;
	//				boolean res=false;
	//				try {
	//					if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	//					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	//					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[j]);
	//					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	//					if (noteComportement>0) {
	//						if ( mEquals!=null) {
	//							res=(boolean)(mEquals.invoke(pt1,  argssn));
	//						}
	//						//System.out.print(res+",");
	//						if ( res ) {
	//							System.out.println("   Aie... g1.equals(g2) avec \""+nomsGarnitures[i]+"\" pour nom de g1 et \""+nomsGarnitures[j]+"\" pour nom de g2 retourne true au lieu de false");
	//							noteComportement=0;
	//						} 
	//					}
	//					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[i]);
	//					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	//					if (noteComportement>0) {
	//						if ( mEquals!=null) {
	//							res=(boolean)(mEquals.invoke(pt1,  argssn));
	//						}
	//						//System.out.print(res+",");
	//						if ( !res ) {
	//							System.out.println("   Aie... g1.equals(g2) avec \""+nomsGarnitures[i]+"\" pour nom de g1, \""+nomsGarnitures[i]+"\" pour nom de g2, "+prixGarnitures[i]+" pour prix de g1 et "+prixGarnitures[j]+" pour prix de g2 retourne false au lieu de true");
	//							noteComportement=0;
	//						} 
	//					}
	//
	//				} catch (Exception e) {
	//					if (e instanceof InvocationTargetException) {
	//						e.getCause().printStackTrace();
	//					} else {
	//						e.printStackTrace();
	//					}
	//					noteComportement=0;
	//				}
	//			}
	//		}
	//		if (noteDeclaration+noteComportement==100) {
	//			System.out.println("   Ok. Votre code passe le test avec succes.");
	//		}
	//		return noteDeclaration+noteComportement;
	//	}
	//
	//
	//	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	//	public static int test05PizzaGetPrixRevient() {
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode getPrixRevient de Pizza");
	//		int noteDeclaration=20;
	//		int noteComportement=100;
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//		}
	//		
	//		if (fieldPrixRevient!=null) fieldPrixRevient.setAccessible(true);
	//		if (fieldNom!=null) fieldNom.setAccessible(true);
	//
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) fieldPrixVente.setAccessible(true);
	//		if (fieldNomPizza!=null) fieldNomPizza.setAccessible(true);
	//		if (fieldBase!=null) fieldBase.setAccessible(true);
	//		if (fieldGarnitures!=null) fieldGarnitures.setAccessible(true);
	//
	//		Method mPrixRevient=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizza.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("getPrixRevient")) {//.getReturnType().equals(cPoint)) {
	//					mPrixRevient=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mPrixRevient==null) {
	//			noteDeclaration=0;
	//			noteComportement=0;
	//			System.out.println("   Aie... Je ne trouve pas la methode getPrixRevient dans Pizza");
	//		}
	//		if (noteDeclaration>0) {
	//			if (mPrixRevient.getParameterCount()!=0) {
	//				System.out.println("   Aie... Votre methode getPrixRevient ne devrait prendre aucun parametre");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;
	//			} 
	////			else {
	////				if (!mPrixRevient.getParameters()[0].getType().equals(Object.class)) {
	////					System.out.println("   Aie... Le type du parametre de equals n'est pas celui attendu");
	////					noteDeclaration=noteDeclaration/2;
	////					noteComportement=0;
	////				}
	////			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!mPrixRevient.getReturnType().equals(double.class)) {
	//				System.out.println("   Aie... Votre methode getPrixRevient n'a pas le type retour attendu");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!Modifier.isPublic(mPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Votre methode getPrixRevient n'est pas declaree public");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//
	//		if (mPrixRevient!=null) mPrixRevient.setAccessible(true);
	////		String[][] argsOk ={
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"mickey", "souris"},
	////				{"donald", "canard"},
	////				{"daffy", "canard"},
	////				{"zira", "singe"},
	////				{"zaius", "singe"}
	////		};
	////		boolean[] rep = {false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,true};
	//
	//
	////		if (fieldNom==null) {
	////			noteComportement=0;
	////		}
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//		double[] oracle= {
	//				1.46,3.1799999999999997,2.2199999999999998,1.7,1.73,2.11,2.37,3.29,2.99,5.22,3.08,3.5700000000000003,3.32,3.41,3.9000000000000004,2.9400000000000004,1.91,3.2,3.3600000000000003,3.54,3.29,2.25,2.92,2.96,2.5300000000000002,2.17,2.41,3.24,4.5,3.08,3.12,3.23,3.16,3.9400000000000004,5.43,3.66,3.48,3.68,4.56,1.7300000000000002,2.8100000000000005,3.34,2.5700000000000003,2.9800000000000004,2.58,4.03,8.91
	//		};
	//		int r=0;
	//	//	Object[] argssn = new Object[1];
	//		Object[] argVide = {};
	//		for (int i=0; noteComportement>0 && i<pizzas.size();i++) {//nomsGarnitures.length-1; i++) {
	////			for (int j=i+1; noteComportement>0 && j<nomsGarnitures.length; j++) {
	////				Object pt1=null;
	////				Object pt2=null;
	////				Objenesis objenesis = new ObjenesisStd(); 
	////				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	////				pt1 = instantiator.newInstance();
	////				pt2 = instantiator.newInstance();
	////				argssn[0]=pt2;
	////				boolean res=false;
	//				double res = 0;
	//				try {
	////					if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	////					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[j]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	//					if (noteComportement>0) {
	//						if ( mPrixRevient!=null) {
	////							res=(boolean)(mPrixRevient.invoke(pt1,  argssn));
	//							res=(double)(mPrixRevient.invoke(pizzas.get(i),  argVide));
	//						}
	//						//System.out.print(res+",");
	//						if ( Math.abs(res-oracle[i])>0.01 ) {
	//							System.out.println("   Aie... sur une pizza dont la base coute "+new Base(basesPizzas[i]).getPrixRevient());
	//							System.out.print("   et ayant des garnitures valant : ");
	//							List<packnp.solution.Garniture> ll = pizzas.get(i).getGarnitures();
	//							for (packnp.solution.Garniture g : pizzas.get(i).getGarnitures()) {
	//								System.out.print(g.getPrixRevient()+" ");
	//							}
	//							System.out.println("\n   la methode getPrixRevient retourne "+res+" au lieu de "+oracle[i]);
	//							noteComportement=0;
	//						} 
	//					}
	//
	//				} catch (Exception e) {
	//					if (e instanceof InvocationTargetException) {
	//						e.getCause().printStackTrace();
	//					} else {
	//						e.printStackTrace();
	//					}
	//					noteComportement=0;
	//				}
	//			}
	//		if (noteDeclaration==0) noteComportement=0;
	//		if (noteComportement==100) {
	//			System.out.println("   Ok. Votre code passe le test avec succes.");
	//		}
	//		return noteComportement;
	//	}
	//
	//
	//	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	//	public static int test06PizzaGetMarge() {
	//		class A extends packnp.pizzas.Pizza{public A(String n, Base b, LinkedList<packnp.solution.Garniture> g, double p)  {super(n,b,g,p);}public double getPrixRevient() {double prixRevient = this.getBase().getPrixRevient();for (packnp.solution.Garniture g : this.getGarnitures()) { prixRevient += g.getPrixRevient();} return prixRevient;} 	               	 	}
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode getMarge de Pizza");
	//		int noteDeclaration=20;
	//		int noteComportement=100;
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//		}
	//		
	//		if (fieldPrixRevient!=null) fieldPrixRevient.setAccessible(true);
	//		if (fieldNom!=null) fieldNom.setAccessible(true);
	//
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) fieldPrixVente.setAccessible(true);
	//		if (fieldNomPizza!=null) fieldNomPizza.setAccessible(true);
	//		if (fieldBase!=null) fieldBase.setAccessible(true);
	//		if (fieldGarnitures!=null) fieldGarnitures.setAccessible(true);
	//
	//		Method mMarge=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizza.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("getMarge")) {//.getReturnType().equals(cPoint)) {
	//					mMarge=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mMarge==null) {
	//			noteDeclaration=0;
	//			noteComportement=0;
	//			System.out.println("   Aie... Je ne trouve pas la methode getMarge dans Pizza");
	//		}
	//		if (noteDeclaration>0) {
	//			if (mMarge.getParameterCount()!=0) {
	//				System.out.println("   Aie... Votre methode getMarge ne devrait prendre aucun parametre");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;
	//			} 
	////			else {
	////				if (!mPrixRevient.getParameters()[0].getType().equals(Object.class)) {
	////					System.out.println("   Aie... Le type du parametre de equals n'est pas celui attendu");
	////					noteDeclaration=noteDeclaration/2;
	////					noteComportement=0;
	////				}
	////			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!mMarge.getReturnType().equals(double.class)) {
	//				System.out.println("   Aie... Votre methode getMarge n'a pas le type retour attendu");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!Modifier.isPublic(mMarge.getModifiers())) {
	//				System.out.println("   Aie... Votre methode getMarge n'est pas declaree public");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//
	//		if (mMarge!=null) mMarge.setAccessible(true);
	////		String[][] argsOk ={
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"mickey", "souris"},
	////				{"donald", "canard"},
	////				{"daffy", "canard"},
	////				{"zira", "singe"},
	////				{"zaius", "singe"}
	////		};
	////		boolean[] rep = {false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,true};
	//
	//
	////		if (fieldNom==null) {
	////			noteComportement=0;
	////		}
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new //Pizza(
	//					A(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//		double[] oracle= {
	//				3.74,2.0200000000000005,5.680000000000001,6.2,6.17,6.09,5.829999999999999,5.71,6.01,4.680000000000001,6.82,6.33,6.58,6.49,6.0,6.96,7.99,6.7,5.839999999999999,5.659999999999999,5.909999999999999,6.949999999999999,6.279999999999999,6.239999999999999,6.669999999999999,7.029999999999999,6.789999999999999,5.959999999999999,5.4,6.82,6.78,6.77,6.84,6.259999999999999,4.77,6.539999999999999,6.719999999999999,6.52,5.840000000000001,9.469999999999999,8.389999999999999,6.5600000000000005,7.33,6.92,7.32,7.169999999999999,3.289999999999999,  
	//		};
	//		int r=0;
	//	//	Object[] argssn = new Object[1];
	//		Object[] argVide = {};
	//		for (int i=0; noteComportement>0 && i<pizzas.size();i++) {//nomsGarnitures.length-1; i++) {
	////			for (int j=i+1; noteComportement>0 && j<nomsGarnitures.length; j++) {
	////				Object pt1=null;
	////				Object pt2=null;
	////				Objenesis objenesis = new ObjenesisStd(); 
	////				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	////				pt1 = instantiator.newInstance();
	////				pt2 = instantiator.newInstance();
	////				argssn[0]=pt2;
	////				boolean res=false;
	//				double res = 0;
	//				try {
	////					if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	////					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[j]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	//					if (noteComportement>0) {
	//						if ( mMarge!=null) {
	////							res=(boolean)(mPrixRevient.invoke(pt1,  argssn));
	//							res=(double)(mMarge.invoke(pizzas.get(i),  argVide));
	//						}
	//			//			System.out.print(res+",");
	//						if ( Math.abs(res-oracle[i])>0.01 ) {
	//							System.out.println("   Aie... sur une pizza dont le prix de vente est "+pizzas.get(i).getPrixVente()
	//									+" et le prix de revient est "+pizzas.get(i).getPrixRevient()+"\n la methode getMarge() retourne "+res+" au lieu de "+oracle[i]);
	////							System.out.print("   et ayant des garnitures valant : ");
	////							List<packnp.solution.Garniture> ll = pizzas.get(i).getGarnitures();
	////							for (packnp.solution.Garniture g : pizzas.get(i).getGarnitures()) {
	////								System.out.print(g.getPrixRevient()+" ");
	////							}
	////							System.out.println("\n   la methode getPrixRevient retourne "+res+" au lieu de "+oracle[i]);
	//							noteComportement=0;
	//						} 
	//					}
	//
	//				} catch (Exception e) {
	//					if (e instanceof InvocationTargetException) {
	//						e.getCause().printStackTrace();
	//					} else {
	//						e.printStackTrace();
	//					}
	//					noteComportement=0;
	//				}
	//			}
	//		if (noteDeclaration==0) noteComportement=0;
	//		if (noteComportement==100) {
	//			System.out.println("   Ok. Votre code passe le test avec succes.");
	//		}
	//		return noteComportement;
	//	}
	//
	//
	//	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	//	public static int test07PizzaContains() {
	//		//class A extends packnp.pizzas.Pizza{public A(String n, Base b, LinkedList<packnp.solution.Garniture> g, double p)  {super(n,b,g,p);}public double getPrixRevient() {double prixRevient = this.getBase().getPrixRevient();for (packnp.solution.Garniture g : this.getGarnitures()) { prixRevient += g.getPrixRevient();} return prixRevient;} 	               	 	}
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode contains(Ingredient) de Pizza");
	//		int noteDeclaration=20;
	//		int noteComportement=100;
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//		}
	//		
	//		if (fieldPrixRevient!=null) fieldPrixRevient.setAccessible(true);
	//		if (fieldNom!=null) fieldNom.setAccessible(true);
	//
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) fieldPrixVente.setAccessible(true);
	//		if (fieldNomPizza!=null) fieldNomPizza.setAccessible(true);
	//		if (fieldBase!=null) fieldBase.setAccessible(true);
	//		if (fieldGarnitures!=null) fieldGarnitures.setAccessible(true);
	//
	//		Method mContains=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizza.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("contains") && m.getParameters().length==1 && m.getParameters()[0].getType().equals(Ingredient.class)) {//.getReturnType().equals(cPoint)) {
	//					mContains=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mContains==null) {
	//			noteDeclaration=0;
	//			noteComportement=0;
	//			System.out.println("   Aie... Je ne trouve pas la methode contains(Ingredient) dans Pizza");
	//		}
	//		if (noteDeclaration>0) {
	//			if (mContains.getParameterCount()!=1) {
	//				System.out.println("   Aie... Votre methode contains ne devrait prendre qu'un parametre");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;
	//			} 
	////			else {
	////				if (!mPrixRevient.getParameters()[0].getType().equals(Object.class)) {
	////					System.out.println("   Aie... Le type du parametre de equals n'est pas celui attendu");
	////					noteDeclaration=noteDeclaration/2;
	////					noteComportement=0;
	////				}
	////			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!mContains.getReturnType().equals(boolean.class)) {
	//				System.out.println("   Aie... Votre methode contains n'a pas le type retour attendu");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!Modifier.isPublic(mContains.getModifiers())) {
	//				System.out.println("   Aie... Votre methode contains n'est pas declaree public");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//
	//		if (mContains!=null) mContains.setAccessible(true);
	////		String[][] argsOk ={
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"mickey", "souris"},
	////				{"donald", "canard"},
	////				{"daffy", "canard"},
	////				{"zira", "singe"},
	////				{"zaius", "singe"}
	////		};
	////		boolean[] rep = {false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,true};
	//
	//
	////		if (fieldNom==null) {
	////			noteComportement=0;
	////		}
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//		List<Ingredient> li =new LinkedList<Ingredient>();
	//		for (Ingredient i : garnitures) {
	//			li.add(i);
	//		}
	//		li.add(new packnp.solution.Base(true));
	//		li.add(new packnp.solution.Base(false));
	//		
	//		boolean[] oracle= {
	//				true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, true, false, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, true, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, true, false, false, false, false, false, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, true, false, true, false, false, false, false, false, false, false, true, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, true, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, true, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, true, false, false, true, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, true, false, false, false, true, false, true, false, true, true, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,
	//		};
	//		int r=0;
	//		Object[] argssn = new Object[1];
	//	//	Object[] argVide = {};
	//	//	System.out.println("");
	//		int k=0;
	//		for (int i=0; noteComportement>0 && i<pizzas.size();i++) {//nomsGarnitures.length-1; i++) {
	//		//	System.out.println("i="+i);
	//			for (Ingredient ing : li) {
	//		//		System.out.println("ing="+ing);
	////			for (int j=i+1; noteComportement>0 && j<nomsGarnitures.length; j++) {
	////				Object pt1=null;
	////				Object pt2=null;
	////				Objenesis objenesis = new ObjenesisStd(); 
	////				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	////				pt1 = instantiator.newInstance();
	////				pt2 = instantiator.newInstance();
	//				argssn[0]=ing;//pt2;
	//				boolean res=false;
	////				double res = 0;
	//				try {
	//				//	System.out.println("");
	////					if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	////					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[j]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	//					if (noteComportement>0) {
	//						if ( mContains!=null) {
	//							res=(boolean)(mContains.invoke(pizzas.get(i),  argssn));
	////							res=(double)(mContains.invoke(pizzas.get(i),  argVide));
	//						}
	////						System.out.print(res+", ");
	//						if ( res!=oracle[k])  {
	//							System.out.println("   Aie... sur une pizza ayant pour base \""+pizzas.get(i).getBase().getNom()+"\" et ayant pour ingredients :");	
	//						
	//							List<packnp.solution.Garniture> ll = pizzas.get(i).getGarnitures();
	//							for (packnp.solution.Garniture g : pizzas.get(i).getGarnitures()) {
	//								System.out.print("\""+g.getNom()+"\"");
	//							}
	//							System.out.println("\n   la methode contains(ing) ou ing est l'ingredient \""+ing.getNom()+"\" retourne "+res+" au lieu de "+(!res));
	//							noteComportement=0;
	//						} 
	//						k++;
	//					} //else System.out.println("compo 0");
	//				
	//
	//				} catch (Exception e) {
	//					//System.out.println("excep");
	//					if (e instanceof InvocationTargetException) {
	//						e.getCause().printStackTrace();
	//					} else {
	//						e.printStackTrace();
	//					}
	//					noteComportement=0;
	//				}
	//			}
	//			}
	//		for (int i=0; noteComportement>0 && i<pizzas.size();i++) {//nomsGarnitures.length-1; i++) {
	//		//	System.out.println("i="+i);
	//		//	for (Ingredient ing : li) {
	//		//		System.out.println("ing="+ing);
	////			for (int j=i+1; noteComportement>0 && j<nomsGarnitures.length; j++) {
	////				Object pt1=null;
	////				Object pt2=null;
	////				Objenesis objenesis = new ObjenesisStd(); 
	////				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	////				pt1 = instantiator.newInstance();
	////				pt2 = instantiator.newInstance();
	//				argssn[0]=null;//ing;//pt2;
	//				boolean res=false;
	////				double res = 0;
	//				try {
	//				//	System.out.println("");
	////					if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	////					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[j]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	//					if (noteComportement>0) {
	//						if ( mContains!=null) {
	//							res=(boolean)(mContains.invoke(pizzas.get(i),  argssn));
	////							res=(double)(mContains.invoke(pizzas.get(i),  argVide));
	//						}
	////						System.out.print(res+", ");
	//						if ( res!=false) {//oracle[k])  {
	//							System.out.println("   Aie... sur une pizza ayant pour base \""+pizzas.get(i).getBase().getNom()+"\" et ayant pour ingredients :");	
	//						
	//							List<packnp.solution.Garniture> ll = pizzas.get(i).getGarnitures();
	//							for (packnp.solution.Garniture g : pizzas.get(i).getGarnitures()) {
	//								System.out.print("\""+g.getNom()+"\"");
	//							}
	//							System.out.println("\n   la methode contains(null) retourne true au lieu de false");
	//							noteComportement=0;
	//						} 
	//						k++;
	//					} //else System.out.println("compo 0");
	//				
	//
	//				} catch (Exception e) {
	//					//System.out.println("excep");
	//					if (e instanceof InvocationTargetException) {
	//						e.getCause().printStackTrace();
	//					} else {
	//						e.printStackTrace();
	//					}
	//					noteComportement=0;
	//				}
	//			}
	//		//	}
	//		//System.out.println("fin");
	//		if (noteDeclaration==0) noteComportement=0;
	//		if (noteComportement==100) {
	//			System.out.println("   Ok. Votre code passe le test avec succes.");
	//		} else System.out.println(noteComportement);
	//		return noteComportement;
	//	}
	//
	//
	//	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	//	public static int test08PizzaContainsLinkedList() {
	//		//class A extends packnp.pizzas.Pizza{public A(String n, Base b, LinkedList<packnp.solution.Garniture> g, double p)  {super(n,b,g,p);}public double getPrixRevient() {double prixRevient = this.getBase().getPrixRevient();for (packnp.solution.Garniture g : this.getGarnitures()) { prixRevient += g.getPrixRevient();} return prixRevient;} 	               	 	}
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode contains(LinkedList<Ingredient>) de Pizza");
	//		int noteDeclaration=20;
	//		int noteComportement=100;
	//		Class cGarniture = Reflexion.getClass("packnp.pizzas.Garniture");
	//		if (cGarniture==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Garniture dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldPrixRevient=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cGarniture.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNom==null) {
	//					fieldNom=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixRevient==null) {
	//					fieldPrixRevient=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Garniture");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Garniture ne doit comporter que deux variables d'instance, l'une memorisant le nom, l'autre le prix de revient.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixRevient==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type double memorisant le prix de revient de la garniture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixRevient.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixRevient.getName());
	//			}
	//			if (!fieldPrixRevient.getName().equals("prixRevient")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de revient de ma garniture se nommme \""+fieldPrixRevient.getName()+"\" au lieu de \"prixRevient\"");
	//			}
	//		}
	//		if (fieldNom==null) {
	//			System.out.println("   Aie... La classe Garniture doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la gariture.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	//			}
	//			if (!fieldNom.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la garniture se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//
	//		Class clIngredient = packnp.pizzas.Ingredient.class;
	//		if (!clIngredient.isAssignableFrom(cGarniture)) {
	//			System.out.println("   Aie... Garniture n'implemente pas l'interface Ingredient");
	//		}
	//		
	//		if (fieldPrixRevient!=null) fieldPrixRevient.setAccessible(true);
	//		if (fieldNom!=null) fieldNom.setAccessible(true);
	//
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) fieldPrixVente.setAccessible(true);
	//		if (fieldNomPizza!=null) fieldNomPizza.setAccessible(true);
	//		if (fieldBase!=null) fieldBase.setAccessible(true);
	//		if (fieldGarnitures!=null) fieldGarnitures.setAccessible(true);
	//
	//		Method mContains=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizza.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("contains") && m.getParameters().length==1 && !m.getParameters()[0].getType().equals(Ingredient.class)) {//.getReturnType().equals(cPoint)) {
	//					mContains=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mContains==null) {
	//			noteDeclaration=0;
	//			noteComportement=0;
	//			System.out.println("   Aie... Je ne trouve pas la methode contains(LinkedList<Ingredient>) dans Pizza");
	//		}
	//		if (noteDeclaration>0) {
	//			if (mContains.getParameterCount()!=1) {
	//				System.out.println("   Aie... Votre methode contains ne devrait prendre qu'un parametre");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;
	//			} 
	////			else {
	////				if (!mPrixRevient.getParameters()[0].getType().equals(Object.class)) {
	////					System.out.println("   Aie... Le type du parametre de equals n'est pas celui attendu");
	////					noteDeclaration=noteDeclaration/2;
	////					noteComportement=0;
	////				}
	////			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!mContains.getReturnType().equals(boolean.class)) {
	//				System.out.println("   Aie... Votre methode contains n'a pas le type retour attendu");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//		if (noteDeclaration>0) {
	//			if (!Modifier.isPublic(mContains.getModifiers())) {
	//				System.out.println("   Aie... Votre methode contains n'est pas declaree public");
	//				noteDeclaration=noteDeclaration/2;
	//				noteComportement=0;			}
	//		}
	//
	//		if (mContains!=null) mContains.setAccessible(true);
	////		String[][] argsOk ={
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"mickey", "souris"},
	////				{"donald", "canard"},
	////				{"daffy", "canard"},
	////				{"zira", "singe"},
	////				{"zaius", "singe"}
	////		};
	////		boolean[] rep = {false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,true};
	//
	//
	////		if (fieldNom==null) {
	////			noteComportement=0;
	////		}
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//		List<Ingredient> li =new LinkedList<Ingredient>();
	//		for (Ingredient i : garnitures) {
	//			li.add(i);
	//		}
	//		li.add(new packnp.solution.Base(true));
	//		li.add(new packnp.solution.Base(false));
	//		List<Ingredient> li2 =new LinkedList<Ingredient>();
	//		for (int h=0; h<4; h++) {//Ingredient i : garnitures) {
	//			li2.add(garnitures.get(h));
	//		}
	//		//li2.add(new packnp.solution.Base(true));
	//		//li2.add(new packnp.solution.Base(false));
	//		List<Ingredient> li3 =new LinkedList<Ingredient>();
	//		for (Ingredient i : garnitures) {
	//			li3.add(i);
	//		}
	//		li3.add(new packnp.solution.Base(true));
	//		li3.add(new packnp.solution.Base(false));
	//
	//		boolean[] oracle= {
	//				true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, 
	//				true, false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, 
	//				true, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, true, false, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, true, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, false, true, false, true, false, false, false, false, false, false, false, true, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, 
	//				true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, true, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, true, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, true, false, false, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				true, false, false, true, false, false, true, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, true, false, false, true, true, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, true, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, false, false, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, 
	//				true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, true, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, true, false, false, true, false, true, false, false, false, false, true, false, false, false, true, false, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, true, true, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 
	//				true, false, true, true, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, 
	//				true, false, true, true, false, false, true, true, true, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true,    
	//		};
	//		int r=0;
	//		Object[] argssn = new Object[1];
	//	//	Object[] argVide = {};
	//	//	System.out.println("");
	//		int k=0;
	//		for (int i=0; noteComportement>0 && i<pizzas.size();i++) {//nomsGarnitures.length-1; i++) {
	//		//	System.out.println("i="+i);
	//		//	for (Ingredient ing : li) {
	//		//		System.out.println("");
	//				for (Ingredient ing2 : li2) {
	//		//			System.out.println("");
	//					for (Ingredient ing3 : li3) {
	//						
	//					LinkedList<Ingredient> lll = new LinkedList<Ingredient>();
	//		//			lll.add(ing);
	//					lll.add(ing2);
	//					lll.add(ing3);
	//		//		System.out.println("ing="+ing);
	////			for (int j=i+1; noteComportement>0 && j<nomsGarnitures.length; j++) {
	////				Object pt1=null;
	////				Object pt2=null;
	////				Objenesis objenesis = new ObjenesisStd(); 
	////				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	////				pt1 = instantiator.newInstance();
	////				pt2 = instantiator.newInstance();
	//				argssn[0]=lll;//ing;//pt2;
	//				boolean res=false;
	////				double res = 0;
	//				try {
	//				//	System.out.println("");
	////					if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	////					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[j]);
	////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	//					if (noteComportement>0) {
	//						if ( mContains!=null) {
	//							res=(boolean)(mContains.invoke(pizzas.get(i),  argssn));
	////							res=(double)(mContains.invoke(pizzas.get(i),  argVide));
	//						}
	////						System.out.print(res+", ");
	//						if ( res!=oracle[k])  {
	//							System.out.println("   Aie... sur une pizza ayant pour base \""+pizzas.get(i).getBase().getNom()+"\" et ayant pour ingredients :");	
	//						
	//							List<packnp.solution.Garniture> ll = pizzas.get(i).getGarnitures();
	//							for (packnp.solution.Garniture g : pizzas.get(i).getGarnitures()) {
	//								System.out.print("\""+g.getNom()+"\"");
	//							}
	//							System.out.println("\n   la methode contains({ing,ing2}) ou ing est l'ingredient \""+lll.get(0).getNom()+"\" et ing2 est \""+lll.get(1).getNom()+"\" retourne "+res+" au lieu de "+(!res));
	//							noteComportement=0;
	//						} 
	//						k++;
	//					} //else System.out.println("compo 0");
	//				
	//
	//				} catch (Exception e) {
	//					//System.out.println("excep");
	//					if (e instanceof InvocationTargetException) {
	//						e.getCause().printStackTrace();
	//					} else {
	//						e.printStackTrace();
	//					}
	//					noteComportement=0;
	//				}
	//			}
	//				}
	//		//	}
	//			}
	////		for (int i=0; noteComportement>0 && i<pizzas.size();i++) {//nomsGarnitures.length-1; i++) {
	////		//	System.out.println("i="+i);
	////		//	for (Ingredient ing : li) {
	////		//		System.out.println("ing="+ing);
	//////			for (int j=i+1; noteComportement>0 && j<nomsGarnitures.length; j++) {
	//////				Object pt1=null;
	//////				Object pt2=null;
	//////				Objenesis objenesis = new ObjenesisStd(); 
	//////				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cGarniture);
	//////				pt1 = instantiator.newInstance();
	//////				pt2 = instantiator.newInstance();
	////				argssn[0]=null;//ing;//pt2;
	////				boolean res=false;
	//////				double res = 0;
	////				try {
	////				//	System.out.println("");
	//////					if (fieldNom!=null) fieldNom.set(pt1,nomsGarnitures[i]);
	//////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt1,prixGarnitures[i]);
	//////					if (fieldNom!=null) fieldNom.set(pt2,nomsGarnitures[j]);
	//////					if (fieldPrixRevient!=null) fieldPrixRevient.set(pt2,prixGarnitures[j]);
	////					if (noteComportement>0) {
	////						if ( mContains!=null) {
	////							res=(boolean)(mContains.invoke(pizzas.get(i),  argssn));
	//////							res=(double)(mContains.invoke(pizzas.get(i),  argVide));
	////						}
	//////						System.out.print(res+", ");
	////						if ( res!=false) {//oracle[k])  {
	////							System.out.println("   Aie... sur une pizza ayant pour base \""+pizzas.get(i).getBase().getNom()+"\" et ayant pour ingredients :");	
	////						
	////							List<packnp.solution.Garniture> ll = pizzas.get(i).getGarnitures();
	////							for (packnp.solution.Garniture g : pizzas.get(i).getGarnitures()) {
	////								System.out.print("\""+g.getNom()+"\"");
	////							}
	////							System.out.println("\n   la methode contains(null) retourne true au lieu de false");
	////							noteComportement=0;
	////						} 
	////						k++;
	////					} //else System.out.println("compo 0");
	////				
	////
	////				} catch (Exception e) {
	////					//System.out.println("excep");
	////					if (e instanceof InvocationTargetException) {
	////						e.getCause().printStackTrace();
	////					} else {
	////						e.printStackTrace();
	////					}
	////					noteComportement=0;
	////				}
	////			}
	//		//	}
	//		//System.out.println("fin");
	//		if (noteDeclaration==0) noteComportement=0;
	//		if (noteComportement==100) {
	//			System.out.println("   Ok. Votre code passe le test avec succes.");
	//		} else System.out.println(noteComportement);
	//		return noteComportement;
	//	}
	//
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test09PizzaPersonnaliseeDeclarationEtConstructeur() {
	//	//	int notePizza=15;
	//		int noteListes=15;
	//		int noteExtends=20;
	//		int noteRienDAutre=5;
	//		int noteConstructeurPizza=30;
	//		int noteConstructeurListes=30;
	//		System.out.println("   Test verifiant la declaration de la classe PizzaPersonnalisee et le comportement de son constructeur : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//			noteRienDAutre=0;
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//			noteRienDAutre=0;
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			noteRienDAutre=0;
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			noteListes=0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//				noteListes=noteListes/2;
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				noteListes=0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			noteListes=0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//				noteListes=noteListes/2;
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				noteListes=0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//				noteConstructeurPizza=0;
	//			}
	//				fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//				noteConstructeurPizza=0;
	//			}
	//				fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//				noteConstructeurPizza=0;
	//			}
	//				fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//				noteConstructeurPizza=0;
	//			}
	//				fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			noteExtends=0;
	//		}
	//
	//		Class[] ar = {};
	//		Class[] arPizza = {cPizza};
	//		Constructor constC1=null;
	//		try {
	//			constC1 = cPizzaPersonnalisee.getDeclaredConstructor(arPizza);
	//		} catch (NoSuchMethodException e) {
	//			constC1=null;
	//		} catch (SecurityException e) {
	//			constC1=null;
	//		}
	//		if (constC1==null) {
	//			System.out.println("   Aie... je ne trouve pas le constructeur de PizzaPersonnalisee prenant en parametre une Pizza");
	//			noteConstructeurPizza=0;
	//			noteConstructeurListes=0;
	//		}
	//		if (!Modifier.isPublic(constC1.getModifiers())) {
	//			System.out.println("   Aie... Le constructeur de PizzaPersonnalisee devrait etre public");
	//			noteConstructeurPizza=noteConstructeurPizza-5;
	//			noteConstructeurListes=noteConstructeurListes-5;
	//		}
	//		constC1.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		Object[][] paramC = new Object[pizzas.size()][1];
	//
	//		for (int i=0; i<pizzas.size(); i++) {
	//			paramC[i][0]=pizzas.get(i);
	//		}
	////		Object[][][] paramC1 ={
	////				{
	////					{"cheeta", "singe"},
	////					{"king-kong", "gorille"},
	////					{"mickey", "souris"},
	////					{"donald", "canard"}
	////				},
	////				{
	////					{"singe", "cheeta"},
	////					{"gorille", "king-kong"},
	////					{"souris", "mickey"},
	////					{"canard", "donald"}
	////				}
	////		};
	//		Object ptc1=null;
	//
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; i<paramC.length ; i++) {
	//					ptc1 = (constC1.newInstance(paramC[i]));
	//					if (noteConstructeurPizza>0 && fieldNomPizza==null) {
	//						noteConstructeurPizza=0;
	//					}else if (noteConstructeurPizza>0 &&fieldNomPizza.get(ptc1)==null) {
	//						System.out.println("  Aie... appele avec une pizza de nom \""+pizzas.get(i).getNom()+"\" votre constructeur initialise la variable heritee nom a null au lieu du nom de la pizza passee en parametre");
	//						noteConstructeurPizza=0;
	//					} else {
	//						String nom = (String)(fieldNomPizza.get(ptc1));
	//						if (noteConstructeurPizza>0 && !nom.equals(pizzas.get(i).getNom())) {
	//							System.out.println("  Aie... appele avec une pizza de nom \""+pizzas.get(i).getNom()+"\" votre constructeur initialise la variable heritee nom a \""+nom+"\" au lieu du nom de la pizza passee en parametre");
	//							noteConstructeurPizza=0;
	//						} 
	//					}
	//					if (noteConstructeurPizza>0 &&fieldPrixVente==null) {
	//						noteConstructeurPizza=0;
	//					} else {
	//						double prix = (double)(fieldPrixVente.get(ptc1));
	//						if (noteConstructeurPizza>0 && Math.abs(prix-pizzas.get(i).getPrixVente())>0.01) {
	//							System.out.println("  Aie... appele avec une pizza dont le prix de vente est "+pizzas.get(i).getPrixVente()+" votre constructeur initialise la variable heritee prixVente a "+prix+" au lieu du prix de vente de la pizza passee en parametre");
	//							noteConstructeurPizza=0;
	//						} 
	//					}
	//					if (noteConstructeurPizza>0 &&fieldBase==null) {
	//						noteConstructeurPizza=0;
	//					} else {
	//						packnp.solution.Base base = (packnp.solution.Base)(fieldBase.get(ptc1));
	//						if (noteConstructeurPizza>0 &&base==null) {
	//							System.out.println("  Aie... appele avec une pizza dont la base est \""+pizzas.get(i).getBase().getNom()+"\" votre constructeur initialise la variable heritee base a null au lieu de la base de la pizza passee en parametre");
	//							noteConstructeurPizza=0;
	//						} else	if (noteConstructeurPizza>0 &&!(pizzas.get(i).getBase().getNom().equals(base.getNom()))) {
	//							System.out.println("  Aie... appele avec une pizza dont la base est \""+pizzas.get(i).getBase().getNom()+"\" votre constructeur initialise la variable heritee base a \""+base.getNom()+"\" au lieu de la base de la pizza passee en parametre");
	//							noteConstructeurPizza=0;
	//						} 
	//					}
	//					if (noteConstructeurPizza>0 &&fieldGarnitures==null) {
	//						noteConstructeurPizza=0;
	//					} else {
	//						Object garnituress = (fieldGarnitures.get(ptc1));
	//						if (noteConstructeurPizza>0 &&garnituress==null) {
	//							System.out.println("  Aie... votre constructeur initialise la liste heritee des garnitures a null au lieu de l'initialiser avec la liste des garnitures de la pizza passee en parametre");
	//							noteConstructeurPizza=0;
	//						} else	if (noteConstructeurPizza>0 &&(!(garnituress instanceof List)||!pizzas.get(i).getGarnitures().equals(garnituress))) {
	//							System.out.println("  Aie... votre constructeur doit initialiser la variable garnitures heritee avec la liste des garnitures de la pizza passee en parametre");
	//							noteConstructeurPizza=0;
	//						} 
	//					}
	//					if (noteConstructeurListes>0 &&fieldEnMoins==null) {
	//						noteConstructeurListes=0;
	//					} else {
	//						Object oem=fieldEnMoins.get(ptc1);
	//						if (noteConstructeurListes>0 &&oem==null) {
	//							System.out.println("   Aie... votre constructeur initialise enMoins a null : il devrait l'initialiser a une liste vide");
	//							noteConstructeurListes=0;
	//						} else {
	//							if (noteConstructeurListes>0 &&!(oem instanceof List)) {
	//								System.out.println("   Aie... enMoins doit etre une liste de Garniture");
	//								noteConstructeurListes=0;
	//							} else {
	//								List<packnp.solution.Garniture> loem = (List<packnp.solution.Garniture>)oem;
	//								if (noteConstructeurListes>0 &&loem.size()!=0) {
	//									System.out.println("   Aie... votre constructeur doit initialiser enMoins avec une liste vide");
	//									noteConstructeurListes=0;
	//								}
	//							}
	//							
	//						}
	//					}
	//					if (noteConstructeurListes>0 &&fieldEnPlus==null) {
	//						noteConstructeurListes=0;
	//					} else {
	//						Object oem=fieldEnPlus.get(ptc1);
	//						if (noteConstructeurListes>0 &&oem==null) {
	//							System.out.println("   Aie... votre constructeur initialise enPlus a null : il devrait l'initialiser a une liste vide");
	//							noteConstructeurListes=0;
	//						} else {
	//							if (noteConstructeurListes>0 &&!(oem instanceof List)) {
	//								System.out.println("   Aie... enPlus doit etre une liste de Garniture");
	//								noteConstructeurListes=0;
	//							} else {
	//								List<packnp.solution.Garniture> loem = (List<packnp.solution.Garniture>)oem;
	//								if (noteConstructeurListes>0 &&loem.size()!=0) {
	//									System.out.println("   Aie... votre constructeur doit initialiser enPlus avec une liste vide");
	//									noteConstructeurListes=0;
	//								}
	//							}
	//							
	//						}
	//					}
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	//		//	System.out.println("noteListes="+noteListes);
	//		///	System.out.println("noteExtends="+noteExtends);
	//		//	System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	//		//	System.out.println("noteConstructeurListes="+noteConstructeurListes);
	//		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	//			System.out.println("   Ok. Votre code passe ce test avec succes.");
	//		}
	//		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//	}
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test10PizzaPersonnaliseeGetGarnitures() {
	//	//	int notePizza=15;
	//	//	int noteListes=15;
	//	//	int noteExtends=20;
	//	//	int noteRienDAutre=5;
	//	//	int noteConstructeurPizza=30;
	//	//	int noteConstructeurListes=30;
	//		int noteDeclaration=10;
	//		int noteComportement=90;
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode getGarnitures() de la classe PizzaPersonnalisee : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//				}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//			}
	//			fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//			}
	//			fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//			}
	//			fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//			}
	//			fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			return 0;
	//		}
	//
	//		Method mGetGarnitures=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizzaPersonnalisee.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("getGarnitures")) {//.getReturnType().equals(cPoint)) {
	//					mGetGarnitures=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mGetGarnitures==null) {
	//			System.out.println("   Aie... Je ne trouve pas la methode getGarnitures() dans PizzaPersonnalisee");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(mGetGarnitures.getModifiers())) {
	//			System.out.println("   Aie... votre methode getGarnitures() doit etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		mGetGarnitures.setAccessible(true);
	////
	////		Class[] ar = {};
	////		Class[] arPizza = {cPizza};
	////		Constructor constC1=null;
	////		try {
	////			constC1 = cPizzaPersonnalisee.getDeclaredConstructor(arPizza);
	////		} catch (NoSuchMethodException e) {
	////			constC1=null;
	////		} catch (SecurityException e) {
	////			constC1=null;
	////		}
	////		if (constC1==null) {
	////			System.out.println("   Aie... je ne trouve pas le constructeur de PizzaPersonnalisee prenant en parametre une Pizza");
	////			noteConstructeurPizza=0;
	////			noteConstructeurListes=0;
	////		}
	////		if (!Modifier.isPublic(constC1.getModifiers())) {
	////			System.out.println("   Aie... Le constructeur de PizzaPersonnalisee devrait etre public");
	////			noteConstructeurPizza=noteConstructeurPizza-5;
	////			noteConstructeurListes=noteConstructeurListes-5;
	////		}
	////		constC1.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		int[] piz = {
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//		};
	//		Object[][] paramC = new Object[piz.length][1];
	//
	//		for (int i=0; i<piz.length; i++) {
	//			paramC[i][0]=pizzas.get(piz[i]);
	//		}
	//		int[][] enM = {
	//				{}, {0}, {27}, {37}, {32},	
	//				{}, {}, {}, {}, {},	
	//				{}, {0}, {27}, {37}, {32},	
	//		};
	//		int[][] enP = {
	//				{}, {}, {}, {}, {},
	//				{}, {1}, {2}, {38}, {39},	
	//				{}, {1}, {2}, {38}, {39},	
	//		};
	//		int[][] oracle = {
	//				{0,27,13,24,37,32},{27,13,24,37,32},{0,13,24,37,32},{0,27,13,24,32},{0,27,13,24,37},
	//				{0,27,13,24,37,32},{0,1,27,13,24,37,32},{0,2,27,13,24,37,32},{0,27,13,24,37,38,32},{0,27,13,24,37,39,32},
	//				{0,27,13,24,37,32},{1,27,13,24,37,32},{0,2,13,24,37,32},{0,27,13,24,32,38},{0,27,13,24,37,39},
	//		};
	//		
	//		
	////		Object[][][] paramC1 ={
	////				{
	////					{"cheeta", "singe"},
	////					{"king-kong", "gorille"},
	////					{"mickey", "souris"},
	////					{"donald", "canard"}
	////				},
	////				{
	////					{"singe", "cheeta"},
	////					{"gorille", "king-kong"},
	////					{"souris", "mickey"},
	////					{"canard", "donald"}
	////				}
	////		};
	//		Object ptc1=null;
	//Object[] argVide= {};
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; noteComportement>0 && i<paramC.length ; i++) {
	//					Object pt1=null;
	//					Objenesis objenesis = new ObjenesisStd(); 
	//					ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cPizzaPersonnalisee);
	//					pt1 = instantiator.newInstance();
	//					if (fieldBase!=null) {
	//						fieldBase.set(pt1, pizzas.get(piz[i]).getBase());
	//					}
	//					if (fieldNomPizza!=null) {
	//						fieldNomPizza.set(pt1, pizzas.get(piz[i]).getNom());
	//					}
	//					if (fieldPrixVente!=null) {
	//						fieldPrixVente.set(pt1, pizzas.get(piz[i]).getPrixVente());
	//					}
	//					if (fieldGarnitures!=null) {
	//						fieldGarnitures.set(pt1, pizzas.get(piz[i]).getGarnitures());
	//					}
	//					LinkedList<packnp.solution.Garniture> lenm = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enM[i].length;l++) {
	//						lenm.add(garnitures.get(enM[i][l]));
	//					}
	//					if (fieldEnMoins!=null) {
	//						fieldEnMoins.set(pt1, lenm);
	//					}
	//					LinkedList<packnp.solution.Garniture> lenp = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enP[i].length;l++) {
	//						lenp.add(garnitures.get(enP[i][l]));
	//					}
	//					if (fieldEnPlus!=null) {
	//						fieldEnPlus.set(pt1, lenp);
	//					}
	//
	//					Object res=(mGetGarnitures.invoke(pt1,  argVide));
	//					if (res==null) {
	//						System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//						for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//							System.out.print(gg.getNom()+", ");
	//						}
	//						System.out.println("\n et ayant pour liste enMoins : ");
	//						for (packnp.solution.Garniture gg:lenm) {
	//							System.out.print(gg.getNom()+", ");
	//						}
	//						System.out.println("\n et pour liste enPlus : ");
	//						for (packnp.solution.Garniture gg:lenp) {
	//							System.out.print(gg.getNom()+", ");
	//						}
	//						System.out.println("\n la methode getGarnitures retourne null");
	//						noteComportement=0;
	//					} else {
	//						List<packnp.solution.Garniture> resg = (List<packnp.solution.Garniture>)res;
	//						LinkedList<packnp.solution.Garniture> loracle = new LinkedList<packnp.solution.Garniture>();
	//						for (int m=0;  m<oracle[i].length; m++) {
	//						//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//							loracle.add(garnitures.get(oracle[i][m]));
	//						}
	//						if (!tool.egaux(resg,loracle)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n la methode getGarnitures retourne une liste composee des garnitures :");
	//							for (packnp.solution.Garniture gg:resg) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu d'une liste composee des Garnitures : ");
	//							for (packnp.solution.Garniture gg:loracle) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						}
	//					}
	//
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	////			System.out.println("noteListes="+noteListes);
	////			System.out.println("noteExtends="+noteExtends);
	////			System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	////			System.out.println("noteConstructeurListes="+noteConstructeurListes);
	////		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//			if (noteDeclaration+noteComportement==100) {
	//				System.out.println("   Ok. Votre code passe ce test avec succes.");
	//			}
	//			return noteDeclaration+noteComportement;
	//
	//	}
	//
	//	
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test11PizzaPersonnaliseeRetirer() {
	//	//	int notePizza=15;
	//	//	int noteListes=15;
	//	//	int noteExtends=20;
	//	//	int noteRienDAutre=5;
	//	//	int noteConstructeurPizza=30;
	//	//	int noteConstructeurListes=30;
	//		int noteDeclaration=10;
	//		int noteComportement=90;
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode retirer(Garniture) de la classe PizzaPersonnalisee : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//				}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//			}
	//			fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//			}
	//			fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//			}
	//			fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//			}
	//			fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			return 0;
	//		}
	//
	//		Method mRetirer=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizzaPersonnalisee.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("retirer")) {//.getReturnType().equals(cPoint)) {
	//					mRetirer=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mRetirer==null) {
	//			System.out.println("   Aie... Je ne trouve pas la methode retirer(Garniture) dans PizzaPersonnalisee");
	//			return 0;
	//		}
	//		if (mRetirer.getParameters().length!=1 || !mRetirer.getParameters()[0].getType().equals(packnp.solution.Garniture.class)) {
	//			System.out.println("   Aie... retirer doit avoir un unique parametre de type packnp.solution.Garniture");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(mRetirer.getModifiers())) {
	//			System.out.println("   Aie... votre methode retirer(Garniture) doit etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		mRetirer.setAccessible(true);
	////
	////		Class[] ar = {};
	////		Class[] arPizza = {cPizza};
	////		Constructor constC1=null;
	////		try {
	////			constC1 = cPizzaPersonnalisee.getDeclaredConstructor(arPizza);
	////		} catch (NoSuchMethodException e) {
	////			constC1=null;
	////		} catch (SecurityException e) {
	////			constC1=null;
	////		}
	////		if (constC1==null) {
	////			System.out.println("   Aie... je ne trouve pas le constructeur de PizzaPersonnalisee prenant en parametre une Pizza");
	////			noteConstructeurPizza=0;
	////			noteConstructeurListes=0;
	////		}
	////		if (!Modifier.isPublic(constC1.getModifiers())) {
	////			System.out.println("   Aie... Le constructeur de PizzaPersonnalisee devrait etre public");
	////			noteConstructeurPizza=noteConstructeurPizza-5;
	////			noteConstructeurListes=noteConstructeurListes-5;
	////		}
	////		constC1.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		int[] piz = {
	//				8, 8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//		};
	//		int[] gar = {
	//			0, 27, 13, 37, 32, 26,	
	//			0, 27, 13, 37, 32,	
	//			0, 27, 13, 37, 32,	
	//			2, 3, 4, 38, 39,
	//			2, 3, 4, 38, 39,
	//		};
	//		Object[][] paramC = new Object[piz.length][1];
	//
	//		for (int i=0; i<piz.length; i++) {
	//			paramC[i][0]=pizzas.get(piz[i]);
	//		}
	//		int[][] enMAvant = {
	//				{}, {}, {}, {}, {},	{},	
	//				{0}, {27}, {13}, {37}, {32},	
	//				{13,24}, {13,24}, {13,24}, {13,24}, {13,24},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//		};
	//		int[][] enPAvant = {
	//				{}, {}, {}, {}, {},{},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{2,6,7}, {3,6,7}, {4,6,7}, {38,6,7}, {39,6,7},
	//				{2}, {3}, {4}, {38}, {39},
	////				{2,6,7}, {3,6,7}, {4,6,7}, {38,6,7}, {39,6,7},
	//		};
	//		int[][] enMApres = {
	//				{0}, {27}, {13}, {37}, {32},{},	
	//				{0}, {27}, {13}, {37}, {32},	
	//				{0,13,24}, {27,13,24}, {13,24}, {13,24,37}, {13,24,32},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//		};
	//		int[][] enPApres = {
	//				{}, {}, {}, {}, {},{},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{6,7}, {6,7}, {6,7}, {6,7}, {6,7},
	//				{}, {}, {}, {}, {},
	////				{6,7}, {6,7}, {6,7}, {6,7}, {6,7},
	//		};
	////		int[][] oracle = {
	////				{0,27,13,24,37,32},{27,13,24,37,32},{0,13,24,37,32},{0,27,13,24,32},{0,27,13,24,37},
	////				{0,27,13,24,37,32},{0,1,27,13,24,37,32},{0,2,27,13,24,37,32},{0,27,13,24,37,38,32},{0,27,13,24,37,39,32},
	////				{0,27,13,24,37,32},{1,27,13,24,37,32},{0,2,13,24,37,32},{0,27,13,24,32,38},{0,27,13,24,37,39},
	////		};
	//		
	//		
	////		Object[][][] paramC1 ={
	////				{
	////					{"cheeta", "singe"},
	////					{"king-kong", "gorille"},
	////					{"mickey", "souris"},
	////					{"donald", "canard"}
	////				},
	////				{
	////					{"singe", "cheeta"},
	////					{"gorille", "king-kong"},
	////					{"souris", "mickey"},
	////					{"canard", "donald"}
	////				}
	////		};
	//		Object ptc1=null;
	////Object[] argVide= {};
	//Object[] arg1 = new Object[1];
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; noteComportement>0 && i<paramC.length ; i++) {
	//					Object pt1=null;
	//					Objenesis objenesis = new ObjenesisStd(); 
	//					ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cPizzaPersonnalisee);
	//					pt1 = instantiator.newInstance();
	//					if (fieldBase!=null) {
	//						fieldBase.set(pt1, pizzas.get(piz[i]).getBase());
	//					}
	//					if (fieldNomPizza!=null) {
	//						fieldNomPizza.set(pt1, pizzas.get(piz[i]).getNom());
	//					}
	//					if (fieldPrixVente!=null) {
	//						fieldPrixVente.set(pt1, pizzas.get(piz[i]).getPrixVente());
	//					}
	//					if (fieldGarnitures!=null) {
	//						fieldGarnitures.set(pt1, pizzas.get(piz[i]).getGarnitures());
	//					}
	//					LinkedList<packnp.solution.Garniture> lenm = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enMAvant[i].length;l++) {
	//						lenm.add(garnitures.get(enMAvant[i][l]));
	//					}
	//					if (fieldEnMoins!=null) {
	//						fieldEnMoins.set(pt1, lenm);
	//					}
	//					LinkedList<packnp.solution.Garniture> lenp = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enPAvant[i].length;l++) {
	//						lenp.add(garnitures.get(enPAvant[i][l]));
	//					}
	//					if (fieldEnPlus!=null) {
	//						fieldEnPlus.set(pt1, lenp);
	//					}
	//					arg1[0]=garnitures.get(gar[i]);
	//
	//					//Object res=
	//							mRetirer.invoke(pt1,  arg1);
	////					if (res==null) {
	////						System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////						for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et ayant pour liste enMoins : ");
	////						for (packnp.solution.Garniture gg:lenm) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et pour liste enPlus : ");
	////						for (packnp.solution.Garniture gg:lenp) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n la methode getGarnitures retourne null");
	////						noteComportement=0;
	////					} else {
	//							List<packnp.solution.Garniture> resg = (List<packnp.solution.Garniture>)fieldGarnitures.get(pt1);
	//							List<packnp.solution.Garniture> resMP = (List<packnp.solution.Garniture>)fieldEnMoins.get(pt1);
	//							List<packnp.solution.Garniture> resPP = (List<packnp.solution.Garniture>)fieldEnPlus.get(pt1);
	//							LinkedList<packnp.solution.Garniture> oracleM = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enMApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleM.add(garnitures.get(enMApres[i][m]));
	//							}
	//							LinkedList<packnp.solution.Garniture> oracleP = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enPApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleP.add(garnitures.get(enPApres[i][m]));
	//							}
	//						if (!tool.egaux(resg,pizzas.get(piz[i]).getGarnitures())) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode retirer("+garnitures.get(gar[i]).getNom()+") la liste des Garnitures heritee est composee de :");
	//							for (packnp.solution.Garniture gg:resg) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu d'une liste composee des Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} else if (!tool.egaux(resMP,oracleM)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode retirer("+garnitures.get(gar[i]).getNom()+") la liste enMoins est composee de :");
	//							for (packnp.solution.Garniture gg:resMP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleM) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} else if (!tool.egaux(resPP,oracleP)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode retirer("+garnitures.get(gar[i]).getNom()+") la liste enPlus est composee de :");
	//							for (packnp.solution.Garniture gg:resPP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} 
	////					}
	//
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	////			System.out.println("noteListes="+noteListes);
	////			System.out.println("noteExtends="+noteExtends);
	////			System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	////			System.out.println("noteConstructeurListes="+noteConstructeurListes);
	////		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//			if (noteDeclaration+noteComportement==100) {
	//				System.out.println("   Ok. Votre code passe ce test avec succes.");
	//			}
	//			return noteDeclaration+noteComportement;
	//
	//	}
	//
	//	
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test12PizzaPersonnaliseeAjouter() {
	//	//	int notePizza=15;
	//	//	int noteListes=15;
	//	//	int noteExtends=20;
	//	//	int noteRienDAutre=5;
	//	//	int noteConstructeurPizza=30;
	//	//	int noteConstructeurListes=30;
	//		int noteDeclaration=10;
	//		int noteComportement=90;
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode ajouter(Garniture) de la classe PizzaPersonnalisee : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//				}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//			}
	//			fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//			}
	//			fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//			}
	//			fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//			}
	//			fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			return 0;
	//		}
	//
	//		Method mAjouter=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizzaPersonnalisee.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("ajouter")) {//.getReturnType().equals(cPoint)) {
	//					mAjouter=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mAjouter==null) {
	//			System.out.println("   Aie... Je ne trouve pas la methode ajouter(Garniture) dans PizzaPersonnalisee");
	//			return 0;
	//		}
	//		if (mAjouter.getParameters().length!=1 || !mAjouter.getParameters()[0].getType().equals(packnp.solution.Garniture.class)) {
	//			System.out.println("   Aie... ajouter doit avoir un unique parametre de type packnp.solution.Garniture");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(mAjouter.getModifiers())) {
	//			System.out.println("   Aie... votre methode ajouter(Garniture) doit etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		mAjouter.setAccessible(true);
	////
	////		Class[] ar = {};
	////		Class[] arPizza = {cPizza};
	////		Constructor constC1=null;
	////		try {
	////			constC1 = cPizzaPersonnalisee.getDeclaredConstructor(arPizza);
	////		} catch (NoSuchMethodException e) {
	////			constC1=null;
	////		} catch (SecurityException e) {
	////			constC1=null;
	////		}
	////		if (constC1==null) {
	////			System.out.println("   Aie... je ne trouve pas le constructeur de PizzaPersonnalisee prenant en parametre une Pizza");
	////			noteConstructeurPizza=0;
	////			noteConstructeurListes=0;
	////		}
	////		if (!Modifier.isPublic(constC1.getModifiers())) {
	////			System.out.println("   Aie... Le constructeur de PizzaPersonnalisee devrait etre public");
	////			noteConstructeurPizza=noteConstructeurPizza-5;
	////			noteConstructeurListes=noteConstructeurListes-5;
	////		}
	////		constC1.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		int[] piz = {
	//				8, 8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//		};
	//		int[] gar = {
	//			0, 27, 13, 37, 32, 26,	
	//			0, 27, 13, 37, 32,	
	//			0, 27, 13, 37, 32,	
	//			2, 3, 4, 38, 39,
	//			2, 3, 4, 38, 39,
	//			2, 3, 4, 38, 39,
	//		};
	//		Object[][] paramC = new Object[piz.length][1];
	//
	//		for (int i=0; i<piz.length; i++) {
	//			paramC[i][0]=pizzas.get(piz[i]);
	//		}
	//		int[][] enMAvant = {
	//				{}, {}, {}, {}, {},	{},	
	//				{0}, {27}, {13}, {37}, {32},	
	//				{13,24}, {13,24}, {13,24}, {13,24}, {13,24},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//		};
	//		int[][] enPAvant = {
	//				{}, {}, {}, {}, {},{},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{2}, {3}, {4}, {38}, {39},
	//				{6,7}, {6,7}, {6,7}, {6,7}, {6,7},
	//		};
	//		int[][] enMApres = {
	//				{}, {}, {}, {}, {},{},	
	//				{}, {}, {}, {}, {},	
	//				{13,24}, {13,24}, {24}, {13,24}, {13,24},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//		};
	//		int[][] enPApres = {
	//				{}, {}, {}, {}, {},{26},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{2}, {3}, {4}, {38}, {39},
	//				{2}, {3}, {4}, {38}, {39},
	//				{2,6,7}, {3,6,7}, {4,6,7}, {38,6,7}, {39,6,7},
	//		};
	////		int[][] oracle = {
	////				{0,27,13,24,37,32},{27,13,24,37,32},{0,13,24,37,32},{0,27,13,24,32},{0,27,13,24,37},
	////				{0,27,13,24,37,32},{0,1,27,13,24,37,32},{0,2,27,13,24,37,32},{0,27,13,24,37,38,32},{0,27,13,24,37,39,32},
	////				{0,27,13,24,37,32},{1,27,13,24,37,32},{0,2,13,24,37,32},{0,27,13,24,32,38},{0,27,13,24,37,39},
	////		};
	//		
	//		
	////		Object[][][] paramC1 ={
	////				{
	////					{"cheeta", "singe"},
	////					{"king-kong", "gorille"},
	////					{"mickey", "souris"},
	////					{"donald", "canard"}
	////				},
	////				{
	////					{"singe", "cheeta"},
	////					{"gorille", "king-kong"},
	////					{"souris", "mickey"},
	////					{"canard", "donald"}
	////				}
	////		};
	//		Object ptc1=null;
	////Object[] argVide= {};
	//Object[] arg1 = new Object[1];
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; noteComportement>0 && i<paramC.length ; i++) {
	//					Object pt1=null;
	//					Objenesis objenesis = new ObjenesisStd(); 
	//					ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cPizzaPersonnalisee);
	//					pt1 = instantiator.newInstance();
	//					if (fieldBase!=null) {
	//						fieldBase.set(pt1, pizzas.get(piz[i]).getBase());
	//					}
	//					if (fieldNomPizza!=null) {
	//						fieldNomPizza.set(pt1, pizzas.get(piz[i]).getNom());
	//					}
	//					if (fieldPrixVente!=null) {
	//						fieldPrixVente.set(pt1, pizzas.get(piz[i]).getPrixVente());
	//					}
	//					if (fieldGarnitures!=null) {
	//						fieldGarnitures.set(pt1, pizzas.get(piz[i]).getGarnitures());
	//					}
	//					LinkedList<packnp.solution.Garniture> lenm = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enMAvant[i].length;l++) {
	//						lenm.add(garnitures.get(enMAvant[i][l]));
	//					}
	//					if (fieldEnMoins!=null) {
	//						fieldEnMoins.set(pt1, lenm);
	//					}
	//					LinkedList<packnp.solution.Garniture> lenp = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enPAvant[i].length;l++) {
	//						lenp.add(garnitures.get(enPAvant[i][l]));
	//					}
	//					if (fieldEnPlus!=null) {
	//						fieldEnPlus.set(pt1, lenp);
	//					}
	//					arg1[0]=garnitures.get(gar[i]);
	//
	//					//Object res=
	//							mAjouter.invoke(pt1,  arg1);
	////					if (res==null) {
	////						System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////						for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et ayant pour liste enMoins : ");
	////						for (packnp.solution.Garniture gg:lenm) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et pour liste enPlus : ");
	////						for (packnp.solution.Garniture gg:lenp) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n la methode getGarnitures retourne null");
	////						noteComportement=0;
	////					} else {
	//							List<packnp.solution.Garniture> resg = (List<packnp.solution.Garniture>)fieldGarnitures.get(pt1);
	//							List<packnp.solution.Garniture> resMP = (List<packnp.solution.Garniture>)fieldEnMoins.get(pt1);
	//							List<packnp.solution.Garniture> resPP = (List<packnp.solution.Garniture>)fieldEnPlus.get(pt1);
	//							LinkedList<packnp.solution.Garniture> oracleM = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enMApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleM.add(garnitures.get(enMApres[i][m]));
	//							}
	//							LinkedList<packnp.solution.Garniture> oracleP = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enPApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleP.add(garnitures.get(enPApres[i][m]));
	//							}
	//						if (!tool.egaux(resg,pizzas.get(piz[i]).getGarnitures())) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode ajouter("+garnitures.get(gar[i]).getNom()+") la liste des Garnitures heritee est composee de :");
	//							for (packnp.solution.Garniture gg:resg) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu d'une liste composee des Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} else if (!tool.egaux(resMP,oracleM)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode ajouter("+garnitures.get(gar[i]).getNom()+") la liste enMoins est composee de :");
	//							for (packnp.solution.Garniture gg:resMP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleM) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} else if (!tool.egaux(resPP,oracleP)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode ajouter("+garnitures.get(gar[i]).getNom()+") la liste enPlus est composee de :");
	//							for (packnp.solution.Garniture gg:resPP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} 
	////					}
	//
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	////			System.out.println("noteListes="+noteListes);
	////			System.out.println("noteExtends="+noteExtends);
	////			System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	////			System.out.println("noteConstructeurListes="+noteConstructeurListes);
	////		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//			if (noteDeclaration+noteComportement==100) {
	//				System.out.println("   Ok. Votre code passe ce test avec succes.");
	//			}
	//			return noteDeclaration+noteComportement;
	//
	//	}
	//
	//
	//	
	//	
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test13PizzaPersonnaliseePrixVente() {
	//	//	int notePizza=15;
	//	//	int noteListes=15;
	//	//	int noteExtends=20;
	//	//	int noteRienDAutre=5;
	//	//	int noteConstructeurPizza=30;
	//	//	int noteConstructeurListes=30;
	//		int noteDeclaration=10;
	//		int noteComportement=90;
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode getPrixVente() de la classe PizzaPersonnalisee : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//				}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//			}
	//			fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//			}
	//			fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//			}
	//			fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//			}
	//			fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			return 0;
	//		}
	//
	//		Method mPrixVente=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizzaPersonnalisee.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("getPrixVente")) {//.getReturnType().equals(cPoint)) {
	//					mPrixVente=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mPrixVente==null) {
	//			System.out.println("   Aie... Je ne trouve pas la methode getPrixVente() dans PizzaPersonnalisee");
	//			return 0;
	//		}
	//		if (mPrixVente.getParameters().length!=0 ) {//| !mPrixVente.getParameters()[0].getType().equals(packnp.solution.Garniture.class)) {
	//			System.out.println("   Aie... getPrixVente ne doit avoir aucun parametre");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(mPrixVente.getModifiers())) {
	//			System.out.println("   Aie... votre methode getPrixVente() doit etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		mPrixVente.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		int[] piz = {
	//				8, 8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				10, 10, 10, 10, 10,	
	//				10, 10, 10, 10, 10,	
	//				10, 10, 10, 10, 10,	
	//		};
	///*		int[] gar = {
	//			0, 27, 13, 37, 32, 26,	
	//			0, 27, 13, 37, 32,	
	//			0, 27, 13, 37, 32,	
	//			2, 3, 4, 38, 39,
	//			2, 3, 4, 38, 39,
	//			2, 3, 4, 38, 39,
	//		};*/
	//		Object[][] paramC = new Object[piz.length][1];
	//		for (int i=0; i<piz.length; i++) {
	//			paramC[i][0]=pizzas.get(piz[i]);
	//		}
	//		int[][] enMAvant = {
	//				{}, {}, {}, {}, {},	{},	
	//				{0}, {27}, {13}, {37}, {32},	
	//				{13,24}, {13,24}, {13,24}, {13,24}, {13,24},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {27}, {27,23}, {27,23,30}, {27,23,17,8},	
	//				{}, {27}, {27,23}, {27,23,30}, {27,23,17,8},	
	//				{}, {27}, {27,23}, {27,23,30}, {27,23,17,8},	
	//		};
	//		int[][] enPAvant = {
	//				{}, {}, {}, {}, {},{},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{2}, {3}, {4}, {38}, {39},
	//				{6,7}, {6,7}, {6,7}, {6,7}, {6,7},
	//				{}, {}, {},{}, {},
	//				{1}, {1}, {1},{1}, {1},
	//				{1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5},{1,2,3,4,5}, {1,2,3,4,5},
	//		};
	///*		int[][] enMApres = {
	//				{}, {}, {}, {}, {},{},	
	//				{}, {}, {}, {}, {},	
	//				{13,24}, {13,24}, {24}, {13,24}, {13,24},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//		};
	//		int[][] enPApres = {
	//				{}, {}, {}, {}, {},{26},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{2}, {3}, {4}, {38}, {39},
	//				{2}, {3}, {4}, {38}, {39},
	//				{2,6,7}, {3,6,7}, {4,6,7}, {38,6,7}, {39,6,7},
	//		};
	////*///		int[][] oracle = {
	////				{0,27,13,24,37,32},{27,13,24,37,32},{0,13,24,37,32},{0,27,13,24,32},{0,27,13,24,37},
	////				{0,27,13,24,37,32},{0,1,27,13,24,37,32},{0,2,27,13,24,37,32},{0,27,13,24,37,38,32},{0,27,13,24,37,39,32},
	////				{0,27,13,24,37,32},{1,27,13,24,37,32},{0,2,13,24,37,32},{0,27,13,24,32,38},{0,27,13,24,37,39},
	////		};
	//		double[] oracle = {
	//				9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,9.0,10.0,10.0,10.0,10.0,10.0,11.9,11.9,11.9,11.9,11.9,9.9,9.9,9.9,9.9,9.9,10.9,9.9,9.9,9.9,9.9, 
	//		};
	//		Object ptc1=null;
	//Object[] argVide= {};
	////Object[] arg1 = new Object[1];
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; noteComportement>0 && i<paramC.length ; i++) {
	//					Object pt1=null;
	//					Objenesis objenesis = new ObjenesisStd(); 
	//					ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cPizzaPersonnalisee);
	//					pt1 = instantiator.newInstance();
	//					if (fieldBase!=null) {
	//						fieldBase.set(pt1, pizzas.get(piz[i]).getBase());
	//					}
	//					if (fieldNomPizza!=null) {
	//						fieldNomPizza.set(pt1, pizzas.get(piz[i]).getNom());
	//					}
	//					if (fieldPrixVente!=null) {
	//						fieldPrixVente.set(pt1, pizzas.get(piz[i]).getPrixVente());
	//					}
	//					if (fieldGarnitures!=null) {
	//						fieldGarnitures.set(pt1, pizzas.get(piz[i]).getGarnitures());
	//					}
	//					LinkedList<packnp.solution.Garniture> lenm = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enMAvant[i].length;l++) {
	//						lenm.add(garnitures.get(enMAvant[i][l]));
	//					}
	//					if (fieldEnMoins!=null) {
	//						fieldEnMoins.set(pt1, lenm);
	//					}
	//					LinkedList<packnp.solution.Garniture> lenp = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enPAvant[i].length;l++) {
	//						lenp.add(garnitures.get(enPAvant[i][l]));
	//					}
	//					if (fieldEnPlus!=null) {
	//						fieldEnPlus.set(pt1, lenp);
	//					}
	//	//				arg1[0]=garnitures.get(gar[i]);
	//
	//					Object res=	mPrixVente.invoke(pt1,  argVide);
	//double pri = (double)res;
	//					//					if (res==null) {
	////						System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////						for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et ayant pour liste enMoins : ");
	////						for (packnp.solution.Garniture gg:lenm) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et pour liste enPlus : ");
	////						for (packnp.solution.Garniture gg:lenp) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n la methode getGarnitures retourne null");
	////						noteComportement=0;
	////					} else {
	///*							List<packnp.solution.Garniture> resg = (List<packnp.solution.Garniture>)fieldGarnitures.get(pt1);
	//							List<packnp.solution.Garniture> resMP = (List<packnp.solution.Garniture>)fieldEnMoins.get(pt1);
	//							List<packnp.solution.Garniture> resPP = (List<packnp.solution.Garniture>)fieldEnPlus.get(pt1);
	//							LinkedList<packnp.solution.Garniture> oracleM = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enMApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleM.add(garnitures.get(enMApres[i][m]));
	//							}
	//							LinkedList<packnp.solution.Garniture> oracleP = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enPApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleP.add(garnitures.get(enPApres[i][m]));
	//							}//*/
	////System.out.print(pri+",");
	//						if (Math.abs(pri-oracle[i])>0.01) {//!tool.egaux(resg,pizzas.get(piz[i]).getGarnitures())) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza au prix de vente de "+pizzas.get(piz[i]).getPrixVente()+" et ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n la methode getPrixVente() retourne "+pri+" au lieu de "+oracle[i]);//la liste des Garnitures heritee est composee de :");
	//			/*				for (packnp.solution.Garniture gg:resg) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu d'une liste composee des Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();*/
	//							noteComportement=0;
	//						} /*else if (!tool.egaux(resMP,oracleM)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode ajouter("+garnitures.get(gar[i]).getNom()+") la liste enMoins est composee de :");
	//							for (packnp.solution.Garniture gg:resMP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleM) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} else if (!tool.egaux(resPP,oracleP)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode ajouter("+garnitures.get(gar[i]).getNom()+") la liste enPlus est composee de :");
	//							for (packnp.solution.Garniture gg:resPP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						}
	//						//*/ 
	////					}
	//
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	////			System.out.println("noteListes="+noteListes);
	////			System.out.println("noteExtends="+noteExtends);
	////			System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	////			System.out.println("noteConstructeurListes="+noteConstructeurListes);
	////		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//			if (noteDeclaration+noteComportement==100) {
	//				System.out.println("   Ok. Votre code passe ce test avec succes.");
	//			}
	//			return noteDeclaration+noteComportement;
	//
	//	}
	//
	//	
	//
	//	
	//	
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test14PizzaPersonnaliseePrixRevient() {
	//	//	int notePizza=15;
	//	//	int noteListes=15;
	//	//	int noteExtends=20;
	//	//	int noteRienDAutre=5;
	//	//	int noteConstructeurPizza=30;
	//	//	int noteConstructeurListes=30;
	//		int noteDeclaration=10;
	//		int noteComportement=90;
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode getPrixRevient() de la classe PizzaPersonnalisee : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//				}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//			}
	//			fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//			}
	//			fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//			}
	//			fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//			}
	//			fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			return 0;
	//		}
	//
	//		Method mPrixVente=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizzaPersonnalisee.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("getPrixRevient")) {//.getReturnType().equals(cPoint)) {
	//					mPrixVente=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mPrixVente==null) {
	//			try {
	//				Method[] methods = cPizza.getDeclaredMethods();
	//				for (Method m : methods) {
	//					if (m.getName().equals("getPrixRevient")) {//.getReturnType().equals(cPoint)) {
	//						mPrixVente=m;
	//					} else{ 
	//						mAutre=m;
	//					}
	//				}
	//			} catch (Exception e1) {
	//			} 
	//			if (mPrixVente==null) {
	//			System.out.println("   Aie... Je ne trouve la methode getPrixRevient() ni dans PizzaPersonnalisee ni dans Pizza");
	//			return 0;
	//			}
	//		}
	//		if (mPrixVente.getParameters().length!=0 ) {//| !mPrixVente.getParameters()[0].getType().equals(packnp.solution.Garniture.class)) {
	//			System.out.println("   Aie... getPrixRevient ne doit avoir aucun parametre");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(mPrixVente.getModifiers())) {
	//			System.out.println("   Aie... votre methode getPrixRevient() doit etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		mPrixVente.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		int[] piz = {
	//				8, 8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				8, 8, 8, 8, 8,	
	//				10, 10, 10, 10, 10,	
	//				10, 10, 10, 10, 10,	
	//				10, 10, 10, 10, 10,	
	//		};
	///*		int[] gar = {
	//			0, 27, 13, 37, 32, 26,	
	//			0, 27, 13, 37, 32,	
	//			0, 27, 13, 37, 32,	
	//			2, 3, 4, 38, 39,
	//			2, 3, 4, 38, 39,
	//			2, 3, 4, 38, 39,
	//		};*/
	//		Object[][] paramC = new Object[piz.length][1];
	//		for (int i=0; i<piz.length; i++) {
	//			paramC[i][0]=pizzas.get(piz[i]);
	//		}
	//		int[][] enMAvant = {
	//				{}, {}, {}, {}, {},	{},	
	//				{0}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	////				{0}, {27}, {13}, {37}, {32},	
	////				{13,24}, {13,24}, {13,24}, {13,24}, {13,24},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {27}, {27,23}, {27,23,30}, {27,23},	
	//				{}, {27}, {27,23}, {27,23,30}, {27,23},	
	//				{}, {27}, {27,23}, {27,23,30}, {27,23},	
	//		};
	//		int[][] enPAvant = {
	//				{}, {}, {}, {}, {},{},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{2}, {3}, {4}, {38}, {39},
	//				{6,7}, {6,7}, {6,7}, {6,7}, {6,7},
	//				{}, {}, {},{}, {},
	//				{1}, {1}, {1},{1}, {1},
	//				{1,2,3,4,5}, {1,2,3,4,5}, {1,2,3,4,5},{1,2,3,4,5}, {1,2,3,4,5},
	//		};
	///*		int[][] enMApres = {
	//				{}, {}, {}, {}, {},{},	
	//				{}, {}, {}, {}, {},	
	//				{13,24}, {13,24}, {24}, {13,24}, {13,24},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//				{}, {}, {}, {}, {},	
	//		};
	//		int[][] enPApres = {
	//				{}, {}, {}, {}, {},{26},
	//				{}, {}, {}, {}, {},
	//				{}, {}, {}, {}, {},
	//				{2}, {3}, {4}, {38}, {39},
	//				{2}, {3}, {4}, {38}, {39},
	//				{2,6,7}, {3,6,7}, {4,6,7}, {38,6,7}, {39,6,7},
	//		};
	////*///		int[][] oracle = {
	////				{0,27,13,24,37,32},{27,13,24,37,32},{0,13,24,37,32},{0,27,13,24,32},{0,27,13,24,37},
	////				{0,27,13,24,37,32},{0,1,27,13,24,37,32},{0,2,27,13,24,37,32},{0,27,13,24,37,38,32},{0,27,13,24,37,39,32},
	////				{0,27,13,24,37,32},{1,27,13,24,37,32},{0,2,13,24,37,32},{0,27,13,24,32,38},{0,27,13,24,37,39},
	////		};
	//		double[] oracle = {
	//				2.99,2.99,2.99,2.99,2.99,2.99,2.4400000000000004,2.9000000000000004,1.99,2.5700000000000003,2.79,1.79,1.79,1.79,1.79,1.79,2.99,2.99,2.99,2.99,2.99,3.7600000000000002,3.95,3.64,3.18,3.2600000000000002,4.5600000000000005,4.5600000000000005,4.5600000000000005,4.5600000000000005,4.5600000000000005,3.08,2.9899999999999998,2.58,1.68,2.58,3.77,3.6799999999999997,3.27,2.37,3.27,  
	//		};
	//		double [] oracle2 = {
	//				2.99,2.99,2.99,2.99,2.99,2.99,2.4400000000000004,2.99,2.99,2.99,2.99,2.99,2.99,2.99,2.99,2.99,2.99,2.99,2.99,2.99,2.99,3.7600000000000002,3.95,3.64,3.18,3.2600000000000002,4.5600000000000005,4.5600000000000005,4.5600000000000005,4.5600000000000005,4.5600000000000005,3.08,2.9899999999999998,2.58,1.68,2.58,3.77,3.6799999999999997,3.27,2.37,3.27, 
	//		};
	//		Object ptc1=null;
	//Object[] argVide= {};
	////Object[] arg1 = new Object[1];
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; noteComportement>0 && i<paramC.length ; i++) {
	//					Object pt1=null;
	//					Objenesis objenesis = new ObjenesisStd(); 
	//					ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cPizzaPersonnalisee);
	//					pt1 = instantiator.newInstance();
	//					if (fieldBase!=null) {
	//						fieldBase.set(pt1, pizzas.get(piz[i]).getBase());
	//					}
	//					if (fieldNomPizza!=null) {
	//						fieldNomPizza.set(pt1, pizzas.get(piz[i]).getNom());
	//					}
	//					if (fieldPrixVente!=null) {
	//						fieldPrixVente.set(pt1, pizzas.get(piz[i]).getPrixVente());
	//					}
	//					if (fieldGarnitures!=null) {
	//						fieldGarnitures.set(pt1, pizzas.get(piz[i]).getGarnitures());
	//					}
	//					LinkedList<packnp.solution.Garniture> lenm = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enMAvant[i].length;l++) {
	//						lenm.add(garnitures.get(enMAvant[i][l]));
	//					}
	//					if (fieldEnMoins!=null) {
	//						fieldEnMoins.set(pt1, lenm);
	//					}
	//					LinkedList<packnp.solution.Garniture> lenp = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enPAvant[i].length;l++) {
	//						lenp.add(garnitures.get(enPAvant[i][l]));
	//					}
	//					if (fieldEnPlus!=null) {
	//						fieldEnPlus.set(pt1, lenp);
	//					}
	//	//				arg1[0]=garnitures.get(gar[i]);
	//
	//					Object res=	mPrixVente.invoke(pt1,  argVide);
	//double pri = (double)res;
	//System.out.print(pri+",");
	//					//					if (res==null) {
	////						System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////						for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et ayant pour liste enMoins : ");
	////						for (packnp.solution.Garniture gg:lenm) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et pour liste enPlus : ");
	////						for (packnp.solution.Garniture gg:lenp) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n la methode getGarnitures retourne null");
	////						noteComportement=0;
	////					} else {
	///*							List<packnp.solution.Garniture> resg = (List<packnp.solution.Garniture>)fieldGarnitures.get(pt1);
	//							List<packnp.solution.Garniture> resMP = (List<packnp.solution.Garniture>)fieldEnMoins.get(pt1);
	//							List<packnp.solution.Garniture> resPP = (List<packnp.solution.Garniture>)fieldEnPlus.get(pt1);
	//							LinkedList<packnp.solution.Garniture> oracleM = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enMApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleM.add(garnitures.get(enMApres[i][m]));
	//							}
	//							LinkedList<packnp.solution.Garniture> oracleP = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enPApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleP.add(garnitures.get(enPApres[i][m]));
	//							}//*/
	////System.out.print(pri+",");
	//						if (Math.abs(pri-oracle[i])>0.01 &&Math.abs(pri-oracle2[i])>0.01) {//!tool.egaux(resg,pizzas.get(piz[i]).getGarnitures())) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza au prix de vente de "+pizzas.get(piz[i]).getPrixVente()+" et ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							
	//							System.out.println("\n la methode getPrixRevient() retourne "+pri+" au lieu de "+oracle[i]);//la liste des Garnitures heritee est composee de :");
	//		System.out.println("Prix des ingredients : ");
	//		System.out.println(new Base(true).getNom()+"="+new Base(true).getPrixRevient());
	//		System.out.println(new Base(false).getNom()+"="+new Base(false).getPrixRevient());
	//		int hhh=0;
	//		for (packnp.solution.Garniture ggg : garnitures) {
	//			System.out.print(ggg.getNom()+"="+ggg.getPrixRevient()+",");
	//			hhh++; if (hhh==6) {System.out.println();hhh=0;}
	//		}
	//							/*				for (packnp.solution.Garniture gg:resg) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu d'une liste composee des Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();*/
	//							noteComportement=0;
	//						} 
	//				/*else if (!tool.egaux(resMP,oracleM)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode ajouter("+garnitures.get(gar[i]).getNom()+") la liste enMoins est composee de :");
	//							for (packnp.solution.Garniture gg:resMP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleM) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} else if (!tool.egaux(resPP,oracleP)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n apres la methode ajouter("+garnitures.get(gar[i]).getNom()+") la liste enPlus est composee de :");
	//							for (packnp.solution.Garniture gg:resPP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						}
	//						//*/ 
	////					}
	//
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	////			System.out.println("noteListes="+noteListes);
	////			System.out.println("noteExtends="+noteExtends);
	////			System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	////			System.out.println("noteConstructeurListes="+noteConstructeurListes);
	////		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//			if (noteDeclaration+noteComportement==100) {
	//				System.out.println("   Ok. Votre code passe ce test avec succes.");
	//			}
	//			return noteDeclaration+noteComportement;
	//
	//	}
	//
	//	
	//	
	//	
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test15PizzaPersonnaliseeIdentique() {
	//	//	int notePizza=15;
	//	//	int noteListes=15;
	//	//	int noteExtends=20;
	//	//	int noteRienDAutre=5;
	//	//	int noteConstructeurPizza=30;
	//	//	int noteConstructeurListes=30;
	//		int noteDeclaration=10;
	//		int noteComportement=90;
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode identique(Pizza) de la classe PizzaPersonnalisee : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//				}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//			}
	//			fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//			}
	//			fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//			}
	//			fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//			}
	//			fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			return 0;
	//		}
	//
	//		Method mIdentique=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizzaPersonnalisee.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("identique")) {//.getReturnType().equals(cPoint)) {
	//					mIdentique=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mIdentique==null) {
	//			if (mIdentique==null) {
	//			System.out.println("   Aie... Je ne trouve la methode identique(Pizza) dans PizzaPersonnalisee ");
	//			return 0;
	//			}
	//		}
	//		if (mIdentique.getParameters().length!=1 || !mIdentique.getParameters()[0].getType().equals(cPizza)) {
	//			System.out.println("   Aie... identique doit avoir un unique parametre de type Pizza");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(mIdentique.getModifiers())) {
	//			System.out.println("   Aie... votre methode identique doit etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		mIdentique.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		int[] piz = {
	//				2, 3, 4, 5, 6,	
	//				0, 0, 0, 0, 0,
	//	};
	//		int[] pizpar = {
	//			0, 0, 0, 0, 0,	
	//			2, 2, 2, 2, 2,
	//		};
	//		Object[][] paramC = new Object[piz.length][1];
	//		for (int i=0; i<piz.length; i++) {
	//			paramC[i][0]=pizzas.get(piz[i]);
	//		}
	//		int[][] enMAvant = {
	//				{}, {}, {}, {}, {},		
	//				{}, {11}, {}, {}, {},		
	//		};
	//		int[][] enPAvant = {
	//				{}, {}, {}, {}, {},
	//				{}, {}, {30}, {30,40}, {20},		
	//		};
	//		int[][] enMApres = {
	//				{11}, {}, {11}, {}, {11},	
	//				{30,40}, {30,40}, {40}, {}, {30,40},		
	//		};
	//		int[][] enPApres = {
	//				{30,40}, {40}, {23,40}, {23,40}, {13,27,32},
	//				{11}, {}, {11}, {11}, {11,20},		
	//		};
	////*///		int[][] oracle = {
	////				{0,27,13,24,37,32},{27,13,24,37,32},{0,13,24,37,32},{0,27,13,24,32},{0,27,13,24,37},
	////				{0,27,13,24,37,32},{0,1,27,13,24,37,32},{0,2,27,13,24,37,32},{0,27,13,24,37,38,32},{0,27,13,24,37,39,32},
	////				{0,27,13,24,37,32},{1,27,13,24,37,32},{0,2,13,24,37,32},{0,27,13,24,32,38},{0,27,13,24,37,39},
	////		};
	///*		double[] oracle = {
	//				2.99,2.99,2.99,2.99,2.99,2.99,2.4400000000000004,2.9000000000000004,1.99,2.5700000000000003,2.79,1.79,1.79,1.79,1.79,1.79,2.99,2.99,2.99,2.99,2.99,3.7600000000000002,3.95,3.64,3.18,3.2600000000000002,4.5600000000000005,4.5600000000000005,4.5600000000000005,4.5600000000000005,4.5600000000000005,3.08,2.9899999999999998,2.58,1.68,2.58,3.77,3.6799999999999997,3.27,2.37,3.27,  
	//		};//*/
	//		Object ptc1=null;
	////Object[] argVide= {};
	//Object[] arg1 = new Object[1];
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; noteComportement>0 && i<paramC.length ; i++) {
	//					Object pt1=null;
	//					Objenesis objenesis = new ObjenesisStd(); 
	//					ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cPizzaPersonnalisee);
	//					pt1 = instantiator.newInstance();
	//					if (fieldBase!=null) {
	//						fieldBase.set(pt1, pizzas.get(piz[i]).getBase());
	//					}
	//					if (fieldNomPizza!=null) {
	//						fieldNomPizza.set(pt1, pizzas.get(piz[i]).getNom());
	//					}
	//					if (fieldPrixVente!=null) {
	//						fieldPrixVente.set(pt1, pizzas.get(piz[i]).getPrixVente());
	//					}
	//					if (fieldGarnitures!=null) {
	//						fieldGarnitures.set(pt1, pizzas.get(piz[i]).getGarnitures());
	//					}
	//					LinkedList<packnp.solution.Garniture> lenm = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enMAvant[i].length;l++) {
	//						lenm.add(garnitures.get(enMAvant[i][l]));
	//					}
	//					if (fieldEnMoins!=null) {
	//						fieldEnMoins.set(pt1, lenm);
	//					}
	//					LinkedList<packnp.solution.Garniture> lenp = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enPAvant[i].length;l++) {
	//						lenp.add(garnitures.get(enPAvant[i][l]));
	//					}
	//					if (fieldEnPlus!=null) {
	//						fieldEnPlus.set(pt1, lenp);
	//					}
	//					arg1[0]=pizzas.get(pizpar[i]);
	//
	//					Object res=	mIdentique.invoke(pt1,  arg1);
	//					
	////double pri = (double)res;
	//					if (res==null) {
	//						System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//						for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//							System.out.print(gg.getNom()+", ");
	//						}
	//						System.out.println("\n et ayant pour liste enMoins : ");
	//						for (packnp.solution.Garniture gg:lenm) {
	//							System.out.print(gg.getNom()+", ");
	//						}
	//						System.out.println("\n et pour liste enPlus : ");
	//						for (packnp.solution.Garniture gg:lenp) {
	//							System.out.print(gg.getNom()+", ");
	//						}
	//						System.out.println("\n la methode identique(p) retourne null");
	//						System.out.println(" avec p comportant les garnitures : ");
	//						for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	//							System.out.print(gg.getNom()+", ");
	//						}
	//						noteComportement=0;
	//					} else {
	//							List<packnp.solution.Garniture> resg = (List<packnp.solution.Garniture>)fieldGarnitures.get(res);
	//							List<packnp.solution.Garniture> resMP = (List<packnp.solution.Garniture>)fieldEnMoins.get(res);
	//							List<packnp.solution.Garniture> resPP = (List<packnp.solution.Garniture>)fieldEnPlus.get(res);
	//							LinkedList<packnp.solution.Garniture> oracleM = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enMApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleM.add(garnitures.get(enMApres[i][m]));
	//							}
	//							LinkedList<packnp.solution.Garniture> oracleP = new LinkedList<packnp.solution.Garniture>();
	//							for (int m=0;  m<enPApres[i].length; m++) {
	//							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	//								oracleP.add(garnitures.get(enPApres[i][m]));
	//							}//*/
	////System.out.print(pri+",");
	//						if (!tool.egaux(resg,pizzas.get(pizpar[i]).getGarnitures())) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							
	//							System.out.println("\n la methode identique(p) avec p ayant pour garnitures :");
	//							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("retourne une pizza ayant les garnitures :");//la liste des Garnitures heritee est composee de :");
	//			 			for (packnp.solution.Garniture gg:resg) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu d'une liste composee des Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} 
	//				else if (!tool.egaux(resMP,oracleM)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n la methode identique(p) avec p ayant pour garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("la pizza retourne a une liste enMoins composee de :");
	//							for (packnp.solution.Garniture gg:resMP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleM) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						} else if (!tool.egaux(resPP,oracleP)) {
	//							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	//							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							lenm = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enMAvant[i].length;l++) {
	//								lenm.add(garnitures.get(enMAvant[i][l]));
	//							}
	//							lenp = new LinkedList<packnp.solution.Garniture>();
	//							for (int l=0; l<enPAvant[i].length;l++) {
	//								lenp.add(garnitures.get(enPAvant[i][l]));
	//							}
	//							System.out.println("\n et ayant pour liste enMoins : ");
	//							for (packnp.solution.Garniture gg:lenm) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n et pour liste enPlus : ");
	//							for (packnp.solution.Garniture gg:lenp) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n la methode identique(p) avec p composee des garnitures :");
	//							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("la pizza retournee a une liste enPlus  composee de :");
	//							for (packnp.solution.Garniture gg:resPP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println("\n au lieu des Garnitures : ");
	//							for (packnp.solution.Garniture gg:oracleP) {
	//								System.out.print(gg.getNom()+", ");
	//							}
	//							System.out.println();
	//							noteComportement=0;
	//						}
	//						 
	//					}
	//
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	////			System.out.println("noteListes="+noteListes);
	////			System.out.println("noteExtends="+noteExtends);
	////			System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	////			System.out.println("noteConstructeurListes="+noteConstructeurListes);
	////		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//			if (noteDeclaration+noteComportement==100) {
	//				System.out.println("   Ok. Votre code passe ce test avec succes.");
	//			}
	//			return noteDeclaration+noteComportement;
	//
	//	}
	//
	//
	//
	//
	//	@SuppressWarnings({ "rawtypes", "unused" })
	//	public static int test16PizzaPersonnaliseeJustePrix() {
	//	//	int notePizza=15;
	//	//	int noteListes=15;
	//	//	int noteExtends=20;
	//	//	int noteRienDAutre=5;
	//	//	int noteConstructeurPizza=30;
	//	//	int noteConstructeurListes=30;
	//		int noteDeclaration=10;
	//		int noteComportement=90;
	//		System.out.println("   Test verifiant le bon fonctionnement de la methode justePrix(LinkedList<Pizza>) de la classe PizzaPersonnalisee : ");
	//		Class cPizzaPersonnalisee = Reflexion.getClass("packnp.pizzas.PizzaPersonnalisee");
	//		if (cPizzaPersonnalisee==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe PizzaPersonnalisee dans le package packnp.pizzas");
	//			return 0;
	//		}
	//		Field fieldEnMoins=null, fieldEnPlus=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	//
	//		Field[] tf = cPizzaPersonnalisee.getDeclaredFields();
	//		for (Field f : tf) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getName().equals("enMoins")) {
	//				if (fieldEnMoins==null) {
	//					fieldEnMoins=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().equals("enPlus")) {//).getType().equals(double.class)) {
	//				if (fieldEnPlus==null) {
	//					fieldEnPlus=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//				}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe PizzaPersonnalisee");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee ne doit comporter que deux variables d'instance, l'une memorisant les garnitures en moins, et l'autre les garnitures en plus.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//			return 0;
	//		}
	//		if (fieldEnMoins==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enMoins.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnMoins.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnMoins.getName());
	//			}
	//			if (!fieldEnMoins.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enMoins doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		if (fieldEnPlus==null) {
	//			System.out.println("   Aie... La classe PizzaPersonnalisee doit comporter une variable d'instance nommee enPlus.");
	//			return 0;
	//		} else {
	//			if (!Modifier.isPrivate(fieldEnPlus.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEnPlus.getName());
	//			}
	////			System.out.println(">>>"+fieldEnMoins.getType().getName()+"<<<");
	//			if (!fieldEnPlus.getType().getName().contains("List")) {
	//				System.out.println("   Aie... La variable enPlus doit etre une liste.");
	//				return 0;
	//			}
	//		}
	//		fieldEnMoins.setAccessible(true);
	//		fieldEnPlus.setAccessible(true);
	//		Class cPizza = Reflexion.getClass("packnp.pizzas.Pizza");
	//		if (cPizza==null) {
	//			System.out.println("   Aie... je ne trouve pas la classe Pizza dans le package packnp.pizzas");
	//			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	//			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	//			return 0;
	//		}
	//		Field fieldPrixVente=null, fieldNomPizza=null, fieldBase=null, fieldGarnitures=null;
	//		fieldConst=null;
	//		fieldStatic=null;
	//		fieldOther=null;
	//
	//		Field[] tfp = cPizza.getDeclaredFields();
	//		for (Field f : tfp) {
	//			if (Modifier.isFinal(f.getModifiers())) {
	//				fieldConst=f;
	//			}
	//			if (Modifier.isStatic(f.getModifiers())) {
	//				fieldStatic=f;
	//			} 
	//			if ( f.getType().equals(String.class)) {
	//				if (fieldNomPizza==null) {
	//					fieldNomPizza=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getType().equals(double.class)) {
	//				if (fieldPrixVente==null) {
	//					fieldPrixVente=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("base")) {
	//				if (fieldBase==null) {
	//					fieldBase=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else if ( f.getName().contentEquals("garnitures")) {
	//				if (fieldGarnitures==null) {
	//					fieldGarnitures=f;
	//				} else {
	//					fieldOther=f;
	//				}
	//			} else{
	//				fieldOther=f;
	//			}
	//		}
	//		if (fieldConst!=null) {
	//			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	//		}
	//		if (fieldStatic!=null) {
	//			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Pizza");
	//			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	//		}
	//		if (fieldOther!=null) {
	//			System.out.println("   Aie... La classe Pizza ne doit comporter que les quatres variables d'instance indiquees sur le diagramme de classes.");
	//			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	//		}
	//		if (fieldPrixVente==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type double memorisant le prix de vente de la Pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldPrixVente.getName());
	//			}
	//			if (!fieldPrixVente.getName().equals("prixVente")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le prix de vente de lz pizza se nommme \""+fieldPrixVente.getName()+"\" au lieu de \"prixVente\"");
	//			}
	//		}
	//		if (fieldNomPizza==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de la pizza.");
	//		} else {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	//				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomPizza.getName());
	//			}
	//			if (!fieldNomPizza.getName().equals("nom")) {
	//				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	//				System.out.println("          Votre variable memorisant le nom de la pizza se nommme \""+fieldNomPizza.getName()+"\" au lieu de \"nom\"");
	//			}
	//		}
	//		if (fieldBase==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee base.");
	//		} 
	//		if (fieldGarnitures==null) {
	//			System.out.println("   Aie... La classe Pizza doit comporter une variable d'instance nommee garnitures.");
	//		} 
	//		
	//		if (fieldPrixVente!=null) {
	//			if (!Modifier.isPrivate(fieldPrixVente.getModifiers())) {
	//				System.out.println("   Aie... la variable prixVente de Pizza doit etre private");
	//			}
	//			fieldPrixVente.setAccessible(true);
	//		}
	//		if (fieldNomPizza!=null) {
	//			if (!Modifier.isPrivate(fieldNomPizza.getModifiers())) {
	//				System.out.println("   Aie... la variable nom de Pizza doit etre private");
	//			}
	//			fieldNomPizza.setAccessible(true);
	//		}
	//		if (fieldBase!=null){
	//			if (!Modifier.isPrivate(fieldBase.getModifiers())) {
	//				System.out.println("   Aie... la variable base de Pizza doit etre private");
	//			}
	//			fieldBase.setAccessible(true);
	//		}
	//		if (fieldGarnitures!=null) {
	//			if (!Modifier.isPrivate(fieldGarnitures.getModifiers())) {
	//				System.out.println("   Aie... la variable garnitures de Pizza doit etre private");
	//			}
	//			fieldGarnitures.setAccessible(true);
	//		}
	//
	//		if (!Reflexion.extendsClass(cPizzaPersonnalisee, cPizza)) {
	//			System.out.println("   Aie... Votre classe PizzaPersonnalisee n'herite pas de la classe Pizza");
	//			return 0;
	//		}
	//
	//		Method mJustePrix=null, mAutre=null;
	//		try {
	//			Method[] methods = cPizzaPersonnalisee.getDeclaredMethods();
	//			for (Method m : methods) {
	//				if (m.getName().equals("justePrix")) {//.getReturnType().equals(cPoint)) {
	//					mJustePrix=m;
	//				} else{ 
	//					mAutre=m;
	//				}
	//			}
	//		} catch (Exception e1) {
	//		} 
	//		if (mJustePrix==null) {
	//			if (mJustePrix==null) {
	//			System.out.println("   Aie... Je ne trouve la methode justePrix(LinkedList<Pizza>) dans PizzaPersonnalisee ");
	//			return 0;
	//			}
	//		}
	//		if (mJustePrix.getParameters().length!=1 || !mJustePrix.getParameters()[0].getType().equals(LinkedList.class)) {
	//			System.out.println("   Aie... justePrix doit avoir un unique parametre de type LinkedList<Pizza>");
	//			return 0;
	//		}
	//		if (!Modifier.isPublic(mJustePrix.getModifiers())) {
	//			System.out.println("   Aie... votre methode justePrix doit etre public");
	//			noteDeclaration=noteDeclaration/2;
	//		}
	//		mJustePrix.setAccessible(true);
	//
	//		List<packnp.solution.Garniture> garnitures=new LinkedList<packnp.solution.Garniture>();
	//		for (int i=0; i<nomsGarnitures.length; i++) {
	//			garnitures.add(new packnp.solution.Garniture(nomsGarnitures[i], prixGarnitures[i]));
	//		}
	//		
	//		List<Pizza> pizzas = new LinkedList<Pizza>();
	//		for (int i=0; i<nomsPizzas.length; i++) {
	//			LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//			for (int j=0; j<garnituresPizzas[i].length; j++) {
	//				garnituresPizza.add(garnitures.get(garnituresPizzas[i][j]));
	//			}
	//			pizzas.add(new Pizza(nomsPizzas[i], new Base( basesPizzas[i]), garnituresPizza, prixPizzas[i]));
	//		}
	//	
	//
	//		int[] piz = {
	//				2, 3, 4, 5, 6,	
	//				0, 0, 0, 0, 0,
	//				7,8,9,10,11,
	//				12,13,14,15,16,
	//				17,18,19,20,21,
	//				22,23,24,25,26,
	//	};
	///*		int[] pizpar = {
	//			0, 0, 0, 0, 0,	
	//			2, 2, 2, 2, 2,
	//		};*/
	//		Object[][] paramC = new Object[piz.length][1];
	//		for (int i=0; i<piz.length; i++) {
	//			paramC[i][0]=pizzas.get(piz[i]);
	//		}
	//		int[][] enMAvant = {
	//				{}, {}, {}, {}, {},		
	//				{}, {11}, {}, {}, {},		
	//				{}, {}, {}, {}, {},		
	//				{}, {}, {}, {}, {},		
	//				{}, {}, {}, {}, {},		
	//				{}, {}, {}, {}, {},		
	//		};
	//		int[][] enPAvant = {
	//				{}, {}, {}, {}, {},
	//				{}, {}, {30}, {30,40}, {20},		
	//				{}, {}, {}, {}, {},		
	//				{}, {}, {}, {}, {},		
	//				{}, {}, {}, {}, {},		
	//				{}, {}, {}, {}, {},		
	//		};
	///*		int[][] enMApres = {
	//				{11}, {}, {11}, {}, {11},	
	//				{30,40}, {30,40}, {40}, {}, {30,40},		
	//		};
	//		int[][] enPApres = {
	//				{30,40}, {40}, {23,40}, {23,40}, {13,27,32},
	//				{11}, {}, {11}, {11}, {11,20},		
	//		};*/
	////*///		int[][] oracle = {
	////				{0,27,13,24,37,32},{27,13,24,37,32},{0,13,24,37,32},{0,27,13,24,32},{0,27,13,24,37},
	////				{0,27,13,24,37,32},{0,1,27,13,24,37,32},{0,2,27,13,24,37,32},{0,27,13,24,37,38,32},{0,27,13,24,37,39,32},
	////				{0,27,13,24,37,32},{1,27,13,24,37,32},{0,2,13,24,37,32},{0,27,13,24,32,38},{0,27,13,24,37,39},
	////		};
	//		double[] oracle = {
	//				6.2,6.2,6.2,7.2,7.2,5.2,5.2,6.2,7.2,6.2,8.2,9.0,8.2,8.2,8.2,8.2,8.2,8.2,8.2,6.2,8.2,7.2,7.2,7.2,7.2,7.2,9.0,8.2,7.2,6.2,  
	//		};//*/
	//		Object ptc1=null;
	////Object[] argVide= {};
	//Object[] arg1 = new Object[1];
	//	//	if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
	//			try {
	//				for (int i=0; noteComportement>0 && i<paramC.length ; i++) {
	//					garnitures=new LinkedList<packnp.solution.Garniture>();
	//					for (int iii=0; iii<nomsGarnitures.length; iii++) {
	//						garnitures.add(new packnp.solution.Garniture(nomsGarnitures[iii], prixGarnitures[iii]));
	//					}
	//					
	//				    pizzas = new LinkedList<Pizza>();
	//					for (int iii=0; iii<nomsPizzas.length; iii++) {
	//						LinkedList<packnp.solution.Garniture> garnituresPizza = new LinkedList<packnp.solution.Garniture>();
	//						for (int j=0; j<garnituresPizzas[iii].length; j++) {
	//							garnituresPizza.add(garnitures.get(garnituresPizzas[iii][j]));
	//						}
	//						pizzas.add(new Pizza(nomsPizzas[iii], new Base( basesPizzas[iii]), garnituresPizza, prixPizzas[iii]));
	//					}
	//
	//					Object pt1=null;
	//					Objenesis objenesis = new ObjenesisStd(); 
	//					ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cPizzaPersonnalisee);
	//					pt1 = instantiator.newInstance();
	//					if (fieldBase!=null) {
	//						fieldBase.set(pt1, pizzas.get(piz[i]).getBase());
	//					}
	//					if (fieldNomPizza!=null) {
	//						fieldNomPizza.set(pt1, pizzas.get(piz[i]).getNom());
	//					}
	//					if (fieldPrixVente!=null) {
	//						fieldPrixVente.set(pt1, pizzas.get(piz[i]).getPrixVente());
	//					}
	//					if (fieldGarnitures!=null) {
	//						fieldGarnitures.set(pt1, pizzas.get(piz[i]).getGarnitures());
	//					}
	//					LinkedList<packnp.solution.Garniture> lenm = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enMAvant[i].length;l++) {
	//						lenm.add(garnitures.get(enMAvant[i][l]));
	//					}
	//					if (fieldEnMoins!=null) {
	//						fieldEnMoins.set(pt1, lenm);
	//					}
	//					LinkedList<packnp.solution.Garniture> lenp = new LinkedList<packnp.solution.Garniture>();
	//					for (int l=0; l<enPAvant[i].length;l++) {
	//						lenp.add(garnitures.get(enPAvant[i][l]));
	//					}
	//					if (fieldEnPlus!=null) {
	//						fieldEnPlus.set(pt1, lenp);
	//					}
	//					arg1[0]=pizzas;//pizzas.get(pizpar[i]);
	//
	//					Object res=	mJustePrix.invoke(pt1,  arg1);
	//					
	//double pri = (double)res;
	////System.out.print(pri+",");
	//if (pri!=oracle[i]) {
	//	System.out.println("   Aie... votre methode retourne "+pri+" au llieu du juste prix a la vue de la carte ("+oracle[i]+")");
	//	noteComportement=0;
	//}
	////					if (res==null) {
	////						System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////						for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et ayant pour liste enMoins : ");
	////						for (packnp.solution.Garniture gg:lenm) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n et pour liste enPlus : ");
	////						for (packnp.solution.Garniture gg:lenp) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						System.out.println("\n la methode identique(p) retourne null");
	////						System.out.println(" avec p comportant les garnitures : ");
	////						for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	////							System.out.print(gg.getNom()+", ");
	////						}
	////						noteComportement=0;
	////					} else {
	////							List<packnp.solution.Garniture> resg = (List<packnp.solution.Garniture>)fieldGarnitures.get(res);
	////							List<packnp.solution.Garniture> resMP = (List<packnp.solution.Garniture>)fieldEnMoins.get(res);
	////							List<packnp.solution.Garniture> resPP = (List<packnp.solution.Garniture>)fieldEnPlus.get(res);
	////							LinkedList<packnp.solution.Garniture> oracleM = new LinkedList<packnp.solution.Garniture>();
	////							for (int m=0;  m<enMApres[i].length; m++) {
	////							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	////								oracleM.add(garnitures.get(enMApres[i][m]));
	////							}
	////							LinkedList<packnp.solution.Garniture> oracleP = new LinkedList<packnp.solution.Garniture>();
	////							for (int m=0;  m<enPApres[i].length; m++) {
	////							//	System.out.println("oracle["+i+"]["+m+"]="+oracle[i][m]+" size="+garnitures.size());
	////								oracleP.add(garnitures.get(enPApres[i][m]));
	////							}//*/
	//////System.out.print(pri+",");
	////						if (!tool.egaux(resg,pizzas.get(pizpar[i]).getGarnitures())) {
	////							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							lenm = new LinkedList<packnp.solution.Garniture>();
	////							for (int l=0; l<enMAvant[i].length;l++) {
	////								lenm.add(garnitures.get(enMAvant[i][l]));
	////							}
	////							lenp = new LinkedList<packnp.solution.Garniture>();
	////							for (int l=0; l<enPAvant[i].length;l++) {
	////								lenp.add(garnitures.get(enPAvant[i][l]));
	////							}
	////							System.out.println("\n et ayant pour liste enMoins : ");
	////							for (packnp.solution.Garniture gg:lenm) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n et pour liste enPlus : ");
	////							for (packnp.solution.Garniture gg:lenp) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							
	////							System.out.println("\n la methode identique(p) avec p ayant pour garnitures :");
	////							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("retourne une pizza ayant les garnitures :");//la liste des Garnitures heritee est composee de :");
	////			 			for (packnp.solution.Garniture gg:resg) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n au lieu d'une liste composee des Garnitures : ");
	////							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println();
	////							noteComportement=0;
	////						} 
	////				else if (!tool.egaux(resMP,oracleM)) {
	////							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							lenm = new LinkedList<packnp.solution.Garniture>();
	////							for (int l=0; l<enMAvant[i].length;l++) {
	////								lenm.add(garnitures.get(enMAvant[i][l]));
	////							}
	////							lenp = new LinkedList<packnp.solution.Garniture>();
	////							for (int l=0; l<enPAvant[i].length;l++) {
	////								lenp.add(garnitures.get(enPAvant[i][l]));
	////							}
	////							System.out.println("\n et ayant pour liste enMoins : ");
	////							for (packnp.solution.Garniture gg:lenm) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n et pour liste enPlus : ");
	////							for (packnp.solution.Garniture gg:lenp) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n la methode identique(p) avec p ayant pour garnitures : ");
	////							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("la pizza retourne a une liste enMoins composee de :");
	////							for (packnp.solution.Garniture gg:resMP) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n au lieu des Garnitures : ");
	////							for (packnp.solution.Garniture gg:oracleM) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println();
	////							noteComportement=0;
	////						} else if (!tool.egaux(resPP,oracleP)) {
	////							System.out.println("   Aie... sur une PizzaPersonnalisee specialisant une Pizza ayant pour Garnitures : ");
	////							for (packnp.solution.Garniture gg:pizzas.get(piz[i]).getGarnitures()) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							lenm = new LinkedList<packnp.solution.Garniture>();
	////							for (int l=0; l<enMAvant[i].length;l++) {
	////								lenm.add(garnitures.get(enMAvant[i][l]));
	////							}
	////							lenp = new LinkedList<packnp.solution.Garniture>();
	////							for (int l=0; l<enPAvant[i].length;l++) {
	////								lenp.add(garnitures.get(enPAvant[i][l]));
	////							}
	////							System.out.println("\n et ayant pour liste enMoins : ");
	////							for (packnp.solution.Garniture gg:lenm) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n et pour liste enPlus : ");
	////							for (packnp.solution.Garniture gg:lenp) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n la methode identique(p) avec p composee des garnitures :");
	////							for (packnp.solution.Garniture gg:pizzas.get(pizpar[i]).getGarnitures()) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("la pizza retournee a une liste enPlus  composee de :");
	////							for (packnp.solution.Garniture gg:resPP) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println("\n au lieu des Garnitures : ");
	////							for (packnp.solution.Garniture gg:oracleP) {
	////								System.out.print(gg.getNom()+", ");
	////							}
	////							System.out.println();
	////							noteComportement=0;
	////						}
	////						 
	////					}
	//
	//				}
	//			} catch (Exception e) {
	//				System.out.println("   Exception levee ");
	//				if (e instanceof InvocationTargetException) {
	//					e.getCause().printStackTrace();
	//				} else {
	//					e.printStackTrace();
	//				}
	//				return 0;
	//			}
	//
	//		
	////			System.out.println("noteListes="+noteListes);
	////			System.out.println("noteExtends="+noteExtends);
	////			System.out.println("noteConstructeurPizza="+noteConstructeurPizza);
	////			System.out.println("noteConstructeurListes="+noteConstructeurListes);
	////		if (noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteListes+noteExtends+noteConstructeurListes+noteConstructeurPizza+noteRienDAutre;
	//			if (noteDeclaration+noteComportement==100) {
	//				System.out.println("   Ok. Votre code passe ce test avec succes.");
	//			}
	//			return noteDeclaration+noteComportement;
	//
	//	}
	//
	//	
	//	
	//	//
	//
	//	
	//	//	@SuppressWarnings({ "rawtypes", "unused" })
	////	public static int test05CageDeclarationVariables() {
	////		int noteLesAnimaux=70;
	////		int noteNom=10;
	////		int noteRienDAutre=20;
	////		System.out.println("   Test verifiant la declaration des variables d'instances de la classe Cage : ");
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////			noteRienDAutre=0;
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////			noteRienDAutre=0;
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////			noteRienDAutre=0;
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////			noteLesAnimaux=0;
	////			noteNom=0;
	////			noteRienDAutre=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////				noteLesAnimaux=noteLesAnimaux/2;
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////				if (fieldLesAnimaux.getType().getName().equals("java.util.ArrayList")) {
	////					noteLesAnimaux=noteLesAnimaux-20;
	////				} else {
	////					noteLesAnimaux = 10;
	////				}
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////				noteNom=0;
	////			}
	////		}
	////		if (noteLesAnimaux==0) {
	////			noteRienDAutre=0;
	////		}
	////		if (noteNom+noteLesAnimaux+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteNom+noteLesAnimaux+noteRienDAutre;
	////	}
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test06CageConstructeurSansParam() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement du constructeur sans parametre de Cage");
	////		int noteDeclaration=0;
	////		int noteInitAnimaux=100;
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////		Class[] ar = {};
	////		Class[] arStringString = {String.class, String.class};
	////		Constructor constC1=null;
	////		try {
	////			constC1 = cCage.getDeclaredConstructor(ar);
	////		} catch (NoSuchMethodException e) {
	////			constC1=null;
	////		} catch (SecurityException e) {
	////			constC1=null;
	////		}
	////		if (constC1==null) {
	////			System.out.println("   Aie... je ne trouve pas le constructeur sans parametre de Cage");
	////			return 0;
	////		}
	////
	////		if (!Modifier.isPublic(constC1.getModifiers())) {
	////			System.out.println("   Aie... Le constructeur de Cage devrait etre public");
	////			noteDeclaration=noteDeclaration/2;
	////		}
	////		constC1.setAccessible(true);
	////		Object[] paramVide ={
	////		};
	////		Object ptc1=null;
	////
	////		try {
	////			ptc1 = (constC1.newInstance(paramVide));
	////			if (fieldLesAnimaux==null) {
	////				noteInitAnimaux=0;
	////			} else if (fieldLesAnimaux.get(ptc1)==null) {
	////				System.out.println("  Aie... votre constructeur initialise la variable "+fieldLesAnimaux.getName()+" a null au lieu de l'initialiser avec une liste vide d'animaux \n");
	////				noteInitAnimaux=0;
	////			} else {
	////				List l = (List)fieldLesAnimaux.get(ptc1);
	////				if (l.size()!=0) {
	////					System.out.println("   Aie... Vous initialisez la variable "+fieldLesAnimaux.getName()+" avec une liste de taille "+l.size()+" au lieu d'une liste vide");
	////					noteInitAnimaux=0;
	////				}
	////				if (noteInitAnimaux>0 && !(fieldLesAnimaux.get(ptc1) instanceof LinkedList)) {
	////					System.out.println("   Aie... Vous initialisez la variable "+fieldLesAnimaux.getName()+" avec une instance de "+(fieldLesAnimaux.get(ptc1)).getClass().getName()+" au lieu d'une LinkedList");
	////					noteInitAnimaux=noteInitAnimaux/2;
	////				}
	////			}
	////		} catch (Exception e) {
	////			System.out.println("   Exception levee lors de la creation d'une Cage avec le constructeur Cage()");
	////			if (e instanceof InvocationTargetException) {
	////				e.getCause().printStackTrace();
	////			} else {
	////				e.printStackTrace();
	////			}
	////			return 0;
	////		}
	////
	////		if (noteDeclaration+noteInitAnimaux==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteInitAnimaux);
	////	}
	////
	////
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test07CagePeutContenir() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de peutContenir de Cage");
	////		int noteDeclaration=15;
	////		int noteComportement=85;
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		class A extends Animal{
	////			String w;String m;
	////			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
	////			public String getEspece() {return this.w;}
	////			public String getNom() {return this.m;}
	////			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
	////		};
	////
	////
	////		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			//System.out.println(">>>"+f.getType().getName()+"<<<");
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////
	////		Method mPeutContenir=null;
	////		try {
	////			Method[] methods = cCage.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("peutContenir")) {//.getReturnType().equals(cPoint)) {
	////					mPeutContenir=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mPeutContenir==null) {
	////			noteComportement=0;
	////			noteDeclaration=0;
	////			System.out.println("   Aie... Je ne trouve pas la methode peutContenir de Cage");
	////		} else {
	////			if (mPeutContenir.getParameterCount()!=1) {
	////
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode peutContenir de Cage doit prendre un unique parametre");
	////			} else if (!(mPeutContenir.getParameters()[0].getType().equals(cAnimal))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode peutContenir de Cage doit prendre un unique parametre de type Animal");
	////			} 
	////			if (!mPeutContenir.getReturnType().equals(boolean.class)) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode peutContenir de Cage doit retourner un booleen");
	////			}
	////		}
	////		if (mPeutContenir!=null) mPeutContenir.setAccessible(true);
	////
	////		String[][][] contenuCage ={
	////				{},
	////				{{"cheeta", "singe"},},
	////				{{"cheeta", "singe"},{"zaius", "singe"},},
	////				{{"mickey", "souris"},},
	////				{{"donald", "canard"},},
	////				{{"daffy", "canard"},{"leon", "canard"},},
	////				{{"zira", "singe"},{"zaius", "singe"},},
	////				{{"zaius", "singe"}}
	////		};
	////		String[][] paramOK = {
	////				{"cheeta", "singe"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"donald", "canard"},
	////				{"donald", "canard"},
	////				{"zira", "singe"},
	////		};
	////		boolean[] res = {
	////				true, true, true, false, false, true, false, true
	////		};
	////
	////		Object[] argssn = new Object[1];
	////		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
	////			Object cage=null;
	////			Objenesis objenesis = new ObjenesisStd(); 
	////			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cCage);
	////			//B cage = new B();
	////			LinkedList<Animal> l = new LinkedList<Animal>();
	////			for (int j=0; j<contenuCage[i].length; j++) {
	////				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
	////			}
	////			cage =(Cage) instantiator.newInstance();
	////			//pt1 = instantiator.newInstance();
	////			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
	////			boolean resPeut=false;
	////			try {
	////				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
	////				if (noteComportement>0) {
	////					if ( mPeutContenir!=null) {
	////						resPeut=(boolean)(mPeutContenir.invoke(cage,  argssn));
	////					}
	////					if (resPeut!=res[i]) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						System.out.println("   Aie... cag.peutContenir(ani) retourne "+resPeut+" sur une cage contenant les animaux {"+cagString+"} avec ani=("+paramOK[i][0]+", "+paramOK[i][1]+")");
	////						noteComportement=0;
	////					}
	////				}
	////				} catch (Exception e) {
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				noteComportement=0;
	////			}
	////		}
	////
	////		if (noteDeclaration+noteComportement==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteComportement);
	////	}
	////
	////
	////
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test08CageAjouterAnimal() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de ajouterAnimal de Cage");
	////		int noteDeclaration=15;
	////		int noteComportement=85;
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		class A extends Animal{
	////			String w;String m;
	////			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
	////			public String getEspece() {return this.w;}
	////			public String getNom() {return this.m;}
	////			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
	////		};
	////
	////	class B extends Cage {
	////			private List<Animal> l;
	////			public B(){l = new LinkedList<Animal>();}
	////			public void set(List<Animal>l) {this.l=l;}
	////			public boolean peutContenir(Animal a){//System.out.println("peutcontenir size="+l.size()+" ->"+(l.size()%2==0));
	////				return l.size()%2==0;}			
	////		};
	////
	////		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			//System.out.println(">>>"+f.getType().getName()+"<<<");
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////
	////		Method mAjouterAnimal=null;
	////		try {
	////			Method[] methods = cCage.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("ajouterAnimal")) {//.getReturnType().equals(cPoint)) {
	////					mAjouterAnimal=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mAjouterAnimal==null) {
	////			noteComportement=0;
	////			noteDeclaration=0;
	////			System.out.println("   Aie... Je ne trouve pas la methode ajouterAnimal de Cage");
	////		} else {
	////			if (mAjouterAnimal.getParameterCount()!=1) {
	////
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode ajouterAnimal de Cage doit prendre un unique parametre");
	////			} else if (!(mAjouterAnimal.getParameters()[0].getType().equals(cAnimal))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode ajouterAnimal de Cage doit prendre un unique parametre de type Animal");
	////			} 
	////		}
	////		if (mAjouterAnimal!=null) mAjouterAnimal.setAccessible(true);
	////
	////		String[][][] contenuCage ={
	////				{},
	////				{{"cheeta", "singe"},},
	////				{{"cheeta", "singe"},{"zaius", "singe"},},
	////				{{"mickey", "souris"},},
	////				{{"donald", "canard"},},
	////				{{"daffy", "canard"},{"leon", "canard"},},
	////				{{"zira", "singe"},{"zaius", "singe"},},
	////				{{"zaius", "singe"}}
	////		};
	////		String[][] paramOK = {
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"donald", "canard"},
	////				{"cheeta", "singe"},
	////				{"daffy", "canard"},
	////		};
	////
	////		Object[] argssn = new Object[1];
	////		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
	////			B cage = new B();
	////			LinkedList<Animal> l = new LinkedList<Animal>();
	////			for (int j=0; j<contenuCage[i].length; j++) {
	////				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
	////			}
	////			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
	////			boolean resPeut=false;
	////			try {
	////				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
	////				cage.set(l);
	////				int sizeBefore = l.size();
	////				if (noteComportement>0) {
	////					if ( mAjouterAnimal!=null) {
	////						mAjouterAnimal.invoke(cage,  argssn);
	////					}
	////					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
	////					if (after==null) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste de cag vaut null");
	////						noteComportement=0;
	////					} else {
	////					if (sizeBefore%2==0 && after.size()!=sizeBefore+1) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la taille de la liste de cag est "+after.size()+" alors qu'il est possible d'ajouter l'animal");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==0 && !after.contains(argssn[0])) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il est possible d'ajouter l'animal");
	////						System.out.println("   Attention : ajouter l'animal (pas une copie de l'animal)");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==1 && sizeBefore!=after.size()) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il n'est pas possible d'ajouter l'animal dans cette cage");
	////						noteComportement=0;
	////					}
	////						
	////					}
	////				}
	////				} catch (Exception e) {
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				noteComportement=0;
	////			}
	////		}
	////
	////		if (noteDeclaration+noteComportement==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteComportement);
	////	}
	////
	////
	////
	////
	////
	////
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test09CageRetirerAnimal() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de retirerAnimal de Cage");
	////		int noteDeclaration=15;
	////		int noteComportement=85;
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		class A extends Animal{
	////			String w;String m;
	////			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
	////			public String getEspece() {return this.w;}
	////			public String getNom() {return this.m;}
	////			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
	////		};
	////
	////
	////		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			//System.out.println(">>>"+f.getType().getName()+"<<<");
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////
	////		Method mRetirerAnimal=null;
	////		try {
	////			Method[] methods = cCage.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("retirerAnimal")) {//.getReturnType().equals(cPoint)) {
	////					mRetirerAnimal=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mRetirerAnimal==null) {
	////			noteComportement=0;
	////			noteDeclaration=0;
	////			System.out.println("   Aie... Je ne trouve pas la methode retirerAnimal de Cage");
	////		} else {
	////			if (mRetirerAnimal.getParameterCount()!=1) {
	////
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode retirerAnimal de Cage doit prendre un unique parametre");
	////			} else if (!(mRetirerAnimal.getParameters()[0].getType().equals(cAnimal))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode retirerAnimal de Cage doit prendre un unique parametre de type Animal");
	////			} 
	////		}
	////		if (mRetirerAnimal!=null) mRetirerAnimal.setAccessible(true);
	////
	////		A cheeta = new A("cheeta", "singe");
	////		A zaius  = new A("zaius", "singe");
	////		A zira   = new A("zira", "singe");
	////		A mickey = new A("mickey", "souris");
	////		A donald = new A("donald", "canard");
	////		A daffy  = new A("daffy", "canard");
	////		A leon   = new A("leon", "canard");
	////		
	////		
	////		A[][] contenuCage ={
	////				{cheeta},
	////				{cheeta, zaius},
	////				{cheeta, zaius},
	////				{cheeta, zaius, zira},
	////				{cheeta, zaius, zira},
	////				{cheeta, zaius, zira},
	////				{donald},
	////				{donald, daffy},
	////				{donald, daffy},
	////				{donald, daffy, leon},
	////				{donald, daffy, leon},
	////				{donald, daffy, leon},
	////		};
	////		A[] paramOK = {
	////				cheeta,
	////				cheeta,
	////				zaius,
	////				cheeta,
	////				zaius,
	////				zira,
	////				donald, 
	////				donald,
	////				daffy,
	////				donald,
	////				daffy,
	////				leon
	////		};
	////		A[][] contenuCageAfter ={
	////				{},
	////				{zaius},
	////				{cheeta},
	////				{zaius, zira},
	////				{cheeta, zira},
	////				{cheeta, zaius},
	////				{},
	////				{daffy},
	////				{donald},
	////				{ daffy, leon},
	////				{donald,  leon},
	////				{donald, daffy},
	////		};
	////
	////		Object[] argssn = new Object[1];
	////		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
	////			Object cage=null;
	////			Objenesis objenesis = new ObjenesisStd(); 
	////			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cCage);
	//////			B cage = new B();
	////			LinkedList<Animal> l = new LinkedList<Animal>();
	////			for (int j=0; j<contenuCage[i].length; j++) {
	////				l.add(contenuCage[i][j]);
	////			}
	////			cage =(Cage) instantiator.newInstance();
	////			argssn[0]=paramOK[i];//new A(paramOK[i][0], paramOK[i][1]);
	////			boolean resPeut=false;
	////			try {
	////				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
	////				if (noteComportement>0) {
	////					if ( mRetirerAnimal!=null) {
	////						mRetirerAnimal.invoke(cage,  argssn);
	////					}
	////					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
	////					if (after==null) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres cag.retirerAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i].getNom()+", "+paramOK[i].getEspece()+") la liste de cag vaut null");
	////						noteComportement=0;
	////					} else {
	////					if (after.size()!=contenuCage[i].length-1) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres cag.retirerAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i].getNom()+", "+paramOK[i].getEspece()+") la taille de la liste de cag est "+after.size()+" au lieu de "+(contenuCage[i].length-1));
	////						noteComportement=0;
	////					} else  {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						boolean contient = true;
	////						for (int k=0; contient &&  k<contenuCageAfter[i].length; k++) {
	////							if (!after.contains(contenuCageAfter[i][k])) {
	////								
	////						System.out.println("   Aie... apres cag.retirerAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i].getNom()+", "+paramOK[i].getEspece()+") la liste des animaux de la cage est "+cagAfterString+". Elle devrait encore contenir ("+contenuCageAfter[i][k].getNom()+", "+contenuCageAfter[i][k].getEspece()+")");
	////						noteComportement=0;
	////							}
	////						}
	////					}
	////						
	////					}
	////				}
	////				} catch (Exception e) {
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				noteComportement=0;
	////			}
	////		}
	////
	////		if (noteDeclaration+noteComportement==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteComportement);
	////	}
	////
	////
	////
	////	
	////
	////
	////
	////
	////
	////
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test10CageToString() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de toString() de Cage");
	////		int noteDeclaration=15;
	////		int noteComportement=85;
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		class A extends Animal{
	////			String w;String m;
	////			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
	////			public String getEspece() {return this.w;}
	////			public String getNom() {return this.m;}
	////			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
	////		};
	////
	////
	////		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////
	////		Method mToString=null;
	////		try {
	////			Method[] methods = cCage.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("toString")) {//.getReturnType().equals(cPoint)) {
	////					mToString=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mToString==null) {
	////			noteComportement=0;
	////			noteDeclaration=0;
	////			System.out.println("   Aie... Je ne trouve pas la redefinitino de la methode toString() dans Cage");
	////		} else {
	////			if (mToString.getParameterCount()!=0) {
	////
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode toString ne doit pas prendre de parametre");
	////			} else if (!(mToString.getReturnType().equals(String.class))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode toString doit retourner une String");
	////			} 
	////		}
	////		if (mToString!=null) mToString.setAccessible(true);
	////
	////		A cheeta = new A("cheeta", "singe");
	////		A zaius  = new A("zaius", "singe");
	////		A zira   = new A("zira", "singe");
	////		A mickey = new A("mickey", "souris");
	////		A donald = new A("donald", "canard");
	////		A daffy  = new A("daffy", "canard");
	////		A leon   = new A("leon", "canard");
	////		
	////		
	////		A[][] contenuCage ={
	////				{},
	////				{cheeta},
	////				{cheeta, zaius},
	////				{cheeta, zaius, zira},
	////				{donald},
	////				{donald, daffy},
	////				{donald, daffy, leon},
	////		};
	////		String[] res ={
	////				"cage :0",
	////				"cage :1",
	////				"cage :2",
	////				"cage :3",
	////				"cage :1",
	////				"cage :2",
	////				"cage :3",
	////		};
	////
	////		Object[] argssn = {};//new Object[1];
	////		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
	////			Object cage=null;
	////			Objenesis objenesis = new ObjenesisStd(); 
	////			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cCage);
	//////			B cage = new B();
	////			LinkedList<Animal> l = new LinkedList<Animal>();
	////			for (int j=0; j<contenuCage[i].length; j++) {
	////				l.add(contenuCage[i][j]);
	////			}
	////			cage =(Cage) instantiator.newInstance();
	////			boolean resPeut=false;
	////			String rest="";
	////			try {
	////				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
	////				if (noteComportement>0) {
	////					if ( mToString!=null) {
	////						rest = (String)mToString.invoke(cage,  argssn);
	////					}
	////					if (rest==null || !rest.equals(res[i])) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
	////						}
	////						System.out.println("   Aie... cag.toString() avec cag contenant les animaux {"+cagString+"} retourne "+(res==null?"null":"\""+rest+"\"")+" au lieu de \""+res[i]+"\"");
	////						noteComportement=0;
	////					} 
	////						
	////					
	////				}
	////				} catch (Exception e) {
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				noteComportement=0;
	////			}
	////		}
	////
	////		if (noteDeclaration+noteComportement==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteComportement);
	////	}
	////
	////
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused" })
	////	public static int test11ZooDeclarationVariables() {
	////		int noteLesAnimaux=30;
	////		int noteNom=30;
	////		int noteLesCages = 30;
	////		int noteRienDAutre=10;
	////		System.out.println("   Test verifiant la declaration des variables d'instances de la classe Zoo : ");
	////		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
	////		if (cZoo==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
	////			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
	////			return 0;
	////		}
	////		Field fieldLesAnimaux=null, fieldLesCages=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf= cZoo.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if (f.getType().equals(String.class)) {
	////				if (fieldNom==null) {
	////					fieldNom=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
	////					if (fieldLesCages==null) {
	////						fieldLesCages=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				} else {
	////					if (fieldLesAnimaux==null) {
	////						fieldLesAnimaux=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldLesAnimaux==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nim")) {
	////							fieldLesAnimaux=f;
	////				}
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("ag")) {
	////							fieldLesCages=f;
	////				}
	////			}
	////		}
	////		if (fieldNom==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
	////							fieldNom=f;
	////				}
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////			noteRienDAutre=0;
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////			noteRienDAutre=0;
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////			noteRienDAutre=0;
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
	////			noteLesAnimaux=0;
	////			noteRienDAutre=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////				noteLesAnimaux=noteLesAnimaux/2;
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////				if (fieldLesAnimaux.getType().getName().equals("java.util.ArrayList")) {
	////					noteLesAnimaux=noteLesAnimaux-20;
	////				} else {
	////					noteLesAnimaux = 10;
	////				}
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////				noteLesAnimaux=noteLesAnimaux-15;
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
	////			noteLesCages=0;
	////			noteRienDAutre=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
	////				noteLesCages=noteLesCages/2;
	////			}
	////			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
	////				if (fieldLesCages.getType().getName().equals("java.util.ArrayList")) {
	////					noteLesCages=noteLesCages-20;
	////				} else {
	////					noteLesCages=noteLesCages-30;
	////				}
	////			}
	////			if (!fieldLesCages.getName().equals("lesCages")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
	////				noteLesCages=noteLesCages-15;
	////			}
	////		}
	////		if (fieldNom==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
	////			noteNom=0;
	////			noteRienDAutre=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	////				noteNom=noteNom/2;
	////			}
	////			if (!fieldNom.getType().equals(String.class)) {
	////				System.out.println("   Aie... Votre variable \""+fieldNom.getName()+"\" n'est pas du type attendu.");
	////					noteNom=noteNom/2;
	////			}
	////			if (!fieldNom.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	////				noteNom=noteNom/2;
	////			}
	////		}
	////		if (noteLesAnimaux<0) noteLesAnimaux=0;
	////		if (noteLesCages<0) noteLesCages=0;
	////		if (noteNom<0) noteNom=0;
	////		if (noteLesAnimaux==0 ||noteLesCages==0 ||noteNom==0) {
	////			noteRienDAutre=0;
	////		}
	////		if (noteNom+noteLesAnimaux+noteLesCages+noteRienDAutre==100) {
	////			System.out.println("   Ok. Votre code passe ce test avec succes.");
	////		}
	////		return noteNom+noteLesAnimaux+noteLesCages+noteRienDAutre;
	////	}
	////
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test12ZooConstructeur3Param() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement du constructeur a trois parametres de Zoo");
	////		int noteLesAnimaux=30;
	////		int noteNom=30;
	////		int noteLesCages = 30;
	////		int noteDeclaration = 10;
	////		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
	////		if (cZoo==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
	////			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
	////			return 0;
	////		}
	////		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf= cZoo.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if (f.getType().equals(String.class)) {
	////				if (fieldNomZoo==null) {
	////					fieldNomZoo=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
	////					if (fieldLesCages==null) {
	////						fieldLesCages=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				} else {
	////					if (fieldLesAnimauxZoo==null) {
	////						fieldLesAnimauxZoo=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nim")) {
	////							fieldLesAnimauxZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("ag")) {
	////							fieldLesCages=f;
	////				}
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
	////							fieldNomZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
	////			noteLesAnimaux=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
	////			}
	////			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
	////			noteLesCages=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
	////			}
	////			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
	////				noteLesCages=0;
	////			}
	////			if (!fieldLesCages.getName().equals("lesCages")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
	////			noteNom=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
	////			}
	////			if (!fieldNomZoo.getType().equals(String.class)) {
	////				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
	////					noteNom=0;
	////			}
	////			if (!fieldNomZoo.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);
	////		if (noteLesAnimaux<0) noteLesAnimaux=0;
	////		if (noteLesCages<0) noteLesCages=0;
	////		if (noteNom<0) noteNom=0;
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Field fieldEspece=null, fieldNom=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cAnimal.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if ( f.getType().equals(String.class)) {
	////				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
	////					fieldEspece=f;
	////				} else if (fieldNom==null) {
	////					fieldNom=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldEspece==null) {
	////			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
	////			}
	////			if (!fieldEspece.getName().equals("espece")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
	////			}
	////		}
	////		if (fieldNom==null) {
	////			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	////			}
	////			if (!fieldNom.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////
	////		if (fieldEspece!=null) fieldEspece.setAccessible(true);
	////		if (fieldNom!=null) fieldNom.setAccessible(true);
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		Field fieldLesAnimaux=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			//System.out.println(">>>"+f.getType().getName()+"<<<");
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimauxZoo!=null) fieldLesAnimauxZoo.setAccessible(true);
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////		
	////		
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////		if (fieldLesCages!=null) fieldLesCages.setAccessible(true);
	////		if (fieldNom!=null) fieldNom.setAccessible(true);
	////		Class[] ar = {};
	////		Class[] arStringLLLL = {String.class, LinkedList.class, LinkedList.class};
	////		Constructor constC1=null;
	////		try {
	////			constC1 = cZoo.getDeclaredConstructor(arStringLLLL);
	////		} catch (NoSuchMethodException e) {
	////			constC1=null;
	////		} catch (SecurityException e) {
	////			constC1=null;
	////		}
	////		if (constC1==null) {
	////			System.out.println("   Aie... je ne trouve pas le constructeur Zoo(String, LinkedList<Cage>, LinkedList<Animal>)");
	////			return 0;
	////		}
	////		if (!Modifier.isPublic(constC1.getModifiers())) {
	////			System.out.println("   Aie... Le constructeur  Zoo(String, LinkedList<Cage>, LinkedList<Animal>) devrait etre public");
	////			noteDeclaration=noteDeclaration/2;
	////		}
	////		constC1.setAccessible(true);
	////
	////			try {
	////				
	////				String[][] animaux= {
	////							{"cheeta", "singe"},{"zaius", "singe"},{"zira", "singe"},{"donald", "canard"},{"daffy", "canard"},{"leon", "canard"}
	////				};
	////				Animal ani=null;
	////				Objenesis objenesis = new ObjenesisStd(); 
	////				ObjectInstantiator instantiatorAnimal= objenesis.getInstantiatorOf(cAnimal);
	////				LinkedList<Animal> lAnimaux = new LinkedList<Animal>();
	////				for (int j=0; j<animaux.length; j++) {
	////					ani = (Animal)instantiatorAnimal.newInstance();
	////					if (fieldNom!=null) fieldNom.set(ani, animaux[j][0]);
	////					if (fieldEspece!=null) fieldEspece.set(ani, animaux[j][1]);
	////					lAnimaux.add(ani);
	////				}
	////				int[][] cages = {
	////						{0, 1},
	////						{2},
	////						{3,4},
	////						{5}
	////				};
	////				LinkedList<Cage> lCages = new LinkedList<Cage>();
	////				ObjectInstantiator instantiatorCage= objenesis.getInstantiatorOf(cCage);
	////				Cage cag=null;
	////				for (int i=0; i<cages.length; i++) {
	////					LinkedList<Animal> la = new LinkedList<Animal>();
	////					for (int j=0; j<cages[i].length; j++) {
	////						la.add(lAnimaux.get(cages[i][j]));
	////					}
	////					cag=(Cage)instantiatorCage.newInstance();
	////					if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cag, la);
	////					lCages.add(cag);
	////				}
	////				String[] noms= {"Aaa", "Bbb", "Ccc"};
	////				int[][] cagesP= {
	////						{0,1},
	////						{2,3},
	////						{0,1,2,3}
	////				};
	////				int[][] animauxP= {
	////						{0,1,2},
	////						{3,4,5},
	////						{0,1,2,3,4,5}
	////				};
	////				for (int i=0; i<noms.length; i++) {
	////					LinkedList<Animal>lac = new LinkedList<Animal>();
	////					for (int j=0; j<animauxP[i].length; j++) {
	////						lac.add(lAnimaux.get(animauxP[i][j]));
	////					}
	////					LinkedList<Cage>lcc = new LinkedList<Cage>();
	////					for (int j=0; j<cagesP[i].length; j++) {
	////						lcc.add(lCages.get(cagesP[i][j]));
	////					}
	////					Object[] aarrgg = { noms[i], lcc, lac};
	////					Object z = (constC1.newInstance(aarrgg));
	////					if (noteNom>0) {
	////						if (fieldNomZoo==null) {
	////							noteNom=0;
	////						} else if (fieldNomZoo.get(z)==null) {
	////							System.out.println("   Aie... votre constructeur affecte le nom a null");
	////							noteNom=0;
	////						} else {
	////							String nom =(String) fieldNomZoo.get(z);
	////							if (!nom.contentEquals(noms[i])) {
	////								System.out.println("   Aie... apres Zoo z=new Zoo(\""+noms[i]+"\", ..., ...) la variable "+fieldNomZoo.getName()+" vaut \""+nom+"\" au lieu de \""+noms[i]+"\"");
	////								noteNom=0;
	////							}
	////						}
	////					}
	////					if (noteLesAnimaux>0) {
	////						if (fieldLesAnimauxZoo==null) {
	////							noteLesAnimaux=0;
	////						} else if (fieldLesAnimauxZoo.get(z)==null) {
	////							System.out.println("   Aie... votre constructeur affecte la liste des animaux a null alors que le parametre reference une liste non vide d'animaux");
	////							noteLesAnimaux=0;
	////						} else {
	////							List<Animal> lan =(List<Animal>) fieldLesAnimauxZoo.get(z);
	////							boolean equal = lan.size()==lac.size();
	////							for (int g=0; equal && g<lac.size(); g++) {
	////								equal= equal && lan.contains(lac.get(g));
	////							}
	////							if (!equal) {
	////								System.out.println("   Aie... apres Zoo z=new Zoo(\""+noms[i]+"\", lcages, lanimaux) la variable "+fieldLesAnimauxZoo.getName()+" ne vaut pas lanimaux");
	////								noteLesAnimaux=0;
	////							}
	////						}
	////					}
	////					if (noteLesCages>0) {
	////						if (fieldLesCages==null) {
	////							noteLesCages=0;
	////						} else if (fieldLesCages.get(z)==null) {
	////							System.out.println("   Aie... votre constructeur affecte la liste des cages a null alors que le parametre reference une liste non vide de cages");
	////							noteLesCages=0;
	////						} else {
	////							List<Cage> lcn =(List<Cage>) fieldLesCages.get(z);
	////							boolean equal = lcn.size()==lcc.size();
	////							for (int g=0; equal && g<lcc.size(); g++) {
	////								equal= equal && lcn.contains(lcc.get(g));
	////							}
	////							if (!equal) {
	////								System.out.println("   Aie... apres Zoo z=new Zoo(\""+noms[i]+"\", lcages, lanimaux) la variable "+fieldLesCages.getName()+" ne vaut pas lcages");
	////								noteLesCages=0;
	////							}
	////						}
	////					}
	////				}
	////
	////			} catch (Exception e) {
	////				System.out.println("   Exception levee lors de la creation d'un Animal avec le constructeur Animal(String,String)");
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				return 0;
	////			}
	////		if (noteDeclaration+noteLesAnimaux+noteLesCages+noteNom==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteLesAnimaux+noteLesCages+noteNom);
	////	}
	////
	////
	////	
	////	
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test13ZooPlacerAnimal() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de placerAnimal de Zoo");
	////		int noteDeclaration=15;
	////		int noteComportement=85;
	////		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
	////		if (cZoo==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
	////			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
	////			return 0;
	////		}
	////		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf= cZoo.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if (f.getType().equals(String.class)) {
	////				if (fieldNomZoo==null) {
	////					fieldNomZoo=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
	////					if (fieldLesCages==null) {
	////						fieldLesCages=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				} else {
	////					if (fieldLesAnimauxZoo==null) {
	////						fieldLesAnimauxZoo=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nim")) {
	////							fieldLesAnimauxZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("ag")) {
	////							fieldLesCages=f;
	////				}
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
	////							fieldNomZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
	////			}
	////			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
	////			}
	////			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesCages.getName().equals("lesCages")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
	////			}
	////			if (!fieldNomZoo.getType().equals(String.class)) {
	////				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldNomZoo.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);
	////
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		class A extends Animal{
	////			String w;String m;
	////			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
	////			public String getEspece() {return this.w;}
	////			public String getNom() {return this.m;}
	////			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
	////		};
	////
	////	class B extends Cage {
	////			private List<Animal> l;
	////			public B(){l = new LinkedList<Animal>();}
	////			public void set(List<Animal>l) {this.l=l;}
	////			public boolean peutContenir(Animal a){//System.out.println("peutcontenir size="+l.size()+" ->"+(l.size()%2==0));
	////				return l.size()%2==0;}	
	////			public void ajouterAnimal(Animal a) {if (peutContenir(a)) l.add(a);}
	////		};
	////
	////		Field fieldLesAnimaux=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			//System.out.println(">>>"+f.getType().getName()+"<<<");
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////
	////		Method mPlacerAnimal=null;
	////		try {
	////			Method[] methods = cZoo.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("placerAnimal")) {//.getReturnType().equals(cPoint)) {
	////					mPlacerAnimal=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mPlacerAnimal==null) {
	////			noteComportement=0;
	////			noteDeclaration=0;
	////			System.out.println("   Aie... Je ne trouve pas la methode placerAnimal de Zoo");
	////		} else {
	////			if (mPlacerAnimal.getParameterCount()!=2) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode placerAnimal de Zoo doit prendre deux parametres");
	////			} else if (!(mPlacerAnimal.getParameters()[0].getType().equals(cAnimal))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode placerAnimal de Zoo doit avoir un Animal pour premier parametre");
	////			} else if (!(mPlacerAnimal.getParameters()[1].getType().equals(cCage))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode placerAnimal de Zoo doit avoir une Cage pour deuxieme parametre");
	////			} 
	////		}
	////		if (mPlacerAnimal!=null) mPlacerAnimal.setAccessible(true);
	////
	////		String[][][] contenuCage ={
	////				{},
	////				{{"cheeta", "singe"},},
	////				{{"cheeta", "singe"},{"zaius", "singe"},},
	////				{{"mickey", "souris"},},
	////				{{"donald", "canard"},},
	////				{{"daffy", "canard"},{"leon", "canard"},},
	////				{{"zira", "singe"},{"zaius", "singe"},},
	////				{{"zaius", "singe"}}
	////		};
	////		String[][] paramOK = {
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"donald", "canard"},
	////				{"cheeta", "singe"},
	////				{"daffy", "canard"},
	////		};
	////		Object zoo=null;
	////		Objenesis objenesis = new ObjenesisStd(); 
	////		ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cZoo);
	////
	////		Object[] argssn = new Object[2];
	////		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
	////			B cage = new B();
	////			LinkedList<Animal> l = new LinkedList<Animal>();
	////			for (int j=0; j<contenuCage[i].length; j++) {
	////				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
	////			}
	////			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
	////			boolean resPeut=false;
	////			try {
	////				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
	////				cage.set(l);
	////				zoo = instantiator.newInstance();
	////				int sizeBefore = l.size();
	////				argssn[1]=cage;
	////				if (noteComportement>0) {
	////					if ( mPlacerAnimal!=null) {
	////						mPlacerAnimal.invoke(zoo,  argssn);
	////					}
	////					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
	////					if (after==null) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste de cag vaut null");
	////						noteComportement=0;
	////					} else {
	////					if (sizeBefore%2==0 && after.size()!=sizeBefore+1) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la taille de la liste de cag est "+after.size()+" alors qu'il est possible d'ajouter l'animal");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==0 && !after.contains(argssn[0])) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il est possible d'ajouter l'animal");
	////						System.out.println("   Attention : ajouter l'animal (pas une copie de l'animal)");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==1 && sizeBefore!=after.size()) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il n'est pas possible d'ajouter l'animal dans cette cage");
	////						noteComportement=0;
	////					}
	////						
	////					}
	////				}
	////				} catch (Exception e) {
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				noteComportement=0;
	////			}
	////		}
	////
	////		if (noteDeclaration+noteComportement==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteComportement);
	////	}
	////
	////
	////
	////
	////	
	////	
	////	
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test14ZooDeplacerAnimal() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de deplacerAnimal de Zoo");
	////		int noteDeclaration=15;
	////		int noteComportement=85;
	////		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
	////		if (cZoo==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
	////			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
	////			return 0;
	////		}
	////		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf= cZoo.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if (f.getType().equals(String.class)) {
	////				if (fieldNomZoo==null) {
	////					fieldNomZoo=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
	////					if (fieldLesCages==null) {
	////						fieldLesCages=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				} else {
	////					if (fieldLesAnimauxZoo==null) {
	////						fieldLesAnimauxZoo=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nim")) {
	////							fieldLesAnimauxZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("ag")) {
	////							fieldLesCages=f;
	////				}
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
	////							fieldNomZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
	////			}
	////			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
	////			}
	////			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesCages.getName().equals("lesCages")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
	////			}
	////			if (!fieldNomZoo.getType().equals(String.class)) {
	////				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldNomZoo.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);
	////
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		class A extends Animal{
	////			String w;String m;
	////			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
	////			public String getEspece() {return this.w;}
	////			public String getNom() {return this.m;}
	////			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
	////		};
	////
	////	class B extends Cage {
	////			private List<Animal> l;
	////			public B(){l = new LinkedList<Animal>();}
	////			public void set(List<Animal>l) {this.l=l;}
	////			public boolean peutContenir(Animal a){//System.out.println("peutcontenir size="+l.size()+" ->"+(l.size()%2==0));
	////				return l.size()%2==0;}	
	////			public void ajouterAnimal(Animal a) {if (peutContenir(a)) l.add(a);}
	////			public void retirerAnimal(Animal a){l.remove(a);}
	////		};
	////
	////		Field fieldLesAnimaux=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			//System.out.println(">>>"+f.getType().getName()+"<<<");
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////
	////		Method mDeplacerAnimal=null;
	////		try {
	////			Method[] methods = cZoo.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("deplacerAnimal")) {//.getReturnType().equals(cPoint)) {
	////					mDeplacerAnimal=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mDeplacerAnimal==null) {
	////			noteComportement=0;
	////			noteDeclaration=0;
	////			System.out.println("   Aie... Je ne trouve pas la methode deplacerAnimal de Zoo");
	////		} else {
	////			if (mDeplacerAnimal.getParameterCount()!=3) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit prendre trois parametres");
	////			} else if (!(mDeplacerAnimal.getParameters()[0].getType().equals(cAnimal))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit avoir un Animal pour premier parametre");
	////			} else if (!(mDeplacerAnimal.getParameters()[1].getType().equals(cCage))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit avoir une Cage pour deuxieme parametre");
	////			}else if (!(mDeplacerAnimal.getParameters()[2].getType().equals(cCage))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit avoir une Cage pour troisieme parametre");
	////			} 
	////		}
	////		if (mDeplacerAnimal!=null) mDeplacerAnimal.setAccessible(true);
	////
	////		String[][][] contenuCage ={
	////				{},
	////				{{"cheeta", "singe"},},
	////				{{"cheeta", "singe"},{"zaius", "singe"},},
	////				{{"mickey", "souris"},},
	////				{{"donald", "canard"},},
	////				{{"daffy", "canard"},{"leon", "canard"},},
	////				{{"zira", "singe"},{"zaius", "singe"},},
	////				{{"zaius", "singe"}}
	////		};
	////		String[][] paramOK = {
	////				{"cheeta", "singe"},
	////				{"king-kong", "gorille"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"zira", "singe"},
	////				{"donald", "canard"},
	////				{"cheeta", "singe"},
	////				{"daffy", "canard"},
	////		};
	////		Object zoo=null;
	////		Objenesis objenesis = new ObjenesisStd(); 
	////		ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cZoo);
	////
	////		Object[] argssn = new Object[3];
	////		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
	////			B cage = new B();
	////			B cageDepart = new B();
	////			LinkedList<Animal> l = new LinkedList<Animal>();
	////			for (int j=0; j<contenuCage[i].length; j++) {
	////				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
	////			}
	////			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
	////			LinkedList<Animal> lDepart = new LinkedList<Animal>();
	////			lDepart.add((A)(argssn[0]));
	////			boolean resPeut=false;
	////			try {
	////				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
	////				cage.set(l);
	////				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cageDepart,lDepart);
	////				cageDepart.set(lDepart);
	////				zoo = instantiator.newInstance();
	////				int sizeBefore = l.size();
	////				argssn[2]=cage;
	////				argssn[1]=cageDepart;
	////				
	////				if (noteComportement>0) {
	////					if ( mDeplacerAnimal!=null) {
	////						mDeplacerAnimal.invoke(zoo,  argssn);
	////					}
	////					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
	////					if (after==null) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste de cageArrivee vaut null");
	////						noteComportement=0;
	////					} else {
	////					if (sizeBefore%2==0 && after.size()!=sizeBefore+1) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la taille de la liste de cageArrivee est "+after.size()+" alors qu'il est possible d'ajouter l'animal");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==0 && !after.contains(argssn[0])) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de cageArrivee est "+cagAfterString+" alors qu'il est possible d'ajouter l'animal");
	////						System.out.println("   Attention : ajouter l'animal (pas une copie de l'animal)");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==0 && lDepart.size()!=0) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") cageDepart contient encore l'animal alors qu'il est possible de le deplacer");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==1 && sizeBefore!=after.size()) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres zoo.placerAnimal(ani,cageDepart, cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il n'est pas possible de deplacer l'animal");
	////						noteComportement=0;
	////					} else if (sizeBefore%2==1 && lDepart.size()==0) {
	////						String cagString="";
	////						for (int j=0; j<contenuCage[i].length; j++) {
	////							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
	////						}
	////						String cagAfterString="";
	////						for (int j=0; j<after.size(); j++) {
	////							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
	////						}
	////						System.out.println("   Aie... apres zoo.placerAnimal(ani,cageDepart, cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" et l'animal a ete retire de la cage de depart alors qu'il n'est pas possible de le deplacer");
	////						noteComportement=0;
	////					}
	////						
	////					}
	////				}
	////				} catch (Exception e) {
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				noteComportement=0;
	////			}
	////		}
	////
	////		if (noteDeclaration+noteComportement==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteComportement);
	////	}
	////
	////
	////	
	////	
	////	
	////	
	////
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test15ZooToString() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de toString de Zoo");
	////		int noteCom = 85;
	////		int noteDec = 15;
	////		class B extends Cage {
	////			private List<Animal> l;
	////			public B(){l = new LinkedList<Animal>();}
	////			public void set(List<Animal>l) {this.l=l;}
	////			public String toString(){return "cage    :"+l.size();}
	////		};
	////		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
	////		if (cZoo==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
	////			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
	////			return 0;
	////		}
	////		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf= cZoo.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if (f.getType().equals(String.class)) {
	////				if (fieldNomZoo==null) {
	////					fieldNomZoo=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
	////					if (fieldLesCages==null) {
	////						fieldLesCages=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				} else {
	////					if (fieldLesAnimauxZoo==null) {
	////						fieldLesAnimauxZoo=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nim")) {
	////							fieldLesAnimauxZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("ag")) {
	////							fieldLesCages=f;
	////				}
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
	////							fieldNomZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
	////			}
	////			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
	////			noteCom=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
	////			}
	////			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
	////				noteCom=0;
	////			}
	////			if (!fieldLesCages.getName().equals("lesCages")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
	////			noteCom=0;
	////		} else {
	////			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
	////			}
	////			if (!fieldNomZoo.getType().equals(String.class)) {
	////				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
	////					noteCom=0;
	////			}
	////			if (!fieldNomZoo.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Field fieldEspece=null, fieldNom=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cAnimal.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if ( f.getType().equals(String.class)) {
	////				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
	////					fieldEspece=f;
	////				} else if (fieldNom==null) {
	////					fieldNom=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldEspece==null) {
	////			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
	////			}
	////			if (!fieldEspece.getName().equals("espece")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
	////			}
	////		}
	////		if (fieldNom==null) {
	////			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	////			}
	////			if (!fieldNom.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////
	////		if (fieldEspece!=null) fieldEspece.setAccessible(true);
	////		if (fieldNom!=null) fieldNom.setAccessible(true);
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		Field fieldLesAnimaux=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimauxZoo!=null) fieldLesAnimauxZoo.setAccessible(true);
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////		
	////		
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////		if (fieldLesCages!=null) fieldLesCages.setAccessible(true);
	////		if (fieldNom!=null) fieldNom.setAccessible(true);
	////		Class[] ar = {};
	////		Class[] arStringLLLL = {String.class, LinkedList.class, LinkedList.class};
	////		Object[] argvide= {};
	////		
	////		Method mToString=null;
	////		try {
	////			Method[] methods = cZoo.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("toString")) {//.getReturnType().equals(cPoint)) {
	////					mToString=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mToString==null) {
	////			noteCom=0;
	////			noteDec=0;
	////			System.out.println("   Aie... Je ne trouve pas de redefinition de la methode toString() dans Zoo");
	////		} else {
	////			if (mToString.getParameterCount()!=0) {
	////				noteCom=0;
	////				noteDec=noteDec/2;
	////				System.out.println("   Aie... la methode toString ne doit prendre aucun parametre");
	////			} else if (!(mToString.getReturnType().equals(String.class))) {
	////				noteCom=0;
	////				noteDec=noteDec/2;
	////				System.out.println("   Aie... la methode toString soit retourner une String");
	////			} else if (!(Modifier.isPublic(mToString.getModifiers()))) {
	////				//noteCom=0;
	////				noteDec=noteDec/2;
	////				System.out.println("   Aie... la methode toString doit etre public");
	////			}
	////		}
	////		if (mToString!=null) mToString.setAccessible(true);
	////		String[] goodRes= {
	////			"Aaa cage :2 cage :1 ",
	////			"Bbb cage :2 cage :1 ",
	////			"Ccc cage :2 cage :1 cage :2 cage :1 "
	////		};
	////
	////			try {
	////				
	////				String[][] animaux= {
	////							{"cheeta", "singe"},{"zaius", "singe"},{"zira", "singe"},{"donald", "canard"},{"daffy", "canard"},{"leon", "canard"}
	////				};
	////				Animal ani=null;
	////				Objenesis objenesis = new ObjenesisStd(); 
	////				ObjectInstantiator instantiatorAnimal= objenesis.getInstantiatorOf(cAnimal);
	////				ObjectInstantiator instantiatorZoo= objenesis.getInstantiatorOf(cZoo);
	////				LinkedList<Animal> lAnimaux = new LinkedList<Animal>();
	////				for (int j=0; j<animaux.length; j++) {
	////					ani = (Animal)instantiatorAnimal.newInstance();
	////					if (fieldNom!=null) fieldNom.set(ani, animaux[j][0]);
	////					if (fieldEspece!=null) fieldEspece.set(ani, animaux[j][1]);
	////					lAnimaux.add(ani);
	////				}
	////				int[][] cages = {
	////						{0, 1},
	////						{2},
	////						{3,4},
	////						{5}
	////				};
	////				LinkedList<Cage> lCages = new LinkedList<Cage>();
	////				B cag=null;
	////				for (int i=0; i<cages.length; i++) {
	////				cag=new B();
	////					LinkedList<Animal> la = new LinkedList<Animal>();
	////					for (int j=0; j<cages[i].length; j++) {
	////						la.add(lAnimaux.get(cages[i][j]));
	////					}
	////					if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cag, la);
	////					cag.set(la);
	////					lCages.add(cag);
	////				}
	////				String[] noms= {"Aaa", "Bbb", "Ccc"};
	////				int[][] cagesP= {
	////						{0,1},
	////						{2,3},
	////						{0,1,2,3}
	////				};
	////				int[][] animauxP= {
	////						{0,1,2},
	////						{3,4,5},
	////						{0,1,2,3,4,5}
	////				};
	////				for (int i=0; noteCom>0 && i<noms.length; i++) {
	////					LinkedList<Animal>lac = new LinkedList<Animal>();
	////					for (int j=0; j<animauxP[i].length; j++) {
	////						lac.add(lAnimaux.get(animauxP[i][j]));
	////					}
	////					LinkedList<Cage>lcc = new LinkedList<Cage>();
	////					for (int j=0; j<cagesP[i].length; j++) {
	////						lcc.add(lCages.get(cagesP[i][j]));
	////					}
	////					Object[] aarrgg = { noms[i], lac, lcc};
	////					Object z = instantiatorZoo.newInstance();//(constC1.newInstance(aarrgg));
	////					if (noteCom>0) {
	////						if (fieldNomZoo==null) {
	////							noteCom=0;
	////						} else {
	////							fieldNomZoo.set(z, noms[i]);
	////						}
	////					}
	////					if (noteCom>0) {
	////						if (fieldLesAnimauxZoo==null) {
	////							noteCom=0;
	////						} else {
	////							fieldLesAnimauxZoo.set(z, lac);
	////						}
	////					}
	////					if (noteCom>0) {
	////						if (fieldLesCages==null) {
	////							noteCom=0;
	////						} else {
	////							fieldLesCages.set(z, lcc);
	////						}
	////					}
	////					String retour ="";
	////					if (noteCom>0 && mToString!=null) {
	////						retour=(String)mToString.invoke(z,  argvide);
	////					}
	////					if (!retour.replaceAll(" ","").replaceAll("\t", "").replaceAll(",", "").equals(goodRes[i].replaceAll(" ","").replaceAll("\t", "").replaceAll(",", ""))) {
	////						System.out.println("Sur le Zoo \""+noms[i]+"\"  disposant de "+lcc.size()+" cages, votre methode toString() retourne : \n\""+retour+"\" au lieu de :\n\""+goodRes[i]+"\"");
	////						noteCom=0;
	////					}
	////				}
	////
	////			} catch (Exception e) {
	////				System.out.println("   Exception levee lors de la creation d'un Animal avec le constructeur Animal(String,String)");
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				return noteDec;
	////			}
	////		if (noteDec+noteCom==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDec+noteCom);
	////	}
	////
	////
	////	
	////	
	////
	////	
	////	
	////	
	////	
	////
	////	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	////	public static int test16ZooCreerVincennes() {
	////		System.out.println("   Test verifiant la declaration et le bon fonctionnement de creerVincennes() de Zoo");
	////		int noteDeclaration=15;
	////		int noteStatic=15;
	////		int noteComportement=70;
	////		int noteNom=10;
	////		int noteCages=30;
	////		int noteAnimaux=30;
	////		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
	////		if (cZoo==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
	////			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
	////			return 0;
	////		}
	////		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;
	////
	////		Field[] tf= cZoo.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if (f.getType().equals(String.class)) {
	////				if (fieldNomZoo==null) {
	////					fieldNomZoo=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
	////					if (fieldLesCages==null) {
	////						fieldLesCages=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				} else {
	////					if (fieldLesAnimauxZoo==null) {
	////						fieldLesAnimauxZoo=f;
	////					} else {
	////						fieldOther=f;
	////					}
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nim")) {
	////							fieldLesAnimauxZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("ag")) {
	////							fieldLesCages=f;
	////				}
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			for (Field f : tf) {
	////				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
	////							fieldNomZoo=f;
	////				}
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimauxZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
	////			}
	////			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////		if (fieldLesCages==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
	////			}
	////			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesCages.getName().equals("lesCages")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
	////			}
	////		}
	////		if (fieldNomZoo==null) {
	////			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
	////		} else {
	////			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
	////			}
	////			if (!fieldNomZoo.getType().equals(String.class)) {
	////				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldNomZoo.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);
	////
	////		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
	////		if (cAnimal==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
	////		if (cCage==null) {
	////			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
	////			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
	////			System.out.println("          ou vous n'avez pas correctement importe le projet.");
	////			return 0;
	////		}
	////
	////		Field fieldLesAnimaux=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cCage.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			//System.out.println(">>>"+f.getType().getName()+"<<<");
	////			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
	////				if (fieldLesAnimaux==null) {
	////					fieldLesAnimaux=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldLesAnimaux==null) {
	////			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
	////			}
	////			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
	////				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
	////			}
	////			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
	////			}
	////		}
	////
	////		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
	////		Field fieldEspece=null, fieldNom=null;
	////		fieldConst=null;
	////		fieldStatic=null;
	////		fieldOther=null;
	////
	////		tf = cAnimal.getDeclaredFields();
	////		for (Field f : tf) {
	////			if (Modifier.isFinal(f.getModifiers())) {
	////				fieldConst=f;
	////			}
	////			if (Modifier.isStatic(f.getModifiers())) {
	////				fieldStatic=f;
	////			} 
	////			if ( f.getType().equals(String.class)) {
	////				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
	////					fieldEspece=f;
	////				} else if (fieldNom==null) {
	////					fieldNom=f;
	////				} else {
	////					fieldOther=f;
	////				}
	////			} else {
	////				fieldOther=f;
	////			}
	////		}
	////		if (fieldConst!=null) {
	////			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
	////		}
	////		if (fieldStatic!=null) {
	////			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
	////			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
	////		}
	////		if (fieldOther!=null) {
	////			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
	////			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
	////		}
	////		if (fieldEspece==null) {
	////			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
	////			}
	////			if (!fieldEspece.getName().equals("espece")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
	////			}
	////		}
	////		if (fieldNom==null) {
	////			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
	////		} else {
	////			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
	////				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
	////				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
	////			}
	////			if (!fieldNom.getName().equals("nom")) {
	////				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
	////				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
	////			}
	////		}
	////
	////		if (fieldEspece!=null) fieldEspece.setAccessible(true);
	////		if (fieldNom!=null) fieldNom.setAccessible(true);
	////
	////		Method mCreateVincennes=null;
	////		try {
	////			Method[] methods = cZoo.getDeclaredMethods();
	////			for (Method m : methods) {
	////				if (m.getName().equals("creerVincennes")) {//.getReturnType().equals(cPoint)) {
	////					mCreateVincennes=m;
	////				} 
	////			}
	////		} catch (Exception e1) {
	////		} 
	////		if (mCreateVincennes==null) {
	////			noteComportement=0;
	////			noteDeclaration=0;
	////			System.out.println("   Aie... Je ne trouve pas la methode createVincennes de Zoo");
	////		} else {
	////			if (mCreateVincennes.getParameterCount()!=0) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode creerVincennes de Zoo ne doit pas comporter de parametres");
	////			} else if (!(mCreateVincennes.getReturnType().equals(cZoo))) {
	////				noteComportement=0;
	////				noteDeclaration=noteDeclaration/2;
	////				System.out.println("   Aie... la methode creerVincennes de Zoo doit avoir Zoo pour type retour");
	////			} 
	////			if (!Modifier.isStatic(mCreateVincennes.getModifiers())) {
	////				//noteComportement=0;
	////				noteStatic=0;
	////				System.out.println("   Aie... la methode creerVincennes de Zoo doit etre une methode de classe (pas une methode d'instance)");
	////			} 
	////		}
	////		if (mCreateVincennes!=null) mCreateVincennes.setAccessible(true);
	////
	////
	////		Object zoo=null;
	////		Objenesis objenesis = new ObjenesisStd(); 
	////		ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cZoo);
	////
	////		Object[] argssn = {};// new Object[2];
	////		Object zooV=null;
	////			try {
	////				zoo = instantiator.newInstance();
	////				if (noteComportement>0) {
	////					if ( mCreateVincennes!=null) {
	////						zooV=mCreateVincennes.invoke(zoo,  argssn);
	////						if (zooV==null) {
	////							System.out.println("   Aie... votre methode creerVincennes() retourne null");
	////							noteComportement=0;
	////						} else {
	////							if (fieldNomZoo!=null) {
	////								String n = (String)(fieldNomZoo.get(zooV));
	////								if (!n.equals("Vincennes")) {
	////									System.out.println("   Aie... le zoo retourne porte le nom \""+n+"\" au lieu de \"Vincennes\"");
	////									noteNom=0;
	////								}
	////							} else {
	////								noteNom=0;
	////							}
	////							if (fieldLesCages!=null) {
	////								fieldLesCages.setAccessible(true);
	////								List<Cage> lc = (List<Cage>)fieldLesCages.get(zooV);
	////								if (lc.size()!=2) {
	////									System.out.println("   Aie... le zoo retourne comporte "+lc.size()+" cages au lieu de 2");
	////									noteCages=0;
	////									noteAnimaux=0;
	////								} else {
	////									if (fieldLesAnimaux!=null) {
	////										List<Animal> la =(List<Animal>) fieldLesAnimaux.get(lc.get(0));
	////										if (la==null || la.size()!=1) {
	////											System.out.println("   Aie... la premiere cage ne comporte pas un animal");
	////											noteAnimaux=0;
	////										} else {
	////											if (fieldNom!=null && fieldEspece!=null) {
	////												String n1 =(String)fieldNom.get(((Animal)(la.get(0))));
	////												if (!n1.toLowerCase().equals("simba")) {
	////													System.out.println("   Aie... l'animal dans la premiere cage s'appelle \""+n1+"\" au lieu de \"simba\"");
	////													noteAnimaux=0;
	////												}
	////												String e1 =(String)fieldEspece.get(((Animal)(la.get(0))));
	////												if (noteAnimaux>0 && !e1.toLowerCase().equals("lion")) {
	////													System.out.println("   Aie... l'animal dans la premiere cage est de l'espece \""+e1+"\" au lieu de \"lion\"");
	////													noteAnimaux=0;
	////												}
	////											} else {
	////												noteAnimaux=0;
	////											}
	////										}
	////										la =(List<Animal>) fieldLesAnimaux.get(lc.get(1));
	////										if (la==null || la.size()!=1) {
	////											System.out.println("   Aie... la deuxieme cage ne comporte pas un animal");
	////											noteAnimaux=0;
	////										} else {
	////											if (fieldNom!=null && fieldEspece!=null) {
	////												String n1 =(String)fieldNom.get(((Animal)(la.get(0))));
	////												if (!n1.toLowerCase().equals("mickey")) {
	////													System.out.println("   Aie... l'animal dans la deuxieme cage s'appelle \""+n1+"\" au lieu de \"mickey\"");
	////													noteAnimaux=0;
	////												}
	////												String e1 =(String)fieldEspece.get(((Animal)(la.get(0))));
	////												if (noteAnimaux>0 && !e1.toLowerCase().equals("souris")) {
	////													System.out.println("   Aie... l'animal dans la deuxieme cage est de l'espece \""+e1+"\" au lieu de \"souris\"");
	////													noteAnimaux=0;
	////												}
	////											} else {
	////												noteAnimaux=0;
	////											}
	////										}
	////									} else {
	////										noteAnimaux=0;
	////									}
	////								}
	////							}else {
	////								noteCages=0;
	////							}
	////							
	////						}
	////						
	////					} else {
	////						noteComportement=0;
	////					}
	////				}
	////				} catch (Exception e) {
	////				if (e instanceof InvocationTargetException) {
	////					e.getCause().printStackTrace();
	////				} else {
	////					e.printStackTrace();
	////				}
	////				noteComportement=0;
	////			}
	////			if (noteComportement>0) {
	////				noteComportement=noteNom+noteCages+noteAnimaux;
	////			}
	////
	////			if (noteDeclaration==0) {
	////				noteStatic=0;
	////			}
	////		if (noteDeclaration+noteComportement+noteStatic==100) {
	////			System.out.println("   Ok. Votre code passe le test.");
	////		}
	////		return (noteDeclaration+noteComportement+noteStatic);
	////	}
}
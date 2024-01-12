package packnp.tests.tools;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import packnp.zoo.Animal;
import packnp.zoo.Cage;





public class TestsMPO20231220 {

	@SuppressWarnings({ "rawtypes", "unused" })
	public static int test01AnimalDeclarationVariables() {
		int noteEspece=40;
		int noteNom=40;
		int noteRienDAutre=20;
		System.out.println("   Test verifiant la declaration des variables d'instances de la classe Animal : ");
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Field fieldEspece=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cAnimal.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
					fieldEspece=f;
				} else if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
			noteRienDAutre=0;
		}
		if (fieldEspece==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
			noteEspece=0;
		} else {
			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
				noteEspece=noteEspece/2;
			}
			if (!fieldEspece.getName().equals("espece")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
				noteEspece=noteEspece/2;
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
			noteNom=0;
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				noteNom=noteNom/2;
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
				noteNom=noteNom/2;
			}
		}
		if (noteNom==0 || noteEspece==0) {
			noteRienDAutre=0;
		}
		if (noteNom+noteEspece+noteRienDAutre==100) {
			System.out.println("   Ok. Votre code passe ce test avec succes.");
		}
		return noteNom+noteEspece+noteRienDAutre;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test02AnimalConstructeur2Param() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement du constructeur a deux parametres de Animal");
		int noteDeclaration=0;
		int noteInitNom=50;
		int noteInitEspece=50;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Field fieldEspece=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cAnimal.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
					fieldEspece=f;
				} else if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldEspece==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
			}
			if (!fieldEspece.getName().equals("espece")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}

		if (fieldEspece!=null) fieldEspece.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		Class[] ar = {};
		Class[] arStringString = {String.class, String.class};
		Constructor constC1=null;
		try {
			constC1 = cAnimal.getDeclaredConstructor(arStringString);
		} catch (NoSuchMethodException e) {
			constC1=null;
		} catch (SecurityException e) {
			constC1=null;
		}
		if (constC1==null) {
			System.out.println("   Aie... je ne trouve pas le constructeur d'Animal prenant en parametre deux String ");
			return 0;
		}
		if (!Modifier.isPublic(constC1.getModifiers())) {
			System.out.println("   Aie... Le constructeur d'Animal devrait etre public");
			noteDeclaration=noteDeclaration/2;
		}
		constC1.setAccessible(true);

		int indexNom=2;
		int indexPrenom=2;
		String[] argsString= {"String", "String"};
		List<String> params = Reflexion.constructeurNomsParametresNomFic("src"+File.separator+"packnp"+File.separator+"zoo"+File.separator+"Animal.java", "Animal", argsString);
		String param0 = (params!=null && params.size()>0) ? params.get(0).toLowerCase() : "";
		String param1 = (params!=null && params.size()>1) ? params.get(1).toLowerCase() : "";
		if ( (param0.contains("o") ||param0.contains("a") || param0.contains("n"))) {
			indexNom=0;
		}
		if ( (param1.contains("o") ||param1.contains("a") || param1.contains("n"))) {
			if (indexNom==2) {
				indexNom=1;
			} else {
				indexNom=2;
			}
		}
		if (indexNom==0) {
			indexPrenom=1;
		} else {
			indexPrenom=0;
		}

		Object[][][] paramC1 ={
				{
					{"cheeta", "singe"},
					{"king-kong", "gorille"},
					{"mickey", "souris"},
					{"donald", "canard"}
				},
				{
					{"singe", "cheeta"},
					{"gorille", "king-kong"},
					{"souris", "mickey"},
					{"canard", "donald"}
				}
		};
		Object ptc1=null;

		if (indexNom==0 ||indexNom==1) { // on a pu determiner l'ordre des parametres
			try {
				for (int i=0; i<paramC1.length; i++) {
					ptc1 = (constC1.newInstance(paramC1[indexNom][i]));
					if (fieldNom==null) {
						noteInitNom=0;
					}else if (fieldNom.get(ptc1)==null) {
						System.out.println("  Aie... votre constructeur initialise la variable "+fieldNom.getName()+" a null \n");
						noteInitNom=0;
					} else {
						String nom = (String)(fieldNom.get(ptc1));
						if (noteInitNom>0 && !nom.equals(paramC1[indexNom][i][indexNom])) {
							if (indexNom==0) {
								System.out.println("  Aie... apres new Animal(\""+paramC1[0][i][0]+"\", \""+paramC1[0][i][1]+"\") la variable "+fieldNom.getName()+" de l'Animal vaut \""+nom+"\" au lieu de \""+paramC1[0][i][0]+"\")");
							} else {
								System.out.println("  Aie... apres new Animal(\""+paramC1[1][i][0]+"\", \""+paramC1[1][i][1]+"\") la variable "+fieldNom.getName()+" de l'Animal vaut \""+nom+"\" au lieu de \""+paramC1[1][i][1]+"\")");
							}
							noteInitNom=0;
						} 
					}
					if (fieldEspece==null) {
						noteInitEspece=0;
					}else if (fieldEspece.get(ptc1)==null) {
						System.out.println("  Aie... votre constructeur initialise la variable "+fieldEspece.getName()+" a null \n");
						noteInitEspece=0;
					} else {
						String espece = (String)(fieldEspece.get(ptc1));
						if (noteInitEspece>0 && !espece.equals(paramC1[indexNom][i][indexPrenom])) {
							if (indexNom==0) {
								System.out.println("  Aie... apres new Animal(\""+paramC1[0][i][indexPrenom]+"\", \""+paramC1[0][i][1]+"\") la variable "+fieldEspece.getName()+" de l'Animal vaut \""+espece+"\" au lieu de \""+paramC1[0][i][indexPrenom]+"\")");
							} else {
								System.out.println("  Aie... apres new Animal(\""+paramC1[1][i][indexPrenom]+"\", \""+paramC1[1][i][1]+"\") la variable "+fieldEspece.getName()+" de l'Animal vaut \""+espece+"\" au lieu de \""+paramC1[1][i][indexPrenom]+"\")");
							}
							noteInitEspece=0;
						} 
					}
				}
			} catch (Exception e) {
				System.out.println("   Exception levee lors de la creation d'un Animal avec le constructeur Animal(String,String)");
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				return 0;
			}
		} else {// les noms des parametres ne permettent pas de determiner l'ordre
			System.out.println("ordre indetermine");
			try {
				indexNom=0;
				for (int i=0; i<paramC1.length; i++) {
					ptc1 = (constC1.newInstance(paramC1[indexNom][i]));
					if (fieldNom.get(ptc1)==null) {
						System.out.println("  Aie... votre constructeur initialise la variable "+fieldNom.getName()+" a null \n");
						noteInitNom=0;
					} else {
						String nom = (String)(fieldNom.get(ptc1));
						if (noteInitNom>0 && !(nom.equals(paramC1[0][i][0]) || nom.equals(paramC1[0][i][1]))) {
							if (indexNom==0) {
								System.out.println("  Aie... apres new Animal(\""+paramC1[0][i][0]+"\", \""+paramC1[0][i][1]+"\") la variable "+fieldNom.getName()+" de l'Animal vaut \""+nom+"\" au lieu de \""+paramC1[0][i][0]+"\")");
							}
							noteInitNom=0;
						} 
					}
					if (fieldEspece.get(ptc1)==null) {
						System.out.println("  Aie... votre constructeur initialise la variable "+fieldEspece.getName()+" a null \n");
						noteInitEspece=0;
					} else {
						String espece = (String)(fieldEspece.get(ptc1));
						if (noteInitEspece>0 && !(espece.equals(paramC1[0][i][0])||espece.equals(paramC1[0][i][1]))) {
							if (indexNom==0) {
								System.out.println("  Aie... apres new Animal(\""+paramC1[0][i][0]+"\", \""+paramC1[0][i][1]+"\") la variable "+fieldEspece.getName()+" de l'Animal vaut \""+espece+"\" au lieu de \""+paramC1[0][i][1]+"\")");
							} 
							noteInitEspece=0;
						} 
					}
				}
			} catch (Exception e) {
				System.out.println("   Exception levee lors de la creation d'un Animal avec le constructeur Animal(String,String)");
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				return 0;
			}

		}
		if (noteDeclaration+noteInitEspece+noteInitNom==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteInitEspece+noteInitNom);
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test03AnimalAccesseurs() {
		System.out.println("   Test verifiant le bon fonctionnement des accesseurs getNom et getEspece de Animal");
		int noteDeclarationGetNom=10;
		int noteComportementGetNom=40;
		int noteDeclarationGetEspece=10;
		int noteComportementGetEspece=40;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Field fieldEspece=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cAnimal.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
					fieldEspece=f;
				} else if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldEspece==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
			}
			if (!fieldEspece.getName().equals("espece")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}

		if (fieldEspece!=null) fieldEspece.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		Method mGetNom=null, mGetEspece=null, mAutre=null;
		try {
			Method[] methods = cAnimal.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("getNom")) {//.getReturnType().equals(cPoint)) {
					mGetNom=m;
				} else if (m.getName().equals("getEspece")) {//'.getReturnType().equals(java.awt.Color.class)) {
					mGetEspece=m;
				} else { mAutre=m;

				}
			}
		} catch (Exception e1) {
		} 
		if (mGetNom==null) {
			noteDeclarationGetNom=0;
			noteComportementGetNom=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur getNom() de Animal");
		}
		if (mGetEspece==null) {
			noteDeclarationGetEspece=0;
			noteComportementGetEspece=0;
			System.out.println("   Aie... Je ne trouve pas l'accesseur getEspece() de Animal");
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
		if (noteDeclarationGetEspece>0) {
			if (mGetEspece.getParameterCount()!=0) {
				System.out.println("   Aie... Votre methode getEspece ne devrait pas prendre de parametres");
				noteDeclarationGetEspece=0;
				noteComportementGetEspece=0;
			}
		}
		if (noteDeclarationGetEspece>0) {
			if (!mGetEspece.getReturnType().equals(String.class)) {
				System.out.println("   Aie... Votre methode getEspece() n'a pas le type retour attendu");
				noteDeclarationGetEspece=noteDeclarationGetEspece/2;
				noteComportementGetEspece=0;			}
		}

		if (mGetNom!=null) mGetNom.setAccessible(true);
		if (mGetEspece!=null) mGetEspece.setAccessible(true);
		String[][] argsOk ={
				{"cheeta", "singe"},
				{"king-kong", "gorille"},
				{"mickey", "souris"},
				{"donald", "canard"}
		};


		Object[] argssn = {};
		for (int i=0; i<argsOk.length; i++) {
			Object pt1=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cAnimal);
			pt1 = instantiator.newInstance();
			String resNom=null, resEspece=null;
			try {
				if (fieldNom!=null) fieldNom.set(pt1,argsOk[i][0]);
				if (fieldEspece!=null) fieldEspece.set(pt1,argsOk[i][1]);
				if (noteComportementGetNom>0) {
					if ( mGetNom!=null) {
						resNom=(String)(mGetNom.invoke(pt1,  argssn));
					}
					if ( resNom==null ) {
						System.out.println("   Aie... votre accesseur getNom n'est pas correct. Il retourne null sur une instance ayant un nom initialise a \""+argsOk[i][0]+"\"");
						noteComportementGetNom=0;
					} else if (!resNom.equals(argsOk[i][0])) {
						System.out.println("   Aie... votre accesseur getNom n'est pas correct. Il retourne \""+resNom+"\" sur une instance ayant un nom initialise a \""+argsOk[i][0]+"\"");
						noteComportementGetNom=0;
					}
				}
				if (noteComportementGetEspece>0) {
					if ( mGetEspece!=null) {
						resEspece=(String)(mGetEspece.invoke(pt1,  argssn));
					}
					if ( resEspece==null ) {
						System.out.println("   Aie... votre accesseur getEspece n'est pas correct. Il retourne null sur une instance ayant une espece initialisee a \""+argsOk[i][1]+"\"");
						noteComportementGetEspece=0;
					} else if (!resEspece.equals(argsOk[i][1])) {
						System.out.println("   Aie... votre accesseur getEspece n'est pas correct. Il retourne \""+resEspece+"\" sur une instance ayant une espece initialisee a \""+argsOk[i][1]+"\"");
						noteComportementGetEspece=0;
					}
				}
			} catch (Exception e) {
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				noteComportementGetNom=0;
				noteComportementGetEspece=0;
			}
		}
		if (noteDeclarationGetNom+noteDeclarationGetEspece+noteComportementGetEspece+noteComportementGetNom==100	) {
			System.out.println("   Ok. Votre code passe le test avec succes.");
		}
		return noteDeclarationGetNom+noteDeclarationGetEspece+noteComportementGetEspece+noteComportementGetNom;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test04AnimalMemeEspece() {
		System.out.println("   Test verifiant le bon fonctionnement de la methode memeEspece");
		int noteDeclaration=30;
		int noteComportement=70;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Field fieldEspece=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cAnimal.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
					fieldEspece=f;
				} else if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldEspece==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
			}
			if (!fieldEspece.getName().equals("espece")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}

		if (fieldEspece!=null) fieldEspece.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		Method mMemeEspece=null, mAutre=null;
		try {
			Method[] methods = cAnimal.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("memeEspece")) {//.getReturnType().equals(cPoint)) {
					mMemeEspece=m;
				} else{ 
					mAutre=m;
				}
			}
		} catch (Exception e1) {
		} 
		if (mMemeEspece==null) {
			noteDeclaration=0;
			noteComportement=0;
			System.out.println("   Aie... Je ne trouve pas la methode memeEspece de Animal");
		}
		if (noteDeclaration>0) {
			if (mMemeEspece.getParameterCount()!=1) {
				System.out.println("   Aie... Votre methode memeEspece devrait comporter un unique parametre");
				noteDeclaration=noteDeclaration/2;
				noteComportement=0;
			} else {
				if (!mMemeEspece.getParameters()[0].getType().equals(cAnimal)) {
					System.out.println("   Aie... Le type du parametre de memeAnimal n'est pas celui attendu");
					noteDeclaration=noteDeclaration/2;
					noteComportement=0;
				}
			}
		}
		if (noteDeclaration>0) {
			if (!mMemeEspece.getReturnType().equals(boolean.class)) {
				System.out.println("   Aie... Votre methode memeEspece n'a pas le type retour attendu");
				noteDeclaration=noteDeclaration/2;
				noteComportement=0;			}
		}
		if (noteDeclaration>0) {
			if (!Modifier.isPublic(mMemeEspece.getModifiers())) {
				System.out.println("   Aie... Votre methode memeEspece n'est pas declaree public");
				noteDeclaration=noteDeclaration/2;
				noteComportement=0;			}
		}

		if (mMemeEspece!=null) mMemeEspece.setAccessible(true);
		String[][] argsOk ={
				{"cheeta", "singe"},
				{"king-kong", "gorille"},
				{"mickey", "souris"},
				{"donald", "canard"},
				{"daffy", "canard"},
				{"zira", "singe"},
				{"zaius", "singe"}
		};
		boolean[] rep = {false,false,false,false,true,true,false,false,false,false,false,false,false,false,false,true,false,false,false,false,true};


		int r=0;
		Object[] argssn = new Object[1];
		for (int i=0; noteComportement>0 && i<argsOk.length-1; i++) {
			for (int j=i+1; noteComportement>0 && j<argsOk.length; j++) {
				Object pt1=null;
				Object pt2=null;
				Objenesis objenesis = new ObjenesisStd(); 
				ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cAnimal);
				pt1 = instantiator.newInstance();
				pt2 = instantiator.newInstance();
				argssn[0]=pt2;
				boolean res=false;
				try {
					if (fieldNom!=null) fieldNom.set(pt1,argsOk[i][0]);
					if (fieldEspece!=null) fieldEspece.set(pt1,argsOk[i][1]);
					if (fieldNom!=null) fieldNom.set(pt2,argsOk[j][0]);
					if (fieldEspece!=null) fieldEspece.set(pt2,argsOk[j][1]+"");
					if (noteComportement>0) {
						if ( mMemeEspece!=null) {
							res=(boolean)(mMemeEspece.invoke(pt1,  argssn));
						}
						//System.out.print(res+",");
						if ( res!=rep[r] ) {
							System.out.println("   Aie... a1.memeEspece(a2) retourne "+res+" au lieu de "+rep[r]+" ou a1 est de l'espece "+argsOk[i][1]+" et a2 de l'espece "+argsOk[j][1]);
							noteComportement=0;
						} 
						r++;
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
		}
		if (noteDeclaration+noteComportement==100) {
			System.out.println("   Ok. Votre code passe le test avec succes.");
		}
		return noteDeclaration+noteComportement;
	}


	@SuppressWarnings({ "rawtypes", "unused" })
	public static int test05CageDeclarationVariables() {
		int noteLesAnimaux=70;
		int noteNom=10;
		int noteRienDAutre=20;
		System.out.println("   Test verifiant la declaration des variables d'instances de la classe Cage : ");
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
			noteRienDAutre=0;
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
			noteLesAnimaux=0;
			noteNom=0;
			noteRienDAutre=0;
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
				noteLesAnimaux=noteLesAnimaux/2;
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
				if (fieldLesAnimaux.getType().getName().equals("java.util.ArrayList")) {
					noteLesAnimaux=noteLesAnimaux-20;
				} else {
					noteLesAnimaux = 10;
				}
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
				noteNom=0;
			}
		}
		if (noteLesAnimaux==0) {
			noteRienDAutre=0;
		}
		if (noteNom+noteLesAnimaux+noteRienDAutre==100) {
			System.out.println("   Ok. Votre code passe ce test avec succes.");
		}
		return noteNom+noteLesAnimaux+noteRienDAutre;
	}


	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test06CageConstructeurSansParam() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement du constructeur sans parametre de Cage");
		int noteDeclaration=0;
		int noteInitAnimaux=100;
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
		Class[] ar = {};
		Class[] arStringString = {String.class, String.class};
		Constructor constC1=null;
		try {
			constC1 = cCage.getDeclaredConstructor(ar);
		} catch (NoSuchMethodException e) {
			constC1=null;
		} catch (SecurityException e) {
			constC1=null;
		}
		if (constC1==null) {
			System.out.println("   Aie... je ne trouve pas le constructeur sans parametre de Cage");
			return 0;
		}

		if (!Modifier.isPublic(constC1.getModifiers())) {
			System.out.println("   Aie... Le constructeur de Cage devrait etre public");
			noteDeclaration=noteDeclaration/2;
		}
		constC1.setAccessible(true);
		Object[] paramVide ={
		};
		Object ptc1=null;

		try {
			ptc1 = (constC1.newInstance(paramVide));
			if (fieldLesAnimaux==null) {
				noteInitAnimaux=0;
			} else if (fieldLesAnimaux.get(ptc1)==null) {
				System.out.println("  Aie... votre constructeur initialise la variable "+fieldLesAnimaux.getName()+" a null au lieu de l'initialiser avec une liste vide d'animaux \n");
				noteInitAnimaux=0;
			} else {
				List l = (List)fieldLesAnimaux.get(ptc1);
				if (l.size()!=0) {
					System.out.println("   Aie... Vous initialisez la variable "+fieldLesAnimaux.getName()+" avec une liste de taille "+l.size()+" au lieu d'une liste vide");
					noteInitAnimaux=0;
				}
				if (noteInitAnimaux>0 && !(fieldLesAnimaux.get(ptc1) instanceof LinkedList)) {
					System.out.println("   Aie... Vous initialisez la variable "+fieldLesAnimaux.getName()+" avec une instance de "+(fieldLesAnimaux.get(ptc1)).getClass().getName()+" au lieu d'une LinkedList");
					noteInitAnimaux=noteInitAnimaux/2;
				}
			}
		} catch (Exception e) {
			System.out.println("   Exception levee lors de la creation d'une Cage avec le constructeur Cage()");
			if (e instanceof InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return 0;
		}

		if (noteDeclaration+noteInitAnimaux==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteInitAnimaux);
	}




	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test07CagePeutContenir() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de peutContenir de Cage");
		int noteDeclaration=15;
		int noteComportement=85;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		class A extends Animal{
			String w;String m;
			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
			public String getEspece() {return this.w;}
			public String getNom() {return this.m;}
			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
		};


		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			//System.out.println(">>>"+f.getType().getName()+"<<<");
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);

		Method mPeutContenir=null;
		try {
			Method[] methods = cCage.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("peutContenir")) {//.getReturnType().equals(cPoint)) {
					mPeutContenir=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mPeutContenir==null) {
			noteComportement=0;
			noteDeclaration=0;
			System.out.println("   Aie... Je ne trouve pas la methode peutContenir de Cage");
		} else {
			if (mPeutContenir.getParameterCount()!=1) {

				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode peutContenir de Cage doit prendre un unique parametre");
			} else if (!(mPeutContenir.getParameters()[0].getType().equals(cAnimal))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode peutContenir de Cage doit prendre un unique parametre de type Animal");
			} 
			if (!mPeutContenir.getReturnType().equals(boolean.class)) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode peutContenir de Cage doit retourner un booleen");
			}
		}
		if (mPeutContenir!=null) mPeutContenir.setAccessible(true);

		String[][][] contenuCage ={
				{},
				{{"cheeta", "singe"},},
				{{"cheeta", "singe"},{"zaius", "singe"},},
				{{"mickey", "souris"},},
				{{"donald", "canard"},},
				{{"daffy", "canard"},{"leon", "canard"},},
				{{"zira", "singe"},{"zaius", "singe"},},
				{{"zaius", "singe"}}
		};
		String[][] paramOK = {
				{"cheeta", "singe"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"donald", "canard"},
				{"donald", "canard"},
				{"zira", "singe"},
		};
		boolean[] res = {
				true, true, true, false, false, true, false, true
		};

		Object[] argssn = new Object[1];
		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
			Object cage=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cCage);
			//B cage = new B();
			LinkedList<Animal> l = new LinkedList<Animal>();
			for (int j=0; j<contenuCage[i].length; j++) {
				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
			}
			cage =(Cage) instantiator.newInstance();
			//pt1 = instantiator.newInstance();
			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
			boolean resPeut=false;
			try {
				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
				if (noteComportement>0) {
					if ( mPeutContenir!=null) {
						resPeut=(boolean)(mPeutContenir.invoke(cage,  argssn));
					}
					if (resPeut!=res[i]) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						System.out.println("   Aie... cag.peutContenir(ani) retourne "+resPeut+" sur une cage contenant les animaux {"+cagString+"} avec ani=("+paramOK[i][0]+", "+paramOK[i][1]+")");
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





	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test08CageAjouterAnimal() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de ajouterAnimal de Cage");
		int noteDeclaration=15;
		int noteComportement=85;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		class A extends Animal{
			String w;String m;
			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
			public String getEspece() {return this.w;}
			public String getNom() {return this.m;}
			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
		};

	class B extends Cage {
			private List<Animal> l;
			public B(){l = new LinkedList<Animal>();}
			public void set(List<Animal>l) {this.l=l;}
			public boolean peutContenir(Animal a){//System.out.println("peutcontenir size="+l.size()+" ->"+(l.size()%2==0));
				return l.size()%2==0;}			
		};

		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			//System.out.println(">>>"+f.getType().getName()+"<<<");
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);

		Method mAjouterAnimal=null;
		try {
			Method[] methods = cCage.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("ajouterAnimal")) {//.getReturnType().equals(cPoint)) {
					mAjouterAnimal=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mAjouterAnimal==null) {
			noteComportement=0;
			noteDeclaration=0;
			System.out.println("   Aie... Je ne trouve pas la methode ajouterAnimal de Cage");
		} else {
			if (mAjouterAnimal.getParameterCount()!=1) {

				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode ajouterAnimal de Cage doit prendre un unique parametre");
			} else if (!(mAjouterAnimal.getParameters()[0].getType().equals(cAnimal))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode ajouterAnimal de Cage doit prendre un unique parametre de type Animal");
			} 
		}
		if (mAjouterAnimal!=null) mAjouterAnimal.setAccessible(true);

		String[][][] contenuCage ={
				{},
				{{"cheeta", "singe"},},
				{{"cheeta", "singe"},{"zaius", "singe"},},
				{{"mickey", "souris"},},
				{{"donald", "canard"},},
				{{"daffy", "canard"},{"leon", "canard"},},
				{{"zira", "singe"},{"zaius", "singe"},},
				{{"zaius", "singe"}}
		};
		String[][] paramOK = {
				{"cheeta", "singe"},
				{"king-kong", "gorille"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"donald", "canard"},
				{"cheeta", "singe"},
				{"daffy", "canard"},
		};

		Object[] argssn = new Object[1];
		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
			B cage = new B();
			LinkedList<Animal> l = new LinkedList<Animal>();
			for (int j=0; j<contenuCage[i].length; j++) {
				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
			}
			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
			boolean resPeut=false;
			try {
				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
				cage.set(l);
				int sizeBefore = l.size();
				if (noteComportement>0) {
					if ( mAjouterAnimal!=null) {
						mAjouterAnimal.invoke(cage,  argssn);
					}
					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
					if (after==null) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste de cag vaut null");
						noteComportement=0;
					} else {
					if (sizeBefore%2==0 && after.size()!=sizeBefore+1) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la taille de la liste de cag est "+after.size()+" alors qu'il est possible d'ajouter l'animal");
						noteComportement=0;
					} else if (sizeBefore%2==0 && !after.contains(argssn[0])) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il est possible d'ajouter l'animal");
						System.out.println("   Attention : ajouter l'animal (pas une copie de l'animal)");
						noteComportement=0;
					} else if (sizeBefore%2==1 && sizeBefore!=after.size()) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres cag.ajouterAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il n'est pas possible d'ajouter l'animal dans cette cage");
						noteComportement=0;
					}
						
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








	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test09CageRetirerAnimal() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de retirerAnimal de Cage");
		int noteDeclaration=15;
		int noteComportement=85;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		class A extends Animal{
			String w;String m;
			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
			public String getEspece() {return this.w;}
			public String getNom() {return this.m;}
			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
		};


		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			//System.out.println(">>>"+f.getType().getName()+"<<<");
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);

		Method mRetirerAnimal=null;
		try {
			Method[] methods = cCage.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("retirerAnimal")) {//.getReturnType().equals(cPoint)) {
					mRetirerAnimal=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mRetirerAnimal==null) {
			noteComportement=0;
			noteDeclaration=0;
			System.out.println("   Aie... Je ne trouve pas la methode retirerAnimal de Cage");
		} else {
			if (mRetirerAnimal.getParameterCount()!=1) {

				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode retirerAnimal de Cage doit prendre un unique parametre");
			} else if (!(mRetirerAnimal.getParameters()[0].getType().equals(cAnimal))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode retirerAnimal de Cage doit prendre un unique parametre de type Animal");
			} 
		}
		if (mRetirerAnimal!=null) mRetirerAnimal.setAccessible(true);

		A cheeta = new A("cheeta", "singe");
		A zaius  = new A("zaius", "singe");
		A zira   = new A("zira", "singe");
		A mickey = new A("mickey", "souris");
		A donald = new A("donald", "canard");
		A daffy  = new A("daffy", "canard");
		A leon   = new A("leon", "canard");
		
		
		A[][] contenuCage ={
				{cheeta},
				{cheeta, zaius},
				{cheeta, zaius},
				{cheeta, zaius, zira},
				{cheeta, zaius, zira},
				{cheeta, zaius, zira},
				{donald},
				{donald, daffy},
				{donald, daffy},
				{donald, daffy, leon},
				{donald, daffy, leon},
				{donald, daffy, leon},
		};
		A[] paramOK = {
				cheeta,
				cheeta,
				zaius,
				cheeta,
				zaius,
				zira,
				donald, 
				donald,
				daffy,
				donald,
				daffy,
				leon
		};
		A[][] contenuCageAfter ={
				{},
				{zaius},
				{cheeta},
				{zaius, zira},
				{cheeta, zira},
				{cheeta, zaius},
				{},
				{daffy},
				{donald},
				{ daffy, leon},
				{donald,  leon},
				{donald, daffy},
		};

		Object[] argssn = new Object[1];
		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
			Object cage=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cCage);
//			B cage = new B();
			LinkedList<Animal> l = new LinkedList<Animal>();
			for (int j=0; j<contenuCage[i].length; j++) {
				l.add(contenuCage[i][j]);
			}
			cage =(Cage) instantiator.newInstance();
			argssn[0]=paramOK[i];//new A(paramOK[i][0], paramOK[i][1]);
			boolean resPeut=false;
			try {
				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
				if (noteComportement>0) {
					if ( mRetirerAnimal!=null) {
						mRetirerAnimal.invoke(cage,  argssn);
					}
					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
					if (after==null) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
						}
						System.out.println("   Aie... apres cag.retirerAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i].getNom()+", "+paramOK[i].getEspece()+") la liste de cag vaut null");
						noteComportement=0;
					} else {
					if (after.size()!=contenuCage[i].length-1) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
						}
						System.out.println("   Aie... apres cag.retirerAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i].getNom()+", "+paramOK[i].getEspece()+") la taille de la liste de cag est "+after.size()+" au lieu de "+(contenuCage[i].length-1));
						noteComportement=0;
					} else  {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						boolean contient = true;
						for (int k=0; contient &&  k<contenuCageAfter[i].length; k++) {
							if (!after.contains(contenuCageAfter[i][k])) {
								
						System.out.println("   Aie... apres cag.retirerAnimal(ani) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i].getNom()+", "+paramOK[i].getEspece()+") la liste des animaux de la cage est "+cagAfterString+". Elle devrait encore contenir ("+contenuCageAfter[i][k].getNom()+", "+contenuCageAfter[i][k].getEspece()+")");
						noteComportement=0;
							}
						}
					}
						
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



	








	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test10CageToString() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de toString() de Cage");
		int noteDeclaration=15;
		int noteComportement=85;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		class A extends Animal{
			String w;String m;
			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
			public String getEspece() {return this.w;}
			public String getNom() {return this.m;}
			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
		};


		Field fieldLesAnimaux=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);

		Method mToString=null;
		try {
			Method[] methods = cCage.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("toString")) {//.getReturnType().equals(cPoint)) {
					mToString=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mToString==null) {
			noteComportement=0;
			noteDeclaration=0;
			System.out.println("   Aie... Je ne trouve pas la redefinitino de la methode toString() dans Cage");
		} else {
			if (mToString.getParameterCount()!=0) {

				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode toString ne doit pas prendre de parametre");
			} else if (!(mToString.getReturnType().equals(String.class))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode toString doit retourner une String");
			} 
		}
		if (mToString!=null) mToString.setAccessible(true);

		A cheeta = new A("cheeta", "singe");
		A zaius  = new A("zaius", "singe");
		A zira   = new A("zira", "singe");
		A mickey = new A("mickey", "souris");
		A donald = new A("donald", "canard");
		A daffy  = new A("daffy", "canard");
		A leon   = new A("leon", "canard");
		
		
		A[][] contenuCage ={
				{},
				{cheeta},
				{cheeta, zaius},
				{cheeta, zaius, zira},
				{donald},
				{donald, daffy},
				{donald, daffy, leon},
		};
		String[] res ={
				"cage :0",
				"cage :1",
				"cage :2",
				"cage :3",
				"cage :1",
				"cage :2",
				"cage :3",
		};

		Object[] argssn = {};//new Object[1];
		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
			Object cage=null;
			Objenesis objenesis = new ObjenesisStd(); 
			ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cCage);
//			B cage = new B();
			LinkedList<Animal> l = new LinkedList<Animal>();
			for (int j=0; j<contenuCage[i].length; j++) {
				l.add(contenuCage[i][j]);
			}
			cage =(Cage) instantiator.newInstance();
			boolean resPeut=false;
			String rest="";
			try {
				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
				if (noteComportement>0) {
					if ( mToString!=null) {
						rest = (String)mToString.invoke(cage,  argssn);
					}
					if (rest==null || !rest.equals(res[i])) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j].getNom()+", "+contenuCage[i][j].getEspece()+")";
						}
						System.out.println("   Aie... cag.toString() avec cag contenant les animaux {"+cagString+"} retourne "+(res==null?"null":"\""+rest+"\"")+" au lieu de \""+res[i]+"\"");
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




	@SuppressWarnings({ "rawtypes", "unused" })
	public static int test11ZooDeclarationVariables() {
		int noteLesAnimaux=30;
		int noteNom=30;
		int noteLesCages = 30;
		int noteRienDAutre=10;
		System.out.println("   Test verifiant la declaration des variables d'instances de la classe Zoo : ");
		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
		if (cZoo==null) {
			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
			return 0;
		}
		Field fieldLesAnimaux=null, fieldLesCages=null, fieldNom=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf= cZoo.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if (f.getType().equals(String.class)) {
				if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
					if (fieldLesCages==null) {
						fieldLesCages=f;
					} else {
						fieldOther=f;
					}
				} else {
					if (fieldLesAnimaux==null) {
						fieldLesAnimaux=f;
					} else {
						fieldOther=f;
					}
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldLesAnimaux==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nim")) {
							fieldLesAnimaux=f;
				}
			}
		}
		if (fieldLesCages==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("ag")) {
							fieldLesCages=f;
				}
			}
		}
		if (fieldNom==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
							fieldNom=f;
				}
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
			noteRienDAutre=0;
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
			noteRienDAutre=0;
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
			noteLesAnimaux=0;
			noteRienDAutre=0;
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
				noteLesAnimaux=noteLesAnimaux/2;
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
				if (fieldLesAnimaux.getType().getName().equals("java.util.ArrayList")) {
					noteLesAnimaux=noteLesAnimaux-20;
				} else {
					noteLesAnimaux = 10;
				}
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
				noteLesAnimaux=noteLesAnimaux-15;
			}
		}
		if (fieldLesCages==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
			noteLesCages=0;
			noteRienDAutre=0;
		} else {
			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
				noteLesCages=noteLesCages/2;
			}
			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
				if (fieldLesCages.getType().getName().equals("java.util.ArrayList")) {
					noteLesCages=noteLesCages-20;
				} else {
					noteLesCages=noteLesCages-30;
				}
			}
			if (!fieldLesCages.getName().equals("lesCages")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
				noteLesCages=noteLesCages-15;
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
			noteNom=0;
			noteRienDAutre=0;
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
				noteNom=noteNom/2;
			}
			if (!fieldNom.getType().equals(String.class)) {
				System.out.println("   Aie... Votre variable \""+fieldNom.getName()+"\" n'est pas du type attendu.");
					noteNom=noteNom/2;
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
				noteNom=noteNom/2;
			}
		}
		if (noteLesAnimaux<0) noteLesAnimaux=0;
		if (noteLesCages<0) noteLesCages=0;
		if (noteNom<0) noteNom=0;
		if (noteLesAnimaux==0 ||noteLesCages==0 ||noteNom==0) {
			noteRienDAutre=0;
		}
		if (noteNom+noteLesAnimaux+noteLesCages+noteRienDAutre==100) {
			System.out.println("   Ok. Votre code passe ce test avec succes.");
		}
		return noteNom+noteLesAnimaux+noteLesCages+noteRienDAutre;
	}



	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test12ZooConstructeur3Param() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement du constructeur a trois parametres de Zoo");
		int noteLesAnimaux=30;
		int noteNom=30;
		int noteLesCages = 30;
		int noteDeclaration = 10;
		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
		if (cZoo==null) {
			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
			return 0;
		}
		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf= cZoo.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if (f.getType().equals(String.class)) {
				if (fieldNomZoo==null) {
					fieldNomZoo=f;
				} else {
					fieldOther=f;
				}
			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
					if (fieldLesCages==null) {
						fieldLesCages=f;
					} else {
						fieldOther=f;
					}
				} else {
					if (fieldLesAnimauxZoo==null) {
						fieldLesAnimauxZoo=f;
					} else {
						fieldOther=f;
					}
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldLesAnimauxZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nim")) {
							fieldLesAnimauxZoo=f;
				}
			}
		}
		if (fieldLesCages==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("ag")) {
							fieldLesCages=f;
				}
			}
		}
		if (fieldNomZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
							fieldNomZoo=f;
				}
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimauxZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
			noteLesAnimaux=0;
		} else {
			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
			}
			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}
		if (fieldLesCages==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
			noteLesCages=0;
		} else {
			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
			}
			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
				noteLesCages=0;
			}
			if (!fieldLesCages.getName().equals("lesCages")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
			}
		}
		if (fieldNomZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
			noteNom=0;
		} else {
			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
			}
			if (!fieldNomZoo.getType().equals(String.class)) {
				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
					noteNom=0;
			}
			if (!fieldNomZoo.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);
		if (noteLesAnimaux<0) noteLesAnimaux=0;
		if (noteLesCages<0) noteLesCages=0;
		if (noteNom<0) noteNom=0;
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Field fieldEspece=null, fieldNom=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cAnimal.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
					fieldEspece=f;
				} else if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldEspece==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
			}
			if (!fieldEspece.getName().equals("espece")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}

		if (fieldEspece!=null) fieldEspece.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		Field fieldLesAnimaux=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			//System.out.println(">>>"+f.getType().getName()+"<<<");
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimauxZoo!=null) fieldLesAnimauxZoo.setAccessible(true);
		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
		
		

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
		if (fieldLesCages!=null) fieldLesCages.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		Class[] ar = {};
		Class[] arStringLLLL = {String.class, LinkedList.class, LinkedList.class};
		Constructor constC1=null;
		try {
			constC1 = cZoo.getDeclaredConstructor(arStringLLLL);
		} catch (NoSuchMethodException e) {
			constC1=null;
		} catch (SecurityException e) {
			constC1=null;
		}
		if (constC1==null) {
			System.out.println("   Aie... je ne trouve pas le constructeur Zoo(String, LinkedList<Cage>, LinkedList<Animal>)");
			return 0;
		}
		if (!Modifier.isPublic(constC1.getModifiers())) {
			System.out.println("   Aie... Le constructeur  Zoo(String, LinkedList<Cage>, LinkedList<Animal>) devrait etre public");
			noteDeclaration=noteDeclaration/2;
		}
		constC1.setAccessible(true);

			try {
				
				String[][] animaux= {
							{"cheeta", "singe"},{"zaius", "singe"},{"zira", "singe"},{"donald", "canard"},{"daffy", "canard"},{"leon", "canard"}
				};
				Animal ani=null;
				Objenesis objenesis = new ObjenesisStd(); 
				ObjectInstantiator instantiatorAnimal= objenesis.getInstantiatorOf(cAnimal);
				LinkedList<Animal> lAnimaux = new LinkedList<Animal>();
				for (int j=0; j<animaux.length; j++) {
					ani = (Animal)instantiatorAnimal.newInstance();
					if (fieldNom!=null) fieldNom.set(ani, animaux[j][0]);
					if (fieldEspece!=null) fieldEspece.set(ani, animaux[j][1]);
					lAnimaux.add(ani);
				}
				int[][] cages = {
						{0, 1},
						{2},
						{3,4},
						{5}
				};
				LinkedList<Cage> lCages = new LinkedList<Cage>();
				ObjectInstantiator instantiatorCage= objenesis.getInstantiatorOf(cCage);
				Cage cag=null;
				for (int i=0; i<cages.length; i++) {
					LinkedList<Animal> la = new LinkedList<Animal>();
					for (int j=0; j<cages[i].length; j++) {
						la.add(lAnimaux.get(cages[i][j]));
					}
					cag=(Cage)instantiatorCage.newInstance();
					if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cag, la);
					lCages.add(cag);
				}
				String[] noms= {"Aaa", "Bbb", "Ccc"};
				int[][] cagesP= {
						{0,1},
						{2,3},
						{0,1,2,3}
				};
				int[][] animauxP= {
						{0,1,2},
						{3,4,5},
						{0,1,2,3,4,5}
				};
				for (int i=0; i<noms.length; i++) {
					LinkedList<Animal>lac = new LinkedList<Animal>();
					for (int j=0; j<animauxP[i].length; j++) {
						lac.add(lAnimaux.get(animauxP[i][j]));
					}
					LinkedList<Cage>lcc = new LinkedList<Cage>();
					for (int j=0; j<cagesP[i].length; j++) {
						lcc.add(lCages.get(cagesP[i][j]));
					}
					Object[] aarrgg = { noms[i], lcc, lac};
					Object z = (constC1.newInstance(aarrgg));
					if (noteNom>0) {
						if (fieldNomZoo==null) {
							noteNom=0;
						} else if (fieldNomZoo.get(z)==null) {
							System.out.println("   Aie... votre constructeur affecte le nom a null");
							noteNom=0;
						} else {
							String nom =(String) fieldNomZoo.get(z);
							if (!nom.contentEquals(noms[i])) {
								System.out.println("   Aie... apres Zoo z=new Zoo(\""+noms[i]+"\", ..., ...) la variable "+fieldNomZoo.getName()+" vaut \""+nom+"\" au lieu de \""+noms[i]+"\"");
								noteNom=0;
							}
						}
					}
					if (noteLesAnimaux>0) {
						if (fieldLesAnimauxZoo==null) {
							noteLesAnimaux=0;
						} else if (fieldLesAnimauxZoo.get(z)==null) {
							System.out.println("   Aie... votre constructeur affecte la liste des animaux a null alors que le parametre reference une liste non vide d'animaux");
							noteLesAnimaux=0;
						} else {
							List<Animal> lan =(List<Animal>) fieldLesAnimauxZoo.get(z);
							boolean equal = lan.size()==lac.size();
							for (int g=0; equal && g<lac.size(); g++) {
								equal= equal && lan.contains(lac.get(g));
							}
							if (!equal) {
								System.out.println("   Aie... apres Zoo z=new Zoo(\""+noms[i]+"\", lcages, lanimaux) la variable "+fieldLesAnimauxZoo.getName()+" ne vaut pas lanimaux");
								noteLesAnimaux=0;
							}
						}
					}
					if (noteLesCages>0) {
						if (fieldLesCages==null) {
							noteLesCages=0;
						} else if (fieldLesCages.get(z)==null) {
							System.out.println("   Aie... votre constructeur affecte la liste des cages a null alors que le parametre reference une liste non vide de cages");
							noteLesCages=0;
						} else {
							List<Cage> lcn =(List<Cage>) fieldLesCages.get(z);
							boolean equal = lcn.size()==lcc.size();
							for (int g=0; equal && g<lcc.size(); g++) {
								equal= equal && lcn.contains(lcc.get(g));
							}
							if (!equal) {
								System.out.println("   Aie... apres Zoo z=new Zoo(\""+noms[i]+"\", lcages, lanimaux) la variable "+fieldLesCages.getName()+" ne vaut pas lcages");
								noteLesCages=0;
							}
						}
					}
				}

			} catch (Exception e) {
				System.out.println("   Exception levee lors de la creation d'un Animal avec le constructeur Animal(String,String)");
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				return 0;
			}
		if (noteDeclaration+noteLesAnimaux+noteLesCages+noteNom==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteLesAnimaux+noteLesCages+noteNom);
	}


	
	


	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test13ZooPlacerAnimal() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de placerAnimal de Zoo");
		int noteDeclaration=15;
		int noteComportement=85;
		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
		if (cZoo==null) {
			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
			return 0;
		}
		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf= cZoo.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if (f.getType().equals(String.class)) {
				if (fieldNomZoo==null) {
					fieldNomZoo=f;
				} else {
					fieldOther=f;
				}
			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
					if (fieldLesCages==null) {
						fieldLesCages=f;
					} else {
						fieldOther=f;
					}
				} else {
					if (fieldLesAnimauxZoo==null) {
						fieldLesAnimauxZoo=f;
					} else {
						fieldOther=f;
					}
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldLesAnimauxZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nim")) {
							fieldLesAnimauxZoo=f;
				}
			}
		}
		if (fieldLesCages==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("ag")) {
							fieldLesCages=f;
				}
			}
		}
		if (fieldNomZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
							fieldNomZoo=f;
				}
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimauxZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
			}
			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}
		if (fieldLesCages==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
		} else {
			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
			}
			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesCages.getName().equals("lesCages")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
			}
		}
		if (fieldNomZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
		} else {
			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
			}
			if (!fieldNomZoo.getType().equals(String.class)) {
				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldNomZoo.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);

		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		class A extends Animal{
			String w;String m;
			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
			public String getEspece() {return this.w;}
			public String getNom() {return this.m;}
			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
		};

	class B extends Cage {
			private List<Animal> l;
			public B(){l = new LinkedList<Animal>();}
			public void set(List<Animal>l) {this.l=l;}
			public boolean peutContenir(Animal a){//System.out.println("peutcontenir size="+l.size()+" ->"+(l.size()%2==0));
				return l.size()%2==0;}	
			public void ajouterAnimal(Animal a) {if (peutContenir(a)) l.add(a);}
		};

		Field fieldLesAnimaux=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			//System.out.println(">>>"+f.getType().getName()+"<<<");
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);

		Method mPlacerAnimal=null;
		try {
			Method[] methods = cZoo.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("placerAnimal")) {//.getReturnType().equals(cPoint)) {
					mPlacerAnimal=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mPlacerAnimal==null) {
			noteComportement=0;
			noteDeclaration=0;
			System.out.println("   Aie... Je ne trouve pas la methode placerAnimal de Zoo");
		} else {
			if (mPlacerAnimal.getParameterCount()!=2) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode placerAnimal de Zoo doit prendre deux parametres");
			} else if (!(mPlacerAnimal.getParameters()[0].getType().equals(cAnimal))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode placerAnimal de Zoo doit avoir un Animal pour premier parametre");
			} else if (!(mPlacerAnimal.getParameters()[1].getType().equals(cCage))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode placerAnimal de Zoo doit avoir une Cage pour deuxieme parametre");
			} 
		}
		if (mPlacerAnimal!=null) mPlacerAnimal.setAccessible(true);

		String[][][] contenuCage ={
				{},
				{{"cheeta", "singe"},},
				{{"cheeta", "singe"},{"zaius", "singe"},},
				{{"mickey", "souris"},},
				{{"donald", "canard"},},
				{{"daffy", "canard"},{"leon", "canard"},},
				{{"zira", "singe"},{"zaius", "singe"},},
				{{"zaius", "singe"}}
		};
		String[][] paramOK = {
				{"cheeta", "singe"},
				{"king-kong", "gorille"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"donald", "canard"},
				{"cheeta", "singe"},
				{"daffy", "canard"},
		};
		Object zoo=null;
		Objenesis objenesis = new ObjenesisStd(); 
		ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cZoo);

		Object[] argssn = new Object[2];
		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
			B cage = new B();
			LinkedList<Animal> l = new LinkedList<Animal>();
			for (int j=0; j<contenuCage[i].length; j++) {
				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
			}
			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
			boolean resPeut=false;
			try {
				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
				cage.set(l);
				zoo = instantiator.newInstance();
				int sizeBefore = l.size();
				argssn[1]=cage;
				if (noteComportement>0) {
					if ( mPlacerAnimal!=null) {
						mPlacerAnimal.invoke(zoo,  argssn);
					}
					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
					if (after==null) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste de cag vaut null");
						noteComportement=0;
					} else {
					if (sizeBefore%2==0 && after.size()!=sizeBefore+1) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la taille de la liste de cag est "+after.size()+" alors qu'il est possible d'ajouter l'animal");
						noteComportement=0;
					} else if (sizeBefore%2==0 && !after.contains(argssn[0])) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il est possible d'ajouter l'animal");
						System.out.println("   Attention : ajouter l'animal (pas une copie de l'animal)");
						noteComportement=0;
					} else if (sizeBefore%2==1 && sizeBefore!=after.size()) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres zoo.placerAnimal(ani,cag) avec cag contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il n'est pas possible d'ajouter l'animal dans cette cage");
						noteComportement=0;
					}
						
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




	
	
	

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test14ZooDeplacerAnimal() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de deplacerAnimal de Zoo");
		int noteDeclaration=15;
		int noteComportement=85;
		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
		if (cZoo==null) {
			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
			return 0;
		}
		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf= cZoo.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if (f.getType().equals(String.class)) {
				if (fieldNomZoo==null) {
					fieldNomZoo=f;
				} else {
					fieldOther=f;
				}
			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
					if (fieldLesCages==null) {
						fieldLesCages=f;
					} else {
						fieldOther=f;
					}
				} else {
					if (fieldLesAnimauxZoo==null) {
						fieldLesAnimauxZoo=f;
					} else {
						fieldOther=f;
					}
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldLesAnimauxZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nim")) {
							fieldLesAnimauxZoo=f;
				}
			}
		}
		if (fieldLesCages==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("ag")) {
							fieldLesCages=f;
				}
			}
		}
		if (fieldNomZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
							fieldNomZoo=f;
				}
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimauxZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
			}
			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}
		if (fieldLesCages==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
		} else {
			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
			}
			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesCages.getName().equals("lesCages")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
			}
		}
		if (fieldNomZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
		} else {
			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
			}
			if (!fieldNomZoo.getType().equals(String.class)) {
				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldNomZoo.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);

		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		class A extends Animal{
			String w;String m;
			public A(String m,String w){super(m,w);this.w=w;this.m=m;}
			public String getEspece() {return this.w;}
			public String getNom() {return this.m;}
			public boolean memeEspece(Animal a){return ((A)a).getEspece().equals(w);}
		};

	class B extends Cage {
			private List<Animal> l;
			public B(){l = new LinkedList<Animal>();}
			public void set(List<Animal>l) {this.l=l;}
			public boolean peutContenir(Animal a){//System.out.println("peutcontenir size="+l.size()+" ->"+(l.size()%2==0));
				return l.size()%2==0;}	
			public void ajouterAnimal(Animal a) {if (peutContenir(a)) l.add(a);}
			public void retirerAnimal(Animal a){l.remove(a);}
		};

		Field fieldLesAnimaux=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			//System.out.println(">>>"+f.getType().getName()+"<<<");
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);

		Method mDeplacerAnimal=null;
		try {
			Method[] methods = cZoo.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("deplacerAnimal")) {//.getReturnType().equals(cPoint)) {
					mDeplacerAnimal=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mDeplacerAnimal==null) {
			noteComportement=0;
			noteDeclaration=0;
			System.out.println("   Aie... Je ne trouve pas la methode deplacerAnimal de Zoo");
		} else {
			if (mDeplacerAnimal.getParameterCount()!=3) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit prendre trois parametres");
			} else if (!(mDeplacerAnimal.getParameters()[0].getType().equals(cAnimal))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit avoir un Animal pour premier parametre");
			} else if (!(mDeplacerAnimal.getParameters()[1].getType().equals(cCage))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit avoir une Cage pour deuxieme parametre");
			}else if (!(mDeplacerAnimal.getParameters()[2].getType().equals(cCage))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode deplacerAnimal de Zoo doit avoir une Cage pour troisieme parametre");
			} 
		}
		if (mDeplacerAnimal!=null) mDeplacerAnimal.setAccessible(true);

		String[][][] contenuCage ={
				{},
				{{"cheeta", "singe"},},
				{{"cheeta", "singe"},{"zaius", "singe"},},
				{{"mickey", "souris"},},
				{{"donald", "canard"},},
				{{"daffy", "canard"},{"leon", "canard"},},
				{{"zira", "singe"},{"zaius", "singe"},},
				{{"zaius", "singe"}}
		};
		String[][] paramOK = {
				{"cheeta", "singe"},
				{"king-kong", "gorille"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"zira", "singe"},
				{"donald", "canard"},
				{"cheeta", "singe"},
				{"daffy", "canard"},
		};
		Object zoo=null;
		Objenesis objenesis = new ObjenesisStd(); 
		ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cZoo);

		Object[] argssn = new Object[3];
		for (int i=0; noteComportement>0 &&  i<contenuCage.length; i++) {
			B cage = new B();
			B cageDepart = new B();
			LinkedList<Animal> l = new LinkedList<Animal>();
			for (int j=0; j<contenuCage[i].length; j++) {
				l.add(new A(contenuCage[i][j][0], contenuCage[i][j][1]));
			}
			argssn[0]=new A(paramOK[i][0], paramOK[i][1]);
			LinkedList<Animal> lDepart = new LinkedList<Animal>();
			lDepart.add((A)(argssn[0]));
			boolean resPeut=false;
			try {
				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cage,l);
				cage.set(l);
				if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cageDepart,lDepart);
				cageDepart.set(lDepart);
				zoo = instantiator.newInstance();
				int sizeBefore = l.size();
				argssn[2]=cage;
				argssn[1]=cageDepart;
				
				if (noteComportement>0) {
					if ( mDeplacerAnimal!=null) {
						mDeplacerAnimal.invoke(zoo,  argssn);
					}
					List<Animal> after = (List)(fieldLesAnimaux.get(cage));
					if (after==null) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste de cageArrivee vaut null");
						noteComportement=0;
					} else {
					if (sizeBefore%2==0 && after.size()!=sizeBefore+1) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la taille de la liste de cageArrivee est "+after.size()+" alors qu'il est possible d'ajouter l'animal");
						noteComportement=0;
					} else if (sizeBefore%2==0 && !after.contains(argssn[0])) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de cageArrivee est "+cagAfterString+" alors qu'il est possible d'ajouter l'animal");
						System.out.println("   Attention : ajouter l'animal (pas une copie de l'animal)");
						noteComportement=0;
					} else if (sizeBefore%2==0 && lDepart.size()!=0) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres zoo.deplacerAnimal(ani,cageDepart,cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") cageDepart contient encore l'animal alors qu'il est possible de le deplacer");
						noteComportement=0;
					} else if (sizeBefore%2==1 && sizeBefore!=after.size()) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres zoo.placerAnimal(ani,cageDepart, cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" alors qu'il n'est pas possible de deplacer l'animal");
						noteComportement=0;
					} else if (sizeBefore%2==1 && lDepart.size()==0) {
						String cagString="";
						for (int j=0; j<contenuCage[i].length; j++) {
							cagString=cagString+"("+contenuCage[i][j][0]+", "+contenuCage[i][j][1]+")";
						}
						String cagAfterString="";
						for (int j=0; j<after.size(); j++) {
							cagAfterString=cagAfterString+"("+((A)(after.get(j))).getNom()+", "+((A)(after.get(j))).getEspece()+")";
						}
						System.out.println("   Aie... apres zoo.placerAnimal(ani,cageDepart, cageArrivee) avec cageArrivee contenant les animaux {"+cagString+"} et ani=("+paramOK[i][0]+", "+paramOK[i][1]+") la liste des animaux de la cage est "+cagAfterString+" et l'animal a ete retire de la cage de depart alors qu'il n'est pas possible de le deplacer");
						noteComportement=0;
					}
						
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


	
	
	
	


	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test15ZooToString() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de toString de Zoo");
		int noteCom = 85;
		int noteDec = 15;
		class B extends Cage {
			private List<Animal> l;
			public B(){l = new LinkedList<Animal>();}
			public void set(List<Animal>l) {this.l=l;}
			public String toString(){return "cage    :"+l.size();}
		};
		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
		if (cZoo==null) {
			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
			return 0;
		}
		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf= cZoo.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if (f.getType().equals(String.class)) {
				if (fieldNomZoo==null) {
					fieldNomZoo=f;
				} else {
					fieldOther=f;
				}
			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
					if (fieldLesCages==null) {
						fieldLesCages=f;
					} else {
						fieldOther=f;
					}
				} else {
					if (fieldLesAnimauxZoo==null) {
						fieldLesAnimauxZoo=f;
					} else {
						fieldOther=f;
					}
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldLesAnimauxZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nim")) {
							fieldLesAnimauxZoo=f;
				}
			}
		}
		if (fieldLesCages==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("ag")) {
							fieldLesCages=f;
				}
			}
		}
		if (fieldNomZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
							fieldNomZoo=f;
				}
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimauxZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
			}
			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}
		if (fieldLesCages==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
			noteCom=0;
		} else {
			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
			}
			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
				noteCom=0;
			}
			if (!fieldLesCages.getName().equals("lesCages")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
			}
		}
		if (fieldNomZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
			noteCom=0;
		} else {
			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
			}
			if (!fieldNomZoo.getType().equals(String.class)) {
				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
					noteCom=0;
			}
			if (!fieldNomZoo.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);
		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Field fieldEspece=null, fieldNom=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cAnimal.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
					fieldEspece=f;
				} else if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldEspece==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
			}
			if (!fieldEspece.getName().equals("espece")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}

		if (fieldEspece!=null) fieldEspece.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		Field fieldLesAnimaux=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimauxZoo!=null) fieldLesAnimauxZoo.setAccessible(true);
		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
		
		

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
		if (fieldLesCages!=null) fieldLesCages.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);
		Class[] ar = {};
		Class[] arStringLLLL = {String.class, LinkedList.class, LinkedList.class};
		Object[] argvide= {};
		
		Method mToString=null;
		try {
			Method[] methods = cZoo.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("toString")) {//.getReturnType().equals(cPoint)) {
					mToString=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mToString==null) {
			noteCom=0;
			noteDec=0;
			System.out.println("   Aie... Je ne trouve pas de redefinition de la methode toString() dans Zoo");
		} else {
			if (mToString.getParameterCount()!=0) {
				noteCom=0;
				noteDec=noteDec/2;
				System.out.println("   Aie... la methode toString ne doit prendre aucun parametre");
			} else if (!(mToString.getReturnType().equals(String.class))) {
				noteCom=0;
				noteDec=noteDec/2;
				System.out.println("   Aie... la methode toString soit retourner une String");
			} else if (!(Modifier.isPublic(mToString.getModifiers()))) {
				//noteCom=0;
				noteDec=noteDec/2;
				System.out.println("   Aie... la methode toString doit etre public");
			}
		}
		if (mToString!=null) mToString.setAccessible(true);
		String[] goodRes= {
			"Aaa cage :2 cage :1 ",
			"Bbb cage :2 cage :1 ",
			"Ccc cage :2 cage :1 cage :2 cage :1 "
		};

			try {
				
				String[][] animaux= {
							{"cheeta", "singe"},{"zaius", "singe"},{"zira", "singe"},{"donald", "canard"},{"daffy", "canard"},{"leon", "canard"}
				};
				Animal ani=null;
				Objenesis objenesis = new ObjenesisStd(); 
				ObjectInstantiator instantiatorAnimal= objenesis.getInstantiatorOf(cAnimal);
				ObjectInstantiator instantiatorZoo= objenesis.getInstantiatorOf(cZoo);
				LinkedList<Animal> lAnimaux = new LinkedList<Animal>();
				for (int j=0; j<animaux.length; j++) {
					ani = (Animal)instantiatorAnimal.newInstance();
					if (fieldNom!=null) fieldNom.set(ani, animaux[j][0]);
					if (fieldEspece!=null) fieldEspece.set(ani, animaux[j][1]);
					lAnimaux.add(ani);
				}
				int[][] cages = {
						{0, 1},
						{2},
						{3,4},
						{5}
				};
				LinkedList<Cage> lCages = new LinkedList<Cage>();
				B cag=null;
				for (int i=0; i<cages.length; i++) {
				cag=new B();
					LinkedList<Animal> la = new LinkedList<Animal>();
					for (int j=0; j<cages[i].length; j++) {
						la.add(lAnimaux.get(cages[i][j]));
					}
					if (fieldLesAnimaux!=null) fieldLesAnimaux.set(cag, la);
					cag.set(la);
					lCages.add(cag);
				}
				String[] noms= {"Aaa", "Bbb", "Ccc"};
				int[][] cagesP= {
						{0,1},
						{2,3},
						{0,1,2,3}
				};
				int[][] animauxP= {
						{0,1,2},
						{3,4,5},
						{0,1,2,3,4,5}
				};
				for (int i=0; noteCom>0 && i<noms.length; i++) {
					LinkedList<Animal>lac = new LinkedList<Animal>();
					for (int j=0; j<animauxP[i].length; j++) {
						lac.add(lAnimaux.get(animauxP[i][j]));
					}
					LinkedList<Cage>lcc = new LinkedList<Cage>();
					for (int j=0; j<cagesP[i].length; j++) {
						lcc.add(lCages.get(cagesP[i][j]));
					}
					Object[] aarrgg = { noms[i], lac, lcc};
					Object z = instantiatorZoo.newInstance();//(constC1.newInstance(aarrgg));
					if (noteCom>0) {
						if (fieldNomZoo==null) {
							noteCom=0;
						} else {
							fieldNomZoo.set(z, noms[i]);
						}
					}
					if (noteCom>0) {
						if (fieldLesAnimauxZoo==null) {
							noteCom=0;
						} else {
							fieldLesAnimauxZoo.set(z, lac);
						}
					}
					if (noteCom>0) {
						if (fieldLesCages==null) {
							noteCom=0;
						} else {
							fieldLesCages.set(z, lcc);
						}
					}
					String retour ="";
					if (noteCom>0 && mToString!=null) {
						retour=(String)mToString.invoke(z,  argvide);
					}
					if (!retour.replaceAll(" ","").replaceAll("\t", "").replaceAll(",", "").equals(goodRes[i].replaceAll(" ","").replaceAll("\t", "").replaceAll(",", ""))) {
						System.out.println("Sur le Zoo \""+noms[i]+"\"  disposant de "+lcc.size()+" cages, votre methode toString() retourne : \n\""+retour+"\" au lieu de :\n\""+goodRes[i]+"\"");
						noteCom=0;
					}
				}

			} catch (Exception e) {
				System.out.println("   Exception levee lors de la creation d'un Animal avec le constructeur Animal(String,String)");
				if (e instanceof InvocationTargetException) {
					e.getCause().printStackTrace();
				} else {
					e.printStackTrace();
				}
				return noteDec;
			}
		if (noteDec+noteCom==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDec+noteCom);
	}


	
	

	
	
	
	

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static int test16ZooCreerVincennes() {
		System.out.println("   Test verifiant la declaration et le bon fonctionnement de creerVincennes() de Zoo");
		int noteDeclaration=15;
		int noteStatic=15;
		int noteComportement=70;
		int noteNom=10;
		int noteCages=30;
		int noteAnimaux=30;
		Class cZoo = Reflexion.getClass("packnp.zoo.Zoo");
		if (cZoo==null) {
			System.out.println("   Aie... je ne trouve pas la classe Zoo dans le package packnp.zoo");
			System.out.println("          vous n'avez pas encore cree cette classe ou vous l'avez cree dans un autre package.");
			return 0;
		}
		Field fieldLesAnimauxZoo=null, fieldLesCages=null, fieldNomZoo=null, fieldConst=null, fieldStatic=null, fieldOther=null;

		Field[] tf= cZoo.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if (f.getType().equals(String.class)) {
				if (fieldNomZoo==null) {
					fieldNomZoo=f;
				} else {
					fieldOther=f;
				}
			} else if (f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (f.getName().toLowerCase().contains("g") ||!f.getName().toLowerCase().contains("n")) {
					if (fieldLesCages==null) {
						fieldLesCages=f;
					} else {
						fieldOther=f;
					}
				} else {
					if (fieldLesAnimauxZoo==null) {
						fieldLesAnimauxZoo=f;
					} else {
						fieldOther=f;
					}
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldLesAnimauxZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nim")) {
							fieldLesAnimauxZoo=f;
				}
			}
		}
		if (fieldLesCages==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("ag")) {
							fieldLesCages=f;
				}
			}
		}
		if (fieldNomZoo==null) {
			for (Field f : tf) {
				if (f.getName().toLowerCase().contains("nom")||f.getName().toLowerCase().contains("name")) {
							fieldNomZoo=f;
				}
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Zoo");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Zoo ne doit comporter que les trois variables induites par le diagramme de classes.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimauxZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des animaux ");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimauxZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimauxZoo.getName());
			}
			if (!fieldLesAnimauxZoo.getType().getName().equals("java.util.List") && !(fieldLesAnimauxZoo.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimauxZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimauxZoo.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimauxZoo.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}
		if (fieldLesCages==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser la liste des cages ");
		} else {
			if (!Modifier.isPrivate(fieldLesCages.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesCages.getName());
			}
			if (!fieldLesCages.getType().getName().equals("java.util.List") && !(fieldLesCages.getType().getName().equals("java.util.LinkedList")) && !(fieldLesCages.getType().getName().equals("java.util.ArrayList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesCages.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesCages.getName().equals("lesCages")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des cages se nomme \""+fieldLesCages.getName()+"\" au lieu de \"lesCages\"");
			}
		}
		if (fieldNomZoo==null) {
			System.out.println("   Aie... La classe Zoo doit comporter une variable d'instance permettant de memoriser le nom du Zoo ");
		} else {
			if (!Modifier.isPrivate(fieldNomZoo.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNomZoo.getName());
			}
			if (!fieldNomZoo.getType().equals(String.class)) {
				System.out.println("   Aie... Votre variable \""+fieldNomZoo.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldNomZoo.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom du Zoo se nomme \""+fieldNomZoo.getName()+"\" au lieu de \"nom\"");
			}
		}
		if (fieldNomZoo!=null) fieldNomZoo.setAccessible(true);

		Class cAnimal = Reflexion.getClass("packnp.zoo.Animal");
		if (cAnimal==null) {
			System.out.println("   Aie... je ne trouve pas la classe Animal dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}
		Class cCage = Reflexion.getClass("packnp.zoo.Cage");
		if (cCage==null) {
			System.out.println("   Aie... je ne trouve pas la classe Cage dans le package packnp.zoo");
			System.out.println("          vous avez vraisemblablement deplace/supprime le fichier de cette classe");
			System.out.println("          ou vous n'avez pas correctement importe le projet.");
			return 0;
		}

		Field fieldLesAnimaux=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cCage.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			//System.out.println(">>>"+f.getType().getName()+"<<<");
			if ( f.getName().contains("nimaux") || f.getType().getName().equals("java.util.List")||f.getType().getName().equals("java.util.LinkedList")) {
				if (fieldLesAnimaux==null) {
					fieldLesAnimaux=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Cage");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe Cage ne doit comporter qu'une variable d'instance permettant de memoriser les animaux qui l'occupent.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldLesAnimaux==null) {
			System.out.println("   Aie... La classe Cage doit comporter une variable d'instance permettant de memoriser la liste des animaux qui l'occupent.");
		} else {
			if (!Modifier.isPrivate(fieldLesAnimaux.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldLesAnimaux.getName());
			}
			if (!fieldLesAnimaux.getType().getName().equals("java.util.List") && !(fieldLesAnimaux.getType().getName().equals("java.util.LinkedList"))) {
				System.out.println("   Aie... Votre variable \""+fieldLesAnimaux.getName()+"\" n'est pas du type attendu.");
			}
			if (!fieldLesAnimaux.getName().equals("lesAnimaux")) {
				System.out.println("   Aie... Vous n'avez pas respecte le nom mentionne sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant la liste des animaux se nomme \""+fieldLesAnimaux.getName()+"\" au lieu de \"lesAnimaux\"");
			}
		}

		if (fieldLesAnimaux!=null) fieldLesAnimaux.setAccessible(true);
		Field fieldEspece=null, fieldNom=null;
		fieldConst=null;
		fieldStatic=null;
		fieldOther=null;

		tf = cAnimal.getDeclaredFields();
		for (Field f : tf) {
			if (Modifier.isFinal(f.getModifiers())) {
				fieldConst=f;
			}
			if (Modifier.isStatic(f.getModifiers())) {
				fieldStatic=f;
			} 
			if ( f.getType().equals(String.class)) {
				if (f.getName().toLowerCase().contains("s") && fieldEspece==null) {
					fieldEspece=f;
				} else if (fieldNom==null) {
					fieldNom=f;
				} else {
					fieldOther=f;
				}
			} else {
				fieldOther=f;
			}
		}
		if (fieldConst!=null) {
			System.out.println("   Aie... Vous avez defini la constante "+fieldConst.getName()+" : aucune constante n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"final\" dans sa declaration");
		}
		if (fieldStatic!=null) {
			System.out.println("   Aie... Vous avez defini la variable de classe "+fieldStatic.getName()+" : aucune variable de classe n'est a definir dans la classe Animal");
			System.out.println("   --> Aucune de vos variables ne devrait avoir le modificateur \"static\" dans sa declaration");
		}
		if (fieldOther!=null) {
			System.out.println("   Aie... La classe animal ne doit comporter que deux variables d'instance, l'une memorisant l'espece, l'autre le nom.");
			System.out.println("          Vous ne devriez pas declarer la variable \""+fieldOther.getName()+"\"");
		}
		if (fieldEspece==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant l'espece de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldEspece.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldEspece.getName());
			}
			if (!fieldEspece.getName().equals("espece")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant l'espece de l'animal se nommme \""+fieldEspece.getName()+"\" au lieu de \"espece\"");
			}
		}
		if (fieldNom==null) {
			System.out.println("   Aie... La classe Animal doit comporter une variable d'instance de type chaine de caracteres memorisant le nom de l'animal.");
		} else {
			if (!Modifier.isPrivate(fieldNom.getModifiers())) {
				System.out.println("   Aie... Afin de respecter le principe d'encapsulation, toutes les variables d'instance doivent etre declaree private.");
				System.out.println("          Veuillez preciser private pour visibilite de la variable "+fieldNom.getName());
			}
			if (!fieldNom.getName().equals("nom")) {
				System.out.println("   Aie... Vous n'avez pas respecte les noms mentionnes sur le diagramme de classes.");
				System.out.println("          Votre variable memorisant le nom de l'animal se nommme \""+fieldNom.getName()+"\" au lieu de \"nom\"");
			}
		}

		if (fieldEspece!=null) fieldEspece.setAccessible(true);
		if (fieldNom!=null) fieldNom.setAccessible(true);

		Method mCreateVincennes=null;
		try {
			Method[] methods = cZoo.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals("creerVincennes")) {//.getReturnType().equals(cPoint)) {
					mCreateVincennes=m;
				} 
			}
		} catch (Exception e1) {
		} 
		if (mCreateVincennes==null) {
			noteComportement=0;
			noteDeclaration=0;
			System.out.println("   Aie... Je ne trouve pas la methode createVincennes de Zoo");
		} else {
			if (mCreateVincennes.getParameterCount()!=0) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode creerVincennes de Zoo ne doit pas comporter de parametres");
			} else if (!(mCreateVincennes.getReturnType().equals(cZoo))) {
				noteComportement=0;
				noteDeclaration=noteDeclaration/2;
				System.out.println("   Aie... la methode creerVincennes de Zoo doit avoir Zoo pour type retour");
			} 
			if (!Modifier.isStatic(mCreateVincennes.getModifiers())) {
				//noteComportement=0;
				noteStatic=0;
				System.out.println("   Aie... la methode creerVincennes de Zoo doit etre une methode de classe (pas une methode d'instance)");
			} 
		}
		if (mCreateVincennes!=null) mCreateVincennes.setAccessible(true);


		Object zoo=null;
		Objenesis objenesis = new ObjenesisStd(); 
		ObjectInstantiator instantiator= objenesis.getInstantiatorOf(cZoo);

		Object[] argssn = {};// new Object[2];
		Object zooV=null;
			try {
				zoo = instantiator.newInstance();
				if (noteComportement>0) {
					if ( mCreateVincennes!=null) {
						zooV=mCreateVincennes.invoke(zoo,  argssn);
						if (zooV==null) {
							System.out.println("   Aie... votre methode creerVincennes() retourne null");
							noteComportement=0;
						} else {
							if (fieldNomZoo!=null) {
								String n = (String)(fieldNomZoo.get(zooV));
								if (!n.equals("Vincennes")) {
									System.out.println("   Aie... le zoo retourne porte le nom \""+n+"\" au lieu de \"Vincennes\"");
									noteNom=0;
								}
							} else {
								noteNom=0;
							}
							if (fieldLesCages!=null) {
								fieldLesCages.setAccessible(true);
								List<Cage> lc = (List<Cage>)fieldLesCages.get(zooV);
								if (lc.size()!=2) {
									System.out.println("   Aie... le zoo retourne comporte "+lc.size()+" cages au lieu de 2");
									noteCages=0;
									noteAnimaux=0;
								} else {
									if (fieldLesAnimaux!=null) {
										List<Animal> la =(List<Animal>) fieldLesAnimaux.get(lc.get(0));
										if (la==null || la.size()!=1) {
											System.out.println("   Aie... la premiere cage ne comporte pas un animal");
											noteAnimaux=0;
										} else {
											if (fieldNom!=null && fieldEspece!=null) {
												String n1 =(String)fieldNom.get(((Animal)(la.get(0))));
												if (!n1.toLowerCase().equals("simba")) {
													System.out.println("   Aie... l'animal dans la premiere cage s'appelle \""+n1+"\" au lieu de \"simba\"");
													noteAnimaux=0;
												}
												String e1 =(String)fieldEspece.get(((Animal)(la.get(0))));
												if (noteAnimaux>0 && !e1.toLowerCase().equals("lion")) {
													System.out.println("   Aie... l'animal dans la premiere cage est de l'espece \""+e1+"\" au lieu de \"lion\"");
													noteAnimaux=0;
												}
											} else {
												noteAnimaux=0;
											}
										}
										la =(List<Animal>) fieldLesAnimaux.get(lc.get(1));
										if (la==null || la.size()!=1) {
											System.out.println("   Aie... la deuxieme cage ne comporte pas un animal");
											noteAnimaux=0;
										} else {
											if (fieldNom!=null && fieldEspece!=null) {
												String n1 =(String)fieldNom.get(((Animal)(la.get(0))));
												if (!n1.toLowerCase().equals("mickey")) {
													System.out.println("   Aie... l'animal dans la deuxieme cage s'appelle \""+n1+"\" au lieu de \"mickey\"");
													noteAnimaux=0;
												}
												String e1 =(String)fieldEspece.get(((Animal)(la.get(0))));
												if (noteAnimaux>0 && !e1.toLowerCase().equals("souris")) {
													System.out.println("   Aie... l'animal dans la deuxieme cage est de l'espece \""+e1+"\" au lieu de \"souris\"");
													noteAnimaux=0;
												}
											} else {
												noteAnimaux=0;
											}
										}
									} else {
										noteAnimaux=0;
									}
								}
							}else {
								noteCages=0;
							}
							
						}
						
					} else {
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
			if (noteComportement>0) {
				noteComportement=noteNom+noteCages+noteAnimaux;
			}

			if (noteDeclaration==0) {
				noteStatic=0;
			}
		if (noteDeclaration+noteComportement+noteStatic==100) {
			System.out.println("   Ok. Votre code passe le test.");
		}
		return (noteDeclaration+noteComportement+noteStatic);
	}
}
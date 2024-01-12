package packnp.tests.tools;
 // Version :  14/11/2019
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
public class Reflexion {
	public static int DELAI = 250; // Temps d'attente entre les affichages lors d'une levee d'exception
 
	@SuppressWarnings("rawtypes")
	public static Class getClass(String nomClasse) {
		Class t;
		try  {
			t = Class.forName(nomClasse);
			return t;
		}  catch (ClassNotFoundException e) {
			return null;
		}
	}
 
	@SuppressWarnings({ "rawtypes" })
	public static boolean isAbstract(Class c) {
		return c!=null && Modifier.isAbstract(c.getModifiers());
	}
 
	@SuppressWarnings({ "rawtypes" })
	public static boolean implementsInterface(Class c, Class interfac) {
		return c!=null
			&& interfac!=null &&
			Arrays.asList( c.getInterfaces()).contains(interfac);
	}
 
	@SuppressWarnings("rawtypes")
	public static boolean extendsClass(Class c, Class superClasse) {
		return (c!=null && superClasse!=null && (c.getSuperclass().equals(superClasse)));
	}
 
	@SuppressWarnings("rawtypes")
	public static Class[] stringToClass(String[] argsString) {
		if (argsString==null) {
			return null;
		}
		Class[] argsClass = new Class[ argsString.length];
		for (int i=0; i<argsString.length; i++) {
			switch (argsString[i]) {
			case "int" : argsClass[i]=int.class;break;
			case "double":argsClass[i]=double.class; break;
			case "long" : argsClass[i]=long.class; break;
			case "char" : argsClass[i]=char.class; break;
			case "boolean":argsClass[i]=boolean.class;break;
			case "byte" : argsClass[i]=byte.class;break;
			case "short" : argsClass[i]=short.class;break;
			case "float":argsClass[i]=float.class;break;
			case "String":argsClass[i]=String.class;break;
			default : argsClass[i]=getClass(argsString[i]);
			}
		}
		return argsClass;
	}
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Constructor getConstructor(Class c, Class[] args) {
		Constructor cons=null;
		try {
			cons= c.getConstructor(args);
		} catch(Exception e) {};
		return cons;
	}
	
	@SuppressWarnings("rawtypes")
	public static Constructor getConstructor(Class c, String[] argsString) {
		return getConstructor( c, stringToClass(argsString));
	}
 
	// Y compris methodes heritees
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getMethod(Class c, String methode, Class[] args) {
		try {
			return c.getMethod(methode, args);
		} catch (Exception nonCapturee) {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Method getMethod(Class c, String methode,String[] argsString) {
		return getMethod(c, methode,  stringToClass(argsString));
	}
	
	// seulement celles declarees dans la classe
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getDeclaredMethod(Class c, String methode, Class[] args) {
		try {
			return c.getDeclaredMethod(methode, args);
		} catch (Exception nonCapturee) {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Method getDeclaredMethod(Class c, String methode,String[] argsString) {
		return getDeclaredMethod(c, methode,  stringToClass(argsString));
	}
	
	@SuppressWarnings("rawtypes")
	public static int nbDeclaredFields(Class c) {
		return c.getDeclaredFields().length;
	}
	
	@SuppressWarnings("rawtypes")
	public static int nbDeclaredFieldsOfType(Class c, Class typ) {
		Field[] fields = c.getDeclaredFields();
		int nb=0;
		for (Field f : fields) {
			if (f.getType().equals(typ)) {
				nb++;
			}
		}
		return nb;
	}
 
	@SuppressWarnings("rawtypes")
	public static boolean fieldsDeclaredPrivate(Class c) {
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			if (!Modifier.isPrivate(f.getModifiers())) {
				return false;
			}
		}
		return true;
	}
 
	@SuppressWarnings("rawtypes")
	public static boolean hasFieldOfType(Class c, Class type ) {
		return fieldsOfType(c, type).size()>0;
		/*
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			if (f.getType().equals(type)) {
				return true;
			}
		}
		return false;*/
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Field> fieldsOfType(Class c, Class type) {
		ArrayList<Field> res = new ArrayList<Field>();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			if (f.getType().equals(type)) {
				res.add(f);
			}
		}
		return res;
	}
	public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    fields.addAll(Arrays.asList(type.getDeclaredFields()));
	
	    if (type.getSuperclass() != null) {
	        getAllFields(fields, type.getSuperclass());
	    }
	
	    return fields;
	}
	public static List<Field> getAllFields(Class<?> type) {
	    return getAllFields(new ArrayList<Field>(), type);
	}
 
	
	//----------------------------------------------
	// A PARTIR DU CODE
	//----------------------------------------------
	
	public static String remove(String code, String mot) {
		String res = code;
		while (res.contains(mot)) {
			res=res.replace(mot,"");
		}
		return res;
	}
	
	public static String removeStartingChars(String code, char c) {
		String res= code;
		while (res!=null && res.length()>0 && res.charAt(0)==c) {
			res=res.substring(1);
		}
		return res;
	}
	
	public static String startingId(String code) {
		String res= code;
		String delimiters = " \t,;(){}";
		while (res.length()>0 && delimiters.contains(""+res.charAt(0))) {
			res=res.substring(1);
		}
		int end=0;
		while (res!=null && res.length()>0
				&& end<res.length()
				&& !delimiters.contains(""+res.charAt(end))) {
			end++;
		}
		return res.substring(0, end);
	}
	
	public static String withoutStartingId(String code) {
		String res= code;
		String delimiters = " \t,;(){}"+(char)10+(char)13;
		while (res.length()>0 && delimiters.contains(""+res.charAt(0))) {
			res=res.substring(1);
		}
		int end=0;
		while (res!=null && res.length()>0 && end<res.length() && !delimiters.contains(""+res.charAt(end))) {
			end++;
		}
		return res.substring(end, res.length());
	}
	
	public static String constructeurNomFic(String nomFic, String nomClasse, String[] args) {
		String code = sansCommentaires(nomFic);
		return constructeur(code, nomClasse, args);
	}
	
	public static List<String> constructeurNomsParametresNomFic(String nomFic, String nomClasse, String[] args) {
		String code = sansCommentaires(nomFic);
		return constructeurNomsParametres(code, nomClasse, args);
	}
	public static String sansEspacesAvantParentheses(String code) {
		String res = code;
		while (res.indexOf(" (")>=0) {
			res = res.replace(" (","(");
		}
		while (res.indexOf("( ")>=0) {
			res = res.replace("( ","(");
		}
		while (res.indexOf("\t(")>=0) {
			res = res.replace("\t(","(");
		}
		return res;
	}
	
	public static String constructeur(String code, String nomClasse, String[] args) {
		code = sansEspacesAvantParentheses(code);
		int index = code.indexOf(nomClasse+"(");
	
		if (index<0) {
			index = code.indexOf(nomClasse+" (");
		}
		if (index<0) {
			return "";
		} else {
			code = code.substring(index);
			code = withoutStartingId(code);
			if (code.length()>0) code = code.substring(1);
			int p = 0;
			while (	p<args.length
					&& code.charAt(0)!=')') {
				if (startingId(code).equals(args[p])) {
					code = withoutStartingId( withoutStartingId(code));
					p++;
				} else {
					return constructeur(code, nomClasse, args);
				}
			}
			code =removeStartingChars(code,' ');
			code = removeStartingChars(code, '\t');
			if (p<args.length || code.length()==0 || code.charAt(0)!=')') {
				return  constructeur(code, nomClasse, args);
			}
			int i= 0;
			while (i<code.length() && code.charAt(i)!='{') {
				i++;
			}
			i++;
			int nbOuvertes=1;
			while (nbOuvertes>0 && i<code.length()) {
				if (code.charAt(i)=='{') {
					nbOuvertes++;
				}
				if (code.charAt(i)=='}') {
					nbOuvertes--;
				}
				i++;
			}
			if (i<code.length()) {
				return code.substring(code.indexOf("{"), i);
			} else {
				return "";
			}
		}
	}
	public static List<String> constructeurNomsParametres(String code, String nomClasse, String[] args) {
		List<String> res= new ArrayList<String>();
		code = sansEspacesAvantParentheses(code);
		int index = code.indexOf(nomClasse+"(");
	
		if (index<0) {
			index = code.indexOf(nomClasse+" (");
		}
		if (index<0) {
			return res;
		} else {
			code = code.substring(index);
			code = withoutStartingId(code);
			if (code.length()>0) code = code.substring(1);
			int p = 0;
			while (	p<args.length
					&& code.charAt(0)!=')') {
				if (startingId(code).equals(args[p])) {
					res.add(startingId(withoutStartingId(code)));
					code = withoutStartingId( withoutStartingId(code));
					p++;
				} else {
					return constructeurNomsParametres(code, nomClasse, args);
				}
			}
			code =removeStartingChars(code,' ');
			code = removeStartingChars(code, '\t');
			if (p<args.length || code.length()==0 || code.charAt(0)!=')') {
				return  constructeurNomsParametres(code, nomClasse, args);
			}
			return res;
	/*		int i= 0;
			while (i<code.length() && code.charAt(i)!='{') {
				i++;
			}
			i++;
			int nbOuvertes=1;
			while (nbOuvertes>0 && i<code.length()) {
				if (code.charAt(i)=='{') {
					nbOuvertes++;
				}
				if (code.charAt(i)=='}') {
					nbOuvertes--;
				}
				i++;
			}
			if (i<code.length()) {
				return code.substring(code.indexOf("{"), i);
			} else {
				return "";
			}*/
		}
	}
	private static String ligne(String code, int pos) {
		int debut = pos;
		int fin = pos;
		while (debut>0 && code.charAt(debut)!='\n') {
			debut--;
		}
		while (fin<code.length() && code.charAt(fin)!='\n') {
			fin++;
		}
		return code.substring(debut, fin);
	
	}
	public static String methode(String code, String nomMethode) {
		int index = code.indexOf(nomMethode);
		String ligne="";//*
		do {
			ligne = ligne(code,index);
		    if (!ligne.contains("private") && !ligne.contains("public")) {
		    	code = code.substring(index+1);
		    	index = code.indexOf(nomMethode);
		    }
		} while (index>0 && (!ligne.contains("private") && !ligne.contains("public")));//*/
		if (index<0) {
			return "";
		} else {
			int i= index+1;
			while (i<code.length() && code.charAt(i)!='{') {
				i++;
			}
			i++;
			int nbOuvertes=1;
			while (nbOuvertes>0 && i<code.length()) {
				if (code.charAt(i)=='{') {
					nbOuvertes++;
				}
				if (code.charAt(i)=='}') {
					nbOuvertes--;
				}
				i++;
			}
			if (i<code.length()) {
				return code.substring(index, i);
			} else {
				return "";
			}
		}
	}
	public static List<String> lignesSansCommentaires(String nomFic) {
		List<String> l=new ArrayList<String>();
		List<String> m=new ArrayList<String>();
		try {
			FileReader fr = new FileReader(nomFic);
			BufferedReader br = new BufferedReader( fr );
			String s;
			do {
				s = br.readLine();
				if (s!=null) {
					l.add(s);
				}
			} while (s!=null);
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		int i=0;
		while (i<l.size()) {
			int debut = l.get(i).indexOf("/*");
			int fin = l.get(i).indexOf("*/");
			if (debut>=0) {
				int iCL = l.get(i).indexOf("//");
				if (iCL>=0 && iCL<debut) {
					m.add(l.get(i).substring(0, iCL));
				} else if (fin>=0) {
					m.add(l.get(i).substring(0, debut)+""+ l.get(i).substring(fin+2));
				} else {
					m.add(l.get(i).substring(0, debut));
					i++;
					while (i<l.size() && l.get(i).indexOf("*/")<0) {
						i++;
					}
					fin = l.get(i).indexOf("*/");
					m.add(l.get(i).substring(fin+2));
				}
			} else if ( l.get(i).indexOf("//")>=0) {
				m.add(l.get(i).substring(0, l.get(i).indexOf("//")));
			}	else {
				m.add(l.get(i));
			}
			i++;
		}
		return m;
	}
	public static String sansCommentaires(String nomFic) {
		return reunirLignes( lignesSansCommentaires(nomFic));
	}
	public static String reunirLignes(List<String> lignes) {
		String res="";
		for (String r : lignes) {
			res=res+r+"\n";
		}
		return res;
	}
 	private static String removeSpaceBefore(String s) {
  		int i=0;
  		String res="";
  		while (i<s.length()-1) {
  			if (!(s.charAt(i)==' '
  					&& (s.charAt(i+1)=='.' ||s.charAt(i+1)=='{' ||s.charAt(i+1)=='(' ||s.charAt(i+1)=='}' ||s.charAt(i+1)==')'  ||s.charAt(i+1)=='[' ||s.charAt(i+1)==']'))) {
  				res=res+s.charAt(i);
  			}
  			i++;
  		}
  		return res+s.charAt(s.length()-1);
  	}
 	private static String removeSpaceAfter(String s) {
  		int i=1;
  		String res=s.length()>0 ? ""+s.charAt(0):"";
  		while (i<s.length()) {
  			if (!(s.charAt(i)==' '
  					&& (s.charAt(i-1)=='.' ||s.charAt(i-1)=='{' ||s.charAt(i-1)=='(' ||s.charAt(i-1)=='}' ||s.charAt(i-1)==')'  ||s.charAt(i-1)=='[' ||s.charAt(i-1)==']'))) {
  				res=res+s.charAt(i);
  			}
  			i++;
  		}
  		return res;
  	}
    public static String normalise(String s) {
    	String res = s.replaceAll(""+(char)13, "").replaceAll(""+(char)10, "").replaceAll(""+(char)9, "");
    	while (res.contains("  ")) {
    		res=res.replaceAll("  ",  " ");
    	}
    		res=removeSpaceBefore(res);
    		res=removeSpaceAfter(res);
    	return res;
    }
 
	public static List<String> getMots(String s) {
		List<String> l = new ArrayList<String>();
		String delimiteurs = " ,.;:/?%*+=-<>\\_()[]{}&"+(char)13+"|"+(char)10+"\t";
		String mot="";
		for (int i=0; i<s.length(); i++) {
			if (delimiteurs.contains(""+s.charAt(i))) {
				if (!mot.equals("")) {
					l.add(mot);
					mot="";
				}
			} else {
				mot=mot+s.charAt(i);
			}
		}
		return l;
 
	}
 
	@SuppressWarnings("rawtypes")
	public static boolean estUneVariableLocale(String nomFic, Class c, String methode, String var) {
		//System.out.println(" estUneVariable("+nomFic+" c="+c+" met="+methode+" var="+var+")");
		String met = methode(sansEspacesAvantParentheses(sansCommentaires(nomFic)),methode);
		 
		//String met = methode(sansCommentaires(nomFic),methode);
		//System.out.println("code------\n"+met+"\n-----");
		List<String> mots = getMots(met);
		int pos = mots.indexOf(var);
		if (pos>0) {
			String[] primitifs= {"int", "char", "boolean", "float", "long", "double"};
			List<String> lprimitifs = Arrays.asList(primitifs);
			//System.out.println("   mot precedent ="+mots.get(pos-1)+"<<<");
			if (lprimitifs.contains(mots.get(pos-1))) {
				return true;
			} else {
				//System.out.println(c.getPackage().getName());
				return getClass(mots.get(pos-1))!=null ||  getClass(c.getPackage().getName()+"."+mots.get(pos-1))!=null;
			}
		} else {
			return false;
		}
	}
	@SuppressWarnings("rawtypes")
	public static boolean respecteDeveloppementEnCouche(String nomFic, Class c, String methode) {
		String met = methode(sansEspacesAvantParentheses(sansCommentaires(nomFic)),methode);
 
		//System.out.println(" ---- avant retrait des methodes : "+met);
		Method[] methodes = c.getDeclaredMethods();
		String sans = met;
		for (Method m : methodes) {
			sans = remove(sans, m.getName());
		}
		//System.out.println(" ---- apres retrait des methodes : "+sans);
		boolean couche = true;
		Field[] fields = c.getDeclaredFields();
 
		int i=0;
		List<String> mots = getMots(sans);
		while (couche && i<fields.length) {
			if (mots.contains(fields[i].getName())){
			//	System.out.println(" mot trouve ="+fields[i].getName()+"<<");
				if (!estUneVariableLocale(nomFic, c, methode,fields[i].getName() )) {
					couche=false;
				}
			}
			i++;
		}
		return couche;
	}
 
	public static void afficheThrowable( Throwable e, String test) {
		System.out.println(">>>>>> AIE..."+test+" lance "+e.toString());
		try {Thread.sleep(DELAI);} catch (Exception ex) {};
		e.printStackTrace();
		try {Thread.sleep(DELAI);} catch (Exception ex) {};
		if (e.getCause()!=null) {
			System.out.println("Cause :");
			e.getCause().printStackTrace();
		}
		try {Thread.sleep(DELAI);} catch (Exception ex) {};
		System.out.println("<<<<<<<<<<<<<<<<<<");
	}
 
}

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Exportateur {

	public static String getDossierProjet() { 	               	 	
		String s = new File(".").getAbsolutePath(); 	               	 	
		return s.substring(0, s.length()-2); 	               	 	
	}

	public static List<File> getAllFiles(File f) { 	               	 	
		List<File> res = new ArrayList<File>(); 	               	 	
		if (f.exists()) { 	               	 	
			if (f.isFile()) { 	               	 	
				res.add(f); 	               	 	
				return res; 	               	 	
			} else { 	               	 	
				for (File fils : f.listFiles()) { 	               	 	
					res.addAll(getAllFiles(fils)); 	               	 	
				} 	               	 	
				return res; 	               	 	
			} 	               	 	
		} else { 	               	 	
			return res; 	               	 	
		} 	               	 	
	} 	               	 	


	public static void addFolder(String zipFileName, String folderToAdd) { 	               	 	

		try { 	               	 	
			// Initiate ZipFile object with the path/name of the zip file. 	               	 	
			ZipFile zipFile = new ZipFile(zipFileName);//"c:\\ZipTest\\AddFolder.zip"); 	               	 	

			// Folder to add 	               	 	
			//String folderToAdd = "c:\\FolderToAdd"; 	               	 	

			// Initiate Zip Parameters which define various properties such 	               	 	
			// as compression method, etc. 	               	 	
			ZipParameters parameters = new ZipParameters(); 	               	 	

			// set compression method to store compression 	               	 	
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); 	               	 	

			// Set the compression level 	               	 	
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); 	               	 	

			// Add folder to the zip file 	               	 	
			zipFile.addFolder(folderToAdd, parameters); 	               	 	

		} catch (ZipException e) { 	               	 	
			e.printStackTrace(); 	               	 	
		} 	               	 	
	} 	               	 	



	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// Verifier si il y a des fichiers vides
		System.out.println("----- Exportateur -----");
		System.out.print("- Recherche d'eventuels fichiers java vides : ");
		List<File> tous = getAllFiles(new File(getDossierProjet())); 	               	 	
		List<File> javaVide = new ArrayList<File>(); 	               	 	
		String mes = "<html>ATTENTION ! Les fichiers java ci-dessous sont vides :"; 	               	 	
		for (File f : tous) { 	               	 	
			if (f.getName().endsWith("java") && f.length()==0) { 	               	 	
				javaVide.add(f); 	               	 	
				mes=mes+"<br>"+f.getAbsolutePath(); 	               	 	
				//System.out.println(f.getAbsolutePath()+" "+f.length()); 	               	 	
			} 	               	 	
		} 	               	 	
		mes=mes+"</html>"; 	               	 	
		if (javaVide.size()>0) {
			System.out.println(javaVide.size()+" fichiers java vide trouves");
			JOptionPane.showMessageDialog(null, mes); 	               	 	
		} else {
			System.out.println(" aucun fichier java vide dans le projet");
		}
		String dossierUtilisateur = System.getProperty("user.home"); 	               	 	
		Calendar calendrier = Calendar.getInstance(new Locale("FR","fr") ); 	               	 	
		// Pour eviter le warning nous pourrions remplacer la ligne ci-dessus par : 
		//     Calendar calendrier = Calendar.getInstance(Locale.of("FR",  "fr")); 
		// Mais cela entraine une erreur du compilateur si la version de java 
		// installee n'est pas la toute derniere.
		String hour = ""+calendrier.get(Calendar.MINUTE); 	               	 	
		if (hour.length()<2) { 	               	 	
			hour="0"+hour; 	               	 	
		} 	               	 	
		hour=""+calendrier.get(Calendar.HOUR_OF_DAY)+hour; 	               	 	
		if (hour.length()<4) { 	               	 	
			hour="0"+hour; 	               	 	
		} 	               	 	
		String nomFichierParDefaut = new File(System.getProperty("user.dir")).getName()+hour+".zip"; 	               	 	
		JFileChooser chooser = new JFileChooser(dossierUtilisateur); 	               	 	
		FileNameExtensionFilter filter = new FileNameExtensionFilter( 	               	 	
				"Zip archive files", "zip", "Zip", "ZIP"); 	               	 	
		chooser.setFileFilter(filter); 	               	 	
		chooser.setName("Indiquez le nom et l'emplacement de l'archive a creer"); 	               	 	
		chooser.setDialogTitle("Indiquez le nom et l'emplacement de l'archive a creer"); 	               	 	
		chooser.setSelectedFile(new File(dossierUtilisateur+File.separator+nomFichierParDefaut)); 	   
		System.out.println("- Veuillez indiquer le nom et l'emplacement de l'archive a creer");
		int returnVal = chooser.showSaveDialog(null);//parent); 	               	 	
		if(returnVal == JFileChooser.APPROVE_OPTION) { 	               	 	
			//System.out.println("You chose to open this file: " + 	               	 	
			//		chooser.getSelectedFile().getName()); 	               	 	

			Tools.save();   
			File exp = chooser.getSelectedFile();//new File("export.zip"); 	               	 	
			if (exp.exists()) { 	               	 	
				exp.delete(); 	               	 	
			} 	               	 	
			addFolder(exp.getAbsolutePath(), getDossierProjet());//"export.zip", ".h"); 	
			System.out.println("- Export cree : "+exp.getAbsolutePath());
			JOptionPane.showMessageDialog(null, "<html>Apres avoir depose le fichier "+exp.getName()+" sur Moodle, <br>"
					+" <FONT style=\"color:rgb(255,0,0);\">VERIFIEZ VOTRE DEPOT</FONT>" + 
					" en retournant sur le lien de depot, en telechargeant l'archive deposee, <br>"+
					" puis en consultant les fichiers java du dossier src de l'archive afin de verifier qu'ils correspondent<br>"+
					" bien a ce que vous avez produit</html>"); 	               	 	

		} else {
			System.out.println("Annulation par l'utilisateur : aucun export effectue");
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("----- Fin -----");
 	}
}

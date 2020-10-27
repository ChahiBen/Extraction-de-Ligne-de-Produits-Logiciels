package analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import obe.IArtifact;
import obe.OBEAccess;
import obe.OBEAttribute;
import obe.OBEClass;
import obe.OBEInterface;
import obe.OBEInvocation;
import obe.OBELocalVariable;
import obe.OBEMethod;
import obe.OBEPackage;
import spoon.Launcher;
import spoon.MavenLauncher;
import spoon.compiler.Environment;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class SpoonMain {
	static CtModel model = null;

	public static void main(String[] args) {
		
		System.out.println("Begin Analysis");

		// Parsing arguments using JCommander
		Arguments arguments = new Arguments();
		boolean isParsed = arguments.parseArguments(args);

		// if there was a problem parsing the arguments then the program is terminated.
		if(!isParsed)
			return;
		
		// Parsed Arguments
		String experiment_source_code = arguments.getSource();
		String experiment_output_filepath = arguments.getTarget();
		
		// Load project (APP_SOURCE only, no TEST_SOURCE for now)
		Launcher launcher = null;
		if(arguments.isMavenProject() ) {
			launcher = new MavenLauncher(experiment_source_code, MavenLauncher.SOURCE_TYPE.APP_SOURCE); // requires M2_HOME environment variable
		}else {
			launcher = new Launcher();
			launcher.addInputResource(experiment_source_code + "/sources");
		}
		
		// Setting the environment for Spoon
		Environment environment = launcher.getEnvironment();
		environment.setCommentEnabled(true); // represent the comments from the source code in the AST
		environment.setAutoImports(true); // add the imports dynamically based on the typeReferences inside the AST nodes.
//		environment.setComplianceLevel(0); // sets the java compliance level.
		
		System.out.println("Run Launcher and fetch model.");
		launcher.run(); // creates model of project
		model = launcher.getModel(); // returns the model of the project

		System.out.println("***");
		List<IArtifact> listArtifact = getIArtifact();
		
		write(listArtifact);
		
		
		
		
		
		System.out.println("all done.");
	}
	
	public static void write(List<IArtifact> list) {
        final String chemin = "C:\\Users\\Chahinez\\Desktop\\306\\TP_rca/Produit1.txt";
       final File fichier =new File(chemin); 
       try {
           // Creation du fichier
           fichier.createNewFile();
           // creation d'un writer (un écrivain)
           final FileWriter writer = new FileWriter(fichier);
           try {
        	   for(IArtifact maListe : list) {
        		   writer.write(maListe.getName() + "\n");
        	   }
               
           } finally {
               // quoiqu'il arrive, on ferme le fichier
               writer.close();
           }
       } catch (Exception e) {
           System.out.println("Impossible de creer le fichier");
       }
   }
	
	public static List<IArtifact> getIArtifact(){
		List<String> groupsClasse = new ArrayList<String>();
		List<String> groupsMethode = new ArrayList<String>();
		List<String> groupsAttribut = new ArrayList<String>();
		List<IArtifact> listArtifact = new ArrayList<IArtifact>();
		List<CtPackage> packag = model.getElements(new TypeFilter<CtPackage>(CtPackage.class));
		for(CtPackage newPack : packag) {// récupéré touts les packages :
			OBEPackage newPackage = new OBEPackage("Package : "+newPack.getSimpleName());
			for(CtType<?> type : model.getAllTypes()) {// récupéré toutes les classes :
				if (type instanceof CtClass) {
					OBEClass newClasse = new OBEClass("Class : "+type.getSimpleName());
					List<CtField<?>> listField = type.getFields();
					for (CtField<?> field : listField) {// récupéré touts les attributs :
						OBEAttribute newAttribute = new OBEAttribute("Attribute : "+type.getSimpleName()+"."+field.getSimpleName());
						newClasse.getaBelongToClass().add(newAttribute);
						listArtifact.add(newAttribute);
					}
					for(CtMethod<?> newMethod : type.getMethods()) {// récupéré toutes les méthodes :
						OBEMethod newM = new OBEMethod("Method : "+type.getSimpleName()+"."+newMethod.getSimpleName());
						List<CtInvocation> listInvocation  = newMethod.getElements(new TypeFilter<CtInvocation>(CtInvocation.class));
						for(CtInvocation invok : listInvocation) {//Récupéré toutes les invocations :
							OBEInvocation newInvocation = new OBEInvocation("Invocation : "+getSimpleName(invok));
							newM.getInvocations().add(newInvocation);
						}
						List<CtLocalVariable> ListLV = newMethod.getElements(new TypeFilter<CtLocalVariable>(CtLocalVariable.class));
						for(CtLocalVariable lv : ListLV) {//Récupéré toutes les variables locales :
							OBELocalVariable newLV = new OBELocalVariable("Local variable : "+lv.getSimpleName());
							newM.getLocalVariables().add(newLV);
						}
						List<CtReference> referenceList = newMethod.getElements(new TypeFilter<CtReference>(CtReference.class));
						for (CtReference reference : referenceList) {
							for (CtField<?> field : listField) {
								if (reference.getSimpleName().equals(field.getSimpleName())) {
									for (OBEAttribute newAttribute : newClasse.getaBelongToClass()) {
										if (("Attribute : " + type.getSimpleName() +"."+ reference.getSimpleName() ).equals(newAttribute.getName())) {
											OBEAccess newAccess = new OBEAccess(reference.getSimpleName(), newAttribute);
											//System.out.println(" \t\t\t- Accès à l'attribut " +reference.getSimpleName());
											newM.getAccesses().add(newAccess);
											break;
										}
									}
								}
							}
						}
						newClasse.getMethod().add(newM);
						listArtifact.add(newM);
						
						groupsAttribut = readFile("C:\\Users\\Chahinez\\Desktop\\306\\TP_rca\\artefact\\C6");
						groupsAttribut = getDepMethodAttribute(newM,groupsAttribut);
						
						for(String group : groupsAttribut) {
							System.out.println(group);
						}
						//System.out.println("***");
						
					}
					newPackage.getListClass().add(newClasse);
					listArtifact.add(newClasse);
					
					//look for dependences :
					groupsClasse = readFile("C:\\Users\\Chahinez\\Desktop\\306\\TP_rca\\artefact\\C6");
					groupsClasse = getDepClassMethod(newClasse,groupsClasse);
					
					groupsMethode = readFile("C:\\Users\\Chahinez\\Desktop\\306\\TP_rca\\artefact\\C6");
					groupsMethode = getDepClassAttribute(newClasse,groupsMethode);
					
					
					for(String group : groupsClasse) {
						System.out.println(group);
					}
					for(String group : groupsMethode) {
						System.out.println(group);
					}
					//System.out.println("***");
				}
				if (type instanceof CtInterface) {
					OBEInterface newClasse = new OBEInterface("Interface : "+type.getSimpleName());
					newPackage.getListInterface().add(newClasse);
				}
			}
		}
		
		return listArtifact;
	}
	
	public static List<String> readFile(String fileName) {
		List<String> list = new ArrayList<String>();
		try (FileReader reader = new FileReader(fileName);
	             BufferedReader br = new BufferedReader(reader)) {
	            // read line by line
	            String line;
	            while ((line = br.readLine()) != null) {
	            	list.add(line);
	                //System.out.println(line);
	            }

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
		return list;
	}
	
	//  dépendences entre une classe et ses méthodes
	public static List<String> getDepClassMethod(OBEClass newClass, List<String> group) {
		  List<String> list = new ArrayList<String>();
		  for (String artefactI : group) {
		      if (newClass.getName().equals(artefactI)) {
		          for (OBEMethod newMethod : newClass.getMethod()) {
		              for (String artefactJ : group) {
		                  if (newMethod.getName().equals(artefactJ)) {
		                	  list.add("\"" + newMethod.getName()+ "\" -> \"" +newClass.getName()+ "\"");
		                  }
		              }
		          }
		      }
		  }
		  return list.stream().distinct().collect(Collectors.toList());
	}
	
	// dépendences entre une classe et ses méthodes
	public static List<String> getDepClassAttribute(OBEClass newClass, List<String> group) {
	    List<String> list = new ArrayList<String>();
	    for (String artefactI : group) {
	        if (newClass.getName().equals(artefactI)) {
	            for (OBEAttribute newattribute : newClass.getaBelongToClass()) {
	                for (String artefactJ : group) {
	                    if (newattribute.getName().equals(artefactJ)) {
	                    	list.add("\"" + newattribute.getName()+ "\" -> \"" +newClass.getName()+ "\"");
	                    }
	                }
	            }
	        }
	    }
	    return list.stream().distinct().collect(Collectors.toList());
	}
	
	// dépendences entre méthodes et attributs
		public static List<String> getDepMethodAttribute(OBEMethod newMethod, List<String> group) {
		    List<String> list = new ArrayList<String>();
		    for (String artefactI : group) {
		        if (newMethod.getName().equals(artefactI)) {
		            for (OBEAccess newaccess : newMethod.getAccesses()) {
		            	//System.out.println("new access :"+ newaccess);
		                for (String artefactJ : group) {
		                    if (newaccess.getAttribute().getName().equals(artefactJ)) {
		                    	list.add("\"" + newaccess.getAttribute().getName()+ "\" -> \"" +newMethod.getName()+ "\"");
		                    }
		                }
		            }
		        }
		    }
		    return list.stream().distinct().collect(Collectors.toList());
		}
		
	public static String getSimpleName(CtInvocation ctInvocation) {
		String pattern = "[a-z|A-Z]+\\(";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(ctInvocation.toString());
		String ctInvocationName = "";
		if (matcher.find()) {
			ctInvocationName = matcher.group(0);
			if (!(ctInvocationName == null || ctInvocationName.length() == 0)) {
				ctInvocationName = ctInvocationName.substring(0, ctInvocationName.length() - 1);
			}
		}
		return ctInvocationName;
	}
}

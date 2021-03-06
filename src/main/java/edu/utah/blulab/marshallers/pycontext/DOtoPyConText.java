package edu.utah.blulab.marshallers.pycontext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


import edu.stanford.nlp.util.Triple;
import edu.utah.blulab.domainontology.*;

public class DOtoPyConText {
	

	public static void main(String[] args)  {
		System.out.println("Starting test app.");


		DOtoPyConText parser = new DOtoPyConText();
		try {
			File ontFile = new File(args[0]);
			String domainName = ontFile.getName().substring(0, ontFile.getName().indexOf("."));
			File modifiersFile = new File(ontFile.getParentFile().getPath() + "/" + domainName + "_modifiers.tsv");
            File targetsFile = new File(ontFile.getParentFile().getPath() + "/" + domainName + "_targets.tsv");
            File rulesFile = new File(ontFile.getParentFile().getPath() + "/" + domainName + "_rules.tsv");

            //System.out.println(modifiersFile.toString());

			if(!modifiersFile.exists()){
				modifiersFile.createNewFile();
			}
            if(!targetsFile.exists()){
                targetsFile.createNewFile();
            }
            if(!modifiersFile.exists()){
                rulesFile.createNewFile();
            }

			parser.createPyConTextFiles(ontFile, modifiersFile, targetsFile, rulesFile);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void createPyConTextFiles(File ontology, File modifierFile, File targetFile, File ruleFile) throws Exception{
	    DomainOntology domain = new DomainOntology(ontology.getPath(), false);

        //write modifier file
        System.out.println("Writing " + modifierFile.getName() + "...");
        BufferedWriter bw = new BufferedWriter(new FileWriter(modifierFile));
        ArrayList<Modifier> modDictionary = domain.createModifierDictionary();

        bw.write("literal\t category\t regex\t rule");


        for(Modifier modifier : modDictionary){
            HashMap<String, String> regexTuples = new HashMap<String, String>();
            ArrayList<Triple<String, String, String>> tripleList = new ArrayList<Triple<String, String, String>>();

            ArrayList<LexicalItem> variants = modifier.getItems();
            for(LexicalItem lexicalItem : variants){
                ArrayList<String> lexicalItemLabels = new ArrayList<String>();
                lexicalItemLabels.add(lexicalItem.getPrefTerm());
                lexicalItemLabels.addAll(lexicalItem.getSynonym());
                lexicalItemLabels.addAll(lexicalItem.getAbbreviation());
                lexicalItemLabels.addAll(lexicalItem.getMisspelling());
                lexicalItemLabels.addAll(lexicalItem.getSubjExp());

                //once regex in modifier ontology are fixed can use match regex method
                if(lexicalItem.getRegex().size() > 0){
                    //System.out.println("Too mnay regex!!");
                    ArrayList<String> regex = lexicalItem.getRegex();
                    for(int i = 0; i < regex.size(); i++){
                        regexTuples.put(lexicalItemLabels.get(i), regex.get(i));
                        tripleList.add(new Triple<String, String, String>(lexicalItemLabels.get(i), regex.get(i),
                                lexicalItem.getActionEn(true)));
                    }
                    for(String variant : lexicalItemLabels){
                        if(!regexTuples.containsKey(variant)){
                            regexTuples.put(variant, "");
                            tripleList.add(new Triple<String, String, String>(variant, "",
                                    lexicalItem.getActionEn(true)));
                        }
                    }
                }
                for(String variant : lexicalItemLabels){
                    if(!regexTuples.containsKey(variant)){
                        regexTuples.put(variant, "");
                        tripleList.add(new Triple<String, String, String>(variant, "",
                                lexicalItem.getActionEn(true)));
                    }
                }

                //regexTuples.putAll(matchRegEx(lexicalItemLabels, lexicalItem.getRegex()));
            }

            if(regexTuples.isEmpty()){
                //System.out.println(modifier.getModName());
                bw.write(modifier.getModName() + "\t\t\t");
                bw.newLine();
            }else{
                for(Triple<String, String, String> triple : tripleList){
                    //System.out.println(modifier.getModName() + "\t" + entry.getKey() + "\t" + entry.getValue());
                    bw.write(modifier.getModName() + "\t" + triple.first() + "\t" + triple.second() + "\t"
                    + triple.third());
                    bw.newLine();
                }
            }

        }


        for(Modifier modifier : domain.createPseudoDictionary()){
            HashMap<String, String> regexTuples = new HashMap<String, String>();
            ArrayList<Triple<String, String, String>> tripleList = new ArrayList<Triple<String, String, String>>();

            ArrayList<LexicalItem> variants = modifier.getItems();
            for(LexicalItem lexicalItem : variants){
                ArrayList<String> lexicalItemLabels = new ArrayList<String>();
                lexicalItemLabels.add(lexicalItem.getPrefTerm());
                lexicalItemLabels.addAll(lexicalItem.getSynonym());
                lexicalItemLabels.addAll(lexicalItem.getAbbreviation());
                lexicalItemLabels.addAll(lexicalItem.getMisspelling());
                lexicalItemLabels.addAll(lexicalItem.getSubjExp());

                //once regex in modifier ontology are fixed can use match regex method
                if(lexicalItem.getRegex().size() > 0){
                    //System.out.println("Too mnay regex!!");
                    ArrayList<String> regex = lexicalItem.getRegex();
                    for(int i = 0; i < regex.size(); i++){
                        regexTuples.put(lexicalItemLabels.get(i), regex.get(i));
                        tripleList.add(new Triple<String, String, String>(lexicalItemLabels.get(i), regex.get(i),
                                lexicalItem.getActionEn(true)));
                    }
                    for(String variant : lexicalItemLabels){
                        if(!regexTuples.containsKey(variant)){
                            regexTuples.put(variant, "");
                            tripleList.add(new Triple<String, String, String>(variant, "",
                                    lexicalItem.getActionEn(true)));
                        }
                    }
                }
                for(String variant : lexicalItemLabels){
                    if(!regexTuples.containsKey(variant)){
                        regexTuples.put(variant, "");
                        tripleList.add(new Triple<String, String, String>(variant, "",
                                lexicalItem.getActionEn(true)));
                    }
                }

                //regexTuples.putAll(matchRegEx(lexicalItemLabels, lexicalItem.getRegex()));
            }

            if(regexTuples.isEmpty()){
                //System.out.println(modifier.getModName());
                bw.write(modifier.getModName() + "\t\t\t");
                bw.newLine();
            }else{
                for(Triple<String, String, String> triple : tripleList){
                    //System.out.println(modifier.getModName() + "\t" + entry.getKey() + "\t" + entry.getValue());
                    bw.write(modifier.getModName() + "\t" + triple.first() + "\t" + triple.second() + "\t"
                            + triple.third());
                    bw.newLine();
                }
            }

        }

        for(Modifier modifier : domain.createClosureDictionary()){
            HashMap<String, String> regexTuples = new HashMap<String, String>();
            ArrayList<Triple<String, String, String>> tripleList = new ArrayList<Triple<String, String, String>>();

            ArrayList<LexicalItem> variants = modifier.getItems();
            for(LexicalItem lexicalItem : variants){
                ArrayList<String> lexicalItemLabels = new ArrayList<String>();
                lexicalItemLabels.add(lexicalItem.getPrefTerm());
                lexicalItemLabels.addAll(lexicalItem.getSynonym());
                lexicalItemLabels.addAll(lexicalItem.getAbbreviation());
                lexicalItemLabels.addAll(lexicalItem.getMisspelling());
                lexicalItemLabels.addAll(lexicalItem.getSubjExp());

                //once regex in modifier ontology are fixed can use match regex method
                if(lexicalItem.getRegex().size() > 0){
                    //System.out.println("Too mnay regex!!");
                    ArrayList<String> regex = lexicalItem.getRegex();
                    for(int i = 0; i < regex.size(); i++){
                        regexTuples.put(lexicalItemLabels.get(i), regex.get(i));
                        tripleList.add(new Triple<String, String, String>(lexicalItemLabels.get(i), regex.get(i),
                                lexicalItem.getActionEn(true)));
                    }
                    for(String variant : lexicalItemLabels){
                        if(!regexTuples.containsKey(variant)){
                            regexTuples.put(variant, "");
                            tripleList.add(new Triple<String, String, String>(variant, "",
                                    lexicalItem.getActionEn(true)));
                        }
                    }
                }
                for(String variant : lexicalItemLabels){
                    if(!regexTuples.containsKey(variant)){
                        regexTuples.put(variant, "");
                        tripleList.add(new Triple<String, String, String>(variant, "",
                                lexicalItem.getActionEn(true)));
                    }
                }

                //regexTuples.putAll(matchRegEx(lexicalItemLabels, lexicalItem.getRegex()));
            }

            if(regexTuples.isEmpty()){
                //System.out.println(modifier.getModName());
                bw.write(modifier.getModName() + "\t\t\t");
                bw.newLine();
            }else{
                for(Triple<String, String, String> triple : tripleList){
                    //System.out.println(modifier.getModName() + "\t" + entry.getKey() + "\t" + entry.getValue());
                    bw.write(modifier.getModName() + "\t" + triple.first() + "\t" + triple.second() + "\t"
                            + triple.third());
                    bw.newLine();
                }
            }

        }

        bw.close();

        //write targets file
        System.out.println("Writing " + targetFile.getName() + "...");
        bw = new BufferedWriter(new FileWriter(targetFile));

        bw.write("Lex\tType\tRegex\n");

        ArrayList<Term> anchors = domain.createAnchorDictionary();

        for(Term term : anchors){
            HashMap<String, String> regexTuples = new HashMap<String, String>();
            ArrayList<String> variants = new ArrayList<String>();

            ArrayList<String> parents = term.getAllParents();
            Term ancestor = getAncestor(domain, parents);

            variants.add(term.getPrefTerm());
            //variants.addAll(term.getSynonym());
            for(String str : term.getSynonym()){
                if(!variants.contains(str)){
                    variants.add(str);
                }
            }
            for(String str : term.getMisspelling()){
                if(!variants.contains(str)){
                    variants.add(str);
                }
            }
            for(String str : term.getAbbreviation()){
                if(!variants.contains(str)){
                    variants.add(str);
                }
            }
            for(String str : term.getSubjExp()){
                if(!variants.contains(str)){
                    variants.add(str);
                }
            }
            //variants.addAll(term.getMisspelling());
            //variants.addAll(term.getAbbreviation());
            //variants.addAll(term.getSubjExp());

            if(term.getRegex().size() > 0){
                ArrayList<String> regex = term.getRegex();
                /**for(int i = 0; i < regex.size(); i++){
                    regexTuples.put(variants.get(i), regex.get(i));
                }
                for(String variant : variants){
                    if(!regexTuples.containsKey(variant)){
                        regexTuples.put(variant, "");
                    }
                }**/
                regexTuples.putAll(matchRegEx(variants, regex));
            }


            if(regexTuples.isEmpty()){
                //System.out.println(term.getPrefTerm() + domain.getDisplayName(term.getURI()) + "" + "\t");
                for(String str : variants){
                    if(ancestor != null){
                        //System.out.println(str + "\t" + domain.getDisplayName(ancestor.getURI()) + "" + "\t");
                        bw.write(str + "\t" + domain.getDisplayName(ancestor.getURI()) + "" + "\t");
                        bw.newLine();
                    }else{
                        //System.out.println(str + "\t" + domain.getDisplayName(term.getURI()) + "" + "\t");
                        bw.write(str + "\t" + domain.getDisplayName(term.getURI()) + "" + "\t");
                        bw.newLine();
                    }
                }


                //bw.write(term.getPrefTerm() + "\t" + domain.getDisplayName(term.getURI()) + "\t");
                //bw.newLine();
            }else{
                for(Map.Entry<String, String> entry : regexTuples.entrySet()){
                    //System.out.println(entry.getKey() + "\t" + domain.getDisplayName(term.getURI()) + "\t" +
                    //entry.getValue());
                    //TODO figure out parentage problem for regex tuples
                    bw.write(entry.getKey() + "\t" + domain.getDisplayName(term.getURI()) + "\t" +
                            entry.getValue());
                    bw.newLine();
                }
            }
        }

        bw.close();

        //write rules file
        final String CATEGORY_RULE = "@CATEGORY_RULE";
        System.out.println("Writing " + ruleFile.getName() + "...");
        bw = new BufferedWriter(new FileWriter(ruleFile));

        //TODO: Get variables and parse out anchor and modifiers associated with each anchor



    }

    public static HashMap<String, String> matchRegEx(ArrayList<String> variants, ArrayList<String> regex){
        HashMap<String, String> tuples = new HashMap<String, String>();

        class comp implements Comparator<String>{
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) {
                    return 1;
                } else if (o1.length() < o2.length()) {
                    return -1;
                } else {
                    return 0;
                }

            }
        }

        Collections.sort(variants, new comp());

        for(String r : regex){
            for(String v : variants){
                if(v.toLowerCase().matches(r)){
                    tuples.put(v.toLowerCase(), r);
                    //variants.remove(v);
                }
            }
        }

        for(String v : variants){
            if(!tuples.containsKey(v.toLowerCase())){
                tuples.put(v.toLowerCase(), v.toLowerCase());
            }

        }



        return tuples;
    }

    private Term getAncestor(DomainOntology domain, ArrayList<String> ancestors){

        for(String str : ancestors){
            if(!str.equalsIgnoreCase("http://blulab.chpc.utah.edu/ontologies/v2/Schema.owl#Anchor") &&
                    !str.equalsIgnoreCase("http://www.w3.org/2002/07/owl#Thing")){
                Term term = new Term(str, domain);
                //System.out.println(term);
                if(term.getDirectParents().isEmpty()){
                    return term;
                }
            }

        }

        return null;
    }




}

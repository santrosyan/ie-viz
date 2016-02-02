package edu.utah.blulab.domainontology;

import java.util.ArrayList;


public class Variable {
	private String varID;
	private String varName;
	private Term concept;
	private ArrayList<String> semanticCategory;
	private ArrayList<String> relationships; //May need feeback on the best representation here.
	private ArrayList<String> rules; //This may change once SWRL rules are implemented in ontology.
	private ArrayList<String> modifiers;
	private ArrayList<String> reportTypes; //may change once Document Ontology built
	private ArrayList<String> sectionHeadings; //may change once SecTag Ontology built
	private ArrayList<Variable> parents, children;
	
	public Variable(){
		varID = "";
		varName = "";
		concept = new Term();
		semanticCategory = new ArrayList<String>();
		relationships = new ArrayList<String>();
		rules = new ArrayList<String>();
		modifiers = new ArrayList<String>();
		reportTypes = new ArrayList<String>();
		sectionHeadings = new ArrayList<String>();
		parents = new ArrayList<Variable>();
		children = new ArrayList<Variable>();
	}

	public String getVarID() {
		return varID;
	}

	public void setVarID(String varID) {
		this.varID = varID;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	public ArrayList<String> getSemanticCategory(){
		return semanticCategory;
	}
	
	public void setSemanticCategory(ArrayList<String> semanticCategory){
		this.semanticCategory = semanticCategory;
	}

	public ArrayList<String> getRelationships() {
		return relationships;
	}

	public void setRelationships(ArrayList<String> relationships) {
		this.relationships = relationships;
	}

	public ArrayList<String> getRules() {
		return rules;
	}

	public void setRules(ArrayList<String> rules) {
		this.rules = rules;
	}

	public ArrayList<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(ArrayList<String> modifiers) {
		this.modifiers = modifiers;
	}

	public ArrayList<String> getReportTypes() {
		return reportTypes;
	}

	public void setReportTypes(ArrayList<String> reportTypes) {
		this.reportTypes = reportTypes;
	}

	public ArrayList<String> getSectionHeadings() {
		return sectionHeadings;
	}

	public void setSectionHeadings(ArrayList<String> sectionHeadings) {
		this.sectionHeadings = sectionHeadings;
	}
	
	public Term getConcept(){
		return concept;
	}
	
	public void setTerm(Term concept){
		this.concept = concept;
	}
	
	public ArrayList<Variable> getChildren(){
		return children;
	}
	
	public void setChildren(ArrayList<Variable> children){
		this.children = children;
	}
	
	public ArrayList<Variable> getParents(){
		return parents;
	}
	
	public void setParents(ArrayList<Variable> parents){
		this.parents = parents;
	}
	
	public boolean hasChildren(){
		return children.isEmpty();
	}
	
	public boolean hasParents(){
		return parents.isEmpty();
	}


	@Override
	public String toString() {
		return "Variable [varID=" + varID + ", varName=" + varName
				+ ", concept=" + concept + ", relationships=" + relationships + ", rules=" + rules
				+ ", modifiers=" + modifiers + ", reportTypes=" + reportTypes + ", sectionHeadings="
				+ sectionHeadings  + ", categories=" + semanticCategory + "]";
	}
	
	
	
	
}

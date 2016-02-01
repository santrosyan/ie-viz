package edu.utah.blulab.domainontology;

import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class OntologyConstants {
	public static final String CT_PM = "http://blulab.chpc.utha.edu/ontologies/v2/ConText.owl";
	public static final String SO_PM = "http://blulab.chpc.utah.edu/ontologies/v2/Schema.owl";
	//public static final String MO_PM = "http://blulab.chpc.utah.edu/ontologies/ModifierOntology.owl";
	public static final String TM_PM = "http://blulab.chpc.utah.edu/ontologies/TermMapping.owl";
	public static final String PREF_LABEL = TM_PM + "#preferredTerm"; //SO_PM + "#prefLabel";
	public static final String ALT_LABEL = TM_PM + "#synonym"; //SO_PM + "#altLabel";
	public static final String HIDDEN_LABEL = TM_PM + "#misspelling"; //SO_PM + "#hiddenLabel";
	public static final String ABR_LABEL = TM_PM + "#abbreviation"; //SO_PM + "#abbrLabel";
	public static final String SUBJ_EXP_LABEL = TM_PM + "#subjectiveExpression"; //SO_PM + "#subjExpLabel";
	public static final String REGEX = TM_PM + "#regex"; //SO_PM + "#regex";
	public static final String PREF_CUI = TM_PM + "#code"; //SO_PM + "#prefCUI";
	public static final String ALT_CUI = TM_PM + "#alternateCode"; //SO_PM + "#altCUI";
	public static final String SEC_HEADING = SO_PM + "#sectionHeader";
	public static final String DOC_TYPE = SO_PM + "#documentType";
	public static final String WINDOW = CT_PM + "#windowSize";
	public static final String ACTION_EN = CT_PM + "#hasActionEn";
	public static final String ACTION_DE = CT_PM + "#hasActionDe";
	public static final String ACTION_SV = CT_PM + "#hasActionSv";
	public static final String TERMINATION = CT_PM + "#hasTermination";
	
	
	
	public static final String HAS_SEM_ATTRIBUTE = SO_PM + "#hasSemAttribute";
	public static final String HAS_LING_ATTRIBUTE = SO_PM + "#hasLingAttribute";
	public static final String HAS_CLOSURE = CT_PM + "#hasTermination";
	
	public static final String FORWARD_ACTION = "forward";
	public static final String BACKWARD_ACTION = "backward";
	public static final String BIDIRECTIONAL_ACTION = "bidirectional";
	public static final String DISCONTINUOUS_ACTION = "discontinuous";
	public static final String TERMINATE_ACTION = "terminate";
	
}

package org.cidarlab.eugene.rules;

public enum RuleOperator {
	/* UNARY STRUCTURAL/POSITIONAL RULES */
	STARTSWITH, ENDSWITH,
	
	/* BINARY STRUCTURAL/POSITIONAL RULES */
	AFTER, ALL_AFTER, SOME_AFTER, 
	NEXTTO, ALL_NEXTTO, SOME_NEXTTO,
	LEFTTO, ALL_LEFTTO, SOME_LEFTTO,
	RIGHTTO, ALL_RIGHTTO, SOME_RIGHTTO,
	ALL_BEFORE, BEFORE,  SOME_BEFORE,
	
	/* RELATIONAL RULES */
	NOTEQUALS, EQUALS,
	
	/* NUMBERING/COUNTING RULES */
	CONTAINS, NOTCONTAINS, 
	MORETHAN, NOTMORETHAN, 
	EXACTLY, NOTEXACTLY,
	
		
	/* RELATIONSHIP RULES */
	REPRESSES, INDUCES, DRIVES, BINDS, MATCHES,	
	
	/* PAIRING RULES */
	NOTTHEN, THEN, WITH, NOTWITH,
	
	/* BIO-RULES */
	ORTHO
}

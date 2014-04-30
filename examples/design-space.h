/** PROPERTIES ***/
Property Name(txt);
Property Sequence(txt);
Property Represses(txt);
Property InducedBy(txt);
Property PromoterType(txt);

Property Pigeon(txt);

PartType SmallMolecule(Name);

/** PART TYPES ***/
PartType Promoter(Name, Sequence, Pigeon);
PartType RBS (Name, Sequence, Pigeon);
PartType Repressor(Name, Sequence, Pigeon);
PartType Reporter(Name, Sequence, Pigeon);
PartType Terminator(Name, Sequence, Pigeon);


//***************************
// Define the Design Space
//***************************

/*** Inducible Promoters ***/
Promoter pBad(
    .Name("pBad"),
    .Sequence("acattgattatttgcacggcgtcacactttgctatgccatagcaagatagtccataagattagcggatcctacctgacgctttttatcgcaactctctactgtttctccataccgtttttttgggctagc"),
    .Pigeon("p pBad 2"));

Promoter pDntR(
    .Name("pDntR"),
    .Sequence("ATAC"),
    .Pigeon("p pDntR 4"));

// let's define some molecules
SmallMolecule araC(.Name("araC"));
araC INDUCES pBad;

SmallMolecule in2(.Name("in2"));
in2 INDUCES pDntR;


/*** Repressible Promoters ***/
Promoter pCI(
    .Name("pCI"),
    .Sequence("taacaccgtgcgtgttgactattttacctctggcggtgataatggttgc"),
    .Pigeon("p pCI 2"));

Promoter pLux(
    .Name("pLux"),
    .Sequence("acctgtaggatcgtacaggtttacgcaagaaaatggtttgttatagtcgaatacctctggcggtgata"),
    .Pigeon("p pLux 5"));
    
Promoter pTetR(
    .Name("pTetR"),
    .Sequence("tccctatcagtgatagagattgacatccctatcagtgatagagatactgagcac"),
    .Pigeon("p pTetR 8"));
 
Promoter pLac(
    .Name("pLac"),
    .Sequence("..."),
    .Pigeon("p pLac 6"));


/*** Repressors ***/
Repressor cI(
    .Name("BBa_C0051"),
    .Sequence("atgagcacaaaaaagaaaccattaacacaagagcagcttgaggacgcacgtcgccttaaagcaatttatgaaaaaaagaaaaatgaacttggcttatccc
aggaatctgtcgcagacaagatggggatggggcagtcaggcgttggtgctttatttaatggcatcaatgcattaaatgcttataacgccgcattgcttgc
aaaaattctcaaagttagcgttgaagaatttagcccttcaatcgccagagaaatctacgagatgtatgaagcggttagtatgcagccgtcacttagaagt
gagtatgagtaccctgttttttctcatgttcaggcagggatgttctcacctgagcttagaacctttaccaaaggtgatgcggagagatgggtaagcacaa
ccaaaaaagccagtgattctgcattctggcttgaggttgaaggtaattccatgaccgcaccaacaggctccaagccaagctttcctgacggaatgttaat
tctcgttgaccctgagcaggctgttgagccaggtgatttctgcatagccagacttgggggtgatgagtttaccttcaagaaactgatcagggatagcggt
caggtgtttttacaaccactaaacccacagtacccaatgatcccatgcaatgagagttgttccgttgtggggaaagttatcgctagtcagtggcctgaag
agacgtttggcgctgcaaacgacgaaaactacgctttagtagcttaataa"),
    .Pigeon("c cI 2")); 

cI REPRESSES pCI;

Repressor LuxR(
	.Name("LuxR"),
	.Sequence("atgaaaaacataaatgccgacgacacatacagaataattaataaaattaaagcttgtagaagcaataatgatattaatcaatgcttatctgatatgacta
aaatggtacattgtgaatattatttactcgcgatcatttatcctcattctatggttaaatctgatatttcaatcctagataattaccctaaaaaatggag
gcaatattatgatgacgctaatttaataaaatatgatcctatagtagattattctaactccaatcattcaccaattaattggaatatatttgaaaacaat
gctgtaaataaaaaatctccaaatgtaattaaagaagcgaaaacatcaggtcttatcactgggtttagtttccctattcatacggctaacaatggcttcg
gaatgcttagttttgcacattcagaaaaagacaactatatagatagtttatttttacatgcgtgtatgaacataccattaattgttccttctctagttga
taattatcgaaaaataaatatagcaaataataaatcaaacaacgatttaaccaaaagagaaaaagaatgtttagcgtgggcatgcgaaggaaaaagctct
tgggatatttcaaaaatattaggttgcagtgagcgtactgtcactttccatttaaccaatgcgcaaatgaaactcaatacaacaaaccgctgccaaagta
tttctaaagcaattttaacaggagcaattgattgcccatactttaaaaattaataa"),
    .Pigeon("c LuxR 5")); 

LuxR REPRESSES pLux;

Repressor TetR(.Name("TetR"),
	.Sequence("
		ttatgacaacttgacggctacatcattcactttttcttcacaaccggcacggaactcgctcgggctggccccggtgcattttttaaatacccgcgagaaa
		tagagttgatcgtcaaaaccaacattgcgaccgacggtggcgataggcatccgggtggtgctcaaaagcagcttcgcctggctgatacgttggtcctcgc
		gccagcttaagacgctaatccctaactgctggcggaaaagatgtgacagacgcgacggcgacaagcaaacatgctgtgcgacgctggcgatatcaaaatt
		gctgtctgccaggtgatcgctgatgtactgacaagcctcgcgtacccgattatccatcggtggatggagcgactcgttaatcgcttccatgcgccgcagt
		aacaattgctcaagcagatttatcgccagcagctccgaatagcgcccttccccttgcccggcgttaatgatttgcccaaacaggtcgctgaaatgcggct
		ggtgcgcttcatccgggcgaaagaaccccgtattggcaaatattgacggccagttaagccattcatgccagtaggcgcgcggacgaaagtaaacccactg
		gtgataccattcgcgagcctccggatgacgaccgtagtgatgaatctctcctggcgggaacagcaaaatatcacccggtcggcaaacaaattctcgtccc
		tgatttttcaccaccccctgaccgcgaatggtgagattgagaatataacctttcattcccagcggtcggtcgataaaaaaatcgagataaccgttggcct
		caatcggcgttaaacccgccaccagatgggcattaaacgagtatcccggcagcaggggatcattttgcgcttcagccatacttttcatactcccgccatt
		cagagaagaaaccaattgtccatattgcatcagacattgccgtcactgcgtcttttactggctcttctcgctaaccaaaccggtaaccccgcttattaaa
		agcattctgtaacaaagcgggaccaaagccatgacaaaaacgcgtaacaaaagtgtctataatcacggcagaaaagtccacattgattatttgcacggcg
		tcacactttgctat"),
    .Pigeon("c TetR 8"));

TetR REPRESSES pTetR;


Repressor LacI(
	.Name("LacI"),
	.Sequence("...."),
    .Pigeon("c LacI 6")); 

LacI REPRESSES pLac;


/*** Ribosome Binding Sites ***/
RBS rbs61100("J61100","tctagaGAAAGAGGGGACAAactagt", "r rbs61100 13");
RBS rbs61101("J61101","tctagaGAAAGACAGGACCCactagt", "r rbs61101 13");
RBS rbs61102("J61102","tctagaGAAAGATCCGATGTactagt", "r rbs61102 13");
RBS rbs61103("J61103","tctagaGAAAGATTAGACAAactagt", "r rbs61103 13");
RBS rbs61104("J61104","tctagaGAAAGAAGGGACAGactagt", "r rbs61104 13");

/*** Reporters ***/
Reporter GFP("GFP",
"tccctatcagtgatagagattgacatccctatcagtgatagagatactgagcactactagagaaagaggagaaatactagatgcgtaaaggagaagaact
tttcactggagttgtcccaattcttgttgaattagatggtgatgttaatgggcacaaattttctgtcagtggagagggtgaaggtgatgcaacatacgga
aaacttacccttaaatttatttgcactactggaaaactacctgttccatggccaacacttgtcactactttcggttatggtgttcaatgctttgcgagat
acccagatcatatgaaacagcatgactttttcaagagtgccatgcccgaaggttatgtacaggaaagaactatatttttcaaagatgacgggaactacaa
gacacgtgctgaagtcaagtttgaaggtgatacccttgttaatagaatcgagttaaaaggtattgattttaaagaagatggaaacattcttggacacaaa
ttggaatacaactataactcacacaatgtatacatcatggcagacaaacaaaagaatggaatcaaagttaacttcaaaattagacacaacattgaagatg
gaagcgttcaactagcagaccattatcaacaaaatactccaattggcgatggccctgtccttttaccagacaaccattacctgtccacacaatctgccct
ttcgaaagatcccaacgaaaagagagaccacatggtccttcttgagtttgtaacagctgctgggattacacatggcatggatgaactatacaaataataa
tactagagccaggcatcaaataaaacgaaaggctcagtcgaaagactgggcctttcgttttatctgttgtttgtcggtgaacgctctctactagagtcac
actggctcaccttcgggtgggcctttctgcgtttata",
	"c GFP 4");

Reporter YFP("YFP",
"aaagaggagaaatactagatggtgagcaagggcgaggagctgttcaccggggtggtgcccatcctggtcgagctggacggcgacgtaaacggccacaagt
tcagcgtgtccggcgagggcgagggcgatgccacctacggcaagctgaccctgaagttcatctgcaccaccggcaagctgcccgtgccctggcccaccct
cgtgaccaccttcggctacggcctgcaatgcttcgcccgctaccccgaccacatgaagctgcacgacttcttcaagtccgccatgcccgaaggctacgtc
caggagcgcaccatcttcttcaaggacgacggcaactacaagacccgcgccgaggtgaagttcgagggcgacaccctggtgaaccgcatcgagctgaagg
gcatcgacttcaaggaggacggcaacatcctggggcacaagctggagtacaactacaacagccacaacgtctatatcatggccgacaagcagaagaacgg
catcaaggtgaacttcaagatccgccacaacatcgaggacggcagcgtgcagctcgccgaccactaccagcagaacacccccatcggcgacggccccgtg
ctgctgcccgacaaccactacctgagctaccagtccgccctgagcaaagaccccaacgagaagcgcgatcacatggtcctgctggagttcgtgaccgccg
ccgggatcactctcggcatggacgagctgtacaagtaataatactagagccaggcatcaaataaaacgaaaggctcagtcgaaagactgggcctttcgtt
ttatctgttgtttgtcggtgaacgctctctactagagtcacactggctcaccttcgggtgggcctttctgcgtttata",
	"c YFP 12");

Reporter RFP(.Name("RFP"), .Pigeon("c RFP 6"));

/*** Terminators ***/
Terminator T1("T1", 
"nagattactataaataggcgtatcacgaggcagaatttcagataaaaaaaatccttagctttcgctaaggatgatttctg
gaattcgcggccgcttctagagccggcttatcggtcagtttcacctgatttacgtaaaaacccgcttcggcgggtttttg
cttttggaggggcagaaagatgaatgactgtccacgacgctatacccaaaagaaatactagtctgcaggcttcctcgctc
actgactcgctgcgctcggtcgttcggctgcggcgagcggtatcagctcactcaaaggcggtaatacggttatccacaga
atcaggggataacgcaggaaagaacatgtgagcaaaaggccagcaaaaggccaggaaccgtaaaaaggccgcgttgctgg
cgtttttccacaggctccgcccccctgacgagcatcacaaaaatcgacgctcaagtcagaggtggcgaaacccgacagga
ctataaagataccaggcgtttccccctggaagctccctcgtgcgctctcctgttccgaccctgccgcttaccggatacct
gtccgcctttctcccttcgggaagcgtggcgctttctcatagctcacgctgtaggtatctcagttcggtgtaggtcgttc
gctccaagctgggctgtgtgcacgaaccccccgttcagcccgaccgctgcgccttatccggtaactatcgtcttgagtcc
aacccggtaagacacgacttatcgccactggcagcagccactggtaacaggattagcagagcgaggtatgtaggcggtgc
tacagagttcttgaagtggtggcctaactacggctacactagaagaacagtatttggtatctgcgctctgctgaagccag
ttaccttcggaaaaagagttggtagctcttgatccggcaaacaaaccaccgctggtagcggtggttttttngtttgcang
cagcaaattacgcgcanaaaaaaagcnaanaaaaaantntttntttttttttnggggnttnnnnccnnggggnaaaaaan
ncccnnnnannnnttttnnnnnnggnantttnnaaangganttccccnnannnctttnnntnaaannnttttttnaannn
nnnnnnttttnnnnnannnannnnnnnnnaannnnncnnnnttnaannngnnnncnttnnnnnngggggnnntttttnnn
nnnnnntttnnnnnccccccnnnnnaannnnncann",
	"t T1 14");

Terminator T7(.Name("T7"), .Pigeon("t T7 13"));
Terminator BBa_B0010("BBa_B0010",
"nnnncccnnanacnntttttatcccaaannnntaanngaaccctccncgncncccttttnnccannnntttnnccggttn
ttctnnnncngttttttggcgggnaaaaaaagnntattnnnnnnnnntntggggnnnnnnntttttngntnccncccccc
cccgcgcgnntcntntgttatgntgtnctctactntctctcgagagattagtacctttggagatctactagagaaagagg
agaaatactagatggcttcctncgaagacgttatcaaagagttcatgcgtttcaaagttcgtatggaaggttccgttaac
ggtcacgagttcgaaatcgaaggtgaaggtgaaggtcgtccgtacgaaggtacccagaccgctaaactgaaagttaccaa
aggtggtccgctgccgttcgcttgggacatcctgtccccgcagttccagtacggttccaaagcttacgttaaacacccgg
ctgacatcccggactacctgaaactgtccttcccggaaggtttcaaatgggaacgtgttatgaacttcgaagacggtggt
gttgttaccgttacccaggactcctccctgcaagacggtgagttcatctacaaagttaaactgcgtggtaccaacttccc
gtccgacggtccggttatgcagaaaaaaaccatgggttgggaagcttccaccgaacgtatgtacccggaagacggtgctc
tgaaaggtgaaatcaaaatgcgtctgaaactgaaagacggtggtcactacgacgctgaagttaaaaccacctacatggct
aaaaaaccggttcagctgccgggtgcttacaaaaccgacatcaaactggacatcacctcccacaacgaagactacaccat
cgttgaacagtacgaacgtgctgaaggtcgtcactccaccggtgcttaataacgctgatagtgctagtgtagatcgctac
tagagccaggcatcaaataaaacgaaaggctcagtcgaaagactgggcctttcgttttatctgttgtttgtcggtgaacg
ctctctactagagtcacactggctcaccttcgggtgggcctttctgcgtttatatactagtagcggccgctgcagtccgg
caaaaaagggcaaggtgtcaccaccctgccctttttctttaaaaccgaaaagattacttcgcgttatgcaggcttcctcg
ctcactgactcgctgcgctcggtcgtnngnnngcnnnnnnnnn",
	"t BBa_B0010 13");

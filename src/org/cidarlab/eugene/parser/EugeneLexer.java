// $ANTLR 3.4 /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g 2014-04-30 09:27:54

/*
Copyright (c) 2012 Boston University.
All rights reserved.
Permission is hereby granted, without written agreement and without
license or royalty fees, to use, copy, modify, and distribute this
software and its documentation for any purpose, provided that the above
copyright notice and the following two paragraphs appear in all copies
of this software.

IN NO EVENT SHALL BOSTON UNIVERSITY BE LIABLE TO ANY PARTY
FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
BOSTON UNIVERSITY HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

BOSTON UNIVERSITY SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND BOSTON UNIVERSITY HAS
NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
ENHANCEMENTS, OR MODIFICATIONS.
*/

package org.cidarlab.eugene.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class EugeneLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__50=50;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__90=90;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__99=99;
    public static final int T__100=100;
    public static final int T__101=101;
    public static final int T__102=102;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__110=110;
    public static final int T__111=111;
    public static final int T__112=112;
    public static final int T__113=113;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__120=120;
    public static final int T__121=121;
    public static final int T__122=122;
    public static final int T__123=123;
    public static final int T__124=124;
    public static final int T__125=125;
    public static final int T__126=126;
    public static final int T__127=127;
    public static final int T__128=128;
    public static final int T__129=129;
    public static final int CHAR=4;
    public static final int COMMENT=5;
    public static final int DYNAMIC_NAME=6;
    public static final int ESC_SEQ=7;
    public static final int EXPONENT=8;
    public static final int FLOAT=9;
    public static final int HEX_DIGIT=10;
    public static final int ID=11;
    public static final int INCLUDE=12;
    public static final int INT=13;
    public static final int OCTAL_ESC=14;
    public static final int STRING=15;
    public static final int UNICODE_ESC=16;
    public static final int WS=17;

    class SaveStruct {
        public CharStream input;
        public int marker;
        SaveStruct(CharStream input) {
            this.input = input;
            this.marker = input.mark();
        }
    }
     
    Stack<SaveStruct> includes = new Stack<SaveStruct>();
     
    // We should override this method for handling EOF of included file
    public Token nextToken(){
        Token token = super.nextToken();
     
        if(token.getType() == Token.EOF && !includes.empty()){
            // We've got EOF and have non empty stack
            SaveStruct ss = includes.pop();
            setCharStream(ss.input);
            input.rewind(ss.marker);

            //this should be used instead of super [like below] to handle exits from nested includes
            //it matters, when the 'include' token is the last in previous stream (using super, lexer 'crashes' returning EOF token)
            token = this.nextToken();
        }
     
        // Skip first token after switching on another input.
        // You need to use this rather than super as there may be nested include files
        if(((CommonToken)token).getStartIndex() < 0) {
            token = this.nextToken();
        }
        
        return token;
    }

    public void includeFile(String name) {
        try {
            // save current lexer's state
            SaveStruct ss = new SaveStruct(input);
            includes.push(ss);
     
            // switch on new input stream
            setCharStream(new ANTLRFileStream(name));
            reset();
        } catch (Exception fnf) {
            fnf.printStackTrace();
        }
    }


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public EugeneLexer() {} 
    public EugeneLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public EugeneLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g"; }

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:83:7: ( '!=' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:83:9: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:84:7: ( '&&' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:84:9: '&&'
            {
            match("&&"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:85:7: ( '(' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:85:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:86:7: ( ')' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:86:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:87:7: ( '*' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:87:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:88:7: ( '+' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:88:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:89:7: ( ',' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:89:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:90:7: ( '-' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:90:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:91:7: ( '.' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:91:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:92:7: ( '.add' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:92:9: '.add'
            {
            match(".add"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:93:7: ( '.addProperties' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:93:9: '.addProperties'
            {
            match(".addProperties"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:94:7: ( '.addProperty' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:94:9: '.addProperty'
            {
            match(".addProperty"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:95:7: ( '/' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:95:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:96:7: ( ':' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:96:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:97:7: ( ';' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:97:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:98:7: ( '<' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:98:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:99:7: ( '<=' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:99:9: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:100:7: ( '=' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:100:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:101:7: ( '==' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:101:9: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:102:7: ( '>' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:102:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:103:7: ( '>=' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:103:9: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:104:7: ( 'AFTER' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:104:9: 'AFTER'
            {
            match("AFTER"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:105:7: ( 'ALL_AFTER' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:105:9: 'ALL_AFTER'
            {
            match("ALL_AFTER"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:106:7: ( 'ALL_BEFORE' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:106:9: 'ALL_BEFORE'
            {
            match("ALL_BEFORE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:107:7: ( 'ALL_LEFTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:107:9: 'ALL_LEFTTO'
            {
            match("ALL_LEFTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:108:7: ( 'ALL_NEXTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:108:9: 'ALL_NEXTTO'
            {
            match("ALL_NEXTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:109:7: ( 'ALL_RIGHTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:109:9: 'ALL_RIGHTTO'
            {
            match("ALL_RIGHTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:110:7: ( 'AND' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:110:9: 'AND'
            {
            match("AND"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:111:7: ( 'Assert' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:111:9: 'Assert'
            {
            match("Assert"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:112:7: ( 'BEFORE' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:112:9: 'BEFORE'
            {
            match("BEFORE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:113:7: ( 'BINDS' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:113:9: 'BINDS'
            {
            match("BINDS"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:114:7: ( 'CONTAINS' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:114:9: 'CONTAINS'
            {
            match("CONTAINS"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:115:7: ( 'Collection' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:115:9: 'Collection'
            {
            match("Collection"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:116:7: ( 'DRIVES' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:116:9: 'DRIVES'
            {
            match("DRIVES"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:117:7: ( 'Device' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:117:9: 'Device'
            {
            match("Device"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:118:7: ( 'DeviceType' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:118:9: 'DeviceType'
            {
            match("DeviceType"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:119:7: ( 'ENDSWITH' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:119:9: 'ENDSWITH'
            {
            match("ENDSWITH"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:120:7: ( 'EQUALS' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:120:9: 'EQUALS'
            {
            match("EQUALS"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:121:7: ( 'EXACTLY' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:121:9: 'EXACTLY'
            {
            match("EXACTLY"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:122:7: ( 'Genbank' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:122:9: 'Genbank'
            {
            match("Genbank"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:123:7: ( 'INDUCES' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:123:9: 'INDUCES'
            {
            match("INDUCES"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:124:7: ( 'INSTANCEOF' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:124:9: 'INSTANCEOF'
            {
            match("INSTANCEOF"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:125:7: ( 'Image' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:125:9: 'Image'
            {
            match("Image"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:126:7: ( 'LEFTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:126:9: 'LEFTTO'
            {
            match("LEFTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:127:7: ( 'MATCHES' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:127:9: 'MATCHES'
            {
            match("MATCHES"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:128:7: ( 'MORETHAN' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:128:9: 'MORETHAN'
            {
            match("MORETHAN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:129:7: ( 'NEXTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:129:9: 'NEXTTO'
            {
            match("NEXTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:130:7: ( 'NOT' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:130:9: 'NOT'
            {
            match("NOT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:131:7: ( 'NOTCONTAINS' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:131:9: 'NOTCONTAINS'
            {
            match("NOTCONTAINS"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:132:7: ( 'NOTEQUALS' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:132:9: 'NOTEQUALS'
            {
            match("NOTEQUALS"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:133:7: ( 'NOTEXACTLY' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:133:9: 'NOTEXACTLY'
            {
            match("NOTEXACTLY"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:134:7: ( 'NOTMORETHAN' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:134:9: 'NOTMORETHAN'
            {
            match("NOTMORETHAN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:135:7: ( 'NOTTHEN' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:135:9: 'NOTTHEN'
            {
            match("NOTTHEN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:136:7: ( 'NOTWITH' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:136:9: 'NOTWITH'
            {
            match("NOTWITH"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:137:7: ( 'Note' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:137:9: 'Note'
            {
            match("Note"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:138:7: ( 'ON' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:138:9: 'ON'
            {
            match("ON"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:139:7: ( 'OR' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:139:9: 'OR'
            {
            match("OR"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:140:7: ( 'ORTHO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:140:9: 'ORTHO'
            {
            match("ORTHO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:141:7: ( 'Part' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:141:9: 'Part'
            {
            match("Part"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:142:7: ( 'PartType' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:142:9: 'PartType'
            {
            match("PartType"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:143:7: ( 'Property' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:143:9: 'Property'
            {
            match("Property"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:144:7: ( 'REPRESSES' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:144:9: 'REPRESSES'
            {
            match("REPRESSES"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:145:7: ( 'RIGHTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:145:9: 'RIGHTTO'
            {
            match("RIGHTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:146:7: ( 'Registry' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:146:9: 'Registry'
            {
            match("Registry"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:147:7: ( 'Rule' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:147:9: 'Rule'
            {
            match("Rule"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:148:7: ( 'SBOL' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:148:9: 'SBOL'
            {
            match("SBOL"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:149:7: ( 'SOME_AFTER' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:149:9: 'SOME_AFTER'
            {
            match("SOME_AFTER"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:150:7: ( 'SOME_BEFORE' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:150:9: 'SOME_BEFORE'
            {
            match("SOME_BEFORE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:151:7: ( 'SOME_LEFTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:151:9: 'SOME_LEFTTO'
            {
            match("SOME_LEFTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:152:7: ( 'SOME_NEXTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:152:9: 'SOME_NEXTTO'
            {
            match("SOME_NEXTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:153:7: ( 'SOME_RIGHTTO' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:153:9: 'SOME_RIGHTTO'
            {
            match("SOME_RIGHTTO"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:154:7: ( 'STARTSWITH' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:154:9: 'STARTSWITH'
            {
            match("STARTSWITH"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:155:7: ( 'THEN' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:155:9: 'THEN'
            {
            match("THEN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:156:7: ( 'WITH' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:156:9: 'WITH'
            {
            match("WITH"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:157:7: ( 'XOR' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:157:9: 'XOR'
            {
            match("XOR"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:158:7: ( '[' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:158:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:159:7: ( ']' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:159:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:160:7: ( '^^' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:160:9: '^^'
            {
            match("^^"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:161:7: ( 'boolean' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:161:9: 'boolean'
            {
            match("boolean"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:162:7: ( 'depth' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:162:9: 'depth'
            {
            match("depth"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:163:7: ( 'do' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:163:9: 'do'
            {
            match("do"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:164:7: ( 'else' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:164:9: 'else'
            {
            match("else"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:165:8: ( 'export' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:165:10: 'export'
            {
            match("export"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:166:8: ( 'false' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:166:10: 'false'
            {
            match("false"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:167:8: ( 'flexible' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:167:10: 'flexible'
            {
            match("flexible"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "T__103"
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:168:8: ( 'for' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:168:10: 'for'
            {
            match("for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__103"

    // $ANTLR start "T__104"
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:169:8: ( 'function' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:169:10: 'function'
            {
            match("function"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__104"

    // $ANTLR start "T__105"
    public final void mT__105() throws RecognitionException {
        try {
            int _type = T__105;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:170:8: ( 'get' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:170:10: 'get'
            {
            match("get"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__105"

    // $ANTLR start "T__106"
    public final void mT__106() throws RecognitionException {
        try {
            int _type = T__106;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:171:8: ( 'if' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:171:10: 'if'
            {
            match("if"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__106"

    // $ANTLR start "T__107"
    public final void mT__107() throws RecognitionException {
        try {
            int _type = T__107;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:172:8: ( 'import' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:172:10: 'import'
            {
            match("import"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__107"

    // $ANTLR start "T__108"
    public final void mT__108() throws RecognitionException {
        try {
            int _type = T__108;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:173:8: ( 'instanceof' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:173:10: 'instanceof'
            {
            match("instanceof"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__108"

    // $ANTLR start "T__109"
    public final void mT__109() throws RecognitionException {
        try {
            int _type = T__109;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:174:8: ( 'instantiate' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:174:10: 'instantiate'
            {
            match("instantiate"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__109"

    // $ANTLR start "T__110"
    public final void mT__110() throws RecognitionException {
        try {
            int _type = T__110;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:175:8: ( 'isEmpty' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:175:10: 'isEmpty'
            {
            match("isEmpty"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__110"

    // $ANTLR start "T__111"
    public final void mT__111() throws RecognitionException {
        try {
            int _type = T__111;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:176:8: ( 'maxDepth' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:176:10: 'maxDepth'
            {
            match("maxDepth"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__111"

    // $ANTLR start "T__112"
    public final void mT__112() throws RecognitionException {
        try {
            int _type = T__112;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:177:8: ( 'num' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:177:10: 'num'
            {
            match("num"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__112"

    // $ANTLR start "T__113"
    public final void mT__113() throws RecognitionException {
        try {
            int _type = T__113;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:178:8: ( 'permute' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:178:10: 'permute'
            {
            match("permute"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__113"

    // $ANTLR start "T__114"
    public final void mT__114() throws RecognitionException {
        try {
            int _type = T__114;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:179:8: ( 'pigeon' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:179:10: 'pigeon'
            {
            match("pigeon"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__114"

    // $ANTLR start "T__115"
    public final void mT__115() throws RecognitionException {
        try {
            int _type = T__115;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:180:8: ( 'print' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:180:10: 'print'
            {
            match("print"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__115"

    // $ANTLR start "T__116"
    public final void mT__116() throws RecognitionException {
        try {
            int _type = T__116;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:181:8: ( 'println' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:181:10: 'println'
            {
            match("println"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__116"

    // $ANTLR start "T__117"
    public final void mT__117() throws RecognitionException {
        try {
            int _type = T__117;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:182:8: ( 'product' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:182:10: 'product'
            {
            match("product"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__117"

    // $ANTLR start "T__118"
    public final void mT__118() throws RecognitionException {
        try {
            int _type = T__118;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:183:8: ( 'remove' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:183:10: 'remove'
            {
            match("remove"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__118"

    // $ANTLR start "T__119"
    public final void mT__119() throws RecognitionException {
        try {
            int _type = T__119;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:184:8: ( 'return' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:184:10: 'return'
            {
            match("return"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__119"

    // $ANTLR start "T__120"
    public final void mT__120() throws RecognitionException {
        try {
            int _type = T__120;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:185:8: ( 'save' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:185:10: 'save'
            {
            match("save"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__120"

    // $ANTLR start "T__121"
    public final void mT__121() throws RecognitionException {
        try {
            int _type = T__121;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:186:8: ( 'size' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:186:10: 'size'
            {
            match("size"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__121"

    // $ANTLR start "T__122"
    public final void mT__122() throws RecognitionException {
        try {
            int _type = T__122;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:187:8: ( 'strict' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:187:10: 'strict'
            {
            match("strict"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__122"

    // $ANTLR start "T__123"
    public final void mT__123() throws RecognitionException {
        try {
            int _type = T__123;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:188:8: ( 'toSequence' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:188:10: 'toSequence'
            {
            match("toSequence"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__123"

    // $ANTLR start "T__124"
    public final void mT__124() throws RecognitionException {
        try {
            int _type = T__124;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:189:8: ( 'true' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:189:10: 'true'
            {
            match("true"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__124"

    // $ANTLR start "T__125"
    public final void mT__125() throws RecognitionException {
        try {
            int _type = T__125;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:190:8: ( 'txt' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:190:10: 'txt'
            {
            match("txt"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__125"

    // $ANTLR start "T__126"
    public final void mT__126() throws RecognitionException {
        try {
            int _type = T__126;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:191:8: ( 'while' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:191:10: 'while'
            {
            match("while"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__126"

    // $ANTLR start "T__127"
    public final void mT__127() throws RecognitionException {
        try {
            int _type = T__127;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:192:8: ( '{' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:192:10: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__127"

    // $ANTLR start "T__128"
    public final void mT__128() throws RecognitionException {
        try {
            int _type = T__128;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:193:8: ( '||' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:193:10: '||'
            {
            match("||"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__128"

    // $ANTLR start "T__129"
    public final void mT__129() throws RecognitionException {
        try {
            int _type = T__129;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:194:8: ( '}' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:194:10: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__129"

    // $ANTLR start "INCLUDE"
    public final void mINCLUDE() throws RecognitionException {
        try {
            int _type = INCLUDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken f=null;

            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3128:9: ( 'include' ( WS )+ f= STRING )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3128:11: 'include' ( WS )+ f= STRING
            {
            match("include"); 



            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3128:21: ( WS )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '\t' && LA1_0 <= '\n')||LA1_0=='\r'||LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3128:22: WS
            	    {
            	    mWS(); 


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            int fStart942 = getCharIndex();
            int fStartLine942 = getLine();
            int fStartCharPos942 = getCharPositionInLine();
            mSTRING(); 
            f = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, fStart942, getCharIndex()-1);
            f.setLine(fStartLine942);
            f.setCharPositionInLine(fStartCharPos942);



            String name = f.getText();
            name = name.substring(1,name.length()-1);
            includeFile(name);
            	

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INCLUDE"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3135:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) | INT ( 'a' .. 'z' | 'A' .. 'Z' | '_' )+ ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' ) | 'a' .. 'z' | 'A' .. 'Z' )
            int alt4=4;
            switch ( input.LA(1) ) {
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                {
                int LA4_1 = input.LA(2);

                if ( ((LA4_1 >= '0' && LA4_1 <= '9')||(LA4_1 >= 'A' && LA4_1 <= 'Z')||LA4_1=='_'||(LA4_1 >= 'a' && LA4_1 <= 'z')) ) {
                    alt4=1;
                }
                else {
                    alt4=3;
                }
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                {
                alt4=2;
                }
                break;
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                {
                int LA4_3 = input.LA(2);

                if ( ((LA4_3 >= '0' && LA4_3 <= '9')||(LA4_3 >= 'A' && LA4_3 <= 'Z')||LA4_3=='_'||(LA4_3 >= 'a' && LA4_3 <= 'z')) ) {
                    alt4=1;
                }
                else {
                    alt4=4;
                }
                }
                break;
            case '_':
                {
                alt4=1;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }

            switch (alt4) {
                case 1 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3135:8: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )
                    {
                    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3135:32: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
                            int LA2_1 = input.LA(2);

                            if ( ((LA2_1 >= '0' && LA2_1 <= '9')||(LA2_1 >= 'A' && LA2_1 <= 'Z')||LA2_1=='_'||(LA2_1 >= 'a' && LA2_1 <= 'z')) ) {
                                alt2=1;
                            }


                        }
                        else if ( (LA2_0=='_') ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3136:4: INT ( 'a' .. 'z' | 'A' .. 'Z' | '_' )+ ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' )
                    {
                    mINT(); 


                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3136:8: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0 >= 'A' && LA3_0 <= 'Z')||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
                            int LA3_1 = input.LA(2);

                            if ( ((LA3_1 >= '0' && LA3_1 <= '9')||(LA3_1 >= 'A' && LA3_1 <= 'Z')||LA3_1=='_'||(LA3_1 >= 'a' && LA3_1 <= 'z')) ) {
                                alt3=1;
                            }


                        }
                        else if ( (LA3_0=='_') ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    	    {
                    	    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 3 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3137:4: 'a' .. 'z'
                    {
                    matchRange('a','z'); 

                    }
                    break;
                case 4 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3138:4: 'A' .. 'Z'
                    {
                    matchRange('A','Z'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3141:5: ( ( '0' .. '9' )+ )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3141:7: ( '0' .. '9' )+
            {
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3141:7: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3146:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT )
            int alt12=3;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3146:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )?
                    {
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3146:9: ( '0' .. '9' )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);


                    match('.'); 

                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3146:25: ( '0' .. '9' )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0 >= '0' && LA7_0 <= '9')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3146:37: ( EXPONENT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='E'||LA8_0=='e') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3146:37: EXPONENT
                            {
                            mEXPONENT(); 


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3147:9: '.' ( '0' .. '9' )+ ( EXPONENT )?
                    {
                    match('.'); 

                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3147:13: ( '0' .. '9' )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0 >= '0' && LA9_0 <= '9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);


                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3147:25: ( EXPONENT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='E'||LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3147:25: EXPONENT
                            {
                            mEXPONENT(); 


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3148:9: ( '0' .. '9' )+ EXPONENT
                    {
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3148:9: ( '0' .. '9' )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0 >= '0' && LA11_0 <= '9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt11 >= 1 ) break loop11;
                                EarlyExitException eee =
                                    new EarlyExitException(11, input);
                                throw eee;
                        }
                        cnt11++;
                    } while (true);


                    mEXPONENT(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3152:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='/') ) {
                int LA16_1 = input.LA(2);

                if ( (LA16_1=='/') ) {
                    alt16=1;
                }
                else if ( (LA16_1=='*') ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }
            switch (alt16) {
                case 1 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3152:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match("//"); 



                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3152:14: (~ ( '\\n' | '\\r' ) )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( ((LA13_0 >= '\u0000' && LA13_0 <= '\t')||(LA13_0 >= '\u000B' && LA13_0 <= '\f')||(LA13_0 >= '\u000E' && LA13_0 <= '\uFFFF')) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3152:28: ( '\\r' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='\r') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3152:28: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    _channel=HIDDEN;

                    }
                    break;
                case 2 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3153:9: '/*' ( options {greedy=false; } : . )* '*/'
                    {
                    match("/*"); 



                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3153:14: ( options {greedy=false; } : . )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0=='*') ) {
                            int LA15_1 = input.LA(2);

                            if ( (LA15_1=='/') ) {
                                alt15=2;
                            }
                            else if ( ((LA15_1 >= '\u0000' && LA15_1 <= '.')||(LA15_1 >= '0' && LA15_1 <= '\uFFFF')) ) {
                                alt15=1;
                            }


                        }
                        else if ( ((LA15_0 >= '\u0000' && LA15_0 <= ')')||(LA15_0 >= '+' && LA15_0 <= '\uFFFF')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3153:42: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);


                    match("*/"); 



                    _channel=HIDDEN;

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3156:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3156:9: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "DYNAMIC_NAME"
    public final void mDYNAMIC_NAME() throws RecognitionException {
        try {
            int _type = DYNAMIC_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3164:2: ( '*' '*' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3164:4: '*' '*'
            {
            match('*'); 

            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DYNAMIC_NAME"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3169:5: ( '\"' ( ESC_SEQ |~ ( '\"' ) )* '\"' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3169:8: '\"' ( ESC_SEQ |~ ( '\"' ) )* '\"'
            {
            match('\"'); 

            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3169:12: ( ESC_SEQ |~ ( '\"' ) )*
            loop17:
            do {
                int alt17=3;
                int LA17_0 = input.LA(1);

                if ( (LA17_0=='\\') ) {
                    int LA17_2 = input.LA(2);

                    if ( (LA17_2=='\"') ) {
                        int LA17_4 = input.LA(3);

                        if ( ((LA17_4 >= '\u0000' && LA17_4 <= '\uFFFF')) ) {
                            alt17=1;
                        }

                        else {
                            alt17=2;
                        }


                    }
                    else if ( (LA17_2=='u') ) {
                        int LA17_5 = input.LA(3);

                        if ( ((LA17_5 >= '0' && LA17_5 <= '9')||(LA17_5 >= 'A' && LA17_5 <= 'F')||(LA17_5 >= 'a' && LA17_5 <= 'f')) ) {
                            int LA17_10 = input.LA(4);

                            if ( ((LA17_10 >= '0' && LA17_10 <= '9')||(LA17_10 >= 'A' && LA17_10 <= 'F')||(LA17_10 >= 'a' && LA17_10 <= 'f')) ) {
                                int LA17_11 = input.LA(5);

                                if ( ((LA17_11 >= '0' && LA17_11 <= '9')||(LA17_11 >= 'A' && LA17_11 <= 'F')||(LA17_11 >= 'a' && LA17_11 <= 'f')) ) {
                                    int LA17_12 = input.LA(6);

                                    if ( ((LA17_12 >= '0' && LA17_12 <= '9')||(LA17_12 >= 'A' && LA17_12 <= 'F')||(LA17_12 >= 'a' && LA17_12 <= 'f')) ) {
                                        alt17=1;
                                    }
                                    else if ( ((LA17_12 >= '\u0000' && LA17_12 <= '/')||(LA17_12 >= ':' && LA17_12 <= '@')||(LA17_12 >= 'G' && LA17_12 <= '`')||(LA17_12 >= 'g' && LA17_12 <= '\uFFFF')) ) {
                                        alt17=2;
                                    }


                                }
                                else if ( ((LA17_11 >= '\u0000' && LA17_11 <= '/')||(LA17_11 >= ':' && LA17_11 <= '@')||(LA17_11 >= 'G' && LA17_11 <= '`')||(LA17_11 >= 'g' && LA17_11 <= '\uFFFF')) ) {
                                    alt17=2;
                                }


                            }
                            else if ( ((LA17_10 >= '\u0000' && LA17_10 <= '/')||(LA17_10 >= ':' && LA17_10 <= '@')||(LA17_10 >= 'G' && LA17_10 <= '`')||(LA17_10 >= 'g' && LA17_10 <= '\uFFFF')) ) {
                                alt17=2;
                            }


                        }
                        else if ( ((LA17_5 >= '\u0000' && LA17_5 <= '/')||(LA17_5 >= ':' && LA17_5 <= '@')||(LA17_5 >= 'G' && LA17_5 <= '`')||(LA17_5 >= 'g' && LA17_5 <= '\uFFFF')) ) {
                            alt17=2;
                        }


                    }
                    else if ( ((LA17_2 >= '0' && LA17_2 <= '3')) ) {
                        alt17=1;
                    }
                    else if ( ((LA17_2 >= '4' && LA17_2 <= '7')) ) {
                        alt17=1;
                    }
                    else if ( (LA17_2=='\\') ) {
                        alt17=1;
                    }
                    else if ( (LA17_2=='\''||LA17_2=='b'||LA17_2=='f'||LA17_2=='n'||LA17_2=='r'||LA17_2=='t') ) {
                        alt17=1;
                    }
                    else if ( ((LA17_2 >= '\u0000' && LA17_2 <= '!')||(LA17_2 >= '#' && LA17_2 <= '&')||(LA17_2 >= '(' && LA17_2 <= '/')||(LA17_2 >= '8' && LA17_2 <= '[')||(LA17_2 >= ']' && LA17_2 <= 'a')||(LA17_2 >= 'c' && LA17_2 <= 'e')||(LA17_2 >= 'g' && LA17_2 <= 'm')||(LA17_2 >= 'o' && LA17_2 <= 'q')||LA17_2=='s'||(LA17_2 >= 'v' && LA17_2 <= '\uFFFF')) ) {
                        alt17=2;
                    }


                }
                else if ( ((LA17_0 >= '\u0000' && LA17_0 <= '!')||(LA17_0 >= '#' && LA17_0 <= '[')||(LA17_0 >= ']' && LA17_0 <= '\uFFFF')) ) {
                    alt17=2;
                }


                switch (alt17) {
            	case 1 :
            	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3169:14: ESC_SEQ
            	    {
            	    mESC_SEQ(); 


            	    }
            	    break;
            	case 2 :
            	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3169:24: ~ ( '\"' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "CHAR"
    public final void mCHAR() throws RecognitionException {
        try {
            int _type = CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3172:5: ( '\\'' ( ESC_SEQ |~ ( '\\'' | '\\\\' ) ) '\\'' )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3172:8: '\\'' ( ESC_SEQ |~ ( '\\'' | '\\\\' ) ) '\\''
            {
            match('\''); 

            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3172:13: ( ESC_SEQ |~ ( '\\'' | '\\\\' ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='\\') ) {
                alt18=1;
            }
            else if ( ((LA18_0 >= '\u0000' && LA18_0 <= '&')||(LA18_0 >= '(' && LA18_0 <= '[')||(LA18_0 >= ']' && LA18_0 <= '\uFFFF')) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }
            switch (alt18) {
                case 1 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3172:15: ESC_SEQ
                    {
                    mESC_SEQ(); 


                    }
                    break;
                case 2 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3172:25: ~ ( '\\'' | '\\\\' )
                    {
                    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }


            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CHAR"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3177:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3177:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3177:22: ( '+' | '-' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='+'||LA19_0=='-') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }


            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3177:33: ( '0' .. '9' )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0 >= '0' && LA20_0 <= '9')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3180:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "ESC_SEQ"
    public final void mESC_SEQ() throws RecognitionException {
        try {
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3185:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt21=3;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt21=1;
                    }
                    break;
                case 'u':
                    {
                    alt21=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt21=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 21, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }
            switch (alt21) {
                case 1 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3185:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 

                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3186:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 


                    }
                    break;
                case 3 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3187:9: OCTAL_ESC
                    {
                    mOCTAL_ESC(); 


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ESC_SEQ"

    // $ANTLR start "OCTAL_ESC"
    public final void mOCTAL_ESC() throws RecognitionException {
        try {
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3192:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt22=3;
            int LA22_0 = input.LA(1);

            if ( (LA22_0=='\\') ) {
                int LA22_1 = input.LA(2);

                if ( ((LA22_1 >= '0' && LA22_1 <= '3')) ) {
                    int LA22_2 = input.LA(3);

                    if ( ((LA22_2 >= '0' && LA22_2 <= '7')) ) {
                        int LA22_4 = input.LA(4);

                        if ( ((LA22_4 >= '0' && LA22_4 <= '7')) ) {
                            alt22=1;
                        }
                        else {
                            alt22=2;
                        }
                    }
                    else {
                        alt22=3;
                    }
                }
                else if ( ((LA22_1 >= '4' && LA22_1 <= '7')) ) {
                    int LA22_3 = input.LA(3);

                    if ( ((LA22_3 >= '0' && LA22_3 <= '7')) ) {
                        alt22=2;
                    }
                    else {
                        alt22=3;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }
            switch (alt22) {
                case 1 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3192:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3193:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 3 :
                    // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3194:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OCTAL_ESC"

    // $ANTLR start "UNICODE_ESC"
    public final void mUNICODE_ESC() throws RecognitionException {
        try {
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3199:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:3199:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 

            match('u'); 

            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNICODE_ESC"

    public void mTokens() throws RecognitionException {
        // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:8: ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | INCLUDE | ID | INT | FLOAT | COMMENT | WS | DYNAMIC_NAME | STRING | CHAR )
        int alt23=121;
        alt23 = dfa23.predict(input);
        switch (alt23) {
            case 1 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:10: T__18
                {
                mT__18(); 


                }
                break;
            case 2 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:16: T__19
                {
                mT__19(); 


                }
                break;
            case 3 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:22: T__20
                {
                mT__20(); 


                }
                break;
            case 4 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:28: T__21
                {
                mT__21(); 


                }
                break;
            case 5 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:34: T__22
                {
                mT__22(); 


                }
                break;
            case 6 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:40: T__23
                {
                mT__23(); 


                }
                break;
            case 7 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:46: T__24
                {
                mT__24(); 


                }
                break;
            case 8 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:52: T__25
                {
                mT__25(); 


                }
                break;
            case 9 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:58: T__26
                {
                mT__26(); 


                }
                break;
            case 10 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:64: T__27
                {
                mT__27(); 


                }
                break;
            case 11 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:70: T__28
                {
                mT__28(); 


                }
                break;
            case 12 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:76: T__29
                {
                mT__29(); 


                }
                break;
            case 13 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:82: T__30
                {
                mT__30(); 


                }
                break;
            case 14 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:88: T__31
                {
                mT__31(); 


                }
                break;
            case 15 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:94: T__32
                {
                mT__32(); 


                }
                break;
            case 16 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:100: T__33
                {
                mT__33(); 


                }
                break;
            case 17 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:106: T__34
                {
                mT__34(); 


                }
                break;
            case 18 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:112: T__35
                {
                mT__35(); 


                }
                break;
            case 19 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:118: T__36
                {
                mT__36(); 


                }
                break;
            case 20 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:124: T__37
                {
                mT__37(); 


                }
                break;
            case 21 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:130: T__38
                {
                mT__38(); 


                }
                break;
            case 22 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:136: T__39
                {
                mT__39(); 


                }
                break;
            case 23 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:142: T__40
                {
                mT__40(); 


                }
                break;
            case 24 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:148: T__41
                {
                mT__41(); 


                }
                break;
            case 25 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:154: T__42
                {
                mT__42(); 


                }
                break;
            case 26 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:160: T__43
                {
                mT__43(); 


                }
                break;
            case 27 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:166: T__44
                {
                mT__44(); 


                }
                break;
            case 28 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:172: T__45
                {
                mT__45(); 


                }
                break;
            case 29 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:178: T__46
                {
                mT__46(); 


                }
                break;
            case 30 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:184: T__47
                {
                mT__47(); 


                }
                break;
            case 31 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:190: T__48
                {
                mT__48(); 


                }
                break;
            case 32 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:196: T__49
                {
                mT__49(); 


                }
                break;
            case 33 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:202: T__50
                {
                mT__50(); 


                }
                break;
            case 34 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:208: T__51
                {
                mT__51(); 


                }
                break;
            case 35 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:214: T__52
                {
                mT__52(); 


                }
                break;
            case 36 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:220: T__53
                {
                mT__53(); 


                }
                break;
            case 37 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:226: T__54
                {
                mT__54(); 


                }
                break;
            case 38 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:232: T__55
                {
                mT__55(); 


                }
                break;
            case 39 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:238: T__56
                {
                mT__56(); 


                }
                break;
            case 40 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:244: T__57
                {
                mT__57(); 


                }
                break;
            case 41 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:250: T__58
                {
                mT__58(); 


                }
                break;
            case 42 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:256: T__59
                {
                mT__59(); 


                }
                break;
            case 43 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:262: T__60
                {
                mT__60(); 


                }
                break;
            case 44 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:268: T__61
                {
                mT__61(); 


                }
                break;
            case 45 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:274: T__62
                {
                mT__62(); 


                }
                break;
            case 46 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:280: T__63
                {
                mT__63(); 


                }
                break;
            case 47 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:286: T__64
                {
                mT__64(); 


                }
                break;
            case 48 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:292: T__65
                {
                mT__65(); 


                }
                break;
            case 49 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:298: T__66
                {
                mT__66(); 


                }
                break;
            case 50 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:304: T__67
                {
                mT__67(); 


                }
                break;
            case 51 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:310: T__68
                {
                mT__68(); 


                }
                break;
            case 52 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:316: T__69
                {
                mT__69(); 


                }
                break;
            case 53 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:322: T__70
                {
                mT__70(); 


                }
                break;
            case 54 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:328: T__71
                {
                mT__71(); 


                }
                break;
            case 55 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:334: T__72
                {
                mT__72(); 


                }
                break;
            case 56 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:340: T__73
                {
                mT__73(); 


                }
                break;
            case 57 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:346: T__74
                {
                mT__74(); 


                }
                break;
            case 58 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:352: T__75
                {
                mT__75(); 


                }
                break;
            case 59 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:358: T__76
                {
                mT__76(); 


                }
                break;
            case 60 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:364: T__77
                {
                mT__77(); 


                }
                break;
            case 61 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:370: T__78
                {
                mT__78(); 


                }
                break;
            case 62 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:376: T__79
                {
                mT__79(); 


                }
                break;
            case 63 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:382: T__80
                {
                mT__80(); 


                }
                break;
            case 64 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:388: T__81
                {
                mT__81(); 


                }
                break;
            case 65 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:394: T__82
                {
                mT__82(); 


                }
                break;
            case 66 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:400: T__83
                {
                mT__83(); 


                }
                break;
            case 67 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:406: T__84
                {
                mT__84(); 


                }
                break;
            case 68 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:412: T__85
                {
                mT__85(); 


                }
                break;
            case 69 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:418: T__86
                {
                mT__86(); 


                }
                break;
            case 70 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:424: T__87
                {
                mT__87(); 


                }
                break;
            case 71 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:430: T__88
                {
                mT__88(); 


                }
                break;
            case 72 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:436: T__89
                {
                mT__89(); 


                }
                break;
            case 73 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:442: T__90
                {
                mT__90(); 


                }
                break;
            case 74 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:448: T__91
                {
                mT__91(); 


                }
                break;
            case 75 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:454: T__92
                {
                mT__92(); 


                }
                break;
            case 76 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:460: T__93
                {
                mT__93(); 


                }
                break;
            case 77 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:466: T__94
                {
                mT__94(); 


                }
                break;
            case 78 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:472: T__95
                {
                mT__95(); 


                }
                break;
            case 79 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:478: T__96
                {
                mT__96(); 


                }
                break;
            case 80 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:484: T__97
                {
                mT__97(); 


                }
                break;
            case 81 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:490: T__98
                {
                mT__98(); 


                }
                break;
            case 82 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:496: T__99
                {
                mT__99(); 


                }
                break;
            case 83 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:502: T__100
                {
                mT__100(); 


                }
                break;
            case 84 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:509: T__101
                {
                mT__101(); 


                }
                break;
            case 85 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:516: T__102
                {
                mT__102(); 


                }
                break;
            case 86 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:523: T__103
                {
                mT__103(); 


                }
                break;
            case 87 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:530: T__104
                {
                mT__104(); 


                }
                break;
            case 88 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:537: T__105
                {
                mT__105(); 


                }
                break;
            case 89 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:544: T__106
                {
                mT__106(); 


                }
                break;
            case 90 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:551: T__107
                {
                mT__107(); 


                }
                break;
            case 91 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:558: T__108
                {
                mT__108(); 


                }
                break;
            case 92 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:565: T__109
                {
                mT__109(); 


                }
                break;
            case 93 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:572: T__110
                {
                mT__110(); 


                }
                break;
            case 94 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:579: T__111
                {
                mT__111(); 


                }
                break;
            case 95 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:586: T__112
                {
                mT__112(); 


                }
                break;
            case 96 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:593: T__113
                {
                mT__113(); 


                }
                break;
            case 97 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:600: T__114
                {
                mT__114(); 


                }
                break;
            case 98 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:607: T__115
                {
                mT__115(); 


                }
                break;
            case 99 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:614: T__116
                {
                mT__116(); 


                }
                break;
            case 100 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:621: T__117
                {
                mT__117(); 


                }
                break;
            case 101 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:628: T__118
                {
                mT__118(); 


                }
                break;
            case 102 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:635: T__119
                {
                mT__119(); 


                }
                break;
            case 103 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:642: T__120
                {
                mT__120(); 


                }
                break;
            case 104 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:649: T__121
                {
                mT__121(); 


                }
                break;
            case 105 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:656: T__122
                {
                mT__122(); 


                }
                break;
            case 106 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:663: T__123
                {
                mT__123(); 


                }
                break;
            case 107 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:670: T__124
                {
                mT__124(); 


                }
                break;
            case 108 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:677: T__125
                {
                mT__125(); 


                }
                break;
            case 109 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:684: T__126
                {
                mT__126(); 


                }
                break;
            case 110 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:691: T__127
                {
                mT__127(); 


                }
                break;
            case 111 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:698: T__128
                {
                mT__128(); 


                }
                break;
            case 112 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:705: T__129
                {
                mT__129(); 


                }
                break;
            case 113 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:712: INCLUDE
                {
                mINCLUDE(); 


                }
                break;
            case 114 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:720: ID
                {
                mID(); 


                }
                break;
            case 115 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:723: INT
                {
                mINT(); 


                }
                break;
            case 116 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:727: FLOAT
                {
                mFLOAT(); 


                }
                break;
            case 117 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:733: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 118 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:741: WS
                {
                mWS(); 


                }
                break;
            case 119 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:744: DYNAMIC_NAME
                {
                mDYNAMIC_NAME(); 


                }
                break;
            case 120 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:757: STRING
                {
                mSTRING(); 


                }
                break;
            case 121 :
                // /Users/ernstl/PostDoc/BU/Eugene/ecosystem/workspace/eugene-v1.9.1/grammar/Eugene.g:1:764: CHAR
                {
                mCHAR(); 


                }
                break;

        }

    }


    protected DFA12 dfa12 = new DFA12(this);
    protected DFA23 dfa23 = new DFA23(this);
    static final String DFA12_eotS =
        "\5\uffff";
    static final String DFA12_eofS =
        "\5\uffff";
    static final String DFA12_minS =
        "\2\56\3\uffff";
    static final String DFA12_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\1\1\1\3";
    static final String DFA12_specialS =
        "\5\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
            "",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "3145:1: FLOAT : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT );";
        }
    }
    static final String DFA23_eotS =
        "\5\uffff\1\72\3\uffff\1\74\1\77\2\uffff\1\101\1\103\1\105\21\64"+
        "\3\uffff\15\64\4\uffff\1\u0085\20\uffff\26\64\1\u009f\1\u00a1\16"+
        "\64\1\u00b0\7\64\1\u00b8\20\64\3\uffff\2\64\1\u00d0\22\64\1\u00e8"+
        "\1\64\1\uffff\1\64\1\uffff\13\64\1\u00f6\2\64\1\uffff\4\64\1\u00fd"+
        "\1\64\1\u00ff\1\uffff\5\64\1\u0105\13\64\1\u0111\2\64\1\u0114\1"+
        "\64\2\uffff\27\64\1\uffff\1\u0133\1\64\1\u0136\4\64\1\u013b\1\u013c"+
        "\2\64\1\u013f\1\u0140\1\uffff\2\64\1\u0143\3\64\1\uffff\1\64\1\uffff"+
        "\5\64\1\uffff\6\64\1\u0153\1\u0154\2\64\1\u0157\1\uffff\1\64\2\uffff"+
        "\1\u015a\7\64\1\u0162\12\64\1\u016d\12\64\1\uffff\1\u0178\1\64\1"+
        "\uffff\4\64\3\uffff\1\64\2\uffff\1\64\1\u0185\1\uffff\1\64\1\u0187"+
        "\11\64\1\u0192\3\64\2\uffff\2\64\1\uffff\1\u0198\2\uffff\5\64\1"+
        "\u019f\1\u01a0\1\uffff\2\64\1\u01a3\1\u01a5\1\64\1\u01a7\4\64\1"+
        "\uffff\1\u01ac\2\64\1\u01af\6\64\1\uffff\14\64\1\uffff\1\u01c2\1"+
        "\uffff\2\64\1\u01c5\5\64\1\u01cc\1\64\1\uffff\1\64\1\u01cf\1\u01d0"+
        "\1\u01d1\1\64\2\uffff\5\64\2\uffff\2\64\1\uffff\1\64\1\uffff\1\64"+
        "\1\uffff\1\u01dd\1\u01de\1\u01df\1\64\1\uffff\1\u01e1\1\64\1\uffff"+
        "\4\64\1\u01e7\1\u01e8\3\64\1\u01ec\7\64\1\u01f4\1\uffff\2\64\1\uffff"+
        "\3\64\1\u01fa\1\64\1\u01fc\1\uffff\1\u01fd\1\u01fe\3\uffff\1\64"+
        "\1\uffff\5\64\1\u0206\2\64\1\u0209\3\uffff\1\64\1\uffff\1\u020b"+
        "\4\64\2\uffff\1\u0210\1\u0211\1\64\1\uffff\1\u0213\6\64\1\uffff"+
        "\1\u021a\1\u021b\2\64\2\uffff\1\u021e\3\uffff\1\64\1\uffff\1\u0221"+
        "\4\64\1\uffff\2\64\1\uffff\1\64\1\uffff\1\64\1\u022a\2\64\2\uffff"+
        "\1\u022d\1\uffff\6\64\2\uffff\2\64\1\uffff\1\64\2\uffff\1\u0238"+
        "\1\u0239\1\u023a\1\64\1\u023c\1\u023d\1\u023e\1\64\1\uffff\1\u0240"+
        "\1\64\1\uffff\1\u0242\4\64\1\u0247\1\u0248\1\64\1\u024a\4\uffff"+
        "\1\u024d\3\uffff\1\u024e\1\uffff\1\u024f\1\uffff\1\u0250\1\u0251"+
        "\1\u0252\1\64\2\uffff\1\u0254\11\uffff\1\u0255\2\uffff";
    static final String DFA23_eofS =
        "\u0256\uffff";
    static final String DFA23_minS =
        "\1\11\4\uffff\1\52\3\uffff\1\60\1\52\2\uffff\3\75\1\106\1\105\1"+
        "\117\1\122\1\116\1\145\1\116\1\105\1\101\1\105\1\116\1\141\1\105"+
        "\1\102\1\110\1\111\1\117\3\uffff\1\157\1\145\1\154\1\141\1\145\1"+
        "\146\1\141\1\165\2\145\1\141\1\157\1\150\4\uffff\1\56\5\uffff\1"+
        "\144\12\uffff\1\124\1\114\1\104\1\163\1\106\2\116\1\154\1\111\1"+
        "\166\1\104\1\125\1\101\1\156\1\104\1\141\1\106\1\124\1\122\1\130"+
        "\1\124\1\164\2\60\1\162\1\157\1\120\1\107\1\147\1\154\1\117\1\115"+
        "\1\101\1\105\1\124\1\122\1\157\1\160\1\60\1\163\1\160\1\154\1\145"+
        "\1\162\1\156\1\164\1\60\1\160\1\143\1\105\1\170\1\155\1\162\1\147"+
        "\1\151\1\155\1\166\1\172\1\162\1\123\1\165\1\164\1\151\1\uffff\1"+
        "\53\1\144\1\105\1\137\1\60\1\145\1\117\1\104\1\124\1\154\1\126\1"+
        "\151\1\123\1\101\1\103\1\142\1\125\1\124\1\147\1\124\1\103\1\105"+
        "\1\124\1\60\1\145\1\uffff\1\110\1\uffff\1\164\1\160\1\122\1\110"+
        "\1\151\1\145\1\114\1\105\1\122\1\116\1\110\1\60\1\154\1\164\1\uffff"+
        "\1\145\1\157\1\163\1\170\1\60\1\143\1\60\1\uffff\1\157\1\164\1\154"+
        "\1\155\1\104\1\60\1\155\1\145\1\156\1\144\1\157\1\165\2\145\1\151"+
        "\2\145\1\60\1\154\1\60\1\120\1\122\1\60\1\uffff\1\162\1\122\1\123"+
        "\1\101\1\145\1\105\1\143\1\127\1\114\1\124\1\141\1\103\1\101\1\145"+
        "\1\124\1\110\2\124\1\117\1\121\1\117\1\110\1\111\1\uffff\1\60\1"+
        "\117\1\60\1\145\1\105\1\124\1\163\2\60\1\137\1\124\2\60\1\uffff"+
        "\1\145\1\150\1\60\1\162\1\145\1\151\1\uffff\1\164\1\uffff\1\162"+
        "\1\141\1\165\1\160\1\145\1\uffff\1\165\1\157\1\164\1\165\1\166\1"+
        "\162\2\60\1\143\1\161\1\60\1\uffff\1\145\1\162\1\uffff\1\60\1\106"+
        "\3\105\1\111\1\164\1\105\1\60\1\111\1\143\1\123\1\145\1\111\1\123"+
        "\1\114\1\156\1\105\1\116\1\60\1\117\1\105\1\110\1\117\1\116\1\125"+
        "\1\101\1\122\1\105\1\124\1\uffff\1\60\1\171\1\uffff\1\162\1\123"+
        "\1\124\1\164\2\uffff\1\60\1\123\2\uffff\1\141\1\60\1\uffff\1\164"+
        "\1\60\1\142\1\151\1\164\1\156\1\144\1\164\1\160\1\164\1\156\1\60"+
        "\1\143\1\145\1\156\2\uffff\1\164\1\165\1\uffff\1\60\1\157\1\uffff"+
        "\1\124\2\106\1\130\1\107\2\60\1\uffff\1\116\1\164\2\60\1\124\1\60"+
        "\1\131\1\153\1\123\1\103\1\uffff\1\60\1\123\1\101\1\60\1\124\1\101"+
        "\1\103\1\105\1\116\1\110\1\uffff\1\160\1\164\1\123\1\117\1\162\1"+
        "\106\3\105\1\111\1\127\1\156\1\uffff\1\60\1\uffff\1\154\1\157\1"+
        "\60\1\143\1\145\1\171\1\164\1\145\1\60\1\156\1\uffff\1\164\3\60"+
        "\1\145\1\uffff\1\160\1\105\1\117\2\124\1\110\2\uffff\1\123\1\151"+
        "\1\uffff\1\171\1\uffff\1\110\1\uffff\3\60\1\105\1\uffff\1\60\1\116"+
        "\1\uffff\1\101\1\114\2\124\2\60\1\145\1\171\1\105\1\60\1\171\1\124"+
        "\2\106\1\130\1\107\1\111\1\60\1\uffff\1\145\1\156\1\uffff\1\145"+
        "\1\151\1\11\1\60\1\150\1\60\1\uffff\2\60\3\uffff\1\156\1\145\2\122"+
        "\3\124\1\60\1\157\1\160\1\60\3\uffff\1\117\1\uffff\1\60\1\111\1"+
        "\123\1\114\1\110\2\uffff\2\60\1\123\1\uffff\1\60\1\105\1\117\2\124"+
        "\1\110\1\124\1\uffff\2\60\1\157\1\141\2\uffff\1\60\3\uffff\1\143"+
        "\1\162\1\60\1\105\2\117\1\124\1\uffff\1\156\1\145\1\uffff\1\106"+
        "\1\uffff\1\116\1\60\1\131\1\101\2\uffff\1\60\1\uffff\2\122\3\124"+
        "\1\110\2\uffff\1\146\1\164\1\uffff\1\145\1\164\1\uffff\3\60\1\117"+
        "\3\60\1\123\1\uffff\1\60\1\116\1\uffff\1\60\1\105\2\117\1\124\2"+
        "\60\1\145\1\60\1\151\3\uffff\1\60\3\uffff\1\60\1\uffff\1\60\1\uffff"+
        "\3\60\1\117\2\uffff\1\60\11\uffff\1\60\2\uffff";
    static final String DFA23_maxS =
        "\1\175\4\uffff\1\52\3\uffff\1\141\1\57\2\uffff\3\75\1\163\1\111"+
        "\1\157\1\145\1\130\1\145\1\155\1\105\1\117\1\157\1\122\1\162\1\165"+
        "\1\124\1\110\1\111\1\117\3\uffff\2\157\1\170\1\165\1\145\1\163\1"+
        "\141\1\165\1\162\1\145\1\164\1\170\1\150\4\uffff\1\172\5\uffff\1"+
        "\144\12\uffff\1\124\1\114\1\104\1\163\1\106\2\116\1\154\1\111\1"+
        "\166\1\104\1\125\1\101\1\156\1\123\1\141\1\106\1\124\1\122\1\130"+
        "\1\124\1\164\2\172\1\162\1\157\1\120\1\107\1\147\1\154\1\117\1\115"+
        "\1\101\1\105\1\124\1\122\1\157\1\160\1\172\1\163\1\160\1\154\1\145"+
        "\1\162\1\156\1\164\1\172\1\160\1\163\1\105\1\170\1\155\1\162\1\147"+
        "\1\157\1\164\1\166\1\172\1\162\1\123\1\165\1\164\1\151\1\uffff\1"+
        "\172\1\144\1\105\1\137\1\172\1\145\1\117\1\104\1\124\1\154\1\126"+
        "\1\151\1\123\1\101\1\103\1\142\1\125\1\124\1\147\1\124\1\103\1\105"+
        "\1\124\1\172\1\145\1\uffff\1\110\1\uffff\1\164\1\160\1\122\1\110"+
        "\1\151\1\145\1\114\1\105\1\122\1\116\1\110\1\172\1\154\1\164\1\uffff"+
        "\1\145\1\157\1\163\1\170\1\172\1\143\1\172\1\uffff\1\157\1\164\1"+
        "\154\1\155\1\104\1\172\1\155\1\145\1\156\1\144\1\157\1\165\2\145"+
        "\1\151\2\145\1\172\1\154\1\71\1\120\1\122\1\172\1\uffff\1\162\1"+
        "\122\1\123\1\101\1\145\1\105\1\143\1\127\1\114\1\124\1\141\1\103"+
        "\1\101\1\145\1\124\1\110\2\124\1\117\1\130\1\117\1\110\1\111\1\uffff"+
        "\1\172\1\117\1\172\1\145\1\105\1\124\1\163\2\172\1\137\1\124\2\172"+
        "\1\uffff\1\145\1\150\1\172\1\162\1\145\1\151\1\uffff\1\164\1\uffff"+
        "\1\162\1\141\1\165\1\160\1\145\1\uffff\1\165\1\157\1\164\1\165\1"+
        "\166\1\162\2\172\1\143\1\161\1\172\1\uffff\1\145\1\162\1\uffff\1"+
        "\172\1\106\3\105\1\111\1\164\1\105\1\172\1\111\1\143\1\123\1\145"+
        "\1\111\1\123\1\114\1\156\1\105\1\116\1\172\1\117\1\105\1\110\1\117"+
        "\1\116\1\125\1\101\1\122\1\105\1\124\1\uffff\1\172\1\171\1\uffff"+
        "\1\162\1\123\1\124\1\164\2\uffff\1\172\1\123\2\uffff\1\141\1\172"+
        "\1\uffff\1\164\1\172\1\142\1\151\1\164\1\156\1\144\1\164\1\160\1"+
        "\164\1\156\1\172\1\143\1\145\1\156\2\uffff\1\164\1\165\1\uffff\1"+
        "\172\1\157\1\uffff\1\124\2\106\1\130\1\107\2\172\1\uffff\1\116\1"+
        "\164\2\172\1\124\1\172\1\131\1\153\1\123\1\103\1\uffff\1\172\1\123"+
        "\1\101\1\172\1\124\1\101\1\103\1\105\1\116\1\110\1\uffff\1\160\1"+
        "\164\1\123\1\117\1\162\1\106\3\105\1\111\1\127\1\156\1\uffff\1\172"+
        "\1\uffff\1\154\1\157\1\172\1\164\1\145\1\171\1\164\1\145\1\172\1"+
        "\156\1\uffff\1\164\3\172\1\145\1\uffff\1\160\1\105\1\117\2\124\1"+
        "\110\2\uffff\1\123\1\151\1\uffff\1\171\1\uffff\1\110\1\uffff\3\172"+
        "\1\105\1\uffff\1\172\1\116\1\uffff\1\101\1\114\2\124\2\172\1\145"+
        "\1\171\1\105\1\172\1\171\1\124\2\106\1\130\1\107\1\111\1\172\1\uffff"+
        "\1\145\1\156\1\uffff\1\145\1\151\1\40\1\172\1\150\1\172\1\uffff"+
        "\2\172\3\uffff\1\156\1\145\2\122\3\124\1\172\1\157\1\160\1\172\3"+
        "\uffff\1\117\1\uffff\1\172\1\111\1\123\1\114\1\110\2\uffff\2\172"+
        "\1\123\1\uffff\1\172\1\105\1\117\2\124\1\110\1\124\1\uffff\2\172"+
        "\1\157\1\141\2\uffff\1\172\3\uffff\1\143\1\162\1\172\1\105\2\117"+
        "\1\124\1\uffff\1\156\1\145\1\uffff\1\106\1\uffff\1\116\1\172\1\131"+
        "\1\101\2\uffff\1\172\1\uffff\2\122\3\124\1\110\2\uffff\1\146\1\164"+
        "\1\uffff\1\145\1\164\1\uffff\3\172\1\117\3\172\1\123\1\uffff\1\172"+
        "\1\116\1\uffff\1\172\1\105\2\117\1\124\2\172\1\145\1\172\1\171\3"+
        "\uffff\1\172\3\uffff\1\172\1\uffff\1\172\1\uffff\3\172\1\117\2\uffff"+
        "\1\172\11\uffff\1\172\2\uffff";
    static final String DFA23_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\uffff\1\6\1\7\1\10\2\uffff\1\16\1\17"+
        "\24\uffff\1\114\1\115\1\116\15\uffff\1\156\1\157\1\160\1\162\1\uffff"+
        "\1\166\1\170\1\171\1\167\1\5\1\uffff\1\11\1\164\1\165\1\15\1\21"+
        "\1\20\1\23\1\22\1\25\1\24\77\uffff\1\163\31\uffff\1\70\1\uffff\1"+
        "\71\16\uffff\1\121\7\uffff\1\131\27\uffff\1\34\27\uffff\1\60\15"+
        "\uffff\1\113\6\uffff\1\126\1\uffff\1\130\5\uffff\1\137\13\uffff"+
        "\1\154\2\uffff\1\12\36\uffff\1\67\2\uffff\1\73\4\uffff\1\101\1\102"+
        "\2\uffff\1\111\1\112\2\uffff\1\122\17\uffff\1\147\1\150\2\uffff"+
        "\1\153\2\uffff\1\26\7\uffff\1\37\12\uffff\1\53\12\uffff\1\72\14"+
        "\uffff\1\120\1\uffff\1\124\12\uffff\1\142\5\uffff\1\155\6\uffff"+
        "\1\35\1\36\2\uffff\1\42\1\uffff\1\43\1\uffff\1\46\4\uffff\1\54\2"+
        "\uffff\1\57\22\uffff\1\123\2\uffff\1\132\6\uffff\1\141\2\uffff\1"+
        "\145\1\146\1\151\13\uffff\1\47\1\50\1\51\1\uffff\1\55\5\uffff\1"+
        "\65\1\66\3\uffff\1\77\7\uffff\1\117\4\uffff\1\161\1\135\1\uffff"+
        "\1\140\1\143\1\144\7\uffff\1\40\2\uffff\1\45\1\uffff\1\56\4\uffff"+
        "\1\74\1\75\1\uffff\1\100\6\uffff\1\125\1\127\2\uffff\1\136\2\uffff"+
        "\1\27\10\uffff\1\62\2\uffff\1\76\12\uffff\1\30\1\31\1\32\1\uffff"+
        "\1\41\1\44\1\52\1\uffff\1\63\1\uffff\1\103\4\uffff\1\110\1\133\1"+
        "\uffff\1\152\1\13\1\14\1\33\1\61\1\64\1\104\1\105\1\106\1\uffff"+
        "\1\134\1\107";
    static final String DFA23_specialS =
        "\u0256\uffff}>";
    static final String[] DFA23_transitionS = {
            "\2\66\2\uffff\1\66\22\uffff\1\66\1\1\1\67\3\uffff\1\2\1\70\1"+
            "\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\12\65\1\13\1\14\1\15\1\16"+
            "\1\17\2\uffff\1\20\1\21\1\22\1\23\1\24\1\64\1\25\1\64\1\26\2"+
            "\64\1\27\1\30\1\31\1\32\1\33\1\64\1\34\1\35\1\36\2\64\1\37\1"+
            "\40\2\64\1\41\1\uffff\1\42\1\43\1\64\1\uffff\1\64\1\44\1\64"+
            "\1\45\1\46\1\47\1\50\1\64\1\51\3\64\1\52\1\53\1\64\1\54\1\64"+
            "\1\55\1\56\1\57\2\64\1\60\3\64\1\61\1\62\1\63",
            "",
            "",
            "",
            "",
            "\1\71",
            "",
            "",
            "",
            "\12\75\47\uffff\1\73",
            "\1\76\4\uffff\1\76",
            "",
            "",
            "\1\100",
            "\1\102",
            "\1\104",
            "\1\106\5\uffff\1\107\1\uffff\1\110\44\uffff\1\111",
            "\1\112\3\uffff\1\113",
            "\1\114\37\uffff\1\115",
            "\1\116\22\uffff\1\117",
            "\1\120\2\uffff\1\121\6\uffff\1\122",
            "\1\123",
            "\1\124\36\uffff\1\125",
            "\1\126",
            "\1\127\15\uffff\1\130",
            "\1\131\11\uffff\1\132\37\uffff\1\133",
            "\1\134\3\uffff\1\135",
            "\1\136\20\uffff\1\137",
            "\1\140\3\uffff\1\141\33\uffff\1\142\17\uffff\1\143",
            "\1\144\14\uffff\1\145\4\uffff\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "",
            "",
            "",
            "\1\152",
            "\1\153\11\uffff\1\154",
            "\1\155\13\uffff\1\156",
            "\1\157\12\uffff\1\160\2\uffff\1\161\5\uffff\1\162",
            "\1\163",
            "\1\164\6\uffff\1\165\1\166\4\uffff\1\167",
            "\1\170",
            "\1\171",
            "\1\172\3\uffff\1\173\10\uffff\1\174",
            "\1\175",
            "\1\176\7\uffff\1\177\12\uffff\1\u0080",
            "\1\u0081\2\uffff\1\u0082\5\uffff\1\u0083",
            "\1\u0084",
            "",
            "",
            "",
            "",
            "\1\75\1\uffff\12\65\7\uffff\4\64\1\u0086\25\64\4\uffff\1\64"+
            "\1\uffff\4\64\1\u0086\25\64",
            "",
            "",
            "",
            "",
            "",
            "\1\u0087",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096\16\uffff\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\23\64\1\u00a0\6\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "\1\u00b7",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00b9",
            "\1\u00bb\17\uffff\1\u00ba",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "\1\u00c0",
            "\1\u00c1\5\uffff\1\u00c2",
            "\1\u00c3\6\uffff\1\u00c4",
            "\1\u00c5",
            "\1\u00c6",
            "\1\u00c7",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cb",
            "",
            "\1\75\1\uffff\1\75\2\uffff\12\u00cc\7\uffff\32\64\4\uffff\1"+
            "\64\1\uffff\32\64",
            "\1\u00cd",
            "\1\u00ce",
            "\1\u00cf",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\12\64\7\uffff\2\64\1\u00e3\1\64\1\u00e4\7\64\1\u00e5\6\64"+
            "\1\u00e6\2\64\1\u00e7\3\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00e9",
            "",
            "\1\u00ea",
            "",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed",
            "\1\u00ee",
            "\1\u00ef",
            "\1\u00f0",
            "\1\u00f1",
            "\1\u00f2",
            "\1\u00f3",
            "\1\u00f4",
            "\1\u00f5",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00f7",
            "\1\u00f8",
            "",
            "\1\u00f9",
            "\1\u00fa",
            "\1\u00fb",
            "\1\u00fc",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00fe",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103",
            "\1\u0104",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0106",
            "\1\u0107",
            "\1\u0108",
            "\1\u0109",
            "\1\u010a",
            "\1\u010b",
            "\1\u010c",
            "\1\u010d",
            "\1\u010e",
            "\1\u010f",
            "\1\u0110",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0112",
            "\12\75",
            "\1\u0113",
            "\1\u0115",
            "\12\64\7\uffff\1\u0116\1\u0117\11\64\1\u0118\1\64\1\u0119\3"+
            "\64\1\u011a\10\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u011b",
            "\1\u011c",
            "\1\u011d",
            "\1\u011e",
            "\1\u011f",
            "\1\u0120",
            "\1\u0121",
            "\1\u0122",
            "\1\u0123",
            "\1\u0124",
            "\1\u0125",
            "\1\u0126",
            "\1\u0127",
            "\1\u0128",
            "\1\u0129",
            "\1\u012a",
            "\1\u012b",
            "\1\u012c",
            "\1\u012d",
            "\1\u012e\6\uffff\1\u012f",
            "\1\u0130",
            "\1\u0131",
            "\1\u0132",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0134",
            "\12\64\7\uffff\23\64\1\u0135\6\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0137",
            "\1\u0138",
            "\1\u0139",
            "\1\u013a",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u013d",
            "\1\u013e",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0141",
            "\1\u0142",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0144",
            "\1\u0145",
            "\1\u0146",
            "",
            "\1\u0147",
            "",
            "\1\u0148",
            "\1\u0149",
            "\1\u014a",
            "\1\u014b",
            "\1\u014c",
            "",
            "\1\u014d",
            "\1\u014e",
            "\1\u014f",
            "\1\u0150",
            "\1\u0151",
            "\1\u0152",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0155",
            "\1\u0156",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0158",
            "\1\u0159",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u015b",
            "\1\u015c",
            "\1\u015d",
            "\1\u015e",
            "\1\u015f",
            "\1\u0160",
            "\1\u0161",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0163",
            "\1\u0164",
            "\1\u0165",
            "\1\u0166",
            "\1\u0167",
            "\1\u0168",
            "\1\u0169",
            "\1\u016a",
            "\1\u016b",
            "\1\u016c",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u016e",
            "\1\u016f",
            "\1\u0170",
            "\1\u0171",
            "\1\u0172",
            "\1\u0173",
            "\1\u0174",
            "\1\u0175",
            "\1\u0176",
            "\1\u0177",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0179",
            "",
            "\1\u017a",
            "\1\u017b",
            "\1\u017c",
            "\1\u017d",
            "",
            "",
            "\12\64\7\uffff\1\u017e\1\u017f\11\64\1\u0180\1\64\1\u0181\3"+
            "\64\1\u0182\10\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0183",
            "",
            "",
            "\1\u0184",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0186",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0188",
            "\1\u0189",
            "\1\u018a",
            "\1\u018b",
            "\1\u018c",
            "\1\u018d",
            "\1\u018e",
            "\1\u018f",
            "\1\u0190",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\13\64\1\u0191\16"+
            "\64",
            "\1\u0193",
            "\1\u0194",
            "\1\u0195",
            "",
            "",
            "\1\u0196",
            "\1\u0197",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0199",
            "",
            "\1\u019a",
            "\1\u019b",
            "\1\u019c",
            "\1\u019d",
            "\1\u019e",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01a1",
            "\1\u01a2",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\23\64\1\u01a4\6\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01a6",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01a8",
            "\1\u01a9",
            "\1\u01aa",
            "\1\u01ab",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01ad",
            "\1\u01ae",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01b0",
            "\1\u01b1",
            "\1\u01b2",
            "\1\u01b3",
            "\1\u01b4",
            "\1\u01b5",
            "",
            "\1\u01b6",
            "\1\u01b7",
            "\1\u01b8",
            "\1\u01b9",
            "\1\u01ba",
            "\1\u01bb",
            "\1\u01bc",
            "\1\u01bd",
            "\1\u01be",
            "\1\u01bf",
            "\1\u01c0",
            "\1\u01c1",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01c3",
            "\1\u01c4",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01c6\20\uffff\1\u01c7",
            "\1\u01c8",
            "\1\u01c9",
            "\1\u01ca",
            "\1\u01cb",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01cd",
            "",
            "\1\u01ce",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01d2",
            "",
            "\1\u01d3",
            "\1\u01d4",
            "\1\u01d5",
            "\1\u01d6",
            "\1\u01d7",
            "\1\u01d8",
            "",
            "",
            "\1\u01d9",
            "\1\u01da",
            "",
            "\1\u01db",
            "",
            "\1\u01dc",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01e0",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01e2",
            "",
            "\1\u01e3",
            "\1\u01e4",
            "\1\u01e5",
            "\1\u01e6",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01e9",
            "\1\u01ea",
            "\1\u01eb",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01ed",
            "\1\u01ee",
            "\1\u01ef",
            "\1\u01f0",
            "\1\u01f1",
            "\1\u01f2",
            "\1\u01f3",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u01f5",
            "\1\u01f6",
            "",
            "\1\u01f7",
            "\1\u01f8",
            "\2\u01f9\2\uffff\1\u01f9\22\uffff\1\u01f9",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u01fb",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "\1\u01ff",
            "\1\u0200",
            "\1\u0201",
            "\1\u0202",
            "\1\u0203",
            "\1\u0204",
            "\1\u0205",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0207",
            "\1\u0208",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "\1\u020a",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u020c",
            "\1\u020d",
            "\1\u020e",
            "\1\u020f",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0212",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0214",
            "\1\u0215",
            "\1\u0216",
            "\1\u0217",
            "\1\u0218",
            "\1\u0219",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u021c",
            "\1\u021d",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "\1\u021f",
            "\1\u0220",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0222",
            "\1\u0223",
            "\1\u0224",
            "\1\u0225",
            "",
            "\1\u0226",
            "\1\u0227",
            "",
            "\1\u0228",
            "",
            "\1\u0229",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u022b",
            "\1\u022c",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u022e",
            "\1\u022f",
            "\1\u0230",
            "\1\u0231",
            "\1\u0232",
            "\1\u0233",
            "",
            "",
            "\1\u0234",
            "\1\u0235",
            "",
            "\1\u0236",
            "\1\u0237",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u023b",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u023f",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0241",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0243",
            "\1\u0244",
            "\1\u0245",
            "\1\u0246",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0249",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u024b\17\uffff\1\u024c",
            "",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0253",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | INCLUDE | ID | INT | FLOAT | COMMENT | WS | DYNAMIC_NAME | STRING | CHAR );";
        }
    }
 

}
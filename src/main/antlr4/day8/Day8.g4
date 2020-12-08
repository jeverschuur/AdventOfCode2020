grammar Day8;

// Parser rules
input: instruction+;
instruction: action=INSTRUCTION offset=NUMBER;

// Lexer rules
fragment Sign: [+-];
fragment Digit: [0-9];

INSTRUCTION: 'nop' | 'acc' | 'jmp';        
NUMBER: Sign? Digit+; 
WS  :  [ \t\r\n\u000C]+ -> skip;
COMMENT:   '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT:   '//' ~[\r\n]* -> channel(HIDDEN);
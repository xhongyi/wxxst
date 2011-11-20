function [ word ] = char_recognition( chars, numChar, typeChar , stdX, stdY )
%UNTITLED8 Summary of this function goes here
%   Detailed explanation goes here
load ('trainingdata.mat');

word = ' ';
for i = 1: numChar
    
    if typeChar(i) == 1
        innerprod = zeros(1, 13);
        
        for j = 1: 13
            total = 0;
            tempChar = chars( :, :, i );
            tempChar = tempChar(:); 
            errorProj = stdChar1(:,:,j);
            innerprod(j) = norm(errorProj * tempChar );
        end
        
        [inn, ichar] = min(innerprod );
        
        word = [word, charName1(ichar) ];
        
    elseif typeChar(i) == 2
        
        innerprod = zeros(1, 8);
        
        for j = 1: 8
            total = 0;
            tempChar = chars( :, :, i );
            tempChar = tempChar(:);
            errorProj = stdChar2(:,:,j);
            innerprod(j) = norm(errorProj * tempChar );
        end
        
        [inn, ichar] = min(innerprod );
        word = [word, charName2(ichar) ];
        
    elseif typeChar(i) == 3
        
        innerprod = zeros(1, 5);
        
        for j = 1: 5
            total = 0;
            tempChar = chars( :, :, i );
            tempChar = tempChar(:);
            errorProj = stdChar3(:,:,j);
            innerprod(j) = norm(errorProj * tempChar );
        end
        
        [inn, ichar] = min(innerprod );
        word = [word, charName3(ichar) ];
        
    end
    
end


end


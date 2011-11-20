function [ array ] = moving_average( array, aveSize, length )

buff = zeros(1, aveSize );

summ = 0;
for i = 1 : aveSize
    buff(i) = array(i);
    summ = summ + array(i);
end
 
for i =5:length
    
    j = mod(i, aveSize) + 1;
    
    summ = summ - buff(j) + array(i);
    buff(j) = array(i) ; 
    
    array(i) = summ / aveSize;

end


function [ numChar, chars, typeChar ] = image_segmentation( image, inputX, inputY, windowX, windowY, stdX, stdY, demo )
%image segmentation function
%get characters in one word from the image
%image: gray scale image


%convert to binary image
[im, centerX, centerY ] = convert2binary_image( image, inputX, inputY, windowX, windowY );

%text line segmentation on y axis
[startY, stopY] = line_segmentation( im, centerX, centerY, windowX, windowY );

%word segmentation on x axis
[segCharX, numChar ] = char_segmentation(im, centerX, centerY, startY, stopY);

%find the char tpye
[ typeChar, segCharY ] = get_charType( im, startY, stopY, segCharX, numChar );

[ chars ] =  interploate2standard(im, segCharX, segCharY, numChar, stdX, stdY );

%display result
if demo
    figure;
    subplot(321)
    imagesc(image); colormap gray;
    grid on;
    subplot(322)
    imagesc( im ); colormap gray;
    title 'image window';
    subplot(312)
    imagesc( im( startY:stopY, : ) ); colormap gray;
    title 'line segmentation';
    
    
    for i = 1:numChar
        subplot(3, numChar, 2*numChar + i);
        temp = chars(:,:,i) ;
        temp = squeeze(temp);
        imagesc( temp );
        colormap gray;
    end
end

end



function [startY, stopY] = line_segmentation( im, centerX, centerY, windowX, windowY )

[ny nx] = size(im);

midValue = 0;
margin = 3;

for j = 1 : nx
    midValue = midValue + im(centerY, j);
end

projThresholdY = max ( midValue / 4 , 30 );
%fprintf('Middle point Y projection threshold is %d .\n', projThresholdY );


sizeUp = 0;
sizeDown = 0;

computeSize = round ( nx / 4 );
xstart = centerX - computeSize;
xstop  = centerX + computeSize;

for i = 1 : windowY - 1
    
    projUp = 0;
    projDown = 0;
    
    if sizeUp == 0 && centerY - i >= 1
        
        for j = xstart : xstop
            projUp = projUp + im ( centerY - i , j );
        end
        
        if projUp < projThresholdY
            sizeUp = i;
        end
    end
    
    if sizeDown == 0 && centerY + i <= ny
        for j = xstart : xstop
            projDown = projDown + im ( centerY + i, j );
        end
        
        if projDown < projThresholdY
            sizeDown = i;
        end
        
    end
end

%line position
startY = centerY - sizeUp - margin;
stopY  = centerY + sizeDown + margin;

end


function [segmentX, numChar ] = char_segmentation(im, centerX, centerY, startY, stopY)

[ny nx] = size(im);
projThresholdX = 2;
minLength = 2;
maxLength = 60;

%fprintf('Middle point X projection threshold is %d .\n', projThresholdX );

proj = zeros(1,nx);
for i = 1: nx
    temp = 0;
    for j = startY: stopY
        temp = temp + im(j,i);
    end
    proj(i)  = temp;
end

%apply a moving average filter, optional
proj = moving_average(proj, 5, nx);

%get a nice center
while ( proj(centerX) < 15 )
    centerX = centerX + 2;
    
    if centerX > nx
        fprintf ('Error 0000 \n');
        return;
    end
    
end



%left side
charLeng = 1;
blankLeng = 0;

segTemp = zeros(1,20);

idx = 1;

for i = centerX : -1 : 1
    
    if proj(i) > projThresholdX
        
        if charLeng == 0
            
            if blankLeng < minLength
                idx = idx - 1;
                charLeng = segTemp(idx - 1) - i;
                blankLeng = 0;
            else
                segTemp(idx) = i;
                idx = idx + 1;
                blankLeng = 0;
                charLeng = 1;
            end
        else
            charLeng = charLeng + 1;
        end
    else
        
        if blankLeng == 0
            
            segTemp(idx) = i;
            idx = idx + 1;
            charLeng = 0;
        end
        blankLeng = blankLeng + 1;
        
    end
    
    if blankLeng > maxLength
        break;
    end
end

%filp the array
segmentX = zeros(1, 30);
for i = 1:idx-1
    segmentX(i) = segTemp(idx - i );
end

%right side

charLeng = 1;
blankLeng = 0;

for i = centerX : nx
    
    if proj(i) > projThresholdX
        
        if charLeng == 0
            if blankLeng < minLength
                idx = idx - 1;
                charLeng = i - segementX(idx - 1);
                blankLeng = 0;
            else
                segmentX(idx) = i;
                idx = idx + 1;
                blankLeng = 0;
                charLeng = 1;
            end
        else
            charLeng = charLeng + 1;
        end
        
    else
        if blankLeng == 0
            segmentX(idx) = i;
            idx = idx + 1;
            charLeng = 0;
        end
        blankLeng = blankLeng + 1;
    end
    
    if blankLeng > maxLength
        break;
    end
end

numChar = ( idx - 1 ) / 2;

end

function [ typeChar, segCharY ] = get_charType( im, startY, stopY, segCharX, numChar )

long = floor((stopY - startY)/3);
short = 5;
margin = 3;
threshold = 20;

typeChar = zeros(1, numChar);
segCharY = zeros(1, numChar*2);

for i = 1:numChar
    
    total = 0;
    
    for j = stopY + short : stopY + long
        for k = segCharX(i*2 - 1) + margin : segCharX(i*2) - margin
            
            total = total + im( j, k );
            
        end
    end
    
    
    if total > threshold
        typeChar(i) = 3;
        segCharY(i*2-1) = startY;
        segCharY(i*2) = stopY + long;
    else
        
        total = 0;
        
        for j = startY - long : startY - short
            
            for k = segCharX(i*2 - 1) + margin : segCharX(i*2)  - margin
                total = total + im( j, k );
            end
        end
        
        if total > threshold
            typeChar(i) = 2;
            segCharY(i*2-1) = startY - long;
            segCharY(i*2) = stopY;
        else
            typeChar(i) = 1;
            segCharY(i*2-1) = startY;
            segCharY(i*2) = stopY;
        end
        
    end
    
end

end

function [ chars ] =  interploate2standard(im, segCharX, segCharY, numChar, stdX, stdY )

chars = zeros(stdY, stdX, numChar );

for i = 1 : numChar
    
    X1 =  segCharX(i*2-1);
    X2 =  segCharX(i*2);
    Y1 =  segCharY(i*2-1);
    Y2 =  segCharY(i*2);
    
    ix = ( X2 - X1) / stdX;
    iy = ( Y2 - Y1) / stdY;
    
    x = X1;
    y = Y1;
    
    for j = 1: stdY
        y = y + iy;
        x = X1;
        for k = 1: stdX
            
            chars(j, k, i ) =  im( floor(y), floor(x));
            x = x + ix;
        end
    end
    
end

end




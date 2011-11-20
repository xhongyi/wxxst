function [ im, centerX, centerY ] = convert2binary_image( image, inputX, inputY, windowX, windowY )

[ny nx] = size(image);

%Compute the projection profile
 pixelThreshold = compute_local_threshold( image, inputX, inputY, nx, ny );

startX = max(1, inputX - windowX );
stopX = min( nx, inputX + windowX );
startY = max(1, inputY - windowY );
stopY = min( ny, inputY + windowY );
centerX = inputX - startX;

%convert to binary image
im = image( startY:stopY , startX:stopX ) < pixelThreshold ;

centerX = inputX - startX;
centerY = inputY - startY;

end



function [ pixelThreshold ] = compute_local_threshold( image, inputX, inputY, nx, ny )

cutOff = 0.4;
size = 50;

%compute local threshold and convert to binary image
startX = max(1, inputX - size );
stopX = min( nx, inputX + size );
startY = max(1, inputY - size );
stopY = min( ny, inputY + size );


localMax = image( inputY, inputX );
localMin = localMax;


for i = startX : stopX
    for j = startY : stopY
        temp = image( j, i);
        
        if localMax < temp
            localMax = temp;
        end
        
        if localMin > temp
            localMin = temp;
        end
    end
end
%fprintf('Local pixel value: maximun = %d, minimum = %d \n', localMax, localMin );

pixelThreshold =  localMax * cutOff + localMin * ( 1 - cutOff );

%fprintf('Cutoff rate: %f, threshold: %f. \n' , cutOff, pixelThreshold );



end

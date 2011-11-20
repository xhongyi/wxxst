clear all;
close all;

stdX = 16;
stdY = 16;


windowX = 1000;
windowY = 150;

%read in the image
temp = imread('sample0.jpg' );
%convert RGB to GRAY
image = rgb2gray(temp);
[ numChar, chars, typeChar ] = image_segmentation(image, 1000, 1000, windowX, windowY, stdX, stdY, 1 );
[ word ] = char_recognition( chars, numChar, typeChar, stdX, stdY  );
fprintf('result: %s \n', word);

image = rgb2gray(temp);
[ numChar, chars, typeChar ] = image_segmentation(image, 1000, 750, windowX, windowY, stdX, stdY, 1 );
[ word ] = char_recognition( chars, numChar, typeChar , stdX, stdY );
fprintf('result: %s \n', word);

image = rgb2gray(temp);
[ numChar, chars, typeChar ] = image_segmentation(image, 1000, 1650, windowX, windowY, stdX, stdY, 1 );
[ word ] = char_recognition( chars, numChar, typeChar , stdX, stdY );
fprintf('result: %s \n', word);


return;








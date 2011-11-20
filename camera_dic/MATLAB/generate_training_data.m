function generate_training_data()

clear all;
close all;
clc

fprintf('Start to generate training data. \n');

stdX = 16;
stdY = 16;
demo = 1;

group1 = zeros(40, stdX * stdY, 13);
group2 = zeros(40, stdX * stdY, 8 );
group3 = zeros(40, stdX * stdY, 5 );


for i = 0:9
    
    X=[800, 800, 800,  800, 800, 800,  1800, 1800, 1800, 1800, 1800, 1800];
    Y=[200, 350, 550, 730, 880, 1080, 210, 400, 600, 780, 950 , 1150];
    
    switch i
        case {0, 1}
            X=[800, 800, 800,  800, 800, 800,  1800, 1800, 1800, 1800, 1800, 1800];
            Y=[200, 350, 550, 730, 880, 1080, 210, 400, 600, 780, 950 , 1150];
        case 2
            X=[800, 800, 800,  800, 800, 800,  1800, 1800, 1800, 1800, 1800, 1800];
            Y = [350, 500, 680, 800, 950, 1130, 350, 530, 700, 850, 1030, 1200 ];
        case 3
            X=[800, 800, 800,  800, 800, 800,  800, 800, 800, 800, 800, 1800];
            Y=[300, 480, 650, 800, 950, 1180, 1300, 950, 1650, 300, 480 , 680];
        case 4
            X=[800, 800, 800,  800, 800, 800,  800, 800, 800, 1800, 1800, 1800];
            Y=[200, 350, 550, 730, 880, 1100, 1280, 350, 1650, 200, 400 , 770];
        case 5
            X=[800, 800, 800,  800, 800, 800,  800, 800, 800, 1800, 1800, 1800];
            Y=[250, 410, 580, 750, 900, 1100, 1250, 410, 1600, 800, 970 , 1150];
        case 6
            X=[800, 800, 800,  800, 800, 800,  800, 800, 800, 1800, 1800, 1800];
            Y=[200, 350, 550, 730, 880, 1080, 1280, 730, 1650, 780, 950 , 1150];
        case 7
            X=[800, 800, 800,  800, 800, 800,  800, 800, 800, 1800, 1800, 1800];
            Y=[200, 350, 550, 730, 880, 1080, 1280, 730, 1650, 780, 950 , 1150];
        case 8
            X=[800, 800, 800,  800, 800, 800,  800, 800, 800, 1800, 1800, 1800];
            Y=[280, 430, 600, 800, 980, 1150, 1300, 980, 1640, 820, 1000, 1200];
        case 9
            X=[800, 800, 800,  800, 800, 800,  800, 800, 800, 1800, 1800, 1800];
            Y=[220, 400, 580, 750, 900, 1080, 1280, 750, 1650, 780, 980 , 1170];
    end
    
    filename = ['tdata/td' int2str(i+1) '.jpg' ];
    fprintf( 'Opening file: %s  \n', filename);
    
    [td1 , td2, td3] = read_training_data( X, Y, filename, stdX, stdY,demo );
    group1(i*4+1 : i*4+4 ,:,:) = td1;
    group2(i*4+1 : i*4+4 ,:,:) = td2;
    group3(i*4+1 : i*4+4 ,:,:) = td3;
    
    fprintf( 'Finish file: %s  \n', filename);
    close all;
    
    
end

charName1 = ['s', 'a', 'o', 'r', 'u', 'c', 't', 'e', 'm', 'n', 'x', 'v', 'z'];
charName2 = ['b', 'd', 'f', 'h', 'i', 'k', 'l', 't' ];
charName3 = ['g', 'j', 'p', 'q', 'y'];


%analysis

stdChar1 = zeros(stdX*stdY, stdX*stdY  , 13 );
stdChar2 = zeros(stdX*stdY, stdX*stdY  , 8 );
stdChar3 = zeros(stdX*stdY, stdX*stdY  , 5 );

for i = 1: 13
    temp = group1(:, :, i);
    [u d v] = svd (temp);
    pca = v(:,1:3);
    errorProjection = eye(stdX*stdY ) - pca*pca' ;
    stdChar1(:,:,i) = errorProjection;
    
end

stdChar1_new = zeros(stdX, stdY, 13);
for i = 1:13
    for j = 1:stdX
        for k = 1:stdY
            stdChar1_new(k,j,i) = stdChar1(k,j,i);
        end
    end
end

for i = 1: 8
    temp = group2(:, :, i);
    [u d v] = svd (temp);
    pca = v(:,1:3);
    errorProjection = eye(stdX*stdY ) - pca*pca' ;
    stdChar2(:,:,i) = errorProjection;
end

stdChar2_new = zeros(stdX, stdY, 8);
for i = 1:8
    for j = 1:stdX
        for k = 1:stdY
            stdChar2_new(k,j,i) = stdChar2(k,j,i);
        end
    end
end


for i = 1: 5
    temp = group3(:, :, i);
    [u d v] = svd (temp);
    pca = v(:,1:3);
    errorProjection = eye(stdX*stdY ) - pca*pca' ;
    stdChar3(:,:,i) = errorProjection;
end

stdChar3_new = zeros(stdX, stdY, 5);
for i = 1:5
    for j = 1:stdX
        for k= 1:stdY
            stdChar3_new(k,j,i) = stdChar3(k,j,i);
        end
    end
end


save( 'trainingdata.mat', 'charName1', 'charName2','charName3', 'stdChar1_new', 'stdChar2_new', 'stdChar3_new','stdChar1', 'stdChar2', 'stdChar3' );
for i = 1:3
    fprintf('Doing SVD: count down %d \n', i);
    pause(1);
end

close all;
clear all;
fprintf('Generate Training Data Success ! \n');


end

function [ td1 , td2, td3] = read_training_data( X, Y, filename, stdX, stdY, demo )


windowX = 700;
windowY = 130;

%read in the image
temp = imread( filename );
%convert RGB to GRAY
image = rgb2gray(temp);

td1 = zeros( 4, stdX*stdY, 13 );
td2 = zeros( 4, stdX*stdY, 8 );
td3 = zeros( 4, stdX*stdY, 5 );


for k = 0 : 3
    
    
    [ numChar, chars, typeChar ] = image_segmentation(image, X(k*3+1), Y(k*3+1), windowX, windowY, stdX, stdY, demo );
    for i = 1 : 13
        temp1 = chars(:, :, i);
        td1(k+1, :, i) = temp1(:);
    end
    
    
    [ numChar, chars, typeChar ] = image_segmentation(image, X(k*3+2), Y(k*3+2), windowX, windowY, stdX, stdY, demo );
    for i = 1 : 8
        temp2 = chars(:, :, i);
        td2(k+1, :, i) = temp2(:);
    end
    
    [ numChar, chars, typeChar ] = image_segmentation(image, X(k*3+3), Y(k*3+3), windowX, windowY, stdX, stdY, demo );
    for i = 1 : 5
        temp3 = chars(:, :, i);
        td3(k+1, :, i) = temp3(:);
    end
    
end

end
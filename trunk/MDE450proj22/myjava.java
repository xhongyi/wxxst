class myjava {
    private:
	    int image[][];
		int nx;   // size of image, x
		int ny;   // size of image, y
	    int im[][];
        int centerX;
        int centerY;
		int startPosY;
		int stopPosY;
		int segmentX[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    public:
        void convert2binary_image( int inputX, int inputY, int windowX, int windowY ) {
		    //Compute the projection profile
            double pixelThreshold = compute_local_threshold( inputX, inputY, nx, ny );
            int startX = max(1, inputX - windowX );
            int stopX = min( nx, inputX + windowX );
            int startY = max(1, inputY - windowY );
            int stopY = min( ny, inputY + windowY );

            //convert to binary image
			for ( int i = startY - 1; i < stopY, i++ ) {
                for ( int j = startX - 1; j < stopX; j++ ) {
				    im = (int) ( image[j][i] < pixelThreshold );
				}
            }
            centerX = inputX - startX;
            centerY = inputY - startY;
        }		
		double compute_local_threshold( int inputX, int inputY, int nx, int ny ) {
		    cutOff = 0.4;
		    size = 50;

            //compute local threshold and convert to binary image
            int startX = max(1, inputX - size );
            int stopX = min( nx, inputX + size );
            int startY = max(1, inputY - size );
            int stopY = min( ny, inputY + size );


            int localMax = image [inputY][inputX];
            int localMin = localMax;
            int temp;

            for (i = startX - 1;  i < stopX; i++ ) {
                for ( j = startY - 1; j < stopY; j++ ) {
                    temp = image[j][i];        
                    if ( localMax < temp )
                        localMax = temp;       
                    if ( localMin > temp )
                        localMin = temp;
                }
            }
            //fprintf('Local pixel value: maximun = %d, minimum = %d \n', localMax, localMin );

            double pixelThreshold =  localMax * cutOff + localMin * ( 1 - cutOff );
			return pixelThreshold;
		}
		void line_segmentation( int windowX, int windowY ) {
            midValue = 0;
            margin = 3;

            for ( int j = 0; j < nx; j++ ) {
                midValue = midValue + im[centerY][j];
			}

            int projThresholdY = max ( midValue / 4 , 30 );
            //fprintf('Middle point Y projection threshold is %d .\n', projThresholdY 
            int sizeUp = 0;
            int sizeDown = 0;

            int computeSize = nx / 4;
            int xstart = centerX - computeSize;
            int xstop  = centerX + computeSize;
            int projUp = 0;
			int projDown = 0;
            for ( int i = 0; i < windowY - 1; i++ ) {
    
                projUp = 0;
                projDown = 0;
    
                if ( sizeUp == 0 && centerY - i >= 0 ) {
        
                    for ( int j = xstart - 1; j < xstop; j ++ ) {
                        projUp = projUp + im [centerY - i][j];
                    }        
                    if ( projUp < projThresholdY )
                       sizeUp = i;
                }
    
                if ( sizeDown == 0 && centerY + i < ny ) {
                    for ( int j = xstart - 1; j < xstop; j++ ) {
                        projDown = projDown + im [centerY + i][j];
                    }
        
                    if ( projDown < projThresholdY ) {
                        sizeDown = i;
                    }
        
                }
            }

            //line position
            startPosY = centerY - sizeUp - margin;
            stopPosY  = centerY + sizeDown + margin;

        }
		int char_segmentation(  ) {

			int projThresholdX = 2;
			int minLength = 2;
			int maxLength = 60;
            int temp = 0;
			//fprintf('Middle point X projection threshold is %d .\n', projThresholdX );

			double proj[];
			proj = new int[nx];
			for ( int i = 0; i < nx; i++ )
			    proj[i] = 0;
			for ( int i = 0; i < nx; i++ ) {
				temp = 0;
				for ( int j = startPosY - 1; j < stopY; j++ ) {
					temp = temp + im[j][i];
				}
				proj[i]  = temp;
			}

			//apply a moving average filter, optional
			proj = moving_average(proj, 5, nx);

			// get a nice center
			while ( proj[centerX - 1] < 15 ) {
				centerX = centerX + 2;

				if ( centerX > nx ){
					//fprintf ('Error 0000 \n');
					System.out.println("error occured");
					return;
				}

			}

            // idx??????   numChar ?????????

			//left side
			int charLeng = 1;
			int blankLeng = 0;

			int segTemp[] = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };

			int idx = 0;

			for ( int i = centerX -1; i >=0; i-- ) {

				if ( proj[i] > projThresholdX ) {
					
					if ( charLeng == 0 ) {
						
						if ( blankLeng < minLength ) {
							idx = idx - 1;
							charLeng = segTemp[idx-1] - i;
							blankLeng = 0;
						}
						else {
							segTemp[idx] = i;
							idx = idx + 1;
							blankLeng = 0;
							charLeng = 1;
						}
					}
					else {
						charLeng = charLeng + 1;
					}
				}
				else {
					
					if ( blankLeng == 0 ) {
						
						segTemp[idx] = i;
						idx = idx + 1;
						charLeng = 0;
					}
					blankLeng = blankLeng + 1;
					
				}

				if ( blankLeng > maxLength )
					break;
			}

			//filp the array
			
			for ( int i = 0; i < idx; i++ ) {
			    segmentX[i] = segTemp[ idx - i ];
			}

			//right side

			charLeng = 1;
			blankLeng = 0;

			for ( int i = centerX - 1; i < nx; i++ ) {

				if ( proj[i] > projThresholdX ) {
					
					if ( charLeng == 0 ) {
						if ( blankLeng < minLength ) {
							idx = idx - 1;
							charLeng = i - segTemp[idx-1];
							blankLeng = 0;
						}
						else {
							segmentX[idx] = i;
							idx = idx + 1;
							blankLeng = 0;
							charLeng = 1;
						}
					}
					else {
						charLeng = charLeng + 1;
					}
				}	
				else {
					if ( blankLeng == 0 ) {
						segmentX[idx] = i;
						idx = idx + 1;
						charLeng = 0;
					}
					blankLeng = blankLeng + 1;
				}

				if ( blankLeng > maxLength )
					break;
			}

			int numChar = ( idx - 1 ) / 2;
            return numChar;
		}
		
		double [] moving_average ( double array[], int aveSize, int length ) {

			double buff[aveSize];
			for ( int i = 0; i < aveSize; i++ )
			    buff[i] = 0;

			int summ = 0;
			for ( i = 0; i < aveSize; i++ ) {
				buff[i] = array[i];
				summ = summ + array[i];
			}
			// i = 5 ???? mod i??????
			for ( int i = 4; i < length; i ++ ) {
				
				j = mod(i + 1, aveSize);
				
				summ = summ - buff[j] + array[i];
				buff[j] = array[i]; 
				
				array[i] = summ / aveSize;
            }
			return array;
		}
		
		void get_charType( int numChar ) {

			int long = floor((stopPosY - startPosY)/3);
			int short = 5;
			int margin = 3;
			int threshold = 20;

			int typeChar [] = new int [numChar];
			for ( int i = 0; i < numChar; i++ )
			    typeChar[i] = 0;
			
			int segCharY [] = new int [numChar * 2];
			for ( int i = 0; i < numChar * 2; i++ )
			    segCharY [i] = 0;

			for ( int i = 0; i < numChar - 1; i++ ) {
				
				int total = 0;
				
				for ( int j = stopPosY + short - 1; j <  stopY + long; j++ ) {
					for ( int k = segCharX[i*2] + margin - 1; k < segCharX[i*2 + 1] - margin; k++ ) {
						
						total = total + im[ j ] [ k ];
						
					}
				}
				
				
				if ( total > threshold ) {
					typeChar[i] = 3;
					segCharY[i * 2] = startPosY;
					segCharY[i * 2 + 1] = stopPosY + long;
				}
				else {
					
					total = 0;
					
					for ( int p = startPosY - long - 1; p < startPosY - short; p++ ) {
						
						for ( int q = segCharX[ i * 2 ] + margin - 1; q < segCharX[i*2 + 1]  - margin; q++ )
							total = total + im[p][q];
					}
					
					if ( total > threshold ) {
						typeChar[i] = 2;
						segCharY[i*2] = startPosY - long;
						segCharY[i*2 + 1] = stopPosY;
					}
					else {
						typeChar[i] = 1;
						segCharY[i*2] = startPosY;
						segCharY[i*2+1] = stopPosY;
					}
					
				}
				
			}
        }
		
		void interploate2standard(im, segCharX, segCharY, numChar, stdX, stdY ) {

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
    
        }


}
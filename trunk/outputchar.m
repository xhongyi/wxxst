function [  ] = outputchar( std1,std2,std3 )
f = fopen('test1.txt','w+')
for k = 1:13
  %fprintf(f,'{');
  for m = 1:256
    %fprintf(f,'{');
    for j = 1:4
        for n = (64*(j-1)+1):(64*j-1)
            fprintf(f,'%f ',std1(m,n,k));
        end
        fprintf(f,'%f\n',std1(m,64*j,k));
    end
  end
end
fclose(f);

f = fopen('test2.txt','w+')
for k = 1:8
  %fprintf(f,'{');
  for m = 1:256
   %fprintf(f,'{');
    for j = 1:4
        for n = (64*(j-1)+1):(64*j-1)
            fprintf(f,'%f ',std2(m,n,k));
        end
        fprintf(f,'%f\n',std2(m,64*j,k));
    end
  end
end
fclose(f);

f = fopen('test3.txt','w+');
for k = 1:5
  %fprintf(f,'{');
  for m = 1:256
    %fprintf(f,'{');
    for j = 1:4
        for n = (64*(j-1)+1):(64*j-1)
            fprintf(f,'%f ',std3(m,n,k));
        end
        fprintf(f,'%f\n',std3(m,64*j,k));
    end
  end
end
fclose(f);
function [  ] = outputchar( std1,std2,std3 )

for k = 1:13
  %fprintf(f,'{');
  str = ['test' num2str(k)];
  f = fopen(str,'w+');
  for m = 1:255
    %fprintf(f,'{');
    for n = 1:255
        fprintf(f,'%f ',std1(m,n,k));
    end
    fprintf(f,'%f\n',std1(m,256,k));
  end
  %fprintf(f,'{');
  for n = 1:255
        fprintf(f,'%f ',std1(256,n,k));
  end
  fprintf(f,'%f\n',std1(256,256,k));
  fclose(f);
end


for k = 1:8
  %fprintf(f,'{');
  str = ['test' num2str(k+13)];
  f = fopen(str,'w+');
  for m = 1:255
    %fprintf(f,'{');
    for n = 1:255
        fprintf(f,'%f ',std2(m,n,k));
    end
    fprintf(f,'%f\n',std2(m,256,k));
  end
  %fprintf(f,'{');
  for n = 1:255
        fprintf(f,'%f ',std2(256,n,k));
  end
  fprintf(f,'%f\n',std2(256,256,k));
  fclose(f);
end



for k = 1:5
  %fprintf(f,'{');
  str = ['test' num2str(k+21)];
  f = fopen(str,'w+');
  for m = 1:255
   % fprintf(f,'{');
    for n = 1:255
        fprintf(f,'%f ',std3(m,n,k));
    end
    fprintf(f,'%f\n',std3(m,256,k));
  end
  %fprintf(f,'{');
  for n = 1:255
        fprintf(f,'%f ',std3(256,n,k));
  end
  fprintf(f,'%f\n',std3(256,256,k));
  fclose(f);
end
end
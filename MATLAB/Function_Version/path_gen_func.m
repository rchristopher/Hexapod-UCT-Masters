function [path,speed,acceleration] = path_gen_func(startpos,endpos,interval,speed,acceleration)
%UNTITLED6 Summary of this function goes here
%   Detailed explanation goes here
    
    path = zeros(3,interval);
    
    x_int = -(startpos(1) - endpos(1))/interval;
    y_int = -(startpos(2) - endpos(2))/interval;
    z_int = -(startpos(3) - endpos(3))/interval;
    
     path(1,1) = [ startpos(1) ];
     path(2,1) = [ startpos(2) ];
     path(3,1) = [ startpos(3) ];
    
    for i=1:interval

        path(1,i+1) = [ startpos(1)+x_int*i ];
        path(2,i+1) = [ startpos(2)+y_int*i ];
        path(3,i+1) = [ startpos(3)+z_int*i ];

    end
    


end


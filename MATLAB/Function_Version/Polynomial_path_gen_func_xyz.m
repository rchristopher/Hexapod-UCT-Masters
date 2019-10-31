function [tt,q1t,q2t,q3t,v1t,v2t,v3t,a1t,a2t,a3t] = Polynomial_path_gen_func(Pe, num)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here

    s = size(Pe);
    
    q1t=[]; q2t=[]; q3t=[];
    v1t=[]; v2t=[]; v3t=[];
    a1t=[]; a2t=[]; a3t=[];
    tt=[];
    
    v0 = 0; vf = 0;
    ac0 = 0; acf = 0;
    
    for i=1:1:s(1)-1

        t0 = i-1;
        tf = i;
        
        t = linspace(t0,tf,num); %time vector (linearly spaced)

        %XYZ Coordinates when t=t0 (LEG1)
        q1_i = Pe(i,1);
        q2_i = Pe(i,2);
        q3_i = Pe(i,3);

        %XYZ Coordinates when t=tf (LEG1)
        q1_f = Pe(i+1,1);
        q2_f = Pe(i+1,2);
        q3_f = Pe(i+1,3);

        M =[1 t0 t0^2 t0^3 t0^4 t0^5;
         0 1 2*t0 3*t0^2 4*t0^3 5*t0^4;
         0 0 2 6*t0 12*t0^2 20*t0^3;
         1 tf tf^2 tf^3 tf^4 tf^5;
         0 1 2*tf 3*tf^2 4*tf^3 5*tf^4;
         0 0 2 6*tf 12*tf^2 20*tf^3];

        b1 = [q1_i; v0; ac0; q1_f; vf; acf]; a1 = M\b1;
        b2 = [q2_i; v0; ac0; q2_f; vf; acf]; a2 = M\b2;
        b3 = [q3_i; v0; ac0; q3_f; vf; acf]; a3 = M\b3;
        
        %position, velocity and acceleration for joint 1
        q1t = [q1t, a1(1) + a1(2).*t+a1(3).*t.^2+a1(4).*t.^3+a1(5).*t.^4+a1(6).*t.^5];
        v1t = [v1t, a1(2) +2*a1(3).*t+3*a1(4).*t.^2+4*a1(5).*t.^3+5*a1(6).*t.^4];
        a1t = [a1t, 2*a1(3) + 6*a1(4).*t+12*a1(5).*t.^2+20*a1(6).*t.^3];

        %position, velocity and acceleration for joint 2
        q2t = [q2t, a2(1) + a2(2).*t+a2(3).*t.^2+a2(4).*t.^3+a2(5).*t.^4+a2(6).*t.^5];
        v2t = [v2t, a2(2) +2*a2(3).*t+3*a2(4).*t.^2+4*a2(5).*t.^3+5*a2(6).*t.^4];
        a2t = [a2t, 2*a2(3) + 6*a2(4).*t+12*a2(5).*t.^2+20*a2(6).*t.^3];

        %position, velocity and acceleration for joint 3
        q3t = [q3t, a3(1) + a3(2).*t+a3(3).*t.^2+a3(4).*t.^3+a3(5).*t.^4+a3(6).*t.^5];
        v3t = [v3t, a3(2) +2*a3(3).*t+3*a3(4).*t.^2+4*a3(5).*t.^3+5*a3(6).*t.^4];
        a3t = [a3t, 2*a3(3) + 6*a3(4).*t+12*a3(5).*t.^2+20*a3(6).*t.^3];

        tt = [tt, t];
        
    end

end


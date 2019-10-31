function [tt,q1t,q2t,q3t,v1t,v2t,v3t,a1t,a2t,a3t] = Cubic_path_gen_heu_func(Pe)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here

    s = size(Pe);
    
    q1t=[]; q2t=[]; q3t=[];
    v1t=[]; v2t=[]; v3t=[];
    a1t=[]; a2t=[]; a3t=[];
    
    tt=[];
    
    v0=0;vf=0;

    qd_1(1) = 0;
    qd_1(s(1)) = 0;
   
    
    t = linspace(0,1,s(1)); 
    
    for k=1:1:s(1)-1
             vk(k+1) = (Pe(k+1,1) - Pe(k,1))/(t(k+1)-t(k));
    end
    
    for i=1:1:s(1)-1

        t0 = i-1;
        tf = i;
        
        fprintf("Segment: %d",tf);
        
        Pe(i,:)
        
        v0
        
        t = linspace(t0,tf,100); 

%       lengths = [0.008, 0.05317, 0.102, 0.152];
           
        if sign(vk(i)) == sign(vk(i+1)) 
            vf=(1/2)*(vk(i)+vk(i+1))
        else
            vf=0
        end
         

        theta_i = Pe(i,:);%inverse_kinematics_func(Pe(i,:),lengths,-1,-1)
        theta_f = Pe(i+1,:);%inverse_kinematics_func(Pe(i+1,:),lengths,-1,-1)

        %XYZ Coordinates when t=t0 (LEG1)
        q1_i = theta_i(1);
        q2_i = theta_i(2);
        q3_i = theta_i(3);

        %XYZ Coordinates when t=tf (LEG1)
        q1_f = theta_f(1);
        q2_f = theta_f(2);
        q3_f = theta_f(3);

        M =[1 t0 t0^2 t0^3;
         0 1 2*t0 3*t0^2;
         1 tf tf^2 tf^3;
         0 1 2*tf 3*tf^2];
     
        a1(1) = q1_i;
        a1(2) = v0;
        a1(3) = (-3*(q1_i-q1_f)-(2*v0+vf)*(tf-t0))/(tf-t0)^2;
        a1(4) = (2*(q1_i-q1_f)+(v0+vf)*(tf-t0))/(tf-t0)^3;
        
        %b1 = [q1_i; v0; q1_f; vf]; a1 = M\b1
        b2 = [q2_i; v0; q2_f; vf]; a2 = M\b2;
        b3 = [q3_i; v0; q3_f; vf]; a3 = M\b3;
               
        %position, velocity and acceleration for joint 1
        q1t = [q1t, a1(1) + a1(2).*(t-t0)+a1(3).*(t-t0).^2+a1(4).*(t-t0).^3];
        
        v1t = [v1t, a1(2) +2*a1(3).*(t)+3*a1(4).*(t).^2];
        a1t = [a1t, 2*a1(3) + 6*a1(4).*(t)];

        %position, velocity and acceleration for joint 2
        q2t = [q2t, a2(1) + a2(2).*(t)+a2(3).*(t).^2+a2(4).*(t).^3];
        v2t = [v2t, a2(2) +2*a2(3).*(t)+3*a2(4).*(t).^2];
        a2t = [a2t, 2*a2(3) + 6*a2(4).*(t)];

        %position, velocity and acceleration for joint 3
        q3t = [q3t, a3(1) + a3(2).*(t)+a3(3).*(t).^2+a3(4).*(t).^3];
        v3t = [v3t, a3(2) +2*a3(3).*(t)+3*a3(4).*(t).^2];
        a3t = [a3t, 2*a3(3) + 6*a3(4).*(t)];

        tt = [tt, t];
        v0=vf;
               
    end

end


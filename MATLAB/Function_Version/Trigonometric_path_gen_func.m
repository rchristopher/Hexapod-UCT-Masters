function [tt,q1t,q2t,q3t,v1t,v2t,v3t,a1t,a2t,a3t] = Polynomial_path_gen_func(Pe,stepHeight, crabAngle)
%UNTITLED4 Summary of this function goes here
%   Detailed explanation goes here

    s = size(Pe);
    
    q1t=[]; q2t=[]; q3t=[];
    v1t=[]; v2t=[]; v3t=[];
    a1t=[]; a2t=[]; a3t=[];
    tt=[];
    
    v0 = 0; vf = 0;
    ac0 = 0; acf = 0;
    
    t0 = i-1;
    tf = i;
        
    t = linspace(t0,tf,100); %time vector (linearly spaced)

    lengths = [0.008, 0.05317, 0.102, 0.152];

    theta_i = inverse_kinematics_func(Pe(1,:),lengths,-1,-1)
    theta_f = inverse_kinematics_func(Pe(2,:),lengths,-1,-1)
    thtea_m =  inverse_kinematics_func([0.175;0;0.1],lengths,-1,-1)

    %XYZ Coordinates when t=t0 (LEG1)
    q1_i = theta_i(1);
    q2_i = theta_i(2);
    q3_i = theta_i(3);

    %XYZ Coordinates when t=tf (LEG1)
    q1_f = theta_f(1);
    q2_f = theta_f(2);
    q3_f = theta_f(3);
   
    %position, velocity and acceleration for joint 1
    q1t = cos(t)*cos(crabAngle);
    v1t = -sin(t)*cos(crabAngle);
    a1t = -cos(t)*cos(crabAngle);

    q2t = cos(t)*sin(crabAngle);
    v2t = -sin(t)*sin(crabAngle);
    a2t = -cos(t)*sin(crabAngle);

    %position, velocity and acceleration for joint 3
    q3t = stepHeight*sin(t);
    v3t = stepHeight*cos(t);
    a3t = -stepHeight*sin(t);

    tt = [tt, t];

end



    clc;
    clear;
    format short;


    L1 = 0.102;
    L2 = 0.152;
    
    x = 0.05;   %m
    y = 0.06;   %m
    z = 0.02;    %m
    
    theta_1 = atan2(y,x);
    
    a = acos( ( L1^2 - L2^2 + y^2 + z^2 )/( 2*L1*sqrt( y^2 + z^2 ) ) );
    
    theta_2 = atan2(y,z) - a;
    
    B = acos( ( L1^2 + L2^2 - y^2 - z^2 )/( 2*L1*L2 ) );
   
    theta_3 = (pi - B);
    
    %syms theta_1 theta_2 theta_3;
    
    
    H_1 =   [cos(theta_1)   0   sin(theta_1)    0;
            sin(theta_1)    0   -cos(theta_1)   0;
            0               1       0           0;
            0               0       0           1];
    
    H_2 =   [cos(theta_2)  -sin(theta_2)       0       L1*cos(theta_2);
            sin(theta_2)   cos(theta_2)        0       L1*sin(theta_2);
            0              0                   1           0;
            0              0                   0           1];
    
    H_3 =   [cos(theta_3)  -sin(theta_3)       0       L2*cos(theta_3);
            sin(theta_3)   cos(theta_3)        0       L2*sin(theta_3);
            0              0                   1           0;
            0              0                   0           1];  
        
    T_3 = ((three_dof_kinematic(H_1,H_2,H_3)));
    
    t1=rad2deg(theta_1);
    t2=rad2deg(theta_2);
    t3=rad2deg(theta_3);
    
    
    L1 = 0.102;
    L2 = 0.152;
    L3 = 0;
    L4 = 0;
   
    
    S1 = [0 -1 0 0 
        ; 1 0 0 0
        ; 0 0 0 0
        ; 0 0 0 0];
    
    S2 = [0 0 1 0 
        ; 0 0 0 0
        ; -1 0 0 0
        ; 0 0 0 0];
    
    S3 = [0 0 1 0 
        ; 0 0 0 0
        ; -1 0 0 L1
        ; 0 0 0 0];
    
    M = [1 0 0 L1 + L2 
       ; 0 1 0 0
       ; 0 0 1 0
       ; 0 0 0 1];
    
    
    [P1,d1] = eig(S1*theta_1);  
    D1 = exp(d1);
    e1 = P1*D1*P1^-1;    %e^([s1]*theta1)
    
    [P2,d2] = eig(S2*theta_2)  ;  
    D2 = exp(d2);
    e2 = P2*D2*P2^-1;    %e^([s1]*theta1)
    
    [P3,d3] = eig(S3*theta_3)    ;
    D3 = exp(d3);
    e3 = P3*D3*P3^-1;    %e^([s1]*theta1)
    
    T = e1*e2*e3*M    ;
    
   % T_dot = diff(T);
  %  T_inv = T^-1;
    
    theta_1_sub = atan2(x,y);
    
    a = acos( ( L1^2 - L2^2 + y^2 + z^2 )/( 2*L1*sqrt( y^2 + z^2 ) ) );
    
    theta_2_sub = atan2(z,y) - a;
    
    B = acos( ( L1^2 + L2^2 - y^2 - z^2 )/( 2*L1*L2 ) );
   
    theta_3_sub = pi - B;
    
    ((subs(T, {theta_1, theta_2, theta_3}, [theta_1_sub theta_2_sub theta_3_sub])))    ;
    
    
        syms theta1 theta2 theta3 L1x L1z L1 L2 L3 L0
        
        
    
    w1 = [0;0;1];    
    q1 = [0;0;0];
    v1 = cross(-w1,q1);
    
    a = screw(w1,q1,theta1)
    
    z1 = [v1;w1];
    
    w_hat1 = [0 -w1(3) w1(2); w1(3) 0 -w1(1); -w1(2) w1(1) 0];
    
    ew_hat_theta1 = eye(3) + w_hat1*sin(theta1) + (w_hat1^2)*(1-cos(theta1));
    
    ez_hat_theta1 = [ew_hat_theta1];
    ef1 = [ (eye(3)-ew_hat_theta1)*(cross(w1,v1)) + w1*(w1')*v1*theta1; 1 ];
    
    ez_hat_theta1 = [ez_hat_theta1 ef1]
        
    w2 = [0;1;0];    
    q2 = [L1x;0;L1z];
    v2 = cross(-w2,q2);
    
    z2 = [v2;w2];
    
    w_hat2 = [0 -w2(3) w2(2); w2(3) 0 -w2(1); -w2(2) w2(1) 0];
    
    ew_hat_theta2 = eye(3) + w_hat2*sin(theta2) + (w_hat2^2)*(1-cos(theta2));
    
    ez_hat_theta2 = [ew_hat_theta2];
    ef2 = [ (eye(3)-ew_hat_theta2)*(cross(w2,v2)) + w2*(w2')*v2*theta2; 1 ];
    
    ez_hat_theta2 = [ez_hat_theta2 ef2]
        
    w3 = [0;1;0];    
    q3 = [L1x+L2;0;L1z];
    v3 = cross(-w3,q3);
    
    z3 = [v3;w3];
    
    w_hat3 = [0 -w3(3) w3(2); w3(3) 0 -w3(1); -w3(2) w3(1) 0];
    
    ew_hat_theta3 = eye(3) + w_hat3*sin(theta3) + (w_hat3^2)*(1-cos(theta3));
    
    ez_hat_theta3 = [ew_hat_theta3];
    ef3 = [ (eye(3)-ew_hat_theta3)*(cross(w3,v3)) + w3*(w3')*v3*theta3; 1 ];
    
    ez_hat_theta3 = [ez_hat_theta3 ef3]
    
    g_st_0 = [1, 0, 0, L1x+L2+L3; 0, 1, 0, 0; 0, 0, 1, L1z; 0, 0, 0, 1];
    
    g_st_theta = simplify(ez_hat_theta1*ez_hat_theta2*ez_hat_theta3*g_st_0)
    

    zi2 = adj(ez_hat_theta1)*z2
    zi3 = adj(ez_hat_theta1*ez_hat_theta2)*z3
    
	Js_st = [z1 zi2 zi3]
    
    
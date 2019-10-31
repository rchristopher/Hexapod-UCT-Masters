function [Tau] = dynamics_func(com_lengths, lengths, theta, theta_d, theta_dd, contact_force)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here

    g = 9.80665;

    L1zc = com_lengths(1);
    L1xc = com_lengths(2);
    L2c = com_lengths(3);
    L3c = com_lengths(4);
    
    L1z = lengths(1);
    L1x = lengths(2);
    L2 = lengths(3);
    L3 = lengths(4);
       
    %% COM Jacobians
    
    z0 = [0;0;1];
    z1 = [-sin(theta(1));cos(theta(1));0];
    z2 = z1;
    
    p0 = [0;0;0];
    p1 = [  L1xc*cos(theta(1));
            L1xc*sin(theta(1));
            L1zc];
        
    p2 = [  cos(theta(1))*(L1xc + L2c*cos(theta(2)));
            sin(theta(1))*(L1xc + L2c*cos(theta(2)));
            L1zc + L2c*sin(theta(2))];
    
    p3 = [  cos(theta(1))*(L1xc + L3c*cos(theta(2) + theta(3)) + L2c*cos(theta(2)));
            sin(theta(1))*(L1xc + L3c*cos(theta(2) + theta(3)) + L2c*cos(theta(2)));
            L1zc + L3c*sin(theta(2) + theta(3)) + L2c*sin(theta(2))    ];
        
    Jvc1 = ( [cross( z0,( p1-p0 ) ), [0;0;0], [0;0;0]] );
    Jvc2 = ( [cross( z0,( p2-p0 ) ), cross( z1,( p2-p1 ) ) , [0;0;0]] );
    Jvc3 = ( [cross( z0,( p3-p0 ) ), cross( z1,( p3-p1 ) ) , cross( z2,( p3-p2 ) )] );
    
    Jwc1 = [z0 [0;0;0] [0;0;0]];
    Jwc2 = [z1 [0;0;0] [0;0;0]];
    Jwc3 = [z2 [0;0;0] [0;0;0]];
    
    %% Rotation Matrices
    
    B = atan2(L1z,L1x);
    
    R_y_beta = [ cos(B)  , 0, sin(B);
                 0       , 1, 0     ;
                -sin(B)  , 0, cos(B)];      %y axis rotation by beta to link 1 COM   
          
    R_z_theta_1 = [ cos(theta(1)) , -sin(theta(1)), 0;
                   sin(theta(1)) , cos(theta(1)) , 0;
                   0           , 0           , 1];       %z axis rotation by theta 1
              
    R0_c1 = R_y_beta*R_z_theta_1;                 %rotation matrix to COM of link 1
   
    R_x_90 = [ 1, 0, 1;
               0, 0, -1;
               0, 1, 0];             %rotation about x by 90
   
    R_z_theta_2 = [ cos(theta(2)) , -sin(theta(2)), 0;
                   sin(theta(2)) , cos(theta(2)) , 0;
                   0           , 0           , 1];       %z axis rotation by theta 2
          
    R0_c2 = R_z_theta_1*R_x_90*R_z_theta_2;
   
    R_z_theta_3 = [ cos(theta(3)) , -sin(theta(3)), 0;
                   sin(theta(3)) , cos(theta(3)) , 0;
                   0           , 0           , 1];       %z axis rotation by theta 3
   
    R0_c3 = R_z_theta_1*R_x_90*R_z_theta_2*R_z_theta_3;
    
    %%  Inertia Matrices
    
    I1 = [ 132857.18, 2687.33, 164548.51;
           2687.33, 490374.16, -250.25;
           164548.51, -250.25, 399885.97 ]/1000000000;
       
    I2 = [ 34886.82, -10778.87, -3780.08;
           -10778.87, 228145.32, 341.87;
           -3780.08, 341.87, 204157.10 ]/1000000000;   
    
    I3 = [ 41560.95, 51130.24, -211.30;
           51130.24, 370796.48, 22.90;
           -211.30, 22.90, 384117.75 ]/1000000000;
       
    %% Link masses
    
    m = [0.14327;0.04977;0.15456];      %kg
    
    %% Mass Matrix
    
    Translational = ( m(1)*transpose(Jvc1)*Jvc1 + m(2)*transpose(Jvc2)*Jvc2 + m(3)*transpose(Jvc3)*Jvc3 );
    Rotational = transpose(Jwc1)*R0_c1*I1*transpose(R0_c1)*Jwc1 + transpose(Jwc2)*R0_c2*I2*transpose(R0_c2)*Jwc2 + transpose(Jwc3)*R0_c3*I3*transpose(R0_c3)*Jwc3;
    
    M =  Translational + Rotational; 
    
    %% Coriolis Matrix
    C = [ - (theta_d(3)*(9444732965739290427392*L3c^2*m(3)*sin(2*theta(2) + 2*theta(3))*abs(L1x + L1z*1i)^2 + 18889465931478580854784*L3c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2) + theta(3)) + 9444732965739290427392*L2c*L3c*m(3)*abs(L1x + L1z*1i)^2*sin(theta(3)) + 9444732965739290427392*L2c*L3c*m(3)*sin(2*theta(2) + theta(3))*abs(L1x + L1z*1i)^2))/(18889465931478580854784*abs(L1x + L1z*1i)^2) - (theta_d(2)*(9444732965739290427392*L3c^2*m(3)*sin(2*theta(2) + 2*theta(3))*abs(L1x + L1z*1i)^2 + 9444732965739290427392*L2c^2*m(2)*sin(2*theta(2))*abs(L1x + L1z*1i)^2 + 9444732965739290427392*L2c^2*m(3)*sin(2*theta(2))*abs(L1x + L1z*1i)^2 + 18889465931478580854784*L3c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2) + theta(3)) + 18889465931478580854784*L2c*L1xc*m(2)*abs(L1x + L1z*1i)^2*sin(theta(2)) + 18889465931478580854784*L2c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2)) + 18889465931478580854784*L2c*L3c*m(3)*sin(2*theta(2) + theta(3))*abs(L1x + L1z*1i)^2))/(18889465931478580854784*abs(L1x + L1z*1i)^2) - (theta_d(1)*(50762228481640336*L1z^2*cos(2*theta(1)) - 3376652406817553920*L1z^2*sin(2*theta(1)) + 4727088849352515*L1x*L1z*cos(theta(1)) - 3108233473720562688*L1x*L1z*sin(theta(1))))/(18889465931478580854784*abs(L1x + L1z*1i)^2), -(theta_d(1)*(9444732965739290427392*L3c^2*m(3)*sin(2*theta(2) + 2*theta(3))*abs(L1x + L1z*1i)^2 + 9444732965739290427392*L2c^2*m(2)*sin(2*theta(2))*abs(L1x + L1z*1i)^2 + 9444732965739290427392*L2c^2*m(3)*sin(2*theta(2))*abs(L1x + L1z*1i)^2 + 18889465931478580854784*L3c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2) + theta(3)) + 18889465931478580854784*L2c*L1xc*m(2)*abs(L1x + L1z*1i)^2*sin(theta(2)) + 18889465931478580854784*L2c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2)) + 18889465931478580854784*L2c*L3c*m(3)*sin(2*theta(2) + theta(3))*abs(L1x + L1z*1i)^2))/(18889465931478580854784*abs(L1x + L1z*1i)^2), -(theta_d(1)*(9444732965739290427392*L3c^2*m(3)*sin(2*theta(2) + 2*theta(3))*abs(L1x + L1z*1i)^2 + 18889465931478580854784*L3c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2) + theta(3)) + 9444732965739290427392*L2c*L3c*m(3)*abs(L1x + L1z*1i)^2*sin(theta(3)) + 9444732965739290427392*L2c*L3c*m(3)*sin(2*theta(2) + theta(3))*abs(L1x + L1z*1i)^2))/(18889465931478580854784*abs(L1x + L1z*1i)^2);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             (theta_d(1)*(9444732965739290427392*L3c^2*m(3)*sin(2*theta(2) + 2*theta(3))*abs(L1x + L1z*1i)^2 + 9444732965739290427392*L2c^2*m(2)*sin(2*theta(2))*abs(L1x + L1z*1i)^2 + 9444732965739290427392*L2c^2*m(3)*sin(2*theta(2))*abs(L1x + L1z*1i)^2 + 18889465931478580854784*L3c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2) + theta(3)) + 18889465931478580854784*L2c*L1xc*m(2)*abs(L1x + L1z*1i)^2*sin(theta(2)) + 18889465931478580854784*L2c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2)) + 18889465931478580854784*L2c*L3c*m(3)*sin(2*theta(2) + theta(3))*abs(L1x + L1z*1i)^2))/(18889465931478580854784*abs(L1x + L1z*1i)^2),                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            -L2c*L3c*m(3)*theta_d(3)*sin(theta(3)),                                                                                                                                                                                                                                                                                                        - L2c*L3c*m(3)*theta_d(2)*sin(theta(3)) - L2c*L3c*m(3)*theta_d(3)*sin(theta(3));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            (theta_d(1)*(9444732965739290427392*L3c^2*m(3)*sin(2*theta(2) + 2*theta(3))*abs(L1x + L1z*1i)^2 + 18889465931478580854784*L3c*L1xc*m(3)*abs(L1x + L1z*1i)^2*sin(theta(2) + theta(3)) + 9444732965739290427392*L2c*L3c*m(3)*abs(L1x + L1z*1i)^2*sin(theta(3)) + 9444732965739290427392*L2c*L3c*m(3)*sin(2*theta(2) + theta(3))*abs(L1x + L1z*1i)^2))/(18889465931478580854784*abs(L1x + L1z*1i)^2),                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             L2c*L3c*m(3)*theta_d(2)*sin(theta(3)),                                                                                                                                                                                                                                                                                                                                                                          0];
    % end coriolis matrix
    
    %% phi Matrix
    
    phi = [ 0;
            g*m(3)*(L3c*cos(theta(2) + theta(3)) + L2*cos(theta(2))) + L2c*g*m(2)*cos(theta(2));
            L3c*g*m(3)*cos(theta(2) + theta(3))];
    
    %% Tau
    Tau = M*theta_dd + C*theta_d + phi - contact_force;
       
end


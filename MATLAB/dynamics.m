   %% Symbol Setup
   
   %syms x y z t L1x L1z L1 L2 L3 L1xc L1zc L2c L3c m1 m2 m3 g real
   %syms theta1 theta2 theta3 theta1_ theta2_ theta3_ theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd
   
    %% C-O-M Jaconians
    
    disp("...dynamics - Center of mass jacobians...");
   
    Jvc1 = simplify( [cross( z0,( p1-p0 ) ), [0;0;0], [0;0;0]] );
    Jvc2 = simplify( [cross( z0,( p2-p0 ) ), cross( z1,( p2-p1 ) ) , [0;0;0]] );
    Jvc3 = simplify( [cross( z0,( p3-p0 ) ), cross( z1,( p3-p1 ) ) , cross( z2,( p3-p2 ) )] );
    
    Jvc1 = subs(Jvc1,[L1x L1z L2 L3],[L1xc L1zc L2c L3c]);
    Jvc2 = subs(Jvc2,[L1x L1z L2 L3],[L1xc L1zc L2c L3c]);
    Jvc3 = subs(Jvc3,[L1x L1z L2 L3],[L1xc L1zc L2c L3c]);
    
    Jwc1 = [z0 [0;0;0] [0;0;0]];
    Jwc2 = [z1 [0;0;0] [0;0;0]];
    Jwc3 = [z2 [0;0;0] [0;0;0]];
    
    Jwc1 = subs(Jwc1,[L1x L1z L2 L3],[L1xc L1zc L2c L3c]);
    Jwc2 = subs(Jwc2,[L1x L1z L2 L3],[L1xc L1zc L2c L3c]);
    Jwc3 = subs(Jwc3,[L1x L1z L2 L3],[L1xc L1zc L2c L3c]);
    
    %% C-O-M Rotation Matrices 
    
    disp("...dynamics - Center of mass rotation matrices...");
    
   B = atan2(L1z,L1x);
    
   R_y_beta = [ cos(B)  , 0, sin(B);
                0       , 1, 0     ;
               -sin(B)  , 0, cos(B)];      %y axis rotation by beta to link 1 COM   
          
   R_z_theta1 = [ cos(th1) , -sin(th1), 0;
                  sin(th1) , cos(th1) , 0;
                  0           , 0           , 1];       %z axis rotation by theta 1
              
   R0_c1 = R_y_beta*R_z_theta1;                 %rotation matrix to COM of link 1
   
   R_x_90 = [ 1, 0, 1;
              0, 0, -1;
              0, 1, 0];             %rotation about x by 90
   
   R_z_theta2 = [ cos(th2) , -sin(th2), 0;
                  sin(th2) , cos(th2) , 0;
                  0           , 0           , 1];       %z axis rotation by theta 2
          
   R0_c2 = R_z_theta1*R_x_90*R_z_theta2;
   
   R_z_theta3 = [ cos(th3) , -sin(th3), 0;
                  sin(th3) , cos(th3) , 0;
                  0           , 0           , 1];       %z axis rotation by theta 3
   
   R0_c3 = R_z_theta1*R_x_90*R_z_theta2*R_z_theta3;
          
  
    
    %% Inertia Tensors in body frame of each link
     
    disp("...dynamics - Body frame inertia tensors...");
    
%     I1 = [ 132857.18, 2687.33, 164548.51;
%            2687.33, 490374.16, -250.25;
%            164548.51, -250.25, 399885.97 ];
%        
%     I2 = [ 34886.82, -10778.87, -3780.08;
%            -10778.87, 228145.32, 341.87;
%            -3780.08, 341.87, 204157.10 ];   
%     
%     I3 = [ 41560.95, 51130.24, -211.30;
%            51130.24, 370796.48, 22.90;
%            -211.30, 22.90, 384117.75 ];
%        
%     I1 = I1/1000000000;
%     I2 = I2/1000000000;
%     I3 = I3/1000000000;
syms I1 I2 I3 real
       
    %% Kinetic Energy
    
    disp("...dynamics - kinetic energy...");
    
    theta = [th1;th2;th3];
    theta_d = [th1d;th2d;th3d];
    theta_dd = [th1dd;th2dd;th3dd];
    
    Translational = ( m1*transpose(Jvc1)*Jvc1 + m2*transpose(Jvc2)*Jvc2 + m3*transpose(Jvc3)*Jvc3 );
    Rotational = transpose(Jwc1)*R0_c1*I1*transpose(R0_c1)*Jwc1 + transpose(Jwc2)*R0_c2*I2*transpose(R0_c2)*Jwc2 + transpose(Jwc3)*R0_c3*I3*transpose(R0_c3)*Jwc3;
    
    M = simplify( Translational + Rotational );         
    K =  ( simplify( 0.5*transpose(theta_d)*( M )*theta_d ) );
            
    
    
    %% Cristoffel Symbols
    disp("...dynamics - christoffel symbols...");   
    syms n;
    C = [n,n,n;n,n,n;n,n,n];
    for i = 1 : 3
        for j = 1 : 3
            for k = 1: 3
                if k == 1
                    C(i,j) = 0.5*(diff(M(i,j),theta(k)) + diff(M(i,k),theta(j)) - diff(M(j,k),theta(i)))*theta_d(k);
                else
                    C(i,j) = C(i,j)+ 0.5*(diff(M(i,j),theta(k)) + diff(M(i,k),theta(j)) - diff(M(j,k),theta(i)))*theta_d(k);
                end
            end        
        end
    end
       
 
      
    %% Potential Energy Matrix G OR phi
    disp("...dynamics - potential energy...");
    
    P_1 = m1*g*L1zc;
    P_2 = m2*g*(L2c*sin(th2)+L1z);
    P_3 = m3*g*(L2*sin(th2) + L1z + L3c*sin(th2 + th3));
    
    P = P_1 + P_2 + P_3;
    
    disp("...dynamics - phi matrix (G)...");
      
    phi = [diff(P,th1); diff(P,th2); diff(P,th3)];
   
    
    %% Full equation  
    disp("...dynamics - full equations (Tau)...");
    Tau = M*theta_dd + C*theta_d + phi;
       
    disp("Dynamics Complete");
    
    %% closing
    %clear Translational Rotational Jvc1 Jvc2 Jvc3 Jwc1 Jwc2 Jwc3 R0c1 R0c2 R0c3 i j k null
    
    
    
    
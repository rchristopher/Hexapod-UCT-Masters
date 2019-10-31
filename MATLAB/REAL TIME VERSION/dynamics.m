    %% C-O-M Jaconians
    
    disp("...dynamics - Center of mass jacobians...");
   
    Jvc1 = ( [cross( z0,( p1-p0 ) ), [0;0;0], [0;0;0]] );
    Jvc2 = ( [cross( z0,( p2-p0 ) ), cross( z1,( p2-p1 ) ) , [0;0;0]] );
    Jvc3 = ( [cross( z0,( p3-p0 ) ), cross( z1,( p3-p1 ) ) , cross( z2,( p3-p2 ) )] );
        
    Jwc1 = [z0 [0;0;0] [0;0;0]];
    Jwc2 = [z1 [0;0;0] [0;0;0]];
    Jwc3 = [z2 [0;0;0] [0;0;0]];
    
    
    %% C-O-M Rotation Matrices 
    
    disp("...dynamics - Center of mass rotation matrices...");
    
    B = atan2(L1z,L1x);
       
    R0_c1 = [ cos(theta(1)), -sin(theta(1)), 0; sin(theta(1)), cos(theta(1)), 0; 0, 0, 1];      
    R0_c2 = A_1;   
    R0_c3 = A_1*A_2;   
           
    R0_c1 = R0_c1(1:3,1:3);      
    R0_c2 = R0_c2(1:3,1:3);   
    R0_c3 = R0_c3(1:3,1:3);  
    
    %% Inertia Tensors in body frame of each link
     
    disp("...dynamics - Body frame inertia tensors...");
    
    I1 = [ 132857.18, 2687.33, 164548.51;
           2687.33, 490374.16, -250.25;
           164548.51, -250.25, 399885.97 ];
       
    I2 = [ 34886.82, -10778.87, -3780.08;
           -10778.87, 228145.32, 341.87;
           -3780.08, 341.87, 204157.10 ];   
    
    I3 = [ 41560.95, 51130.24, -211.30;
           51130.24, 370796.48, 22.90;
           -211.30, 22.90, 384117.75 ];
       
    I1 = I1/1000000000;
    I2 = I2/1000000000;
    I3 = I3/1000000000;
       
    %% Kinetic Energy
    
    disp("...dynamics - kinetic energy...");
       
    Translational = ( m(1)*transpose(Jvc1)*Jvc1 + m(2)*transpose(Jvc2)*Jvc2 + m(3)*transpose(Jvc3)*Jvc3 );
    Rotational = transpose(Jwc1)*R0_c1*I1*transpose(R0_c1)*Jwc1 + transpose(Jwc2)*R0_c2*I2*transpose(R0_c2)*Jwc2 + transpose(Jwc3)*R0_c3*I3*transpose(R0_c3)*Jwc3;
    
    M = ( Translational + Rotational )      
    K =  ( ( 0.5*transpose(theta_d)*( M )*theta_d ) );
            
    %% Potential Energy
    
    disp("...dynamics - potential energy...");
    
    P_1 = m(1)*g*L1zc;
    P_2 = m(2)*g*L2c*sin(theta1);
    P_3 = m(3)*g*(L2*sin(theta2) + L3c*sin(theta2+theta3));
    
    P = P_1 + P_2 + P_3;
    
    %% Cristoffel Symbols
    disp("...dynamics - christoffel symbols...");  
   
    C = symMat([3 3],'C','real');
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
       
   
    %% Potential Energy Matrix G
    disp("...dynamics - phi matrix (G)...");
       
    phi = [diff(P,theta1); diff(P,theta2); diff(P,theta3)];
   
    
    %% Full equation  
    disp("...dynamics - full equations (Tau)...");
    Tau = M*theta_dd + C*theta_d + phi
       
    disp("Dynamics Complete");
    
    %% closing
    clear Translational Rotational Jvc1 Jvc2 Jvc3 Jwc1 Jwc2 Jwc3 R0c1 R0c2 R0c3 i j k null
    
    
    
    
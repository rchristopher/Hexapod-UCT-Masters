    z0 = [0;0;1];
    z1 = [-sin(theta(1));cos(theta(1));0];
    z2 = z1;
    
    p0 = [0;0;0];
    p1 = ( A_1(1:3,4) );
    p2 = A_1*A_2;
    p2 = ( p2(1:3,4) );
    p3 = A_1*A_2*A_3;
    p3 = ( p3(1:3,4) );
    
    J = ( [cross(z0,(p3-p0)),cross(z1,(p3-p1)),cross(z2,(p3-p2))  ; z0, z1, z2] ) ;
    J_plus = ((inv(transpose(J)*J))*transpose(J));
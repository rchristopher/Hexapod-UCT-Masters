    theta1 =  ( atan2(y,x) );
    
    C3 = ( ( x - L1x*cos(theta1) )^2 + ( y - L1x*sin(theta1) )^2 + ( z - L1z )^2 - L2^2 - L3^2 )/( 2*L2*L3 );
    S3 = -sqrt(1 - C3^2);

    theta2 =  ( atan2( z-L1z, sqrt( (x-L1x*cos(theta1))^2 + (y - L1x*sin(theta1))^2 ) ) - atan2(L3*S3, L2 + L3*C3) );

    theta3 =  ( atan2(S3, C3) );
    
    theta = [theta1; theta2; theta3];
    

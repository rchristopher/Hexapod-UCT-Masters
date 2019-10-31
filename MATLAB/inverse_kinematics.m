    %syms x y z t L1x L1z L1 L2 L3 L1xc L1zc L2c L3c m1 m2 m3 real
    %syms theta1 theta2 theta3 theta1_ theta2_ theta3_ theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd

    
    %S3 is positive for elbow down and negative for elbow up (I think...)
    
    theta1 =  simplify( atan2(y,x) );
    
    C3 = ( ( x - L1x*cos(theta1) )^2 + ( y - L1x*sin(theta1) )^2 + ( z - L1z )^2 - L2^2 - L3^2 )/( 2*L2*L3 );
    S3 = -sqrt(1 - C3^2);

    theta2 =  simplify( atan2( z-L1z, sqrt( (x-L1x*cos(theta1))^2 + (y - L1x*sin(theta1))^2 ) ) - atan2(L3*S3, L2 + L3*C3) );

    theta3 =  simplify( atan2(S3, C3) );
    
    theta = [theta1; theta2; theta3];
    

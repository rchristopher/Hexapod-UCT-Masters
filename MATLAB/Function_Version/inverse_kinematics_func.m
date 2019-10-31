function [theta] = inverse_kinematics_func(Pe,lengths,elbow,LEG)    %elbow = -1 for elbow up, -1 for relative to LEG
%UNTITLED5 Summary of this function goes here
%   Detailed explanation goes here

   if LEG < 0
       x = Pe(1);
       y = Pe(2);
       z = Pe(3);
   else
       x = Pe(1)*cos(LEG)-Pe(2)*sin(LEG) - 0.12554;
       y = Pe(2)*cos(LEG)+Pe(1)*sin(LEG);
       z = Pe(3);
   end 
    
    L1z = lengths(1);
    L1x = lengths(2);
    L2 = lengths(3);
    L3 = lengths(4);
    
    theta1 =  ( atan2(y,x) );
    
    C3 = ( ( x - L1x*cos(theta1) )^2 + ( y - L1x*sin(theta1) )^2 + ( z - L1z )^2 - L2^2 - L3^2 )/( 2*L2*L3 );
    C3(C3>1) = 1;
    C3(C3<-1) = -1;
    S3 = elbow*sqrt(1 - C3^2);

    theta2 =  ( atan2( z-L1z, sqrt( (x-L1x*cos(theta1))^2 + (y - L1x*sin(theta1))^2 ) ) - atan2(L3*S3, L2 + L3*C3) );

    theta3 =  ( atan2(S3, C3) );
    
    theta = [theta1; theta2; theta3];
        
end


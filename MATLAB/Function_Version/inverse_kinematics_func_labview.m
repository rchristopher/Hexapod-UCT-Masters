function [theta] = inverse_kinematics_func_labview(x_l,y_l,z_l,L1z,L1x,L2,L3,elbow,LEG)    %elbow = -1 for elbow up
%UNTITLED5 Summary of this function goes here
%   Detailed explanation goes here
   
   if LEG == -1
       x = x_l;
       y = y_l;
       z = z_l;
   else
       x = sqrt(x_l^2+y_l^2)*cos( sin(LEG)*cos( atan2(y_l,x_l) )-cos(LEG)*sin( atan2(y_l,x_l) ))-0.12554;
       y = sqrt(x_l^2+y_l^2)*sin( sin(LEG)*cos( atan2(y_l,x_l) )-cos(LEG)*sin( atan2(y_l,x_l) ));
       z = z_l;
   end 

    theta1 =  ( atan2(y,x) );
    
    C3 = ( ( x - L1x*cos(theta1) )^2 + ( y - L1x*sin(theta1) )^2 + ( z - L1z )^2 - L2^2 - L3^2 )/( 2*L2*L3 );
    C3(C3>1) = 1;
    C3(C3<-1) = -1;
    S3 = elbow*sqrt(1 - C3^2);

    theta2 =  ( atan2( z-L1z, sqrt( (x-L1x*cos(theta1))^2 + (y - L1x*sin(theta1))^2 ) ) - atan2(L3*S3, L2 + L3*C3) );

    theta3 =  ( atan2(S3, C3) );
    
    theta = [theta1; theta2; theta3];
        
end


function [T,Pe] = forward_kinematics_func(theta,lengths)
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here

    

    L1z = lengths(1);
    L1x = lengths(2);
    L2 = lengths(3);
    L3 = lengths(4);

    A_1 =   [cos(theta(1))    0   sin(theta(1))    L1x*cos(theta(1));
             sin(theta(1))    0   -cos(theta(1))   L1x*sin(theta(1));
             0              1       0          L1z;
             0              0       0          1];



    A_2 =   [cos(theta(2))  -sin(theta(2))       0       L2*cos(theta(2));
             sin(theta(2))   cos(theta(2))       0       L2*sin(theta(2));
             0              0                1           0;
             0              0                0           1];



    A_3 =   [cos(theta(3))  -sin(theta(3))       0       L3*cos(theta(3));
             sin(theta(3))   cos(theta(3))       0       L3*sin(theta(3));
             0              0                1           0;
             0              0                0           1]; 

    A_4 =   [1  0   0   0;
             0  0   1   0;
             0  -1  0   0;
             0  0   0   1];   
   
    T =  A_1*A_2*A_3*A_4;
    
    Pe(1) = T(1,4);
    Pe(1) = T(2,4);
    Pe(1) = T(3,4);


end


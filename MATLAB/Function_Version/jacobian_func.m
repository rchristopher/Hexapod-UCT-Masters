function [J,theta_d,theta_dd] = jacobian_func(lengths, theta, Pos_d, Pos_dd)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here

    L1z = lengths(1);
    L1x = lengths(2);
    L2 = lengths(3);
    L3 = lengths(4);

    %%
    J = [ -sin(theta(1))*(L1x + L3*cos(theta(2) + theta(3)) + L2*cos(theta(2))), cos(theta(1))*(L3*sin(theta(2) + theta(3)) + L2*sin(theta(2))), L3*sin(theta(2) + theta(3))*cos(theta(1));
          cos(theta(1))*(L1x + L3*cos(theta(2) + theta(3)) + L2*cos(theta(2))), sin(theta(1))*(L3*sin(theta(2) + theta(3)) + L2*sin(theta(2))), L3*sin(theta(2) + theta(3))*sin(theta(1));
                                                                     0,             - L3*cos(theta(2) + theta(3)) - L2*cos(theta(2)),            -L3*cos(theta(2) + theta(3));
                                                                     0,                                           -sin(theta(1)),                        -sin(theta(1));
                                                                     0,                                            cos(theta(1)),                         cos(theta(1));
                                                                     1,                                                      0,                                   0];
     %% J ends here
     transpose(J(1:3,1:3))
     J_plus = ((inv(transpose(J)*J))*transpose(J))
     
     theta_d =  J_plus*Pos_d;
     
     %% 
     J_d = [   (19*theta_d(3)*sin(theta(2) + theta(3))*sin(theta(1)))/125 - (theta_d(1)*cos(theta(1))*(15200*cos(theta(2) + theta(3)) + 10200*cos(theta(2)) + 5317))/100000 + (theta_d(2)*sin(theta(1))*(15200*sin(theta(2) + theta(3)) + 10200*sin(theta(2))))/100000, theta_d(2)*cos(theta(1))*((19*cos(theta(2) + theta(3)))/125 + (51*cos(theta(2)))/500) + (19*theta_d(3)*cos(theta(2) + theta(3))*cos(theta(1)))/125 - theta_d(1)*sin(theta(1))*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500), (19*theta_d(2)*cos(theta(2) + theta(3))*cos(theta(1)))/125 + (19*theta_d(3)*cos(theta(2) + theta(3))*cos(theta(1)))/125 - (19*theta_d(1)*sin(theta(2) + theta(3))*sin(theta(1)))/125;
     - (theta_d(1)*sin(theta(1))*(15200*cos(theta(2) + theta(3)) + 10200*cos(theta(2)) + 5317))/100000 - (19*theta_d(3)*sin(theta(2) + theta(3))*cos(theta(1)))/125 - (theta_d(2)*cos(theta(1))*(15200*sin(theta(2) + theta(3)) + 10200*sin(theta(2))))/100000, theta_d(2)*sin(theta(1))*((19*cos(theta(2) + theta(3)))/125 + (51*cos(theta(2)))/500) + (19*theta_d(3)*cos(theta(2) + theta(3))*sin(theta(1)))/125 + theta_d(1)*cos(theta(1))*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500), (19*theta_d(1)*sin(theta(2) + theta(3))*cos(theta(1)))/125 + (19*theta_d(2)*cos(theta(2) + theta(3))*sin(theta(1)))/125 + (19*theta_d(3)*cos(theta(2) + theta(3))*sin(theta(1)))/125;
             0,(19*theta_d(3)*sin(theta(2) + theta(3)))/125 + theta_d(2)*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500),                                                                              (19*theta_d(2)*sin(theta(2) + theta(3)))/125 + (19*theta_d(3)*sin(theta(2) + theta(3)))/125;
             0, -theta_d(1)*cos(theta(1)), -theta_d(1)*cos(theta(1));
             0, -theta_d(1)*sin(theta(1)), -theta_d(1)*sin(theta(1));
             0, 0, 0];
     %% Jd ends here

     theta_dd = J_plus*( Pos_dd - J_d*theta_d );
     
end


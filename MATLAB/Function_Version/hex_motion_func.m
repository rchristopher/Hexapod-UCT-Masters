function [theta, theta_d, theta_dd, Tau] = hex_motion_func(Path, Speed, Acceleration)
%UNTITLED3 Summary of this function goes here
%   Detailed explanation goes here

    L1z = 0.008;
    L1x = 0.05317;
    L2 = 0.102;
    L3 = 0.152;
    lengths = [L1z, L1x, L2, L3];
    com_lengths = [L1z/2 L1x/2 L2/2 L3/2];
   
    s = size(Path);
    
    theta = zeros(size(Path));
    theta_d = zeros(size(Path));
    theta_dd = zeros(size(Path));
    Tau = zeros(size(Path));
       
    for i=1:s(2)
        
        theta(:,i) =  (inverse_kinematics_func(Path(:,i),lengths,-1));

        [J, theta_d(:,i), theta_dd(:,i)] = jacobian_func(lengths, theta(:,i), Speed(:,i), Acceleration(:,i));
        contact_force = transpose(J)*[  0; 0; 10 ; 0; 0; 0 ];    %10N vertically up (ground force)

        Tau(:,i) = dynamics_func(com_lengths, lengths, theta(:,i), theta_d(:,i), theta_dd(:,i), contact_force);

    

    end
    
end


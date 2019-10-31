function [outputArg] = adj(inputArg)
%ADJ Summary of this function goes here
%   Detailed explanation goes here
R = inputArg(1:3,1:3);
p = inputArg(1:3,4);

p_hat = make_skew_R3(p);

outputArg = [R p_hat*R; zeros(3) R];
end


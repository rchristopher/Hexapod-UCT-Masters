function [outputArg1] = make_skew_R3(inputArg1)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
outputArg1 = [0 -inputArg1(3) inputArg1(2); inputArg1(3) 0 -inputArg1(1); -inputArg1(2) inputArg1(1) 0];
end


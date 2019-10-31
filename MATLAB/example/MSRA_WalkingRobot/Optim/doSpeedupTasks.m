% Sets up optimization environment for acceleration and parallel computing
% Copyright 2017 The MathWorks, Inc.

if parallelFlag
    if isempty(gcp('nocreate'))
        parpool; % Uses default saved profile
    end
    addAttachedFiles(gcp,fullfile(pwd,{'Robot','Optim','Libraries'}));
    parfevalOnAll(@addpath,0,'Optim',genpath('Robot'),genpath('Libraries'));
    parfevalOnAll(@load_system,0,mdlName);
    if accelFlag
        parfevalOnAll(@set_param,0,mdlName,'SimulationMode','accelerator');
        parfevalOnAll(@set_param,0,mdlName,'SimMechanicsOpenEditorOnUpdate','off');
    end
else
   if accelFlag
        load_system(mdlName);
        set_param(mdlName,'SimulationMode','accelerator');
        set_param(mdlName,'SimMechanicsOpenEditorOnUpdate','off');
   end
end
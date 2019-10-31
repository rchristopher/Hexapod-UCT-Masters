#include "__cf_Path_Sim.h"
#ifndef RTW_HEADER_Path_Sim_cap_host_h_
#define RTW_HEADER_Path_Sim_cap_host_h_
#ifdef HOST_CAPI_BUILD
#include "rtw_capi.h"
#include "rtw_modelmap.h"
typedef struct { rtwCAPI_ModelMappingInfo mmi ; } Path_Sim_host_DataMapInfo_T
;
#ifdef __cplusplus
extern "C" {
#endif
void Path_Sim_host_InitializeDataMapInfo ( Path_Sim_host_DataMapInfo_T *
dataMap , const char * path ) ;
#ifdef __cplusplus
}
#endif
#endif
#endif

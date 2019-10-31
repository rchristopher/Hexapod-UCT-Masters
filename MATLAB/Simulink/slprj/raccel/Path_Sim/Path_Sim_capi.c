#include "__cf_Path_Sim.h"
#include "rtw_capi.h"
#ifdef HOST_CAPI_BUILD
#include "Path_Sim_capi_host.h"
#define sizeof(s) ((size_t)(0xFFFF))
#undef rt_offsetof
#define rt_offsetof(s,el) ((uint16_T)(0xFFFF))
#define TARGET_CONST
#define TARGET_STRING(s) (s)    
#else
#include "builtin_typeid_types.h"
#include "Path_Sim.h"
#include "Path_Sim_capi.h"
#include "Path_Sim_private.h"
#ifdef LIGHT_WEIGHT_CAPI
#define TARGET_CONST                  
#define TARGET_STRING(s)               (NULL)                    
#else
#define TARGET_CONST                   const
#define TARGET_STRING(s)               (s)
#endif
#endif
static const rtwCAPI_Signals rtBlockSignals [ ] = { { 0 , 1 , TARGET_STRING (
"Path_Sim/Mathematical Model " ) , TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 0 }
, { 1 , 2 , TARGET_STRING ( "Path_Sim/Mathematical Model " ) , TARGET_STRING
( "" ) , 1 , 0 , 0 , 0 , 0 } , { 2 , 0 , TARGET_STRING ( "Path_Sim/Transpose"
) , TARGET_STRING ( "" ) , 0 , 0 , 1 , 0 , 0 } , { 3 , 1 , TARGET_STRING (
"Path_Sim/Mathematical Model /DYNAMICS " ) , TARGET_STRING ( "" ) , 3 , 0 , 0
, 0 , 0 } , { 4 , 2 , TARGET_STRING (
"Path_Sim/Mathematical Model /FORWARD KINEMATICS " ) , TARGET_STRING ( "" ) ,
0 , 0 , 0 , 0 , 0 } , { 0 , 0 , ( NULL ) , ( NULL ) , 0 , 0 , 0 , 0 , 0 } } ;
static const rtwCAPI_BlockParameters rtBlockParameters [ ] = { { 5 ,
TARGET_STRING ( "Path_Sim/Constant" ) , TARGET_STRING ( "Value" ) , 0 , 0 , 0
} , { 6 , TARGET_STRING ( "Path_Sim/Zero" ) , TARGET_STRING ( "Value" ) , 0 ,
2 , 0 } , { 7 , TARGET_STRING ( "Path_Sim/Variable Selector1" ) ,
TARGET_STRING ( "Elements" ) , 1 , 3 , 0 } , { 8 , TARGET_STRING (
"Path_Sim/Mathematical Model /L1x, L1z, L2, L3" ) , TARGET_STRING ( "Value" )
, 0 , 4 , 0 } , { 0 , ( NULL ) , ( NULL ) , 0 , 0 , 0 } } ; static const
rtwCAPI_ModelParameters rtModelParameters [ ] = { { 0 , ( NULL ) , 0 , 0 , 0
} } ;
#ifndef HOST_CAPI_BUILD
static void * rtDataAddrMap [ ] = { & rtB . n1mst2lf5a [ 0 ] , & rtB .
cdn1ccno31 [ 0 ] , & rtB . h3cto0llv3 [ 0 ] , & rtB . n1mst2lf5a [ 0 ] , &
rtB . cdn1ccno31 [ 0 ] , & rtP . Constant_Value [ 0 ] , & rtP . Zero_Value [
0 ] , & rtP . VariableSelector1_Elements , & rtP . L1xL1zL2L3_Value [ 0 ] , }
; static int32_T * rtVarDimsAddrMap [ ] = { ( NULL ) } ;
#endif
static TARGET_CONST rtwCAPI_DataTypeMap rtDataTypeMap [ ] = { { "double" ,
"real_T" , 0 , 0 , sizeof ( real_T ) , SS_DOUBLE , 0 , 0 } , { "int" ,
"int32_T" , 0 , 0 , sizeof ( int32_T ) , SS_INT32 , 0 , 0 } } ;
#ifdef HOST_CAPI_BUILD
#undef sizeof
#endif
static TARGET_CONST rtwCAPI_ElementMap rtElementMap [ ] = { { ( NULL ) , 0 ,
0 , 0 , 0 } , } ; static const rtwCAPI_DimensionMap rtDimensionMap [ ] = { {
rtwCAPI_MATRIX_COL_MAJOR , 0 , 2 , 0 } , { rtwCAPI_MATRIX_COL_MAJOR , 2 , 2 ,
0 } , { rtwCAPI_MATRIX_COL_MAJOR , 4 , 2 , 0 } , { rtwCAPI_SCALAR , 6 , 2 , 0
} , { rtwCAPI_VECTOR , 8 , 2 , 0 } } ; static const uint_T rtDimensionArray [
] = { 3 , 3 , 3 , 1 , 6 , 3 , 1 , 1 , 1 , 4 } ; static const real_T
rtcapiStoredFloats [ ] = { 0.0 , 1.0 } ; static const rtwCAPI_FixPtMap
rtFixPtMap [ ] = { { ( NULL ) , ( NULL ) , rtwCAPI_FIX_RESERVED , 0 , 0 , 0 }
, } ; static const rtwCAPI_SampleTimeMap rtSampleTimeMap [ ] = { { ( const
void * ) & rtcapiStoredFloats [ 0 ] , ( const void * ) & rtcapiStoredFloats [
1 ] , 0 , 0 } } ; static rtwCAPI_ModelMappingStaticInfo mmiStatic = { {
rtBlockSignals , 5 , ( NULL ) , 0 , ( NULL ) , 0 } , { rtBlockParameters , 4
, rtModelParameters , 0 } , { ( NULL ) , 0 } , { rtDataTypeMap ,
rtDimensionMap , rtFixPtMap , rtElementMap , rtSampleTimeMap ,
rtDimensionArray } , "float" , { 1620099481U , 1886550842U , 1189330833U ,
1677001332U } , ( NULL ) , 0 , 0 } ; const rtwCAPI_ModelMappingStaticInfo *
Path_Sim_GetCAPIStaticMap ( void ) { return & mmiStatic ; }
#ifndef HOST_CAPI_BUILD
void Path_Sim_InitializeDataMapInfo ( void ) { rtwCAPI_SetVersion ( ( *
rt_dataMapInfoPtr ) . mmi , 1 ) ; rtwCAPI_SetStaticMap ( ( *
rt_dataMapInfoPtr ) . mmi , & mmiStatic ) ; rtwCAPI_SetLoggingStaticMap ( ( *
rt_dataMapInfoPtr ) . mmi , ( NULL ) ) ; rtwCAPI_SetDataAddressMap ( ( *
rt_dataMapInfoPtr ) . mmi , rtDataAddrMap ) ; rtwCAPI_SetVarDimsAddressMap (
( * rt_dataMapInfoPtr ) . mmi , rtVarDimsAddrMap ) ;
rtwCAPI_SetInstanceLoggingInfo ( ( * rt_dataMapInfoPtr ) . mmi , ( NULL ) ) ;
rtwCAPI_SetChildMMIArray ( ( * rt_dataMapInfoPtr ) . mmi , ( NULL ) ) ;
rtwCAPI_SetChildMMIArrayLen ( ( * rt_dataMapInfoPtr ) . mmi , 0 ) ; }
#else
#ifdef __cplusplus
extern "C" {
#endif
void Path_Sim_host_InitializeDataMapInfo ( Path_Sim_host_DataMapInfo_T *
dataMap , const char * path ) { rtwCAPI_SetVersion ( dataMap -> mmi , 1 ) ;
rtwCAPI_SetStaticMap ( dataMap -> mmi , & mmiStatic ) ;
rtwCAPI_SetDataAddressMap ( dataMap -> mmi , NULL ) ;
rtwCAPI_SetVarDimsAddressMap ( dataMap -> mmi , NULL ) ; rtwCAPI_SetPath (
dataMap -> mmi , path ) ; rtwCAPI_SetFullPath ( dataMap -> mmi , NULL ) ;
rtwCAPI_SetChildMMIArray ( dataMap -> mmi , ( NULL ) ) ;
rtwCAPI_SetChildMMIArrayLen ( dataMap -> mmi , 0 ) ; }
#ifdef __cplusplus
}
#endif
#endif

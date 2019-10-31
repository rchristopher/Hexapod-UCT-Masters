#include "__cf_Path_Sim.h"
#include "rt_logging_mmi.h"
#include "Path_Sim_capi.h"
#include <math.h>
#include "Path_Sim.h"
#include "Path_Sim_private.h"
#include "Path_Sim_dt.h"
extern void * CreateDiagnosticAsVoidPtr_wrapper ( const char * id , int nargs
, ... ) ; RTWExtModeInfo * gblRTWExtModeInfo = NULL ; extern boolean_T
gblExtModeStartPktReceived ; void raccelForceExtModeShutdown ( ) { if ( !
gblExtModeStartPktReceived ) { boolean_T stopRequested = false ;
rtExtModeWaitForStartPkt ( gblRTWExtModeInfo , 1 , & stopRequested ) ; }
rtExtModeShutdown ( 1 ) ; }
#include "slsv_diagnostic_codegen_c_api.h"
const int_T gblNumToFiles = 0 ; const int_T gblNumFrFiles = 0 ; const int_T
gblNumFrWksBlocks = 0 ;
#ifdef RSIM_WITH_SOLVER_MULTITASKING
boolean_T gbl_raccel_isMultitasking = 1 ;
#else
boolean_T gbl_raccel_isMultitasking = 0 ;
#endif
boolean_T gbl_raccel_tid01eq = 0 ; int_T gbl_raccel_NumST = 2 ; const char_T
* gbl_raccel_Version = "9.1 (R2018a) 06-Feb-2018" ; void
raccel_setup_MMIStateLog ( SimStruct * S ) {
#ifdef UseMMIDataLogging
rt_FillStateSigInfoFromMMI ( ssGetRTWLogInfo ( S ) , & ssGetErrorStatus ( S )
) ;
#else
UNUSED_PARAMETER ( S ) ;
#endif
} static DataMapInfo rt_dataMapInfo ; DataMapInfo * rt_dataMapInfoPtr = &
rt_dataMapInfo ; rtwCAPI_ModelMappingInfo * rt_modelMapInfoPtr = & (
rt_dataMapInfo . mmi ) ; const char * gblSlvrJacPatternFileName =
"slprj\\raccel\\Path_Sim\\Path_Sim_Jpattern.mat" ; const int_T
gblNumRootInportBlks = 0 ; const int_T gblNumModelInputs = 0 ; extern
rtInportTUtable * gblInportTUtables ; extern const char * gblInportFileName ;
const int_T gblInportDataTypeIdx [ ] = { - 1 } ; const int_T gblInportDims [
] = { - 1 } ; const int_T gblInportComplex [ ] = { - 1 } ; const int_T
gblInportInterpoFlag [ ] = { - 1 } ; const int_T gblInportContinuous [ ] = {
- 1 } ;
#include "simstruc.h"
#include "fixedpoint.h"
B rtB ; static SimStruct model_S ; SimStruct * const rtS = & model_S ; void
MdlStart ( void ) { { void * * slioCatalogueAddr = rt_slioCatalogueAddr ( ) ;
void * r2 = ( NULL ) ; void * * pOSigstreamManagerAddr = ( NULL ) ; const int
maxErrorBufferSize = 16384 ; char errMsgCreatingOSigstreamManager [ 16384 ] ;
bool errorCreatingOSigstreamManager = false ; const char *
errorAddingR2SharedResource = ( NULL ) ; * slioCatalogueAddr =
rtwGetNewSlioCatalogue ( rt_GetMatSigLogSelectorFileName ( ) ) ;
errorAddingR2SharedResource = rtwAddR2SharedResource (
rtwGetPointerFromUniquePtr ( rt_slioCatalogue ( ) ) , 1 ) ; if (
errorAddingR2SharedResource != ( NULL ) ) { rtwTerminateSlioCatalogue (
slioCatalogueAddr ) ; * slioCatalogueAddr = ( NULL ) ; ssSetErrorStatus ( rtS
, errorAddingR2SharedResource ) ; return ; } r2 = rtwGetR2SharedResource (
rtwGetPointerFromUniquePtr ( rt_slioCatalogue ( ) ) ) ;
pOSigstreamManagerAddr = rt_GetOSigstreamManagerAddr ( ) ;
errorCreatingOSigstreamManager = rtwOSigstreamManagerCreateInstance (
rt_GetMatSigLogSelectorFileName ( ) , r2 , pOSigstreamManagerAddr ,
errMsgCreatingOSigstreamManager , maxErrorBufferSize ) ; if (
errorCreatingOSigstreamManager ) { * pOSigstreamManagerAddr = ( NULL ) ;
ssSetErrorStatus ( rtS , errMsgCreatingOSigstreamManager ) ; return ; } } {
bool externalInputIsInDatasetFormat = false ; void * pISigstreamManager =
rt_GetISigstreamManager ( ) ; rtwISigstreamManagerGetInputIsInDatasetFormat (
pISigstreamManager , & externalInputIsInDatasetFormat ) ; if (
externalInputIsInDatasetFormat ) { } } } void MdlOutputs ( int_T tid ) {
int32_T k ; real_T L1x ; real_T L2 ; real_T L3 ; real_T J_plus [ 54 ] ;
real_T J_d [ 54 ] ; real_T p3gjjpwmpy [ 9 ] ; real_T fiqbdyifed [ 9 ] ;
real_T irf0wgtwcl [ 9 ] ; real_T a0nss5njk5 [ 27 ] ; real_T gnuatyedt3 [ 27 ]
; real_T dwoa2dpul2 [ 9 ] ; int32_T i ; int32_T i_p ; real_T tmp [ 6 ] ;
real_T z1 ; real_T b_x ; real_T b_z1 ; real_T c_z1 ; real_T x_idx_0 ; real_T
z1_idx_0 ; real_T b_z1_idx_0 ; real_T S3_idx_0 ; real_T x_idx_1 ; real_T
z1_idx_1 ; real_T b_z1_idx_1 ; real_T S3_idx_1 ; if ( ssIsSampleHit ( rtS , 0
, 0 ) ) { if ( ssIsSampleHit ( rtS , 0 , 0 ) ) { L1x = rtP . L1xL1zL2L3_Value
[ 2 ] * rtP . L1xL1zL2L3_Value [ 2 ] ; L2 = rtP . L1xL1zL2L3_Value [ 3 ] *
rtP . L1xL1zL2L3_Value [ 3 ] ; L3 = 2.0 * rtP . L1xL1zL2L3_Value [ 2 ] * rtP
. L1xL1zL2L3_Value [ 3 ] ; z1 = muDoubleScalarAtan2 ( rtP . Constant_Value [
1 ] , rtP . Constant_Value [ 0 ] ) ; b_x = rtP . Constant_Value [ 0 ] - rtP .
L1xL1zL2L3_Value [ 0 ] * muDoubleScalarCos ( z1 ) ; b_z1 = b_x * b_x ; b_x =
rtP . Constant_Value [ 1 ] - rtP . L1xL1zL2L3_Value [ 0 ] * muDoubleScalarSin
( z1 ) ; c_z1 = b_x * b_x ; b_x = rtP . Constant_Value [ 2 ] - rtP .
L1xL1zL2L3_Value [ 1 ] ; b_z1 = ( ( ( ( b_z1 + c_z1 ) + b_x * b_x ) - L1x ) -
L2 ) / L3 ; x_idx_0 = rtP . Constant_Value [ 0 ] - rtP . L1xL1zL2L3_Value [ 0
] * muDoubleScalarCos ( z1 ) ; z1_idx_0 = z1 ; b_z1_idx_0 = b_z1 ; S3_idx_0 =
- muDoubleScalarSqrt ( 1.0 - b_z1 * b_z1 ) ; z1 = muDoubleScalarAtan2 ( rtP .
Constant_Value [ 4 ] , rtP . Constant_Value [ 3 ] ) ; b_x = rtP .
Constant_Value [ 3 ] - rtP . L1xL1zL2L3_Value [ 0 ] * muDoubleScalarCos ( z1
) ; b_z1 = b_x * b_x ; b_x = rtP . Constant_Value [ 4 ] - rtP .
L1xL1zL2L3_Value [ 0 ] * muDoubleScalarSin ( z1 ) ; c_z1 = b_x * b_x ; b_x =
rtP . Constant_Value [ 5 ] - rtP . L1xL1zL2L3_Value [ 1 ] ; b_z1 = ( ( ( (
b_z1 + c_z1 ) + b_x * b_x ) - L1x ) - L2 ) / L3 ; x_idx_1 = rtP .
Constant_Value [ 3 ] - rtP . L1xL1zL2L3_Value [ 0 ] * muDoubleScalarCos ( z1
) ; z1_idx_1 = z1 ; b_z1_idx_1 = b_z1 ; S3_idx_1 = - muDoubleScalarSqrt ( 1.0
- b_z1 * b_z1 ) ; z1 = muDoubleScalarAtan2 ( rtP . Constant_Value [ 7 ] , rtP
. Constant_Value [ 6 ] ) ; b_x = rtP . Constant_Value [ 6 ] - rtP .
L1xL1zL2L3_Value [ 0 ] * muDoubleScalarCos ( z1 ) ; b_z1 = b_x * b_x ; b_x =
rtP . Constant_Value [ 7 ] - rtP . L1xL1zL2L3_Value [ 0 ] * muDoubleScalarSin
( z1 ) ; c_z1 = b_x * b_x ; b_x = rtP . Constant_Value [ 8 ] - rtP .
L1xL1zL2L3_Value [ 1 ] ; b_z1 = ( ( ( ( b_z1 + c_z1 ) + b_x * b_x ) - L1x ) -
L2 ) / L3 ; L1x = - muDoubleScalarSqrt ( 1.0 - b_z1 * b_z1 ) ; L2 = rtP .
Constant_Value [ 6 ] - rtP . L1xL1zL2L3_Value [ 0 ] * muDoubleScalarCos ( z1
) ; L3 = rtP . Constant_Value [ 1 ] - rtP . L1xL1zL2L3_Value [ 0 ] *
muDoubleScalarSin ( z1_idx_0 ) ; b_x = muDoubleScalarSqrt ( x_idx_0 * x_idx_0
+ L3 * L3 ) ; L3 = rtP . Constant_Value [ 4 ] - rtP . L1xL1zL2L3_Value [ 0 ]
* muDoubleScalarSin ( z1_idx_1 ) ; x_idx_1 = muDoubleScalarSqrt ( x_idx_1 *
x_idx_1 + L3 * L3 ) ; L3 = rtP . Constant_Value [ 7 ] - rtP .
L1xL1zL2L3_Value [ 0 ] * muDoubleScalarSin ( z1 ) ; dwoa2dpul2 [ 0 ] =
z1_idx_0 ; dwoa2dpul2 [ 1 ] = muDoubleScalarAtan2 ( rtP . Constant_Value [ 2
] - rtP . L1xL1zL2L3_Value [ 1 ] , b_x ) - muDoubleScalarAtan2 ( rtP .
L1xL1zL2L3_Value [ 3 ] * S3_idx_0 , rtP . L1xL1zL2L3_Value [ 3 ] * b_z1_idx_0
+ rtP . L1xL1zL2L3_Value [ 2 ] ) ; dwoa2dpul2 [ 2 ] = muDoubleScalarAtan2 (
S3_idx_0 , b_z1_idx_0 ) ; dwoa2dpul2 [ 3 ] = z1_idx_1 ; dwoa2dpul2 [ 4 ] =
muDoubleScalarAtan2 ( rtP . Constant_Value [ 5 ] - rtP . L1xL1zL2L3_Value [ 1
] , x_idx_1 ) - muDoubleScalarAtan2 ( rtP . L1xL1zL2L3_Value [ 3 ] * S3_idx_1
, rtP . L1xL1zL2L3_Value [ 3 ] * b_z1_idx_1 + rtP . L1xL1zL2L3_Value [ 2 ] )
; dwoa2dpul2 [ 5 ] = muDoubleScalarAtan2 ( S3_idx_1 , b_z1_idx_1 ) ;
dwoa2dpul2 [ 6 ] = z1 ; dwoa2dpul2 [ 7 ] = muDoubleScalarAtan2 ( rtP .
Constant_Value [ 8 ] - rtP . L1xL1zL2L3_Value [ 1 ] , muDoubleScalarSqrt ( L2
* L2 + L3 * L3 ) ) - muDoubleScalarAtan2 ( rtP . L1xL1zL2L3_Value [ 3 ] * L1x
, rtP . L1xL1zL2L3_Value [ 3 ] * b_z1 + rtP . L1xL1zL2L3_Value [ 2 ] ) ;
dwoa2dpul2 [ 8 ] = muDoubleScalarAtan2 ( L1x , b_z1 ) ; memcpy ( & rtB .
cdn1ccno31 [ 0 ] , & dwoa2dpul2 [ 0 ] , 9U * sizeof ( real_T ) ) ; L1x = rtP
. L1xL1zL2L3_Value [ 0 ] ; L2 = rtP . L1xL1zL2L3_Value [ 2 ] ; L3 = rtP .
L1xL1zL2L3_Value [ 3 ] ; for ( k = 0 ; k < 3 ; k ++ ) { memset ( & J_plus [ 0
] , 0 , 54U * sizeof ( real_T ) ) ; z1 = muDoubleScalarCos ( rtB . cdn1ccno31
[ 3 * k + 2 ] ) ; b_x = muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] )
; b_z1 = muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; z1_idx_0 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; b_z1_idx_0 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; S3_idx_0 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; z1_idx_1 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; b_z1_idx_1 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; S3_idx_1 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; x_idx_1 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; x_idx_0 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; c_z1 =
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; J_plus [ 18 * k ] = -
( ( ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [
3 * k + 2 ] ) * L3 + L1x ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1
] ) * L2 ) * ( muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) * 2.0 ) ) / (
( ( ( ( ( ( ( ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] * 2.0 +
rtB . cdn1ccno31 [ 3 * k + 2 ] * 2.0 ) * ( L3 * L3 ) + muDoubleScalarCos (
rtB . cdn1ccno31 [ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 ) ) + L2 * L2 ) + L3 * L3
) + L1x * L1x * 2.0 ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] *
2.0 + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * ( 2.0 * L2 * L3 ) ) +
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) * ( 4.0 * L3 * L1x ) ) + 2.0 * L2 * L3 * muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k + 2 ] ) ) + 4.0 * L2 * L1x * muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k + 1 ] ) ) + 2.0 ) ; J_plus [ 3 + 18 * k ] = ( (
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) * L3 + L1x ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) *
L2 ) * ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) * 2.0 ) / ( ( ( ( (
( ( ( ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] * 2.0 + rtB .
cdn1ccno31 [ 3 * k + 2 ] * 2.0 ) * ( L3 * L3 ) + muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 ) ) + L2 * L2 ) + L3 * L3 ) +
L1x * L1x * 2.0 ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] * 2.0
+ rtB . cdn1ccno31 [ 3 * k + 2 ] ) * ( 2.0 * L2 * L3 ) ) + muDoubleScalarCos
( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * ( 4.0 *
L3 * L1x ) ) + 2.0 * L2 * L3 * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k +
2 ] ) ) + 4.0 * L2 * L1x * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ]
) ) + 2.0 ) ; J_plus [ 6 + 18 * k ] = 0.0 ; J_plus [ 9 + 18 * k ] = 0.0 ;
J_plus [ 12 + 18 * k ] = 0.0 ; J_plus [ 15 + 18 * k ] = 1.0 / ( ( ( ( ( ( ( (
( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] * 2.0 + rtB . cdn1ccno31
[ 3 * k + 2 ] * 2.0 ) * ( L3 * L3 ) / 2.0 + muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 ) / 2.0 ) + L2 * L2 / 2.0 ) + L3
* L3 / 2.0 ) + L1x * L1x ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1
] * 2.0 + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * ( L2 * L3 ) ) +
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) * ( 2.0 * L3 * L1x ) ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) * ( L2 * L3 ) ) + 2.0 * L2 * L1x * muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k + 1 ] ) ) + 1.0 ) ; J_plus [ 1 + 18 * k ] = ( (
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) - muDoubleScalarSin (
rtB . cdn1ccno31 [ 3 * k + 2 ] * 2.0 + rtB . cdn1ccno31 [ 3 * k + 1 ] ) * (
L3 * L3 ) / 2.0 ) + muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * (
L3 * L3 ) / 2.0 ) * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) / ( ( (
L3 * L3 - L3 * L3 * ( z1 * z1 ) ) + 1.0 ) * L2 ) ; J_plus [ 4 + 18 * k ] = (
( muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) - muDoubleScalarSin (
rtB . cdn1ccno31 [ 3 * k + 2 ] * 2.0 + rtB . cdn1ccno31 [ 3 * k + 1 ] ) * (
L3 * L3 ) / 2.0 ) + muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * (
L3 * L3 ) / 2.0 ) * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) / ( ( (
L3 * L3 - L3 * L3 * ( b_x * b_x ) ) + 1.0 ) * L2 ) ; J_plus [ 7 + 18 * k ] =
- ( ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * 2.0 -
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] * 2.0 + rtB . cdn1ccno31 [
3 * k + 1 ] ) * ( L3 * L3 ) ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k
+ 1 ] ) * ( L3 * L3 ) ) / ( ( ( L3 * L3 - muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k + 2 ] * 2.0 ) * ( L3 * L3 ) ) + 2.0 ) * L2 ) ; J_plus [ 10
+ 18 * k ] = muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) * L3 *
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) / ( ( ( L3 * L3 - L3 * L3 *
( b_z1 * b_z1 ) ) + 1.0 ) * L2 ) ; J_plus [ 13 + 18 * k ] = - (
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) * L3 * muDoubleScalarCos (
rtB . cdn1ccno31 [ 3 * k + 2 ] ) ) / ( ( ( L3 * L3 - L3 * L3 * ( z1_idx_0 *
z1_idx_0 ) ) + 1.0 ) * L2 ) ; J_plus [ 16 + 18 * k ] = 0.0 ; J_plus [ 2 + 18
* k ] = ( ( ( ( L3 * L3 * ( b_z1_idx_0 * b_z1_idx_0 ) * muDoubleScalarSin (
rtB . cdn1ccno31 [ 3 * k + 1 ] ) - muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 *
k + 1 ] ) * ( L3 * L3 ) ) - muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1
] ) ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * ( L3 * L3 ) *
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) * muDoubleScalarSin (
rtB . cdn1ccno31 [ 3 * k + 2 ] ) ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3
* k + 1 ] ) * ( L2 * L3 ) * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 2
] ) ) * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) / ( ( ( L3 * L3 - L3
* L3 * ( S3_idx_0 * S3_idx_0 ) ) + 1.0 ) * L2 ) ; J_plus [ 5 + 18 * k ] = ( (
( ( L3 * L3 * ( z1_idx_1 * z1_idx_1 ) * muDoubleScalarSin ( rtB . cdn1ccno31
[ 3 * k + 1 ] ) - muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * ( L3
* L3 ) ) - muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) ) +
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * ( L3 * L3 ) *
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) * muDoubleScalarSin (
rtB . cdn1ccno31 [ 3 * k + 2 ] ) ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3
* k + 1 ] ) * ( L2 * L3 ) * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 2
] ) ) * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) / ( ( ( L3 * L3 - L3
* L3 * ( b_z1_idx_1 * b_z1_idx_1 ) ) + 1.0 ) * L2 ) ; J_plus [ 8 + 18 * k ] =
( ( ( ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * ( L3 * L3 ) +
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) ) - muDoubleScalarCos (
rtB . cdn1ccno31 [ 3 * k + 1 ] ) * ( L3 * L3 ) * ( S3_idx_1 * S3_idx_1 ) ) +
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) * ( L3 * L3 ) *
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * muDoubleScalarSin (
rtB . cdn1ccno31 [ 3 * k + 2 ] ) ) + muDoubleScalarSin ( rtB . cdn1ccno31 [ 3
* k + 1 ] ) * ( L2 * L3 ) * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 2
] ) ) / ( ( ( L3 * L3 - L3 * L3 * ( x_idx_1 * x_idx_1 ) ) + 1.0 ) * L2 ) ;
J_plus [ 11 + 18 * k ] = - ( ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k +
2 ] ) * L3 + L2 ) * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) ) / ( (
( L3 * L3 - L3 * L3 * ( x_idx_0 * x_idx_0 ) ) + 1.0 ) * L2 ) ; J_plus [ 14 +
18 * k ] = ( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 2 ] ) * L3 + L2 )
* muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) / ( ( ( L3 * L3 - L3 * L3
* ( c_z1 * c_z1 ) ) + 1.0 ) * L2 ) ; J_plus [ 17 + 18 * k ] = 0.0 ; memset (
& fiqbdyifed [ 0 ] , 0 , 9U * sizeof ( real_T ) ) ; for ( i = 0 ; i < 3 ; i
++ ) { fiqbdyifed [ i + 3 * k ] = 0.0 ; for ( i_p = 0 ; i_p < 6 ; i_p ++ ) {
fiqbdyifed [ i + 3 * k ] += J_plus [ ( 3 * i_p + i ) + 18 * k ] * rtP .
Zero_Value [ 6 * k + i_p ] ; } } memset ( & J_d [ 0 ] , 0 , 54U * sizeof (
real_T ) ) ; J_d [ 18 * k ] = ( ( muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 *
k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * L3 + muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k + 1 ] ) * L2 ) * ( fiqbdyifed [ 3 * k + 1 ] *
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) ) - ( ( muDoubleScalarCos (
rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * L3 + L1x
) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * L2 ) * (
fiqbdyifed [ 3 * k ] * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) ) ) +
fiqbdyifed [ 3 * k + 2 ] * L3 * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k
+ 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k ] ) ; J_d [ 6 + 18 * k ] = ( ( muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * L3 +
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * L2 ) * ( fiqbdyifed [
3 * k + 1 ] * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) ) - (
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) * L3 + muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * L2 ) *
( fiqbdyifed [ 3 * k ] * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) ) )
+ fiqbdyifed [ 3 * k + 2 ] * L3 * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 *
k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * muDoubleScalarCos ( rtB .
cdn1ccno31 [ 3 * k ] ) ; J_d [ 12 + 18 * k ] = ( fiqbdyifed [ 3 * k + 1 ] *
L3 * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [
3 * k + 2 ] ) * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) + fiqbdyifed
[ 3 * k + 2 ] * L3 * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB
. cdn1ccno31 [ 3 * k + 2 ] ) * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ]
) ) - muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [
3 * k + 2 ] ) * ( fiqbdyifed [ 3 * k ] * L3 ) * muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k ] ) ; J_d [ 1 + 18 * k ] = ( ( muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * L3 +
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * L2 ) * ( - fiqbdyifed
[ 3 * k + 1 ] * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) ) - ( (
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) * L3 + L1x ) + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) *
L2 ) * ( fiqbdyifed [ 3 * k ] * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k
] ) ) ) - fiqbdyifed [ 3 * k + 2 ] * L3 * muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) *
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) ; J_d [ 7 + 18 * k ] = ( (
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) * L3 + muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * L2 ) *
( fiqbdyifed [ 3 * k ] * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) ) +
( muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 *
k + 2 ] ) * L3 + muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k + 1 ] ) * L2 )
* ( fiqbdyifed [ 3 * k + 1 ] * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ]
) ) ) + fiqbdyifed [ 3 * k + 2 ] * L3 * muDoubleScalarCos ( rtB . cdn1ccno31
[ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k ] ) ; J_d [ 13 + 18 * k ] = ( muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * ( fiqbdyifed [
3 * k ] * L3 ) * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) +
fiqbdyifed [ 3 * k + 1 ] * L3 * muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k
+ 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) * muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k ] ) ) + fiqbdyifed [ 3 * k + 2 ] * L3 * muDoubleScalarCos
( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) *
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) ; J_d [ 2 + 18 * k ] = 0.0 ;
J_d [ 8 + 18 * k ] = ( muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] +
rtB . cdn1ccno31 [ 3 * k + 2 ] ) * L3 + muDoubleScalarSin ( rtB . cdn1ccno31
[ 3 * k + 1 ] ) * L2 ) * fiqbdyifed [ 3 * k + 1 ] + fiqbdyifed [ 3 * k + 2 ]
* L3 * muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31
[ 3 * k + 2 ] ) ; J_d [ 14 + 18 * k ] = fiqbdyifed [ 3 * k + 1 ] * L3 *
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k
+ 2 ] ) + fiqbdyifed [ 3 * k + 2 ] * L3 * muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k + 1 ] + rtB . cdn1ccno31 [ 3 * k + 2 ] ) ; J_d [ 3 + 18 *
k ] = 0.0 ; J_d [ 9 + 18 * k ] = - fiqbdyifed [ 3 * k ] * muDoubleScalarCos (
rtB . cdn1ccno31 [ 3 * k ] ) ; J_d [ 15 + 18 * k ] = - fiqbdyifed [ 3 * k ] *
muDoubleScalarCos ( rtB . cdn1ccno31 [ 3 * k ] ) ; J_d [ 4 + 18 * k ] = 0.0 ;
J_d [ 10 + 18 * k ] = - fiqbdyifed [ 3 * k ] * muDoubleScalarSin ( rtB .
cdn1ccno31 [ 3 * k ] ) ; J_d [ 16 + 18 * k ] = - fiqbdyifed [ 3 * k ] *
muDoubleScalarSin ( rtB . cdn1ccno31 [ 3 * k ] ) ; J_d [ 5 + 18 * k ] = 0.0 ;
J_d [ 18 * k + 11 ] = 0.0 ; J_d [ 18 * k + 17 ] = 0.0 ; memset ( & p3gjjpwmpy
[ 0 ] , 0 , 9U * sizeof ( real_T ) ) ; for ( i = 0 ; i < 6 ; i ++ ) { tmp [ i
] = rtP . Zero_Value [ 6 * k + i ] - ( ( J_d [ ( 18 * k + i ) + 6 ] *
fiqbdyifed [ 3 * k + 1 ] + J_d [ 18 * k + i ] * fiqbdyifed [ 3 * k ] ) + J_d
[ ( 18 * k + i ) + 12 ] * fiqbdyifed [ 3 * k + 2 ] ) ; } for ( i = 0 ; i < 3
; i ++ ) { p3gjjpwmpy [ i + 3 * k ] = 0.0 ; for ( i_p = 0 ; i_p < 6 ; i_p ++
) { p3gjjpwmpy [ i + 3 * k ] += J_plus [ ( 3 * i_p + i ) + 18 * k ] * tmp [
i_p ] ; } } } L1x = rtP . L1xL1zL2L3_Value [ 0 ] / 2.0 ; L2 = rtP .
L1xL1zL2L3_Value [ 2 ] / 2.0 ; L3 = rtP . L1xL1zL2L3_Value [ 3 ] / 2.0 ; for
( k = 0 ; k < 3 ; k ++ ) { memset ( & gnuatyedt3 [ 0 ] , 0 , 27U * sizeof (
real_T ) ) ; memset ( & a0nss5njk5 [ 0 ] , 0 , 27U * sizeof ( real_T ) ) ;
gnuatyedt3 [ 9 * k ] = ( ( ( ( ( ( ( ( ( ( ( ( ( L2 * L2 * 0.04977 / 2.0 + L2
* L2 * 0.15456 / 2.0 ) + L3 * L3 * 0.15456 / 2.0 ) + L1x * L1x * 0.14327 ) +
L1x * L1x * 0.04977 ) + L1x * L1x * 0.15456 ) + muDoubleScalarCos (
dwoa2dpul2 [ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 * 0.04977 ) / 2.0 ) +
muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 * 0.15456 )
/ 2.0 ) + muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3
* k + 2 ] * 2.0 ) * ( L3 * L3 * 0.15456 ) / 2.0 ) + 2.0 * L3 * L1x * 0.15456
* muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] + dwoa2dpul2 [ 3 * k + 2 ] ) )
+ L2 * L3 * 0.15456 * muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 2 ] ) ) + 2.0
* L2 * L1x * 0.04977 * muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] ) ) + 2.0
* L2 * L1x * 0.15456 * muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] ) ) +
muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3 * k + 2 ]
) * ( L2 * L3 * 0.15456 ) ) + 0.00098816082 ; gnuatyedt3 [ 3 + 9 * k ] = 0.0
; gnuatyedt3 [ 6 + 9 * k ] = 0.0 ; gnuatyedt3 [ 1 + 9 * k ] = 0.0 ;
gnuatyedt3 [ 4 + 9 * k ] = ( ( L2 * L2 * 0.04977 + L2 * L2 * 0.15456 ) + L3 *
L3 * 0.15456 ) + 2.0 * L2 * L3 * 0.15456 * muDoubleScalarCos ( dwoa2dpul2 [ 3
* k + 2 ] ) ; gnuatyedt3 [ 7 + 9 * k ] = ( muDoubleScalarCos ( dwoa2dpul2 [ 3
* k + 2 ] ) * L2 + L3 ) * ( L3 * 0.15456 ) ; gnuatyedt3 [ 2 + 9 * k ] = 0.0 ;
gnuatyedt3 [ 5 + 9 * k ] = ( muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 2 ] ) *
L2 + L3 ) * ( L3 * 0.15456 ) ; gnuatyedt3 [ 8 + 9 * k ] = L3 * L3 * 0.15456 ;
a0nss5njk5 [ 9 * k ] = ( ( ( muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] *
2.0 + dwoa2dpul2 [ 3 * k + 2 ] * 2.0 ) * ( L3 * L3 * 0.15456 ) / 2.0 +
muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] + dwoa2dpul2 [ 3 * k + 2 ] ) * (
L3 * L1x * 0.15456 ) ) + L2 * L3 * 0.15456 * muDoubleScalarSin ( dwoa2dpul2 [
3 * k + 2 ] ) / 2.0 ) + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 +
dwoa2dpul2 [ 3 * k + 2 ] ) * ( L2 * L3 * 0.15456 ) / 2.0 ) * - fiqbdyifed [ 3
* k + 2 ] - ( ( ( ( ( ( muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 )
* ( L2 * L2 * 0.04977 ) / 2.0 + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ]
* 2.0 ) * ( L2 * L2 * 0.15456 ) / 2.0 ) + muDoubleScalarSin ( dwoa2dpul2 [ 3
* k + 1 ] * 2.0 + dwoa2dpul2 [ 3 * k + 2 ] * 2.0 ) * ( L3 * L3 * 0.15456 ) /
2.0 ) + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] + dwoa2dpul2 [ 3 * k + 2
] ) * ( L3 * L1x * 0.15456 ) ) + L2 * L1x * 0.04977 * muDoubleScalarSin (
dwoa2dpul2 [ 3 * k + 1 ] ) ) + L2 * L1x * 0.15456 * muDoubleScalarSin (
dwoa2dpul2 [ 3 * k + 1 ] ) ) + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] *
2.0 + dwoa2dpul2 [ 3 * k + 2 ] ) * ( L2 * L3 * 0.15456 ) ) * fiqbdyifed [ 3 *
k + 1 ] ; a0nss5njk5 [ 3 + 9 * k ] = ( ( ( ( ( ( muDoubleScalarSin (
dwoa2dpul2 [ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 * 0.04977 ) / 2.0 +
muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 * 0.15456 )
/ 2.0 ) + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3
* k + 2 ] * 2.0 ) * ( L3 * L3 * 0.15456 ) / 2.0 ) + muDoubleScalarSin (
dwoa2dpul2 [ 3 * k + 1 ] + dwoa2dpul2 [ 3 * k + 2 ] ) * ( L3 * L1x * 0.15456
) ) + L2 * L1x * 0.04977 * muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] ) ) +
L2 * L1x * 0.15456 * muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] ) ) +
muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3 * k + 2 ]
) * ( L2 * L3 * 0.15456 ) ) * - fiqbdyifed [ 3 * k ] ; a0nss5njk5 [ 6 + 9 * k
] = ( ( ( muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3
* k + 2 ] * 2.0 ) * ( L3 * L3 * 0.15456 ) / 2.0 + muDoubleScalarSin (
dwoa2dpul2 [ 3 * k + 1 ] + dwoa2dpul2 [ 3 * k + 2 ] ) * ( L3 * L1x * 0.15456
) ) + L2 * L3 * 0.15456 * muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 2 ] ) /
2.0 ) + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3 *
k + 2 ] ) * ( L2 * L3 * 0.15456 ) / 2.0 ) * - fiqbdyifed [ 3 * k ] ;
a0nss5njk5 [ 1 + 9 * k ] = ( ( ( ( ( ( muDoubleScalarSin ( dwoa2dpul2 [ 3 * k
+ 1 ] * 2.0 ) * ( L2 * L2 * 0.04977 ) / 2.0 + muDoubleScalarSin ( dwoa2dpul2
[ 3 * k + 1 ] * 2.0 ) * ( L2 * L2 * 0.15456 ) / 2.0 ) + muDoubleScalarSin (
dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3 * k + 2 ] * 2.0 ) * ( L3 * L3
* 0.15456 ) / 2.0 ) + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] +
dwoa2dpul2 [ 3 * k + 2 ] ) * ( L3 * L1x * 0.15456 ) ) + L2 * L1x * 0.04977 *
muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] ) ) + L2 * L1x * 0.15456 *
muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] ) ) + muDoubleScalarSin (
dwoa2dpul2 [ 3 * k + 1 ] * 2.0 + dwoa2dpul2 [ 3 * k + 2 ] ) * ( L2 * L3 *
0.15456 ) ) * fiqbdyifed [ 3 * k ] ; a0nss5njk5 [ 4 + 9 * k ] = - L2 * L3 *
0.15456 * fiqbdyifed [ 3 * k + 2 ] * muDoubleScalarSin ( dwoa2dpul2 [ 3 * k +
2 ] ) ; a0nss5njk5 [ 7 + 9 * k ] = - L2 * L3 * 0.15456 * fiqbdyifed [ 3 * k +
1 ] * muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 2 ] ) - L2 * L3 * 0.15456 *
fiqbdyifed [ 3 * k + 2 ] * muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 2 ] ) ;
a0nss5njk5 [ 2 + 9 * k ] = ( ( ( muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ]
* 2.0 + dwoa2dpul2 [ 3 * k + 2 ] * 2.0 ) * ( L3 * L3 * 0.15456 ) / 2.0 +
muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] + dwoa2dpul2 [ 3 * k + 2 ] ) * (
L3 * L1x * 0.15456 ) ) + L2 * L3 * 0.15456 * muDoubleScalarSin ( dwoa2dpul2 [
3 * k + 2 ] ) / 2.0 ) + muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 1 ] * 2.0 +
dwoa2dpul2 [ 3 * k + 2 ] ) * ( L2 * L3 * 0.15456 ) / 2.0 ) * fiqbdyifed [ 3 *
k ] ; a0nss5njk5 [ 5 + 9 * k ] = L2 * L3 * 0.15456 * fiqbdyifed [ 3 * k + 1 ]
* muDoubleScalarSin ( dwoa2dpul2 [ 3 * k + 2 ] ) ; a0nss5njk5 [ 8 + 9 * k ] =
0.0 ; memset ( & irf0wgtwcl [ 0 ] , 0 , 9U * sizeof ( real_T ) ) ; memset ( &
rtB . n1mst2lf5a [ 0 ] , 0 , 9U * sizeof ( real_T ) ) ; irf0wgtwcl [ 3 * k ]
= L2 * 9.80655 * 0.04977 * muDoubleScalarCos ( dwoa2dpul2 [ 3 * k ] ) ;
irf0wgtwcl [ 1 + 3 * k ] = ( muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] +
dwoa2dpul2 [ 3 * k + 2 ] ) * L3 + muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1
] ) * rtP . L1xL1zL2L3_Value [ 2 ] ) * 1.515700368 ; irf0wgtwcl [ 2 + 3 * k ]
= muDoubleScalarCos ( dwoa2dpul2 [ 3 * k + 1 ] + dwoa2dpul2 [ 3 * k + 2 ] ) *
( L3 * 9.80655 * 0.15456 ) ; for ( i = 0 ; i < 3 ; i ++ ) { rtB . n1mst2lf5a
[ i + 3 * k ] = irf0wgtwcl [ 3 * k + i ] + ( ( gnuatyedt3 [ ( 9 * k + i ) + 6
] * p3gjjpwmpy [ 3 * k + 2 ] + ( gnuatyedt3 [ ( 9 * k + i ) + 3 ] *
p3gjjpwmpy [ 3 * k + 1 ] + gnuatyedt3 [ 9 * k + i ] * p3gjjpwmpy [ 3 * k ] )
) + ( a0nss5njk5 [ ( 9 * k + i ) + 6 ] * fiqbdyifed [ 3 * k + 2 ] + (
a0nss5njk5 [ ( 9 * k + i ) + 3 ] * fiqbdyifed [ 3 * k + 1 ] + a0nss5njk5 [ 9
* k + i ] * fiqbdyifed [ 3 * k ] ) ) ) ; } } } k = rtP .
VariableSelector1_Elements ; if ( rtP . VariableSelector1_Elements < 0 ) { k
= 0 ; } else { if ( rtP . VariableSelector1_Elements >= 3 ) { k = 2 ; } } rtB
. h3cto0llv3 [ 0 ] = rtB . cdn1ccno31 [ k ] ; rtB . h3cto0llv3 [ 1 ] = rtB .
cdn1ccno31 [ 3 + k ] ; rtB . h3cto0llv3 [ 2 ] = rtB . cdn1ccno31 [ 6 + k ] ;
} UNUSED_PARAMETER ( tid ) ; } void MdlUpdate ( int_T tid ) {
UNUSED_PARAMETER ( tid ) ; } void MdlUpdateTID1 ( int_T tid ) {
UNUSED_PARAMETER ( tid ) ; } void MdlTerminate ( void ) { if (
rt_slioCatalogue ( ) != ( NULL ) ) { void * * slioCatalogueAddr =
rt_slioCatalogueAddr ( ) ; rtwSaveDatasetsToMatFile (
rtwGetPointerFromUniquePtr ( rt_slioCatalogue ( ) ) ,
rt_GetMatSigstreamLoggingFileName ( ) ) ; rtwTerminateSlioCatalogue (
slioCatalogueAddr ) ; * slioCatalogueAddr = NULL ; } } void
MdlInitializeSizes ( void ) { ssSetNumContStates ( rtS , 0 ) ; ssSetNumY (
rtS , 0 ) ; ssSetNumU ( rtS , 0 ) ; ssSetDirectFeedThrough ( rtS , 0 ) ;
ssSetNumSampleTimes ( rtS , 1 ) ; ssSetNumBlocks ( rtS , 17 ) ;
ssSetNumBlockIO ( rtS , 3 ) ; ssSetNumBlockParams ( rtS , 32 ) ; } void
MdlInitializeSampleTimes ( void ) { ssSetSampleTime ( rtS , 0 , 0.0 ) ;
ssSetOffsetTime ( rtS , 0 , 1.0 ) ; } void raccel_set_checksum ( ) {
ssSetChecksumVal ( rtS , 0 , 1620099481U ) ; ssSetChecksumVal ( rtS , 1 ,
1886550842U ) ; ssSetChecksumVal ( rtS , 2 , 1189330833U ) ; ssSetChecksumVal
( rtS , 3 , 1677001332U ) ; }
#if defined(_MSC_VER)
#pragma optimize( "", off )
#endif
SimStruct * raccel_register_model ( void ) { static struct _ssMdlInfo mdlInfo
; ( void ) memset ( ( char * ) rtS , 0 , sizeof ( SimStruct ) ) ; ( void )
memset ( ( char * ) & mdlInfo , 0 , sizeof ( struct _ssMdlInfo ) ) ;
ssSetMdlInfoPtr ( rtS , & mdlInfo ) ; { static time_T mdlPeriod [
NSAMPLE_TIMES ] ; static time_T mdlOffset [ NSAMPLE_TIMES ] ; static time_T
mdlTaskTimes [ NSAMPLE_TIMES ] ; static int_T mdlTsMap [ NSAMPLE_TIMES ] ;
static int_T mdlSampleHits [ NSAMPLE_TIMES ] ; static boolean_T
mdlTNextWasAdjustedPtr [ NSAMPLE_TIMES ] ; static int_T mdlPerTaskSampleHits
[ NSAMPLE_TIMES * NSAMPLE_TIMES ] ; static time_T mdlTimeOfNextSampleHit [
NSAMPLE_TIMES ] ; { int_T i ; for ( i = 0 ; i < NSAMPLE_TIMES ; i ++ ) {
mdlPeriod [ i ] = 0.0 ; mdlOffset [ i ] = 0.0 ; mdlTaskTimes [ i ] = 0.0 ;
mdlTsMap [ i ] = i ; mdlSampleHits [ i ] = 1 ; } } ssSetSampleTimePtr ( rtS ,
& mdlPeriod [ 0 ] ) ; ssSetOffsetTimePtr ( rtS , & mdlOffset [ 0 ] ) ;
ssSetSampleTimeTaskIDPtr ( rtS , & mdlTsMap [ 0 ] ) ; ssSetTPtr ( rtS , &
mdlTaskTimes [ 0 ] ) ; ssSetSampleHitPtr ( rtS , & mdlSampleHits [ 0 ] ) ;
ssSetTNextWasAdjustedPtr ( rtS , & mdlTNextWasAdjustedPtr [ 0 ] ) ;
ssSetPerTaskSampleHitsPtr ( rtS , & mdlPerTaskSampleHits [ 0 ] ) ;
ssSetTimeOfNextSampleHitPtr ( rtS , & mdlTimeOfNextSampleHit [ 0 ] ) ; }
ssSetSolverMode ( rtS , SOLVER_MODE_SINGLETASKING ) ; { ssSetBlockIO ( rtS ,
( ( void * ) & rtB ) ) ; ( void ) memset ( ( ( void * ) & rtB ) , 0 , sizeof
( B ) ) ; } ssSetDefaultParam ( rtS , ( real_T * ) & rtP ) ; { static
DataTypeTransInfo dtInfo ; ( void ) memset ( ( char_T * ) & dtInfo , 0 ,
sizeof ( dtInfo ) ) ; ssSetModelMappingInfo ( rtS , & dtInfo ) ; dtInfo .
numDataTypes = 14 ; dtInfo . dataTypeSizes = & rtDataTypeSizes [ 0 ] ; dtInfo
. dataTypeNames = & rtDataTypeNames [ 0 ] ; dtInfo . BTransTable = &
rtBTransTable ; dtInfo . PTransTable = & rtPTransTable ; }
Path_Sim_InitializeDataMapInfo ( ) ; ssSetIsRapidAcceleratorActive ( rtS ,
true ) ; ssSetRootSS ( rtS , rtS ) ; ssSetVersion ( rtS ,
SIMSTRUCT_VERSION_LEVEL2 ) ; ssSetModelName ( rtS , "Path_Sim" ) ; ssSetPath
( rtS , "Path_Sim" ) ; ssSetTStart ( rtS , 0.0 ) ; ssSetTFinal ( rtS , 10.0 )
; { static RTWLogInfo rt_DataLoggingInfo ; rt_DataLoggingInfo .
loggingInterval = NULL ; ssSetRTWLogInfo ( rtS , & rt_DataLoggingInfo ) ; } {
rtliSetLogXSignalInfo ( ssGetRTWLogInfo ( rtS ) , ( NULL ) ) ;
rtliSetLogXSignalPtrs ( ssGetRTWLogInfo ( rtS ) , ( NULL ) ) ; rtliSetLogT (
ssGetRTWLogInfo ( rtS ) , "tout" ) ; rtliSetLogX ( ssGetRTWLogInfo ( rtS ) ,
"" ) ; rtliSetLogXFinal ( ssGetRTWLogInfo ( rtS ) , "" ) ;
rtliSetLogVarNameModifier ( ssGetRTWLogInfo ( rtS ) , "none" ) ;
rtliSetLogFormat ( ssGetRTWLogInfo ( rtS ) , 4 ) ; rtliSetLogMaxRows (
ssGetRTWLogInfo ( rtS ) , 0 ) ; rtliSetLogDecimation ( ssGetRTWLogInfo ( rtS
) , 1 ) ; rtliSetLogY ( ssGetRTWLogInfo ( rtS ) , "" ) ;
rtliSetLogYSignalInfo ( ssGetRTWLogInfo ( rtS ) , ( NULL ) ) ;
rtliSetLogYSignalPtrs ( ssGetRTWLogInfo ( rtS ) , ( NULL ) ) ; } { static
ssSolverInfo slvrInfo ; ssSetStepSize ( rtS , 0.2 ) ; ssSetMinStepSize ( rtS
, 0.0 ) ; ssSetMaxNumMinSteps ( rtS , - 1 ) ; ssSetMinStepViolatedError ( rtS
, 0 ) ; ssSetMaxStepSize ( rtS , 0.2 ) ; ssSetSolverMaxOrder ( rtS , - 1 ) ;
ssSetSolverRefineFactor ( rtS , 1 ) ; ssSetOutputTimes ( rtS , ( NULL ) ) ;
ssSetNumOutputTimes ( rtS , 0 ) ; ssSetOutputTimesOnly ( rtS , 0 ) ;
ssSetOutputTimesIndex ( rtS , 0 ) ; ssSetZCCacheNeedsReset ( rtS , 0 ) ;
ssSetDerivCacheNeedsReset ( rtS , 0 ) ; ssSetNumNonContDerivSigInfos ( rtS ,
0 ) ; ssSetNonContDerivSigInfos ( rtS , ( NULL ) ) ; ssSetSolverInfo ( rtS ,
& slvrInfo ) ; ssSetSolverName ( rtS , "VariableStepDiscrete" ) ;
ssSetVariableStepSolver ( rtS , 1 ) ; ssSetSolverConsistencyChecking ( rtS ,
0 ) ; ssSetSolverAdaptiveZcDetection ( rtS , 0 ) ;
ssSetSolverRobustResetMethod ( rtS , 0 ) ; ssSetSolverStateProjection ( rtS ,
0 ) ; ssSetSolverMassMatrixType ( rtS , ( ssMatrixType ) 0 ) ;
ssSetSolverMassMatrixNzMax ( rtS , 0 ) ; ssSetModelOutputs ( rtS , MdlOutputs
) ; ssSetModelLogData ( rtS , rt_UpdateTXYLogVars ) ;
ssSetModelLogDataIfInInterval ( rtS , rt_UpdateTXXFYLogVars ) ;
ssSetModelUpdate ( rtS , MdlUpdate ) ; ssSetTNextTid ( rtS , INT_MIN ) ;
ssSetTNext ( rtS , rtMinusInf ) ; ssSetSolverNeedsReset ( rtS ) ;
ssSetNumNonsampledZCs ( rtS , 0 ) ; } ssSetChecksumVal ( rtS , 0 ,
1620099481U ) ; ssSetChecksumVal ( rtS , 1 , 1886550842U ) ; ssSetChecksumVal
( rtS , 2 , 1189330833U ) ; ssSetChecksumVal ( rtS , 3 , 1677001332U ) ; {
static const sysRanDType rtAlwaysEnabled = SUBSYS_RAN_BC_ENABLE ; static
RTWExtModeInfo rt_ExtModeInfo ; static const sysRanDType * systemRan [ 6 ] ;
gblRTWExtModeInfo = & rt_ExtModeInfo ; ssSetRTWExtModeInfo ( rtS , &
rt_ExtModeInfo ) ; rteiSetSubSystemActiveVectorAddresses ( & rt_ExtModeInfo ,
systemRan ) ; systemRan [ 0 ] = & rtAlwaysEnabled ; systemRan [ 1 ] = &
rtAlwaysEnabled ; systemRan [ 2 ] = & rtAlwaysEnabled ; systemRan [ 3 ] = &
rtAlwaysEnabled ; systemRan [ 4 ] = & rtAlwaysEnabled ; systemRan [ 5 ] = &
rtAlwaysEnabled ; rteiSetModelMappingInfoPtr ( ssGetRTWExtModeInfo ( rtS ) ,
& ssGetModelMappingInfo ( rtS ) ) ; rteiSetChecksumsPtr ( ssGetRTWExtModeInfo
( rtS ) , ssGetChecksums ( rtS ) ) ; rteiSetTPtr ( ssGetRTWExtModeInfo ( rtS
) , ssGetTPtr ( rtS ) ) ; } return rtS ; }
#if defined(_MSC_VER)
#pragma optimize( "", on )
#endif
const int_T gblParameterTuningTid = 1 ; void MdlOutputsParameterSampleTime (
int_T tid ) { UNUSED_PARAMETER ( tid ) ; }

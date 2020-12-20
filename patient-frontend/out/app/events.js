// Compiled by ClojureScript 1.10.773 {}
goog.provide('app.events');
goog.require('cljs.core');
goog.require('re_frame.core');
goog.require('clojure.walk');
goog.require('app.current_query_parameters');
goog.require('app.helpers');
goog.require('app.config');
goog.require('ajax.core');
goog.require('day8.re_frame.http_fx');
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"drop-db","drop-db",-1084825065),(function (db,_){
return cljs.core.PersistentArrayMap.EMPTY;
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"initialize","initialize",609952913),(function (_,___$1){
return new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"last-event","last-event",2067154394),null,new cljs.core.Keyword(null,"errors","errors",-908790718),null,new cljs.core.Keyword(null,"page","page",849072397),new cljs.core.Keyword(null,"about","about",1423892543),new cljs.core.Keyword(null,"patients-sorter","patients-sorter",-843801150),new cljs.core.Keyword(null,"id","id",-1388402092)], null);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"change-page","change-page",-1758403218),(function (db,p__3281){
var vec__3282 = p__3281;
var _ = cljs.core.nth.call(null,vec__3282,(0),null);
var page = cljs.core.nth.call(null,vec__3282,(1),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"page","page",849072397),page);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"set-current-patient-id","set-current-patient-id",-445580537),(function (db,p__3285){
var vec__3286 = p__3285;
var _ = cljs.core.nth.call(null,vec__3286,(0),null);
var id = cljs.core.nth.call(null,vec__3286,(1),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"current-patient-id","current-patient-id",809228000),id);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"last-event","last-event",2067154394),(function (db,p__3289){
var vec__3290 = p__3289;
var _ = cljs.core.nth.call(null,vec__3290,(0),null);
var event_name = cljs.core.nth.call(null,vec__3290,(1),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),event_name);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"add-id-query-parameter","add-id-query-parameter",1216145411),(function (db,p__3293){
var vec__3294 = p__3293;
var _ = cljs.core.nth.call(null,vec__3294,(0),null);
var id = cljs.core.nth.call(null,vec__3294,(1),null);
return cljs.core.assoc_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"patient-id","patient-id",-1070946504)], null),id);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"add-fullname-query-parameter","add-fullname-query-parameter",798204773),(function (db,p__3297){
var vec__3298 = p__3297;
var _ = cljs.core.nth.call(null,vec__3298,(0),null);
var fullname = cljs.core.nth.call(null,vec__3298,(1),null);
return cljs.core.assoc_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"full_name","full_name",1257415930)], null),fullname);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"add-birthdate-query-parameter","add-birthdate-query-parameter",1037566969),(function (db,p__3301){
var vec__3302 = p__3301;
var _ = cljs.core.nth.call(null,vec__3302,(0),null);
var birthdate = cljs.core.nth.call(null,vec__3302,(1),null);
return cljs.core.assoc_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"birthdate","birthdate",-534621599)], null),birthdate);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"add-insurance-query-parameter","add-insurance-query-parameter",-843797936),(function (db,p__3305){
var vec__3306 = p__3305;
var _ = cljs.core.nth.call(null,vec__3306,(0),null);
var insurance = cljs.core.nth.call(null,vec__3306,(1),null);
return cljs.core.assoc_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"insurance","insurance",1498778018)], null),insurance);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"add-address-query-parameter","add-address-query-parameter",-1190187320),(function (db,p__3309){
var vec__3310 = p__3309;
var _ = cljs.core.nth.call(null,vec__3310,(0),null);
var address = cljs.core.nth.call(null,vec__3310,(1),null);
return cljs.core.assoc_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"address","address",559499426)], null),address);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"select-gender","select-gender",1599627472),(function (db,p__3313){
var vec__3314 = p__3313;
var _ = cljs.core.nth.call(null,vec__3314,(0),null);
var gender = cljs.core.nth.call(null,vec__3314,(1),null);
return cljs.core.assoc_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"gender","gender",-733930727)], null),gender);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"add-all-query-parameters","add-all-query-parameters",-1962742609),(function (db,p__3317){
var vec__3318 = p__3317;
var _ = cljs.core.nth.call(null,vec__3318,(0),null);
var id = cljs.core.nth.call(null,vec__3318,(1),null);
var temp__5733__auto__ = app.helpers.patient_by_id.call(null,id,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009),db);
if(cljs.core.truth_(temp__5733__auto__)){
var patient = temp__5733__auto__;
return app.helpers.assoc_patient_params_to_form_query_params_in_state.call(null,db,patient);
} else {
var patient = app.helpers.patient_by_id.call(null,id,new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755),db);
return app.helpers.assoc_patient_params_to_form_query_params_in_state.call(null,db,patient);
}
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"add-one-patient-into-state","add-one-patient-into-state",747960049),(function (db,p__3321){
var vec__3322 = p__3321;
var _ = cljs.core.nth.call(null,vec__3322,(0),null);
var patient = cljs.core.nth.call(null,vec__3322,(1),null);

return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009),cljs.core.conj.call(null,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009).cljs$core$IFn$_invoke$arity$1(db),patient));
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"save-patients-into-state","save-patients-into-state",319128606),(function (db,p__3325){
var vec__3326 = p__3325;
var _ = cljs.core.nth.call(null,vec__3326,(0),null);
var patients = cljs.core.nth.call(null,vec__3326,(1),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009),clojure.walk.keywordize_keys.call(null,patients));
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"save-filtered-patients-into-state","save-filtered-patients-into-state",-201134964),(function (db,p__3329){
var vec__3330 = p__3329;
var _ = cljs.core.nth.call(null,vec__3330,(0),null);
var patients = cljs.core.nth.call(null,vec__3330,(1),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755),clojure.walk.keywordize_keys.call(null,patients),new cljs.core.Keyword(null,"last-event","last-event",2067154394),"Filtered patients");
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"put-errors-into-state","put-errors-into-state",-912527099),(function (db,p__3333){
var vec__3334 = p__3333;
var _ = cljs.core.nth.call(null,vec__3334,(0),null);
var errors = cljs.core.nth.call(null,vec__3334,(1),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"errors","errors",-908790718),errors);
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"delete-patient-from-state","delete-patient-from-state",5105919),(function (db,p__3337){
var vec__3338 = p__3337;
var _ = cljs.core.nth.call(null,vec__3338,(0),null);
var patient_id = cljs.core.nth.call(null,vec__3338,(1),null);
var remover = (function (keyword){
return cljs.core.remove.call(null,(function (p){
return cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"id","id",-1388402092).cljs$core$IFn$_invoke$arity$1(p),patient_id);
}),keyword.call(null,db));
});
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009),remover.call(null,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009)),new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755),remover.call(null,new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755)));
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"update-patient-into-state","update-patient-into-state",-130348093),(function (db,p__3341){
var vec__3342 = p__3341;
var _ = cljs.core.nth.call(null,vec__3342,(0),null);
var patient_id = cljs.core.nth.call(null,vec__3342,(1),null);
var query_parameters = cljs.core.nth.call(null,vec__3342,(2),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),"Patient updated");
}));
re_frame.core.reg_event_db.call(null,new cljs.core.Keyword(null,"set-patients-sorter","set-patients-sorter",235909245),(function (db,p__3345){
var vec__3346 = p__3345;
var _ = cljs.core.nth.call(null,vec__3346,(0),null);
var sorter = cljs.core.nth.call(null,vec__3346,(1),null);
return cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"patients-sorter","patients-sorter",-843801150),sorter);
}));
re_frame.core.reg_event_fx.call(null,new cljs.core.Keyword(null,"load-all-patients","load-all-patients",-66273835),(function (p__3349,_){
var map__3350 = p__3349;
var map__3350__$1 = (((((!((map__3350 == null))))?(((((map__3350.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__3350.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__3350):map__3350);
var db = cljs.core.get.call(null,map__3350__$1,new cljs.core.Keyword(null,"db","db",993250759));
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),"Loading all patients"),new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"method","method",55703592),new cljs.core.Keyword(null,"get","get",1683182755),new cljs.core.Keyword(null,"uri","uri",-774711847),"http://localhost:7500/patients",new cljs.core.Keyword(null,"response-format","response-format",1664465322),ajax.core.json_response_format.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"keywords","keywords",1526959054),true], null)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"save-patients-into-state","save-patients-into-state",319128606)], null),new cljs.core.Keyword(null,"on-failure","on-failure",842888245),null], null)], null);
}));
re_frame.core.reg_event_fx.call(null,new cljs.core.Keyword(null,"create-patient","create-patient",-540547639),(function (p__3352,p__3353){
var map__3354 = p__3352;
var map__3354__$1 = (((((!((map__3354 == null))))?(((((map__3354.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__3354.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__3354):map__3354);
var db = cljs.core.get.call(null,map__3354__$1,new cljs.core.Keyword(null,"db","db",993250759));
var vec__3355 = p__3353;
var _ = cljs.core.nth.call(null,vec__3355,(0),null);
var parameters = cljs.core.nth.call(null,vec__3355,(1),null);
var empty_parameters = cljs.core.nth.call(null,vec__3355,(2),null);
if(cljs.core.truth_(cljs.core.complement.call(null,cljs.core.empty_QMARK_).call(null,empty_parameters))){
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Please provide all needed inputs: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(empty_parameters)].join('')], null)], null);
} else {
return new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Saving patient: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(parameters)].join('')),new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"add-one-patient-into-state","add-one-patient-into-state",747960049),parameters], null),new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.PersistentArrayMap(null, 7, [new cljs.core.Keyword(null,"method","method",55703592),new cljs.core.Keyword(null,"post","post",269697687),new cljs.core.Keyword(null,"params","params",710516235),parameters,new cljs.core.Keyword(null,"format","format",-1306924766),ajax.core.json_request_format.call(null),new cljs.core.Keyword(null,"uri","uri",-774711847),"http://localhost:7500/patients",new cljs.core.Keyword(null,"response-format","response-format",1664465322),ajax.core.json_response_format.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"keywords","keywords",1526959054),true], null)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"save-patients-into-state","save-patients-into-state",319128606)], null),new cljs.core.Keyword(null,"on-failure","on-failure",842888245),null], null)], null);
}
}));
re_frame.core.reg_event_fx.call(null,new cljs.core.Keyword(null,"load-patients-with-query","load-patients-with-query",460714965),(function (p__3359,p__3360){
var map__3361 = p__3359;
var map__3361__$1 = (((((!((map__3361 == null))))?(((((map__3361.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__3361.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__3361):map__3361);
var db = cljs.core.get.call(null,map__3361__$1,new cljs.core.Keyword(null,"db","db",993250759));
var vec__3362 = p__3360;
var _ = cljs.core.nth.call(null,vec__3362,(0),null);
var query_parameters = cljs.core.nth.call(null,vec__3362,(1),null);
var query_parameters_to_use = clojure.walk.keywordize_keys.call(null,app.helpers.remove_nils_and_empty_strings.call(null,app.helpers.stringify_map_keywords.call(null,query_parameters)));
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Loading filtered patients",cljs.core.str.cljs$core$IFn$_invoke$arity$1(query_parameters_to_use)].join(''),new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755),null),new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.PersistentArrayMap(null, 7, [new cljs.core.Keyword(null,"method","method",55703592),new cljs.core.Keyword(null,"get","get",1683182755),new cljs.core.Keyword(null,"params","params",710516235),query_parameters_to_use,new cljs.core.Keyword(null,"format","format",-1306924766),ajax.core.json_request_format.call(null),new cljs.core.Keyword(null,"uri","uri",-774711847),"http://localhost:7500/patients/find",new cljs.core.Keyword(null,"response-format","response-format",1664465322),ajax.core.json_response_format.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"keywords","keywords",1526959054),true], null)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"save-filtered-patients-into-state","save-filtered-patients-into-state",-201134964)], null),new cljs.core.Keyword(null,"on-failure","on-failure",842888245),null], null)], null);
}));
re_frame.core.reg_event_fx.call(null,new cljs.core.Keyword(null,"update-patient","update-patient",-488020697),(function (p__3366,p__3367){
var map__3368 = p__3366;
var map__3368__$1 = (((((!((map__3368 == null))))?(((((map__3368.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__3368.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__3368):map__3368);
var db = cljs.core.get.call(null,map__3368__$1,new cljs.core.Keyword(null,"db","db",993250759));
var vec__3369 = p__3367;
var _ = cljs.core.nth.call(null,vec__3369,(0),null);
var empty_parameters = cljs.core.nth.call(null,vec__3369,(1),null);
var patient_id = cljs.core.nth.call(null,vec__3369,(2),null);
var parameters = cljs.core.nth.call(null,vec__3369,(3),null);
if(cljs.core.truth_(cljs.core.complement.call(null,cljs.core.empty_QMARK_).call(null,empty_parameters))){
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Some inputs were deleted. Can not update. ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(empty_parameters),"<- empty"].join('')], null)], null);
} else {
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Updating patient patient with parameters: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(parameters)].join('')),new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.PersistentArrayMap(null, 7, [new cljs.core.Keyword(null,"method","method",55703592),new cljs.core.Keyword(null,"post","post",269697687),new cljs.core.Keyword(null,"params","params",710516235),parameters,new cljs.core.Keyword(null,"format","format",-1306924766),ajax.core.json_request_format.call(null),new cljs.core.Keyword(null,"uri","uri",-774711847),["http://localhost:7500/patients","/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(patient_id),"/update"].join(''),new cljs.core.Keyword(null,"response-format","response-format",1664465322),ajax.core.json_response_format.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"keywords","keywords",1526959054),true], null)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"update-patient-into-state","update-patient-into-state",-130348093),parameters], null),new cljs.core.Keyword(null,"on-failure","on-failure",842888245),null], null)], null);
}
}));
re_frame.core.reg_event_fx.call(null,new cljs.core.Keyword(null,"delete-patient-with-id","delete-patient-with-id",-557706635),(function (p__3373,p__3374){
var map__3375 = p__3373;
var map__3375__$1 = (((((!((map__3375 == null))))?(((((map__3375.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__3375.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__3375):map__3375);
var db = cljs.core.get.call(null,map__3375__$1,new cljs.core.Keyword(null,"db","db",993250759));
var vec__3376 = p__3374;
var _ = cljs.core.nth.call(null,vec__3376,(0),null);
var patient_id = cljs.core.nth.call(null,vec__3376,(1),null);
return new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Deleting patient with id: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(patient_id)].join('')),new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.PersistentArrayMap(null, 6, [new cljs.core.Keyword(null,"method","method",55703592),new cljs.core.Keyword(null,"delete","delete",-1768633620),new cljs.core.Keyword(null,"uri","uri",-774711847),["http://localhost:7500/patients","/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(patient_id),"/delete"].join(''),new cljs.core.Keyword(null,"format","format",-1306924766),ajax.core.json_request_format.call(null),new cljs.core.Keyword(null,"response-format","response-format",1664465322),ajax.core.json_response_format.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"keywords","keywords",1526959054),true], null)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"delete-patient-from-state","delete-patient-from-state",5105919),patient_id], null),new cljs.core.Keyword(null,"on-failure","on-failure",842888245),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Failed to delete patient"," with id: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(patient_id)].join('')], null)], null)], null);
}));
re_frame.core.reg_event_fx.call(null,new cljs.core.Keyword(null,"master-patient-index","master-patient-index",-1313027027),(function (p__3380,p__3381){
var map__3382 = p__3380;
var map__3382__$1 = (((((!((map__3382 == null))))?(((((map__3382.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__3382.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__3382):map__3382);
var db = cljs.core.get.call(null,map__3382__$1,new cljs.core.Keyword(null,"db","db",993250759));
var vec__3383 = p__3381;
var _ = cljs.core.nth.call(null,vec__3383,(0),null);
var parameters = cljs.core.nth.call(null,vec__3383,(1),null);
var empty_parameters = cljs.core.nth.call(null,vec__3383,(2),null);
if(cljs.core.truth_(cljs.core.complement.call(null,cljs.core.empty_QMARK_).call(null,empty_parameters))){
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Please provide all needed inputs: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(empty_parameters)].join('')], null)], null);
} else {
return new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"db","db",993250759),cljs.core.assoc.call(null,db,new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Saving patient: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(parameters)].join('')),new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"add-one-patient-into-state","add-one-patient-into-state",747960049),parameters], null),new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),new cljs.core.PersistentArrayMap(null, 7, [new cljs.core.Keyword(null,"method","method",55703592),new cljs.core.Keyword(null,"post","post",269697687),new cljs.core.Keyword(null,"params","params",710516235),parameters,new cljs.core.Keyword(null,"format","format",-1306924766),ajax.core.json_request_format.call(null),new cljs.core.Keyword(null,"uri","uri",-774711847),"http://localhost:7500/patients/master",new cljs.core.Keyword(null,"response-format","response-format",1664465322),ajax.core.json_response_format.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"keywords","keywords",1526959054),true], null)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"save-patients-into-state","save-patients-into-state",319128606)], null),new cljs.core.Keyword(null,"on-failure","on-failure",842888245),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"last-event","last-event",2067154394),["Master patient validation failed.","Patient you are trying to save ","is likely to exist.",cljs.core.str.cljs$core$IFn$_invoke$arity$1(parameters)].join('')], null)], null)], null);
}
}));

//# sourceMappingURL=events.js.map

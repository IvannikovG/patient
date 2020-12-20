// Compiled by ClojureScript 1.10.773 {}
goog.provide('app.helpers');
goog.require('cljs.core');
goog.require('clojure.string');
goog.require('clojure.walk');
app.helpers.stringify_map_keywords = (function app$helpers$stringify_map_keywords(map){
return clojure.walk.stringify_keys.call(null,map);
});
app.helpers.empty_value_QMARK_ = (function app$helpers$empty_value_QMARK_(val){
if(typeof val === 'number'){
return false;
} else {
if(cljs.core.empty_QMARK_.call(null,val)){
return true;
} else {
if((val == null)){
return true;
} else {
if(cljs.core._EQ_.call(null,"unselected",val)){
return true;
} else {
return false;

}
}
}
}
});
app.helpers.remove_nils_and_empty_strings = (function app$helpers$remove_nils_and_empty_strings(record){
return cljs.core.reduce.call(null,(function (m,p__3240){
var vec__3241 = p__3240;
var k = cljs.core.nth.call(null,vec__3241,(0),null);
var v = cljs.core.nth.call(null,vec__3241,(1),null);
if((((v == null)) || (cljs.core.empty_QMARK_.call(null,v)) || (cljs.core._EQ_.call(null,"unselected",v)))){
return m;
} else {
return cljs.core.assoc.call(null,m,k,v);
}
}),cljs.core.PersistentArrayMap.EMPTY,record);
});
app.helpers.lower_cased_values = (function app$helpers$lower_cased_values(record){
return cljs.core.reduce.call(null,(function (m,p__3244){
var vec__3245 = p__3244;
var k = cljs.core.nth.call(null,vec__3245,(0),null);
var v = cljs.core.nth.call(null,vec__3245,(1),null);
if(typeof v === 'string'){
return cljs.core.assoc.call(null,m,k,clojure.string.lower_case.call(null,v));
} else {
return cljs.core.assoc.call(null,m,k,v);
}
}),cljs.core.PersistentArrayMap.EMPTY,record);
});
app.helpers.find_empty_keywords = (function app$helpers$find_empty_keywords(record){
return cljs.core.map.call(null,cljs.core.first,cljs.core.filter.call(null,(function (el){
return app.helpers.empty_value_QMARK_.call(null,cljs.core.second.call(null,el));
}),record));
});
app.helpers.patient_by_id = (function app$helpers$patient_by_id(id,keyword,db){
return cljs.core.first.call(null,cljs.core.filter.call(null,(function (p){
return cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"id","id",-1388402092).cljs$core$IFn$_invoke$arity$1(p),(id | (0)));
}),keyword.call(null,db)));
});
app.helpers.assoc_patient_params_to_form_query_params_in_state = (function app$helpers$assoc_patient_params_to_form_query_params_in_state(db,patient){
return cljs.core.assoc_in.call(null,cljs.core.assoc_in.call(null,cljs.core.assoc_in.call(null,cljs.core.assoc_in.call(null,cljs.core.assoc_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"full_name","full_name",1257415930)], null),new cljs.core.Keyword(null,"full_name","full_name",1257415930).cljs$core$IFn$_invoke$arity$1(patient)),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"gender","gender",-733930727)], null),new cljs.core.Keyword(null,"gender","gender",-733930727).cljs$core$IFn$_invoke$arity$1(patient)),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"insurance","insurance",1498778018)], null),new cljs.core.Keyword(null,"insurance","insurance",1498778018).cljs$core$IFn$_invoke$arity$1(patient)),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"address","address",559499426)], null),new cljs.core.Keyword(null,"address","address",559499426).cljs$core$IFn$_invoke$arity$1(patient)),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"birthdate","birthdate",-534621599)], null),new cljs.core.Keyword(null,"birthdate","birthdate",-534621599).cljs$core$IFn$_invoke$arity$1(patient));
});
app.helpers.sort_patients_by = (function app$helpers$sort_patients_by(patients,key_string){
var key = cljs.core.keyword.call(null,key_string);
var allowed_sorters = new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"birthdate","birthdate",-534621599),null,new cljs.core.Keyword(null,"id","id",-1388402092),null,new cljs.core.Keyword(null,"full_name","full_name",1257415930),null], null), null);
if((!(cljs.core.contains_QMARK_.call(null,allowed_sorters,key)))){
return patients;
} else {
if((key == null)){
return patients;
} else {
return cljs.core.sort_by.call(null,key,patients);

}
}
});

//# sourceMappingURL=helpers.js.map

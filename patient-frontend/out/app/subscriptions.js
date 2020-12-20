// Compiled by ClojureScript 1.10.773 {}
goog.provide('app.subscriptions');
goog.require('cljs.core');
goog.require('re_frame.core');
goog.require('app.helpers');
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"log-database","log-database",963662895),(function (db,_){
cljs.core.println.call(null,db);

return db;
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"current-patient-id","current-patient-id",809228000),(function (db,_){
return new cljs.core.Keyword(null,"current-patient-id","current-patient-id",809228000).cljs$core$IFn$_invoke$arity$1(db);
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"page","page",849072397),(function (db,_){
return new cljs.core.Keyword(null,"page","page",849072397).cljs$core$IFn$_invoke$arity$1(db);
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009),(function (db,_){
return app.helpers.sort_patients_by.call(null,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009).cljs$core$IFn$_invoke$arity$1(db),cljs.core.deref.call(null,re_frame.core.subscribe.call(null,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"patients-sorter","patients-sorter",-843801150)], null))));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755),(function (db,_){
return app.helpers.sort_patients_by.call(null,new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755).cljs$core$IFn$_invoke$arity$1(db),cljs.core.deref.call(null,re_frame.core.subscribe.call(null,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"patients-sorter","patients-sorter",-843801150)], null))));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"filtered-patients-not-searched?","filtered-patients-not-searched?",886247283),(function (db,_){
return (new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755).cljs$core$IFn$_invoke$arity$1(db) == null);
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"filtered-patients-not-found?","filtered-patients-not-found?",-90835747),(function (db,_){
return cljs.core.empty_QMARK_.call(null,new cljs.core.Keyword(null,"filtered-patients-list","filtered-patients-list",-1015755755).cljs$core$IFn$_invoke$arity$1(db));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"patients-exist?","patients-exist?",-1062720667),(function (db,_){
return (function (){var and__4115__auto__ = cljs.core.complement.call(null,cljs.core.nil_QMARK_);
if(cljs.core.truth_(and__4115__auto__)){
return cljs.core.complement.call(null,cljs.core.empty_QMARK_);
} else {
return and__4115__auto__;
}
})().call(null,new cljs.core.Keyword(null,"patients-list","patients-list",1915767009).cljs$core$IFn$_invoke$arity$1(db));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),(function (db,_){
return new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367).cljs$core$IFn$_invoke$arity$1(db);
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"patient-id","patient-id",-1070946504),(function (db,_){
return cljs.core.get_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"patient-id","patient-id",-1070946504)], null));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"full_name","full_name",1257415930),(function (db,_){
return cljs.core.get_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"full_name","full_name",1257415930)], null));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"insurance","insurance",1498778018),(function (db,_){
return cljs.core.get_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"insurance","insurance",1498778018)], null));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"address","address",559499426),(function (db,_){
return cljs.core.get_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"address","address",559499426)], null));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"gender","gender",-733930727),(function (db,_){
return cljs.core.get_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"gender","gender",-733930727)], null));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"birthdate","birthdate",-534621599),(function (db,_){
return cljs.core.get_in.call(null,db,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"query-parameters","query-parameters",-1096629367),new cljs.core.Keyword(null,"birthdate","birthdate",-534621599)], null));
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"filtered-patients","filtered-patients",-545674070),(function (db,_){
return new cljs.core.Keyword(null,"filtered-patients","filtered-patients",-545674070).cljs$core$IFn$_invoke$arity$1(db);
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"form-errors","form-errors",-255814982),(function (db,_){
return new cljs.core.Keyword(null,"errors","errors",-908790718).cljs$core$IFn$_invoke$arity$1(db);
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"last-event","last-event",2067154394),(function (db,_){
return new cljs.core.Keyword(null,"last-event","last-event",2067154394).cljs$core$IFn$_invoke$arity$1(db);
}));
re_frame.core.reg_sub.call(null,new cljs.core.Keyword(null,"patients-sorter","patients-sorter",-843801150),(function (db,_){
return new cljs.core.Keyword(null,"patients-sorter","patients-sorter",-843801150).cljs$core$IFn$_invoke$arity$1(db);
}));

//# sourceMappingURL=subscriptions.js.map

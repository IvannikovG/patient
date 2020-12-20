// Compiled by ClojureScript 1.10.773 {}
goog.provide('re_frame.fx');
goog.require('cljs.core');
goog.require('re_frame.router');
goog.require('re_frame.db');
goog.require('re_frame.interceptor');
goog.require('re_frame.interop');
goog.require('re_frame.events');
goog.require('re_frame.registrar');
goog.require('re_frame.loggers');
goog.require('re_frame.trace');
re_frame.fx.kind = new cljs.core.Keyword(null,"fx","fx",-1237829572);
if(cljs.core.truth_(re_frame.registrar.kinds.call(null,re_frame.fx.kind))){
} else {
throw (new Error("Assert failed: (re-frame.registrar/kinds kind)"));
}
re_frame.fx.reg_fx = (function re_frame$fx$reg_fx(id,handler){
return re_frame.registrar.register_handler.call(null,re_frame.fx.kind,id,handler);
});
/**
 * An interceptor whose `:after` actions the contents of `:effects`. As a result,
 *   this interceptor is Domino 3.
 * 
 *   This interceptor is silently added (by reg-event-db etc) to the front of
 *   interceptor chains for all events.
 * 
 *   For each key in `:effects` (a map), it calls the registered `effects handler`
 *   (see `reg-fx` for registration of effect handlers).
 * 
 *   So, if `:effects` was:
 *    {:dispatch  [:hello 42]
 *     :db        {...}
 *     :undo      "set flag"}
 * 
 *   it will call the registered effect handlers for each of the map's keys:
 *   `:dispatch`, `:undo` and `:db`. When calling each handler, provides the map
 *   value for that key - so in the example above the effect handler for :dispatch
 *   will be given one arg `[:hello 42]`.
 * 
 *   You cannot rely on the ordering in which effects are executed, other than that
 *   `:db` is guaranteed to be executed first.
 */
re_frame.fx.do_fx = re_frame.interceptor.__GT_interceptor.call(null,new cljs.core.Keyword(null,"id","id",-1388402092),new cljs.core.Keyword(null,"do-fx","do-fx",1194163050),new cljs.core.Keyword(null,"after","after",594996914),(function re_frame$fx$do_fx_after(context){
if(re_frame.trace.is_trace_enabled_QMARK_.call(null)){
var _STAR_current_trace_STAR__orig_val__10919 = re_frame.trace._STAR_current_trace_STAR_;
var _STAR_current_trace_STAR__temp_val__10920 = re_frame.trace.start_trace.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"op-type","op-type",-1636141668),new cljs.core.Keyword("event","do-fx","event/do-fx",1357330452)], null));
(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__temp_val__10920);

try{try{var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.call(null,effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5735__auto___10953 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5735__auto___10953)){
var new_db_10954 = temp__5735__auto___10953;
re_frame.registrar.get_handler.call(null,re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false).call(null,new_db_10954);
} else {
}

var seq__10921 = cljs.core.seq.call(null,effects_without_db);
var chunk__10922 = null;
var count__10923 = (0);
var i__10924 = (0);
while(true){
if((i__10924 < count__10923)){
var vec__10931 = cljs.core._nth.call(null,chunk__10922,i__10924);
var effect_key = cljs.core.nth.call(null,vec__10931,(0),null);
var effect_value = cljs.core.nth.call(null,vec__10931,(1),null);
var temp__5733__auto___10955 = re_frame.registrar.get_handler.call(null,re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5733__auto___10955)){
var effect_fn_10956 = temp__5733__auto___10955;
effect_fn_10956.call(null,effect_value);
} else {
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: no handler registered for effect:",effect_key,". Ignoring.");
}


var G__10957 = seq__10921;
var G__10958 = chunk__10922;
var G__10959 = count__10923;
var G__10960 = (i__10924 + (1));
seq__10921 = G__10957;
chunk__10922 = G__10958;
count__10923 = G__10959;
i__10924 = G__10960;
continue;
} else {
var temp__5735__auto__ = cljs.core.seq.call(null,seq__10921);
if(temp__5735__auto__){
var seq__10921__$1 = temp__5735__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__10921__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__10921__$1);
var G__10961 = cljs.core.chunk_rest.call(null,seq__10921__$1);
var G__10962 = c__4556__auto__;
var G__10963 = cljs.core.count.call(null,c__4556__auto__);
var G__10964 = (0);
seq__10921 = G__10961;
chunk__10922 = G__10962;
count__10923 = G__10963;
i__10924 = G__10964;
continue;
} else {
var vec__10934 = cljs.core.first.call(null,seq__10921__$1);
var effect_key = cljs.core.nth.call(null,vec__10934,(0),null);
var effect_value = cljs.core.nth.call(null,vec__10934,(1),null);
var temp__5733__auto___10965 = re_frame.registrar.get_handler.call(null,re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5733__auto___10965)){
var effect_fn_10966 = temp__5733__auto___10965;
effect_fn_10966.call(null,effect_value);
} else {
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: no handler registered for effect:",effect_key,". Ignoring.");
}


var G__10967 = cljs.core.next.call(null,seq__10921__$1);
var G__10968 = null;
var G__10969 = (0);
var G__10970 = (0);
seq__10921 = G__10967;
chunk__10922 = G__10968;
count__10923 = G__10969;
i__10924 = G__10970;
continue;
}
} else {
return null;
}
}
break;
}
}finally {if(re_frame.trace.is_trace_enabled_QMARK_.call(null)){
var end__10456__auto___10971 = re_frame.interop.now.call(null);
var duration__10457__auto___10972 = (end__10456__auto___10971 - new cljs.core.Keyword(null,"start","start",-355208981).cljs$core$IFn$_invoke$arity$1(re_frame.trace._STAR_current_trace_STAR_));
cljs.core.swap_BANG_.call(null,re_frame.trace.traces,cljs.core.conj,cljs.core.assoc.call(null,re_frame.trace._STAR_current_trace_STAR_,new cljs.core.Keyword(null,"duration","duration",1444101068),duration__10457__auto___10972,new cljs.core.Keyword(null,"end","end",-268185958),re_frame.interop.now.call(null)));

re_frame.trace.run_tracing_callbacks_BANG_.call(null,end__10456__auto___10971);
} else {
}
}}finally {(re_frame.trace._STAR_current_trace_STAR_ = _STAR_current_trace_STAR__orig_val__10919);
}} else {
var effects = new cljs.core.Keyword(null,"effects","effects",-282369292).cljs$core$IFn$_invoke$arity$1(context);
var effects_without_db = cljs.core.dissoc.call(null,effects,new cljs.core.Keyword(null,"db","db",993250759));
var temp__5735__auto___10973 = new cljs.core.Keyword(null,"db","db",993250759).cljs$core$IFn$_invoke$arity$1(effects);
if(cljs.core.truth_(temp__5735__auto___10973)){
var new_db_10974 = temp__5735__auto___10973;
re_frame.registrar.get_handler.call(null,re_frame.fx.kind,new cljs.core.Keyword(null,"db","db",993250759),false).call(null,new_db_10974);
} else {
}

var seq__10937 = cljs.core.seq.call(null,effects_without_db);
var chunk__10938 = null;
var count__10939 = (0);
var i__10940 = (0);
while(true){
if((i__10940 < count__10939)){
var vec__10947 = cljs.core._nth.call(null,chunk__10938,i__10940);
var effect_key = cljs.core.nth.call(null,vec__10947,(0),null);
var effect_value = cljs.core.nth.call(null,vec__10947,(1),null);
var temp__5733__auto___10975 = re_frame.registrar.get_handler.call(null,re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5733__auto___10975)){
var effect_fn_10976 = temp__5733__auto___10975;
effect_fn_10976.call(null,effect_value);
} else {
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: no handler registered for effect:",effect_key,". Ignoring.");
}


var G__10977 = seq__10937;
var G__10978 = chunk__10938;
var G__10979 = count__10939;
var G__10980 = (i__10940 + (1));
seq__10937 = G__10977;
chunk__10938 = G__10978;
count__10939 = G__10979;
i__10940 = G__10980;
continue;
} else {
var temp__5735__auto__ = cljs.core.seq.call(null,seq__10937);
if(temp__5735__auto__){
var seq__10937__$1 = temp__5735__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__10937__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__10937__$1);
var G__10981 = cljs.core.chunk_rest.call(null,seq__10937__$1);
var G__10982 = c__4556__auto__;
var G__10983 = cljs.core.count.call(null,c__4556__auto__);
var G__10984 = (0);
seq__10937 = G__10981;
chunk__10938 = G__10982;
count__10939 = G__10983;
i__10940 = G__10984;
continue;
} else {
var vec__10950 = cljs.core.first.call(null,seq__10937__$1);
var effect_key = cljs.core.nth.call(null,vec__10950,(0),null);
var effect_value = cljs.core.nth.call(null,vec__10950,(1),null);
var temp__5733__auto___10985 = re_frame.registrar.get_handler.call(null,re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5733__auto___10985)){
var effect_fn_10986 = temp__5733__auto___10985;
effect_fn_10986.call(null,effect_value);
} else {
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: no handler registered for effect:",effect_key,". Ignoring.");
}


var G__10987 = cljs.core.next.call(null,seq__10937__$1);
var G__10988 = null;
var G__10989 = (0);
var G__10990 = (0);
seq__10937 = G__10987;
chunk__10938 = G__10988;
count__10939 = G__10989;
i__10940 = G__10990;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.dispatch_later = (function re_frame$fx$dispatch_later(p__10991){
var map__10992 = p__10991;
var map__10992__$1 = (((((!((map__10992 == null))))?(((((map__10992.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__10992.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__10992):map__10992);
var effect = map__10992__$1;
var ms = cljs.core.get.call(null,map__10992__$1,new cljs.core.Keyword(null,"ms","ms",-1152709733));
var dispatch = cljs.core.get.call(null,map__10992__$1,new cljs.core.Keyword(null,"dispatch","dispatch",1319337009));
if(((cljs.core.empty_QMARK_.call(null,dispatch)) || ((!(typeof ms === 'number'))))){
return re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"error","error",-978969032),"re-frame: ignoring bad :dispatch-later value:",effect);
} else {
return re_frame.interop.set_timeout_BANG_.call(null,(function (){
return re_frame.router.dispatch.call(null,dispatch);
}),ms);
}
});
re_frame.fx.reg_fx.call(null,new cljs.core.Keyword(null,"dispatch-later","dispatch-later",291951390),(function (value){
if(cljs.core.map_QMARK_.call(null,value)){
return re_frame.fx.dispatch_later.call(null,value);
} else {
var seq__10994 = cljs.core.seq.call(null,cljs.core.remove.call(null,cljs.core.nil_QMARK_,value));
var chunk__10995 = null;
var count__10996 = (0);
var i__10997 = (0);
while(true){
if((i__10997 < count__10996)){
var effect = cljs.core._nth.call(null,chunk__10995,i__10997);
re_frame.fx.dispatch_later.call(null,effect);


var G__10998 = seq__10994;
var G__10999 = chunk__10995;
var G__11000 = count__10996;
var G__11001 = (i__10997 + (1));
seq__10994 = G__10998;
chunk__10995 = G__10999;
count__10996 = G__11000;
i__10997 = G__11001;
continue;
} else {
var temp__5735__auto__ = cljs.core.seq.call(null,seq__10994);
if(temp__5735__auto__){
var seq__10994__$1 = temp__5735__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__10994__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__10994__$1);
var G__11002 = cljs.core.chunk_rest.call(null,seq__10994__$1);
var G__11003 = c__4556__auto__;
var G__11004 = cljs.core.count.call(null,c__4556__auto__);
var G__11005 = (0);
seq__10994 = G__11002;
chunk__10995 = G__11003;
count__10996 = G__11004;
i__10997 = G__11005;
continue;
} else {
var effect = cljs.core.first.call(null,seq__10994__$1);
re_frame.fx.dispatch_later.call(null,effect);


var G__11006 = cljs.core.next.call(null,seq__10994__$1);
var G__11007 = null;
var G__11008 = (0);
var G__11009 = (0);
seq__10994 = G__11006;
chunk__10995 = G__11007;
count__10996 = G__11008;
i__10997 = G__11009;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx.call(null,new cljs.core.Keyword(null,"fx","fx",-1237829572),(function (seq_of_effects){
if((!(cljs.core.sequential_QMARK_.call(null,seq_of_effects)))){
return re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"error","error",-978969032),"re-frame: \":fx\" effect expects a seq, but was given ",cljs.core.type.call(null,seq_of_effects));
} else {
var seq__11010 = cljs.core.seq.call(null,cljs.core.remove.call(null,cljs.core.nil_QMARK_,seq_of_effects));
var chunk__11011 = null;
var count__11012 = (0);
var i__11013 = (0);
while(true){
if((i__11013 < count__11012)){
var vec__11020 = cljs.core._nth.call(null,chunk__11011,i__11013);
var effect_key = cljs.core.nth.call(null,vec__11020,(0),null);
var effect_value = cljs.core.nth.call(null,vec__11020,(1),null);
if(cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: \":fx\" effect should not contain a :db effect");
} else {
}

var temp__5733__auto___11026 = re_frame.registrar.get_handler.call(null,re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5733__auto___11026)){
var effect_fn_11027 = temp__5733__auto___11026;
effect_fn_11027.call(null,effect_value);
} else {
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring.");
}


var G__11028 = seq__11010;
var G__11029 = chunk__11011;
var G__11030 = count__11012;
var G__11031 = (i__11013 + (1));
seq__11010 = G__11028;
chunk__11011 = G__11029;
count__11012 = G__11030;
i__11013 = G__11031;
continue;
} else {
var temp__5735__auto__ = cljs.core.seq.call(null,seq__11010);
if(temp__5735__auto__){
var seq__11010__$1 = temp__5735__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__11010__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__11010__$1);
var G__11032 = cljs.core.chunk_rest.call(null,seq__11010__$1);
var G__11033 = c__4556__auto__;
var G__11034 = cljs.core.count.call(null,c__4556__auto__);
var G__11035 = (0);
seq__11010 = G__11032;
chunk__11011 = G__11033;
count__11012 = G__11034;
i__11013 = G__11035;
continue;
} else {
var vec__11023 = cljs.core.first.call(null,seq__11010__$1);
var effect_key = cljs.core.nth.call(null,vec__11023,(0),null);
var effect_value = cljs.core.nth.call(null,vec__11023,(1),null);
if(cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"db","db",993250759),effect_key)){
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: \":fx\" effect should not contain a :db effect");
} else {
}

var temp__5733__auto___11036 = re_frame.registrar.get_handler.call(null,re_frame.fx.kind,effect_key,false);
if(cljs.core.truth_(temp__5733__auto___11036)){
var effect_fn_11037 = temp__5733__auto___11036;
effect_fn_11037.call(null,effect_value);
} else {
re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"warn","warn",-436710552),"re-frame: in \":fx\" effect found ",effect_key," which has no associated handler. Ignoring.");
}


var G__11038 = cljs.core.next.call(null,seq__11010__$1);
var G__11039 = null;
var G__11040 = (0);
var G__11041 = (0);
seq__11010 = G__11038;
chunk__11011 = G__11039;
count__11012 = G__11040;
i__11013 = G__11041;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx.call(null,new cljs.core.Keyword(null,"dispatch","dispatch",1319337009),(function (value){
if((!(cljs.core.vector_QMARK_.call(null,value)))){
return re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"error","error",-978969032),"re-frame: ignoring bad :dispatch value. Expected a vector, but got:",value);
} else {
return re_frame.router.dispatch.call(null,value);
}
}));
re_frame.fx.reg_fx.call(null,new cljs.core.Keyword(null,"dispatch-n","dispatch-n",-504469236),(function (value){
if((!(cljs.core.sequential_QMARK_.call(null,value)))){
return re_frame.loggers.console.call(null,new cljs.core.Keyword(null,"error","error",-978969032),"re-frame: ignoring bad :dispatch-n value. Expected a collection, but got:",value);
} else {
var seq__11042 = cljs.core.seq.call(null,cljs.core.remove.call(null,cljs.core.nil_QMARK_,value));
var chunk__11043 = null;
var count__11044 = (0);
var i__11045 = (0);
while(true){
if((i__11045 < count__11044)){
var event = cljs.core._nth.call(null,chunk__11043,i__11045);
re_frame.router.dispatch.call(null,event);


var G__11046 = seq__11042;
var G__11047 = chunk__11043;
var G__11048 = count__11044;
var G__11049 = (i__11045 + (1));
seq__11042 = G__11046;
chunk__11043 = G__11047;
count__11044 = G__11048;
i__11045 = G__11049;
continue;
} else {
var temp__5735__auto__ = cljs.core.seq.call(null,seq__11042);
if(temp__5735__auto__){
var seq__11042__$1 = temp__5735__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__11042__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__11042__$1);
var G__11050 = cljs.core.chunk_rest.call(null,seq__11042__$1);
var G__11051 = c__4556__auto__;
var G__11052 = cljs.core.count.call(null,c__4556__auto__);
var G__11053 = (0);
seq__11042 = G__11050;
chunk__11043 = G__11051;
count__11044 = G__11052;
i__11045 = G__11053;
continue;
} else {
var event = cljs.core.first.call(null,seq__11042__$1);
re_frame.router.dispatch.call(null,event);


var G__11054 = cljs.core.next.call(null,seq__11042__$1);
var G__11055 = null;
var G__11056 = (0);
var G__11057 = (0);
seq__11042 = G__11054;
chunk__11043 = G__11055;
count__11044 = G__11056;
i__11045 = G__11057;
continue;
}
} else {
return null;
}
}
break;
}
}
}));
re_frame.fx.reg_fx.call(null,new cljs.core.Keyword(null,"deregister-event-handler","deregister-event-handler",-1096518994),(function (value){
var clear_event = cljs.core.partial.call(null,re_frame.registrar.clear_handlers,re_frame.events.kind);
if(cljs.core.sequential_QMARK_.call(null,value)){
var seq__11058 = cljs.core.seq.call(null,value);
var chunk__11059 = null;
var count__11060 = (0);
var i__11061 = (0);
while(true){
if((i__11061 < count__11060)){
var event = cljs.core._nth.call(null,chunk__11059,i__11061);
clear_event.call(null,event);


var G__11062 = seq__11058;
var G__11063 = chunk__11059;
var G__11064 = count__11060;
var G__11065 = (i__11061 + (1));
seq__11058 = G__11062;
chunk__11059 = G__11063;
count__11060 = G__11064;
i__11061 = G__11065;
continue;
} else {
var temp__5735__auto__ = cljs.core.seq.call(null,seq__11058);
if(temp__5735__auto__){
var seq__11058__$1 = temp__5735__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__11058__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__11058__$1);
var G__11066 = cljs.core.chunk_rest.call(null,seq__11058__$1);
var G__11067 = c__4556__auto__;
var G__11068 = cljs.core.count.call(null,c__4556__auto__);
var G__11069 = (0);
seq__11058 = G__11066;
chunk__11059 = G__11067;
count__11060 = G__11068;
i__11061 = G__11069;
continue;
} else {
var event = cljs.core.first.call(null,seq__11058__$1);
clear_event.call(null,event);


var G__11070 = cljs.core.next.call(null,seq__11058__$1);
var G__11071 = null;
var G__11072 = (0);
var G__11073 = (0);
seq__11058 = G__11070;
chunk__11059 = G__11071;
count__11060 = G__11072;
i__11061 = G__11073;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return clear_event.call(null,value);
}
}));
re_frame.fx.reg_fx.call(null,new cljs.core.Keyword(null,"db","db",993250759),(function (value){
if((!((cljs.core.deref.call(null,re_frame.db.app_db) === value)))){
return cljs.core.reset_BANG_.call(null,re_frame.db.app_db,value);
} else {
return null;
}
}));

//# sourceMappingURL=fx.js.map

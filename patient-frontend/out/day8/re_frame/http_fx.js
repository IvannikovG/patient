// Compiled by ClojureScript 1.10.773 {}
goog.provide('day8.re_frame.http_fx');
goog.require('cljs.core');
goog.require('goog.net.ErrorCode');
goog.require('re_frame.core');
goog.require('ajax.core');
goog.require('goog.net.XhrIo');
/**
 * ajax-request only provides a single handler for success and errors
 */
day8.re_frame.http_fx.ajax_xhrio_handler = (function day8$re_frame$http_fx$ajax_xhrio_handler(on_success,on_failure,xhrio,p__3252){
var vec__3253 = p__3252;
var success_QMARK_ = cljs.core.nth.call(null,vec__3253,(0),null);
var response = cljs.core.nth.call(null,vec__3253,(1),null);
if(cljs.core.truth_(success_QMARK_)){
return on_success.call(null,response);
} else {
var details = cljs.core.merge.call(null,new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"uri","uri",-774711847),xhrio.getLastUri(),new cljs.core.Keyword(null,"last-method","last-method",-563909920),xhrio.lastMethod_,new cljs.core.Keyword(null,"last-error","last-error",1848699973),xhrio.getLastError(),new cljs.core.Keyword(null,"last-error-code","last-error-code",276598110),xhrio.getLastErrorCode(),new cljs.core.Keyword(null,"debug-message","debug-message",-502855302),goog.net.ErrorCode.getDebugMessage(xhrio.getLastErrorCode())], null),response);
return on_failure.call(null,details);
}
});
day8.re_frame.http_fx.request__GT_xhrio_options = (function day8$re_frame$http_fx$request__GT_xhrio_options(p__3258){
var map__3259 = p__3258;
var map__3259__$1 = (((((!((map__3259 == null))))?(((((map__3259.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__3259.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__3259):map__3259);
var request = map__3259__$1;
var on_success = cljs.core.get.call(null,map__3259__$1,new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"http-no-on-success","http-no-on-success",-1593227158)], null));
var on_failure = cljs.core.get.call(null,map__3259__$1,new cljs.core.Keyword(null,"on-failure","on-failure",842888245),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"http-no-on-failure","http-no-on-failure",962976084)], null));
var api = (new goog.net.XhrIo());
return cljs.core.dissoc.call(null,cljs.core.assoc.call(null,request,new cljs.core.Keyword(null,"api","api",-899839580),api,new cljs.core.Keyword(null,"handler","handler",-195596612),cljs.core.partial.call(null,day8.re_frame.http_fx.ajax_xhrio_handler,(function (p1__3256_SHARP_){
return re_frame.core.dispatch.call(null,cljs.core.conj.call(null,on_success,p1__3256_SHARP_));
}),(function (p1__3257_SHARP_){
return re_frame.core.dispatch.call(null,cljs.core.conj.call(null,on_failure,p1__3257_SHARP_));
}),api)),new cljs.core.Keyword(null,"on-success","on-success",1786904109),new cljs.core.Keyword(null,"on-failure","on-failure",842888245),new cljs.core.Keyword(null,"on-request","on-request",972531605));
});
day8.re_frame.http_fx.dispatch_on_request = (function day8$re_frame$http_fx$dispatch_on_request(request,xhrio){
var temp__5733__auto__ = new cljs.core.Keyword(null,"on-request","on-request",972531605).cljs$core$IFn$_invoke$arity$1(request);
if(cljs.core.truth_(temp__5733__auto__)){
var on_request = temp__5733__auto__;
return re_frame.core.dispatch.call(null,cljs.core.conj.call(null,on_request,xhrio));
} else {
return null;
}
});
day8.re_frame.http_fx.http_effect = (function day8$re_frame$http_fx$http_effect(request){
var seq_request_maps = ((cljs.core.sequential_QMARK_.call(null,request))?request:new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [request], null));
var seq__3261 = cljs.core.seq.call(null,seq_request_maps);
var chunk__3262 = null;
var count__3263 = (0);
var i__3264 = (0);
while(true){
if((i__3264 < count__3263)){
var request__$1 = cljs.core._nth.call(null,chunk__3262,i__3264);
var xhrio_3265 = ajax.core.ajax_request.call(null,day8.re_frame.http_fx.request__GT_xhrio_options.call(null,request__$1));
day8.re_frame.http_fx.dispatch_on_request.call(null,request__$1,xhrio_3265);


var G__3266 = seq__3261;
var G__3267 = chunk__3262;
var G__3268 = count__3263;
var G__3269 = (i__3264 + (1));
seq__3261 = G__3266;
chunk__3262 = G__3267;
count__3263 = G__3268;
i__3264 = G__3269;
continue;
} else {
var temp__5735__auto__ = cljs.core.seq.call(null,seq__3261);
if(temp__5735__auto__){
var seq__3261__$1 = temp__5735__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__3261__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__3261__$1);
var G__3270 = cljs.core.chunk_rest.call(null,seq__3261__$1);
var G__3271 = c__4556__auto__;
var G__3272 = cljs.core.count.call(null,c__4556__auto__);
var G__3273 = (0);
seq__3261 = G__3270;
chunk__3262 = G__3271;
count__3263 = G__3272;
i__3264 = G__3273;
continue;
} else {
var request__$1 = cljs.core.first.call(null,seq__3261__$1);
var xhrio_3274 = ajax.core.ajax_request.call(null,day8.re_frame.http_fx.request__GT_xhrio_options.call(null,request__$1));
day8.re_frame.http_fx.dispatch_on_request.call(null,request__$1,xhrio_3274);


var G__3275 = cljs.core.next.call(null,seq__3261__$1);
var G__3276 = null;
var G__3277 = (0);
var G__3278 = (0);
seq__3261 = G__3275;
chunk__3262 = G__3276;
count__3263 = G__3277;
i__3264 = G__3278;
continue;
}
} else {
return null;
}
}
break;
}
});
re_frame.core.reg_fx.call(null,new cljs.core.Keyword(null,"http-xhrio","http-xhrio",1846166714),day8.re_frame.http_fx.http_effect);

//# sourceMappingURL=http_fx.js.map

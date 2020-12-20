// Compiled by ClojureScript 1.10.773 {}
goog.provide('reagent.debug');
goog.require('cljs.core');
reagent.debug.has_console = (typeof console !== 'undefined');
reagent.debug.tracking = false;
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.warnings !== 'undefined')){
} else {
reagent.debug.warnings = cljs.core.atom.call(null,null);
}
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.track_console !== 'undefined')){
} else {
reagent.debug.track_console = (function (){var o = ({});
(o.warn = (function() { 
var G__9340__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"warn","warn",-436710552)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__9340 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__9341__i = 0, G__9341__a = new Array(arguments.length -  0);
while (G__9341__i < G__9341__a.length) {G__9341__a[G__9341__i] = arguments[G__9341__i + 0]; ++G__9341__i;}
  args = new cljs.core.IndexedSeq(G__9341__a,0,null);
} 
return G__9340__delegate.call(this,args);};
G__9340.cljs$lang$maxFixedArity = 0;
G__9340.cljs$lang$applyTo = (function (arglist__9342){
var args = cljs.core.seq(arglist__9342);
return G__9340__delegate(args);
});
G__9340.cljs$core$IFn$_invoke$arity$variadic = G__9340__delegate;
return G__9340;
})()
);

(o.error = (function() { 
var G__9343__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"error","error",-978969032)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__9343 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__9344__i = 0, G__9344__a = new Array(arguments.length -  0);
while (G__9344__i < G__9344__a.length) {G__9344__a[G__9344__i] = arguments[G__9344__i + 0]; ++G__9344__i;}
  args = new cljs.core.IndexedSeq(G__9344__a,0,null);
} 
return G__9343__delegate.call(this,args);};
G__9343.cljs$lang$maxFixedArity = 0;
G__9343.cljs$lang$applyTo = (function (arglist__9345){
var args = cljs.core.seq(arglist__9345);
return G__9343__delegate(args);
});
G__9343.cljs$core$IFn$_invoke$arity$variadic = G__9343__delegate;
return G__9343;
})()
);

return o;
})();
}
reagent.debug.track_warnings = (function reagent$debug$track_warnings(f){
(reagent.debug.tracking = true);

cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

f.call(null);

var warns = cljs.core.deref.call(null,reagent.debug.warnings);
cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

(reagent.debug.tracking = false);

return warns;
});

//# sourceMappingURL=debug.js.map

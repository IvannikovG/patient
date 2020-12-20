// Compiled by ClojureScript 1.10.773 {}
goog.provide('app.test_runner');
goog.require('cljs.core');
goog.require('cljs.test');
goog.require('figwheel.main.async_result');
goog.require('app.new_integration_test');
cljs.core._add_method.call(null,cljs.test.report,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword("cljs.test","default","cljs.test/default",-1581405322),new cljs.core.Keyword(null,"end-run-tests","end-run-tests",267300563)], null),(function (m){
if(cljs.test.successful_QMARK_.call(null,m)){
return figwheel.main.async_result.send.call(null,"Tests passed!");
} else {
return cljs.core.println.call(null,"FAIL");
}
}));
app.test_runner._main = (function app$test_runner$_main(var_args){
var args__4742__auto__ = [];
var len__4736__auto___2573 = arguments.length;
var i__4737__auto___2574 = (0);
while(true){
if((i__4737__auto___2574 < len__4736__auto___2573)){
args__4742__auto__.push((arguments[i__4737__auto___2574]));

var G__2575 = (i__4737__auto___2574 + (1));
i__4737__auto___2574 = G__2575;
continue;
} else {
}
break;
}

var argseq__4743__auto__ = ((((0) < args__4742__auto__.length))?(new cljs.core.IndexedSeq(args__4742__auto__.slice((0)),(0),null)):null);
return app.test_runner._main.cljs$core$IFn$_invoke$arity$variadic(argseq__4743__auto__);
});

(app.test_runner._main.cljs$core$IFn$_invoke$arity$variadic = (function (args){
return cljs.test.run_block.call(null,(function (){var env2571 = cljs.test.empty_env.call(null);
var summary2572 = cljs.core.volatile_BANG_.call(null,new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"summary","summary",380847952),new cljs.core.Keyword(null,"fail","fail",1706214930),(0),new cljs.core.Keyword(null,"error","error",-978969032),(0),new cljs.core.Keyword(null,"pass","pass",1574159993),(0),new cljs.core.Keyword(null,"test","test",577538877),(0)], null));
return cljs.core.concat.call(null,cljs.core.concat.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
cljs.test.set_env_BANG_.call(null,env2571);

cljs.test.do_report.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"ns","ns",441598760),new cljs.core.Symbol(null,"app.new-integration-test","app.new-integration-test",-849814246,null),new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"begin-test-ns","begin-test-ns",-1701237033)], null));

return cljs.test.block.call(null,(function (){var env__914__auto__ = cljs.test.get_current_env.call(null);
return cljs.core.concat.call(null,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
if((env__914__auto__ == null)){
cljs.test.set_env_BANG_.call(null,cljs.test.empty_env.call(null));
} else {
}


return null;
})], null),cljs.test.test_vars_block.call(null,new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Var(function(){return app.new_integration_test.initialize;},new cljs.core.Symbol("app.new-integration-test","initialize","app.new-integration-test/initialize",-94145101,null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",441598760),new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"file","file",-1269645878),new cljs.core.Keyword(null,"end-column","end-column",1425389514),new cljs.core.Keyword(null,"column","column",2078222095),new cljs.core.Keyword(null,"line","line",212345235),new cljs.core.Keyword(null,"end-line","end-line",1837326455),new cljs.core.Keyword(null,"arglists","arglists",1661989754),new cljs.core.Keyword(null,"doc","doc",1913296891),new cljs.core.Keyword(null,"test","test",577538877)],[new cljs.core.Symbol(null,"app.new-integration-test","app.new-integration-test",-849814246,null),new cljs.core.Symbol(null,"initialize","initialize",-2044482856,null),"/Users/georgii/Documents/clojure/patient/patient-frontend/test/app/new_integration_test.cljs",(20),(1),(31),(31),cljs.core.List.EMPTY,null,(cljs.core.truth_(app.new_integration_test.initialize)?app.new_integration_test.initialize.cljs$lang$test:null)])),new cljs.core.Var(function(){return app.new_integration_test.pure_events;},new cljs.core.Symbol("app.new-integration-test","pure-events","app.new-integration-test/pure-events",801161955,null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",441598760),new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"file","file",-1269645878),new cljs.core.Keyword(null,"end-column","end-column",1425389514),new cljs.core.Keyword(null,"column","column",2078222095),new cljs.core.Keyword(null,"line","line",212345235),new cljs.core.Keyword(null,"end-line","end-line",1837326455),new cljs.core.Keyword(null,"arglists","arglists",1661989754),new cljs.core.Keyword(null,"doc","doc",1913296891),new cljs.core.Keyword(null,"test","test",577538877)],[new cljs.core.Symbol(null,"app.new-integration-test","app.new-integration-test",-849814246,null),new cljs.core.Symbol(null,"pure-events","pure-events",1912605576,null),"/Users/georgii/Documents/clojure/patient/patient-frontend/test/app/new_integration_test.cljs",(21),(1),(44),(44),cljs.core.List.EMPTY,null,(cljs.core.truth_(app.new_integration_test.pure_events)?app.new_integration_test.pure_events.cljs$lang$test:null)])),new cljs.core.Var(function(){return app.new_integration_test.patient_state;},new cljs.core.Symbol("app.new-integration-test","patient-state","app.new-integration-test/patient-state",1841217937,null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",441598760),new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"file","file",-1269645878),new cljs.core.Keyword(null,"end-column","end-column",1425389514),new cljs.core.Keyword(null,"column","column",2078222095),new cljs.core.Keyword(null,"line","line",212345235),new cljs.core.Keyword(null,"end-line","end-line",1837326455),new cljs.core.Keyword(null,"arglists","arglists",1661989754),new cljs.core.Keyword(null,"doc","doc",1913296891),new cljs.core.Keyword(null,"test","test",577538877)],[new cljs.core.Symbol(null,"app.new-integration-test","app.new-integration-test",-849814246,null),new cljs.core.Symbol(null,"patient-state","patient-state",293476686,null),"/Users/georgii/Documents/clojure/patient/patient-frontend/test/app/new_integration_test.cljs",(23),(1),(78),(78),cljs.core.List.EMPTY,null,(cljs.core.truth_(app.new_integration_test.patient_state)?app.new_integration_test.patient_state.cljs$lang$test:null)])),new cljs.core.Var(function(){return app.new_integration_test.requests_test_create_update_find;},new cljs.core.Symbol("app.new-integration-test","requests-test-create-update-find","app.new-integration-test/requests-test-create-update-find",-1275044568,null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",441598760),new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"file","file",-1269645878),new cljs.core.Keyword(null,"end-column","end-column",1425389514),new cljs.core.Keyword(null,"column","column",2078222095),new cljs.core.Keyword(null,"line","line",212345235),new cljs.core.Keyword(null,"end-line","end-line",1837326455),new cljs.core.Keyword(null,"arglists","arglists",1661989754),new cljs.core.Keyword(null,"doc","doc",1913296891),new cljs.core.Keyword(null,"test","test",577538877)],[new cljs.core.Symbol(null,"app.new-integration-test","app.new-integration-test",-849814246,null),new cljs.core.Symbol(null,"requests-test-create-update-find","requests-test-create-update-find",-935291891,null),"/Users/georgii/Documents/clojure/patient/patient-frontend/test/app/new_integration_test.cljs",(42),(1),(136),(136),cljs.core.List.EMPTY,null,(cljs.core.truth_(app.new_integration_test.requests_test_create_update_find)?app.new_integration_test.requests_test_create_update_find.cljs$lang$test:null)])),new cljs.core.Var(function(){return app.new_integration_test.requests_test_delete;},new cljs.core.Symbol("app.new-integration-test","requests-test-delete","app.new-integration-test/requests-test-delete",1211481525,null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",441598760),new cljs.core.Keyword(null,"name","name",1843675177),new cljs.core.Keyword(null,"file","file",-1269645878),new cljs.core.Keyword(null,"end-column","end-column",1425389514),new cljs.core.Keyword(null,"column","column",2078222095),new cljs.core.Keyword(null,"line","line",212345235),new cljs.core.Keyword(null,"end-line","end-line",1837326455),new cljs.core.Keyword(null,"arglists","arglists",1661989754),new cljs.core.Keyword(null,"doc","doc",1913296891),new cljs.core.Keyword(null,"test","test",577538877)],[new cljs.core.Symbol(null,"app.new-integration-test","app.new-integration-test",-849814246,null),new cljs.core.Symbol(null,"requests-test-delete","requests-test-delete",-185174690,null),"/Users/georgii/Documents/clojure/patient/patient-frontend/test/app/new_integration_test.cljs",(30),(1),(162),(162),cljs.core.List.EMPTY,null,(cljs.core.truth_(app.new_integration_test.requests_test_delete)?app.new_integration_test.requests_test_delete.cljs$lang$test:null)]))], null)),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
if((env__914__auto__ == null)){
return cljs.test.clear_env_BANG_.call(null);
} else {
return null;
}
})], null));
})());
}),(function (){
return cljs.test.do_report.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"ns","ns",441598760),new cljs.core.Symbol(null,"app.new-integration-test","app.new-integration-test",-849814246,null),new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"end-test-ns","end-test-ns",1620675645)], null));
})], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
return cljs.core._vreset_BANG_.call(null,summary2572,cljs.core.partial.call(null,cljs.core.merge_with,cljs.core._PLUS_).call(null,cljs.core._deref.call(null,summary2572),new cljs.core.Keyword(null,"report-counters","report-counters",-1702609242).cljs$core$IFn$_invoke$arity$1(cljs.test.get_and_clear_env_BANG_.call(null))));
})], null)),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [(function (){
cljs.test.set_env_BANG_.call(null,env2571);

cljs.test.do_report.call(null,cljs.core.deref.call(null,summary2572));

cljs.test.report.call(null,cljs.core.assoc.call(null,cljs.core.deref.call(null,summary2572),new cljs.core.Keyword(null,"type","type",1174270348),new cljs.core.Keyword(null,"end-run-tests","end-run-tests",267300563)));

return cljs.test.clear_env_BANG_.call(null);
})], null));
})());
}));

(app.test_runner._main.cljs$lang$maxFixedArity = (0));

/** @this {Function} */
(app.test_runner._main.cljs$lang$applyTo = (function (seq2570){
var self__4724__auto__ = this;
return self__4724__auto__.cljs$core$IFn$_invoke$arity$variadic(cljs.core.seq.call(null,seq2570));
}));


//# sourceMappingURL=test_runner.js.map

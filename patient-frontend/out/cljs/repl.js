// Compiled by ClojureScript 1.10.773 {}
goog.provide('cljs.repl');
goog.require('cljs.core');
goog.require('cljs.spec.alpha');
goog.require('goog.string');
goog.require('goog.string.format');
cljs.repl.print_doc = (function cljs$repl$print_doc(p__1663){
var map__1664 = p__1663;
var map__1664__$1 = (((((!((map__1664 == null))))?(((((map__1664.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1664.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1664):map__1664);
var m = map__1664__$1;
var n = cljs.core.get.call(null,map__1664__$1,new cljs.core.Keyword(null,"ns","ns",441598760));
var nm = cljs.core.get.call(null,map__1664__$1,new cljs.core.Keyword(null,"name","name",1843675177));
cljs.core.println.call(null,"-------------------------");

cljs.core.println.call(null,(function (){var or__4126__auto__ = new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
return [(function (){var temp__5735__auto__ = new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(temp__5735__auto__)){
var ns = temp__5735__auto__;
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(ns),"/"].join('');
} else {
return null;
}
})(),cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join('');
}
})());

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Protocol");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m))){
var seq__1666_1698 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m));
var chunk__1667_1699 = null;
var count__1668_1700 = (0);
var i__1669_1701 = (0);
while(true){
if((i__1669_1701 < count__1668_1700)){
var f_1702 = cljs.core._nth.call(null,chunk__1667_1699,i__1669_1701);
cljs.core.println.call(null,"  ",f_1702);


var G__1703 = seq__1666_1698;
var G__1704 = chunk__1667_1699;
var G__1705 = count__1668_1700;
var G__1706 = (i__1669_1701 + (1));
seq__1666_1698 = G__1703;
chunk__1667_1699 = G__1704;
count__1668_1700 = G__1705;
i__1669_1701 = G__1706;
continue;
} else {
var temp__5735__auto___1707 = cljs.core.seq.call(null,seq__1666_1698);
if(temp__5735__auto___1707){
var seq__1666_1708__$1 = temp__5735__auto___1707;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__1666_1708__$1)){
var c__4556__auto___1709 = cljs.core.chunk_first.call(null,seq__1666_1708__$1);
var G__1710 = cljs.core.chunk_rest.call(null,seq__1666_1708__$1);
var G__1711 = c__4556__auto___1709;
var G__1712 = cljs.core.count.call(null,c__4556__auto___1709);
var G__1713 = (0);
seq__1666_1698 = G__1710;
chunk__1667_1699 = G__1711;
count__1668_1700 = G__1712;
i__1669_1701 = G__1713;
continue;
} else {
var f_1714 = cljs.core.first.call(null,seq__1666_1708__$1);
cljs.core.println.call(null,"  ",f_1714);


var G__1715 = cljs.core.next.call(null,seq__1666_1708__$1);
var G__1716 = null;
var G__1717 = (0);
var G__1718 = (0);
seq__1666_1698 = G__1715;
chunk__1667_1699 = G__1716;
count__1668_1700 = G__1717;
i__1669_1701 = G__1718;
continue;
}
} else {
}
}
break;
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m))){
var arglists_1719 = new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_((function (){var or__4126__auto__ = new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
return new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m);
}
})())){
cljs.core.prn.call(null,arglists_1719);
} else {
cljs.core.prn.call(null,((cljs.core._EQ_.call(null,new cljs.core.Symbol(null,"quote","quote",1377916282,null),cljs.core.first.call(null,arglists_1719)))?cljs.core.second.call(null,arglists_1719):arglists_1719));
}
} else {
}
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"special-form","special-form",-1326536374).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Special Form");

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.contains_QMARK_.call(null,m,new cljs.core.Keyword(null,"url","url",276297046))){
if(cljs.core.truth_(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))){
return cljs.core.println.call(null,["\n  Please see http://clojure.org/",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))].join(''));
} else {
return null;
}
} else {
return cljs.core.println.call(null,["\n  Please see http://clojure.org/special_forms#",cljs.core.str.cljs$core$IFn$_invoke$arity$1(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join(''));
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Macro");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"spec","spec",347520401).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Spec");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"REPL Special Function");
} else {
}

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
var seq__1670_1720 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"methods","methods",453930866).cljs$core$IFn$_invoke$arity$1(m));
var chunk__1671_1721 = null;
var count__1672_1722 = (0);
var i__1673_1723 = (0);
while(true){
if((i__1673_1723 < count__1672_1722)){
var vec__1684_1724 = cljs.core._nth.call(null,chunk__1671_1721,i__1673_1723);
var name_1725 = cljs.core.nth.call(null,vec__1684_1724,(0),null);
var map__1687_1726 = cljs.core.nth.call(null,vec__1684_1724,(1),null);
var map__1687_1727__$1 = (((((!((map__1687_1726 == null))))?(((((map__1687_1726.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1687_1726.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1687_1726):map__1687_1726);
var doc_1728 = cljs.core.get.call(null,map__1687_1727__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_1729 = cljs.core.get.call(null,map__1687_1727__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name_1725);

cljs.core.println.call(null," ",arglists_1729);

if(cljs.core.truth_(doc_1728)){
cljs.core.println.call(null," ",doc_1728);
} else {
}


var G__1730 = seq__1670_1720;
var G__1731 = chunk__1671_1721;
var G__1732 = count__1672_1722;
var G__1733 = (i__1673_1723 + (1));
seq__1670_1720 = G__1730;
chunk__1671_1721 = G__1731;
count__1672_1722 = G__1732;
i__1673_1723 = G__1733;
continue;
} else {
var temp__5735__auto___1734 = cljs.core.seq.call(null,seq__1670_1720);
if(temp__5735__auto___1734){
var seq__1670_1735__$1 = temp__5735__auto___1734;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__1670_1735__$1)){
var c__4556__auto___1736 = cljs.core.chunk_first.call(null,seq__1670_1735__$1);
var G__1737 = cljs.core.chunk_rest.call(null,seq__1670_1735__$1);
var G__1738 = c__4556__auto___1736;
var G__1739 = cljs.core.count.call(null,c__4556__auto___1736);
var G__1740 = (0);
seq__1670_1720 = G__1737;
chunk__1671_1721 = G__1738;
count__1672_1722 = G__1739;
i__1673_1723 = G__1740;
continue;
} else {
var vec__1689_1741 = cljs.core.first.call(null,seq__1670_1735__$1);
var name_1742 = cljs.core.nth.call(null,vec__1689_1741,(0),null);
var map__1692_1743 = cljs.core.nth.call(null,vec__1689_1741,(1),null);
var map__1692_1744__$1 = (((((!((map__1692_1743 == null))))?(((((map__1692_1743.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1692_1743.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1692_1743):map__1692_1743);
var doc_1745 = cljs.core.get.call(null,map__1692_1744__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists_1746 = cljs.core.get.call(null,map__1692_1744__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name_1742);

cljs.core.println.call(null," ",arglists_1746);

if(cljs.core.truth_(doc_1745)){
cljs.core.println.call(null," ",doc_1745);
} else {
}


var G__1747 = cljs.core.next.call(null,seq__1670_1735__$1);
var G__1748 = null;
var G__1749 = (0);
var G__1750 = (0);
seq__1670_1720 = G__1747;
chunk__1671_1721 = G__1748;
count__1672_1722 = G__1749;
i__1673_1723 = G__1750;
continue;
}
} else {
}
}
break;
}
} else {
}

if(cljs.core.truth_(n)){
var temp__5735__auto__ = cljs.spec.alpha.get_spec.call(null,cljs.core.symbol.call(null,cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.ns_name.call(null,n)),cljs.core.name.call(null,nm)));
if(cljs.core.truth_(temp__5735__auto__)){
var fnspec = temp__5735__auto__;
cljs.core.print.call(null,"Spec");

var seq__1694 = cljs.core.seq.call(null,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"args","args",1315556576),new cljs.core.Keyword(null,"ret","ret",-468222814),new cljs.core.Keyword(null,"fn","fn",-1175266204)], null));
var chunk__1695 = null;
var count__1696 = (0);
var i__1697 = (0);
while(true){
if((i__1697 < count__1696)){
var role = cljs.core._nth.call(null,chunk__1695,i__1697);
var temp__5735__auto___1751__$1 = cljs.core.get.call(null,fnspec,role);
if(cljs.core.truth_(temp__5735__auto___1751__$1)){
var spec_1752 = temp__5735__auto___1751__$1;
cljs.core.print.call(null,["\n ",cljs.core.name.call(null,role),":"].join(''),cljs.spec.alpha.describe.call(null,spec_1752));
} else {
}


var G__1753 = seq__1694;
var G__1754 = chunk__1695;
var G__1755 = count__1696;
var G__1756 = (i__1697 + (1));
seq__1694 = G__1753;
chunk__1695 = G__1754;
count__1696 = G__1755;
i__1697 = G__1756;
continue;
} else {
var temp__5735__auto____$1 = cljs.core.seq.call(null,seq__1694);
if(temp__5735__auto____$1){
var seq__1694__$1 = temp__5735__auto____$1;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__1694__$1)){
var c__4556__auto__ = cljs.core.chunk_first.call(null,seq__1694__$1);
var G__1757 = cljs.core.chunk_rest.call(null,seq__1694__$1);
var G__1758 = c__4556__auto__;
var G__1759 = cljs.core.count.call(null,c__4556__auto__);
var G__1760 = (0);
seq__1694 = G__1757;
chunk__1695 = G__1758;
count__1696 = G__1759;
i__1697 = G__1760;
continue;
} else {
var role = cljs.core.first.call(null,seq__1694__$1);
var temp__5735__auto___1761__$2 = cljs.core.get.call(null,fnspec,role);
if(cljs.core.truth_(temp__5735__auto___1761__$2)){
var spec_1762 = temp__5735__auto___1761__$2;
cljs.core.print.call(null,["\n ",cljs.core.name.call(null,role),":"].join(''),cljs.spec.alpha.describe.call(null,spec_1762));
} else {
}


var G__1763 = cljs.core.next.call(null,seq__1694__$1);
var G__1764 = null;
var G__1765 = (0);
var G__1766 = (0);
seq__1694 = G__1763;
chunk__1695 = G__1764;
count__1696 = G__1765;
i__1697 = G__1766;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return null;
}
} else {
return null;
}
}
});
/**
 * Constructs a data representation for a Error with keys:
 *  :cause - root cause message
 *  :phase - error phase
 *  :via - cause chain, with cause keys:
 *           :type - exception class symbol
 *           :message - exception message
 *           :data - ex-data
 *           :at - top stack element
 *  :trace - root cause stack elements
 */
cljs.repl.Error__GT_map = (function cljs$repl$Error__GT_map(o){
var base = (function (t){
return cljs.core.merge.call(null,new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"type","type",1174270348),(((t instanceof cljs.core.ExceptionInfo))?new cljs.core.Symbol(null,"ExceptionInfo","ExceptionInfo",294935087,null):(((t instanceof Error))?cljs.core.symbol.call(null,"js",t.name):null
))], null),(function (){var temp__5735__auto__ = cljs.core.ex_message.call(null,t);
if(cljs.core.truth_(temp__5735__auto__)){
var msg = temp__5735__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"message","message",-406056002),msg], null);
} else {
return null;
}
})(),(function (){var temp__5735__auto__ = cljs.core.ex_data.call(null,t);
if(cljs.core.truth_(temp__5735__auto__)){
var ed = temp__5735__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"data","data",-232669377),ed], null);
} else {
return null;
}
})());
});
var via = (function (){var via = cljs.core.PersistentVector.EMPTY;
var t = o;
while(true){
if(cljs.core.truth_(t)){
var G__1767 = cljs.core.conj.call(null,via,t);
var G__1768 = cljs.core.ex_cause.call(null,t);
via = G__1767;
t = G__1768;
continue;
} else {
return via;
}
break;
}
})();
var root = cljs.core.peek.call(null,via);
return cljs.core.merge.call(null,new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"via","via",-1904457336),cljs.core.vec.call(null,cljs.core.map.call(null,base,via)),new cljs.core.Keyword(null,"trace","trace",-1082747415),null], null),(function (){var temp__5735__auto__ = cljs.core.ex_message.call(null,root);
if(cljs.core.truth_(temp__5735__auto__)){
var root_msg = temp__5735__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"cause","cause",231901252),root_msg], null);
} else {
return null;
}
})(),(function (){var temp__5735__auto__ = cljs.core.ex_data.call(null,root);
if(cljs.core.truth_(temp__5735__auto__)){
var data = temp__5735__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"data","data",-232669377),data], null);
} else {
return null;
}
})(),(function (){var temp__5735__auto__ = new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358).cljs$core$IFn$_invoke$arity$1(cljs.core.ex_data.call(null,o));
if(cljs.core.truth_(temp__5735__auto__)){
var phase = temp__5735__auto__;
return new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"phase","phase",575722892),phase], null);
} else {
return null;
}
})());
});
/**
 * Returns an analysis of the phase, error, cause, and location of an error that occurred
 *   based on Throwable data, as returned by Throwable->map. All attributes other than phase
 *   are optional:
 *  :clojure.error/phase - keyword phase indicator, one of:
 *    :read-source :compile-syntax-check :compilation :macro-syntax-check :macroexpansion
 *    :execution :read-eval-result :print-eval-result
 *  :clojure.error/source - file name (no path)
 *  :clojure.error/line - integer line number
 *  :clojure.error/column - integer column number
 *  :clojure.error/symbol - symbol being expanded/compiled/invoked
 *  :clojure.error/class - cause exception class symbol
 *  :clojure.error/cause - cause exception message
 *  :clojure.error/spec - explain-data for spec error
 */
cljs.repl.ex_triage = (function cljs$repl$ex_triage(datafied_throwable){
var map__1771 = datafied_throwable;
var map__1771__$1 = (((((!((map__1771 == null))))?(((((map__1771.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1771.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1771):map__1771);
var via = cljs.core.get.call(null,map__1771__$1,new cljs.core.Keyword(null,"via","via",-1904457336));
var trace = cljs.core.get.call(null,map__1771__$1,new cljs.core.Keyword(null,"trace","trace",-1082747415));
var phase = cljs.core.get.call(null,map__1771__$1,new cljs.core.Keyword(null,"phase","phase",575722892),new cljs.core.Keyword(null,"execution","execution",253283524));
var map__1772 = cljs.core.last.call(null,via);
var map__1772__$1 = (((((!((map__1772 == null))))?(((((map__1772.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1772.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1772):map__1772);
var type = cljs.core.get.call(null,map__1772__$1,new cljs.core.Keyword(null,"type","type",1174270348));
var message = cljs.core.get.call(null,map__1772__$1,new cljs.core.Keyword(null,"message","message",-406056002));
var data = cljs.core.get.call(null,map__1772__$1,new cljs.core.Keyword(null,"data","data",-232669377));
var map__1773 = data;
var map__1773__$1 = (((((!((map__1773 == null))))?(((((map__1773.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1773.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1773):map__1773);
var problems = cljs.core.get.call(null,map__1773__$1,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814));
var fn = cljs.core.get.call(null,map__1773__$1,new cljs.core.Keyword("cljs.spec.alpha","fn","cljs.spec.alpha/fn",408600443));
var caller = cljs.core.get.call(null,map__1773__$1,new cljs.core.Keyword("cljs.spec.test.alpha","caller","cljs.spec.test.alpha/caller",-398302390));
var map__1774 = new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.first.call(null,via));
var map__1774__$1 = (((((!((map__1774 == null))))?(((((map__1774.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1774.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1774):map__1774);
var top_data = map__1774__$1;
var source = cljs.core.get.call(null,map__1774__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
return cljs.core.assoc.call(null,(function (){var G__1779 = phase;
var G__1779__$1 = (((G__1779 instanceof cljs.core.Keyword))?G__1779.fqn:null);
switch (G__1779__$1) {
case "read-source":
var map__1780 = data;
var map__1780__$1 = (((((!((map__1780 == null))))?(((((map__1780.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1780.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1780):map__1780);
var line = cljs.core.get.call(null,map__1780__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.call(null,map__1780__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var G__1782 = cljs.core.merge.call(null,new cljs.core.Keyword(null,"data","data",-232669377).cljs$core$IFn$_invoke$arity$1(cljs.core.second.call(null,via)),top_data);
var G__1782__$1 = (cljs.core.truth_(source)?cljs.core.assoc.call(null,G__1782,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__1782);
var G__1782__$2 = (cljs.core.truth_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,source))?cljs.core.dissoc.call(null,G__1782__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__1782__$1);
if(cljs.core.truth_(message)){
return cljs.core.assoc.call(null,G__1782__$2,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__1782__$2;
}

break;
case "compile-syntax-check":
case "compilation":
case "macro-syntax-check":
case "macroexpansion":
var G__1783 = top_data;
var G__1783__$1 = (cljs.core.truth_(source)?cljs.core.assoc.call(null,G__1783,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),source):G__1783);
var G__1783__$2 = (cljs.core.truth_(new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,source))?cljs.core.dissoc.call(null,G__1783__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397)):G__1783__$1);
var G__1783__$3 = (cljs.core.truth_(type)?cljs.core.assoc.call(null,G__1783__$2,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__1783__$2);
var G__1783__$4 = (cljs.core.truth_(message)?cljs.core.assoc.call(null,G__1783__$3,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__1783__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.call(null,G__1783__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__1783__$4;
}

break;
case "read-eval-result":
case "print-eval-result":
var vec__1784 = cljs.core.first.call(null,trace);
var source__$1 = cljs.core.nth.call(null,vec__1784,(0),null);
var method = cljs.core.nth.call(null,vec__1784,(1),null);
var file = cljs.core.nth.call(null,vec__1784,(2),null);
var line = cljs.core.nth.call(null,vec__1784,(3),null);
var G__1787 = top_data;
var G__1787__$1 = (cljs.core.truth_(line)?cljs.core.assoc.call(null,G__1787,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),line):G__1787);
var G__1787__$2 = (cljs.core.truth_(file)?cljs.core.assoc.call(null,G__1787__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file):G__1787__$1);
var G__1787__$3 = (cljs.core.truth_((function (){var and__4115__auto__ = source__$1;
if(cljs.core.truth_(and__4115__auto__)){
return method;
} else {
return and__4115__auto__;
}
})())?cljs.core.assoc.call(null,G__1787__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null))):G__1787__$2);
var G__1787__$4 = (cljs.core.truth_(type)?cljs.core.assoc.call(null,G__1787__$3,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type):G__1787__$3);
if(cljs.core.truth_(message)){
return cljs.core.assoc.call(null,G__1787__$4,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message);
} else {
return G__1787__$4;
}

break;
case "execution":
var vec__1788 = cljs.core.first.call(null,trace);
var source__$1 = cljs.core.nth.call(null,vec__1788,(0),null);
var method = cljs.core.nth.call(null,vec__1788,(1),null);
var file = cljs.core.nth.call(null,vec__1788,(2),null);
var line = cljs.core.nth.call(null,vec__1788,(3),null);
var file__$1 = cljs.core.first.call(null,cljs.core.remove.call(null,(function (p1__1770_SHARP_){
var or__4126__auto__ = (p1__1770_SHARP_ == null);
if(or__4126__auto__){
return or__4126__auto__;
} else {
return new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["NO_SOURCE_PATH",null,"NO_SOURCE_FILE",null], null), null).call(null,p1__1770_SHARP_);
}
}),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"file","file",-1269645878).cljs$core$IFn$_invoke$arity$1(caller),file], null)));
var err_line = (function (){var or__4126__auto__ = new cljs.core.Keyword(null,"line","line",212345235).cljs$core$IFn$_invoke$arity$1(caller);
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
return line;
}
})();
var G__1791 = new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890),type], null);
var G__1791__$1 = (cljs.core.truth_(err_line)?cljs.core.assoc.call(null,G__1791,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471),err_line):G__1791);
var G__1791__$2 = (cljs.core.truth_(message)?cljs.core.assoc.call(null,G__1791__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742),message):G__1791__$1);
var G__1791__$3 = (cljs.core.truth_((function (){var or__4126__auto__ = fn;
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
var and__4115__auto__ = source__$1;
if(cljs.core.truth_(and__4115__auto__)){
return method;
} else {
return and__4115__auto__;
}
}
})())?cljs.core.assoc.call(null,G__1791__$2,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994),(function (){var or__4126__auto__ = fn;
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
return (new cljs.core.PersistentVector(null,2,(5),cljs.core.PersistentVector.EMPTY_NODE,[source__$1,method],null));
}
})()):G__1791__$2);
var G__1791__$4 = (cljs.core.truth_(file__$1)?cljs.core.assoc.call(null,G__1791__$3,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397),file__$1):G__1791__$3);
if(cljs.core.truth_(problems)){
return cljs.core.assoc.call(null,G__1791__$4,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595),data);
} else {
return G__1791__$4;
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__1779__$1)].join('')));

}
})(),new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358),phase);
});
/**
 * Returns a string from exception data, as produced by ex-triage.
 *   The first line summarizes the exception phase and location.
 *   The subsequent lines describe the cause.
 */
cljs.repl.ex_str = (function cljs$repl$ex_str(p__1795){
var map__1796 = p__1795;
var map__1796__$1 = (((((!((map__1796 == null))))?(((((map__1796.cljs$lang$protocol_mask$partition0$ & (64))) || ((cljs.core.PROTOCOL_SENTINEL === map__1796.cljs$core$ISeq$))))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__1796):map__1796);
var triage_data = map__1796__$1;
var phase = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","phase","clojure.error/phase",275140358));
var source = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","source","clojure.error/source",-2011936397));
var line = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","line","clojure.error/line",-1816287471));
var column = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","column","clojure.error/column",304721553));
var symbol = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","symbol","clojure.error/symbol",1544821994));
var class$ = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","class","clojure.error/class",278435890));
var cause = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","cause","clojure.error/cause",-1879175742));
var spec = cljs.core.get.call(null,map__1796__$1,new cljs.core.Keyword("clojure.error","spec","clojure.error/spec",2055032595));
var loc = [cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__4126__auto__ = source;
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
return "<cljs repl>";
}
})()),":",cljs.core.str.cljs$core$IFn$_invoke$arity$1((function (){var or__4126__auto__ = line;
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
return (1);
}
})()),(cljs.core.truth_(column)?[":",cljs.core.str.cljs$core$IFn$_invoke$arity$1(column)].join(''):"")].join('');
var class_name = cljs.core.name.call(null,(function (){var or__4126__auto__ = class$;
if(cljs.core.truth_(or__4126__auto__)){
return or__4126__auto__;
} else {
return "";
}
})());
var simple_class = class_name;
var cause_type = ((cljs.core.contains_QMARK_.call(null,new cljs.core.PersistentHashSet(null, new cljs.core.PersistentArrayMap(null, 2, ["RuntimeException",null,"Exception",null], null), null),simple_class))?"":[" (",simple_class,")"].join(''));
var format = goog.string.format;
var G__1798 = phase;
var G__1798__$1 = (((G__1798 instanceof cljs.core.Keyword))?G__1798.fqn:null);
switch (G__1798__$1) {
case "read-source":
return format.call(null,"Syntax error reading source at (%s).\n%s\n",loc,cause);

break;
case "macro-syntax-check":
return format.call(null,"Syntax error macroexpanding %sat (%s).\n%s",(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,(cljs.core.truth_(spec)?(function (){var sb__4667__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__1799_1808 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__1800_1809 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__1801_1810 = true;
var _STAR_print_fn_STAR__temp_val__1802_1811 = (function (x__4668__auto__){
return sb__4667__auto__.append(x__4668__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__1801_1810);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__1802_1811);

try{cljs.spec.alpha.explain_out.call(null,cljs.core.update.call(null,spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.call(null,(function (p1__1793_SHARP_){
return cljs.core.dissoc.call(null,p1__1793_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__1800_1809);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__1799_1808);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__4667__auto__);
})():format.call(null,"%s\n",cause)));

break;
case "macroexpansion":
return format.call(null,"Unexpected error%s macroexpanding %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "compile-syntax-check":
return format.call(null,"Syntax error%s compiling %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "compilation":
return format.call(null,"Unexpected error%s compiling %sat (%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);

break;
case "read-eval-result":
return format.call(null,"Error reading eval result%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause);

break;
case "print-eval-result":
return format.call(null,"Error printing return value%s at %s (%s).\n%s\n",cause_type,symbol,loc,cause);

break;
case "execution":
if(cljs.core.truth_(spec)){
return format.call(null,"Execution error - invalid arguments to %s at (%s).\n%s",symbol,loc,(function (){var sb__4667__auto__ = (new goog.string.StringBuffer());
var _STAR_print_newline_STAR__orig_val__1803_1812 = cljs.core._STAR_print_newline_STAR_;
var _STAR_print_fn_STAR__orig_val__1804_1813 = cljs.core._STAR_print_fn_STAR_;
var _STAR_print_newline_STAR__temp_val__1805_1814 = true;
var _STAR_print_fn_STAR__temp_val__1806_1815 = (function (x__4668__auto__){
return sb__4667__auto__.append(x__4668__auto__);
});
(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__temp_val__1805_1814);

(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__temp_val__1806_1815);

try{cljs.spec.alpha.explain_out.call(null,cljs.core.update.call(null,spec,new cljs.core.Keyword("cljs.spec.alpha","problems","cljs.spec.alpha/problems",447400814),(function (probs){
return cljs.core.map.call(null,(function (p1__1794_SHARP_){
return cljs.core.dissoc.call(null,p1__1794_SHARP_,new cljs.core.Keyword(null,"in","in",-1531184865));
}),probs);
}))
);
}finally {(cljs.core._STAR_print_fn_STAR_ = _STAR_print_fn_STAR__orig_val__1804_1813);

(cljs.core._STAR_print_newline_STAR_ = _STAR_print_newline_STAR__orig_val__1803_1812);
}
return cljs.core.str.cljs$core$IFn$_invoke$arity$1(sb__4667__auto__);
})());
} else {
return format.call(null,"Execution error%s at %s(%s).\n%s\n",cause_type,(cljs.core.truth_(symbol)?[cljs.core.str.cljs$core$IFn$_invoke$arity$1(symbol)," "].join(''):""),loc,cause);
}

break;
default:
throw (new Error(["No matching clause: ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(G__1798__$1)].join('')));

}
});
cljs.repl.error__GT_str = (function cljs$repl$error__GT_str(error){
return cljs.repl.ex_str.call(null,cljs.repl.ex_triage.call(null,cljs.repl.Error__GT_map.call(null,error)));
});

//# sourceMappingURL=repl.js.map

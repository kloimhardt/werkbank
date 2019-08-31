rm src/abc/la_habra.cljs
cat code_template/la_habra_trunk_1.cljs >src/abc/la_habra.cljs
echo "(defn cx [frame] (list))" >>src/abc/la_habra.cljs
cat code_template/la_habra_trunk_2.cljs >>src/abc/la_habra.cljs

rm src/abc/code.clj
cp code_template/code_trunk.clj src/abc/code.clj
clojure -m figwheel.main --build dev

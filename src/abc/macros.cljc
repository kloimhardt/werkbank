(ns abc.macros
  #?(:clj (:require [abc.setup :as setup]))
  #?(:cljs (:require-macros [abc.macros :refer [la-habra]])))

#?(:clj (defmacro la-habra [] @setup/la-habra-def))
